package com.rui.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.edu.entity.FillQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-05-02
 */
public interface FillQuestionService extends IService<FillQuestion> {
    void listQuestion(Page page);

    void saveQuestion(FillQuestion question);

    void removeQuestion(String id);
}
