package model;

/** This class holds in-house sourced parts attributes.
 * <p>RUNTIME ERROR</p>
 * <p>An error I occurred was calling the super constructor incorrectly. To correct this,
 *  I rearranged the order of the constructor attributes to match the inherited class. </p>*/
public class InHouse extends Part {

    // Private field
    private int machineId;

    /** This method calls a super constructor to inherit from Part class. */
        // Calling Super Constructor
    public InHouse(int id, int stock, int min, int max, String name, double price, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** @return the machine ID */
    public int getMachineId() {
        return machineId;
    }

    /** @param machineId machineId to set */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }


}

