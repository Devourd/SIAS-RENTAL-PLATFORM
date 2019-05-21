package com.ldu.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ldu.pojo.Catelog;
import com.ldu.pojo.CommentExtend;
import com.ldu.pojo.Comments;
import com.ldu.pojo.Goods;
import com.ldu.pojo.GoodsExtend;
import com.ldu.pojo.Image;
import com.ldu.pojo.Purse;
import com.ldu.pojo.User;
import com.ldu.service.CatelogService;
import com.ldu.service.GoodsService;
import com.ldu.service.ImageService;
import com.ldu.service.PurseService;
import com.ldu.service.UserService;
import com.ldu.util.DateUtil;


@Controller
@RequestMapping(value = "/goods")
public class GoodsController {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private CatelogService catelogService;
	@Autowired
	private UserService userService;
	@Resource
	private PurseService purseService;
	

	/**
	 * The home page displays the goods, and each item is queried 6 pieces. According to the latest sort of keys, the key names are catelogGoods1, catelogGoods2....
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/homeGoods")
	public ModelAndView homeGoods() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// Number of product types
		int catelogSize = 7;
		// Display the number of items for each category
		int goodsSize = 6;

		List<Goods> goodsList = null;
		List<GoodsExtend> goodsAndImage = null;

		/* Get the latest release list */
		goodsList = goodsService.getGoodsOrderByDate(goodsSize);
		goodsAndImage = new ArrayList<GoodsExtend>();
		for (int j = 0; j < goodsList.size(); j++) {
			// Encapsulate user information and image information into the GoodsExtend class and pass it to the foreground
			GoodsExtend goodsExtend = new GoodsExtend();
			Goods goods = goodsList.get(j);
			List<Image> images = imageService.getImagesByGoodsPrimaryKey(goods.getId());
			goodsExtend.setGoods(goods);
			goodsExtend.setImages(images);
			goodsAndImage.add(j, goodsExtend);
		}
		String key0 = "catelog" + "Goods";
		modelAndView.addObject(key0, goodsAndImage);

		/* Get other list item information */
		for (int i = 1; i <= catelogSize; i++) {
			goodsList = goodsService.getGoodsByCatelogOrderByDate(i, goodsSize);
			goodsAndImage = new ArrayList<GoodsExtend>();
			for (int j = 0; j < goodsList.size(); j++) {
				// Encapsulate user information and image information into the GoodsExtend class and pass it to the foreground
				GoodsExtend goodsExtend = new GoodsExtend();
				Goods goods = goodsList.get(j);
				List<Image> images = imageService.getImagesByGoodsPrimaryKey(goods.getId());
				goodsExtend.setGoods(goods);
				goodsExtend.setImages(images);
				goodsAndImage.add(j, goodsExtend);
			}
			String key = "catelog" + "Goods" + i;
			modelAndView.addObject(key, goodsAndImage);
		}
		modelAndView.setViewName("goods/homeGoods");
		return modelAndView;
	}

	/**
	 * Search for goods
	 * 
	 * @param str          //Ajax pass value
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchGoods(@RequestParam(value = "str", required = false) String str) throws Exception {
		List<Goods> goodsList = goodsService.searchGoods(str, str);
		List<GoodsExtend> goodsExtendList = new ArrayList<GoodsExtend>();
		for (int i = 0; i < goodsList.size(); i++) {
			GoodsExtend goodsExtend = new GoodsExtend();
			Goods goods = goodsList.get(i);
			List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(goods.getId());
			goodsExtend.setGoods(goods);
			goodsExtend.setImages(imageList);
			goodsExtendList.add(i, goodsExtend);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("goodsExtendList", goodsExtendList);
		modelAndView.addObject("search", str);
		modelAndView.setViewName("/goods/searchGoods");
		return modelAndView;
	}

	/**
	 * Query this product
	 * 
	 * @param id
	 *            Require this parameter to be empty
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/catelog")
	public ModelAndView homeGoods(HttpServletRequest request, @RequestParam(value = "str", required = false) String str)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// Display the number of items for each category
		int goodsSize = 12;
		List<Goods> goodsList = null;
		List<GoodsExtend> goodsAndImage = null;
		/* Get the latest release list */
		goodsList = goodsService.getGoodsByStr(goodsSize, str, str);
		goodsAndImage = new ArrayList<GoodsExtend>();
		for (int j = 0; j < goodsList.size(); j++) {
			// Encapsulate user information and image information into the GoodsExtend class and pass it to the foreground
			GoodsExtend goodsExtend = new GoodsExtend();
			Goods goods = goodsList.get(j);
			List<Image> images = imageService.getImagesByGoodsPrimaryKey(goods.getId());
			goodsExtend.setGoods(goods);
			goodsExtend.setImages(images);
			goodsAndImage.add(j, goodsExtend);
		}
		modelAndView.addObject("goodsExtendList", goodsAndImage);
		modelAndView.addObject("search", str);
		modelAndView.setViewName("/goods/catelogGoods");
		return modelAndView;
	}

	/**
	 * Query this product
	 * 
	 * @param id
	 *         Require this parameter to be empty
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/catelog/{id}")
	public ModelAndView catelogGoods(HttpServletRequest request, @PathVariable("id") Integer id,
			@RequestParam(value = "str", required = false) String str) throws Exception {
		List<Goods> goodsList = goodsService.getGoodsByCatelog(id, str, str);
		Catelog catelog = catelogService.selectByPrimaryKey(id);
		List<GoodsExtend> goodsExtendList = new ArrayList<GoodsExtend>();
		for (int i = 0; i < goodsList.size(); i++) {
			GoodsExtend goodsExtend = new GoodsExtend();
			Goods goods = goodsList.get(i);
			List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(goods.getId());
			goodsExtend.setGoods(goods);
			goodsExtend.setImages(imageList);
			goodsExtendList.add(i, goodsExtend);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("goodsExtendList", goodsExtendList);
		modelAndView.addObject("catelog", catelog);
		modelAndView.addObject("search", str);
		modelAndView.setViewName("/goods/catelogGoods");
		return modelAndView;
	}

	/**
	 * Query the product details based on the product id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsId/{id}")
	public ModelAndView getGoodsById(HttpServletRequest request, @PathVariable("id") Integer id,
			@RequestParam(value = "str", required = false) String str) throws Exception {
		Goods goods = goodsService.getGoodsByPrimaryKey(id);
		User seller = userService.selectByPrimaryKey(goods.getUserId());
		Catelog catelog = catelogService.selectByPrimaryKey(goods.getCatelogId());
		GoodsExtend goodsExtend = new GoodsExtend();
		List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(id);
		CommentExtend CommentExtend=goodsService.selectCommentsByGoodsId(id);
		goodsExtend.setGoods(goods);
		goodsExtend.setImages(imageList);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("CommentExtend",CommentExtend);
		modelAndView.addObject("goodsExtend", goodsExtend);
		modelAndView.addObject("seller", seller);
		modelAndView.addObject("search", str);
		modelAndView.addObject("catelog", catelog);
		modelAndView.setViewName("/goods/detailGoods");
		return modelAndView;

	}
	
	 /**
     * Post a comment
     * @return 
     */
    @RequestMapping(value = "/addComments",method=RequestMethod.POST)
    public void deleteFocus(HttpServletRequest request,Comments comments) {
    	User cur_user = (User)request.getSession().getAttribute("cur_user");
        comments.setUser(cur_user);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date createAt =new Date();
		comments.setCreateAt(sdf.format(createAt));
        goodsService.addComments(comments);
       
	}

	/**
	 * Edit product information
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editGoods/{id}")
	public ModelAndView editGoods(HttpServletRequest request,@PathVariable("id") Integer id) throws Exception {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Goods goods = goodsService.getGoodsByPrimaryKey(id);
		List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(id);
		GoodsExtend goodsExtend = new GoodsExtend();
		goodsExtend.setGoods(goods);
		goodsExtend.setImages(imageList);
		ModelAndView modelAndView = new ModelAndView();
		Integer userId = cur_user.getId();
		Purse myPurse = purseService.getPurseByUserId(userId);
		modelAndView.addObject("myPurse", myPurse);
		// Add product information to model
		modelAndView.addObject("goodsExtend", goodsExtend);
		modelAndView.setViewName("/goods/editGoods");
		return modelAndView;
	}

	/**
	 * Submit product change information
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editGoodsSubmit")
	public String editGoodsSubmit(HttpServletRequest request, Goods goods) throws Exception {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		goods.setUserId(cur_user.getId());
		String polish_time = DateUtil.getNowDay();
		goods.setPolishTime(polish_time);
		goods.setStatus(1);
		goodsService.updateGoodsByPrimaryKeyWithBLOBs(goods.getId(), goods);
		return "redirect:/user/allGoods";
	}

	/**
	 * Merchandise
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/offGoods")
	public ModelAndView offGoods() throws Exception {

		return null;
	}

	/**
	 *User deleted item
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteGoods/{id}")
	public String deleteGoods(HttpServletRequest request, @PathVariable("id") Integer id) throws Exception {
		Goods goods = goodsService.getGoodsByPrimaryKey(id);
		// After deleting the item, the number-1 of the catlog, the goods_num-1, image of the user table are deleted, and the value of the session is updated.
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		goods.setUserId(cur_user.getId());
		int number = cur_user.getGoodsNum();
		Integer calelog_id = goods.getCatelogId();
		Catelog catelog = catelogService.selectByPrimaryKey(calelog_id);
		catelogService.updateCatelogNum(calelog_id, catelog.getNumber() - 1);
		userService.updateGoodsNum(cur_user.getId(), number - 1);
		cur_user.setGoodsNum(number - 1);
		request.getSession().setAttribute("cur_user", cur_user);// Modify session value
		//imageService.deleteImagesByGoodsPrimaryKey(id);
		goodsService.deleteGoodsByPrimaryKey(id);
		return "redirect:/user/allGoods";
	}

	/**
	 * Publish goods
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/publishGoods")
	public ModelAndView publishGoods(HttpServletRequest request) {
		// Can verify that the user is logged in
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		// if (cur_user == null) {
		// return "/goods/homeGoods";
		// } else {
		Integer userId = cur_user.getId();
		Purse myPurse = purseService.getPurseByUserId(userId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("myPurse", myPurse);
		mv.setViewName("/goods/pubGoods");
		return mv;
	}

	/**
	 *Submit published product information
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/publishGoodsSubmit")
	public String publishGoodsSubmit(HttpServletRequest request, Image ima, Goods goods, MultipartFile image)
			throws Exception {
		// Query the current user cur_user object, easy to use id
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		goods.setUserId(cur_user.getId());
		goodsService.addGood(goods, 10);// Insert items in the goods table
		// Returns the id of the inserted item
		int goodsId = goods.getId();
		ima.setGoodsId(goodsId);
		imageService.insert(ima);//Insert product image in image table
		// After the product is released, the number of the catlog is +1, the goods_num+1 of the user table, and the value of the session is updated.
		int number = cur_user.getGoodsNum();
		Integer calelog_id = goods.getCatelogId();
		Catelog catelog = catelogService.selectByPrimaryKey(calelog_id);
		catelogService.updateCatelogNum(calelog_id, catelog.getNumber() + 1);
		userService.updateGoodsNum(cur_user.getId(), number + 1);
		cur_user.setGoodsNum(number + 1);
		request.getSession().setAttribute("cur_user", cur_user);// Modify session value
		return "redirect:/user/allGoods";
	}

	/**
	 * Upload item
	 * 
	 * @param session
	 * @param myfile
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadFile")
	public Map<String, Object> uploadFile(HttpSession session, MultipartFile myfile)
			throws IllegalStateException, IOException {
			// Original name
			String oldFileName = myfile.getOriginalFilename(); // Get the original name of the uploaded file
			// Store the physical path of the picture
			String file_path = session.getServletContext().getRealPath("upload");
			// System.out.println("file_path:"+file_path);
			// upload image
			if (myfile != null && oldFileName != null && oldFileName.length() > 0) {
				// New image name
				String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
				// New picture
				File newFile = new File(file_path + "/" + newFileName);
				// Write data in memory to disk
				myfile.transferTo(newFile);
				// Return the new image name to the front end
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("success",);
				map.put("imgUrl", newFileName);
				return map;
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("error", "Image is illegal");
				return map;
			}
		}

	/**
	 * Query the product details based on the product id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/buyId/{id}")
	public ModelAndView getGoodsdetailById(HttpServletRequest request, @PathVariable("id") Integer id)
			throws Exception {
		Goods goods = goodsService.getGoodsByPrimaryKey(id);
		GoodsExtend goodsExtend = new GoodsExtend();
		List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(id);
		goodsExtend.setGoods(goods);
		goodsExtend.setImages(imageList);
		User cur_user = (User)request.getSession().getAttribute("cur_user");
        Integer userId = cur_user.getId();
		Purse myPurse=purseService.getPurseByUserId(userId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("goodsExtend", goodsExtend);
		modelAndView.addObject("myPurse",myPurse);
		modelAndView.setViewName("/user/pay");
		return modelAndView;
	}
	
}