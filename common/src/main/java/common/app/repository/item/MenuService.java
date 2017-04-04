package common.app.repository.item;

import java.util.List;

import common.app.model.item.Menu;

/**
 * This Service provides the necessary functionality to manage menus
 * @author kaikun
 *
 */
public interface MenuService {
	
	/** Adds the menu to the system.
	 * 
	 * @param menu the merchant to add
	 * 
	 */
	public void addMenu(Menu menu);
	
	/** Updates the existing menu in the system.
	 * 
	 * @param menu the menu to update
	 * 
	 */
	public void updateMenu(Menu menu);
	
	/**
     * Deletes the menu with the given identifier from the System.
     *
     * @param merchantId : id of the merchant
     * @param menuId : id of the menu from the merchant
     *
     * @throws common.app.error.RecordNotFoundException if the menu does not exist
     * @throws IllegalArgumentException if the menuId or merchantId is invalid
     */
	public void deleteMenu(long merchantId, long menuId);
	
	/**
     * Answers the menu with the given identifier.
     *
     * @param merchantId : id of the merchant
     * @param menuId : id of the menu from the merchant
     * @return the menu with the given {@code merchantId} {@code menuId}
     * 
     * @throws common.app.error.RecordNotFoundException if the menu does not exist
     * @throws IllegalArgumentException if the merchantId or menuId is invalid
     */
	public Menu getMenu(long merchantId, long menuId);
	
	/** Answers a list with all menus from the merchant in the system
	 * @param merchantId identifier of the merchant who created the menus
	 * @param start defines the start of the result list
	 * @param limit defines how many menus should be returned
	 * @return List of all menus from the merchant in the system
	 */
	public List<Menu> getMenus(long merchantId, int start, int limit);
	
	/** Returns the total amount of menus from the merchant in the system
	 * 
	 * @param merchantId identifier of the merchant who created the menus
	 * @return total amount of menus from the merchant in the system
	 */
	public long count(long merchantId);
	
}
