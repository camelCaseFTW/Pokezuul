
/**
 * An item that any ItemHolder can carry
 * 
 * @author Alok Swamy
 */
public class Item
{
    protected String itemName;
    protected String itemDescription;
    // Item weight was temporarily removed because it has no use currently in the game
    //protected int itemWeight;

    public Item(String name) {
        itemName = name;
        itemDescription = "An ordinary item";
        //itemWeight = 0;
    }
    
    public Item() {
        this("");
    }

    public String getName() {
        return itemName;
    }
    
    public String getDescription() {
        return itemDescription;
    }
    /*
    public int getWeight() {
        return itemWeight;
    }
    */
    public void setName (String s) {
        itemName = s;
    }
    
    public void setDescriptiong(String s) {
        itemDescription = s;
    }
    /*
    public void setWeight(int i) {
        itemWeight = i;
    }
    */
    public String toString() {
        return itemName;
    }
}
