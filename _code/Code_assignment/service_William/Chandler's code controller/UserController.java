package com.ldu.controller;

import com.ldu.pojo.Focus;
import com.ldu.pojo.Goods;
import com.ldu.pojo.GoodsExtend;
import com.ldu.pojo.Image;
import com.ldu.pojo.Notice;
import com.ldu.pojo.NoticeExtend;
import com.ldu.pojo.Purse;
import com.ldu.pojo.User;
import com.ldu.service.FocusService;
import com.ldu.service.GoodsService;
import com.ldu.service.ImageService;
import com.ldu.service.NoticeService;
import com.ldu.service.PurseService;
import com.ldu.service.UserService;
import com.ldu.util.DateUtil;
import com.ldu.util.MD5;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private ImageService imageService;

	@Resource
	private FocusService focusService;

	@Resource
	private PurseService purseService;
	
	@Resource
	private NoticeService noticeService;

	/**
	 * User registration
	 * 
	 * @param user1
	 * @return
	 */
	@RequestMapping(value = "/addUser")
	public String addUser(HttpServletRequest request, @ModelAttribute("user") User user1) {
		String url = request.getHeader("Referer");
		User user = userService.getUserByPhone(user1.getPhone());
		if (user == null) {// Check if the user has already registered
			String t = DateUtil.getNowDate();
			// MD5 encryption for passwords
			String str = MD5.md5(user1.getPassword());
			user1.setCreateAt(t);//Start time
			user1.setPassword(str);
			user1.setGoodsNum(0);
			user1.setStatus((byte) 1);//Initial normal state
			user1.setPower(100);
			userService.addUser(user1);
			purseService.addPurse(user1.getId());// Generate a wallet at the time of registration
		}
		return "redirect:" + url;
	}
	
	/**
	 * Registration verification account
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request){
		String phone=request.getParameter("phone");
		User user = userService.getUserByPhone(phone);
		if(user==null) {
			return "{\"success\":true,\"flag\":false}";//User exists, registration failed
		}else {
			return "{\"success\":true,\"flag\":true}";//User does not exist, you can register
		}
	}
	
	/**
	 * Login verification password
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value = "/password",method = RequestMethod.POST)
	@ResponseBody
	public String password(HttpServletRequest request){
		String phone=request.getParameter("phone");
		String password=request.getParameter("password");
		if((phone==null||phone=="")&&(password==null||password=="")) {
			return "{\"success\":false,\"flag\":true}";
		}else {
			User user = userService.getUserByPhone(phone);
			if(user==null) {
				return "{\"success\":false,\"flag\":false}";//Account error
			}
			String pwd = MD5.md5(password);
			if (pwd.equals(user.getPassword())) {
				return "{\"success\":true,\"flag\":false}";//The password is correct
			}else {
				return "{\"success\":true,\"flag\":true}";//wrong password
			}
		}
		
	}*/
	

	/**
	 * Verify login
	 * @param request
	 * @param user
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/login")
	public ModelAndView loginValidate(HttpServletRequest request, HttpServletResponse response, User user,
			ModelMap modelMap) {
		User cur_user = userService.getUserByPhone(user.getPhone());
		String url = request.getHeader("Referer");
		if (cur_user != null) {
			String pwd = MD5.md5(user.getPassword());
			if (pwd.equals(cur_user.getPassword())) {
				if(cur_user.getStatus()==1) {
				request.getSession().setAttribute("cur_user", cur_user);
				return new ModelAndView("redirect:" + url);
				}
			}
		}
		return new ModelAndView("redirect:" + url);
	}

	/**
	 * Change username
	 * 
	 * @param request
	 * @param user
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/changeName")
	public ModelAndView changeName(HttpServletRequest request, User user, ModelMap modelMap) {
		String url = request.getHeader("Referer");
		// Get the current user from the session
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		cur_user.setUsername(user.getUsername());// Change the current user's username
		userService.updateUserName(cur_user);// Perform a modification
		request.getSession().setAttribute("cur_user", cur_user);// Modify session value
		return new ModelAndView("redirect:" + url);
	}

	/**
	 * Improve or modify information
	 * 
	 * @param request
	 * @param user
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/updateInfo")
	public ModelAndView updateInfo(HttpServletRequest request, User user, ModelMap modelMap) {
		// Get the current user from the session
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		cur_user.setUsername(user.getUsername());
		cur_user.setQq(user.getQq());
		userService.updateUserName(cur_user);// Perform a modification
		request.getSession().setAttribute("cur_user", cur_user);// Modify session value
		return new ModelAndView("redirect:/user/basic");
	}

	/**
	 * User exit
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().setAttribute("cur_user", null);
		return "redirect:/goods/homeGoods";
	}

	/**
	 * Personal center
	 * 
	 * @return
	 */
	@RequestMapping(value = "/home")
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer userId = cur_user.getId();
		int size=5;
		Purse myPurse = purseService.getPurseByUserId(userId);
		List<User> users=userService.getUserOrderByDate(size);
		List<Notice> notice=noticeService.getNoticeList();
		mv.addObject("notice", notice);
		mv.addObject("myPurse", myPurse);
		mv.addObject("users", users);
		mv.setViewName("/user/home");
		return mv;
	}

	/**
	 *Personal information settings
	 * 
	 * @return
	 */
	@RequestMapping(value = "/basic")
	public ModelAndView basic(HttpServletRequest request) {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer userId = cur_user.getId();
		Purse myPurse = purseService.getPurseByUserId(userId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("myPurse", myPurse);
		mv.setViewName("/user/basic");
		return mv;
	}

	/**
	 * I am idle, I have checked out all the user products and the pictures corresponding to the products.
	 * 
	 * The model returned by @return is the goodsAndImage object, which contains goods and images, refer to the corresponding class.
	 */
	@RequestMapping(value = "/allGoods")
	public ModelAndView goods(HttpServletRequest request) {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer userId = cur_user.getId();
		List<Goods> goodsList = goodsService.getGoodsByUserId(userId);
		List<GoodsExtend> goodsAndImage = new ArrayList<GoodsExtend>();
		for (int i = 0; i < goodsList.size(); i++) {
			// Encapsulate user information and image information into the GoodsExtend class and pass it to the foreground
			GoodsExtend goodsExtend = new GoodsExtend();
			Goods goods = goodsList.get(i);
			List<Image> images = imageService.getImagesByGoodsPrimaryKey(goods.getId());
			goodsExtend.setGoods(goods);
			goodsExtend.setImages(images);
			goodsAndImage.add(i, goodsExtend);
		}
		Purse myPurse = purseService.getPurseByUserId(userId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("goodsAndImage", goodsAndImage);
		mv.setViewName("/user/goods");
		mv.addObject("myPurse", myPurse);
		return mv;
	}

	/**
	 * My concern, query all user products and pictures corresponding to the products.
	 * 
	 * The model returned by return is the goodsAndImage object, which contains goods and images, refer to the corresponding class.
	 */
	@RequestMapping(value = "/allFocus")
	public ModelAndView focus(HttpServletRequest request) {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer userId = cur_user.getId();
		List<Focus> focusList = focusService.getFocusByUserId(userId);
		List<GoodsExtend> goodsAndImage = new ArrayList<GoodsExtend>();
		for (int i = 0; i < focusList.size(); i++) {
			// Encapsulate user information and image information into the GoodsExtend class and pass it to the foreground
			GoodsExtend goodsExtend = new GoodsExtend();
			Focus focus = focusList.get(i);
			Goods goods = goodsService.getGoodsByPrimaryKey(focus.getGoodsId());
			List<Image> images = imageService.getImagesByGoodsPrimaryKey(focus.getGoodsId());
			goodsExtend.setGoods(goods);
			goodsExtend.setImages(images);
			goodsAndImage.add(i, goodsExtend);
		}
		Purse myPurse = purseService.getPurseByUserId(userId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("goodsAndImage", goodsAndImage);
		mv.addObject("myPurse", myPurse);
		mv.setViewName("/user/focus");
		return mv;
	}

	/**
	 * Delete my attention
	 * @return
	 */
	@RequestMapping(value = "/deleteFocus/{id}")
	public String deleteFocus(HttpServletRequest request, @PathVariable("id") Integer goods_id) {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer user_id = cur_user.getId();
		focusService.deleteFocusByUserIdAndGoodsId(goods_id, user_id);

		return "redirect:/user/allFocus";

	}

	/**
	 * Add my attention
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addFocus/{id}")
	public String addFocus(HttpServletRequest request, @PathVariable("id") Integer goods_id) {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer user_id = cur_user.getId();
		//First get all the user's watchlists
		List<Focus> focus=focusService.getFocusByUserId(user_id);
		//If the watchlist is empty, add attention directly
		if(focus.isEmpty()) {
			focusService.addFocusByUserIdAndId(goods_id, user_id);
			return "redirect:/user/allFocus";
		}
		//Traverse all the watchlists
		for (Focus myfocus : focus) {
			int goodsId=myfocus.getGoodsId();
			//If the item has been followed, return directly
			if(goodsId == goods_id.intValue()) {
				return "redirect:/user/allFocus";
			}
		}
		focusService.addFocusByUserIdAndId(goods_id, user_id);
		return "redirect:/user/allFocus";

	}

	/**
	 * My purse
	 * 
	 * The model returned by @return is the goodsAndImage object
	 */
	@RequestMapping(value = "/myPurse")
	public ModelAndView getMoney(HttpServletRequest request) {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer user_id = cur_user.getId();
		Purse purse = purseService.getPurseByUserId(user_id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("myPurse", purse);
		mv.setViewName("/user/purse");
		return mv;
	}

	/**
	 *Recharge and withdrawal According to the two values passed, it is judged whether to recharge or withdraw cash.
	 * 
	 * The model returned by @return is the goodsAndImage object
	 */
	@RequestMapping(value = "/updatePurse")
	public String updatePurse(HttpServletRequest request, Purse purse) {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer user_id = cur_user.getId();
		purse.setUserId(user_id);
		purse.setState(0);
		if (purse.getRecharge() != null) {
			purseService.updatePurse(purse);
		}
		if (purse.getWithdrawals() != null) {
			purseService.updatePurse(purse);
		}
		return "redirect:/user/myPurse";
	}
	
	@RequestMapping(value = "/insertSelective",method = RequestMethod.POST)
	@ResponseBody
	public String insertSelective(HttpServletRequest request){
		String context=request.getParameter("context");
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Notice notice=new Notice();
		notice.setContext(context);
		Date dt = new Date();     
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		notice.setCreateAt(sdf.format(dt));
		notice.setStatus((byte) 0);
		notice.setUser(cur_user);
		if(context==null||context=="") {
			return "{\"success\":false,\"msg\":\"The release failed, please enter the content!\"}";
		}
	try {
			noticeService.insertSelective(notice);
		} catch (Exception e) {
			return "{\"success\":false,\"msg\":\"Publishing failed!\"}";
		}
			return "{\"success\":true,\"msg\":\"Successfully released!\"}";
		
	}
	
	

}
