# TrainingGroundV3

Welcome to TrainingGroundV3, where the aim is still a turn-based combat game featuring a party of four faces off against a group of enemies. This version implements new features, such as inventory.

## Game Overview
This project is a **console-based turn-based battle system** written in Kotlin.  
Players control a small party of heroes who can fight various enemies, use items, rest to recover, and view stats — all through simple text-based commands.

This project builds on Assignment 2 by introducing:
- **An Enemy hierarchy** with unique behaviors  
- **An Inventory system** featuring Potions and Bombs  
- **A BattleService** that separates combat logic from console input/output  
- **TurnResult** data class for tracking and communicating battle outcomes

---

## Design Summary

### 1. Object-Oriented Design

#### Character.kt
- Base class for all party members.  
- Includes attributes like HP, Strength, Intelligence, and Agility.  
- Subclasses: `Warrior`, `Mage`, `Berserker`, `Assassin`.

#### Enemy.kt
- Base `Enemy` class plus unique types:  
  - `Goblin`: 10% chance to dodge attacks.  
  - `Orc`: Reduces incoming damage by 1 (minimum 1).  
  - `Wolf`: First attack gains +2 damage (“pounce”).  
  - Higher-level enemies (`Wyvern`, `Dragon`) for scaling difficulty.

#### Item.kt
- Data class representing an item’s name, type, damage/heal value, and description.  
- Current types: **Potion** (heals) and **Bomb** (deals fixed damage).

#### Inventory.kt
- Manages adding, removing, and using items.  
- Prevents over-healing and prints clear messages when items are used.

#### BattleService.kt
- Handles core combat logic for both player and enemy turns.  
- Determines hit chances, applies damage, and checks victory/defeat conditions.  
- Returns results through `TurnResult`.

#### TurnResult.kt
- Immutable data class that represents the outcome of a turn.  
- Contains: attacker, defender, damage, defeated flag, and status (ONGOING / VICTORY / DEFEAT / ESCAPED).

---

## Getting Started
Clone the repository:
```bash
git clone https://github.com/GiavanPhamNguyen/TrainingGroundV3.git
cd TrainingGroundV3
open project in IntelliJ IDEA or Kotlin-compatible IDE
run main() in ConsoleMain.kt

## How to Play

### Main Menu Options
- **Battle** – Engage in fights with enemies.  
- **Inventory** – View or use items.  
- **Camp** – Rest and recover HP.  
- **Stats** – Display current HP and stats.  
- **Run** – Attempt to flee from combat.  

### Battle Turn Options
- **Fight** – Choose a party member to attack an enemy.  
- **Inventory** – Use a Potion or Bomb during battle.  
- **Run** – Try to escape (50% success chance).  

---

## Gold System
- Gold rewards are given after defeating enemies.  
- The reward is **randomly generated** based on enemy level
- Any party members who are alive will receive said reward