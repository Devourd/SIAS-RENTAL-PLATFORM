package com.ldu.controller;

import com.ldu.pojo.Admin;
import com.ldu.pojo.Goods;
import com.ldu.pojo.Orders;
import com.ldu.pojo.Purse;
import com.ldu.pojo.User;
import com.ldu.service.AdminService;
import com.ldu.service.GoodsService;
import com.ldu.service.OrdersService;
import com.ldu.service.PurseService;
import com.ldu.service.UserService;
import com.ldu.util.GoodsGrid;
import com.ldu.util.OrdersGrid;
import com.ldu.util.PurseGrid;
import com.ldu.util.UserGrid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Resource
	private UserService userService;

	@Resource
	private GoodsService goodsService;

	@Resource
	private OrdersService ordersService;

	@Resource
	private PurseService purseService;

	@Resource
	private AdminService adminService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String login(HttpSession session) {
		// Clear session
		//session.invalidate();
		return "/admin/login";
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String index(HttpServletRequest request, Admin admins) {
		Admin myadmin = adminService.findAdmin(admins.getPhone(), admins.getPassword());
		if (myadmin != null) {
			request.getSession().setAttribute("admin", myadmin);
			return "/admin/index";
		}
		return "/admin/login";

	}

	@RequestMapping(value = "/indexs")
	public String indexs(HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		if (admin != null) {
			Integer id = admin.getId();
			Admin myadmin = adminService.findAdminById(id);
			request.getSession().setAttribute("admin", myadmin);
			return "/admin/index";
		}
		return "/admin/login";

	}
	
	
	/*Personal information*/
	@RequestMapping(value = "/info")
	@ResponseBody
	public ModelAndView getInfo(HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("admin", admin);
		modelAndView.setViewName("admin/info");
		return modelAndView;
	}
	
	/* Personal password */
	@RequestMapping(value = "/modify")
	@ResponseBody
	public ModelAndView getModify(HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("admin", admin);
		modelAndView.setViewName("admin/modify");
		return modelAndView;
	}
	/* change Password*/
	@RequestMapping(value = "/changePassword")
	@ResponseBody
	public ModelAndView changePassword(HttpServletRequest request,Admin admin) {
		String pwd=request.getParameter("password1");
		ModelAndView modelAndView = new ModelAndView();
		Admin admins = (Admin) request.getSession().getAttribute("admin");
		
		if(admin.getPassword().equals(admins.getPassword())) {
			admins.setPassword(pwd);
			adminService.updateAdmin(admins);
		}else {
			modelAndView.addObject("msg", "The original password is incorrect. Please re-enter！");
			modelAndView.setViewName("admin/modify");
			return modelAndView;
		}
		modelAndView.setViewName("admin/login");
		return modelAndView;
	}

	/*********************************************************
	 * User Management 1. Find all users 2. View users 3. Modify users 4. Delete users 5. Query users
	 * 
	 **********************************************************/

	/*Find all users */
	@RequestMapping(value = "/userList")
	@ResponseBody
	public ModelAndView getUserList(@RequestParam("pageNum") int pageNum) {
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int total = userService.getUserNum();
		List<User> rows = userService.getPageUser(pageNum, pageSize);
		UserGrid userGrid = new UserGrid();
		userGrid.setCurrent(pageNum);
		userGrid.setRowCount(pageSize);
		userGrid.setRows(rows);
		userGrid.setTotal(total);
		modelAndView.addObject("userGrid", userGrid);
		modelAndView.setViewName("admin/user/user_list");
		return modelAndView;
	}

	/* View user */
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public User getUser(HttpServletRequest request) {
		String id = request.getParameter("id");
		User user = userService.getUserById(Integer.parseInt(id));
		return user;
	}

	/* Modify user */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(HttpServletRequest request, User user) {
		User users = userService.selectByPrimaryKey(user.getId());
		user.setPassword(users.getPassword());
		try {
			userService.updateUserName(user);
		} catch (Exception e) {
			return "{\"success\":false,\"msg\":\"Save failed!\"}";
		}
		return "{\"success\":true,\"msg\":\"	Successfully saved!\"}";
	}

	/* delete users */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(HttpServletRequest request, @RequestParam(value = "ids[]") String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				userService.deleteUserById(ids[i]);
			}
		} catch (Exception e) {
			return "{\"success\":false,\"msg\":\"failed to delete!\"}";
		}
		return "{\"success\":true,\"msg\":\"successfully deleted!\"}";
	}

	/* Query user */
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView searchUser(HttpServletRequest request, User user) {
		ModelAndView mv = new ModelAndView();
		int pageNum = 1;
		int pageSize = 10;
		int total = userService.getUserNum();
		String phone = user.getPhone();
		String username = user.getUsername();
		String qq = user.getQq();
		List<User> rows = userService.getPageUserByUser(phone, username, qq, pageNum, pageSize);
		UserGrid userGrid = new UserGrid();
		User searchuser = new User();
		searchuser.setPhone(phone);
		searchuser.setUsername(username);
		searchuser.setQq(qq);
		userGrid.setCurrent(pageNum);
		userGrid.setRowCount(pageSize);
		userGrid.setRows(rows);
		userGrid.setTotal(total);
		mv.addObject("userGrid", userGrid);
		mv.addObject("searchuser", searchuser);
		mv.setViewName("admin/user/user_list");
		return mv;
	}

	/*********************************************************
	 * Product Management 1. Find all items 2. View items 3. Modify items 4. Delete items 5. Query items lyq
	 * 
	 **********************************************************/

	/* Find all products */
	@RequestMapping(value = "/goodsList")
	@ResponseBody
	public ModelAndView getGoodsList(@RequestParam("pageNum") int pageNum) {
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int total = goodsService.getGoodsNum();
		List<Goods> rows = goodsService.getPageGoods(pageNum, pageSize);
		GoodsGrid goodsGrid = new GoodsGrid();
		goodsGrid.setCurrent(pageNum);
		goodsGrid.setRowCount(pageSize);
		goodsGrid.setRows(rows);
		goodsGrid.setTotal(total);
		modelAndView.addObject("goodsGrid", goodsGrid);
		modelAndView.setViewName("admin/goods/goods_list");
		return modelAndView;
	}

	/* View product */
	@RequestMapping(value = "/getGoods")
	@ResponseBody
	public Goods getGoods(HttpServletRequest request) {
		String id = request.getParameter("id");
		Goods goods = goodsService.getGoodsById(Integer.parseInt(id));
		return goods;
	}

	/*Modify product */
	@RequestMapping(value = "/updateGoods", method = RequestMethod.POST)
	@ResponseBody
	public String updateGoods(HttpServletRequest request, Goods goods) {
		int id = goods.getId();
		Goods oldGoods = goodsService.getGoodsById(id);
		goods.setUserId(oldGoods.getUserId());
		goods.setPolishTime(oldGoods.getPolishTime());
		goods.setEndTime(oldGoods.getEndTime());
		try {
			goodsService.updateGoodsByPrimaryKeyWithBLOBs(goods.getId(), goods);
		} catch (Exception e) {
			return "{\"success\":false,\"msg\":\"Save failed!\"}";
		}
		return "{\"success\":true,\"msg\":\"Successfully saved!\"}";
	}

	/* Delete item */
	@RequestMapping(value = "/deleteGoods", method = RequestMethod.POST)
	@ResponseBody
	public String deleteGoods(HttpServletRequest request, @RequestParam(value = "ids[]") String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				goodsService.deleteGoodsByPrimaryKeys(Integer.parseInt(ids[i]));
			}
		} catch (Exception e) {
			return "{\"success\":false,\"msg\":\"failed to delete!\"}";
		}
		return "{\"success\":true,\"msg\":\"successfully deleted!\"}";
	}

	/* Query product */
	@RequestMapping(value = "/searchGoods", method = RequestMethod.POST)
	public ModelAndView searchGoods(HttpServletRequest request, Goods goods) {
		ModelAndView mv = new ModelAndView();
		int pageNum = 1;
		int pageSize = 10;
		int total = goodsService.getGoodsNum();
		Integer id = goods.getId();
		String name = goods.getName();
		Integer status = goods.getStatus();
		List<Goods> rows = goodsService.getPageGoodsByGoods(id, name, status, pageNum, pageSize);
		GoodsGrid goodsGrid = new GoodsGrid();
		Goods searchgoods = new Goods();
		searchgoods.setId(id);
		searchgoods.setName(name);
		searchgoods.setStatus(status);
		goodsGrid.setCurrent(pageNum);
		goodsGrid.setRowCount(pageSize);
		goodsGrid.setRows(rows);
		goodsGrid.setTotal(total);
		mv.addObject("goodsGrid", goodsGrid);
		mv.addObject("searchgoods", searchgoods);
		mv.setViewName("admin/goods/goods_list");
		return mv;
	}

	/*********************************************************
	 * Order Management 1. Find all orders 2. View orders 3. Modify orders 4. Delete orders 5. Query orders lyq
	 * 
	 **********************************************************/

	/* Find all orders */
	@RequestMapping(value = "/ordersList")
	@ResponseBody
	public ModelAndView getOrdersList(@RequestParam("pageNum") int pageNum) {
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int total = ordersService.getOrdersNum();
		List<Orders> rows = ordersService.getPageOrders(pageNum, pageSize);
		OrdersGrid ordersGrid = new OrdersGrid();
		ordersGrid.setCurrent(pageNum);
		ordersGrid.setRowCount(pageSize);
		ordersGrid.setRows(rows);
		ordersGrid.setTotal(total);
		modelAndView.addObject("ordersGrid", ordersGrid);
		modelAndView.setViewName("admin/orders/orders_list");
		return modelAndView;
	}

	/* check order */
	@RequestMapping(value = "/getOrders")
	@ResponseBody
	public Orders getOrders(HttpServletRequest request) {
		String id = request.getParameter("id");
		Orders orders = ordersService.getOrdersById(Integer.parseInt(id));
		return orders;
	}

	/* Change Order */
	@RequestMapping(value = "/updateOrders", method = RequestMethod.POST)
	@ResponseBody
	public String updateOrders(HttpServletRequest request, Orders orders) {
		int id = orders.getId();
		Orders oldorders = ordersService.getOrdersById(id);
		orders.setGoodsId(oldorders.getGoodsId());
		orders.setUserId(oldorders.getUserId());
		Goods goods=goodsService.getGoodsById(oldorders.getGoods().getId());
		if (oldorders.getOrderState() != orders.getOrderState()) {
			Float balance = orders.getOrderPrice();
			if (orders.getOrderState() == 3) {
				purseService.updatePurseByuserId(goods.getUserId(), balance);
			} else {
				purseService.updatePurseOfdel(goods.getUserId(), balance);
			}
		}
		try {
			ordersService.updateByPrimaryKey(id, orders);
		} catch (Exception e) {
			return "{\"success\":false,\"msg\":\"Save failed!\"}";
		}
		return "{\"success\":true,\"msg\":\"Successfully saved!\"}";
	}

	/*Delete order */
	@RequestMapping(value = "/deleteOrders", method = RequestMethod.POST)
	@ResponseBody
	public String deleteOrders(HttpServletRequest request, @RequestParam(value = "ids[]") String[] ids) {
		try {
			for (int i = 0; i < ids.length; i++) {
				ordersService.deleteOrdersByPrimaryKeys(Integer.parseInt(ids[i]));
			}
		} catch (Exception e) {
			return "{\"success\":false,\"msg\":\"failed to delete!\"}";
		}
		return "{\"success\":true,\"msg\":\"successfully deleted!\"}";
	}
	
	/*checking order */
	@RequestMapping(value = "/searchOrders", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView searchOrders(HttpServletRequest request, Orders orders) {
		ModelAndView mv = new ModelAndView();
		int pageNum = 1;
		int pageSize = 10;
		int total = ordersService.getOrdersNum();
		Long orderNum = orders.getOrderNum();
		String orderInformation = orders.getOrderInformation();
		Integer orderState = orders.getOrderState();
		List<Orders> rows = ordersService.getPageOrdersByOrders(orderNum, orderInformation, orderState, pageNum, pageSize);
		OrdersGrid ordersGrid = new OrdersGrid();
		Orders searchorders = new Orders();
		searchorders.setOrderNum(orderNum);
		searchorders.setOrderInformation(orderInformation);
		searchorders.setOrderState(orderState);
		ordersGrid.setCurrent(pageNum);
		ordersGrid.setRowCount(pageSize);
		ordersGrid.setRows(rows);
		ordersGrid.setTotal(total);
		mv.addObject("ordersGrid", ordersGrid);
		mv.addObject("searchorders", searchorders);
		mv.setViewName("admin/orders/orders_list");
		return mv;
	}
	
	
	/*********************************************************
	 * Wallet Management 1. Find all wallets 2. View wallet 3. Modify wallet 4. Delete wallet 5. Query wallet lyq
	 * 
	 **********************************************************/

	/*Find all wallets */
	@RequestMapping(value = "/purseList")
	@ResponseBody
	public ModelAndView getPurseList(HttpServletRequest request) {
		int pageNum=Integer.parseInt(request.getParameter("pageNum"));
		ModelAndView modelAndView = new ModelAndView();
		int pageSize = 10;
		int total = purseService.getPurseNum();
		List<Purse> rows = purseService.getPagePurse(pageNum, pageSize);
		PurseGrid purseGrid = new PurseGrid();
		purseGrid.setCurrent(pageNum);
		purseGrid.setRowCount(pageSize);
		purseGrid.setRows(rows);
		purseGrid.setTotal(total);
		modelAndView.addObject("purseGrid", purseGrid);
		modelAndView.setViewName("admin/purse/purse_list");
		return modelAndView;
	}

	/* Query wallet */
	@RequestMapping(value = "/searchPurse", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView searchPurse(HttpServletRequest request, Purse purse) {
		ModelAndView mv = new ModelAndView();
		int pageNum = 1;
		int pageSize = 10;
		int total = purseService.getPurseNum();
		Integer userId = purse.getUserId();
		Integer state=purse.getState();
		List<Purse> rows = purseService.getPagePurseByPurse(userId,state, pageNum, pageSize);
		PurseGrid purseGrid = new PurseGrid();
		Purse searchpurse = new Purse();
		searchpurse.setUserId(userId);
		searchpurse.setState(state);
		purseGrid.setCurrent(pageNum);
		purseGrid.setRowCount(pageSize);
		purseGrid.setRows(rows);
		purseGrid.setTotal(total);
		mv.addObject("purseGrid", purseGrid);
		mv.addObject("searchpurse", searchpurse);
		mv.setViewName("admin/purse/purse_list");
		return mv;
	}
	
	/* check order */
	@RequestMapping(value = "/getPurse")
	@ResponseBody
	public Purse getPurse(HttpServletRequest request) {
		String id = request.getParameter("id");
		Purse purse = purseService.getPurseById(Integer.parseInt(id));
		return purse;
	}
	
	/* Modify wallet*/
//	@RequestMapping(value = "/updatePurse", method = RequestMethod.POST)
//	@ResponseBody
//	public String updatePurse(HttpServletRequest request, Purse purse) {
//		int id = purse.getId();
//		try {
//			purseService.updateByPrimaryKey(id, purse);
//		} catch (Exception e) {
//			return "{\"success\":false,\"msg\":\"Save failed!\"}";
//		}
//		return "{\"success\":true,\"msg\":\"Successfully saved!\"}";
//	}
	
	/* passing */
	@RequestMapping(value = "/updatePursePass", method = RequestMethod.POST)
	@ResponseBody
	public String updatePursePass(HttpServletRequest request, Purse purse) {
		Float balance=purse.getBalance();
		purse.setState(2);
		try {
			if(purse.getRecharge()!=null){//Recharge Recharge amount = null Current amount = current amount + recharge amount
				Float recharge=purse.getRecharge();
				Float balanceRecharge=balance+recharge;
				purse.setBalance(balanceRecharge);
				purseService.updatePursePassById(purse.getId(),purse);
			}if(purse.getWithdrawals()!=null) {//withdraw
				Float withdrawals=purse.getWithdrawals();
				Float balanceWithdrawals=balance-withdrawals;
				purse.setBalance(balanceWithdrawals);
				purseService.updatePurseRefuseById(purse.getId(),purse);
			}	
		} 
		catch (Exception e) {
			return "{\"success\":true,\"msg\":\"The audit failed, please check the amount!\"}";
		}
		return "{\"success\":true,\"msg\":\"Successful review!\"}";
	}
	
	/* Fail */
	@RequestMapping(value = "/updatePurseRefuse", method = RequestMethod.POST)
	@ResponseBody
	public String updatePurseRefuse(HttpServletRequest request, Purse purse) {
		purse.setState(1);
		try {
			
		 purseService.updatePurseRefuseById(purse.getId(),purse);
				
		} 
		catch (Exception e) {
			return "{\"success\":true,\"msg\":\"Audit failure!\"}";
		}
		return "{\"success\":true,\"msg\":\"Successful review!\"}";
	}
	
	/* User view audit results*/
	@RequestMapping(value = "/updatePurseState", method = RequestMethod.GET)
	public void updatePurseState(HttpServletRequest request) {
		 Integer id=Integer.parseInt(request.getParameter("id"));
		 Purse purse= purseService.getPurseById(id);
		 purse.setState(null);	
		 this.purseService.updateByPrimaryKey(id,purse);//Modify state to null
	}
	
	
}