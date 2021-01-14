import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.21"
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("plugin.spring") version "1.4.21"
}


group = "com.mlt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.projectreactor:reactor-core:3.4.1")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.cloud:spring-cloud-commons:3.0.0")
    implementation("io.r2dbc:r2dbc-postgresql")
    testImplementation("io.projectreactor:reactor-test:3.4.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

// Set the unit test platform
//https://docs.gradle.org/current/userguide/java_testing.html#compiling_and_executing_junit_jupiter_tests
tasks.withType<Test> {
    useJUnitPlatform()
}


// Jvm options https://kotlinlang.org/docs/reference/using-gradle.html
tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "15"
    }
}