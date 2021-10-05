plugins {
    kotlin("jvm") version "1.5.31"
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

group = "de.sschrass"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
}
