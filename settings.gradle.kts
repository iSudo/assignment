pluginManagement {
    plugins {
        id("java")
        id("org.siouan.fronend-jdk17") version "8.0.0"
        id("org.springframework.boot") version "3.1.4"
        id("io.spring.dependency-management") version "1.1.0"
    }
}

rootProject.name = "helmes-technical-assignment"

include("backend", "frontend")

