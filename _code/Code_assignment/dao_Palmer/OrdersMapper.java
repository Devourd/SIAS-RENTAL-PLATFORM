package com.ldu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ldu.pojo.Orders;
public interface OrdersMapper {
	/**
	 * Query the user's order to buy
	 * @param user_id
	 * @return
	 */
	public List<Orders> selectOrdersByUserId(Integer user_id);
	
	/**
	 * Query user's order
	 * @param user_id
	 * @return
	 */
	
	public List<Orders> selectOrdersByUserAndGoods(Integer user_id);
	
	/**
	 * Add order
	 * @param orders
	 */

	public void addOrders(Orders orders);

	/**
	 *Ship
	 * @param orders
	 */
	public void deliverByOrderNum(Integer orderNum);

	/**
	 * Receipt
	 * @param orders
	 */
	public void receiptByOrderNum(Integer orderNum);
	
	/**
	 * Query all orders
	 * @return
	 */

	public List<Orders> getOrdersList();
	
	/**
	 * Get based on id
	 * @param ordersId
	 * @return
	 */
	public Orders selectById(int id);

	public void updateByPrimaryKey(Orders orders);

	public void deleteByPrimaryKeys(int id);

	public List<Orders> getPageOrdersByOrders(@Param("orderNum")Long orderNum,@Param("orderInformation") String orderInformation,@Param("orderState") Integer orderState);

}
