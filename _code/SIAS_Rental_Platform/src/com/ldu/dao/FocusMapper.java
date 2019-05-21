package com.ldu.dao;

import java.util.List;

import com.ldu.pojo.Focus;

public interface FocusMapper {
	
	 /**
     * Query all the products of interest of the logged in user
     * @param user_id
     * @return
     */
    public List<Focus> getFocusByUserId(Integer user_id);
    
    /**
     * Delete the item of interest based on user id and id
     * @param id Follow id
     * @param user_id User id
     */
    
    public void deleteFocusByUserIdAndGoodsId(Integer goods_id,Integer user_id);

    /**
     * Add attention
     * @param goods_id
     * @param user_id
     */
	public void addFocusByUserIdAndGoodsId(Integer goods_id, Integer user_id);

}
