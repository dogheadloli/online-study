<template>
  <div>
    <el-row class="main" type="flex" justify="center">
      <el-col :span="16">
        <div id="artcle-info">
            <h2 class="text-center title"><strong>{{blogVo.title}}</strong></h2>
          <!-- 描述：文章信息 -->
          <div class="text-center timeAndView">
            <i class="el-icon-time"></i>
            发表于：<span>{{blogVo.gmtCreate}}</span>
          </div>
          <div class="text-center timeAndView">
            <i class="el-icon-view"></i>
            阅读量：<span>{{blogVo.viewCount}}</span>
          </div>
          <div class="text-center timeAndView">
            <i class="el-icon-user"></i>
            作者:<span>{{blogVo.userName}}</span>
          </div>
        </div>


        <hr/>

        <div id="artcle-content">
          <p v-html="blogVo.description">{{blogVo.description}}</p>
        </div>

        <hr/>

        <div id="statement">
          <div class="item">本博客所有文章除特别声明外,转载请注明出处!</div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import blogApi from '@/api/blog'

  export default {
    asyncData({params, error}) {
      return blogApi.getBlogInfo(params.id)
        .then(response => {
          console.log(response.data.data.item)
          return {
            blogVo: response.data.data.item,
          }
        })
    }
  };
</script>

<style scoped>

  #artcle-content{
    font-size: 16px;
  }

  #artcle-info {
    padding: 40px;
    background-image: url("~@/assets/vue.jpg");
    margin-bottom: 40px;
  }

  #artcle-info .abstract {
    color: #ffffff;
    border-left: 3px solid #F56C6C;
    padding: 10px;
    background-color: rgba(126, 129, 135, 0.3);
  }

  #artcle-info .title{
    padding: 10px;
    line-height: 30px;
    font-size: 34px;
  }

  #artcle-info .timeAndView {
    padding: 10px;
    line-height: 10px;
    font-size: 16px;
    color: #ffffff;
  }

  pre.has {
    color: #ffffff;
    background-color: rgba(0, 0, 0, 0.8);
  }

  img.has {
    width: 100%;
  }

  #statement {
    border-left: 3px solid #F56C6C;
    padding: 20px;
    background-color: #EBEEF5;
  }
</style>
