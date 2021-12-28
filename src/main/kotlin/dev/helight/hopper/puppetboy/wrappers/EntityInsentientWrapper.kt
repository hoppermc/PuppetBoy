package dev.helight.hopper.puppetboy.wrappers

import dev.helight.hopper.puppetboy.PuppetBoy
import dev.helight.hopper.puppetboy.reflections.AliasedEntityInsentient
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

@Suppress("MemberVisibilityCanBePrivate")
@JvmInline
value class EntityInsentientWrapper(
    val obj: AliasedEntityInsentient
) {

    fun lookAt(x: Double, y: Double, z: Double) {
        PuppetBoy.controllers.lookAt(obj, x, y, z)
    }

    fun lookAt(location: Location) {
       lookAt(location.x, location.y, location.z) //Todo: Check world
    }

    fun lookAt(vector: Vector) {
        lookAt(vector.x, vector.y, vector.z)
    }

    fun lookAt(entity: Entity) {
        lookAt(entity.location)
    }

    fun moveTo(x: Double, y: Double, z: Double, speed: Double = 1.0, tolerance: Int = 1): Boolean {
        val path = PuppetBoy.controllers.createPath(obj, x, y, z, tolerance) ?: return false
        PuppetBoy.controllers.moveToPath(obj, path, speed)
        return true
    }

    fun moveTo(location: Location, speed: Double = 1.0, tolerance: Int = 1): Boolean {
        return moveTo(location.x, location.y, location.z, speed, tolerance) //TODO: Check world
    }

    fun moveTo(vector: Vector, speed: Double = 1.0, tolerance: Int = 1): Boolean {
        return moveTo(vector.x, vector.y, vector.z, speed, tolerance)
    }

    fun moveTo(entity: Entity, speed: Double = 1.0, tolerance: Int = 1): Boolean {
        return moveTo(entity.location, speed, tolerance)
    }

}