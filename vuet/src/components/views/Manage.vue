<template>
  <el-container style="min-height: 100%">
    <el-aside
      :width="sideWidth + 'px'"
      style="
        height: 100%;
        background-color: rgb(238, 241, 246);
        box-shadow: 2px 0 6px rgb(0 21 41 /35%);
      "
    >
       <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"></Aside>

    </el-aside>

    <el-container>
      <el-header
        style="
          border-bottom: 1px solid #ccc;
        "
      >
       <MyHeader :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse"></MyHeader>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from '../views/Aside.vue'
import MyHeader from '../views/MyHeader.vue'
import User from '../views/User.vue'
import request from "@/utils/request";
export default {
  name: "HelloWorld",
  components:{
    Aside,
    MyHeader,
    User
  },
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
      multipleSeletion:[]
    };
  },
  methods: {
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
        if (res) {
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
      if(row){
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
          // 这里
          console.log("显示所有",res);
          console.log("数据",res.data.records);
          this.tableData = res.data.records;
          this.total = res.data.total;
        });
    },
    // checkbox
    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSeletion = val;
    },
    // 批量删除
    delBach(){
      let ids = this.multipleSeletion.map(v => v.id);
      console.log(ids);
      request.post("/user/del/batch/",ids).then((res)=>{
        console.log(res);
        if(res.data){
          this.$message.success("批量删除成功");
          this.load();
        }else{
          this.$message.error("批量删除失败");
        }
      })
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
  },
  created() {
    this.load();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
