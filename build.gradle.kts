// See https://gradle.org and https://github.com/gradle/kotlin-dsl

// Apply the java plugin to add support for Java
plugins {
    java
    application
}

repositories {
    jcenter()
}

dependencies {
    // Annotations for better code documentation
    compile("com.intellij:annotations:12.0")

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")

    // HTTP client for unit tests
    testCompile("org.apache.httpcomponents:fluent-hc:4.5.3")

    //MapDB
    compile("org.mapdb:mapdb:3.0.5")

    //SLF4J
    compile("org.slf4j:slf4j-simple:1.6.1");

    compile("org.slf4j:slf4j-api:1.7.25");
    //logback
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("ch.qos.logback:logback-core:1.2.3")

    //One-nio
    compile("ru.odnoklassniki:one-nio:1.0.2")
}

tasks {
    "test"(Test::class) {
        maxHeapSize = "128m"
        useJUnitPlatform()
    }
}

application {
    // Define the main class for the application
    mainClassName = "ru.mail.polis.Cluster"

    // And limit Xmx
    applicationDefaultJvmArgs = listOf("-Xmx128m")
}
