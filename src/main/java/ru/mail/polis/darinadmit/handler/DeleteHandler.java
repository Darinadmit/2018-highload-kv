package ru.mail.polis.darinadmit.handler;

import one.nio.http.HttpClient;
import one.nio.http.HttpException;
import one.nio.http.Response;
import one.nio.pool.PoolException;
import org.jetbrains.annotations.NotNull;
import ru.mail.polis.KVDao;
import ru.mail.polis.darinadmit.KVDaoServiceImpl;
import ru.mail.polis.darinadmit.    RF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 * Обработка DELETE запроса
 */
public class DeleteHandler extends RequestHandler {

    public DeleteHandler(@NotNull String methodName, @NotNull KVDao dao, @NotNull RF rf, String id) {
        super(methodName, dao, rf, id, null);
    }

    @Override
    public Response onProxied() {
        dao.remove(id.getBytes());
        return new Response(Response.ACCEPTED, Response.EMPTY);
    }

    @Override
    public Callable<Boolean> ifMe() {
        return () -> {
            dao.remove(id.getBytes());
            return true;
        };
    }

    @Override
    public Callable<Boolean> ifNotMe(HttpClient client) {
        return () -> {
            final Response response = client.delete(KVDaoServiceImpl.ENTITY_PATH + "?id=" + id, KVDaoServiceImpl.PROXY_HEADER);
            return response.getStatus() == 202;
        };
    }

    @Override
    public Response onSuccess(int acks) {
        return success(Response.ACCEPTED, acks, Response.EMPTY);
    }

    @Override
    public Response onFail(int acks) {
        return gatewayTimeout(acks);
    }

    /**
     * @param futures список будущих ack
     * @return <ol>
     * <li> 202 Accepted, если хотя бы ack из from реплик подтвердили операцию </li>
     * <li> 504 Not Enough Replicas, если не набралось ack подтверждений из всего множества from реплик </li>
     * </ol>
     */
    @Override
    public Response getResponse(ArrayList<Future<Boolean>> futures) {
        return super.getResponse(futures);
    }
}