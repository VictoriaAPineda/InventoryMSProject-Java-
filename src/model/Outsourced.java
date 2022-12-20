package model;
/**This class works with OutSoured parts. */
public class Outsourced extends Part {

    private String companyName;

    /** Constructor for an outsourced part object.
     * @param id id of part
     * @param name of part
     * @param price of part
     * @param stock quantity of a part
     * @param min minimum quantity of a part
     * @param max maximum quantity of a part
     * @param companyName name of company of a part
     *  */
    public Outsourced(int id, String name, double price, int stock, int min, int max,String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /** getCompanyName method. Retrieves the named of company
     @return company name
     */
    public String getCompanyName() {
        return companyName;
    }
    /** setMachineid method. Sets the machineid
     @param companyName  the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
