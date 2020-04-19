package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.edu.entity.Chapter;
import com.rui.edu.entity.Video;
import com.rui.edu.mapper.ChapterMapper;
import com.rui.edu.mapper.VideoMapper;
import com.rui.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.edu.service.VodService;
import com.rui.edu.vo.ChapterVo;
import com.rui.edu.vo.VideoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 章节 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VodService vodService;

    @Override
    @Transactional
    public void removeChapterById(String id) {
        // 删阿里云oss视频
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("chapter_id", id);
        List<Video> list = videoMapper.selectList(queryWrapper);
        if (list != null && list.size() > 0) {
            for (Video video : list) {
                String videoSourceId = video.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)) {
                    vodService.removeVideo(videoSourceId);
                }
            }
        }

        // 删数据库库视频
        QueryWrapper videoQueryWrapper = new QueryWrapper();
        videoQueryWrapper.eq("chapter_id", id);
        videoMapper.delete(videoQueryWrapper);

        // 删章节
        baseMapper.deleteById(id);
    }

    @Override
    public List<ChapterVo> nestedList(String courseId) {
        List chapterVoList = new ArrayList();

        // 章节
        QueryWrapper chapterQueryWrapper = new QueryWrapper();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterQueryWrapper.orderByDesc("sort");
        List chapterList = baseMapper.selectList(chapterQueryWrapper);

        Iterator iterator = chapterList.iterator();
        while (iterator.hasNext()) {
            Chapter chapter = (Chapter) iterator.next();
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            QueryWrapper videoQueryWrapper = new QueryWrapper();
            videoQueryWrapper.eq("chapter_id", chapter.getId());
            videoQueryWrapper.orderByAsc("sort");
            List videoList = videoMapper.selectList(videoQueryWrapper);
            List videoVoList = new ArrayList();
            Iterator iterator1 = videoList.iterator();
            while (iterator1.hasNext()) {
                Video video = (Video) iterator1.next();
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(video, videoVo);
                videoVoList.add(videoVo);
            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }
}
