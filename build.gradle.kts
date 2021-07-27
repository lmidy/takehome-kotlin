val ktorversion: String by project
val kotlinversion: String by project
val logbackversion: String by project
val exposedVersion: String by project
val hikaricpversion: String by project
val ktorflywayversion: String by project
val flywayVersion: String by project
val postgresVersion: String by project

plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "1.5.0"
    id("org.jmailen.kotlinter") version "3.4.4"
    id("org.flywaydb.flyway") version "5.2.4"
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.ktor:ktor-gson:$ktorversion")

    implementation("org.flywaydb:flyway-core:$flywayVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("com.zaxxer:HikariCP:$hikaricpversion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$ktorversion")
}

flyway {
    var url = System.getenv("DB_URL")
    var user = System.getenv("DB_USER")
    var password = System.getenv("DB_PASSWORD")
    var baselineOnMigrate = true
    var locations = arrayOf("classpath:resources/db/migration")
}
