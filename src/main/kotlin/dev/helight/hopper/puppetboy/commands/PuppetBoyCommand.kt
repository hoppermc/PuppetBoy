package dev.helight.hopper.puppetboy.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import dev.helight.hopper.puppetboy.*
import dev.helight.hopper.puppetboy.EntityExtension.goalSelector
import dev.helight.hopper.puppetboy.EntityExtension.handle
import dev.helight.hopper.puppetboy.goals.EntitySelector
import dev.helight.hopper.puppetboy.goals.impl.KeepDistanceGoal
import dev.helight.hopper.puppetboy.reflections.Classes
import org.bukkit.entity.Entity
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie

@CommandAlias("puppetboy")
class PuppetBoyCommand(
    val puppetBoy: PuppetBoy
) : BaseCommand() {

    @Subcommand("debug goals")
    @CommandPermission("puppetboy.debug")
    fun getGoalsOfEntity(player: Player) {
        val entity = getLookingAt(player)!!
        player.spigot().sendMessage(entity.goalSelector.debugComponent)
    }

    @Subcommand("debug test")
    @CommandPermission("puppetboy.debug")
    fun spawnTest(player: Player) {
        val entity = player.world.spawn(player.location, Zombie::class.java)
        val handle = entity.handle
        val goalSelector = entity.goalSelector
        goalSelector.clear()
        goalSelector.add(0, VanillaGoals.float(handle))
        goalSelector.add(3, VanillaGoals.lookAtPlayer(handle, Classes.ENTITY_HUMAN.getClazz(), 10f, 1f))
        goalSelector.add(2, KeepDistanceGoal(entity, EntitySelector.ConstantEntity(player), 8.0))

    }

    fun getLookingAt(player: Player): Entity? = player.getTargetEntity(100)

}