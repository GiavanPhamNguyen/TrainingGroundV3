import model.*
import service.*

fun main() {
    var quit = false // initialize a quit to handle quit option from user
    var turn = 0
    val party = mutableListOf<Character>( //create the party
        Warrior(),
        Mage(),
        Berserker(),
        Assassin()
    )
    val enemies = mutableListOf( //create the enemies to be used in the game
        Wolf(),
        Goblin(),
        Orc(),
        Wyvern(),
        Dragon()
    )
    //creating an inventory object and a battle service object
    val inventory = Inventory()
    val battleService = BattleService()

    //Welcome the user and explain the game to the user
    println("Welcome to Training Ground V3")
    println("Please enter the player's name: ")
    val user = readln()
    println()
    println("Thank you for playing, $user!")
    println("In this version, you will be facing five creatures of varying health and strength.")
    println("You are in charge of a party of four, a warrior, a mage, a berserker, and an assassin.")
    println("Each member have different levels of strength, intelligence, and agility that will affect the outcome of every turn.")
    println()
    println("This is your party:")
    for (i in party.indices) {
        println("Slot ${i + 1}: ${party[i].name} - HP: ${party[i].HP}/${party[i].maxHP}, " +
                "Strength: ${party[i].strength}, Intelligence: ${party[i].intelligence}, Agility: ${party[i].agility}")
    }
    println()
    println("In this version, you will also have access to two unique types of items, potions and bombs.")
    println("Using the potion will allow you to heal a member of your party at any time with 10 HP.")
    println("You may also choose to throw a bomb at any enemy creature for 6 points of damage.")
    inventory.addItem(potion, 3)
    inventory.addItem(bomb, 3)
    inventory.showInventory()

    println("Let the training begin: ")

    //creating a result object to update the turn results after each turn.
    val result = TurnResult(
        attacking = "",
        attacked = "",
        damage = 0,
        defeated = false,
        status = Status.ONGOING
    )
    var enemyIndex = 0 // index to update the enemy party each time an enemy is defeated.
   while(!quit && enemies.any {it.isAlive()} && party.any {it.isAlive()}) {
       println("Your party is confronted by the ${enemies[enemyIndex].name}!")
        //reset the status to ONGOING to let the program know the fight is ongoing
       result.status = Status.ONGOING

       //present the user with the options that are available for the duration of the enemy being alive
       while (result.status == Status.ONGOING && enemies[enemyIndex].isAlive()) {
           val validOptions = listOf("fight", "inventory", "camp", "stats", "run")
           println("What would you like to do?: ")
           println("Fight")
           println("Inventory")
           println("Camp")
           println("Stats")
           println("Run")
           var input = readln().trim().lowercase()
           while (input !in validOptions) {
               println("Invalid choice, please try again.")
               input = readln().trim().lowercase()
           }
           when (input) {
               "fight" -> {
                   var partyIndex = -1
                   while (partyIndex == -1) {
                       println("Which party member would you like to use?")
                       for (i in party.indices) {
                           println("${i + 1}. ${party[i].name} - HP: ${party[i].HP} / ${party[i].maxHP}")
                       }
                       val input = readln().trim().lowercase()

                       partyIndex = when (input) {
                           "warrior" -> 0
                           "mage" -> 1
                           "berserker" -> 2
                           "assassin" -> 3
                           else -> {
                               println("Invalid party member. Try again.")
                               -1
                           }
                       }

                       if (partyIndex != -1 && !party[partyIndex].isAlive()) {
                           println("${party[partyIndex].name} has been defeated. Pick another.")
                           partyIndex = -1
                       }
                   }
                   //player attacks
                   val playerAttack = battleService.playerAttack(party[partyIndex], enemies[enemyIndex])
                   println("${playerAttack.attacking} dealt ${playerAttack.damage} damage to ${playerAttack.attacked}'s HP!")

                   if (!enemies[enemyIndex].isAlive()) {
                       println("${enemies[enemyIndex].name} has been defeated!")
                       result.status = Status.VICTORY
                       break
                   } else {
                       //enemy attacks
                       val enemyAttack = battleService.enemyAttack(enemies[enemyIndex], party[partyIndex])
                       println("${enemyAttack.attacking} dealt ${enemyAttack.damage} damage to ${enemyAttack.attacked}'s HP!")

                       if (!party[partyIndex].isAlive()) {
                           println("${party[partyIndex].name} has been downed!")
                           // Only mark defeat if all members are dead
                           if (party.none { it.isAlive() }) {
                               println("All party members have been defeated!")
                               result.status = Status.DEFEAT
                               break
                           }
                       }
                   }
               }
               //inventory option for user, choosing either potion or bomb will present subsequent options accordingly
           "inventory" ->{
               inventory.showInventory()
               println("Which item would you like to use?")
               input = readln().trim().lowercase()
               while (input != "potion" && input != "bomb") {
                   println("Input invalid. Please try again.")
                   input = readln().trim().lowercase()
               }
               when (input) {
                   "potion" -> {
                       for (i in party.indices) {
                           println("${i + 1}. ${party[i].name} - HP: ${party[i].HP} / ${party[i].maxHP}")
                       }
                       var partyIndex = -1
                       while (partyIndex == -1) {
                           println("Which party member would you like to use this potion on?")
                           input = readln().trim().lowercase()
                           partyIndex = when (input) {
                               "warrior" -> 0
                               "mage" -> 1
                               "berserker" -> 2
                               "assassin" -> 3
                               else -> {
                                   println("Invalid party member. Try again.")
                                   -1
                               }
                           }
                       }
                       inventory.useItem(party[partyIndex], potion)
                   }
                   "bomb" -> {
                       for (i in enemies.indices) {
                           println("${i + 1}. ${enemies[i].name} - HP: ${enemies[i].HP} / ${enemies[i].maxHP}")
                       }
                       println("Which enemy would you like to throw this bomb at?")
                       var enemyIndex = -1
                       while (enemyIndex == -1) {
                           input = readln().trim().lowercase()
                           enemyIndex = when (input) {
                               "wolf" -> 0
                               "goblin" -> 1
                               "orc" -> 2
                               "wyvern" -> 3
                               "dragon" -> 4
                               else -> {
                                   println("Invalid enemy chosen. Try again.")
                                   -1
                               }
                           }
                       }
                       var partyIndex = -1
                       while (partyIndex == -1) {
                           println("Which party member would you like to throw this bomb?")
                           input = readln().trim().lowercase()
                           partyIndex = when (input) {
                               "warrior" -> 0
                               "mage" -> 1
                               "berserker" -> 2
                               "assassin" -> 3
                               else -> {
                                   println("Invalid party member. Try again.")
                                   -1
                               }
                           }
                       }
                       inventory.useItem(party[partyIndex], bomb, enemies[enemyIndex])
                   }
               }
               turn++
           }
           //rest mechanic in order to allow party members to heal up to the max HP
           "camp" ->{
               println("The party decided to rest and recovers a small amount of HP")
               for (i in party.indices) {
                   val recovery = (1..5).random()
                   party[i].HP += recovery
                   if (party[i].HP > party[i].maxHP) {
                       party[i].HP = party[i].maxHP
                   }
                   println("${party[i].name} recovered $recovery HP.")
                   println("${party[i].name}: ${party[i].HP} / ${party[i].maxHP}")
               }
               turn++
           }
           //displaying current party stats
           "stats" ->{
               for (i in party.indices) {
                   println("${i + 1}. ${party[i].name} - HP: ${party[i].HP} / ${party[i].maxHP}")
               }
               turn++
           }
            // option for the user to run from the fight, but with a 50% chance of being unsuccessful and being attacked by the enemy
           "run" -> {
               val escapeChance = (1..2).random()
               if (escapeChance == 1) {
                   println("The party was unable to successfully leave the fight.")
                   val random = (0..3).random()
                   battleService.enemyAttack(enemies[enemyIndex], party[random])
               } else {
                   println("The party was able to successfully leave the fight.")
                   result.status = Status.ESCAPED
                   quit = true
               }
           }
        }
           //adding gold for defeating the enemy
           if (!enemies[enemyIndex].isAlive()){
               println("The party has defeated the ${enemies[enemyIndex].name}!")
               val loot = (1..10).random() * enemies[enemyIndex].lvl
               for (i in party.indices) {
                   if (party[i].isAlive()) {
                       party[i].gold += loot
                       println("${party[i].name} gained $loot coins! ${party[i].name} now has a total of ${party[i].gold} gold coins!")
                   }
               }
               println()
               result.status = Status.VICTORY
               enemyIndex++
               turn++
           }
           turn++
       }
    }
    //checking whether all party members or all enemies have been defeated.
    if (!quit && party.none { it.isAlive() }) {
        println("Your party has been defeated! Thanks for playing!")
        println("Here are the party's gold!")
        for (i in party.indices) {
            println("${party[i].name} has ${party[i].gold} gold coins!")
        }
        println("$user took a total of $turn turns!")
    } else if (!quit && enemies.none { it.isAlive() }) {
        println("All enemies defeated! Victory!")
    }
}
