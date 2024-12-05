import java.util.ArrayList;
import java.util.Random;

/**
 * The SnackBar manages the students' interactions with the SnackMachine.
 */
public class SnackBar {
    private Random random;
    private String[] flavours;
    private SnackMachine snackMachine;
    private ArrayList<Student> students;

    /**
     * Constructor for the SnackBar class.
     * Initializes the SnackBar with a given number of students, packets of crisps, and cost per packet.
     *
     * @param numStudents the number of students in the SnackBar.
     * @param numPackets  the number of packets of crisps in the SnackMachine.
     * @param packetCost  the cost of a single packet of crisps in pennies.
     */
    public SnackBar(int numStudents, int numPackets, int packetCost) {
        random = new Random();
        flavours = new String[]{"plain", "salt and vinegar", "cheese and onion", "prawn cocktail", "barbecue"};
        snackMachine = new SnackMachine(numPackets, packetCost);
        students = new ArrayList<>();

        // Stocking SnackMachines
        for (int i = 0; i < numPackets; i++) {
            String randomFlavour = flavours[random.nextInt(flavours.length)];
            snackMachine.addPack(new PackOfCrisps(randomFlavour));
        }

        // Students
        for (int i = 0; i < numStudents; i++) {
            students.add(new Student(randomFlavour(), snackMachine));
        }
    }

    /**
     * Selects a random flavour of crisps from the available flavours.
     *
     * @return a random flavour as a String.
     */
    private String randomFlavour() {
        return flavours[random.nextInt(flavours.length)];
    }

    /**
     * Describes the state of the SnackBar.
     */
    public void describe() {
        System.out.println("The SnackBar has " + students.size() + " hungry students.");
        System.out.println("The SnackMachine has:");

        // Count packets by flavour
        int[] flavourCounts = new int[flavours.length];
        for (PackOfCrisps pack : snackMachine.getPackets()) {
            for (int i = 0; i < flavours.length; i++) {
                if (pack.getFlavour().equals(flavours[i])) {
                    flavourCounts[i]++;
                }
            }
        }
        for (int i = 0; i < flavours.length; i++) {
            System.out.println(flavourCounts[i] + " packets of " + flavours[i] + " crisps");
        }
    }

    /**
     * Simulates the SnackBar operation for a given number of steps.
     *
     * @param nSteps the number of time steps to simulate.
     */
    public void runSnackBar(int nSteps) {
        for (int step = 1; step <= nSteps; step++) {
            System.out.println("\nTime Step " + step);
            describe();

            // Choose a random student and call snackTime method
            Student randomStudent = students.get(random.nextInt(students.size()));
            randomStudent.snackTime();
        }
    }

    /**
     * Main method.
     * Constructs a SnackBar object and simulates its operation.
     *
     * @param args command line arguments: number of students, number of packets, packet cost, and number of steps.
     */
    public static void main(String[] args) {
        int numStudents = 5;
        int numPackets = 20;
        int packetCost = 3;
        int nSteps = 10;

        // Parse command line arguments
        if (args.length >= 2) {
            try {
                numStudents = Integer.parseInt(args[0]); // First argument: number of students
                nSteps = Integer.parseInt(args[1]);      // Second argument: number of steps
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Using default values.");
            }
        } else if (args.length != 0) {
            System.err.println("Usage: java SnackBar [numStudents] [numSteps]");
            System.exit(1);
        }

        // Run
        SnackBar snackBar = new SnackBar(numStudents, numPackets, packetCost);
        snackBar.runSnackBar(nSteps);
    }
}
