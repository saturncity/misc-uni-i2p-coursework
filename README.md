> [!WARNING]
> This is a six-class Java simulation I wrote in December 2024 for my first-year Introduction to Programming module: a snack bar where students buy, open and eat packets of crisps from a vending machine, one action per student per time step. It compiles and runs, but the simulation stalls. A student whose favorite flavor sells out keeps queueing for it forever instead of picking something else, so a 400-step run spent 335 steps printing the same failure line at a machine that still held 15 packets. I've written that up in [Known issues](#known-issues) rather than fixing it, because the point of keeping this is the record of what I could build at the time.

<div align="center">

![Java](https://img.shields.io/badge/Java%2021-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white)

![No dependencies](https://img.shields.io/badge/dependencies-none-6E6E6E?style=for-the-badge)
![License](https://img.shields.io/badge/license-MIT-3DA639?style=for-the-badge)

</div>

## About

It's an object-interaction exercise, so the classes mostly exist to hold each other as fields. A `SnackMachine` holds packets and pennies, a `Student` holds a pocket and a reference to the machine, a `Pocket` holds `Penny` objects, and a `SnackBar` sits on top to stock the machine, create the students and run the clock. Every class and method carries a Javadoc block.

The whole thing is a text simulation. `SnackBar.runSnackBar` loops for a number of steps, and on each step it prints the machine's stock by flavor, picks one student at random and calls `snackTime()` on them. That method is a four-way state machine on the student's own packet: no packet means buy one, a closed packet means open it, an empty packet means throw it away, and anything else means eat a crisp. One action per step, so a full packet takes 13 steps to get through.

I modeled money as objects rather than a number. Each student starts with a `Pocket` of 20 `Penny` instances, a packet costs 3, and buying one moves three `Penny` objects out of the pocket and into the machine one at a time. `Penny` itself is an empty class with no fields and no methods, which makes the pocket a hash set of 20 objects that differ only by identity.

There are two commits. The second one deletes an unused method, which tells you roughly how much revision this got.

- A vending machine with a fixed capacity, per-flavor stock counting, penny-by-penny payment and a purchase that fails cleanly when you're short
- Students with a unique ID, a favorite flavor, a pocket of coins and a four-state snack routine
- Packets that track whether they're open and how many of their 10 crisps are left
- Random stocking across five flavors: plain, salt and vinegar, cheese and onion, prawn cocktail, barbecue
- A per-step report of what's in the machine, so you can watch stock drain
- Student count and step count settable from the command line, with defaults for both

## Tech stack

| Layer | Technology | Why it's here |
| --- | --- | --- |
| Language | Java 21 | `.idea/misc.xml` pins `languageLevel="JDK_21"` and the project SDK to `21`. Nothing in the source needs anything newer than generics and the enhanced `for` loop, so it'll compile on a much older JDK. I built and ran it on OpenJDK 21.0.9 |
| Standard library | `java.util.ArrayList`, `HashSet`, `Random` | Three imports across six files. No third-party code anywhere |
| Build | Plain `javac` | No Maven, no Gradle, no wrapper. One `javac` call builds it |
| IDE | IntelliJ IDEA | `.idea/` and `I2P Project.iml` are committed. You don't need the IDE to build this |

## What it looks like

Nothing renders outside a terminal, so there are no screenshots. Everything below is real output from the commands in [Running](#running), on OpenJDK 21.0.9. The machine stocks flavors at random, so your numbers will differ.

One student over six steps shows the whole state machine: buy, open, then eat one crisp per step. Watch the prawn cocktail count drop from 4 to 3 between step 1 and step 2, which is the purchase landing:

```text
Time Step 1
The SnackBar has 1 hungry students.
The SnackMachine has:
5 packets of plain crisps
2 packets of salt and vinegar crisps
5 packets of cheese and onion crisps
4 packets of prawn cocktail crisps
4 packets of barbecue crisps
student1 is buying a pack of crisps
student1 bought a pack of prawn cocktail crisps!

Time Step 2
...
3 packets of prawn cocktail crisps
...
student1 is opening the packet

Time Step 3
...
student1 is eating a prawn cocktail crisp
```

Run it long enough and it jams. Here the student's favorite flavor has hit zero while 15 packets of everything else sit in the machine, and every remaining step prints this and nothing else:

```text
Time Step 67
The SnackBar has 1 hungry students.
The SnackMachine has:
0 packets of plain crisps
2 packets of salt and vinegar crisps
2 packets of cheese and onion crisps
4 packets of prawn cocktail crisps
7 packets of barbecue crisps
student1 is buying a pack of crisps
The machine has run out of student1's favourite plain crisps!
```

## Getting started

### Prerequisites

- **A JDK, 8 or newer.** The project files pin 21 and that's what I tested on, but nothing in the source needs it. Whatever `javac` is on your path will do.
- **Nothing else.** No package manager, no dependencies to fetch, no environment variables, no database, no network calls.

You don't need IntelliJ. The `.idea/` folder and `I2P Project.iml` are committed because that's how I worked, but there's no generated source here, so plain `javac` builds exactly what the IDE does.

### Installation

There's no install step. Clone it:

```bash
git clone https://github.com/saturncity/misc-uni-i2p-coursework.git
cd misc-uni-i2p-coursework
```

### Running

Compile all six files into an `out/` directory. It has to be all of them together, since every class references at least one other:

```bash
javac -d out src/*.java
```

`SnackBar` holds the only `main`. With no arguments you get 5 students and 10 steps:

```bash
java -cp out SnackBar
```

Pass exactly two arguments for student count and step count, in that order:

```bash
java -cp out SnackBar 3 50
```

Pass exactly one and it prints a usage line and exits 1. Pass more than two and it ignores the extras without a word. Packet count and packet cost are hardcoded at 20 and 3 and there's no argument for either, whatever the Javadoc on `main` claims. That's covered in [Known issues](#known-issues).

`out/` is already in `.gitignore`, so it won't turn up in `git status`.

There's no server here, so no port and no URL. There are no tests either: no test directory, no JUnit, and no assertions anywhere in the source.

## Project structure

```text
.
├── src/
│   ├── SnackBar.java         # holds main. Stocks the machine, makes the students, runs the clock
│   ├── SnackMachine.java     # stock, capacity, cost, and the pennies it swallows. The biggest class
│   ├── Student.java          # snackTime() is the four-state routine the whole simulation runs on
│   ├── PackOfCrisps.java     # open/closed, 10 crisps, a `flavour` string
│   ├── Pocket.java           # a HashSet of Penny objects with add, remove and count
│   └── Penny.java            # an empty class. 13 lines, 11 of them Javadoc
├── .idea/                    # committed IntelliJ config, not needed to build
├── I2P Project.iml           # marks src as a source root, inherits the project JDK
├── LICENSE
└── README.md
```

## Known issues

There are no `TODO` or `FIXME` comments in the source. I found all of this by reading the code and confirming each one against a run. I'm not fixing any of it.

**The simulation stalls**

1. **A student who can't buy gives up permanently.** The constructor sets `favouriteFlavour` once and nothing reassigns it, and `snackTime()` branches only on the student's own packet, so a student with no packet does nothing but retry the same purchase. Once that flavor hits zero the student is finished for the rest of the run, even with the machine full of everything else. One student over 400 steps: 335 of them printed `The machine has run out of student1's favourite plain crisps!` at a machine holding 15 packets.
2. **Running out of money jams it the same way.** 20 pennies at 3 each buys 6 packets, then the student is stuck on `student1 doesn't have enough money to buy a pack!` for good. Nothing ever refills a pocket. It takes roughly 80 steps to reach, so a default 10-step run never sees it.
3. **Nothing restocks the machine.** `SnackBar` fills it once in its constructor to exactly `capacity` packets and there's no reload path, so stock only ever goes down. That also means `addPack`'s `The machine is full! Cannot add more packs.` branch can't fire from the simulation, because the machine is stocked to capacity and never touched again.

**Command line handling**

4. **The Javadoc on `main` describes arguments that don't exist.** It says "number of students, number of packets, packet cost, and number of steps". The code reads two: `args[0]` as student count, `args[1]` as step count. Packet count and cost are locals fixed at 20 and 3.
5. **One argument is a fatal error.** `if (args.length >= 2)` parses, `else if (args.length != 0)` catches the single-argument case and exits 1. Three or more arguments take the first branch and the extras vanish without a word.
6. **"Invalid input. Using default values." is only half true.** The two `parseInt` calls sit in one `try`, and `numStudents` is assigned before `nSteps` throws. `java -cp out SnackBar 3 abc` prints the fallback message and then runs with 3 students, not the default 5. Only the step count falls back.

**Unreachable code**

7. **Every defensive message in the model classes is dead.** `Student` guards each one before it can fire: it checks `isEmpty()` and `isClosed()` before calling `eatCrisp()`, so `Packet is empty!` and `Packet isn't open!` never print; it checks `pennyCount()` and `countPackets(flavour)` before calling `buyPack`, so `You need to insert more pennies!` and `No pack of the specified flavour available!` never print; and `Pocket.removePenny()` can't return its null branch. Confirmed over a 400-step run, where none of them appeared. They're the right checks to have written, they just have no path to them.
8. **A dozen accessors have no callers.** `PackOfCrisps.isOpen`, `isFull`, `setOpen`, `setNumOfCrisps`, `setFlavour` and `getNumOfCrisps`; `SnackMachine.getCapacity`, `setCapacity`, `setCost`, `getPayment`, `setPayment`, `getPennies`, `isEmpty` and the no-argument `countPackets()`. Full getter-and-setter pairs on every field was the house style my course taught, so they're there whether or not anything wants them.

**Model problems**

9. **`Student.buyCrisps` announces a purchase it never checked.** It assigns `snackMachine.buyPack(...)` to `packOfCrisps` and prints `bought a pack` on the next line with no null test, so a failed purchase would set the field to null and claim success. The guards above it stop that happening today, which makes it a bug waiting for someone to relax a guard.
10. **`buyPack` keeps your money either way.** On success it sets `payment = 0` without returning change, and when the flavor is missing it returns null while leaving `payment` and the inserted pennies where they are. Neither is reachable from `Student`, which always inserts exactly `cost`.
11. **`SnackMachine.pennies` is a black hole.** `insertMoney` appends every coin and nothing ever reads the list or empties it. `getPennies()` has no callers, so the machine's takings are unobservable.
12. **`Pocket` uses a `HashSet<Penny>` to count identical objects.** `Penny` has no fields and overrides neither `equals` nor `hashCode`, so the set holds 20 objects that are distinguishable only by identity and hash to 20 different buckets by accident of `Object.hashCode`. An `int` counter does the same work. Modeling a coin as an object was the assignment's call, storing them in a hash set was mine.
13. **`PackOfCrisps` hardcodes the packet size 10 twice.** Once in the `PackOfCrisps` constructor and again in `isFull()`, with the comment `// 10 when full, 0 when empty` doing the job a constant should.
14. **`Student.STUDENT_ID` is a mutable static in constant case.** It also counts per JVM rather than per `SnackBar`, which doesn't show because `main` only ever builds one.
15. **`The SnackBar has 1 hungry students.`** No plural handling on any of the output.

## Contributing

I'm not taking changes to this one and I'm not patching the list above, since fixing the stall would erase what the repo is a record of. Fork it if a piece is useful to you. If you want the interesting part, it's `Student.snackTime()` and the way the guards around it have quietly made half the error handling unreachable.

## License

MIT. See [LICENSE](LICENSE) for the full text.
