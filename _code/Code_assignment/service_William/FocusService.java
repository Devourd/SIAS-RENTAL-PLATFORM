package com.ldu.service;

import java.util.List;

import com.ldu.pojo.Focus;

/**
 * Created by William，66195207.
 */

public interface FocusService {
	
	/**
     * According to the user's id, query out all the idle of the user
     * @param user_id
     * @return
     */
    public List<Focus> getFocusByUserId(Integer user_id);
    
    /**
     * Delete my concerns based on user ID and concern ID
     * @param id
     * @param user_id 用户id
     */
    public void deleteFocusByUserIdAndGoodsId(Integer goods_id,Integer user_id);

    /**
     * Add my attention
     * @param id
     * @param user_id 用户id
     */
	public void addFocusByUserIdAndId(Integer goods_id, Integer user_id);

}
