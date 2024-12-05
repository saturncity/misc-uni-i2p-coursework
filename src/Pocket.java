import java.util.HashSet;

/**
 * Represents a pocket that can hold a collection of Penny objects.
 * Provides methods to add, remove, count, and check pennies in the pocket.
 */
public class Pocket {
    private HashSet<Penny> pennies;

    /**
     * Constructor for Pocket.
     * Initializes a pocket with a specified number of pennies.
     *
     * @param size the initial number of pennies to add to the pocket.
     */
    public Pocket(int size) {
        pennies = new HashSet<>();
        for (int i = 0; i < size; i++) {
            pennies.add(new Penny());
        }
    }

    /**
     * Counts the number of pennies currently in the pocket.
     *
     * @return the number of pennies in the pocket.
     */
    public int pennyCount() {
        return pennies.size();
    }

    /**
     * Removes a Penny from the pocket if one is available.
     *
     * @return the removed Penny, or null if the pocket is empty.
     */
    public Penny removePenny() {
        if (pennies.isEmpty()) {
            return null;
        } else {
            Penny penny = pennies.iterator().next();
            pennies.remove(penny);
            return penny;
        }
    }
}