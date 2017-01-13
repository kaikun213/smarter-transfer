package com.smarter_transfer.springrest.registration.item;

import java.util.Date;
import java.util.List;

import com.smarter_transfer.springrest.registration.item.model.Item;

/**
 * This Service provides the necessary functionality to manage items
 * @author kaikun
 *
 */
public interface ItemService {
	
	/** Adds the item to the system.
	 * 
	 * @param item the merchant to add
	 * 
	 */
	public void addItem(Item item);
	
	/** Updates the existing item in the system.
	 * 
	 * @param item the item to update
	 * 
	 */
	public void updateItem(Item item);
	
	/**
     * Deletes the item with the given identifier from the System.
     *
     * @param merchantId : id of the merchant
     * @param itemId : id of the item from the merchant
     *
     * @throws common.app.error.RecordNotFoundException if the item does not exist
     * @throws IllegalArgumentException if the itemId is invalid
     */
	public void deleteItem(long merchantId, long itemId);
	
	/**
     * Answers the item with the given identifier.
     *
     * @param merchantId : id of the merchant
     * @param itemId : id of the item from the merchant
     * @return the item with the given {@code merchantId} {@code itemId}
     * 
     * @throws common.app.error.RecordNotFoundException if the item does not exist
     * @throws IllegalArgumentException if the merchantId or itemId is invalid
     */
	public Item getItem(long merchantId, long itemId);
	
	/** Answers a list with all items from the merchant in the system
	 * @param merchantId identifier of the merchant who created the items
	 * @param start defines the start of the result list
	 * @param limit defines how many items should be returned
	 * @return List of all items from the merchant in the system
	 */
	public List<Item> getItems(long merchantId, int start, int limit);
	
	/** Returns the total amount of items from the merchant in the system
	 * 
	 * @param merchantId identifier of the merchant who created the items
	 * @return total amount of items from the merchant in the system
	 */
	public long count(long merchantId);

}
