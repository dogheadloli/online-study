package com.rui.edu.service;

import com.rui.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
public interface VideoService extends IService<Video> {

    public void removeVideoById(String id);

}
