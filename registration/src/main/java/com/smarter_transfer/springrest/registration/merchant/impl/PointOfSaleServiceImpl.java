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

import com.smarter_transfer.springrest.registration.merchant.PointOfSaleService;

import common.app.error.RecordNotFoundException;
import common.app.model.merchant.PointOfSale;


/**
 * {@link PointOfSaleService} implementation.
 * @author kaikun
 *
 */
@Component
@Transactional
public class PointOfSaleServiceImpl implements PointOfSaleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PointOfSaleServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;


	@Override
	public void addPOS(PointOfSale pos) {
		sessionFactory.getCurrentSession().save(pos);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Added new POS: {}", pos.toString());
        }
	}

	@Override
	public void updatePOS(PointOfSale pos) {
		sessionFactory.getCurrentSession().update(pos);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updated POS: {}", pos.toString());
        }
	}

	@Override
	public void deletePOS(long merchantId, long posId) {
		if (merchantId <= 0) {
			throw new IllegalArgumentException("The merchantId must be greater than zero");
		}
		if (posId <= 0) {
			throw new IllegalArgumentException("The posId must be greater than zero");
		}
		PointOfSale pos = getPOS(merchantId, posId);
		if (pos == null || pos.getIsDeleted()) {
			throw new RecordNotFoundException("No PointOfSale with POS-ID " + posId);
		}
		pos.setIsDeleted(true);
		sessionFactory.getCurrentSession().update(pos);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Deleted POS: {}", pos.toString());
		}
	}

	@Override
	public PointOfSale getPOS(long merchantId, long posId) {
		if (merchantId <= 0) {
			  throw new IllegalArgumentException("The merchantId must be greater than zero");
		  }
		if (posId <= 0) {
			throw new IllegalArgumentException("The posId must be greater than zero");
		}
		PointOfSale pos = (PointOfSale) sessionFactory.getCurrentSession().get(PointOfSale.class, posId);
		if (pos == null || pos.getIsDeleted()) {
			throw new RecordNotFoundException("No PointOfSale with POS-ID " + posId);
		}
		if (pos.getMerchant().getMerchantId() != merchantId){
			  throw new IllegalArgumentException("The merchantId: " + merchantId + " is not the owner of the item.");

	      }
		return pos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PointOfSale> getPOSs(long merchantId, int start, int limit) {
		return sessionFactory.getCurrentSession()
		        .createCriteria(PointOfSale.class)
		        .add(Restrictions.eq("merchant.merchantId", merchantId))
		        .setFirstResult(start)
		        .setMaxResults(limit)
		        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		        .list();
	}

	@Override
	public long count(long merchantId) {
    	return (long) sessionFactory.getCurrentSession().createCriteria(PointOfSale.class).add(Restrictions.eq("merchant.merchantId", merchantId)).setProjection(Projections.rowCount()).uniqueResult();
	}

}
