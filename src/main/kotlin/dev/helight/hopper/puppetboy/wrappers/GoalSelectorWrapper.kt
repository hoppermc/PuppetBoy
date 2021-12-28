package dev.helight.hopper.puppetboy.wrappers

import dev.helight.hopper.puppetboy.DebugUtils
import dev.helight.hopper.puppetboy.PuppetBoy
import dev.helight.hopper.puppetboy.goals.PuppetGoal
import dev.helight.hopper.puppetboy.reflections.AliasedGoal
import dev.helight.hopper.puppetboy.reflections.AliasedGoalSelector
import dev.helight.hopper.puppetboy.reflections.SetReflectionUtils
import net.md_5.bungee.api.chat.TextComponent

@JvmInline
value class GoalSelectorWrapper(
    val obj: AliasedGoalSelector
) {

    val debugComponent: TextComponent
        get() = DebugUtils.textComponentifyWrappedGoalSet(goals)

    var goals: Collection<PrioritizedGoalWrapper>
        get() = PuppetBoy.mechanism.getWrappedGoalSet(obj).map { PrioritizedGoalWrapper(it!!) }
        set(value) = SetReflectionUtils.replace(PuppetBoy.mechanism.getWrappedGoalSet(obj), value)

    fun clear() {
        SetReflectionUtils.clear(PuppetBoy.mechanism.getWrappedGoalSet(obj))
    }

    fun add(priority: Int, goal: AliasedGoal) {
        SetReflectionUtils.add(PuppetBoy.mechanism.getWrappedGoalSet(obj), PuppetBoy.mechanism.createWrappedGoal(priority, goal))
    }

    fun add(priority: Int, goal: PuppetGoal) {
        SetReflectionUtils.add(PuppetBoy.mechanism.getWrappedGoalSet(obj), PuppetBoy.mechanism.createWrappedGoal(priority, PuppetBoy.mechanism.wrapPuppetGoal(goal)))
    }


    fun add(wrapper: PrioritizedGoalWrapper) {
        SetReflectionUtils.add(PuppetBoy.mechanism.getWrappedGoalSet(obj), wrapper.obj)
    }

}