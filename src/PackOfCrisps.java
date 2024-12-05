/**
 * Represents a packet of crisps with a specific flavour.
 * A packet starts closed, contains 10 crisps initially, and can be opened to allow crisps to be eaten.
 * The packet can become empty when all crisps are consumed.
 */
public class PackOfCrisps {
    private boolean open;
    private int numOfCrisps; // 10 when full, 0 when empty
    private String flavour;

    /**
     * Constructor for PackOfCrisps.
     * Initializes a new packet with the given flavour, closed and full of crisps.
     *
     * @param flavour the flavour of the crisps.
     */
    public PackOfCrisps(String flavour) {
        open = false;
        this.flavour = flavour;
        numOfCrisps = 10;
    }

    /**
     * Checks if the packet is open.
     *
     * @return true if the packet is open, false otherwise.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Checks if the packet is closed.
     *
     * @return true if the packet is closed, false otherwise.
     */
    public boolean isClosed() {
        return !open;
    }

    /**
     * Sets the state of the packet to open or closed.
     *
     * @param open true to open the packet, false to close it.
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * Gets the number of crisps remaining in the packet.
     *
     * @return the number of crisps in the packet.
     */
    public int getNumOfCrisps() {
        return numOfCrisps;
    }

    /**
     * Checks if the packet is empty.
     *
     * @return true if the packet is empty (0 crisps), false otherwise.
     */
    public boolean isEmpty() {
        return numOfCrisps == 0;
    }

    /**
     * Checks if the packet is full.
     *
     * @return true if the packet is full (10 crisps), false otherwise.
     */
    public boolean isFull() {
        return numOfCrisps == 10;
    }

    /**
     * Sets the number of crisps in the packet.
     *
     * @param numOfCrisps the new number of crisps to set in the packet.
     */
    public void setNumOfCrisps(int numOfCrisps) {
        this.numOfCrisps = numOfCrisps;
    }

    /**
     * Eats a crisp from the packet.
     * If the packet is not open, prints a warning message and does nothing.
     * If the packet is empty, prints a warning message and does nothing.
     * Otherwise, reduces the number of crisps in the packet by one.
     */
    public void eatCrisp() {
        if (open) {
            if (numOfCrisps > 0) {
                numOfCrisps--;
            } else {
                System.out.println("Packet is empty!");
            }
        } else {
            System.out.println("Packet isn't open!");
        }
    }

    /**
     * Gets the flavour of the crisps in the packet.
     *
     * @return the flavour of the crisps.
     */
    public String getFlavour() {
        return flavour;
    }

    /**
     * Sets the flavour of the crisps in the packet.
     *
     * @param flavour the new flavour of the crisps.
     */
    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    /**
     * Opens the packet.
     * Changes the state of the packet to open.
     */
    public void open() {
        open = true;
    }
}