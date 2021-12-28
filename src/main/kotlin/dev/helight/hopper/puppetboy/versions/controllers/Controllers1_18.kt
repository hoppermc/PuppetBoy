package dev.helight.hopper.puppetboy.versions.controllers

import dev.helight.hopper.puppetboy.reflections.AliasedEntityInsentient
import dev.helight.hopper.puppetboy.reflections.AliasedPathEntity
import dev.helight.hopper.puppetboy.reflections.Methods
import dev.helight.hopper.puppetboy.versions.Controllers

class Controllers1_18 : Controllers {

    override fun lookAt(obj: AliasedEntityInsentient, x: Double, y: Double, z: Double) {
        val controller = Methods.GET_CONTROLLER_LOOK.getMethod().invoke(obj)
        Methods.LOOK_AT_DOUBLES.getMethod().invoke(controller, x, y, z)
    }

    override fun setSpeed(obj: AliasedEntityInsentient, speed: Double) {
        val navigation = Methods.GET_NAVIGATION_ABSTRACT.getMethod().invoke(obj)
        Methods.SET_NAVIGATION_SPEED.getMethod().invoke(navigation, speed)
    }

    override fun createPath(obj: AliasedEntityInsentient, x: Double, y: Double, z: Double, tolerance: Int): AliasedPathEntity? {
        val navigation = Methods.GET_NAVIGATION_ABSTRACT.getMethod().invoke(obj)
        val method = Methods.CREATE_PATH.getMethod()
        return method.invoke(navigation, x, y, z, tolerance)
    }

    override fun moveToPath(obj: AliasedEntityInsentient, path: AliasedPathEntity, speed: Double) {
        val navigation = Methods.GET_NAVIGATION_ABSTRACT.getMethod().invoke(obj)
        val moveMethod = Methods.MOVE_TO_PATH.getMethod()
        moveMethod.invoke(navigation, path, speed)
    }

}