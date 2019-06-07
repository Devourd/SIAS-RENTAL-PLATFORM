package com.ldu.service;

import java.util.List;

import com.ldu.pojo.Purse;
/**
 * Created by William，66195207.
 *  Tested by: William，66195207.
 *  Debugged by: William，66195207.
 */

public interface PurseService {
	
	public void updatePurseByuserId(Integer userId, Float balance);

	public void updatePurseOfdel(Integer user_id, Float balance);

	public void addPurse(Integer userId);

	/**
	 * Getting wallet information based on user ID
	 * @param user_id
	 * @return
	 */
	public Purse getPurseByUserId(Integer user_id);

	/**
	 * Recharge or offer
	 * @param purse
	 */
	public void updatePurse(Purse purse);

	public int getPurseNum();

	public List<Purse> getPagePurse(int pageNum, int pageSize);

	public List<Purse> getPagePurseByPurse(Integer userId,Integer state, int pageNum, int pageSize);

	public Purse getPurseById(int id);

	public void updateByPrimaryKey(Integer id, Purse purse);

	public void updatePursePassById(Integer id, Purse purse);

	public void updatePurseRefuseById(Integer id,Purse purse);



}
