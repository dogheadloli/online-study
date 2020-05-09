package com.rui.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.edu.entity.MultiQuestion;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-05-02
 */
public interface MultiQuestionService extends IService<MultiQuestion> {
    void listQuestion(Page page);

    void saveQuestion(MultiQuestion multiQuestion);

    void removeQuestion(String id);
}
