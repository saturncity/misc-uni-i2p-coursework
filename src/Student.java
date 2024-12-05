/**
 * Represents a student who can buy and consume crisps from a snack machine.
 * Each student has a unique ID, a favourite crisp flavour, a pocket of coins, and interacts with a SnackMachine.
 */
public class Student {
    private static int STUDENT_ID = 0; // Static variable to generate unique IDs

    private String favouriteFlavour;
    private SnackMachine snackMachine;
    private String idNumber;
    private Pocket pocket;
    private PackOfCrisps packOfCrisps;

    /**
     * Constructor for the Student class.
     * Initializes the student's favourite flavour, snack machine, unique ID, pocket, and initial crisps pack.
     *
     * @param flavour the student's favourite crisp flavour.
     * @param machine the SnackMachine the student will use to buy crisps.
     */
    public Student(String flavour, SnackMachine machine) {
        STUDENT_ID++; // Increment static ID for unique identification
        this.favouriteFlavour = flavour;
        this.snackMachine = machine;
        this.idNumber = "student" + STUDENT_ID; // UID
        this.pocket = new Pocket(20); // Starting with 20 pennies
        this.packOfCrisps = null; // Starting without crisps
    }

    /**
     * Attempts to buy a packet of crisps of the favourite flavour.
     */
    private void buyCrisps() {
        if (pocket.pennyCount() < snackMachine.getCost()) {
            System.out.println(idNumber + " doesn't have enough money to buy a pack!");
            return;
        }

        if (snackMachine.countPackets(favouriteFlavour) == 0) {
            System.out.println("The machine has run out of " + idNumber + "'s favourite " + favouriteFlavour + " crisps!");
            return;
        }

        // Insert pennies into the machine
        for (int i = 0; i < snackMachine.getCost(); i++) {
            snackMachine.insertMoney(pocket.removePenny());
        }

        // Buy a pack of crisps
        this.packOfCrisps = snackMachine.buyPack(favouriteFlavour);
        System.out.println(idNumber + " bought a pack of " + favouriteFlavour + " crisps!");
    }


    /**
     * Handles the logic for snack time: buying, opening, eating, and finishing a pack of crisps.
     */
    public void snackTime() {
        if (packOfCrisps == null) {
            System.out.println(idNumber + " is buying a pack of crisps");
            buyCrisps();
        } else if (packOfCrisps.isClosed()) {
            System.out.println(idNumber + " is opening the packet");
            packOfCrisps.open();
        } else if (packOfCrisps.isEmpty()) {
            System.out.println(idNumber + " has finished the packet!");
            packOfCrisps = null; // Dispose of the empty pack
        } else {
            System.out.println(idNumber + " is eating a " + favouriteFlavour + " crisp");
            packOfCrisps.eatCrisp();
        }
    }
}
