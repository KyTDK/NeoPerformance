import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.7.0"
    id("io.papermc.paperweight.userdev") version "1.3.7"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "1.0.6" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "com.neomechanical.neoperformance"
version = "1.6"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://jitpack.io")

    maven("https://papermc.io/repo/repository/maven-public/") {
        content {
            includeGroup("io.papermc.paper")
            includeGroup("net.kyori")
            includeGroup("io.papermc")
        }
    }

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        content {
            includeGroup("org.spigotmc")
        }
        metadataSources {
            artifact()
        }
    }
    /*
     As Spigot-API depends on the BungeeCord ChatComponent-API,
    we need to add the Sonatype OSS repository, as Gradle,
    in comparison to maven, doesn't want to understand the ~/.m2
    directory unless added using mavenLocal(). Maven usually just gets
    it from there, as most people have run the BuildTools at least once.
    This is therefore not needed if you're using the full Spigot/CraftBukkit,
    or if you're using the Bukkit API.
    */
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")

    // This is needed for CraftBukkit and Spigot.
    //com.github.tchristofferson:ConfigUpdater

    maven("https://repo.minebench.de/") {
        content {
            includeGroup("de.themoep")
        }
    }

    maven("https://mvn.lumine.io/repository/maven-public/") {
        content {
            includeGroup("io.lumine.xikage")
        }
    }

    maven("https://repo.incendo.org/content/repositories/snapshots") {
        content {
            includeGroup("org.incendo.interfaces")
        }
    }
    maven("https://libraries.minecraft.net/") {
        content {
            includeGroup("com.mojang")
        }
    }
}

dependencies {
    paperDevBundle("1.19-R0.1-SNAPSHOT")

    implementation("org.jetbrains:annotations:23.0.0")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")    // Pick only one of these and read the comment in the repositories block.
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT") // The Spigot API with no shadowing. Requires the OSS repo.
    compileOnly("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT") // The full Spigot server with no shadowing. Requires mavenLocal.
    implementation("com.sun.mail:javax.mail:1.6.2")
    implementation("javax.activation:activation:1.1.1")
    implementation("io.netty:netty-all:4.1.77.Final")
    implementation("org.jetbrains.kotlin:kotlin-annotation-processing-gradle:1.4.20")
    implementation("io.papermc:paperlib:1.0.7")
}
tasks.withType<ShadowJar> {
    dependencies {
        include(dependency("io.papermc:paperlib:"))
        include(dependency("com.sun.mail:javax.mail:1.6.2"))
        include(dependency("javax.activation:activation:1.1.1"))
    }
    archiveClassifier.set("")
}
tasks {
    build {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}

bukkit {
    // Default values can be overridden if needed
    // name = "TestPlugin"
    // version = "1.0"
    // description = "This is a test plugin"
    // website = "https://example.com"
    // author = "Notch"

    // Plugin main class (required)
    main = "com.neomechanical.neoperformance.NeoPerformance"

    // API version (should be set for 1.13+)
    apiVersion = "1.14"
    version = "1.6"
    // Other possible properties from plugin.yml (optional)
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.STARTUP // or POSTWORLD
    authors = listOf("NeoDevs")
    prefix = "NeoPerformance"
    defaultPermission =
        net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP // TRUE, FALSE, OP or NOT_OP

    commands {
        register("neoperformance") {
            description = "Show server performance"
            aliases = listOf("np", "performance")
            permission = "neoperformance.admin"
            usage = "/neoperformance"
        }
    }
}