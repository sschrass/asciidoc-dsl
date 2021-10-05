import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("com.asarkar.gradle.build-time-tracker") version "3.0.1"
}

group = "io.github.sschrass"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_16.toString()
    }
}
