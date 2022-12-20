package model;


/** This class works with an InHouse part. */
public class InHouse extends Part {

    private int machineid;

    // must call the constructor from the class it's inheriting from
    // added machineId to be included in constructor
    /** Constructor for inHouse part object.
     * @param id id of part
     * @param name name of part
     * @param price price of part
     * @param stock quantity of a part
     * @param min minimum quantity of a part
     * @param max maxiumum quantity of a part
     * @param machineid machine id of a part
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max,int machineid) {
        super(id, name, price, stock, min, max);// must be the 1st statement
        // adding this parameter to this InHouse machineid obj field
        this.machineid = machineid;
    }
    /** getMachineID method. Retrieves the id of a machine
     @return the machineid
     */
    public int getMachineid() {
        return machineid;
    }
    /** setMachineid method. Sets the machineid
      @param machineid  the machineid to set
     */
    public void setMachineid(int machineid) {
        this.machineid = machineid;
    }
}
