/**
 * @author Charan Williams
 * */
package Model.Part;
//Declare subclass Outsourced
public class Outsourced extends Part {
    //Declare string companyName
    /**The company name for an outsourced part*/
    private String companyName;
    //Setter
    /**
     * Sets the company name of an outsourced part
     * @param companyName the company name of the outsourced part
     * */
    public void setCompanyName(String companyName) {
        this.companyName=companyName;
    }
    //Getter
    /**
     * Gets the company name of an outsourced part
     * @return companyName the company name of the outsourced part
     * */
    public String getCompanyName(){
        return companyName;
    }
    //Constructor
    /**
     * Creates a new outsourced part object
     * @param id the part's ID
     * @param name the part's name
     * @param price the part's price
     * @param stock the part's stock amount
     * @param min the part's minimum amount
     * @param max the part's maximum amount
     * @param companyName the part's company name
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name,price, stock, min, max);
        this.companyName=companyName;
    }
}
