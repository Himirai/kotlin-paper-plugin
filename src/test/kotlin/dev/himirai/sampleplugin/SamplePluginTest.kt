package dev.himirai.sampleplugin

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.logging.Level
import kotlin.properties.Delegates.notNull

class SamplePluginTest {

	private var server: ServerMock by notNull()
	private var plugin: SamplePlugin by notNull()
	private var player: PlayerMock by notNull()
	private val notPassed: String by lazy { "Test `{test}` not passed" }

	@BeforeEach
	fun setup() {
		server = MockBukkit.mock()
		plugin = MockBukkit.load(SamplePlugin::class.java)
		plugin.logger.log(Level.INFO, "Server is ready")
	}

	@Test
	fun testSpeed() {
		player = server.addPlayer("Himirai")
		assertTrue(player.level == 100) { notPassed.replace("{test}", "level") }
		assertFalse(player.level != 100) { notPassed.replace("{test}", "level") }
		plugin.logger.log(Level.INFO, "All tests passed!")
		player.disconnect()
	}

	@AfterEach
	fun teardown() {
		MockBukkit.unmock()
		plugin.logger.log(Level.INFO, "Server closed")
	}

}
