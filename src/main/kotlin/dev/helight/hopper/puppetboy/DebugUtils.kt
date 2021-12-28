package dev.helight.hopper.puppetboy

import dev.helight.hopper.puppetboy.reflections.AliasedGoal
import dev.helight.hopper.puppetboy.wrappers.PrioritizedGoalWrapper
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text

object DebugUtils {

    fun textComponentifyWrappedGoalSet(set: Collection<PrioritizedGoalWrapper>): TextComponent = TextComponent().apply {
        text = "Pathfinder Goals:\n"
        extra = set.map {
            TextComponent().apply {
                addExtra(TextComponent().apply {
                    text = "${it.priority}"
                    color = ChatColor.YELLOW
                })
                addExtra(": ")
                addExtra(textComponentifyGoal(it.goal))
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

    fun printDeclaredMethods(clazz: Class<*>) {
        println("Methods of ${clazz.name}:")
        clazz.declaredMethods.forEach {
            println(it.name + "(${it.parameters.joinToString { parameter -> parameter.name + ": " + parameter.type.simpleName }})" +": " + it.returnType.simpleName)
        }
    }

    private fun shortenName(name: String): String {
        val spliced = name.split(".")
        val packageName = spliced.take(2).joinToString(".")
        val className = spliced.last()
        return "$packageName.$className"
    }

}