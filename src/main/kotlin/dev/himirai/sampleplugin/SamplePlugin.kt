package dev.himirai.sampleplugin

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

lateinit var PLUGIN: SamplePlugin

class SamplePlugin : JavaPlugin(), Listener {

    override fun onEnable() {
        PLUGIN = this
        Bukkit.getPluginManager().registerEvents(this, this)
    }

    @EventHandler
    fun AsyncPlayerPreLoginEvent.on() {
        logger.log(Level.INFO, "Player ${playerProfile.name} pre-logging in...")
    }

    @EventHandler
    fun PlayerLoginEvent.on() {
        logger.log(Level.INFO, "Player ${player.name} logged in!")
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