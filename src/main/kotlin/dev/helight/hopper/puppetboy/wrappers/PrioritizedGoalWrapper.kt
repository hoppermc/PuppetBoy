package dev.helight.hopper.puppetboy.wrappers

import dev.helight.hopper.puppetboy.DebugUtils
import dev.helight.hopper.puppetboy.PuppetBoy
import dev.helight.hopper.puppetboy.reflections.AliasedGoal
import dev.helight.hopper.puppetboy.reflections.AliasedWrappedGoal
import net.md_5.bungee.api.chat.TextComponent

@JvmInline
value class PrioritizedGoalWrapper(
    val obj: AliasedWrappedGoal
) {

    val priority: Int
        get() = PuppetBoy.instance.version.mechanism.getWrappedGoalPriority(obj)

    val goal: AliasedGoal
        get() = PuppetBoy.instance.version.mechanism.getWrappedGoalGoal(obj)

    val debugGoalComponent: TextComponent
        get() = DebugUtils.textComponentifyGoal(goal)

    companion object {
        fun create(priority: Int, goal: AliasedGoal) = PrioritizedGoalWrapper(PuppetBoy.mechanism.createWrappedGoal(priority, goal))
    }
}