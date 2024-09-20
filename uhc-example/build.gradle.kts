plugins {
    id("java")
}

group = "fr.bgoodes"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    project.parent?.let { implementation(it) }
}