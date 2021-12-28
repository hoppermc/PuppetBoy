package dev.helight.hopper.puppetboy.versions

import dev.helight.hopper.puppetboy.reflections.AliasedEntityInsentient
import dev.helight.hopper.puppetboy.reflections.AliasedPathEntity

interface Controllers {

    fun lookAt(obj: AliasedEntityInsentient, x: Double, y: Double, z: Double)
    fun setSpeed(obj: AliasedEntityInsentient, speed: Double)
    fun createPath(obj: AliasedEntityInsentient, x: Double, y: Double, z: Double, tolerance: Int): AliasedPathEntity?
    fun moveToPath(obj: AliasedEntityInsentient, path: AliasedPathEntity, speed: Double)

}