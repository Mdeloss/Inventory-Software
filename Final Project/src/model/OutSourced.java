package model;

/** This class holds outsourced parts attributes.
 * <p>RUNTIME ERROR</p>
 *  * <p>An error I occurred was calling the super constructor incorrectly. To correct this,
 *  I rearranged the order of the constructor attributes to match the inherited class. </p>*/
public class OutSourced extends Part {

    // Private fields
    private String companyName;

    /** This method calls a super constructor to inherit from Part class. */
        // Calling Super Constructor
     public OutSourced(int id, int stock, int min, int max, String name, double price, String companyName) {
         super(id, name, price, stock, min, max);
         this.companyName = companyName;
     }

    // Getter and Setters
    /** @return the company Name */
    public String getCompanyName() {
        return companyName;
    }

    /** @param companyName the company name to set */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
