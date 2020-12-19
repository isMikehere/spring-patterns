plugins {
    java
    id("io.freefair.lombok") version "5.3.0"
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

    testImplementation("io.projectreactor:reactor-test:3.4.1")
    testImplementation("junit", "junit", "4.12")
}
