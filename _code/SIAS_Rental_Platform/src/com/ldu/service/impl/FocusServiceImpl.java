package com.ldu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ldu.dao.FocusMapper;
import com.ldu.pojo.Focus;
import com.ldu.service.FocusService;
/**
 * Created by William，66195207.
 */
@Service("focusService")
public class FocusServiceImpl implements FocusService {
	
	 @Resource
	 private FocusMapper focusMapper;

	 /**
	  * Get my attention based on user ID
	  */
	 public List<Focus> getFocusByUserId(Integer user_id) {
		List<Focus> focusList = focusMapper.getFocusByUserId(user_id);
				
        return focusList;
	}
	 
	 /*
	  * Delete according to user ID and concern ID
	  */

	public void deleteFocusByUserIdAndGoodsId(Integer goods_id, Integer user_id) {
		
		focusMapper.deleteFocusByUserIdAndGoodsId(goods_id, user_id);
		
	}
	/*
	  * 添加我的关注
	  */
	public void addFocusByUserIdAndId(Integer goods_id, Integer user_id) {
		
		focusMapper.addFocusByUserIdAndGoodsId(goods_id,user_id);
		
	}

}
