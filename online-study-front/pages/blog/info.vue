<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新文章</h2>

    <hr/>

    <!-- 课程信息表单-->
    <el-form label-width="120px">

      <el-form-item label="文章标题">
        <el-col :span="12">
          <el-input placeholder="请输入标题" v-model="blogInfo.title"/>
        </el-col>
      </el-form-item>

      <!-- 所属分类 -->
      <el-form-item label="文章类别">
        <el-cascader
          emitPath="false"
          v-model="blogInfo.subjectId"
          placeholder="请选择"
          :options="subjectList"
        >
        </el-cascader>
      </el-form-item>

      <!-- 文章内容-->

      <el-form-item label="文章内容">

        <vue-editor v-model="blogInfo.description"></vue-editor>

      </el-form-item>

    </el-form>


    <div style="text-align:center">
      <el-button :disabled="saveBtnDisabled" type="primary" @click="save()">发布</el-button>
    </div>


  </div>

</template>

<script>

  import subject from '@/api/subject'
  import blog from '@/api/blog'
  import cookie from "js-cookie";


  const defaultForm = {
    title: '',
    subjectId: [],
    userId:'',
    description: ''
  }

  export default {

    data() {
      return {

        blogInfo: defaultForm,
        saveBtnDisabled: false, // 保存按钮是否禁用
        subjectList: [],
        loginInfo: {
          id: '',
          age: '',
          avatar: '',
          mobile: '',
          nickname: '',
          sex: ''
        }
      }
    },

    watch: {
      $route(to, from) {
        console.log('watch $route')
        this.init()
      }
    },

    created() {
      console.log('info created')
      this.init()
    },

    methods: {

      // 初始化所有表单数据
      init() {
        console.log('init')
        this.blogInfo = {...defaultForm}
        // 获取课程类别列表
        this.initSubjectList()

      },

      // 初始化类别下拉列表数据
      initSubjectList() {
        console.log('1')
        subject.getNestedTreeList().then(response => {
          this.subjectList = response.data.data.items
        })
      },

      save() {
        console.log('next')
        this.saveBtnDisabled = true
        this.saveData()
      },

      // 保存
      saveData() {
        console.log(this.blogInfo.subjectId);
        //从cookie获取用户信息
        let userStr = cookie.get('guli_ucenter')
        // console.log(userStr)
        // 把字符串转换json对象(js对象)
        if (userStr) {
          this.loginInfo = JSON.parse(userStr)
          this.blogInfo.userId = this.loginInfo.id
        } else {
          this.$message({
            type: 'error',
            message: '请先登录!'
          })
        }
        blog.saveBlogInfo(this.blogInfo).then(response => {
          this.$message({
            type: 'success',
            message: '保存成功!'
          })
          return response// 将响应结果传递给下一个then
        }).then(response => {
          this.$router.push({path: '/blog'})
        })
      }

    }
  }
</script>

<style lang="scss" scoped>
</style>
