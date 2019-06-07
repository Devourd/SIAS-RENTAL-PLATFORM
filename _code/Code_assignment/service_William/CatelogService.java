package com.ldu.service;

import java.util.List;

import com.ldu.pojo.Catelog;

/**
 * Created by William，66195207.
 *  Tested by: William，66195207.
 *  Debugged by: William，66195207.
 */
public interface CatelogService {
    public List<Catelog> getAllCatelog();
    public int getCount(Catelog catelog);
    Catelog selectByPrimaryKey(Integer id);
    int updateByPrimaryKey(Catelog record);
    int updateCatelogNum(Integer id,Integer number);
}
