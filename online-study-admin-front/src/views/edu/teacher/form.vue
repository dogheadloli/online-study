<template>
  <div>
    <div class="app-container">
      <el-form :model="teacher" label-width="120px">
        <el-form-item label="教师名称" :prop="'name'" :rules='rules.name'>
          <el-input placeholder="请输入教师名称" v-model="teacher.name"/>
        </el-form-item>
        <el-form-item label="教师排序">
          <el-input-number v-model="teacher.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="教师类型" :prop="'level'" :rules='rules.level'>
          <el-select v-model="teacher.level" clearable placeholder="请选择">
            <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
            <el-option :value="1" label="高级教师"/>
            <el-option :value="2" label="首席教师"/>
          </el-select>
        </el-form-item>
        <el-form-item label="教师资历" :prop="'career'" :rules='rules.career'>
          <el-input placeholder="请输教师资历" v-model="teacher.career"/>
        </el-form-item>
        <el-form-item label="教师简介" :prop="'intro'" :rules='rules.intro'>
          <el-input placeholder="请输教师简介" v-model="teacher.intro" :rows="10" type="textarea"/>
        </el-form-item>

        <!-- 讲师头像：TODO -->
        <el-form-item label="头像上传">
          <pan-thumb :image="teacher.avatar"/>
          <el-button type="primary" icon="upload" @click="imagecropperShow=true">点击上传
          </el-button>

          <image-cropper
            v-show="imagecropperShow"
            :width="300"
            :height="300"
            :key="imagecropperKey"
            :url="UPLOAD_API+'/oss/file/upload'"
            :max-size="2048"
            field="file"
            @close="close"
            @crop-upload-success="cropSuccess"/>
        </el-form-item>

        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import teacher from '@/api/edu/teacher'
  import ImageCropper from '@/components/ImageCropper'
  import PanThumb from '@/components/PanThumb'

  export default {
    components: {ImageCropper, PanThumb},
    data() {
      return {
        rules: {
          name: {
            required: true,
            message: "请输入教师名称",
            trigger:"blur"
          },
          level: {
            required: true,
            message: "请选择教师类型",
            trigger:"change"
          },
          career: {
            required: true,
            message: "请输入教师资历",
            trigger:"blur"
          },
          intro: {
            required: true,
            message: "请输入教师简介",
            trigger:"blur"
          }
        },
        teacher: {
          name: '',
          sort: 0,
          level: 1,
          career: '',
          intro: '',
          avatar: process.env.OSS_PATH + '/avatar/default.jpg'
        },
        saveBtnDisabled: false, // 保存按钮是否禁用,
        imagecropperShow: false,
        imagecropperKey: 0,
        BASE_API: process.env.BASE_API,
        UPLOAD_API: process.env.UPLOAD_API
      }
    },

    created() {
      console.log('created')
      // 判断路由参数
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.fetchDataById(id)
      }
    },

    methods: {

      saveOrUpdate() {
        this.saveBtnDisabled = true
        if (!this.teacher.id) {
          this.saveData()
        } else {
          this.updateData()
        }
      },

      // 保存
      saveData() {
        teacher.save(this.teacher).then(response => {
          return this.$message({
            type: 'success',
            message: '保存成功!'
          })
        }).then(resposne => {
          this.$router.push({path: '/edu/teacher'})
        })
      },

      // 根据id查询记录
      fetchDataById(id) {
        teacher.getById(id).then(response => {
          this.teacher = response.data.item
        })
      },

      // 根据id更新记录
      updateData() {
        teacher.updateById(this.teacher)
          .then(response => {
            return this.$message({
              type: 'success',
              message: '修改成功!'
            })
          }).then(resposne => {
          this.$router.push({path: '/edu/teacher'})
        })
      },

      // 上传成功的回调，data就是响应数据
      cropSuccess(data) {
        this.imagecropperShow = false
        this.imagecropperKey = this.imagecropperKey + 1

        // 从后端获取文件上传的url地址，并赋值给 this.teacher.avatar
        // data 来自后端服务器的返回值
        this.teacher.avatar = data.url
      },

      // 关闭文件上传组件
      close() {
        this.imagecropperShow = false
        // 关闭窗口时，改变组件id，使下一次打开窗口时的组件进行重新渲染
        this.imagecropperKey = this.imagecropperKey + 1
      }

    }
  }
</script>
