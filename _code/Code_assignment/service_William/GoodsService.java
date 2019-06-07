package com.ldu.service;

import java.util.List;
import com.ldu.pojo.CommentExtend;
import com.ldu.pojo.Comments;
import com.ldu.pojo.Goods;

/**
 * Created by William，66195207.
 *  Tested by: William，66195207.
 *  Debugged by: William，66195207.
 */

public interface GoodsService {
    /**
     * Release commodities
     * @param goods
     * @param duration Allowable shelf time
     */
    public int addGood(Goods goods , Integer duration);

    /**
     * Getting goods through primary keys
     * @param goodsId
     * @return
     */
    public Goods getGoodsByPrimaryKey(Integer goodsId);
    
    public Goods getGoodsById(Integer goodsId);

    /**
     * Update commodity information
     * @param goods
     */
    public void updateGoodsByPrimaryKeyWithBLOBs(int goodsId ,Goods goods);

    /**
     * Delete merchandise by primary key
     * @param id
     */
    public void deleteGoodsByPrimaryKey(Integer id);//To update
    
    public void deleteGoodsByPrimaryKeys(Integer id);//To Delete

    /**
     * Access to all commodity information
     */
    public List<Goods> getAllGoods();

    List<Goods> searchGoods(String name, String describle);

    /**
     * Getting Commodity Information by Newly Published Categories
     */
    public List<Goods> getGoodsByStr(Integer limit,String name,String describle);

    /**
     * Getting Commodity Information by Commodity Classification
     */
    public List<Goods> getGoodsByCatelog(Integer id,String name,String describle);

    
    /**
     * Get the latest releases, sort them by time, and get the first limit results
     * @param limit
     * @return
     */
    public List<Goods> getGoodsOrderByDate(Integer limit);
    /**
     * According to the classification ID and time order, the first limit results are obtained.
     * @param catelogId
     * @param limit
     * @return
     */
    public List<Goods> getGoodsByCatelogOrderByDate(Integer catelogId,Integer limit);

    /**
     * According to the user's id, query out all the idle of the user
     * @param user_id
     * @return
     */
    public List<Goods> getGoodsByUserId(Integer user_id);

    /**
     * When submitting an order, modify the product status according to goodsId
     * @param goods
     */
	public void updateGoodsByGoodsId(Goods goods);
	
	/**
	 * Number of Goods Obtained
	 * @return
	 */
	public int getGoodsNum();

	public List<Goods> getPageGoods(int pageNum, int pageSize);
	
	/**
	 * Fuzzy query
	 * @param id
	 * @param name
	 * @param form
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Goods> getPageGoodsByGoods(Integer id, String name, Integer status, int pageNum, int pageSize);

	
	public CommentExtend selectCommentsByGoodsId(Integer id);
	
	/**
	 * New comments
	 * @param id
	 */
	public void addComments(Comments comments);

}