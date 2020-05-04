<template>

  <div class="exam">

    <el-table :data="list" border style="width: 100%">

      <el-table-column label="题库名称" width="180">
        <template slot-scope="scope">
          {{ scope.row.courseName }}
        </template>
      </el-table-column>
      <el-table-column label="题目总数" width="120">
        <template slot-scope="scope">
          {{ scope.row.questionNum }}
        </template>
      </el-table-column>
      <el-table-column label="选择题数量" width="120">
        <template slot-scope="scope">
          {{ scope.row.multiQuestionNum }}
        </template>
      </el-table-column>
      <el-table-column label="判断题数量" width="120">
        <template slot-scope="scope">
          {{ scope.row.judgeQuestionNum }}
        </template>
      </el-table-column>
      <el-table-column label="填空题数量" width="120">
        <template slot-scope="scope">
          {{ scope.row.fillQuestionNum }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" width="200">
        <template slot-scope="scope">
          {{ scope.row.gmtModified }}
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="add(scope.row.id)" type="primary" size="small">增加试题</el-button>
        </template>
      </el-table-column>

    </el-table>

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
</template>

<script>
  import exam from '@/api/edu/exam'

  export default {
    data() {
      return {
        list: null, // 数据列表
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
          if (response.success === true) {
            this.list = response.data.rows
            this.total = response.data.total
          }
        })
      },

      add(id) { //增加题库
        this.$router.push({
          path: '/edu/library/addQuestion',
          query: {
            libraryId: id
          }
        })
      }
    },
  };
</script>
<style lang="scss" scoped>
  .exam {
    padding: 0px 40px;

    .page {
      margin-top: 20px;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .edit {
      margin-left: 20px;
    }
  }
</style>
