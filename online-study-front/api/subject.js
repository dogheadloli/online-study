import request from '@/utils/request'
import qs from 'qs'

const api_name = '/admin/edu/subject'

export default {

  getNestedTreeList() {
    return request({// 封装axios
      url: `${api_name}/list`,
      method: 'get'
    })
  },

  save(addEventForm) {
    return request({
        url: `${api_name}/save`,
        method: 'post',
        data: {
          title: addEventForm.subjectName,
          parentId: addEventForm.parentNodeId,
          parentLevel: addEventForm.parentLevel
        }
      }
    )
  },

  update(updateEventForm) {
    return request({
      url: `${api_name}/update`,
      method: 'post',
      data: {
        title: updateEventForm.currentSubjectName,
        id: updateEventForm.nodeId
      }
    })
  },

  delete(id){
    return request({
      url: `${api_name}/${id}`,
      method: 'delete',
    })
  }
}
