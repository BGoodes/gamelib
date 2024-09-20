plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("java-library")
    id("java")
}

allprojects {
    group = "fr.bgoodes"
    version = "1.0.0-SNAPSHOT"

    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "java-library")
    apply(plugin = "idea")

    val targetJavaVersion = JavaVersion.VERSION_21
    java {
        sourceCompatibility = targetJavaVersion
        targetCompatibility = targetJavaVersion
        if (JavaVersion.current() < targetJavaVersion) {
            toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion.majorVersion))
        }
    }

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
        compileOnly("org.jetbrains:annotations:24.1.0")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.shadowJar {
        mergeServiceFiles()
    }

    tasks.processResources.configure {
        val props = mapOf(Pair("version", "$version"))

        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}