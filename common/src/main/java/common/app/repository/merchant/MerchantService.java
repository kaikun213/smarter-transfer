package common.app.repository.merchant;

import java.util.List;

import common.app.error.DuplicateRecordException;
import common.app.model.merchant.Merchant;

/**
 * This Service provides the necessary functionality to manage merchants
 * @author kaikun
 *
 */
public interface MerchantService {
	/** Adds the merchant to the system.
	 * 
	 * @param merchant the merchant to add
	 * 
	 * @throws common.app.error.DuplicateRecordException if the keshId already exists
	 */
	public void addMerchant(Merchant merchant);
	
	/** Updates the existing merchant in the system.
	 * 
	 * @param merchant the merchant to update
	 * 
	 * @throws common.app.error.DuplicateRecordException if the keshId already exists
	 */
	public void updateMerchant(Merchant merchant);
	
	/**
     * Deletes the merchant with the given identifier from the System.
     *
     * @param merchantId : ID of the merchant
     *
     * @return the merchant with the given {@code merchantId}
     *
     * @throws common.app.error.RecordNotFoundException if the merchant does not exist
     * @throws IllegalArgumentException if the merchantId is invalid
     */
	public void deleteMerchant(long merchantId);
	
	/**
     * Answers the merchant with the given identifier.
     *
     * @param merchantId : ID of the merchant
     *
     * @return the merchant with the given {@code merchantId}
     *
     * @throws common.app.error.RecordNotFoundException if the merchant does not exist
     * @throws IllegalArgumentException if the merchantId is invalid
     */
	public Merchant getMerchant(long merchantId);
	/** Answers a list with all merchants in the system
	 * @param start defines the start of the result list
	 * @param limit defines how many items should be returned
	 * @return List of all merchants in the system
	 */
	public List<Merchant> getMerchants(int start, int limit);
	/** Returns the total amount of merchants in the system
	 * 
	 * @return total amount of merchants in the system
	 */
	public long count();
	/** Check if there is a Violation of unique constrains on keshId
	 * 
	 * @param merchantId merchantId of the merchant with the keshId
	 * @param keshId KeshId of the merchant to insert/update
	 * @throws DuplicateRecordException if there is already a duplicate keshId which does not belong to the same merchantId
	 */
    public void checkUniqueKeshId(long merchantId, long keshId) throws DuplicateRecordException;


}
