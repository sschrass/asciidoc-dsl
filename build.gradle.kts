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
description = "AsciiDoc DSL for Kotlin"

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

tasks.jar {
    manifest {
        attributes(
            "Name" to "${project.group}.$artifact/".replace(".", "/"),
            "Specification-Title" to project.description,
            "Specification-Version" to project.version.toString(),
            "Specification-Vendor" to "${project.group}",
            "Implementation-Title" to "${project.group}.$artifact",
            "Implementation-Version" to project.version.toString(),
            "Implementation-Vendor" to "${project.group}",
        )
    }
}

val dokka = tasks.dokkaJavadoc
val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokka)
    archiveClassifier.set("javadoc")
    from(dokka)
}

publishing {
    val ossrhUsername: String? by project
    val ossrhPassword: String? by project
    val ossrhRepository = if (version.toString().contains("-SNAPSHOT")) {
        "https://s01.oss.sonatype.org/content/repositories/snapshots/"
    } else {
        "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
    }

    repositories {
        maven(ossrhRepository) {
            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }

    publications {
        create<MavenPublication>(rootProject.name) {
            groupId = group.toString()
            artifactId = artifact
            version = version.toString()

            from(components["java"])
            artifact(javadocJar)

            pom {
                name.set(description)
                description.set("A yet very shallow AsciiDoc DSL for Kotlin.")
                url.set("https://github.com/sschrass/asciidoc-dsl")
                licenses {
                    name.set("MIT License")
                    url.set("https://github.com/sschrass/asciidoc-dsl/blob/mainline/LICENSE")
                }
                developers {
                    developer {
                        name.set("Stefan Schrass")
                        email.set("stefan.schrass@gmail.com")
                        organization.set("io.github.sschrass")
                        organizationUrl.set("https://github.com/sschrass")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/sschrass/asciidoc-dsl.git")
                    developerConnection.set("scm:git:git://github.com/sschrass/asciidoc-dsl.git")
                    url.set("https://github.com/sschrass/asciidoc-dsl")
                }
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications[rootProject.name])
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_16.toString()
    }
}
