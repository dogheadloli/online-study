import request from '@/utils/request'
export default {
  getPlayAuth(vid) {
    return request({
      url: `http://localhost:8130/admin/vod/video/getPlayAuth/${vid}`,
      method: 'get'
    })
  }

}
