package dev.helight.hopper.puppetboy.reflections

import dev.helight.hopper.puppetboy.PuppetBoy
import java.lang.reflect.Method

enum class Methods(
    val clazz: Classes
) {
    GET_HANDLE(Classes.CRAFT_ENTITY),
    SET_TYPES(Classes.GOAL),

    GET_CONTROLLER_LOOK(Classes.ENTITY_INSENTIENT),
    GET_CONTROLLER_JUMP(Classes.ENTITY_INSENTIENT),
    GET_NAVIGATION_ABSTRACT(Classes.ENTITY_INSENTIENT),

    LOOK_AT_DOUBLES(Classes.CONTROLLER_LOOK),

    SET_NAVIGATION_SPEED(Classes.NAVIGATION_ABSTRACT),
    CREATE_PATH(Classes.NAVIGATION_ABSTRACT),
    MOVE_TO_PATH(Classes.NAVIGATION_ABSTRACT)
    ;

    private val cache: MutableMap<Methods, Method> = mutableMapOf()

    fun getMethod(): Method = cache.getOrPut(this) {
        clazz.getClazz().declaredMethods.filter {
            it.name == PuppetBoy.instance.version.resolveMethod(this)
        }.first { PuppetBoy.instance.version.filterMethod(this, it) }.apply { isAccessible = true }
    }
}