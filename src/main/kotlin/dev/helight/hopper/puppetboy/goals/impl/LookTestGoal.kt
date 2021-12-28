package dev.helight.hopper.puppetboy.goals.impl

import dev.helight.hopper.puppetboy.EntityExtension.insentient
import dev.helight.hopper.puppetboy.goals.Flags
import dev.helight.hopper.puppetboy.goals.PuppetGoal
import org.bukkit.entity.Mob

class LookTestGoal(val mob: Mob) : PuppetGoal {

    override val flags: Set<Flags> = setOf(Flags.LOOK)

    override fun shouldActivate(): Boolean = true

    var i = 40
    var switch = false

    override fun tick() {
        i--
        if (i == 0) {
            switch = !switch
            i = 40
            println("Switchting sides")
        }
        mob.insentient.lookAt(mob.location.x + when(switch) {
            true -> 2
            false -> -2
        }, mob.location.y + mob.eyeHeight, mob.location.z)
    }
}