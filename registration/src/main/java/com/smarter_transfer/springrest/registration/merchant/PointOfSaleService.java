package com.smarter_transfer.springrest.registration.merchant;

import java.util.List;

import com.smarter_transfer.springrest.registration.merchant.model.PointOfSale;

/**
 * This Service provides the necessary functionality to manage PointOfSales
 * @author kaikun
 *
 */
public interface PointOfSaleService {
	
	/** Adds the pos to the system.
	 * 
	 * @param pos the pos to add
	 * 
	 * @throws common.app.error.DuplicateRecordException if the posId already exists
	 */
	public void addPOS(PointOfSale pos);
	
	/** Updates the existing pos in the system.
	 * 
	 * @param pos the pos to update
	 * 
	 * @throws common.app.error.DuplicateRecordException if the posId already exists
	 */
	public void updatePOS(PointOfSale pos);
	
	/**
     * Deletes the pos with the given identifier from the System.
     *
     * @param posId : ID of the pos
     *
     * @return the pos with the given {@code posId}
     *
     * @throws common.app.error.RecordNotFoundException if the pos does not exist
     * @throws IllegalArgumentException if the posId is invalid
     */
	public void deletePOS(long merchantId, long posId);
	
	/**
     * Answers the pos with the given identifier.
     *
     * @param posId : ID of the pos
     *
     * @return the pos with the given {@code posId}
     *
     * @throws common.app.error.RecordNotFoundException if the pos does not exist
     * @throws IllegalArgumentException if the posId is invalid
     */
	public PointOfSale getPOS(long merchantId, long posId);
	/** Answers a list with all POSs in the system
	 * @param merchantId the id of merchant who owns the POS
	 * @param start defines the start of the result list
	 * @param limit defines how many items should be returned
	 * @return List of all POSs in the system
	 */
	public List<PointOfSale> getPOSs(long merchantId, int start, int limit);
	/** Returns the total amount of POSs in the system
	 * @param merchantId the id of the merchant which POS are counted
	 * @return total amount of POSs in the system
	 */
	public long count(long merchantId);

}
