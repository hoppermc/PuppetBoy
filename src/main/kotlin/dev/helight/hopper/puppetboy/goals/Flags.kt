package dev.helight.hopper.puppetboy.goals

import dev.helight.hopper.puppetboy.reflections.Classes
import java.util.*

enum class Flags {

    /**
     * Indicates that a goal is going to use pathfinder movement.
     *
     * Mostly used together with [JUMP], because it's relatively useless
     * otherwise, since the absence of [JUMP] makes the mob target only
     * the same or a lower height level, making it predetermined to just
     * fall down the next cliff.
     */
    MOVE,

    /**
     * Indicates that a goal is influencing only or also the look direction.
     *
     * If no [LOOK] goal is defined, the entity will just look at the location it
     * has last been instructed to move to.
     */
    LOOK,

    /**
     * Indicates that goal is makes use of the entity's ability to jump.
     */
    JUMP,

    /**
     * Never used in vanilla, also shouldn't be used by you except you have a good reason to.
     */
    @Deprecated(message = "Never used in vanilla, also shouldn't be used by you")
    TARGET; // This is never used as far as I know, why does this even exist?

    // TODO: Cache this and move it to versions

    fun getEnumConstant(): Any {
        val constants = Classes.GOAL.getClazz().declaredClasses[0].enumConstants
        return when(constants.size) {
            4 -> constants[this.ordinal] // Vanilla and Spigot enum
            5 -> constants[this.ordinal + 1] // Paper injects its own enum field at pos 0. Why the f**k don't they just append it to the end? EDIT: They could have just used the TARGET Goal :D
            else -> error("Invalid amount of enum constants in PathfinderGoal.Type")
        }
    }

    companion object {
        fun toEnumSet(collection: Collection<Flags>): Any {
            return EnumSet::class.java.methods.first {
                it.name.contains("copyOf")
            }.invoke(null, collection.map {
                it.getEnumConstant()
            }.toList())
        }
    }
}