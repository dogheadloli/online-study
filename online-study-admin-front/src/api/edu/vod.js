import request from '@/utils/request'

const api_name = '/admin/vod/video'

export default {

  removeById(id) {
    return request({
      url: `http://127.0.0.1:8130/admin/vod/video/${id}`,
      method: 'delete'
    })
  },

  getUploadAuthAndAddress(title, fileName) {
    return request({
      url: `http://127.0.0.1:8130/get-upload-auth-and-address/${title}/${fileName}`,
      method: 'get'
    })
  },

  refreshUploadAuthAndAddress(videoId) {
    return request({
      url: `${api_name}/refresh-upload-auth-and-address/${videoId}`,
      method: 'get'
    })
  }
}
