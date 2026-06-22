plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.3.0"
}

group = "com.github.adombeck"
version = "1.0.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.1")
        instrumentationTools()
    }
}

intellijPlatform {
    pluginConfiguration {
        name = "Current Project Tracker"
        version = "1.0.0"
        ideaVersion {
            sinceBuild = "241"
            untilBuild = provider { null }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
