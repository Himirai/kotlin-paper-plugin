import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.9.21"
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

// change this to your needs
val mainClassName = "SamplePlugin"
group = "dev.himirai.${mainClassName.lowercase()}"
version = "1.0.0-SNAPSHOT"
val internal = "$group.internal"

repositories {
	mavenCentral()
	maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")

	testImplementation(platform("org.junit:junit-bom:5.10.2"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation(kotlin("test"))
	testImplementation("com.github.seeseemelk:MockBukkit-v1.19:3.0.0")
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
//        If you need to relocate something, then, uncomment it and paste in list path to folders which should be relocated
//        val relocations = listOf(
//            "com"
//        )
//        relocations.forEach { relocate(it, "$internal.$it") }
		archiveClassifier.set("")
		archiveFileName.set("$mainClassName-${project.version}.jar")
	}

	build {
		dependsOn(shadowJar)
	}

	withType<KotlinCompile> {
		kotlinOptions.jvmTarget = "17"
	}
}
