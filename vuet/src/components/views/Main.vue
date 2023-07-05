<template>
  <div style="margin: 20px 0">
    <div style="margin: 20px 0">
      <el-input
        type="text"
        placeholder="请输入员工姓名"
        suffix-icon="el-icon-search"
        style="width: 200px"
        v-model="yname"
      ></el-input>
      <el-button class="ml-5" type="primary" @click="serach">搜索</el-button>
      <el-button type="danger" @click="reset">重置</el-button>
    </div>
    <el-button type="primary" @click="handleAdd"
      >新增&nbsp;<i class="el-icon-circle-plus-outline"
    /></el-button>
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

    <el-table
      :data="tableGood"
      border
      stripe
      style="margin-top:10px"
      :header-cell-style="{ background: '#ccc' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"> </el-table-column>
      <el-table-column prop="yname" label="学生姓名"> </el-table-column>
      <el-table-column prop="age" label="年龄"> </el-table-column>
      <el-table-column label="性别">
        <template slot-scope="scope">
          {{ scope.row.gender === "1" ? "男" : "女" }}
        </template>
      </el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="xname" label="学历"></el-table-column>
      <el-table-column prop="sname" label="就业公司"></el-table-column>
      <el-table-column label="录取">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.state"
            active-color="#13ce66"
            inactive-color="#ccc"
            @change="changeEnable(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
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
        :page-sizes="[2, 5, 10, 15]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>

    <!-- 弹窗 -->
    <el-dialog title="员工录入" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="90px" size="small">
        <el-form-item label="姓名">
          <el-input v-model="form.yname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender" style="width:100%">
              <el-option label="男" value="1"></el-option>
              <el-option label="女" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址">
            <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="学历">
            <el-select v-model="form.xid" style="width:100%">
                <el-option v-for="item in xForm" :label="item.li" :value="item.xid" :key="item.xid"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="公司">
            <el-select v-model="form.gid" style="width:100%">
                <!-- <el-option label="字节跳动" value="1"></el-option>
                <el-option label="阿里巴巴" value="2"></el-option>
                <el-option label="腾讯" value="3"></el-option>
                <el-option label="华为" value="4"></el-option> -->
                <el-option v-for="item in gFrom" :key="item.gid" :label="item.gname" :value="item.gid"></el-option>
            </el-select>
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
export default {
  data() {
    return {
      yname: "",
      pageNum: 1,
      pageSize: 2,
      tableGood: [],
      total: 0,
      dialogFormVisible: false,
      form: {},
      xForm:[],
      gFrom:[],
      multipleSeletion:[]
    };
  },
  methods: {
    load() {
      this.request
        .get("/yuan/page", {
          params: {
            yname: this.yname,
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          console.log("小哈哈哈", res);
          this.tableGood = res.data.records;
          this.total = res.data.total;
        });
    },
    reset() {
      this.yname = "";
      this.load();
    },
    serach() {
      console.log(this.yname);
      this.load();
    },
    // 添加
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
      this.request.get("/yuan/XueFindAll").then(res=>{
            console.log("小坏蛋",res);
            this.xForm = res.data;
            console.log(this.xForm);
        });
        this.request.get("/yuan/GoodFindAll").then(res=>{
            this.gFrom = res.data;
        })
    },
    changeEnable(row){
        this.request.post("/yuan/",row).then(res=>{
            if (res.code === "200") {
          this.$message.success("更新成功");
        } else {
          this.$message.error("更新失败");
        }
        })
    },
    // 批量删除
    delBach() {
        let ids = this.multipleSeletion.map(v=>v.id);
        this.request.post("/yuan/del/batch/",ids).then(res=>{
            console.log(res);
            if(res.code === '200'){
                this.$message.success("批量删除成功");
                this.load();
            }else{
                this.$message.error("批量删除失败");
            }
        })
    },
    handleSelectionChange(val) {
        this.multipleSeletion = val;
    },
    // 修改
    handleEdit(row) {
        if(row){
            this.load();
            this.form = row;
        }
        this.request.get("/yuan/XueFindAll").then(res=>{
            console.log("小坏蛋",res);
            this.xForm = res.data;
            console.log(this.xForm);
        });
        this.request.get("/yuan/GoodFindAll").then(res=>{
            this.gFrom = res.data;
        })
        this.dialogFormVisible = true;
    },
    // 删除
    handleDelete(row) {
        this.request.delete("/yuan/" + row.id).then((res) => {
          this.$message.error("删除成功");
        console.log(res);
        this.load();
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
    // 提交
    save() {
        this.request.post("/yuan",this.form).then(res=>{
            console.log(res);
            if(res){
                this.$message.success("执行成功");
                this.load();
            }else{
                this.$message.error("保存失败");
            }
            this.dialogFormVisible = false;
            this.load();
        })
    },
  },
  created() {
    this.load();
  },
};
</script>