package dev.helight.hopper.puppetboy

import dev.helight.hopper.puppetboy.wrappers.EntityInsentientWrapper
import dev.helight.hopper.puppetboy.wrappers.GoalSelectorWrapper
import net.minecraft.world.entity.EntityInsentient
import org.bukkit.entity.Entity
import org.bukkit.entity.Mob

object EntityExtension {

    val Entity.handle: Any
        get() = PuppetBoy.mechanism.getHandle(this)

    val Entity.insentient: EntityInsentientWrapper
        get() = EntityInsentientWrapper(handle)

    val Entity.goalSelector: GoalSelectorWrapper
        get() = GoalSelectorWrapper(PuppetBoy.mechanism.getGoalSelector(handle))


}