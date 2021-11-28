plugins {
    id("com.gradle.enterprise") version ("3.7.2")
}

rootProject.name = "AsciidocDsl"

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}
