<template>

  <div class="app-container">

    <el-input
      v-model="filterText"
      placeholder="输入关键字进行过滤"/>

    <el-row>
      <el-button type="primary" size="mini" @click="() => appendRootEvent()">添加根分类</el-button>
    </el-row>

    <el-tree ref="tree"
             :data="subjectList"
             :filter-node-method="filterNode"
             class="filter-tree"
             default-expand-all
             :expand-on-click-node="false">
      <span class="tree-expand" slot-scope="{ node,data }">
        <span class="tree-label">{{ node.label }}</span>
         <span class="tree-btn">
            <el-button type="text" @click="() => appendEvent(node,data)">
              <i class="el-icon-plus"></i>
            </el-button>
           <el-button type="text" @click="() => appendUpdateEvent(node,data)">
              <i class="el-icon-edit"></i>
            </el-button>
           <el-button type="text" @click="() => removeEvent(node,data)">
              <i class="el-icon-delete"></i>
            </el-button>
        </span>
      </span>
    </el-tree>

    <!--新增事件节点分类弹窗-->
    <el-dialog
      title="新增课程分类"
      width="25%"
      class="add-event-dialog"
      :visible.sync="addEventdialogVisible"
      size="tiny">
      <el-form ref="addEventForm" :model="addEventForm">
        <el-form-item label="分类名称" prop="subjectName">
          <el-input v-model="addEventForm.subjectName"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="addEventFormCancleBtn('addEventForm')">取 消</el-button>
      <el-button type="primary" @click="addEventFormSubmitBtn('addEventForm')">确 定</el-button>
    </span>
    </el-dialog>

    <!--修改事件节点分类弹窗-->
    <el-dialog
      title="修改课程分类"
      width="25%"
      class="add-event-dialog"
      :visible.sync="updateEventdialogVisible"
      size="tiny">
      <el-form ref="updateEventForm" :model="updateEventForm">
        <el-form-item label="分类名称" prop="subjectName">
          <el-input v-model="updateEventForm.currentSubjectName"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="updateEventFormCancleBtn('updateEventForm')">取 消</el-button>
      <el-button type="primary" @click="updateEventFormSubmitBtn('updateEventForm')">确 定</el-button>
    </span>
    </el-dialog>

  </div>

</template>

<script>

  import subject from '@/api/edu/subject'

  export default {

    data() {
      return {
        filterText: '',
        subjectList: [], // 课程列表
        addEventdialogVisible: false,
        updateEventdialogVisible: false,
        addEventForm: {
          subjectName: '',
          parentNodeId: '',
          parentLevel: ''
        },
        updateEventForm: {
          currentSubjectName: '',
          nodeId: '',
        }
      }
    },

    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },

    created() {
      this.fetchNodeList()
    },

    methods: {

      /*节点新增，新增树形菜单事件分类弹窗，提交按钮*/
      addEventFormSubmitBtn() {
        /*获取当前input上输入的文字*/

        subject.save(this.addEventForm).then(response => {
          return this.$message({
            type: 'success',
            message: '保存成功!'
          })
        }).then((res) => {
          console.log('请求成功');
          this.addEventdialogVisible = false;
          this.addEventForm.subjectName = '';
          this.fetchNodeList();
        })
      },

      /*节点新增，新增树形菜单事件分类弹窗，提交按钮*/
      updateEventFormSubmitBtn() {
        /*获取当前input上输入的文字*/
        subject.update(this.updateEventForm).then((res) => {
          return this.$message({
            type: 'success',
            message: '修改成功!'
          })
        }).then((res) => {
          console.log('请求成功');
          this.updateEventdialogVisible = false;
          this.updateEventForm.subjectName = '';
          this.fetchNodeList();
        })
      },

      /*添加根节点*/
      appendRootEvent() {
        this.addEventdialogVisible = true;
        this.addEventForm.parentNodeId = 0;
        this.addEventForm.parentLevel = -1;
      },

      /*树形控件添加节点，添加弹窗出现*/
      appendEvent(node, data) {
        this.addEventdialogVisible = true;
        this.addEventForm.parentNodeId = data.id;
        this.addEventForm.parentLevel = data.level;
      },

      /*树形控件修改节点，添加弹窗出现*/
      appendUpdateEvent(node, data) {
        console.log(data)
        this.updateEventdialogVisible = true;
        this.updateEventForm.currentSubjectName = data.label;
        this.updateEventForm.nodeId = data.id;
        this.addEventForm.parentLevel = data.level;
      },

      /*关闭窗口*/
      addEventFormCancleBtn() {
        this.addEventdialogVisible = false;
        this.addEventForm.subjectName = '';
      },

      /*关闭窗口*/
      updateEventFormCancleBtn() {
        this.updateEventdialogVisible = false;
        this.updateEventForm.currentSubjectName = '';
      },

      /*树形控件删除节点*/
      removeEvent(node, data) {
        console.log(data);
        if (data.children === null) {
          this.$confirm('确定要删除分类吗？', '温馨提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            subject.delete(data.id).then((res) => {
              return this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }).then(() => {
              this.fetchNodeList();
            });
          })
        } else {
          this.$message('请先删除子节点');
        }
      },

      fetchNodeList() {
        subject.getNestedTreeList().then(response => {
          console.log(response)
          if (response.success === true) {
            this.subjectList = response.data.items
          }
        })
      },

      // 节点过滤的方法
      filterNode(value, data) {
        //   console.log('filterNode:第一个参数' + value)
        //   console.log('filterNode:第二个参数')
        console.log(data)
        //   console.log('执行filterNode.....')
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      }
    }
  }
</script>
