package com.rui.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.edu.entity.QuestionLibrary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.edu.vo.QuestionLibraryVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-05-02
 */
public interface QuestionLibraryService extends IService<QuestionLibrary> {
    void listQuestionLibrary(Page page);

    void addMultiCount(String id);

    void addFillCount(String id);

    void addJudgeCount(String id);

    void reduceMultiCount(String id);

    void reduceFillCount(String id);

    void reduceJudgeCount(String id);
}
