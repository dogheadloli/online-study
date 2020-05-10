import request from '@/utils/request'

export default {
  getExam(id){
    return request({
      url:`/eduservice/question-library/getExam/${id}`,
      method:'get',
    })
  },
  getPageList(page, limit) {
    return request({
      url: `/eduservice/question-library/list/${page}/${limit}`,
      method: 'get',
    })
  },

  getMultQuestion(page, limit) {
    return request({
      url: `/eduservice/multi-question/listQuestion/${page}/${limit}`,
      method: 'get',
    })
  },
  getFillQuestion(page, limit) {
    return request({
      url: `/eduservice/fill-question/listQuestion/${page}/${limit}`,
      method: 'get',
    })
  },
  getJudgeQuestion(page, limit) {
    return request({
      url: `/eduservice/judge-question/listQuestion/${page}/${limit}`,
      method: 'get',
    })
  },

  getMultQuestionById(id) {
    return request({
      url: `/eduservice/multi-question/getQuestion/${id}`,
      method: `get`,
    })
  },
  getFillQuestionById(id) {
    return request({
      url: `/eduservice/fill-question/getQuestion/${id}`,
      method: `get`,
    })
  },
  getJudgeQuestionById(id) {
    return request({
      url: `/eduservice/judge-question/getQuestion/${id}`,
      method: `get`,
    })
  },

  addMultiQuestion(multiQuestion) {
    return request({
      url: '/eduservice/multi-question/addQuestion',
      method: 'post',
      data: multiQuestion
    })
  },
  addFillQuestion(fillQuestion) {
    return request({
      url: '/eduservice/fill-question/addQuestion',
      method: 'post',
      data: fillQuestion
    })
  },
  addJudgeQuestion(judgeQuestion) {
    return request({
      url: '/eduservice/judge-question/addQuestion',
      method: 'post',
      data: judgeQuestion
    })
  },

  deleteMultiQuestionById(id){
    return request({
      url:`/eduservice/multi-question/deleteQuestion/${id}`,
      method:'delete'
    })
  },

  deleteFillQuestionById(id){
    return request({
      url:`/eduservice/fill-question/deleteQuestion/${id}`,
      method:'delete'
    })
  },

  deleteJudgeQuestionById(id){
    return request({
      url:`/eduservice/judge-question/deleteQuestion/${id}`,
      method:'delete'
    })
  },

  updateMultiQuestion(multiQuestion){
    return request({
      url: '/eduservice/multi-question/updateQuestion',
      method: 'post',
      data: multiQuestion
    })
  },

  updateFillQuestion(fillQuestion){
    return request({
      url: '/eduservice/fill-question/updateQuestion',
      method: 'post',
      data: fillQuestion
    })
  },

  updateJudgeQuestion(judgeQuestion){
    return request({
      url: '/eduservice/judge-question/updateQuestion',
      method: 'post',
      data: judgeQuestion
    })
  },

}
