pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "NeoPerformance"

include(":plugin")
include(":paper")
include(":spigot")
include(":common")