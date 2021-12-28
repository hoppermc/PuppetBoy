package dev.helight.hopper.puppetboy.versions.mechanisms

import dev.helight.hopper.puppetboy.PuppetBoy
import dev.helight.hopper.puppetboy.goals.Flags
import dev.helight.hopper.puppetboy.goals.PuppetGoal
import dev.helight.hopper.puppetboy.reflections.*
import dev.helight.hopper.puppetboy.versions.Mechanism
import net.bytebuddy.description.method.MethodDescription
import net.bytebuddy.description.modifier.Visibility
import net.bytebuddy.implementation.FieldAccessor
import net.bytebuddy.implementation.MethodDelegation
import net.bytebuddy.implementation.bind.annotation.FieldValue
import net.bytebuddy.matcher.ElementMatchers.*
import org.bukkit.entity.Entity

class Mechanism1_18() : Mechanism {

    lateinit var internalPuppetWrapperClass: Class<*>

    override val puppetWrapperClass: Class<*>
        get() = internalPuppetWrapperClass

    fun prepare() {
        internalPuppetWrapperClass = createPuppetWrapperClass()
    }

    override fun getHandle(entity: Entity): AliasedEntityHandle =
        Methods.GET_HANDLE.getMethod().invoke(entity)

    override fun getGoalSelector(obj: AliasedEntityInsentient): AliasedGoalSelector =
        Fields.INSENTIENT_SELECTOR.getField().get(obj)

    override fun getWrappedGoalSet(obj: AliasedGoalSelector): MutableSet<*> =
        Fields.SELECTOR_GOALS.getField().get(obj) as MutableSet<*>

    override fun getWrappedGoalGoal(obj: AliasedWrappedGoal): AliasedGoal =
        Fields.WRAPPED_GOAL.getField().get(obj)

    override fun getWrappedGoalPriority(obj: AliasedWrappedGoal): Int =
        Fields.WRAPPED_PRIORITY.getField().get(obj) as Int

    override fun createWrappedGoal(priority: Int, goal: AliasedGoal): AliasedWrappedGoal =
        Classes.GOAL_WRAPPED.getClazz().constructors[0].newInstance(priority, goal)

    override fun wrapPuppetGoal(goal: PuppetGoal): AliasedGoal {
        val clazz = createPuppetWrapperClass()
        val instance = clazz.getDeclaredConstructor().newInstance()
        (instance as PuppetGoalFieldAccessor).setGoal(goal)
        Methods.SET_TYPES.getMethod().invoke(instance, Flags.toEnumSet(goal.flags))
        return instance
    }

    private fun createPuppetWrapperClass(): Class<*> {
        return PuppetBoy.instance.generation.create {
            it.subclass(Classes.GOAL.getClazz())
                .defineField("goal", PuppetGoal::class.java, Visibility.PUBLIC)
                .implement(PuppetGoalFieldAccessor::class.java).intercept(FieldAccessor.ofBeanProperty())

                .method(named<MethodDescription?>("a").and(takesArguments(0))).intercept(MethodDelegation.to(PuppetWrapperClassTarget::class.java))
                .method(named<MethodDescription?>("b").and(takesArguments(0))).intercept(MethodDelegation.to(PuppetWrapperClassTarget::class.java))
                .method(named<MethodDescription?>("c").and(takesArguments(0))).intercept(MethodDelegation.to(PuppetWrapperClassTarget::class.java))
                .method(named<MethodDescription?>("d").and(takesArguments(0))).intercept(MethodDelegation.to(PuppetWrapperClassTarget::class.java))
                .method(named<MethodDescription?>("e").and(takesArguments(0))).intercept(MethodDelegation.to(PuppetWrapperClassTarget::class.java))

                .name("dev.helight.hopper.puppetboy.GeneratedPuppetGoalWrapperImpl")
                .make()
        }
    }

}

interface PuppetGoalFieldAccessor {
    fun getGoal(): PuppetGoal
    fun setGoal(goal: PuppetGoal)
}

object PuppetWrapperClassTarget {

    @JvmStatic
    fun a(@FieldValue("goal") goal: PuppetGoal): Boolean = goal.shouldActivate()

    @JvmStatic
    fun b(@FieldValue("goal") goal: PuppetGoal): Boolean = goal.shouldContinue()

    @JvmStatic
    fun c(@FieldValue("goal") goal: PuppetGoal) = goal.initialize()

    @JvmStatic
    fun d(@FieldValue("goal") goal: PuppetGoal) = goal.finalize()

    @JvmStatic
    fun e(@FieldValue("goal") goal: PuppetGoal) = goal.tick()
}