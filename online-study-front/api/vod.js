import request from '@/utils/request'
export default {
  getPlayAuth(vid) {
    return request({
      url: `http://localhost:8222/vod/video/getPlayAuth/${vid}`,
      method: 'get'
    })
  }

}
