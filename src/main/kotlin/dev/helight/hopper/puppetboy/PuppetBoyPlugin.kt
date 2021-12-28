package dev.helight.hopper.puppetboy

import co.aikar.commands.PaperCommandManager
import dev.helight.hopper.puppetboy.commands.PuppetBoyCommand
import dev.helight.hopper.puppetboy.goals.Flags
import org.bukkit.plugin.java.JavaPlugin

class PuppetBoyPlugin : JavaPlugin() {

    lateinit var puppetBoy: PuppetBoy
    lateinit var commandManager: PaperCommandManager

    override fun onLoad() {
        puppetBoy = PuppetBoy(this)
    }

    override fun onEnable() {
        commandManager = PaperCommandManager(this)
        commandManager.registerCommand(PuppetBoyCommand(puppetBoy))
    }
}