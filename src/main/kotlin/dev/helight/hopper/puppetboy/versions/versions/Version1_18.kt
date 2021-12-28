package dev.helight.hopper.puppetboy.versions.versions

import com.comphenix.protocol.utility.MinecraftReflection
import dev.helight.hopper.puppetboy.reflections.Classes
import dev.helight.hopper.puppetboy.reflections.Fields
import dev.helight.hopper.puppetboy.reflections.Methods
import dev.helight.hopper.puppetboy.versions.Controllers
import dev.helight.hopper.puppetboy.versions.Mechanism
import dev.helight.hopper.puppetboy.versions.Version
import dev.helight.hopper.puppetboy.versions.controllers.Controllers1_18
import dev.helight.hopper.puppetboy.versions.mechanisms.Mechanism1_18
import net.minecraft.world.entity.ai.navigation.NavigationAbstract
import java.lang.reflect.Method

class Version1_18 : Version {

    override val mechanism: Mechanism1_18 = Mechanism1_18()
    override val controllers: Controllers1_18 = Controllers1_18()

    override fun prepare() {
       mechanism.prepare()
    }

    override fun resolveClass(clazz: Classes): String = when (clazz) {
        Classes.CRAFT_ENTITY -> "${MinecraftReflection.getCraftBukkitPackage()}.entity.CraftEntity"
        Classes.ENTITY_INSENTIENT -> "net.minecraft.world.entity.EntityInsentient"
        Classes.GOAL_SELECTOR -> "net.minecraft.world.entity.ai.goal.PathfinderGoalSelector"
        Classes.GOAL -> "net.minecraft.world.entity.ai.goal.PathfinderGoal"
        Classes.GOAL_WRAPPED -> "net.minecraft.world.entity.ai.goal.PathfinderGoalWrapped"
        Classes.ENTITY_HUMAN -> "net.minecraft.world.entity.player.EntityHuman"

        Classes.CONTROLLER_LOOK -> "net.minecraft.world.entity.ai.control.ControllerLook"
        Classes.CONTROLLER_JUMP -> "net.minecraft.world.entity.ai.control.ControllerJump"
        Classes.NAVIGATION_ABSTRACT -> "net.minecraft.world.entity.ai.navigation.NavigationAbstract"
        Classes.PATH_ENTITY -> "net.minecraft.world.level.pathfinder.PathEntity"

        Classes.VANILLA_FLOAT -> "net.minecraft.world.entity.ai.goal.PathfinderGoalFloat"
        Classes.VANILLA_RANDOM_LOOKAROUND -> "net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround"
        Classes.VANILLA_STROLL -> "net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll"
        Classes.VANILLA_STROLL_LAND -> "net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand"
        Classes.VANILLA_RANDOM_SWIM -> "net.minecraft.world.entity.ai.goal.PathfinderGoalRandomSwim"
        Classes.VANILLA_AVOID_TARGET -> "net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget"
        Classes.VANILLA_LOOK_PLAYER -> "net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer"
    }

    override fun resolveMethod(method: Methods): String = when (method) {
        Methods.GET_HANDLE -> "getHandle"
        Methods.SET_TYPES -> "a"
        Methods.GET_CONTROLLER_LOOK -> "z"
        Methods.GET_CONTROLLER_JUMP -> "C"
        Methods.GET_NAVIGATION_ABSTRACT -> "D"

        Methods.LOOK_AT_DOUBLES -> "a"

        Methods.SET_NAVIGATION_SPEED -> "a"
        Methods.CREATE_PATH -> "a"
        Methods.MOVE_TO_PATH -> "a"
    }

    override fun filterMethod(methods: Methods, method: Method): Boolean  = when (methods) {
        Methods.GET_HANDLE -> true
        Methods.SET_TYPES -> method.parameterCount == 1 && method.parameterTypes[0].name.contains("EnumSet")
        Methods.GET_CONTROLLER_LOOK -> method.parameterCount == 0
        Methods.GET_CONTROLLER_JUMP -> method.parameterCount == 0
        Methods.GET_NAVIGATION_ABSTRACT -> method.parameterCount == 0
        Methods.LOOK_AT_DOUBLES -> method.parameterCount == 3 && method.parameterTypes[0].name.contains("double")
        Methods.SET_NAVIGATION_SPEED -> method.parameterCount == 1 && method.parameterTypes[0].name.contains("double")
        Methods.CREATE_PATH -> method.parameterCount == 4 && method.parameterTypes[0].name.contains("double") &&  method.parameterTypes.last().name == "int"
        Methods.MOVE_TO_PATH -> method.parameterCount == 2 && method.parameterTypes[0].name.contains("PathEntity")
    }

    override fun resolveField(field: Fields): String = when (field) {
        Fields.INSENTIENT_SELECTOR -> "bR"
        Fields.SELECTOR_GOALS -> "d"
        Fields.WRAPPED_GOAL -> "a"
        Fields.WRAPPED_PRIORITY -> "b"
    }

}