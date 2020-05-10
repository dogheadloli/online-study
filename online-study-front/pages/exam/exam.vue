<!--考生答题界面-->
<template>
  <div id="answer">
    <div class="flexarea">
      <!--左边题目编号区-->
      <div class="left">
        <ul class="l-top">
          <li>
            <a href="javascript:;"></a>
            <span>当前</span>
          </li>
          <li>
            <a href="javascript:;"></a>
            <span>未答</span>
          </li>
          <li>
            <a href="javascript:;"></a>
            <span>已答</span>
          </li>
        </ul>

        <div class="l-bottom">
          <div class="item">
            <p>选择题</p>
            <ul>
              <li v-for="(list, index1) in data.multiQuestions" :key="index1">
                <a href="javascript:;" @click="getQuestion(index1,1)"
                   :class="{'border': currentIndex == index1 && currentType == 1,'bg': multiAnswer[index1] != ''}">
                  {{index1+1}}
                </a>
              </li>
            </ul>
          </div>

          <div class="item">
            <p>填空题</p>
            <ul>
              <li v-for="(list, index2) in data.fillQuestions" :key="index2">
                <a href="javascript:;" @click="getQuestion(index2,2)"
                   :class="{'border': currentIndex == index2 && currentType == 2,'bg': fillAnswer[index2] != ''}">
                  {{index2+1}}
                </a>
              </li>
            </ul>
          </div>

          <div class="item">
            <p>判断题</p>
            <ul>
              <li v-for="(list, index3) in data.judgeQuestions" :key="index3">
                <a href="javascript:;" @click="getQuestion(index3,3)"
                   :class="{'border': currentIndex == index3 && currentType == 3,'bg':judgeAnswer[index3] != ''}">
                  {{index3+1}}</a>
              </li>
            </ul>
          </div>

          <div v-if="!isPractice" class="final" v-on:click="commit()">结束考试</div>
          <div v-if="isPractice" class="final" v-on:click="exit()">返回首页</div>

        </div>
      </div>


      <!--右边选择答题区-->
      <div class="right">
        <div class="title">
          <p>{{title}}</p>
          <i class="iconfont icon-right auto-right"></i>
          <span>全卷共{{data.questionNum}}题

            </span>
        </div>

        <div v-if="currentType == 1">
          <div class="content">
            <p class="topic"><span class="number">{{currentIndex+1}}</span>{{currentQuestion.question}}</p>


            <el-radio-group v-model="multiAnswer[currentIndex]">
              <el-radio label="A">{{currentQuestion.answerA}}</el-radio>
              <el-radio label="B">{{currentQuestion.answerB}}</el-radio>
              <el-radio label="C">{{currentQuestion.answerC}}</el-radio>
              <el-radio label="D">{{currentQuestion.answerD}}</el-radio>
            </el-radio-group>

            <div class="analysis" v-if="isPractice">
              <ul>
                <li>
                  <el-tag type="success">正确答案：</el-tag>
                  <span class="right">{{currentQuestion.rightAnswer}}</span>
                </li>
                <li>
                  <el-tag>结果：</el-tag>
                  <span v-if="currentQuestion.rightAnswer == multiAnswer[currentIndex]" class="answerRight">回答正确</span>
                  <span v-if="currentQuestion.rightAnswer != multiAnswer[currentIndex]" class="answerFail">回答错误</span>
                </li>
                <li>
                  <el-tag>答案解析：</el-tag>
                </li>
                <li>{{currentQuestion.analysis == null ? '暂无解析': currentQuestion.analysis}}</li>
              </ul>
            </div>
          </div>
        </div>

        <div class="fill" v-if="currentType == 2">

          <div class="content">
            <p class="topic"><span class="number">{{currentIndex+1}}</span>{{currentQuestion.question}}</p>

            <el-input placeholder="请填在此处"
                      v-model="fillAnswer[currentIndex]"
                      clearable
            >
            </el-input>


            <div class="analysis" v-if="isPractice">
              <ul>
                <li>
                  <el-tag type="success">正确答案：</el-tag>
                  <span class="right">{{currentQuestion.answer}}</span>
                </li>
                <li>
                  <el-tag>结果：</el-tag>
                  <span v-if="currentQuestion.answer == fillAnswer[currentIndex]" class="answerRight">回答正确</span>
                  <span v-if="currentQuestion.answer != fillAnswer[currentIndex]" class="answerFail">回答错误</span>
                </li>
                <li>
                  <el-tag>答案解析：</el-tag>
                </li>
                <li>{{currentQuestion.analysis == null ? '暂无解析': currentQuestion.analysis}}</li>
              </ul>
            </div>
          </div>
        </div>

        <div class="judge" v-if="currentType == 3">
          <div class="content">
            <p class="topic"><span class="number">{{currentIndex+1}}</span>{{currentQuestion.question}}</p>

            <el-radio-group v-model="judgeAnswer[currentIndex]">
              <el-radio :label="'T'">正确</el-radio>
              <el-radio :label="'F'">错误</el-radio>
            </el-radio-group>

            <div class="analysis" v-if="isPractice">
              <ul>
                <li>
                  <el-tag type="success">正确答案：</el-tag>
                  <span class="right">{{currentQuestion.answer}}</span>
                </li>
                <li>
                  <el-tag>结果：</el-tag>
                  <span v-if="currentQuestion.answer == judgeAnswer[currentIndex]" class="answerRight">回答正确</span>
                  <span v-if="currentQuestion.answer != judgeAnswer[currentIndex]" class="answerFail">回答错误</span>
                </li>
                <li>
                  <el-tag>答案解析：</el-tag>
                </li>
                <li>{{currentQuestion.analysis == null ? '暂无解析': currentQuestion.analysis}}</li>
              </ul>
            </div>
          </div>
        </div>

        <div class="operation">
          <ul class="end">
            <el-button-group>
              <el-button type="primary" v-on:click="preQuestion(currentIndex)" icon="el-icon-arrow-left">上一题</el-button>
              <el-button type="primary" v-on:click="nextQuestion(currentIndex)">下一题<i
                class="el-icon-arrow-right el-icon--right"></i></el-button>
            </el-button-group>
          </ul>
        </div>

      </div>

    </div>

  </div>
</template>

<script>
  import exam from '@/api/exam'

  export default {
    data() {
      return {
        data: {
          multiQuestions: [],
          judgeQuestions: [],
          fillQuestions: [],
          multiQuestionNum: 0,
          judgeQuestionNum: 0,
          fillQuestionNum: 0,
          questionNum: 0,
        },
        title: "请选择正确的选项",
        currentQuestion: {},
        currentAnswer: '',
        currentIndex: 0,
        currentType: 1, //当前题型类型  1--选择题  2--填空题  3--判断题
        multiAnswer: [],
        fillAnswer: [], //保存所有填空题答案
        judgeAnswer: [], //保存所有判断题答案
        right: [[]], //储存是否正确
        isPractice: false,
      }
    },

    created() {
      this.getExamData()
    },

    methods: {

      getExamData() { //获取当前试卷所有信息
        let id = this.$route.query.id //获取路由传递过来的试卷编号
        exam.getExam(id).then(res => {  //通过examCode请求试卷详细信息
          this.data = res.data.data //获取考试详情

          this.right[1] = []
          for (let i = 0; i < this.data.multiQuestionNum; i++) {
            this.multiAnswer[i] = ''
            this.right[1][i] = false
          }
          this.right[2] = []
          for (let i = 0; i < this.data.fillQuestionNum; i++) {
            this.fillAnswer[i] = ''
            this.right[2][i] = false
          }
          this.right[3] = []
          for (let i = 0; i < this.data.judgeQuestionNum; i++) {
            this.judgeAnswer[i] = ''
          }

          this.currentQuestion = this.data.multiQuestions[this.currentIndex]

        })

      },

      /* saveMultiAnswer(index, myAnswer, rightAnswer) {
         this.multiAnswer[index] = myAnswer
         if (myAnswer === rightAnswer) {
           this.right[1][index] = true
         }
         this.isAnswer[1][index] = true
       },

       saveFillAnswer(index, myAnswer, rightAnswer) {
         this.fillAnswer[index] = myAnswer
         if (myAnswer === rightAnswer) {
           this.right[2][index] = true
         }
         this.isAnswer[2][index] = true
       },
       saveJudgeAnswer(index, myAnswer, rightAnswer) {
         this.judgeAnswer[index] = myAnswer
         if (myAnswer === rightAnswer) {
           this.right[3][index] = true
         }
         this.isAnswer[3][index] = true
       },*/


      nextQuestion(index) {
        if (this.currentType === 1) {
          let newIndex = index + 1
          if (newIndex === this.data.multiQuestionNum) {
            this.currentType = 2;
            this.currentIndex = 0;
          } else {
            this.currentIndex = newIndex
          }
        } else if (this.currentType === 2) {
          let newIndex = index + 1
          if (newIndex === this.data.fillQuestionNum) {
            this.currentType = 3;
            this.currentIndex = 0;
          } else {
            this.currentIndex = newIndex
          }
        } else if (this.currentType === 3) {
          let newIndex = index + 1
          if (newIndex === this.data.judgeQuestionNum) {
            this.currentType = 3;
            this.currentIndex = index;
          } else {
            this.currentIndex = newIndex
          }
        }

        this.getCurrentQuestion()
      },

      preQuestion(index) {
        if (this.currentType === 1) {
          let newIndex = index - 1
          if (newIndex === -1) {
            this.currentType = 1;
            this.currentIndex = 0;
          } else {
            this.currentIndex = newIndex
          }
        } else if (this.currentType === 2) {
          let newIndex = index - 1
          if (newIndex === -1) {
            this.currentType = 1;
            this.currentIndex = this.data.multiQuestionNum - 1;
          } else {
            this.currentIndex = newIndex
          }
        } else if (this.currentType === 3) {
          let newIndex = index - 1
          if (newIndex === -1) {
            this.currentType = 2;
            this.currentIndex = this.data.fillQuestionNum - 1;
          } else {
            this.currentIndex = newIndex
          }
        }

        this.getCurrentQuestion()
      },

      getCurrentQuestion() {
        if (this.currentType === 1) {
          this.currentQuestion = this.data.multiQuestions[this.currentIndex]
        }
        if (this.currentType === 2) {
          this.currentQuestion = this.data.fillQuestions[this.currentIndex]
        }
        if (this.currentType === 3) {
          this.currentQuestion = this.data.judgeQuestions[this.currentIndex]
        }
      },

      getQuestion(index, type) {
        if (type === 1) {
          this.currentIndex = index
          this.currentType = type
          this.currentQuestion = this.data.multiQuestions[index]
        }
        if (type === 2) {
          this.currentIndex = index
          this.currentType = type
          this.currentQuestion = this.data.fillQuestions[index]
        }
        if (type === 3) {
          this.currentIndex = index
          this.currentType = type
          this.currentQuestion = this.data.judgeQuestions[index]
        }
      },

      commit() {
        this.$confirm('确认要提交试卷？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.isPractice = true
          this.getQuestion(0, 1)
          window.scrollTo(0, 0)
        })
      },

      exit() {
        this.$router.push('/exam')
      }
    }
  }
</script>

<style lang="scss">
  .iconfont.icon-time {
    color: #2776df;
    margin: 0px 6px 0px 20px;
  }

  .analysis {
    margin-top: 20px;

    .answerRight {
      color: #32CD32;
      font-size: 18px;
      padding: 0px 6px;
      border-radius: 4px;
      margin-left: 20px;
    }

    .answerFail {
      color: #FF0000;
      font-size: 18px;
      padding: 0px 6px;
      border-radius: 4px;
      margin-left: 20px;
    }

    .right {
      color: #2776df;
      font-size: 18px;
      padding: 0px 6px;
      border-radius: 4px;
      margin-left: 20px;
    }

    ul li:nth-child(2) {
      margin: 20px 0px;
    }

    ul li:nth-child(4) {
      padding: 10px;
      background-color: #E1FFFF;
      border-radius: 4px;
    }
  }

  .analysis span:nth-child(1) {
    font-size: 18px;
  }

  .mark {
    position: absolute;
    width: 4px;
    height: 4px;
    content: "";
    background-color: red;
    border-radius: 50%;
    top: 0px;
    left: 22px;
  }

  .border {
    position: relative;
    border: 1px solid #FF90AA !important;
  }

  .bg {
    background-color: #5188b8 !important;
  }

  .fill .el-input {
    display: inline-flex;
    width: 150px;
    margin-left: 20px;

    .el-input__inner {
      border: 1px solid transparent;
      border-bottom: 1px solid #eee;
      padding-left: 20px;
    }
  }

  /* slider过渡效果 */
  .slider-fade-enter-active {
    transition: all .3s ease;
  }

  .slider-fade-leave-active {
    transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .slider-fade-enter, .slider-fade-leave-to {
    transform: translateX(-100px);
    opacity: 0;
  }

  .operation .end li:nth-child(2) {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: rgb(39, 118, 223);
    border-radius: 50%;
    width: 50px;
    height: 50px;
    color: #fff;
  }

  .operation .end li {
    cursor: pointer;
    margin: 0 100px;
  }

  .operation {
    font-size: 18px;
    background-color: #fff;
    border-radius: 4px;
    padding: 10px 0px;
    margin-right: 10px;
  }

  .operation .end {
    display: flex;
    justify-content: center;
    align-items: center;
    color: rgb(39, 118, 223);
  }

  .content .number {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    width: 20px;
    height: 20px;
    background-color: rgb(135, 206, 235);
    border-radius: 4px;
    margin-right: 4px;
  }

  .content {
    font-size: 16px;
    padding: 0px 20px;
    line-height: 150%;
  }

  .content .topic {
    font-size: 18px;
    padding: 20px 0px;
    padding-top: 30px;
  }

  .right .content {
    background-color: #fff;
    margin: 10px;
    margin-left: 0px;
    height: 470px;
  }

  .content .el-radio-group label {
    color: #000;
    margin: 5px 0px;
  }

  .content .el-radio-group {
    display: flex;
    flex-direction: column;
  }

  .right .title p {
    margin-left: 20px;
  }

  .flexarea {
    display: flex;
  }

  .flexarea .right {
    flex: 1;
  }

  .auto-right {
    margin-left: auto;
    color: #2776df;
    margin-right: 10px;
  }

  .right .title {
    font-size: 18px;
    margin-right: 10px;
    padding-right: 30px;
    display: flex;
    margin-top: 10px;
    background-color: #fff;
    height: 50px;
    line-height: 50px;
  }

  .clearfix {
    clear: both;
  }

  .l-bottom .final {
    cursor: pointer;
    display: inline-block;
    text-align: center;
    background-color: rgb(39, 118, 223);
    width: 240px;
    margin: 20px 0px 20px 10px;
    border-radius: 4px;
    height: 30px;
    line-height: 30px;
    color: #fff;
    margin-top: 22px;
  }

  #answer .left .item {
    padding: 0px;
    font-size: 16px;
  }

  .l-bottom {
    border-radius: 4px;
    background-color: #fff;
  }

  .l-bottom .item p {
    margin-bottom: 15px;
    margin-top: 10px;
    color: #000;
    margin-left: 10px;
    letter-spacing: 2px;
  }

  .l-bottom .item li {
    width: 15%;
    margin-left: 5px;
    margin-bottom: 10px;
  }

  .l-bottom .item {
    display: flex;
    flex-direction: column;
  }

  .l-bottom .item ul {
    width: 100%;
    margin-bottom: -8px;
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
  }

  .l-bottom .item ul li a {
    position: relative;
    justify-content: center;
    display: inline-flex;
    align-items: center;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    background-color: #fff;
    border: 1px solid #eee;
    text-align: center;
    color: #000;
    font-size: 16px;
  }

  .left .l-top {
    display: flex;
    justify-content: space-around;
    padding: 16px 0px;
    border: 1px solid #eee;
    border-radius: 4px;
    margin-bottom: 10px;
    background-color: #fff;
  }

  .left {
    font-size: 16px;
    width: 260px;
    height: 100%;
    margin: 10px 10px 0px 10px;
  }

  .left .l-top li:nth-child(2) a {
    border: 1px solid #eee;
  }

  .left .l-top li:nth-child(3) a {
    background-color: #5188b8;
    border: none;
  }

  .left .l-top li:nth-child(4) a {
    position: relative;
    border: 1px solid #eee;
  }

  .left .l-top li:nth-child(4) a::before {
    width: 4px;
    height: 4px;
    content: " ";
    position: absolute;
    background-color: red;
    border-radius: 50%;
    top: 0px;
    left: 16px;
  }

  .left .l-top li {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
  }

  .left .l-top li a {
    display: inline-block;
    padding: 10px;
    border-radius: 50%;
    background-color: #fff;
    border: 1px solid #FF90AA;
  }

  #answer .top {
    background-color: rgb(39, 118, 223);
  }

  #answer .item {
    color: #fff;
    display: flex;
    padding: 20px;
    font-size: 20px;
  }

  #answer .top .item li:nth-child(1) {
    margin-right: 10px;
  }

  #answer .top .item li:nth-child(3) {
    position: relative;
    margin-left: auto;
  }

  #answer {
    padding-bottom: 30px;
  }

  .icon20 {
    font-size: 20px;
    font-weight: bold;
  }

  .item .msg {
    padding: 10px 15px;
    border-radius: 4px;
    top: 47px;
    right: -30px;
    color: #6c757d;
    position: absolute;
    border: 1px solid rgba(0, 0, 0, .15);
    background-color: #fff;
  }

  .item .msg p {
    font-size: 16px;
    width: 200px;
    text-align: left;
  }
</style>
