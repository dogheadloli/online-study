<template>
  <div id="aCoursesList" class="bg-fa of">

    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部文章</span>
        </h2>
        <h2 class="fr tac">
          <el-button round size="medium" type="success" v-on:click="addBlog()">写一篇</el-button>
        </h2>
      </header>


      <!-- /无数据提示 开始-->
      <section class="no-data-wrap" v-if="data.total==0">
        <em class="icon30 no-data-ico">&nbsp;</em>
        <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
      </section>

      <li v-for="item in data.items" :key="item.id">

        <!--<section class="c-sort-box unBr">-->
        <el-row id="artList" type="flex" justify="space-around">
          <el-col :span="26">
            <el-row class="art-item">
              <el-card shadow="hover">
                <h2>
                  <router-link to="~/article" tag="span" class="art-title">{{item.title}}</router-link>
                </h2>

                <el-row class="art-info d-flex align-items-center justify-content-start">
                  <div class="art-time"><i class="el-icon-time"></i>：{{item.gmtCreate}}</div>

                  <div class="d-flex align-items-center"><img class="tag" src="~/assets/tag.png"/>：
                    <el-tag size="small">{{item.subjectName}}</el-tag>
                  </div>
                </el-row>

                <el-row class="art-body">
                  <div class="side-img hidden-sm-and-down"><img class="art-banner" src="~/assets/vue.jpg"></div>

                  <div class="side-abstract">
                    <div class="art-abstract">
                      {{item.description}}
                    </div>

                    <div class="art-more">

                      <el-button v-on:click="gotoLink(item.id)" plain>阅读更多</el-button>


                      <div class="view"><i class="el-icon-view"></i>：{{item.viewCount}}</div>

                    </div>

                  </div>
                </el-row>
              </el-card>
              <img class="star" src="~/assets/star.png"/>
            </el-row>
          </el-col>
        </el-row>

      </li>

      <!-- 公共分页 开始 -->
      <!-- 公共分页 开始 -->
      <div>
        <div class="paging">
          <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
          <a
            :class="{undisable: !data.hasPrevious}"
            href="#"
            title="首页"
            @click.prevent="gotoPage(1)">首页</a>

          <a
            :class="{undisable: !data.hasPrevious}"
            href="#"
            title="前一页"
            @click.prevent="gotoPage(data.current-1)">&lt;</a>

          <a
            v-for="page in data.pages"
            :key="page"
            :class="{current: data.current == page, undisable: data.current == page}"
            :title="'第'+page+'页'"
            href="#"
            @click.prevent="gotoPage(page)">{{ page }}</a>

          <a
            :class="{undisable: !data.hasNext}"
            href="#"
            title="后一页"
            @click.prevent="gotoPage(data.current+1)">&gt;</a>

          <a
            :class="{undisable: !data.hasNext}"
            href="#"
            title="末页"
            @click.prevent="gotoPage(data.pages)">末页</a>

          <div class="clear"/>
        </div>
      </div>

    </section>
  </div>
</template>
<script>

  import blogApi from '@/api/blog'
  import cookie from 'js-cookie'

  export default {
    data() {
      return {
        page: 1,
        data: {}
      }
    },
    created() {
      this.initBlogFirst();
    },

    methods: {
      initBlogFirst() {
        blogApi.getBlogList(1, 5)
          .then(response => {
            this.data = response.data.data
          })
      },

      gotoLink(id) {
        this.$router.push({path:'/blog/'+id})
      },
      //分页切换的方法
      //参数是页码数
      gotoPage(page) {
        blogApi.getBlogList(page, 5)
          .then(response => {
            this.data = response.data.data
          })
        window.scrollTo(0, 0)
      },

      addBlog() {
        let userStr = cookie.get('guli_ucenter');
        console.log(cookie)
        console.log(userStr)
        if (userStr) {
          window.open('/blog/info','_blank')
        }else {
          this.$message({
            type: 'error',
            message: "请先登录"
          })
          this.$router.push({path: '/login'})
        }
      }

    },

  };
</script>

<style scoped>
  #side .item {
    margin-bottom: 30px;
  }

  .art-item {
    margin-bottom: 30px;
    position: relative;
  }

  .art-item .star {
    width: 60px;
    height: 60px;
    position: absolute;
    top: 0;
    right: 0;
  }

  img.tag {
    width: 16px;
    height: 16px;
  }

  .art-title {
    border-left: 3px solid #F56C6C;
    padding-left: 5px;
    cursor: pointer;
  }

  .art-title:hover {
    padding-left: 10px;
    color: #409EFF;
  }

  .art-time {
    margin-right: 20px;
    font-size: 14px;
  }

  .art-body {
    display: flex;
    padding: 10px 0;
    font-size: 16px;
  }

  .side-img {
    height: 150px;
    width: 270px;
    overflow: hidden;
    margin-right: 10px;
  }

  img.art-banner {
    width: 100%;
    height: 100%;
    transition: all 0.6s;
  }

  img.art-banner:hover {
    transform: scale(1.4);
  }

  .side-abstract {
    width: 800px;
    flex: 1;
    display: flex;
    flex-direction: column;
  }

  .art-abstract {
    flex: 1;
    color: #aaa;

  }

  .art-more {
    height: 40px;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
  }

  .art-more .view {
    color: #aaa;
  }

  h5 {
    font-size: 18px;
  }

  .pagination {
    background-color: #F9F9F9;
  }
</style>
