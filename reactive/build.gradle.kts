import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.21"
}


group = "com.mlt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.projectreactor:reactor-core:3.4.1")
    implementation("org.projectlombok:lombok:1.18.16")
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