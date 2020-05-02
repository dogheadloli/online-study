package com.rui.edu.controller;

import com.rui.common.vo.Result;
import com.rui.edu.entity.CrmBanner;
import com.rui.edu.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前台bannber显示
 * </p>
 *
 * @author Rui
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public Result getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        Map map = new HashMap();
        map.put("list",list);
        return Result.ok(map);
    }
}

