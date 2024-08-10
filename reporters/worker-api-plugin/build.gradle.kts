plugins {
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("worker-api-plugin") {
            id = "reporters.worker.api.plugin"
            implementationClass = "reporters.WorkerApiPlugin"
        }
    }
}
