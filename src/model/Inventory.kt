package model
import model.Character
import model.Enemy

// inventory class with functions to add, use, or remove item
open class Inventory(private val items: MutableMap<Item, Int> = mutableMapOf()) {

    fun addItem(item: Item, quantity: Int = 1) {
        val currentCount = items.getOrDefault(item, 0)
        items[item] = currentCount + quantity
        println("${item.name} x$quantity added to inventory! (Total: ${items[item]})")
    }

    fun removeItem(item: Item, quantity: Int = 1): Boolean {
        val currentCount = items[item]
        return if (currentCount != null) {
            if (currentCount > quantity) {
                items[item] = currentCount - quantity
                println("${item.name} x$quantity removed. (${items[item]} left)")
            } else {
                items.remove(item)
                println("${item.name} removed from inventory.")
            }
            true
        } else {
            println("${item.name} not found in inventory.")
            false
        }
    }

    open fun useItem(character: Character, item: Item, target: Enemy? = null) {
        if (items.containsKey(item)) {
            println("Using ${item.name}...")
            when (item.name.lowercase()) {
                "potion" -> {
                    println("${item.name} restores ${item.heal} HP to ${character.name}.")
                    character.HP += item.heal
                    removeItem(item)
                }
                "bomb" ->  if (target != null) {
                    println("The party throws a bomb at ${target.name}!")
                    target.takeDamage(item.damage)
                    println("${target.name} takes ${item.damage} damage!")
                    removeItem(item)
                } else {
                    println("There's no target to use the bomb on!")
                }
            }
        } else {
            println("${item.name} is not found in the bag.")
        }
    }

    fun showInventory() {
        if (items.isEmpty()) {
            println("Inventory is empty.")
        } else {
            println("Party's inventory:")
            var index = 1
            for ((item, count) in items) {
                println("${index++}. ${item.name} x$count - ${item.type} (${item.description})")
            }
        }
    }
}

//two specific items to represent bombs and potions

val bomb = Item(
    name = "Bomb",
    type = "Throwable",
    damage = 12,
    heal = 0,
    description = "Explosive. Deals 12 on successful hit."
)

val potion = Item(
    name = "Potion",
    type = "Consumable",
    damage = 0,
    heal = 10,
    description = "Basic healing potion, restores 10 HP."
)
