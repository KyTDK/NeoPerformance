plugins {
    `java-library`
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/"){
        content {
            includeGroup("org.spigotmc")
        }
        metadataSources {
            artifact()
        }
    }    /*
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
    mavenLocal()
    mavenCentral()
    // This is needed for CraftBukkit and Spigot.

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
    apiVersion = "1.13"

    // Other possible properties from plugin.yml (optional)
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.STARTUP // or POSTWORLD
    authors = listOf("NeoDevs")
    prefix = "NeoPerformance"
    defaultPermission = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP // TRUE, FALSE, OP or NOT_OP

    commands {
        register("neoperformance") {
            description = "Show server performance"
            aliases = listOf("np","performance")
            permission = "neoperformance.admin"
            usage = "/neoperformance"
        }
        // ...
    }
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")    // Pick only one of these and read the comment in the repositories block.
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT") // The Spigot API with no shadowing. Requires the OSS repo.
    compileOnly("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT") // The full Spigot server with no shadowing. Requires mavenLocal.
    implementation("io.netty:netty-all:4.1.77.Final")
    implementation("org.jetbrains.kotlin:kotlin-annotation-processing-gradle:1.4.20")
}