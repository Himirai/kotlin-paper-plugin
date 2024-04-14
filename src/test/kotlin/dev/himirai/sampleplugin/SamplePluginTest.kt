package dev.himirai.sampleplugin

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.logging.Level
import kotlin.properties.Delegates

class SamplePluginTest {

    var server: ServerMock by Delegates.notNull()
    var plugin: SamplePlugin by Delegates.notNull()
    var player: PlayerMock by Delegates.notNull()
    val notPassed: String by lazy { "Test `{test}` not passed" }

    @BeforeEach
    fun setup() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(SamplePlugin::class.java)
        plugin.logger.log(Level.INFO, "Server is ready")
    }

    @Test
    fun testSpeed() {
        player = server.addPlayer()
        assert(player.level == 100) { notPassed.replace("{test}", "level") } // TRUE
//        assert(player.level == 99) { notPassed.replace("{test}", "level") }                       // FALSE
    }

    @AfterEach
    fun teardown() {
        MockBukkit.unmock()
        plugin.logger.log(Level.INFO, "Server closed")
    }

}