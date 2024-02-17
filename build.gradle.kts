import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `maven-publish`
    signing
    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.build.time.tracker)
    alias(libs.plugins.dokka)
    alias(libs.plugins.licensee)
}

group = "io.github.sschrass"
val artifact = "asciidoc-dsl"
version = "0.1.2-SNAPSHOT"
description = "AsciiDoc DSL for Kotlin"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(libs.kotlin.stdlib)
    dokkaJavadocPlugin(libs.dokka.plugin)

    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.shared)
}

licensee {
    allow("Apache-2.0")
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
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

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}

tasks.withType<DependencyUpdatesTask>().configureEach {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

tasks.test {
    useJUnitPlatform()
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any(version.uppercase()::contains)
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
