<template>
  <div class="add">
    <section class="append">
      <ul>
        <li>
          <span>题目类型:</span>
          <el-select v-model="optionValue" placeholder="请选择题型" @change="getParams()" class="w150">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </li>
      </ul>
    </section>

    <el-table :data="list" border style="width: 100%">

      <el-table-column label="题库名称" width="180">
        <template slot-scope="scope">
          {{ scope.row.courseName }}
        </template>
      </el-table-column>
      <el-table-column label="题目信息" width="360">
        <template slot-scope="scope">
          {{ scope.row.question }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" width="180">
        <template slot-scope="scope">
          {{ scope.row.gmtModified }}
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="updateQuestion(scope.row.id)" type="primary" size="small">修改试题</el-button>
          <el-button @click="deleteQuestion(scope.row.id)" type="danger" size="small">删除试题</el-button>
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
        options: [ //题库类型
          {
            value: '选择题',
            label: '选择题'
          },
          {
            value: '填空题',
            label: '填空题'
          },
          {
            value: '判断题',
            label: '判断题'
          },
        ],
        optionValue: '选择题', //题型选中值
      };
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData(page = 1) { //分页查询所有试卷信息
        if (this.$route.query && this.$route.query.optionValue) {
          this.optionValue = this.$route.query.optionValue
          console.log(this.optionValue)
        }
        this.page = page
        this.getParams();
      },

      getParams() {
        if (this.optionValue === '选择题') {
          exam.getMultQuestion(this.page, this.limit).then(response => {
            if (response.success === true) {
              this.list = response.data.rows
              this.total = response.data.total
            }
          })
        }
        if (this.optionValue === '填空题') {
          exam.getFillQuestion(this.page, this.limit).then(response => {
            if (response.success === true) {
              this.list = response.data.rows
              this.total = response.data.total
            }
          })
        }
        if (this.optionValue === '判断题') {
          exam.getJudgeQuestion(this.page, this.limit).then(response => {
            if (response.success === true) {
              this.list = response.data.rows
              this.total = response.data.total
            }
          })
        }
      },
      updateQuestion(id) {
        this.$router.push({
          path: '/edu/library/addQuestion',
          query: {
            optionValue: this.optionValue,
            id: id
          }
        })
      },

      deleteQuestion(id) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.optionValue === '选择题') {
            return exam.deleteMultiQuestionById(id)
          }
          if (this.optionValue === '填空题') {
            return exam.deleteFillQuestionById(id)
          }
          if (this.optionValue === '判断题') {
            return exam.deleteJudgeQuestionById(id)
          }
        }).then(() => { // 如果上一个then成功则执行此处的then回调
          this.fetchData()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch((response) => { // 失败
          if (response === 'cancel') {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          }
        })
      }
    }
  };
</script>

<style lang="scss" scoped>
  .add {
    margin: 0px 40px;

    .box {
      padding: 0px 20px;

      ul li {
        margin: 10px 0px;
        display: flex;
        align-items: center;

        .el-input {
          width: 6%;
        }

        .w150 {
          margin-left: 20px;
          width: 7%;
        }
      }
    }

    .el-icon-circle-plus {
      margin-right: 10px;
    }

    .icon-daoru-tianchong {
      margin-right: 10px;
    }

    .append {
      margin: 0px 20px;

      ul {
        display: flex;
        align-items: center;

        li {
          margin-right: 20px;
        }
      }

      .change {
        margin-top: 20px;
        padding: 20px 16px;
        background-color: #E7F6F6;
        border-radius: 4px;

        .title {
          padding-left: 6px;
          color: #2f4f4f;

          span:nth-child(1) {
            margin-right: 6px;
          }

          .answer {
            margin: 20px 0px 20px 8px;
          }

          .el-textarea {
            width: 98% !important;
          }
        }

        .options {
          ul {
            display: flex;
            flex-direction: column;
          }

          ul li {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 98%;
            margin: 10px 0px;

            span {
              margin-right: 20px;
            }
          }
        }

        .submit {
          display: flex;
          justify-content: center;
          align-items: center;
        }
      }

      .fill {
        .fillAnswer {
          display: flex;
          justify-content: center;
          align-items: center;

          span {
            margin-right: 6px;
          }

          .el-input {
            width: 91% !important;
          }
        }

        .analysis {
          margin-top: 20px;
          margin-left: 5px;
        }
      }

      .judge {
        .judgeAnswer {
          margin-left: 20px;
          margin-bottom: 20px;
        }
      }

      .w150 {
        width: 150px;
      }

      li:nth-child(2) {
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
  }
</style>


