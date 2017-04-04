package common.app.repository.item.impl;

import java.time.LocalDateTime;
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

import common.app.error.RecordNotFoundException;
import common.app.model.item.Item;
import common.app.model.item.ItemHistory;
import common.app.repository.item.ItemService;
/**
 * {@link ItemService} implementation.
 * @author kaikun
 *
 */
@Component
@Transactional
public class ItemServiceImpl implements ItemService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

	@Override
	public void addItem(Item item) {
		sessionFactory.getCurrentSession().save(item);
		ItemHistory itemHistory = new ItemHistory(item, 0);
		sessionFactory.getCurrentSession().save(itemHistory);
		
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Added new item: {}", item.toString());
            LOGGER.info("Added new itemHistory: {}", itemHistory.toString());
        } 
	}

	@Override
	public void updateItem(Item item) {
		/* retrieve revision number from last updated version, increment and save new version */
	    long revisionNumber = getRevisionNumber(item.getItemId(), item.getUpdated());
		sessionFactory.getCurrentSession().update(item);
		/* refresh item for new updated_at */
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().refresh(item);
		/* retrieve current created_at and updated_at to be synchronized with itemHistory */
		ItemHistory itemHistory = new ItemHistory(item, revisionNumber+1);
		sessionFactory.getCurrentSession().save(itemHistory);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updated item: {}", item.toString());
            LOGGER.info("Updated itemHistory: {}", itemHistory.toString());
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getItems(long merchantId, int start, int limit) {
		return sessionFactory.getCurrentSession()
		        .createCriteria(Item.class)
		        .add(Restrictions.eq("merchant.merchantId", merchantId))
		        .setFirstResult(start)
		        .setMaxResults(limit)
		        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		        .list();
	}

	@Override
	public long count(long merchantId) {
		return (Long) sessionFactory.getCurrentSession().createCriteria(Item.class).add(Restrictions.eq("merchant.merchantId", merchantId)).setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public void deleteItem(long merchantId, long itemId) {
		if (merchantId <= 0) {
			  throw new IllegalArgumentException("The merchantId must be greater than zero");
		  }
		else if (itemId <= 0){
			throw new IllegalArgumentException("The itemId must be greater than zero");
		}
		Item item = getItem(merchantId, itemId);
		sessionFactory.getCurrentSession().delete(item);
		if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleted item: {}", item.toString());
        }
	}

	@Override
	public Item getItem(long merchantId, long itemId) {
	  if (merchantId <= 0) {
		  throw new IllegalArgumentException("The merchantId must be greater than zero");
	  }
	  else if (itemId <= 0){
		  throw new IllegalArgumentException("The itemId must be greater than zero");
	  }
      Item item = (Item) sessionFactory.getCurrentSession().get(Item.class, itemId);
      if (item == null) {
    	  throw new RecordNotFoundException("No item: [merchantId =" + merchantId + " ; itemId = " + itemId + "]");
      }
      if (item.getMerchant().getMerchantId() != merchantId){
		  throw new IllegalArgumentException("The merchantId: " + merchantId + " is not the owner of the item.");

      }
      return item;
	}

	private long getRevisionNumber(long itemId, LocalDateTime lastUpdated) {
		long revisionNumber = (Long) sessionFactory.getCurrentSession().createCriteria(ItemHistory.class)
				  .add(Restrictions.eq("itemHistoryPK.itemId", itemId))
				  .add(Restrictions.eq("updated", lastUpdated))
				  .setProjection(Projections.distinct(Projections.property("itemHistoryPK.revisionNumber")))
				  .uniqueResult();
		return revisionNumber;
	}

	@Override
	public List<Item> getItems(long merchantId, long menuId, int start, int limit) {
		/*return sessionFactory.getCurrentSession()
		        .createCriteria(Item.class)
		        .createCriteria("menu_item", "MENU")
		        .add(Restrictions.eq("merchant.merchantId", merchantId))
		        .add(Restrictions.eq("menu.menuId", value))
		        .setFirstResult(start)
		        .setMaxResults(limit)
		        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		        .list();
		        */
		return null;
	}


}
