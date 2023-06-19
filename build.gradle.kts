import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.libsDirectory

plugins {
    kotlin("jvm") version "1.8.20"
}

group = "org.mrlem.kasc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    testImplementation(kotlin("test"))
}