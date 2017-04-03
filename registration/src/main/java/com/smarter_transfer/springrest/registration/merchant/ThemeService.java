package com.smarter_transfer.springrest.registration.merchant;

import common.app.model.merchant.Theme;

/**
 * This Service provides the necessary functionality to manage themes
 * @author kaikun
 *
 */

public interface ThemeService {
	/** Adds the theme to the system.
	 * 
	 * @param theme the theme to add
	 * 
	 * @throws IllegalArgumentException if the theme already exists
	 */
	public void addTheme(Theme theme);
	
	/** Updates the existing theme in the system.
	 * 
	 * @param theme the theme to update
	 */
	public void updateTheme(Theme theme);
	
	/**
     * Deletes the theme with the given identifier from the System.
     *
     * @param themeId : ID of the theme
     *
     * @return the theme with the given {@code themeId}
     *
     * @throws common.app.error.RecordNotFoundException if the theme does not exist
     */
	public void deleteTheme(long themeId);
	
	/**
     * Answers the theme with the given identifier.
     *
     * @param themeId : ID of the theme
     *
     * @return the theme with the given {@code themeId}
     *
     * @throws common.app.error.RecordNotFoundException if the theme does not exist
     */
	public Theme getTheme(long themeId);

}
