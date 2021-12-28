package dev.helight.hopper.puppetboy.goals

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob

interface EntitySelector {
    fun select(mob: Mob): LivingEntity? = null
    operator fun invoke(mob: Mob) = select(mob)

    data class ConstantEntity(
        val entity: LivingEntity
    ) : EntitySelector {
        override fun select(mob: Mob): LivingEntity = entity
    }

    data class MutableEntity(
        var entity: LivingEntity?
    ) : EntitySelector {
        override fun select(mob: Mob): LivingEntity? = entity
    }

    class ClassBased<E: LivingEntity>(
        val clazz: Class<E>,
        val radius: Double = 25.0
    ) : EntitySelector {
        override fun select(mob: Mob): LivingEntity? = mob.getNearbyEntities(radius, radius, radius)
            .filterIsInstance(clazz)
            .minByOrNull { it.location.distance(mob.location) }
    }

    class LambdaBased(
        val block: Mob.() -> LivingEntity?
    ): EntitySelector {
        override fun select(mob: Mob): LivingEntity? = block(mob)
    }
}