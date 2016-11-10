package com.smarter_transfer.springrest.registration.merchant.impl;

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

import com.smarter_transfer.springrest.registration.merchant.MerchantService;
import com.smarter_transfer.springrest.registration.merchant.impl.MerchantServiceImpl;
import com.smarter_transfer.springrest.registration.merchant.model.Merchant;

import common.app.error.DuplicateRecordException;
import common.app.error.RecordNotFoundException;

/**
 * {@link MerchantService} implementation.
 * @author kaikun
 *
 */
@Component
@Transactional
public class MerchantServiceImpl implements MerchantService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

	
	public void addMerchant(Merchant merchant) {
        sessionFactory.getCurrentSession().save(merchant);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Added new merchant: {}", merchant.getCompanyName());
        }
	}

	
	public void updateMerchant(Merchant merchant) {
		sessionFactory.getCurrentSession().update(merchant);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updated merchant: {}", merchant.getCompanyName());
        }
	}

    @Transactional(readOnly = true)
	public Merchant getMerchant(long merchantId) {
		  if (merchantId <= 0) {
			  throw new IllegalArgumentException("The merchantId must be greater than zero");
		  }
	      Merchant merchant = (Merchant) sessionFactory.getCurrentSession().get(Merchant.class, merchantId);
	      if (merchant == null || merchant.getIsDeleted()) {
	    	  throw new RecordNotFoundException("No merchant with Merchant-ID " + merchantId);
	      }
	      return merchant;
	}

	public void deleteMerchant(long merchantId) {
		  if (merchantId <= 0) {
			  throw new IllegalArgumentException("The merchantId must be greater than zero");
		  }
	      Merchant merchant = (Merchant) sessionFactory.getCurrentSession().get(Merchant.class, merchantId);
	      if (merchant == null || merchant.getIsDeleted()) {
	    	  throw new RecordNotFoundException("No merchant with Merchant-ID " + merchantId);
	      }
	      merchant.setIsDeleted(true);
	      sessionFactory.getCurrentSession().update(merchant);
	      if (LOGGER.isInfoEnabled()) {
	         LOGGER.info("Deleted merchant: {}", merchant.getCompanyName());
	      }
	}

    @SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Merchant> getMerchants(int start, int limit) {
		return sessionFactory.getCurrentSession()
        .createCriteria(Merchant.class)
        .setFirstResult(start)
        .setMaxResults(limit)
        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
        .list();
	}
    
    public long count(){
    	return (long) sessionFactory.getCurrentSession().createCriteria(Merchant.class).setProjection(Projections.rowCount()).uniqueResult();
    }
    
    public void checkUniqueKeshId(long merchantId, long keshId) throws Exception{
    	/* Test if merchant with equal keshId already exists */
		@SuppressWarnings("unchecked")
		List<Long> eqKeshId =  sessionFactory.getCurrentSession()
						            .createCriteria(Merchant.class)
						            .add(Restrictions.eq("keshId", keshId))
						            .setProjection(Projections.distinct(Projections.property("merchantId")))			
						            .setResultTransformer(Criteria.PROJECTION)
						            .list();
		if (!eqKeshId.isEmpty()){
			// check if the existing merchant is the same as the updated one
			if (eqKeshId.get(0) != merchantId)
	    	throw new DuplicateRecordException("The merchant can not get updated. Duplicate keshId error.");
		}
    }

}
