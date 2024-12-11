import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.d5b"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        manifest {
            attributes(mapOf("Main-Class" to "dev.d5b.aoc2024.MainKt"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
