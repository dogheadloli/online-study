<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="active" finish-status="success" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="编辑课程章节"/>
      <el-step title="发布课程"/>
    </el-steps>

    <!-- 课程信息表单-->
    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input placeholder="请输入标题" v-model="courseInfo.title"/>
      </el-form-item>

      <!-- 所属分类 -->
      <el-form-item label="课程类别">
        <el-cascader
          v-model="courseInfo.subjectId"
          placeholder="请选择"
          :options="subjectList"
        >
        </el-cascader>
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程教师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="课时数">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>

      <!-- 课程封面-->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleCoverSuccess"
          :before-upload="beforeCoverUpload"
          :action="UPLOAD_API+'/admin/oss/file/upload?host=cover'"
          class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/>
        元
      </el-form-item>
    </el-form>

    <div style="text-align:center">
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next()">保存并下一步</el-button>
    </div>
  </div>
</template>

<script>
  import course from '@/api/edu/course'
  import subject from '@/api/edu/subject'
  import teacher from '@/api/edu/teacher'
  import Tinymce from '@/components/Tinymce'

  const defaultForm = {
    title: '',
    subjectId: '',
    teacherId: '',
    lessonNum: 0,
    description: '',
    cover: process.env.OSS_PATH + '/cover/default.jpg',
    price: 0
  }

  export default {
    // 组件注册
    components: {Tinymce},
    data() {
      return {
        active: 0,
        courseInfo: defaultForm,
        saveBtnDisabled: false, // 保存按钮是否禁用
        subjectList: [],
        teacherList: [], // 讲师列表
        BASE_API: process.env.BASE_API, // api接口地址
        UPLOAD_API: process.env.UPLOAD_API
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
        if (this.$route.params && this.$route.params.id) { // 回显
          const id = this.$route.params.id
          // 根据id获取课程基本信息
          this.fetchCourseInfoById(id)
        } else { // 新增
          this.courseInfo = {...defaultForm}
          // 获取课程类别列表
          this.initSubjectList()
        }

        // 获取讲师列表
        this.initTeacherList()
      },

      fetchCourseInfoById(id) {
        course.getCourseInfoById(id).then(response => {
          this.courseInfo = response.data.item

          // 填充一级类别和二级类别
          subject.getNestedTreeList().then(response => {
            this.subjectList = response.data.items

          /*  // 遍历一级类别列表，找到响应的二级类别列表，并填充二级类别
            for (let i = 0; i < this.subjectNestedList.length; i++) {
              if (this.subjectNestedList[i].id === this.courseInfo.subjectParentId) {
                // 当或得到当前数据的一级类别所对应的的二级类别列表时，将二级类别下拉列表进行填充
                this.subSubjectList = this.subjectNestedList[i].children
              }
            }*/
          })
        })
      },

      // 获取讲师列表
      initTeacherList() {
        teacher.getList().then(response => {
          this.teacherList = response.data.items
        })
      },

      // 初始化类别下拉列表数据
      initSubjectList() {
        subject.getNestedTreeList().then(response => {
          // this.subjectNestedList = response.data.items
          this.subjectList = response.data.items
        })
      },

      next() {
        console.log('next')
        this.saveBtnDisabled = true
        if (!this.courseInfo.id) {
          this.saveData()
        } else {
          this.updateData()
        }
      },

      // 保存
      saveData() {
        console.log(this.courseInfo.subjectId);
        let subIds = eval(this.courseInfo.subjectId);
        this.courseInfo.subjectId = subIds[subIds.length - 1];
        console.log(this.courseInfo.subjectId);
        course.saveCourseInfo(this.courseInfo).then(response => {
          this.$message({
            type: 'success',
            message: '保存成功!'
          })
          return response// 将响应结果传递给下一个then
        }).then(response => {
          this.$router.push({path: '/edu/course/chapter/' + response.data.courseId})
        })
      },

      updateData() {
        this.saveBtnDisabled = true
        course.updateCourseInfoById(this.courseInfo).then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
        }).then(() => {
          this.$router.push({path: '/edu/course/chapter/' + this.courseInfo.id})
        })
      },

      handleCoverSuccess(res, file) {
        console.log(res)// 上传响应
        console.log(URL.createObjectURL(file.raw))// base64编码
        this.courseInfo.cover = res.data.url
      },

      beforeCoverUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传封面图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传封面图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      }

    }
  }
</script>

<style scoped>
  .tinymce-container {
    position: relative;
    line-height: normal;
  }
</style>
