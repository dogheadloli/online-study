package com.rui.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.CrmBanner;
import com.rui.edu.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author Rui
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner, null);
        Map map = new HashMap();
        map.put("items", pageBanner.getRecords());
        map.put("total", pageBanner.getTotal());
        return Result.ok(map);
    }

    /**
     * 增加
     *
     * @param crmBanner
     * @return
     */
    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return Result.ok();
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        Map map = new HashMap();
        map.put("item", banner);
        return Result.ok(map);
    }

    /**
     * 修改
     *
     * @param banner
     * @return
     */
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public Result updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        bannerService.removeById(id);
        return Result.ok();
    }
}

