package com.iongroup.recruitment.financial_market;

import java.util.*;
import java.util.stream.Collectors;

public class FinancialMarketImpl implements FinancialMarket {

	public static class OrderBook {
		private static List<Order> buyOrderList;
		private static List<Order> sellOrderList;

		public OrderBook() {
			buyOrderList = new LinkedList<>();
			sellOrderList = new LinkedList<>();
		}
		public List<Order> getBuyOrderList(){
			return this.buyOrderList;
		}
		public List<Order> getSellOrderList(){
			return this.sellOrderList;
		}

	}
	private static OrderBook orderBook;
	private static Integer totalOrderId = 0;

	public FinancialMarketImpl() {
		//TODO: to be implemented
		orderBook = new OrderBook();
	}
	public Integer getTotalOrderId(){
		return this.totalOrderId;
	}
	public void setTotalOrderId(){
		totalOrderId+=1;
	}
	public OrderBook getOrderBook(){
		return this.orderBook;
	}

	/**
	 * Creates a new order and adds it to the market.
	 *
	 * @param productId The ID of the order's product
	 * @param verb      The order's verb, either {@code BUY} or {@code SELL}
	 * @param price     The order's price, i.e how much is paid/received for each unit bought/sold of this product
	 * @param quantity  The order's quantity, i.e. how many units of product are to be bought or sold
	 * @return The newly-created order's market identifier
	 */

	@Override
	public String createOrder(String productId, OrderVerb verb, double price, double quantity) {
		//String orderId, double quantity, double price, String productId, OrderVerb verb, boolean active
		//TODO: to be implemented
		String totalOrderId = getTotalOrderId().toString();
		OrderImpl orderImpl = new OrderImpl(totalOrderId,quantity,price,productId,verb, true );
		if(verb.equals(OrderVerb.BUY)) {
			this.getOrderBook().getBuyOrderList().add(orderImpl);
		} else {
			this.getOrderBook().getSellOrderList().add(orderImpl);
		}
		this.setTotalOrderId();
		return totalOrderId;
	}

	/**
	 * Retrieves an order from the market.
	 *
	 * @param orderId The ID of the order to be retrieved
	 * @return The order with the specified ID if it exists, otherwise the Optional empty value
	 */

	@Override
	public Optional<Order> getOrder(String orderId) {
		//TODO: to be implemented
		if(!(this.orderBook.getBuyOrderList().isEmpty() && this.orderBook.getSellOrderList().isEmpty())) {
			List<Order> buyOrderList = this.orderBook.getBuyOrderList();
			if(buyOrderList.size()>0) {
				Optional<Order> order = buyOrderList.stream().filter(tempOrder -> tempOrder.getId().equals(orderId)).findFirst();
				if (order.isPresent()) {
					return order;
				}
			}
			List<Order> sellOrderList = this.orderBook.getSellOrderList();
			if(sellOrderList.size()>0) {
				Optional<Order> order = sellOrderList.stream().filter(tempOrder -> tempOrder.getId().equals(orderId)).findFirst();
				if (order.isPresent()) {
					return order;
				}
			}
		}
		return Optional.empty();
	}
	// private Optional<Order> checkOrderIsPresent(List<Order> orderList){
	// 	Optional<Order> order = orderList.stream().filter(order -> order.getId.equals(orderId)).findFirst());
	// 	if (order.isPresent()) {
	// 		return order;
	// 	}
	// 	return Optional.empty();
	// }

//	private Integer findBuyOrderLevel(String orderId) {
//
//	}

	/**
	 * Changes the quantity and price of an existing order.
	 *
	 * @param orderId  The ID of the order to be modified
	 * @param newPrice The order's new price
	 * @param newQty   The order's new quantity
	 * @return {@code true} if the order was successfully modified, otherwise {@code false}.
	 */
	@Override
	public boolean modifyOrder(String orderId, double newPrice, double newQty) {
		//TODO: to be implemented
		Optional<Order> order = this.getOrder(orderId);
		//String orderId, double quantity, double price, String productId, OrderVerb verb, boolean active
		if(order.isPresent()){
			Order newOrder = new OrderImpl(orderId, newQty, newPrice, order.get().getProductId(), order.get().getVerb(), order.get().isActive());
			if(order.get().getVerb().equals(OrderVerb.BUY)) {
				for(int i=0; i< this.getOrderBook().getBuyOrderList().size(); i++) {
					if(this.getOrderBook().getBuyOrderList().get(i).getId().equals(orderId)){
						this.getOrderBook().getBuyOrderList().remove(i);
						this.getOrderBook().getBuyOrderList().add( newOrder);
						return true;
					}
				}
			} else {
				for(int i=0; i< this.getOrderBook().getSellOrderList().size(); i++) {
					if(this.getOrderBook().getSellOrderList().get(i).getId().equals(orderId)){
						this.getOrderBook().getSellOrderList().remove(i);
						this.getOrderBook().getSellOrderList().add( newOrder);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Removes an existing order from the market.
	 *
	 * @param orderId  The ID of the order to be deleted
	 * @return {@code true} if the order was successfully deleted, otherwise {@code false}.
	 */
	@Override
	public boolean deleteOrder(String orderId) {
		//TODO: to be implemented
		Optional<Order> order = this.getOrder(orderId);
		//String orderId, double quantity, double price, String productId, OrderVerb verb, boolean active
		if(order.isPresent()){
			if(order.get().getVerb().equals(OrderVerb.BUY)) {
				for(Order tempOrder: this.getOrderBook().getBuyOrderList()) {
					if(tempOrder.getId().equals(orderId)){
						this.getOrderBook().getBuyOrderList().remove(tempOrder);
						return true;
					}
				}
			} else{
				for(Order tempOrder: this.getOrderBook().getSellOrderList()) {
					if(tempOrder.getId().equals(orderId)){
						this.getOrderBook().getSellOrderList().remove(tempOrder);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Suspends an active order.
	 *
	 * @param orderId The ID of the order to be suspended
	 * @return {@code true} if the order was successfully suspended, otherwise {@code false}.
	 */

	@Override
	public boolean suspendOrder(String orderId) {
		//TODO: to be implemented
		Optional<Order> order = this.getOrder(orderId);
		//String orderId, double quantity, double price, String productId, OrderVerb verb, boolean active
		if(order.isPresent()){
			if(!order.get().isActive()){
				return false;
			}
			Order newOrder = new OrderImpl(orderId,
					order.get().getQuantity(),
					order.get().getPrice(),
					order.get().getProductId(),
					order.get().getVerb(),
					false);
			if(order.get().getVerb().equals(OrderVerb.BUY)) {
				for(int i=0; i< this.getOrderBook().getBuyOrderList().size(); i++) {
					if(this.getOrderBook().getBuyOrderList().get(i).getId().equals(orderId)){
						this.getOrderBook().getBuyOrderList().remove(i);
						this.getOrderBook().getBuyOrderList().add(newOrder);
						return true;
					}
				}
			} else {
				for(int i=0; i< this.getOrderBook().getSellOrderList().size(); i++) {
					if(this.getOrderBook().getSellOrderList().get(i).getId().equals(orderId)){
						this.getOrderBook().getSellOrderList().remove(i);
						this.getOrderBook().getSellOrderList().add(newOrder);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Activates a suspended order.
	 *
	 * @param orderId The ID of the order to be suspended
	 * @return {@code true} if the order was successfully activated, otherwise {@code false}.
	 */
	@Override
	public boolean activateOrder(String orderId) {
		//TODO: to be implemented
		Optional<Order> order = this.getOrder(orderId);
		if(order.isPresent()){
			if(order.get().isActive()){
				return false;
			}
			Order newOrder = new OrderImpl(orderId,
					order.get().getQuantity(),
					order.get().getPrice(),
					order.get().getProductId(),
					order.get().getVerb(),
					true);
			if(order.get().getVerb().equals(OrderVerb.BUY)) {
				for(int i=0; i< this.getOrderBook().getBuyOrderList().size(); i++) {
					if(this.getOrderBook().getBuyOrderList().get(i).getId().equals(orderId)){
						this.getOrderBook().getBuyOrderList().remove(i);
						this.getOrderBook().getBuyOrderList().add(newOrder);
						return true;
					}
				}
			} else {
				for(int i=0; i< this.getOrderBook().getSellOrderList().size(); i++) {
					if(this.getOrderBook().getSellOrderList().get(i).getId().equals(orderId)){
						this.getOrderBook().getSellOrderList().remove(i);
						this.getOrderBook().getSellOrderList().add(newOrder);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Retrieve all active orders at the best level for a given product and verb.
	 *
	 * <p>
	 * An order is considered to be at the best level if its price is the best price amongst active orders
	 * with the same verb. The best price depends on the order's verb:
	 * <ul>
	 *   <li>Sell orders - a lower price is better</li>
	 *   <li>Buy orders - an higher price is better</li>
	 * </ul>
	 * </p>
	 *
	 * @param productId The product ID
	 * @param verb      The verb
	 * @return A set of active orders at the best level for a given product and verb.
	 */
	@Override
	public Set<Order> getOrdersAtBestLevel(String productId, OrderVerb verb) {
		//TODO: to be implemented
		Set<Order> orderSet = new HashSet<>();
		if(!isThatProductIdExistInVerb(productId, verb)){
			return Collections.emptySet();
		}
		List<Order> orderList = new LinkedList<>();
		if(verb.equals(OrderVerb.BUY)) {
			orderList = this.getOrderBook().getBuyOrderList().stream()
					.filter(order -> order.getProductId().equals(productId) && order.isActive() != false)
					.sorted(Comparator.comparing(Order::getPrice).reversed()).collect(Collectors.toList());
			Double maxPrice = orderList.get(0).getPrice();
			for(Order order: orderList){
				if(order.getPrice() == maxPrice){
					orderSet.add(order);
				}
			}
		} else {
			orderList =this.getOrderBook().getSellOrderList().stream()
					.filter(order -> order.getProductId().equals(productId) && order.isActive() != false)
					.sorted(Comparator.comparing(Order::getPrice)).collect(Collectors.toList());
			Double minPrice = orderList.get(0).getPrice();
			for(Order order: orderList){
				if(order.getPrice() == minPrice){
					orderSet.add(order);
				}
			}
		}

		return orderSet;
	}
	private boolean isThatProductIdExistInVerb(String productId, OrderVerb verb){
		if(verb.equals(OrderVerb.BUY)) {
			Optional<Order> optionalOrder = this.getOrderBook().getBuyOrderList().stream().filter(order -> order.getProductId().equals(productId)).findFirst();
			if(optionalOrder.isPresent()){
				return true;
			}
			return false;
		} else {
			Optional<Order> optionalOrder = this.getOrderBook().getSellOrderList().stream().filter(order -> order.getProductId().equals(productId)).findFirst();
			if(optionalOrder.isPresent()){
				return true;
			}
			return false;
		}
	}
}
