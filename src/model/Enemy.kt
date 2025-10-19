package model

// general enemy class and fucntions for the enemies used in this program to inherit from
open class Enemy(
    var name: String,
    var lvl: Int,
    var HP: Int,
    var maxHP: Int,
    var damage: Int,
    ) {

    open fun attack(): Int{
        return damage
    }

    open fun takeDamage(amount: Int) {
        HP = (HP - amount).coerceAtLeast(0)
    }

    fun isAlive(): Boolean {
        return HP > 0
    }
}

// wolf will be able to do + 2 damage if it is its first turn
class Wolf : Enemy(
   "Wolf",
    1,
    18,
    18,
    4
){
    private var hasPounced = false
    override fun attack(): Int {
        return if(!hasPounced) {
            hasPounced = true
            damage + 2
        } else damage
    }

}


//Goblin will be able to dodge with a 10% success rate everytime takeDamage is to be implented
class Goblin : Enemy(
    "Goblin",
    2,
    20,
    20,
    3
){
    override fun takeDamage(amount: Int){
        val dodgeChance = (1..10).random()
        if (dodgeChance == 1){
            println("Goblin successfully dodged the attack!")
        } else {
            HP = (HP - amount).coerceAtLeast(0)
        }
    }
}

//Orc will minus 1 point of damage everytime takeDamage is implemented due to armor
class Orc : Enemy(
    "Orc",
    3,
    30,
    30,
    5
){
    override fun takeDamage(amount: Int){
        val reduced = (amount - 1).coerceAtLeast(1)
        super.takeDamage(reduced)
    }
}
class Wyvern : Enemy(
    "Wyvern",
    4,
    50,
    50,
    10
)
class Dragon : Enemy(
    "Dragon",
    5,
    100,
    100,
    20
)



