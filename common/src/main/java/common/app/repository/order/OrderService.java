//package common.app.repository.order;
//
//import java.util.List;
//
//import org.springframework.data.repository.PagingAndSortingRepository;
//
//import common.app.model.merchant.Merchant;
//import common.app.model.order.Order;
//import common.app.model.user.User;
//
///**
// * This Service provides the necessary functionality to add finalized transactions
// * @author kaikun
// *
// */
//public interface OrderService extends PagingAndSortingRepository<Order,Long>{
//	
//	List<Order> findByUser(User user);
//	List<Order> findByMerchant(Merchant merchant);
//
//}
