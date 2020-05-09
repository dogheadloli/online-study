package com.rui.edu.service;

import com.rui.edu.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-04-21
 */
public interface CrmBannerService extends IService<CrmBanner> {
   public List<CrmBanner> selectAllBanner();
}
