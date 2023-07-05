<template>
  <div>
    <div style="margin: 20px 0">
      <el-input
        type="text"
        placeholder="请输入名称"
        suffix-icon="el-icon-search"
        style="width: 200px"
        v-model="username"
      ></el-input>
      <el-input
        type="text"
        placeholder="请输入邮箱"
        suffix-icon="el-icon-message"
        style="width: 200px"
        class="ml-5"
        v-model="email"
      ></el-input>
      <el-input
        type="text"
        placeholder="请输入地址"
        suffix-icon="el-icon-position"
        style="width: 200px"
        class="ml-5"
        v-model="address"
      ></el-input>
      <el-button class="ml-5" type="primary" @click="serach">搜索</el-button>
      <el-button type="danger" @click="reset">重置</el-button>
    </div>

    <div style="margin: 20px 0">
      <el-button type="primary" @click="handleAdd"
        >新增&nbsp;<i class="el-icon-circle-plus-outline"></i
      ></el-button>
      <el-popconfirm
        class="mr-5 ml-5"
        confirm-button-text="确定"
        cancel-button-text="不用了"
        icon="el-icon-info"
        icon-color="red"
        title="你确定要删除吗?"
        @confirm="delBach"
      >
        <el-button type="danger" slot="reference"
          >批量删除&nbsp;<i class="el-icon-remove-outline"></i
        ></el-button>
      </el-popconfirm>

      <!-- accept支持什么格式 -->
      <el-upload
        action="http://localhost:9090/user/import"
        style="display:inline-block"
        :show-file-list="false"
        accept="xlsx"
        :on-success="handleimportSuccess"
      >
        <el-button type="primary"
          >导入&nbsp;<i class="el-icon-bottom"></i
        ></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5"
        >导出&nbsp;<i class="el-icon-top"></i
      ></el-button>
    </div>

    <el-table
      :data="tableData"
      border
      stripe
      :header-cell-style="{ background: '#ccc' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"> </el-table-column>
      <el-table-column prop="username" label="用户名" width="140">
      </el-table-column>
      <el-table-column prop="role" label="角色信息">
         <template slot-scope="scope">
            <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>
            <el-tag type="warning" v-if="scope.row.role === 'ROLE_TEACHER'">老师</el-tag>
            <el-tag type="success" v-if="scope.row.role === 'ROLE_STUDENT'">学生</el-tag>
         </template>
      </el-table-column>
      <el-table-column prop="nickname" label="名称" width="120">
      </el-table-column>
      <el-table-column prop="email" label="邮箱" width="120"> </el-table-column>
      <el-table-column prop="phone" label="电话" width="120"> </el-table-column>
      <el-table-column prop="address" label="地址"> </el-table-column>
      <el-table-column label="操作" width="500" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click="lokCoures(scope.row.courses)"
          v-if="scope.row.role === 'ROLE_TEACHER' "
            >查看教授课程&nbsp;<i class="el-icon-document"></i
          ></el-button>
          <el-button type="warning" @click="locasStuCourse(scope.row.stuCourses)"
          v-if="scope.row.role === 'ROLE_STUDENT' "
            >查看已选课程&nbsp;<i class="el-icon-document"></i
          ></el-button>
          <el-button type="success" @click="handleEdit(scope.row)"
            >编辑&nbsp;<i class="el-icon-edit"></i
          ></el-button>
          <el-popconfirm
            class="ml-5"
            confirm-button-text="确定"
            cancel-button-text="不用了"
            icon="el-icon-info"
            icon-color="red"
            title="你确定删除吗?"
            @confirm="handleDelete(scope.row)"
          >
            <el-button type="danger" slot="reference"
              >删除&nbsp;<i class="el-icon-remove-outline"></i
            ></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    
    <div style="padding: 10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>

    <!-- 弹窗 -->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="55px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.role" placeholder="请选择角色" style="width:100%">
            <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


    <el-dialog title="课程信息" :visible.sync="vis" width="30%">
      <el-table :data="courses" border stripe>
         <el-table-column prop="name" label="课程名称"></el-table-column>
         <el-table-column prop="score" label="学分"></el-table-column>
      </el-table>
    </el-dialog>


    <el-dialog title="课程信息" :visible.sync="stuis" width="30%">
      <el-table :data="stuCourses" border stripe>
         <el-table-column prop="name" label="课程名称"></el-table-column>
         <el-table-column prop="score" label="学分"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import request from "@/utils/request";
export default {
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      collapseBtnClass: "el-icon-s-fold",
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      // 输入框
      username: "",
      // 邮箱
      email: "",
      // 地址
      address: "",
      // 提示框状态
      dialogFormVisible: false,
      form: {},
      // checkbox
      multipleSeletion: [],
      roles:[],
      courses:[],
      stuCourses:[],
      vis:false,
      stuis:false
    };
  },
  methods: {
    locasStuCourse(stuCourses){
      this.stuCourses = stuCourses;
      this.stuis = true;
    },
    lokCoures(courses){
      console.log('good',courses);
       this.courses = courses;
       this.vis = true;
    },
    collapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = 64;
        this.collapseBtnClass = "el-icon-s-unfold";
        this.logoTextShow = false;
      } else {
        this.sideWidth = 200;
        this.collapseBtnClass = "el-icon-s-fold";
        this.logoTextShow = true;
      }
    },
    // 搜索
    serach() {
      this.load();
    },
    // 打开弹窗
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    // 添加
    save() {
      request.post("/user", this.form).then((res) => {
        console.log(res);
        if (res.data) {
          this.$message.success("保存成功");
        } else {
          this.$message.error("保存失败");
        }
        this.dialogFormVisible = false;
        this.load();
      });
    },
    // 修改 打开模态框
    handleEdit(row) {
      console.log(row);
      if (row) {
        this.load();
        this.form = row;
      }
      this.dialogFormVisible = true;
    },
    // 删除
    handleDelete(row) {
      console.log(row);
      request.delete("/user/" + row.id).then((res) => {
        console.log(res);
        this.load();
      });
    },
    reset() {
      this.username = "";
      this.email = "";
      this.address = "";
    },
    load() {
      request
        .get("/user/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            username: this.username,
            email: this.email,
            address: this.address,
          },
        })
        .then((res) => {
          console.log(res);
          this.tableData = res.data.records;
          this.total = res.data.total;
        });

        this.request.get("/role").then(res=>{
          console.log("笨蛋",res);
           this.roles = res;
        })
    },
    // checkbox
    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSeletion = val;
    },
    // 批量删除
    delBach() {
      let ids = this.multipleSeletion.map((v) => v.id);
      console.log(ids);
      request.post("/user/del/batch/", ids).then((res) => {
        console.log(res);
        if (res.data) {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },
    handleSizeChange(pageSize) {
      // console.log(pageSize)
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      // console.log(pageNum)
      this.pageNum = pageNum;
      this.load();
    },
    // 导出
    exp() {
      window.open("http://localhost:9090/user/export");
    },
    handleimportSuccess(){
      this.$message.success('文件导入成功');
      this.load();
    }
  },
  created() {
    this.load();
  },
};
</script>