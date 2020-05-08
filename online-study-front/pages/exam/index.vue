<template>
  <div id="myExam">
    <div class="wrapper">
      <ul class="top">
        <h2 class="order">试卷列表</h2>
        <li class="search-li">

        </li>
      </ul>


    <ul class="paper">
      <li class="item" v-for="(library,index) in list" :key="index">
        <h4 @click="toExam(library.id)"><b>{{library.courseName}}</b></h4>
        <p class="name">总题量：{{library.questionNum}}</p>
        <div class="info">
          <span>选择题：{{library.multiQuestionNum}}</span>
          <span>判断题：{{library.judgeQuestionNum}}</span>
          <span>填空题：{{library.fillQuestionNum}}</span>
        </div>
      </li>
    </ul>

  </div>

     <div class="pagination">
       <!-- 分页 -->
       <el-pagination
         :current-page="page"
         :page-size="limit"
         :total="total"
         style="padding: 30px 0; text-align: center;"
         layout="total, prev, pager, next, jumper"
         @current-change="fetchData"
       />
     </div>

  </div>
</template>

<script>
  import exam from '@/api/exam'

  export default {
    data() {
      return {
        list: {}, // 数据列表
        total: 0, // 总记录数
        page: 1, // 页码
        limit: 5, // 每页记录数

      }
    },
    created() {
      this.fetchData()

    },

    methods: {

      fetchData(page = 1) { //分页查询所有试卷信息
        this.page = page
        exam.getPageList(this.page, this.limit).then(response => {
          this.list = response.data.data.rows
          this.total = response.data.data.total

        })

        console.log(1)
        console.log(this.list)
      },


      //跳转到试卷详情页
      toExam(id) {
        this.$router.push({path: '/exam/exam', query: {id: id}})
      }
    }
  }
</script>

<style lang="scss" scoped>
  .pagination {
    padding: 20px 0px 30px 0px;

    .el-pagination {
      display: flex;
      justify-content: center;
    }
  }

  .paper {
    h4 {
      cursor: pointer;
    }
  }

  .paper .item a {
    color: #000;
  }

  .wrapper .top .order {
    cursor: pointer;
  }

  .wrapper .top .order:hover {
    color: #0195ff;
    border-bottom: 2px solid #0195ff;
  }

  .wrapper .top .order:visited {
    color: #0195ff;
    border-bottom: 2px solid #0195ff;
  }

  .item .info i {
    margin-right: 5px;
    color: #0195ff;
  }

  .item .info span {
    margin-right: 14px;
  }

  .paper .item {
    width: 380px;
    border-radius: 4px;
    padding: 20px 30px;
    border: 1px solid #eee;
    box-shadow: 0 0 4px 2px rgba(217, 222, 234, 0.3);
    transition: all 0.6s ease;
  }

  .paper .item:hover {
    box-shadow: 0 0 4px 2px rgba(140, 193, 248, 0.45);
    transform: scale(1.03);
  }

  .paper .item .info {
    font-size: 14px;
    color: #88949b;
  }

  .paper .item .name {
    font-size: 14px;
    color: #88949b;
  }

  .paper * {
    margin: 20px 0;
  }

  .wrapper .paper {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
  }

  .top .el-icon-search {
    position: absolute;
    right: 10px;
    top: 10px;
  }

  .top .icon {
    position: relative;
  }

  .wrapper .top {
    border-bottom: 1px solid #eee;
    margin-bottom: 20px;
  }

  #myExam .search-li {
    margin-left: auto;
  }

  .top .search-li {
    margin-left: auto;
  }

  .top li {
    display: flex;
    align-items: center;
  }

  .top .search {
    margin-left: auto;
    padding: 10px;
    border-radius: 4px;
    border: 1px solid #eee;
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
  }

  .top .search:hover {
    color: #0195ff;
    border-color: #0195ff;
  }

  .wrapper .top {
    display: flex;
  }

  .wrapper .top li {
    margin: 20px;
  }

  #myExam {
    width: 980px;
    margin: 0 auto;
  }

  #myExam .title {
    margin: 20px;
  }

  #myExam .wrapper {
    background-color: #fff;
  }
</style>
