package dev.helight.hopper.puppetboy.goals.impl

import dev.helight.hopper.puppetboy.EntityExtension.insentient
import dev.helight.hopper.puppetboy.goals.Flags
import dev.helight.hopper.puppetboy.goals.LocationSelector
import dev.helight.hopper.puppetboy.goals.PuppetGoal
import org.bukkit.Location
import org.bukkit.entity.Mob

class GoToGoal(
    val mob: Mob,
    var selector: LocationSelector,
    val speed: Double = 1.0,
    val recalcInterval: Int = 5
) : PuppetGoal {

    var target: Location? = null
    var timeToRecalc = recalcInterval

    private fun refreshTarget() {
        target = selector(mob)
    }

    override fun shouldActivate(): Boolean {
        refreshTarget()
        if (target == null) return false
        if (target!!.world != mob.world) return false
        return true
    }

    override fun initialize() {
        timeToRecalc = 0
    }

    override fun finalize() {
        timeToRecalc = recalcInterval
    }

    override fun tick() {
        if (timeToRecalc-- != 0) return
        timeToRecalc = recalcInterval
        val insentient = mob.insentient
        insentient.moveTo(target!!, speed)
    }

    override fun toString(): String {
        return "GoToGoal(selector=$selector, speed=$speed)"
    }

    override val flags: Set<Flags> = setOf(Flags.MOVE, Flags.JUMP)
}