import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
	`maven-publish`
	kotlin("jvm") version "2.0.0"
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("io.papermc.paperweight.userdev").version("1.7.1")
	id("io.github.patrick.remapper") version "1.4.1"
	id("xyz.jpenilla.run-paper") version "2.2.3"
	id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1"
}

// TODO: change this to your needs
group = "dev.himirai.${project.name.lowercase()}"
version = "1.0.0"

val internal = "$group.internal"

repositories {
	mavenCentral()
	maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
	paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

bukkitPluginYaml {
	main = "$group.${project.name}"
	apiVersion = "1.20"
	authors.add("Himirai")
	version = project.version.toString()
	load = BukkitPluginYaml.PluginLoadOrder.POSTWORLD
//    depend.add("WorldEdit")
//    softDepend.add("Vault")
}

tasks {
	runServer {
		version("1.20.4")
		// provide links to plugins which should be installed
		val plugins = listOf<String>()
		downloadPlugins {
			plugins.forEach { url(it) }
		}
	}

	shadowJar {
		val relocations = listOf("org.intellij", "org.jetbrains", "kotlin")
		relocations.forEach { relocate(it, "$internal.$it") }
		archiveClassifier.set("")
		archiveFileName.set("v${project.version}/${project.name}.jar")
	}

	build {
		dependsOn(reobfJar)
	}

	reobfJar {
		dependsOn(shadowJar)
		outputJar.set(layout.buildDirectory.file("libs/v${project.version}/${project.name}-remapped.jar"))
	}

	assemble {
		dependsOn(reobfJar)
	}
}

afterEvaluate {
	publishing {
		publications {
			create<MavenPublication>("maven") {
				from(components["java"])
				artifactId = project.name
				artifact(tasks.shadowJar.get().archiveFile) {
					classifier = ""
				}
			}
		}
	}
}
