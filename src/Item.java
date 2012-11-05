
/**
 * An item that any ItemHolder can carry
 * 
 * @author Alok Swamy
 */
public class Item {
	
	private static final String default_description = "An ordinary item";
	
    protected String itemName;
    protected String itemDescription;

    public Item(String name, String description){
    	itemName = name;
    	itemDescription = description;
    }
    
    public Item(String name) {
        this(name, default_description);
    }
    
    public Item() {
        this("", default_description);
    }

    public String getName() {
        return itemName;
    }
    
    public String getDescription() {
        return itemDescription;
    }

    public void setName (String s) {
        itemName = s;
    }
    
    public void setDescriptiong(String s) {
        itemDescription = s;
    }

    public String toString() {
        return itemName;
    }
}
