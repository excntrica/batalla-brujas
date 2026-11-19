# Witch Battle

A turn-based 1 vs 1 witch battle game for the console, written in Java.
Built with Object-Oriented Programming: classes, enums, records, inheritance
and polymorphism. Inspired by *The Owl House*.

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
- **History:** every finished game is saved to `historial.txt` and can be
  reviewed when you launch the game again.

## Classes in the project

| Class | What it does |
|---|---|
| `Aquelarre` | Enum with the 10 covens. Builds their three powers and holds the advantage. |
| `Glifo` | Enum with the 4 glyphs. |
| `Poder` | Abstract class: the base of every power. Declares `ejecutar()`. |
| `PoderAtaque` | Attack power. Fails if the rival is defending. |
| `PoderEspecial` | Special power. Heals, weakens or seals the rival, and has a cooldown. |
| `PoderDefensa` | Defense power. Raises a shield against the next hit. |
| `ContextoPoder` | Carries everything a power needs to run (players, state, log). |
| `Accion` | Record that represents what a player does on their turn. |
| `MazoGlifos` | The glyph deck, using `Stack` and `Collections.shuffle`. |
| `Jugador` | HP, hand of glyphs and the no-repeat blocking rules. |
| `EstadoCombate` | Temporary effects: burn, shield, energy, cooldown... |
| `CalculadoraDanio` | Works out the damage, including the coven-critical hit. |
| `MotorCombate` | Applies the turn rules and returns the result. |
| `Consola` | Everything shown on screen: colours, health bars and panels. |
| `Historial` | Saves and reads the game history from a text file. |
| `Juego` | The `main` method and the game loop. |

## Design

The project is split into three layers:

- **Data:** `Aquelarre`, `Glifo`, `Poder` and its subclasses.
- **Rules:** `MotorCombate`, `CalculadoraDanio`, `EstadoCombate`.
- **Interface:** `Consola`, `Juego`.

### Polymorphism

Each power is an object that knows how to run itself. `Poder` is an abstract
class with an `ejecutar()` method, and `PoderAtaque`, `PoderEspecial` and
`PoderDefensa` implement it in their own way.

`MotorCombate` never checks which coven it is dealing with: it just calls
`poder.ejecutar(ctx)`. That means a new coven or a new kind of power can be
added without touching the engine.

### Persistence

`Historial` writes the result of every game to `historial.txt` using
`java.nio.file`, and reads it back when the game starts.

## How to run it

You need Java 17 or higher.

```bash
javac *.java
java Juego
