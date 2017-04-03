package com.smarter_transfer.springrest.registration.merchant.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smarter_transfer.springrest.registration.merchant.ThemeService;

import common.app.error.RecordNotFoundException;
import common.app.model.merchant.Theme;
/**
 * {@link ThemeService} implementation.
 * @author kaikun
 *
 */
@Component
@Transactional
public class ThemeServiceImpl implements ThemeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ThemeServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

	
	public void addTheme(Theme theme) {
		if (theme.getThemeId() > 0) {
			throw new IllegalArgumentException("The themeId is invalid (not generated)");
		}
	    Theme existingTheme = (Theme) sessionFactory.getCurrentSession().get(Theme.class, theme.getThemeId());
	    if (existingTheme != null){
	    	throw new IllegalArgumentException("The theme can not be added.There is already a theme with the themeId:" + existingTheme.getThemeId());
	    }
        sessionFactory.getCurrentSession().save(theme);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Added new theme: {}", theme.getThemeId());
        }
	}

	
	public void updateTheme(Theme theme) {
		if (theme.getThemeId() <= 0) {
			throw new IllegalArgumentException("The themeId must be greater than zero");
		}
	    sessionFactory.getCurrentSession().update(theme);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updated theme: {}", theme.getThemeId());
        }
	}

    @Transactional(readOnly = true)
	public Theme getTheme(long themeId) {
		  if (themeId <= 0) {
			  throw new IllegalArgumentException("The themeId must be greater than zero");
		  }
	      Theme theme = (Theme) sessionFactory.getCurrentSession().get(Theme.class, themeId);
	      if (theme == null) {
	    	  throw new RecordNotFoundException("No theme with ID: " + themeId);
	      }
	      return theme;
	}

	public void deleteTheme(long themeId) {
		  if (themeId <= 0) {
			  throw new IllegalArgumentException("The themeId must be greater than zero");
		  }
	      Theme theme = (Theme) sessionFactory.getCurrentSession().get(Theme.class, themeId);
	      if (theme == null) {
	    	  throw new RecordNotFoundException("No theme with ID: " + themeId);
	      }
	      sessionFactory.getCurrentSession().delete(theme);
	      if (LOGGER.isInfoEnabled()) {
	         LOGGER.info("Deleted theme: {}", theme.getThemeId());
	      }
	}

}
