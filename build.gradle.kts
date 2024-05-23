import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.9.21"
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("io.papermc.paperweight.userdev").version("1.5.11")
	id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
	id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1"
}

// TODO: change this to your needs
val mainClassName = "SamplePlugin"
group = "dev.himirai.${mainClassName.lowercase()}"
version = "1.0.0-SNAPSHOT"
val internal = "$group.internal"

repositories {
	mavenCentral()
	maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
	paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
	testImplementation(platform("org.junit:junit-bom:5.10.2"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation(kotlin("test"))
	testImplementation("com.github.seeseemelk:MockBukkit-v1.20:3.86.1")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

bukkit {
	main = "$group.$mainClassName"
	apiVersion = "1.19"
	author = "Himirai"
	version = project.version.toString()
	load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
//    depend = listOf("WorldEdit")
//    softDepend = listOf("Vault")
}

tasks {
	test {
		useJUnitPlatform()
		testLogging {
			events("passed", "skipped", "failed")
		}
	}

	shadowJar {
		val relocations = listOf("org.intellij", "org.jetbrains", "kotlin")
		relocations.forEach { relocate(it, "$internal.$it") }
		archiveClassifier.set("")
		archiveFileName.set("v${project.version}/$mainClassName.jar")
	}

	assemble {
		dependsOn(reobfJar)
	}

	reobfJar {
		outputJar.set(layout.buildDirectory.file("libs/v${project.version}/${project.name}-remapped.jar"))
		doFirst {
			val versionDir = file("${layout.buildDirectory}/libs/v${project.version}")
			if (!versionDir.exists()) versionDir.mkdirs()
		}
	}

	build {
		dependsOn(shadowJar)
	}

	withType<KotlinCompile> {
		kotlinOptions.jvmTarget = "17"
	}
}
