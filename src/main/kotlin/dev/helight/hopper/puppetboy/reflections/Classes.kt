package dev.helight.hopper.puppetboy.reflections

import dev.helight.hopper.puppetboy.PuppetBoy

enum class Classes {
    CRAFT_ENTITY,
    ENTITY_INSENTIENT,
    GOAL_SELECTOR,
    GOAL,
    GOAL_WRAPPED,

    ENTITY_HUMAN,

    CONTROLLER_LOOK,
    CONTROLLER_JUMP,
    NAVIGATION_ABSTRACT,
    PATH_ENTITY,

    VANILLA_FLOAT,
    VANILLA_RANDOM_LOOKAROUND,
    VANILLA_STROLL,
    VANILLA_STROLL_LAND,
    VANILLA_RANDOM_SWIM,
    VANILLA_AVOID_TARGET,
    VANILLA_LOOK_PLAYER
    ;

    private val cache: MutableMap<Classes, Class<*>> = mutableMapOf()

    fun getClazz(): Class<*> = cache.getOrPut(this) {
        Class.forName(PuppetBoy.instance.version.resolveClass(this))
    }

    fun instantiate(cIndex: Int, vararg args: Any): Any {
        val constructor = getClazz().constructors[cIndex]
        val remappedArgs = arrayOfNulls<Any?>(args.size)
        constructor.parameterTypes.forEachIndexed { index, clazz ->
            val casted = clazz.cast(args[index])
            println(clazz.name)
            remappedArgs[index] = casted
            println("=> ${remappedArgs[index]!!.javaClass.name}")
            println(clazz.isAssignableFrom(remappedArgs[index]!!.javaClass))
        }
        return constructor.newInstance(*remappedArgs)
    }
}