import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    `maven-publish`
    signing
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("com.asarkar.gradle.build-time-tracker") version "3.0.1"
    id("org.jetbrains.dokka") version "1.5.31"
}

group = "io.github.sschrass"
val artifact = "asciidoc-dsl"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    dokkaJavadocPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.5.31")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

publishing {
    publications {
        create<MavenPublication>(rootProject.name) {
            groupId = group.toString()
            artifactId = artifact
            version = version.toString()

            from(components["java"])
        }
    }
}

signing {
    sign(publishing.publications[rootProject.name])
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_16.toString()
    }
}
