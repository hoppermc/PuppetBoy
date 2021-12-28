package dev.helight.hopper.puppetboy.reflections

import dev.helight.hopper.puppetboy.PuppetBoy
import java.lang.reflect.Field

enum class Fields(
    val clazz: Classes
) {
    INSENTIENT_SELECTOR(Classes.ENTITY_INSENTIENT),
    SELECTOR_GOALS(Classes.GOAL_SELECTOR),
    WRAPPED_GOAL(Classes.GOAL_WRAPPED),
    WRAPPED_PRIORITY(Classes.GOAL_WRAPPED)
    ;

    private val cache: MutableMap<Fields, Field> = mutableMapOf()

    fun getField(): Field = cache.getOrPut(this) {
        clazz.getClazz().getDeclaredField(PuppetBoy.instance.version.resolveField(this)).apply { isAccessible = true }
    }
}