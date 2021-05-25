val ktorversion: String by project
val kotlinversion: String by project
val logbackversion: String by project
val exposedVersion: String by project
val hikaricpversion: String by project
val ktorflywayversion: String by project
val flywayVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"
}

group = "midy"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}
repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorversion")
    implementation("io.ktor:ktor-server-netty:$ktorversion")
    implementation("ch.qos.logback:logback-classic:$logbackversion")
    testImplementation("io.ktor:ktor-server-tests:$ktorversion")

    implementation("io.ktor:ktor-gson:$ktorversion")

    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("com.viartemev:ktor-flyway-feature:$ktorflywayversion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")
    implementation("com.zaxxer:HikariCP:$hikaricpversion")
    implementation("org.postgresql:postgresql:42.2.1")
}

