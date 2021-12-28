package dev.helight.hopper.puppetboy.goals

interface PuppetGoal {

    val flags: Set<Flags>
    fun shouldActivate(): Boolean
    fun shouldContinue(): Boolean = shouldActivate()
    fun initialize() {}
    fun tick() {}
    fun finalize() {}

}