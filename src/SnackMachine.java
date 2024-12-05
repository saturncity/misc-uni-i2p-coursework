import java.util.ArrayList;

/**
 * Represents a vending machine that dispenses crisps.
 * The machine holds a limited number of packets, accepts pennies as payment, and will allow customers to buy snacks.
 */
public class SnackMachine {
    private ArrayList<PackOfCrisps> packets;
    private int capacity;
    private int cost;
    private int payment;
    private ArrayList<Penny> pennies;

    /**
     * Constructor for SnackMachine.
     * Initializes a brand-new machine with a specific capacity and cost per packet.
     *
     * @param capacity the maximum number of packets.
     * @param cost     the cost (in pennies) of a packet.
     */
    public SnackMachine(int capacity, int cost) {
        packets = new ArrayList<>();
        this.capacity = capacity;
        this.cost = cost;
        payment = 0;
        pennies = new ArrayList<>();
    }

    /**
     * Inserts a Penny into the machine as payment.
     *
     * @param penny the Penny to insert.
     */
    public void insertMoney(Penny penny) {
        pennies.add(penny);
        payment++;
    }

    /**
     * Buys a packet of crisps with the specified flavour.
     * Ensures sufficient payment has been inserted and that the specified flavour is available in the machine.
     *
     * @param flavour the flavour of the crisps to buy.
     * @return the bought PackOfCrisps if successful, or null if insufficient payment or the flavour is unavailable.
     */
    public PackOfCrisps buyPack(String flavour) {
        if (payment < cost) {
            System.out.println("You need to insert more pennies!");
            return null;
        }

        for (PackOfCrisps pack : packets) {
            if (pack.getFlavour().equals(flavour)) {
                packets.remove(pack);
                payment = 0;
                return pack;
            }
        }
        System.out.println("No pack of the specified flavour available!");
        return null;
    }

    /**
     * Gets the collection of packets currently in the machine.
     *
     * @return an ArrayList of PackOfCrisps objects.
     */
    public ArrayList<PackOfCrisps> getPackets() {
        return packets;
    }

    /**
     * Counts the total number of packets in the machine.
     *
     * @return the total number of packets in the machine.
     */
    public int countPackets() {
        return packets.size();
    }

    /**
     * Counts the number of packets of a specific flavour in the machine.
     *
     * @param flavour the flavour of crisps to count.
     * @return the number of packets with the specified flavour.
     */
    public int countPackets(String flavour) {
        int counter = 0;
        for (int i = 0; i < packets.size(); i++) {
            if (packets.get(i).getFlavour().equals(flavour)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Adds a packet of crisps to the machine if there is enough space.
     *
     * @param pack the PackOfCrisps to add to the machine.
     */
    public void addPack(PackOfCrisps pack) {
        if (!isFull()) {
            packets.add(pack);
        } else {
            System.out.println("The machine is full! Cannot add more packs.");
        }
    }

    /**
     * Gets the maximum capacity of the machine.
     *
     * @return the capacity of the machine.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Checks if the machine is empty.
     *
     * @return true if the machine is empty, false otherwise.
     */
    public boolean isEmpty() {
        return packets.isEmpty();
    }

    /**
     * Checks if the machine is full.
     *
     * @return true if the machine is full, false otherwise.
     */
    public boolean isFull() {
        return packets.size() >= capacity;
    }

    /**
     * Sets the capacity of the machine.
     *
     * @param capacity the new capacity of the machine.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the cost of a single packet of crisps.
     *
     * @return the cost of a single packet.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost of a single packet of crisps.
     *
     * @param cost the new cost of a packet.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets the current payment amount in the machine.
     *
     * @return the payment amount in pennies.
     */
    public int getPayment() {
        return payment;
    }

    /**
     * Sets the current payment amount in the machine.
     *
     * @param payment the new payment amount.
     */
    public void setPayment(int payment) {
        this.payment = payment;
    }

    /**
     * Gets the collection of pennies currently in the machine.
     *
     * @return an ArrayList of Penny objects.
     */
    public ArrayList<Penny> getPennies() {
        return pennies;
    }

    /**
     * Gets a list of all unique flavours currently available in the machine.
     *
     * @return an ArrayList of unique crisp flavours as Strings.
     */
    public ArrayList<String> getAvailableFlavours() {
        ArrayList<String> availableFlavours = new ArrayList<>();
        for (PackOfCrisps pack : packets) {
            String flavour = pack.getFlavour();
            if (!availableFlavours.contains(flavour)) {
                availableFlavours.add(flavour);
            }
        }
        return availableFlavours;
    }

}