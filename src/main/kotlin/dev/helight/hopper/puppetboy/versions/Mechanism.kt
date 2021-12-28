package dev.helight.hopper.puppetboy.versions

import dev.helight.hopper.puppetboy.goals.PuppetGoal
import dev.helight.hopper.puppetboy.reflections.*
import org.bukkit.entity.Entity

interface Mechanism {

    fun getHandle(entity: Entity): AliasedEntityHandle
    fun getGoalSelector(obj: AliasedEntityInsentient): AliasedGoalSelector
    fun getWrappedGoalSet(obj: AliasedGoalSelector): MutableSet<*>

    fun getWrappedGoalGoal(obj: AliasedWrappedGoal): AliasedGoal
    fun getWrappedGoalPriority(obj: AliasedWrappedGoal): Int

    fun createWrappedGoal(priority: Int, goal: AliasedGoal): AliasedWrappedGoal

    fun wrapPuppetGoal(goal: PuppetGoal): AliasedGoal

    val puppetWrapperClass: Class<*>

}