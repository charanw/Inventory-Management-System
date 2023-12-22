/**
 * @author Charan Williams
 * */
package Model.Part;
//Declare subclass InHouse
public class InHouse extends Part {
    //Declare int machineId
    /**The machine ID for an in-house part*/
    private int machineId;
    //Setter
    /**
     * Sets the machine ID of an in-house part
     * @param machineId the machine ID of the in-house part
     * */
    public void setMachineId(int machineId) {
        this.machineId=machineId;
    }
    //Getter
    /**
     * Gets the machine ID of an in-house part
     * @return  machineId the machine ID of the in-house part
     * */
    public int getMachineId(){
        return machineId;
    }
    //Constructor
    /**
     * Creates a new in-house part object
     * @param  id the part's ID
     * @param name the part's name
     * @param price the part's price
     * @param stock the part's sock amount
     * @param min the part's minimum amount
     * @param max the part's maximum amount
     * @param machineId the part's machine ID
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id,name, price, stock, min, max);
        this.machineId=machineId;
    }
}
