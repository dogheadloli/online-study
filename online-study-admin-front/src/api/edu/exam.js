import request from '@/utils/request'

export default {
  getPageList(page, limit) {
    return request({
      url: `/edu/question-library/list/${page}/${limit}`,
      method: 'get',
    })
  },

  getMultQuestion(page, limit) {
    return request({
      url: `/edu/multi-question/listQuestion/${page}/${limit}`,
      method: 'get',
    })
  },
  getFillQuestion(page, limit) {
    return request({
      url: `/edu/fill-question/listQuestion/${page}/${limit}`,
      method: 'get',
    })
  },
  getJudgeQuestion(page, limit) {
    return request({
      url: `/edu/judge-question/listQuestion/${page}/${limit}`,
      method: 'get',
    })
  },

  getMultQuestionById(id) {
    return request({
      url: `/edu/multi-question/getQuestion/${id}`,
      method: `get`,
    })
  },
  getFillQuestionById(id) {
    return request({
      url: `/edu/fill-question/getQuestion/${id}`,
      method: `get`,
    })
  },
  getJudgeQuestionById(id) {
    return request({
      url: `/edu/judge-question/getQuestion/${id}`,
      method: `get`,
    })
  },

  addMultiQuestion(multiQuestion) {
    return request({
      url: '/edu/multi-question/addQuestion',
      method: 'post',
      data: multiQuestion
    })
  },
  addFillQuestion(fillQuestion) {
    return request({
      url: '/edu/fill-question/addQuestion',
      method: 'post',
      data: fillQuestion
    })
  },
  addJudgeQuestion(judgeQuestion) {
    return request({
      url: '/edu/judge-question/addQuestion',
      method: 'post',
      data: judgeQuestion
    })
  },

  deleteMultiQuestionById(id){
    return request({
      url:`/edu/multi-question/deleteQuestion/${id}`,
      method:'delete'
    })
  },

  deleteFillQuestionById(id){
    return request({
      url:`/edu/fill-question/deleteQuestion/${id}`,
      method:'delete'
    })
  },

  deleteJudgeQuestionById(id){
    return request({
      url:`/edu/judge-question/deleteQuestion/${id}`,
      method:'delete'
    })
  },

  updateMultiQuestion(multiQuestion){
    return request({
      url: '/edu/multi-question/updateQuestion',
      method: 'post',
      data: multiQuestion
    })
  },

  updateFillQuestion(fillQuestion){
    return request({
      url: '/edu/fill-question/updateQuestion',
      method: 'post',
      data: fillQuestion
    })
  },

  updateJudgeQuestion(judgeQuestion){
    return request({
      url: '/edu/judge-question/updateQuestion',
      method: 'post',
      data: judgeQuestion
    })
  },

}
