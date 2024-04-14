package dev.himirai.sampleplugin

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File
import java.util.logging.Level

lateinit var PLUGIN: SamplePlugin

open class SamplePlugin : JavaPlugin, Listener {

	constructor() : super()

	constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File) : super(loader, description, dataFolder, file)

	override fun onEnable() {
		PLUGIN = this
		Bukkit.getPluginManager().registerEvents(this, this)
	}

	@EventHandler
	fun AsyncPlayerPreLoginEvent.on() {
		logger.log(Level.INFO, "Player ${playerProfile.name} pre-logging in...")
	}

	@EventHandler
	fun PlayerJoinEvent.on() {
		logger.log(Level.INFO, "Player ${player.name} connected!")
		player.level = 100
	}

	@EventHandler
	fun PlayerQuitEvent.on() {
		logger.log(Level.INFO, "Player ${player.name} disconnected")
		player.level = 0
	}

}
