import java.util.*;
/**
 * Abstract class ItemHolder - An object that is able to carry a bunch of items
 * 
 * @author Alok Swamy
 */
public abstract class ItemHolder {

	private static final String NO_ITEMS = " -Empty-";
	protected List<Item> items;
	protected List<Item> removedItems; // for undo purposes; E.K
    
    public ItemHolder() {
        items = new ArrayList<Item>();
        removedItems = new ArrayList<Item>();
    }
    
    public void insertItem(Item item) {
        items.add(item);
        if(removedItems.contains(item))// added for undo purposes; E.K
        {
        	removedItems.remove(item);
        }
    }
    
    public void removeItem(Item item) {
        if (items.contains(item))
        	{
        		removedItems.add(items.get(items.indexOf(item))); // to keep the integrity of the Item; E.K
        		items.remove(item);
        		
        	}
    }
    /**
     * Accessor for the removed Items
     * @return List<Items> removedItems
     * @author Ehsan Karami
     */
    public List<Item> getRemovedItems()
    {
    	return this.removedItems;
    }
    
    public void removeItem(String itemName) {
        /*for (Item item : items )
            if (item.getName().equals(itemName)) removeItem(item);*/ //E.K
    	Item itm = new Item(itemName);
    	removeItem(itm);
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
    
    public int numOfItems() {
    	return items.size();
    }
}