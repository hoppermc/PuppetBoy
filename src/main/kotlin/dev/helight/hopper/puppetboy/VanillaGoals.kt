package dev.helight.hopper.puppetboy

import dev.helight.hopper.puppetboy.reflections.AliasedEntityCreature
import dev.helight.hopper.puppetboy.reflections.AliasedEntityInsentient
import dev.helight.hopper.puppetboy.reflections.AliasedGoal
import dev.helight.hopper.puppetboy.reflections.Classes

object VanillaGoals {

    fun float(entityCreature: AliasedEntityCreature): AliasedGoal
        = Classes.VANILLA_FLOAT.instantiate(0, entityCreature)

    fun randomLookAround(entityCreature: AliasedEntityCreature): AliasedGoal
        = Classes.VANILLA_RANDOM_LOOKAROUND.instantiate(0, entityCreature)

    fun stroll(entityCreature: AliasedEntityInsentient, speed: Double = .8, interval: Int = 120): AliasedGoal
            = Classes.VANILLA_STROLL.getClazz().constructors.first { it.parameterCount == 3 }.newInstance(entityCreature, speed, interval)

    fun strollLand(entityCreature: AliasedEntityInsentient, speed: Double = .8, probability: Float = .001f): AliasedGoal
        = Classes.VANILLA_STROLL_LAND.getClazz().constructors.first { it.parameterCount == 3 }.newInstance(entityCreature, speed, probability)

    fun randomSwim(entityCreature: AliasedEntityCreature, speed: Double = .8, interval: Int = 40): AliasedGoal
        = Classes.VANILLA_RANDOM_SWIM.getClazz().constructors.first { it.parameterCount == 3 }.newInstance(entityCreature, speed, interval)

    fun avoidTarget(entityCreature: AliasedEntityCreature, clazz: Class<*>, distance: Float = 8f, fleeSpeed: Double = 1.4, strollAwaySpeed: Double = 1.0): AliasedGoal
        = Classes.VANILLA_AVOID_TARGET.getClazz().constructors.first { it.parameterCount == 5 }.newInstance(entityCreature, clazz, distance, strollAwaySpeed, fleeSpeed)

    fun lookAtPlayer(entityCreature: AliasedEntityCreature, clazz: Class<*> = Classes.ENTITY_HUMAN.getClazz(), distance: Float = 8f, probability: Float = .02f, horizontalOnly: Boolean = false): AliasedGoal
        = Classes.VANILLA_LOOK_PLAYER.getClazz().constructors.first { it.parameterCount == 5 }.newInstance(entityCreature, clazz, distance, probability, horizontalOnly)
}