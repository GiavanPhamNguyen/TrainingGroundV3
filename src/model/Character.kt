package model

// character class and functions that the four party members will inherit from
open class Character(
    val name: String,
    var HP: Int,
    val maxHP: Int,
    var strength: Int,
    var intelligence: Int,
    var agility: Int,
    var gold : Int
) {
    open fun attack(): Int{
        var damage = (2..12).random()
        damage += strength
        return damage
    }
    fun takeDamage(amount: Int) {
        HP = (HP - amount).coerceAtLeast(0)
    }

    fun isAlive(): Boolean {
        return HP > 0
    }
}

//four specific character classes with unique stats, such as
// relative strength that will factor in the attacking function
// intelligence will factor in to the character's own roll for hit chance
//agility will affect enemy's roll for hit chance for implementation of character's dodging
class Warrior() : Character(
    "Warrior",40,40,4,4,2, 0)

class Mage() : Character(
 "Mage",20,20,2,7,2,0)

class Berserker() : Character(
"Berserker",60,60,7,3,1,0)

class Assassin() : Character(
"Assassin",30,30,3,5,3,0)



