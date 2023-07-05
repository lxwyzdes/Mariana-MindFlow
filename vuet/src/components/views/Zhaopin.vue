<template>
  <div>
    <div style="margin: 20px 0">
      <el-input
        type="text"
        placeholder="请输入名称"
        suffix-icon="el-icon-search"
        style="width: 200px"
        v-model="title"
      >
      </el-input>
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
    </div>
    <el-table
      :data="zhaoPinList"
      border
      stripe
      :header-cell-style="{ background: '#ccc' }"
      @selection-change="handleSelectionChange"
      style="width: 100%"
    >
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="id" label="编号" width="50" align="center"></el-table-column>
      <el-table-column label="公司图片" width="120">
        <template slot-scope="scope">
          <img :src="scope.row.src" alt="" height="80" width="120" />
        </template>
      </el-table-column>
      <el-table-column label="公司名">
        <template slot-scope="scope">
          <div class="ziti">
            {{ scope.row.title }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="author" label="大佬"></el-table-column>
      <el-table-column label="公司介绍">
        <template slot-scope="scope">
          <div class="good">
            {{ scope.row.introduce }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="xingzhi" label="公司类型"> </el-table-column>
      <el-table-column prop="createtime" label="面试时间"> </el-table-column>
      <el-table-column prop="monney" label="薪资"> </el-table-column>
      <el-table-column prop="gangwei" label="招聘岗位"> </el-table-column>
      <el-table-column prop="phone" label="电话"> </el-table-column>
      <el-table-column prop="ccc" label="学历要求"> </el-table-column>
      <el-table-column prop="xxx" label="技术要求"> </el-table-column>
      <el-table-column prop="address" label="面试地点"> </el-table-column>
      <el-table-column lang="操作" width="180" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)"
            >编辑&nbsp;<i class="el-icon-edit"></i>
          </el-button>
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
              >删除&nbsp;<i class="el-icon-remove-outline"></i>
            </el-button>
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
    <!-- 添加和修改 -->
    <el-dialog title="公司信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="公司图片">
          <el-upload
            class="avatar-uploader"
            :action="'http://localhost:9090/file/upload'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="form.src" :src="form.src" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="公司">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="创始人">
          <el-input v-model="form.author" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="介绍">
          <el-input v-model="form.introduce" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input v-model="form.xingzhi" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="面试时间">
           <el-date-picker
            v-model="form.createtime"
            style="width:100%"
            type="date"
            placeholder="选择日期"
            value-format="yyyy:MM:dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="薪资">
          <el-input v-model="form.monney" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="招聘岗位">
          <el-input v-model="form.gangwei" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学历要求">
          <el-input v-model="form.ccc" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="技术要求">
          <el-input v-model="form.xxx" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="面试地点">
          <el-input v-model="form.address" autocomplete="off"></el-input>
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
      title: "",
      pageNum: 1,
      pageSize: 2,
      zhaoPinList: [],
      mullcation: [],
      total:0,
      dialogFormVisible:false,
      form:{}
    };
  },
  methods: {
    save(){
      this.request.post("/zhaopin/",this.form).then(res=>{
        if(res){
          this.$message.success("执行成功");
        }
        this.xxx();
        this.dialogFormVisible = false;
      })
    },
    xxx() {
      this.request
        .get("/zhaopin/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            title: this.title,
          },
        })
        .then((res) => {
          this.zhaoPinList = res.data.records;
          this.total = res.data.total;
        });
    },
    handleAvatarSuccess(res) {
            this.form.src = res;
        },
    // 点击编辑按钮
    handleEdit(row) {
      if(row){
        this.form = row;
        this.xxx();
      }
      this.dialogFormVisible = true;
    },
    // 点击删除按钮
    handleDelete(row) {
      this.request.delete("/zhaopin/"+row.id).then(res=>{
        if(res){
          this.$message.success("删除成功");
        }
        this.xxx();
      })
    },
    handleSelectionChange(val) {
      this.mullcation = val;
    },
    handleSizeChange(pageSize) {
      // console.log(pageSize)
      this.pageSize = pageSize;
      this.xxx();
    },
    handleCurrentChange(pageNum) {
      // console.log(pageNum)
      this.pageNum = pageNum;
      this.xxx();
    },
    reset() {
      this.xxx();
      this.title = "";
    },
    serach() {
      this.xxx();
    },
    // 点击添加按钮
    handleAdd() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    // 批量删除
    delBach() {
      let ids = this.mullcation.map(v => v.id);
      this.request.post("/zhaopin/del/batch",ids).then(res=>{
        if(res){
          this.$message.success("批量删除成功");
        }
        this.xxx();
      })
    },
  },
  created() {
    this.xxx();
  },
};
</script>

<style>
.ziti {
  font-weight: 700;
  font-size: 18px;
}
.good {
  height: 70px;
}
.avatar-uploader .el-upload {
  border: 1px dashed gray;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: white;
  width: 108px;
  height: 108px;
  line-height: 108px;
  text-align: center;
  background-color: gray;
}

.avatar {
  width: 108px;
  height: 108px;
  display: block;
}
</style>