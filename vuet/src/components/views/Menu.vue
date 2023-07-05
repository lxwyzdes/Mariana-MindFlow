<template>
  <div>
    <div style="margin: 20px 0">
      <el-input
        type="text"
        placeholder="请输入名称"
        suffix-icon="el-icon-search"
        style="width: 200px"
        v-model="name"
      ></el-input>
      <el-button class="ml-5" type="primary" @click="serach">搜索</el-button>
      <el-button type="danger" @click="reset">重置</el-button>
    </div>

    <div style="margin: 20px 0">
      <el-button type="primary" @click="handleAddGood"
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
    </div>

    <el-table
      :data="tableData"
      border
      stripe
      :header-cell-style="{ background: '#ccc' }"
      @selection-change="handleSelectionChange"
      row-key="id"
      default-expand-all
    >
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="id" label="ID"> </el-table-column>
      <el-table-column prop="name" label="名称">
      </el-table-column>
      <el-table-column prop="path" label="路径">
      </el-table-column>
      <el-table-column prop="pagePath" label="页面路径">
      </el-table-column>
      <el-table-column label="图标" align="center" class-name="good">
        <template slot-scope="scope">
           <i :class="scope.row.icon"></i>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述">
      </el-table-column>
      <el-table-column label="操作" width="300px">
        <template slot-scope="scope">
            <el-button type="primary" @click="handleAdd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增子菜单<i class="el-icon-plus"></i></el-button>
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
    

    <!-- 弹窗 -->
    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="95px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
             <el-select clearable v-model="form.icon" placeholder="请选择" style="width:100%">
               <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
                 <i :class="item.value"></i>{{item.name}}
               </el-option>
             </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
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
      name: "",
      // 提示框状态
      dialogFormVisible: false,
      form: {},
      // checkbox
      multipleSeletion: [],
      // 从后台传过来的
      options:[]
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
    reset(){
      this.name = "";
      this.load();
    },
    // 搜索
    serach() {
      this.$message.success("搜索成功");
      this.load();
    },
    // 添加子菜单
    handleAdd(pid) {
      this.dialogFormVisible = true;
      this.form = {};
      if(pid){
          this.form.pid = pid;
      }
    },
    handleAddGood(){
        this.dialogFormVisible = true;
        this.form = {};
        this.load();
        // 请求图标的数据
      this.request.get("/menu/icons").then(res => {
         this.options = res.data;
      })
    },
    // 添加
    save() {
      request.post("/menu", this.form).then((res) => {
        console.log(res);
        if (res) {
          this.$message.success("保存成功");
          this.load();
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

      // 请求图标的数据
      this.request.get("/menu/icons").then(res => {
         this.options = res.data;
      })

    },
    // 删除
    handleDelete(row) {
      console.log(row);
      request.delete("/menu/" + row.id).then((res) => {
        console.log(res);
        this.load();
      });
    },
    load() {
      request
        .get("/menu", {
          params: {
            name:this.name
          },
        })
        .then((res) => {
          console.log('123',res);
          this.tableData = res.data;
        });
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
      request.post("/menu/del/batch/", ids).then((res) => {
        console.log(res);
        if (res) {
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
    handleAddSon(){

    }
  },
  created() {
    this.load();
  },
};
</script>

<style scoped>
.good i{
  font-size: 18px;
}
</style>