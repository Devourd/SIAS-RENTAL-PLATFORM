package com.ldu.controller;

import org.springframework.stereotype.Controller;

import com.ldu.service.CatelogService;

import javax.annotation.Resource;

/**
 * Created by William，66195207.
 *  Tested by: William，66195207.
 *  Debugged by: William，66195207.
 */
@Controller
public class CatelogController {
    @Resource
    private CatelogService catelogService;

}
