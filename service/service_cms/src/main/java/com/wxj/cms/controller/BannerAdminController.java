package com.wxj.cms.controller;


import com.wxj.R;
import com.wxj.cms.entity.CrmBanner;
import com.wxj.cms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/cms/bannerFront")
public class BannerAdminController {
    @Autowired
  private   CrmBannerService crmBannerService;

    @GetMapping("getAllBanner")
   public R getAllBanner(){
   List<CrmBanner>  bannerList=  crmBannerService.selectAllBanner();
        return R.Ok().data("bannerList",bannerList);

    }



}

