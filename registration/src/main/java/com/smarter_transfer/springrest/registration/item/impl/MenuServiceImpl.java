package com.smarter_transfer.springrest.registration.item.impl;

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

import com.smarter_transfer.springrest.registration.item.MenuService;

import common.app.error.RecordNotFoundException;
import common.app.model.item.Menu;
import common.app.model.merchant.PointOfSale;

/**
 * {@link MenuService} implementation.
 * @author kaikun
 *
 */
@Component
@Transactional
public class MenuServiceImpl implements MenuService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

	@Override
	public void addMenu(Menu menu) {
		sessionFactory.getCurrentSession().save(menu);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Added new Menu: {}", menu.toString());
        } 
	}

	@Override
	public void updateMenu(Menu menu) {
		sessionFactory.getCurrentSession().update(menu);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updated Menu: {}", menu.toString());
        } 
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteMenu(long merchantId, long menuId) {
		if (merchantId <= 0) {
			  throw new IllegalArgumentException("The merchantId must be greater than zero");
		  }
		else if (menuId <= 0){
			throw new IllegalArgumentException("The menuId must be greater than zero");
		}
		long referencingPOS = (long) sessionFactory.getCurrentSession().createCriteria(PointOfSale.class).add(Restrictions.eq("menuId", menuId)).setProjection(Projections.rowCount()).uniqueResult();
		if (referencingPOS > 0) {
			/* give a list of the posIds which reference this menu */
			List<Long> posIds = (List<Long>) sessionFactory.getCurrentSession().createCriteria(PointOfSale.class).add(Restrictions.eq("menuId", menuId)).setProjection(Projections.distinct(Projections.property("posId"))).list();
			StringBuilder ids = new StringBuilder();
			for (Long l : posIds) ids.append(l + ";");
			throw new IllegalArgumentException("You can not delete a menu as long as a POS is still referencing it. Id's of referencing POS: " + ids.toString());
		}
		Menu menu = getMenu(merchantId, menuId);
		sessionFactory.getCurrentSession().delete(menu);
		if (LOGGER.isInfoEnabled()) {
          LOGGER.info("Deleted menu: {}", menu.toString());
      }
	}

	@Override
	public Menu getMenu(long merchantId, long menuId) {
		if (merchantId <= 0) {
			throw new IllegalArgumentException("The merchantId must be greater than zero");
		} else if (menuId <= 0) {
			throw new IllegalArgumentException("The menuId must be greater than zero");
		}
		Menu menu = sessionFactory.getCurrentSession().get(Menu.class, menuId);
		if (menu == null) {
			throw new RecordNotFoundException("No menu: [merchantId =" + merchantId + " ; menuId = " + menuId + "]");
		}
		if (menu.getMerchant().getMerchantId() != merchantId) {
			throw new IllegalArgumentException("The merchantId: " + merchantId + " is not the owner of the menu.");

		}
		return menu;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getMenus(long merchantId, int start, int limit) {
		return sessionFactory.getCurrentSession()
		        .createCriteria(Menu.class)
		        .add(Restrictions.eq("merchant.merchantId", merchantId))
		        .setFirstResult(start)
		        .setMaxResults(limit)
		        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		        .list();
	}

	@Override
	public long count(long merchantId) {
		return (long) sessionFactory.getCurrentSession().createCriteria(Menu.class).add(Restrictions.eq("merchant.merchantId", merchantId)).setProjection(Projections.rowCount()).uniqueResult();
	}

}
