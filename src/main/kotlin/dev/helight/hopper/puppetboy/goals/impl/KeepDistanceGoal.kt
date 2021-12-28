package dev.helight.hopper.puppetboy.goals.impl

import dev.helight.hopper.puppetboy.EntityExtension.insentient
import dev.helight.hopper.puppetboy.goals.EntitySelector
import dev.helight.hopper.puppetboy.goals.Flags
import dev.helight.hopper.puppetboy.goals.PuppetGoal
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob

class KeepDistanceGoal(
    val mob: Mob,
    var selector: EntitySelector,
    val distance: Double,
    val speed: Double = 1.0,
    val recalcInterval: Int = 5
) : PuppetGoal {

    var target: LivingEntity? = null
    var timeToRecalc = recalcInterval

    private fun refreshTarget() {
        target = selector(mob)
    }

    override fun initialize() {
        timeToRecalc = 0
    }

    override fun finalize() {
        timeToRecalc = recalcInterval
    }

    override fun shouldActivate(): Boolean {
        refreshTarget()
        if (target == null) return false
        if (target!!.isDead) return false
        if (target!!.world != mob.world) return false
        if (mob.location.distance(target!!.location) > distance) return false
        return true
    }

    override fun tick() {
        if (timeToRecalc-- != 0) return
        timeToRecalc = recalcInterval

        val currentDistance = mob.location.distance(target!!.location)
        val direction = mob.location.toVector().subtract(target!!.location.toVector()).normalize()
        mob.insentient.moveTo(mob.location.add(direction.multiply(distance - currentDistance)), speed)
    }

    override fun toString(): String {
        return "KeepDistanceGoal(selector=$selector, distance=$distance, speed=$speed)"
    }

    override val flags: Set<Flags> = setOf(Flags.MOVE, Flags.JUMP)



}