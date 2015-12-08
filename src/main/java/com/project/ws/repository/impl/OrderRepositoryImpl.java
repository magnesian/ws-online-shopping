package com.project.ws.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.Order;
import com.project.ws.repository.custom.OrderCustomRepository;

@Repository
@EnableAutoConfiguration
public class OrderRepositoryImpl implements OrderCustomRepository {

	private Double orderAmount = 0.00;

	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public Integer addOrder(Order order) {
		Integer count = 0;
		String SQL = "INSERT INTO order_details (cust_id, cust_bill_id, cust_addr_id, total_order_amt) VALUES(" +
				order.getCustomerId() + ", " + order.getBillId() + ", " + order.getAddrId() + ", " + order.getOrderAmount() + ")";
		try {
			count = em.createNativeQuery(SQL).executeUpdate();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(SQL);
		return count;
	}
	

	@Override
	public List<Order> findAllOrders(Integer customerId) {
		String SQL = "select o from Order o where cust_id = " + customerId;
		TypedQuery<Order> query = em.createQuery(SQL, Order.class);
		List<Order> resultList = query.getResultList();
		return resultList;
	}
	
	@Override
	public List<Order> findAllOrdersForVendor(Integer vendorId) {
		String SQL = "select distinct o.* from order_details o, order_line_item ol, product p where o.order_id = ol.order_id"
				+ " and ol.product_id = p.product_id and p.vendor_id = " + vendorId;
		Query query = em.createNativeQuery(SQL, Order.class);
		List<Order> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public Integer findLatestOrder(Integer customerId) {
		//select the order just created
		String SQL = "select max(o.orderId) from Order o where status_cd = 'ACT' and cust_id = " + customerId;
		Integer orderId = em.createQuery(SQL, Integer.class).getSingleResult();
		return orderId;
	}

	@Override
	public List<Order> findAllActiveOrders(Integer customerId) {
		String SQL = "select o from Order o where status_cd = 'ACT' and cust_id = " + customerId;
		TypedQuery<Order> query = em.createQuery(SQL, Order.class);
		List<Order> resultList = query.getResultList();
		
		return resultList;
	}

	@Override
	public Order findOrder(Integer orderId) {
		String SQL = "select o from Order o where orderId = " + orderId;
		Order orderInfo = em.createQuery(SQL, Order.class).getSingleResult();
		return orderInfo;
	}

	@Override
	@Transactional
	public Integer updateOrderStatus(Integer orderId, String status) {
		String SQL = "update order_details set status_cd = '" + status + "' where order_id = " + orderId;
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1)
			System.out.println("order status updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
}

