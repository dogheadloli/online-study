package com.rui.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.edu.entity.JudgeQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-05-02
 */
public interface JudgeQuestionService extends IService<JudgeQuestion> {
    void listQuestion(Page page);

    void saveQuestion(JudgeQuestion question);

    void removeQuestion(String id);
}
