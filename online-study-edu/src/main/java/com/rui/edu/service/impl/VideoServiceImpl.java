package com.rui.edu.service.impl;

import com.rui.edu.entity.Video;
import com.rui.edu.mapper.VideoMapper;
import com.rui.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.edu.service.VodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodService vodService;

    @Override
    public void removeVideoById(String id) {
        // 删除阿里云OSS
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodService.removeVideo(videoSourceId);
        }

        // 删数据库
        baseMapper.deleteById(id);
    }
}
