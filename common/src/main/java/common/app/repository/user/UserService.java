package common.app.repository.user;

import java.util.List;

import common.app.error.DuplicateRecordException;
import common.app.model.user.User;

/**
 * This Service provides the necessary functionality to manage users
 * @author kaikun
 *
 */
public interface UserService {
	/**
     * Adds a user to the system.
     *
     * @param user the user to add
     * 
     * @throws common.app.error.DuplicateRecordException if the keshId already exists
     */
	public void addUser(User user);
	
	/** Updates the existing user in the system.
     * 
     * @param user the user to update
     * 
	 * @throws common.app.error.DuplicateRecordException if the keshId already exists
     */
	public void updateUser(User user);
	
	/**
     * Deletes the user with the given identifier from the System.
     *
     * @param userId : ID of the User
     *
     * @return the user with the given {@code keshId}
     *
     * @throws common.app.error.RecordNotFoundException if the user does not exist
     * @throws IllegalArgumentException if the userId is invalid
     */
	public void deleteUser(long userId);
	
	/**
     * Answers the user with the given identifier.
     *
     * @param userId : ID of the User
     *
     * @return the user with the given {@code keshId}
     *
     * @throws common.app.error.RecordNotFoundException if the user does not exists
     * @throws IllegalArgumentException if the userId is invalid
     */
	public User getUser(long userId);
	/** Answers a list with all users in the system
	 * 
	 * @return List of all users in the system
	 */
	public List<User> getUsers();
	/** Returns the total amount of users in the system
	 * 
	 * @return total amount of users in the system
	 */
	public long count();
	/** Check if there is a Violation of unique constrains on keshId
	 * 
	 * @param userId userId of the user with the keshId
	 * @param keshId KeshId of the user to insert/update
	 * @throws DuplicateRecordException if there is already a duplicate keshId which does not belong to the same userId
	 */
    public void checkUniqueKeshId(long userId, String keshId) throws DuplicateRecordException;

}
