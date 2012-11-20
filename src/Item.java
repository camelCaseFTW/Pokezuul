
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
    
    /**
     * This method is to overwrite the equals method of the super class,
     * it returns true, if this Item is the same Object as obj
     * or if the ItemNames are the same, ignoring the case
     * @param Object
     * @return boolean
     * @author Ehsan Karami
     */
    public boolean equals(Object obj)
    {
    	if( !(obj instanceof Item) ) return false;
    	if(this == obj) return true;
    	Item itm = (Item)obj;
    	return this.getName().equalsIgnoreCase(itm.getName());
    }
}
