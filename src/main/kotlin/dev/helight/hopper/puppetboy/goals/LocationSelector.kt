package dev.helight.hopper.puppetboy.goals

import org.bukkit.Location
import org.bukkit.entity.Mob

interface LocationSelector {

    fun select(mob: Mob): Location? = null
    operator fun invoke(mob: Mob) = select(mob)

    data class ConstantLocation(
        val location: Location
    ) : LocationSelector {
        override fun select(mob: Mob): Location = location
    }

    data class MutableLocation(
        var location: Location?
    ) : LocationSelector {
        override fun select(mob: Mob): Location? = location
    }

    class LambdaBased(
        val block: Mob.() -> Location?
    ): LocationSelector {
        override fun select(mob: Mob): Location? = block(mob)
    }
}