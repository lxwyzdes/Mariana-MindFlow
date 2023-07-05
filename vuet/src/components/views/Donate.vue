<template>
  <div>
    <div style="margin: 20px 0">
      <el-input type="text" placeholder="请输入名称" suffix-icon="el-icon-search" style="width: 200px" v-model="title">
      </el-input>
      <el-button class="ml-5" type="primary" @click="serach">搜索</el-button>
      <el-button type="danger" @click="reset">重置</el-button>
    </div>
    <div style="margin: 20px 0">
      <el-button type="primary" @click="handleAdd">新增&nbsp;<i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm class="mr-5 ml-5" confirm-button-text="确定" cancel-button-text="不用了" icon="el-icon-info"
        icon-color="red" title="你确定要删除吗?" @confirm="delBach">
        <el-button type="danger" slot="reference">批量删除&nbsp;<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>
    <el-table :data="anlianData" border stripe :header-cell-style="{ background: '#ccc' }"
      @selection-change="handleSelectionChange" style="width: 100%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="编号" width="70" align="center"> </el-table-column>
      <el-table-column label="图书" width="180">
        <template slot-scope="scope">
          <img :src="scope.row.src" alt="" height="80" width="120" />
        </template>
      </el-table-column>
      <el-table-column label="书名">
        <template slot-scope="scope">
          <div class="ziti">
            {{ scope.row.title }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="author" label="作者"> </el-table-column>
      <el-table-column prop="releaseTime" label="发布时间"> </el-table-column>
      <el-table-column label="介绍">
        <template slot-scope="scope">
          <div class="good">
            {{ scope.row.introduce }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="bookType" label="类型"> </el-table-column>
      <el-table-column lang="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑&nbsp;<i class="el-icon-edit"></i>
          </el-button>
          <el-popconfirm class="ml-5" confirm-button-text="确定" cancel-button-text="不用了" icon="el-icon-info"
            icon-color="red" title="你确定删除吗?" @confirm="handleDelete(scope.row)">
            <el-button type="danger" slot="reference">删除&nbsp;<i class="el-icon-remove-outline"></i>
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
        :page-sizes="[2, 10, 15, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <!-- 添加 -->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="图片">
          <el-upload class="avatar-uploader" :action="'http://localhost:9090/file/upload'" :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img v-if="form.src" :src="form.src" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="书名">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="form.author" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="发布时间">
          <el-input v-model="form.releaseTime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="介绍">
          <el-input v-model="form.introduce" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input v-model="form.bookType" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="js">
import request from "@/utils/request";

export default {
  data() {
    return {
      anlianData: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      title: '',
      multipleSeletion: [],
      roles: [],
      courses: [],
      stuCourses: [],
      vis: false,
      stuis: false,
      dialogFormVisible: false,
      form: {},
      src: ''
    }
  },
  methods: {
    load() {
      request.get("/anlian/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title: this.title,
        }
      }).then(res => {
        // console.log('你好啊', res);
        this.anlianData = res.data.records;
        this.total = res.data.total;
      })
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
      request.delete("/anlian/" + row.id).then((res) => {
        console.log(res);
        this.load();
      });
    },
    handleAvatarSuccess(res) {
      console.log(this.anlianData.avatarUrl);
      this.form.src = res
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
    // checkbox
    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSeletion = val;
    },
    // 批量删除
    delBach() {
      let ids = this.multipleSeletion.map((v) => v.id);
      console.log(ids);
      request.post("/anlian/del/batch/", ids).then((res) => {
        console.log(res);
        if (res.code) {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },
    // 添加
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    // 重置
    reset() {
      this.title = "";
    },
    serach() {
      this.load();
    },
    save() {
      request.post("/anlian", this.form).then(res => {
        if (res.code) {
          this.$message.success("保存成功");
        } else {
          this.$message.error("保存失败");
        }
        this.dialogFormVisible = false;
        this.load();
      })
    }
  },
  created() {
    this.load();
  },
}
</script>

<style scoped>
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
