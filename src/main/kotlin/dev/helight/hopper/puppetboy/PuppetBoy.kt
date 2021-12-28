package dev.helight.hopper.puppetboy

import dev.helight.hopper.puppetboy.versions.Controllers
import dev.helight.hopper.puppetboy.versions.Mechanism
import dev.helight.hopper.puppetboy.versions.Version
import dev.helight.hopper.puppetboy.versions.versions.Version1_18
import net.bytebuddy.ByteBuddy
import org.bukkit.plugin.Plugin

class PuppetBoy(
    val plugin: Plugin
) {

    val generation: PuppetGenerationClassLoader
    var version: Version

    init {
        instance = this
        generation = PuppetGenerationClassLoader()
        version = Version1_18()
        version.prepare()
    }

    companion object {
        lateinit var instance: PuppetBoy

        val version: Version
            get() = instance.version

        val mechanism: Mechanism
            get() = instance.version.mechanism

        val controllers: Controllers
            get() = instance.version.controllers
    }
}