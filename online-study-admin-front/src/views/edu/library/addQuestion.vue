<template>
  <div class="add">
    <span slot="label"><i class="el-icon-circle-plus"></i>添加试题</span>
    <section class="append">
      <ul>
        <li>
          <span>题目类型:</span>
          <el-select v-model="optionValue" placeholder="请选择题型" class="w150">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </li>

        <li v-if="optionValue == '选择题'">
          <span>正确选项:</span>
          <el-select v-model="multiQuestion.rightAnswer" placeholder="选择正确答案" class="w150">
            <el-option
              v-for="item in rights"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </li>
      </ul>

      <!-- 选择题部分 -->
      <div class="change" v-if="optionValue == '选择题'">
        <div class="title">
          <el-tag>题目:</el-tag>
          <span>在下面的输入框中输入题目,例如：“DNS 服务器和DHCP服务器的作用是（）”</span>
          <el-input
            type="textarea"
            rows="4"
            v-model="multiQuestion.question"
            placeholder="请输入题目内容"
            resize="none"
            class="answer">
          </el-input>
        </div>

        <div class="options">
          <ul>
            <li>
              <el-tag type="success">A</el-tag>
              <el-input
                placeholder="请输入选项A的内容"
                v-model="multiQuestion.answerA"
                clearable="">
              </el-input>
            </li>
            <li>
              <el-tag type="success">B</el-tag>
              <el-input
                placeholder="请输入选项B的内容"
                v-model="multiQuestion.answerB"
                clearable="">
              </el-input>
            </li>
            <li>
              <el-tag type="success">C</el-tag>
              <el-input
                placeholder="请输入选项C的内容"
                v-model="multiQuestion.answerC"
                clearable="">
              </el-input>
            </li>
            <li>
              <el-tag type="success">D</el-tag>
              <el-input
                placeholder="请输入选项D的内容"
                v-model="multiQuestion.answerD"
                clearable="">
              </el-input>
            </li>
          </ul>
        </div>
        <div class="title">
          <el-tag>解析:</el-tag>
          <span>在下面的输入框中输入题目解析</span>
          <el-input
            type="textarea"
            rows="4"
            v-model="multiQuestion.analysis"
            placeholder="请输入答案解析"
            resize="none"
            class="answer">
          </el-input>
        </div>
        <div class="submit">
          <el-button type="primary" @click="multiSubmit()">立即添加</el-button>
        </div>
      </div>
      <!-- 填空题部分 -->
      <div class="change fill" v-if="optionValue == '填空题'">
        <div class="title">
          <el-tag>题目:</el-tag>
          <span>在下面的输入框中输入题目,例如：“从计算机网络系统组成的角度看，计算机网络可以分为()和()。”</span>
          <el-input
            type="textarea"
            rows="4"
            v-model="fillQuestion.question"
            placeholder="请输入题目内容"
            resize="none"
            class="answer">
          </el-input>
        </div>
        <div class="fillAnswer">
          <el-tag>正确答案:</el-tag>
          <el-input v-model="fillQuestion.answer"></el-input>
        </div>
        <div class="title analysis">
          <el-tag type="success">解析:</el-tag>
          <span>下方输入框中输入答案解析</span>
          <el-input
            type="textarea"
            rows="4"
            v-model="fillQuestion.analysis"
            placeholder="请输入答案解析"
            resize="none"
            class="answer">
          </el-input>
        </div>
        <div class="submit">
          <el-button type="primary" @click="fillSubmit()">立即添加</el-button>
        </div>
      </div>
      <!-- 判断题 -->
      <div class="change judge" v-if="optionValue == '判断题'">
        <div class="title">
          <el-tag>题目:</el-tag>
          <span>在下面的输入框中输入题目,例如：“ 在计算机网络中,所有的主机构成了网络的资源子网。”</span>
          <el-input
            type="textarea"
            rows="4"
            v-model="judgeQuestion.question"
            placeholder="请输入题目内容"
            resize="none"
            class="answer">
          </el-input>
        </div>
        <div class="judgeAnswer">
          <el-radio v-model="judgeQuestion.answer" label="T">正确</el-radio>
          <el-radio v-model="judgeQuestion.answer" label="F">错误</el-radio>
        </div>
        <div class="title">
          <el-tag>解析:</el-tag>
          <span>在下面的输入框中输入题目解析</span>
          <el-input
            type="textarea"
            rows="4"
            v-model="judgeQuestion.analysis"
            placeholder="请输入答案解析"
            resize="none"
            class="answer">
          </el-input>
        </div>
        <div class="submit">
          <el-button type="primary" @click="judgeSubmit()">立即添加</el-button>
        </div>
      </div>
    </section>

  </div>
</template>

<script>
  import exam from '@/api/edu/exam'

  export default {
    data() {
      return {
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
        rights: [ //正确答案
          {
            value: 'A',
            label: 'A'
          },
          {
            value: 'B',
            label: 'B'
          },
          {
            value: 'C',
            label: 'C'
          },
          {
            value: 'D',
            label: 'D'
          },
        ],
        libraryId: null,
        optionValue: '选择题', //题型选中值
        multiQuestion: {
          id: '',
          libraryId: '',
          question: '',
          answerA: '',
          answerB: '',
          answerC: '',
          answerD: '',
          rightAnswer: '',
          analysis: ''
        },
        fillQuestion: {
          id: '',
          libraryId: '',
          question: '',
          answer: '',
          analysis: ''
        },
        judgeQuestion: {
          id: '',
          libraryId: '',
          question: '',
          answer: '',
          analysis: ''
        }
      };
    },
    created() {
      this.getParams()
    },
    methods: {

      getParams() {
        if (this.$route.query && this.$route.query.libraryId) {
          this.libraryId = this.$route.query.libraryId
          console.log(this.libraryId)
        }
        if (this.$route.query && this.$route.query.optionValue) {
          this.optionValue = this.$route.query.optionValue
          if (this.optionValue === '选择题') {
            exam.getMultQuestionById(this.$route.query.id).then(response => {
              this.multiQuestion = response.data.item
            })
          }
          if (this.optionValue === '填空题') {
            exam.getFillQuestionById(this.$route.query.id).then(response => {
              this.fillQuestion = response.data.item
            })
          }
          if (this.optionValue === '判断题') {
            exam.getJudgeQuestionById(this.$route.query.id).then(response => {
              this.judgeQuestion = response.data.item
            })
          }
        }
      },
      multiSubmit() { //选择题题库提交
        if (this.multiQuestion.id === '') {
          this.multiQuestion.libraryId = this.libraryId;
          exam.addMultiQuestion(this.multiQuestion).then(res => {
            let success = res.success
            if (success === true) {
              this.$message({
                message: '已添加到题库',
                type: 'success'
              })
            }
          })
        } else {
          exam.updateMultiQuestion(this.multiQuestion).then(res => {
            let success = res.success
            if (success === true) {
              this.$message({
                message: '已添加到题库',
                type: 'success'
              })
            }
          })
        }
        this.$router.push({
          path: '/edu/library/question',
          query: {
            optionValue: this.optionValue
          }
        })
      },
      fillSubmit() { //填空题提交
        if (this.fillQuestion.id === '') {
          this.fillQuestion.libraryId = this.libraryId;
          exam.addFillQuestion(this.fillQuestion).then(res => {
            let success = res.success
            if (success === true) {
              this.$message({
                message: '已添加到题库',
                type: 'success'
              })

            }
          })
        } else {
          exam.updateFillQuestion(this.fillQuestion).then(res => {
            let success = res.success
            if (success === true) {
              this.$message({
                message: '已添加到题库',
                type: 'success'
              })
            }
          })
        }
        this.$router.push({
          path: '/edu/library/question',
          query: {
            optionValue: this.optionValue
          }
        })
      },
      judgeSubmit() { //判断题提交
        if (this.judgeQuestion.id === '') {
          this.judgeQuestion.libraryId = this.libraryId;
          exam.addJudgeQuestion(this.judgeQuestion).then(res => {
            let success = res.success
            if (success === true) {
              this.$message({
                message: '已添加到题库',
                type: 'success'
              })
            }
          })
        } else {
          exam.updateJudgeQuestion(this.judgeQuestion).then(res => {
            let success = res.success
            if (success === true) {
              this.$message({
                message: '已添加到题库',
                type: 'success'
              })
            }
          })
        }
        this.$router.push({
          path: '/edu/library/question',
          query: {
            optionValue: this.optionValue
          }
        })
      }
    },
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


