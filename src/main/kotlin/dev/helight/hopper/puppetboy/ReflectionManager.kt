package dev.helight.hopper.puppetboy

import dev.helight.hopper.puppetboy.reflections.*
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.entity.Entity


class ReflectionManager {

    fun getHandle(entity: Entity): AliasedEntityHandle = Methods.GET_HANDLE.getMethod().invoke(entity)

    fun getGoalSelector(obj: AliasedEntityInsentient): AliasedGoalSelector = Fields.INSENTIENT_SELECTOR.getField().get(obj)

    fun getWrappedGoalSet(obj: AliasedGoalSelector): MutableSet<*> = Fields.SELECTOR_GOALS.getField().get(obj) as MutableSet<*>

    fun wrapGoal(priority: Int, obj: AliasedGoal): AliasedWrappedGoal = Classes.GOAL_WRAPPED.getClazz().constructors[0].newInstance(priority, obj)

    fun stringifyWrappedGoalSet(set: Set<*>): String = set.mapNotNull {
        if (it == null) return@mapNotNull null
        "[${getPriorityOfWrapped(it)}]: ${getGoalOfWrapped(it).javaClass.name}"
    }.joinToString("\n")

    fun textComponentifyWrappedGoalSet(set: Set<*>): TextComponent = TextComponent().apply {
        text = "Pathfinder Goals:\n"
        extra = set.mapNotNull {
            if (it == null) return@mapNotNull null
            TextComponent().apply {
                text = "${getPriorityOfWrapped(it)}: "
                addExtra(textComponentifyGoal(getGoalOfWrapped(it)))
                addExtra("\n")
            }
        }
    }

    fun textComponentifyGoal(obj: AliasedGoal) = TextComponent().apply {
        addExtra(TextComponent().apply {
            text = shortenName(obj.javaClass.name)
            text += "\n"
            color = ChatColor.WHITE
            hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("Copy class name to Clipboard"))
            clickEvent = ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, obj.javaClass.name)
            addExtra("Fields: ")
        })
        obj.javaClass.declaredFields.forEach { field ->
            field.isAccessible = true
            addExtra(TextComponent().apply {
                text = field.name
                text += " "
                isBold = true
                hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent().apply {
                    addExtra(TextComponent().apply {
                        text = "Type: ${field.type.simpleName}\n"
                    })
                    addExtra(TextComponent().apply {
                        text = "Value: ${field.get(obj)?.toString() ?: "null"}"
                    })
                }))
                clickEvent = ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, field.name)
            })
        }
        addExtra("\nConstructors:\n")
        var i = 0
        obj.javaClass.declaredConstructors.forEach { constructor ->
            constructor.isAccessible = true
            addExtra(TextComponent().apply {
                text = (i++).toString() + " "
                text += "(${
                    constructor.parameters.joinToString(", ") {
                        it.type.simpleName
                    }
                })"
                text += "\n"
            })
        }
    }

    fun getGoalOfWrapped(obj: AliasedWrappedGoal): AliasedGoal = Fields.WRAPPED_GOAL.getField().get(obj)
    fun getPriorityOfWrapped(obj: AliasedWrappedGoal): Int = Fields.WRAPPED_PRIORITY.getField().get(obj) as Int

    private fun shortenName(name: String): String {
        val spliced = name.split(".")
        val packageName = spliced.take(2).joinToString(".")
        val className = spliced.last()
        return "$packageName.$className"
    }
}