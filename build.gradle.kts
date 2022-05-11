import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    `maven-publish`
    signing
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
    id("com.github.ben-manes.versions") version "0.42.0"
    id("com.autonomousapps.dependency-analysis") version "0.80.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("com.asarkar.gradle.build-time-tracker") version "4.3.0"
    id("org.jetbrains.dokka") version "1.6.21"
    id("app.cash.licensee") version "1.3.1"
}

group = "io.github.sschrass"
val artifact = "asciidoc-dsl"
version = "0.1.1-SNAPSHOT"
description = "AsciiDoc DSL for Kotlin"

repositories {
    mavenCentral()
}

dependencies {
    dokkaJavadocPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.6.21")
    api("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.3.0")
    testImplementation("io.kotest:kotest-assertions-shared-jvm:5.3.0")
}

licensee {
    allow("Apache-2.0")
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

ktlint {
    version.set("0.45.2")
}

tasks.jar {
    manifest {
        attributes(
            "Name" to "${project.group}/$artifact/".replace(".", "/"),
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
    val ossrhRepository = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
        .takeUnless { version.toString().contains("-SNAPSHOT") }
        ?: "https://s01.oss.sonatype.org/content/repositories/snapshots/"

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
                name.set(artifact)
                description.set("A yet very shallow AsciiDoc DSL for Kotlin.")
                url.set("https://github.com/sschrass/asciidoc-dsl")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/sschrass/asciidoc-dsl/blob/mainline/LICENSE")
                    }
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

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

tasks.test {
    useJUnitPlatform()
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any(version.toUpperCase()::contains)
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
