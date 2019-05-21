package com.ldu.service;

import java.util.List;

import com.ldu.pojo.Image;

/**
 * Created by William，66195207.
 */

public interface ImageService {
    int insert(Image record);
    /**
     * Get a picture of the product through the product ID
     * @param goodsId
     * @return
     */
    public List<Image> getImagesByGoodsPrimaryKey(Integer goodsId);

    /**
     * Delete goods by commodity ID
     * @param goodsId
     * @return
     */
    int deleteImagesByGoodsPrimaryKey(Integer goodsId);
}