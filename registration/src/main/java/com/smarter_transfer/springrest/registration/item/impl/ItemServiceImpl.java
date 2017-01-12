package com.smarter_transfer.springrest.registration.item.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smarter_transfer.springrest.registration.item.ItemService;
import com.smarter_transfer.springrest.registration.item.model.Item;
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
		try {
		sessionFactory.getCurrentSession().save(item);
    	long itemIndex = (long) sessionFactory.getCurrentSession().createCriteria(Item.class).add(Restrictions.eq("itemPK.merchant.merchantId", item.getItemPK().getMerchant().getMerchantId())).setProjection(Projections.rowCount()).uniqueResult();
    	long itemId = (long) ((Item)sessionFactory.getCurrentSession().createCriteria(Item.class).add(Restrictions.eq("itemPK.merchant.merchantId", item.getItemPK().getMerchant().getMerchantId())).list().get((int) itemIndex-1)).getItemPK().getItemId();
    	item.getItemPK().setItemId(itemId);
    	System.out.println("itemIndex: " + itemIndex);
    	System.out.println("itemId: " + itemId);
    	System.out.println("Added new item: {}" +item.getItemPK().toString());
		}
    	catch(Exception e){
        	System.err.println("ERROR CATCHED " + e.getMessage());
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Added new item: {}", item.getItemPK().toString());
        } 
	}

	@Override
	public void updateItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Item> getItems(long merchantId, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(long merchantId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteItem(long merchantId, long itemId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item getItem(long merchantId, long itemId) {
		// TODO Auto-generated method stub
		return null;
	}


}
