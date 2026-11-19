# Witch Battle

A turn-based 1 vs 1 witch battle game for the console, written in Java.
Built with Object-Oriented Programming: classes, enums, records and objects
that talk to each other. Inspired by *The Owl House*.

Developed with **Java 23** in **IntelliJ IDEA 2026.3**.

## How to play

First you pick a mode (against the CPU or two players), then you choose your coven.

Each turn you have 5 visible cards:
- 3 fixed powers from your coven: Attack, Special and Defense.
- 2 wild magic glyphs drawn from the deck.

The turn has two phases:
1. **Glyph rotation:** you may discard one glyph and draw a new one. Optional.
2. **Combat action:** choose one of these three options:
   - Play only a power from your character.
   - Play only a glyph.
   - Wild Combo: a power and a glyph together (costs 2 energy).

Whoever drops the opponent to 0 HP wins.

## The 10 covens

Each coven has its own attack, special and defense, and is strong against
another coven (attacking that one deals critical damage).

| Coven | Strong against |
|---|---|
| Abominations | Construction |
| Plants | Abominations |
| Illusions | Beasts |
| Bard | Illusions |
| Healing | Potions |
| Beasts | Bard |
| Potions | Plants |
| Oracle | Emperor |
| Construction | Oracle |
| Emperor | Healing |

## The 4 glyphs

- **Fire:** extra damage and burn. Burns organic defenses.
- **Ice:** slows the rival down or builds barricades.
- **Plant:** traps the rival and blocks their defense next turn.
- **Light:** blinds the rival so you dodge the next attack.

## Extra rules

- **Energy:** you gain 1 point at the start of each turn. Only combos cost 2.
- **Cooldown:** the Special cannot be used for 2 turns after you use it.
- **No repeats:** you cannot use the same power or the same glyph two turns in a row.
- **Defense:** if the rival is defending, your Attack fails.

## Classes in the project

| Class | What it does |
|---|---|
| `Aquelarre` | Enum with the 10 covens, their powers and their advantage. |
| `Glifo` | Enum with the 4 glyphs. |
| `Poder` | Enum with the three power types: Attack, Special, Defense. |
| `Accion` | Record that represents what a player does on their turn. |
| `MazoGlifos` | The glyph deck, using `Stack` and `Collections.shuffle`. |
| `Jugador` | HP, hand of glyphs and the no-repeat blocking rules. |
| `EstadoCombate` | Temporary effects: burn, shield, energy, cooldown... |
| `CalculadoraDanio` | Works out the damage, including the coven-critical hit. |
| `MotorCombate` | Applies the turn rules and returns the result. |
| `Consola` | Everything shown on screen. |
| `Juego` | The `main` method and the game loop. |

The project is split in three parts: the data (`Aquelarre`, `Glifo`),
the rules (`MotorCombate`, `CalculadoraDanio`) and the console interface
(`Consola`, `Juego`).

## How to run it

You need Java 17 or higher.

```bash
javac *.java
java Juego
