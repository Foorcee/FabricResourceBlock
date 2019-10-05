import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("fabric-loom") version "0.2.4-SNAPSHOT"
    id("com.palantir.git-version") version "0.12.0-rc2"
}

group = "de.foorcee.resourceblock"
val gitVersion: groovy.lang.Closure<Any> by extra
version = "0.1.0-SNAPSHOT+" + try {
    gitVersion()
} catch (e: Exception) {
    "unknown"
}
extra.set("archivesBaseName", "ResourceBlock")
description = "Block Server Resource Packs"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
extra.set("sourceCompatibility", 1.8)
extra.set("targetCompatibility", 1.8)

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    maven(url = "https://maven.fabricmc.net/")
    maven(url = "http://server.bbkr.space:8081/artifactory/libs-snapshot")
}


tasks.named<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        filter<ReplaceTokens>("tokens" to mapOf(
                "version" to project.property("version"),
                "description" to project.property("description")
        ))
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.14.4")
    mappings("net.fabricmc:yarn:1.14.4+build.13")
    modCompile("net.fabricmc:fabric-loader:0.6.3+build.167")

    modCompile("net.fabricmc.fabric-api:fabric-api:0.4.0+build.240-1.14")
}

minecraft {
}
