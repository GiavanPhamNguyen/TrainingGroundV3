package service
import model.Character
import model.Enemy

// the meat and butter. Functions for fighting between user and the enemies
class BattleService {
    fun playerAttack(attacking: Character, defending: Enemy): TurnResult {
        val hitChance = (2..12).random() + attacking.intelligence
        if (hitChance > 7) {
            val damage = attacking.attack()
            defending.takeDamage(damage)
            val defeated = !defending.isAlive()
            return TurnResult(attacking.name, defending.name, damage, defeated)
        } else {
            val defeated = !defending.isAlive()
            return TurnResult(attacking.name, defending.name, 0, defeated)
        }
    }

    fun enemyAttack(attacking: Enemy, defending: Character): TurnResult {
            val hitChance = (2..12).random() + attacking.lvl - defending.agility
            if (hitChance > 7) {
                val damage = attacking.attack()
                defending.takeDamage(damage)
                val defeated = !defending.isAlive()
                return TurnResult(attacking.name, defending.name, damage, defeated)
            } else {
                val defeated = !defending.isAlive()
                return TurnResult(attacking.name, defending.name, 0, defeated)
            }
    }
}
