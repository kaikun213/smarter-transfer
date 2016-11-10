package com.smarter_transfer.springrest.registration.user.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smarter_transfer.springrest.registration.user.UserService;
import com.smarter_transfer.springrest.registration.user.model.User;

import common.app.error.DuplicateRecordException;
import common.app.error.RecordNotFoundException;
/**
 * {@link UserService} implementation.
 * @author kaikun
 *
 */
@Component
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

	
	public void addUser(User user) {
	    sessionFactory.getCurrentSession().save(user);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Added new user: {}", user.getName());
        }
	}

	
	public void updateUser(User user) {
	    sessionFactory.getCurrentSession().update(user);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updated user: {}", user.getName());
        }
	}

    @Transactional(readOnly = true)
	public User getUser(long userId) {
		  if (userId <= 0) {
			  throw new IllegalArgumentException("The userId must be greater than zero");
		  }
	      User user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
	      if (user == null || user.getIsDeleted()) {
	    	  throw new RecordNotFoundException("No user with User-ID " + userId);
	      }
	      return user;
	}

	public void deleteUser(long userId) {
		  if (userId <= 0) {
			  throw new IllegalArgumentException("The userId must be greater than zero");
		  }
	      User user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
	      if (user == null || user.getIsDeleted()) {
	    	  throw new RecordNotFoundException("No user with User-ID " + userId);
	      }
	      user.setIsDeleted(true);
	      sessionFactory.getCurrentSession().update(user);
	      if (LOGGER.isInfoEnabled()) {
	         LOGGER.info("Deleted user: {}", user.getName());
	      }
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return sessionFactory.getCurrentSession()
        .createCriteria(User.class)
        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
        .list();
	}
	
	public long count(){
    	return (long) sessionFactory.getCurrentSession().createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
    }
	
    public void checkUniqueKeshId(long userId, long keshId) throws Exception{
    	/* Test if user with equal keshId already exists */
		@SuppressWarnings("unchecked")
		List<Long> eqKeshId =  sessionFactory.getCurrentSession()
						        .createCriteria(User.class)
						        .add(Restrictions.eq("keshId", keshId))
						        .setProjection(Projections.distinct(Projections.property("userId")))
						        .setResultTransformer(Criteria.PROJECTION)
						        .list();
		if (!eqKeshId.isEmpty() && eqKeshId.get(0) != userId){
		throw new DuplicateRecordException("The user can not get updated. Duplicate keshId error.");
		}
    }


}
