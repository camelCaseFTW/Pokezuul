import java.util.*;
/**
 * Abstract class ItemHolder - An object that is able to carry a bunch of items
 * 
 * @author Alok Swamy, edited by Eshan
 */
public abstract class ItemHolder {
	
	private static final String NO_ITEMS = " -Empty-";
    protected List<Item> items;
    
    public ItemHolder() {
        items = new ArrayList<Item>();
    }
    
    public void insertItem(Item item) {
        items.add(item);
    }
    
    public void removeItem(Item item) {
        if (items.contains(item)) items.remove(item);
    }
    
    public void removeItem(String itemName) {
        for (Item item : items )
            if (item.getName().equals(itemName)) removeItem(item);
    }
    
    public void clearItemList() {
        items = new ArrayList<Item>();
    }
    
    /*
     * Prints out the list of items that this object is carrying
     */
    public String getAllItems() {
        String allItems = new String();
        Iterator<Item> iterator = items.iterator(); // Fixed the warning by adding the generic type, <Item>; E.K
        
        while(iterator.hasNext()) {
            allItems = allItems + "  " + iterator.next();
        }
        if (allItems.length()==0) allItems = NO_ITEMS;
        return allItems;
    }
    
    public List<Item> getItemList() {
        return items;
    }
}