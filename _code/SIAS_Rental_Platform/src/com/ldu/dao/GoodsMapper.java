package com.ldu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ldu.pojo.CommentExtend;
import com.ldu.pojo.Comments;
import com.ldu.pojo.Goods;

public interface GoodsMapper {
    /**
     * Delete by primary key
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);//Update
    
    int deleteByPrimaryKeys(Integer id);//delete

    /**
     * Add item
     * @param record
     * @return
     */
    int insert(Goods record);

    /**
     *
     * @param record
     * @return
     */
    int insertSelective(Goods record);

    /**
     * Query by id
     * @param id
     * @return
     */
    Goods selectByPrimaryKey(Integer id);
    

	Goods selectById(Integer goodsId);

    /**
     * Change information by primary key
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Goods record);

    /**
     * Change information via primary key, including large text information
     * @param record
     * @return
     */
    int updateByPrimaryKeyWithBLOBs(Goods record);

    /**
     * Change information by primary key
     * @param record
     * @return
     */
    int updateByPrimaryKey(Goods record);

    /**
     * Query all products
     * @return
     */
    public List<Goods> selectAllGoods();

    List<Goods> searchGoods(@Param("name") String name,@Param("describle") String describle);

    /**
     * Query products according to the latest release classification
     * @param catelog_id
     * @return
     */
    public List<Goods> selectByStr(@Param("limit")Integer limit,@Param("name") String name,@Param("describle") String describle);

    /**
     * Query product based on id of product category
     * @param catelog_id
     * @return
     */
    public List<Goods> selectByCatelog(@Param("catelog_id") Integer catelog_id,@Param("name") String name,@Param("describle") String describle);

    
    /**
     * Get product information according to time, and perform paged query
     * Not implemented in xml
     * @return
     */
    public List<Goods> selectByDate(int page,int maxResults);

    /**
     * Query product information according to catelog_id, the results are sorted by polishing time, the latest one is before
     * @return
     */
    public List<Goods> selectByCatelogOrderByDate(@Param("catelogId")Integer catelogId,@Param("limit")Integer limit);

    
    /**
     * Query the latest published product information, the results are sorted by polishing time, the latest one is before
     * @return
     */
    public List<Goods> selectOrderByDate(@Param("limit")Integer limit);

    /**
     * Query all idle items of the logged in user
     * @param user_id
     * @return
     */
    public List<Goods> getGoodsByUserId(Integer user_id);
    
    /**
     * Modify item status when submitting an order
     * @param user_id
     * @return
     */
    int updateGoodsByGoodsId(Goods goods);
    /**
     * Get the number of products
     * @return
     */
	List<Goods> getGoodsList();
	/**
	 * Fuzzy query
	 * @param id
	 * @param name
	 * @param form
	 * @return
	 */
	List<Goods> getPageGoodsByGoods(@Param("id")Integer id, @Param("name")String name,@Param("status")Integer status);
	
	CommentExtend selectCommentsByGoodsId(@Param("id")Integer id);

	public void addComments(Comments comments);
}