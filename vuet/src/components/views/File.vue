<template>
  <div>
      <div style="margin: 20px 0">
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
          <!-- accept支持什么格式 -->
      <el-upload
        action="http://localhost:9090/file/upload"
        style="display:inline-block"
        :show-file-list="false"
        accept="xlsx"
        :on-success="handleUploadSuccess"
      >
        <el-button type="primary"
          >上传文件&nbsp;<i class="el-icon-bottom"></i
        ></el-button>
      </el-upload>

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
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"> </el-table-column>
      <el-table-column prop="name" label="文件名称">
      </el-table-column>
      <el-table-column prop="type" label="文件类型"> </el-table-column>
      <el-table-column prop="size" label="文件大小(kb)"> </el-table-column>
       <el-table-column label="预览">
        <template slot-scope="scope">
          <el-button type="primary" @click="preview(scope.row.url)">预览</el-button>
        </template>
      </el-table-column>
      <el-table-column label="下载">
          <template slot-scope="scope">
              <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
          </template>
      </el-table-column>
      <el-table-column label="启用">
          <template slot-scope="scope">
              <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
          </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      name:'',
      multipleSeletion:[],
       pageNum: 1,
      pageSize: 2,
      total:0
    };
  },
  created(){
      this.load();
  },
  methods: {
    changeEnable(row){
        this.request.post("/file/update",row).then(res=>{
            console.log(res);
            if(res.code === '200'){
                this.$message.success("更新成功");
            }else{
                this.$message.error("更新失败");
            }
        })
    },
    // 预览
     preview(url) {
      window.open('https://file.keking.cn/onlinePreview?url=' + encodeURIComponent(window.btoa((url))))
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
    // 删除
    handleDelete(row) {
      console.log(row);
      this.request.delete("/file/" + row.id).then((res) => {
        console.log(res);
        this.load();
      });
    },
    reset() {
      this.name = "";
      this.load();
    },
    load() {
      this.request
        .get("/file/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
          },
        })
        .then((res) => {
          console.log("暗恋",res);
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
    delBach() {
      let ids = this.multipleSeletion.map((v) => v.id);
      console.log(ids);
      this.request.post("/file/del/batch/", ids).then((res) => {
        console.log(res);
        if (res.code === '200') {
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
    handleUploadSuccess(res){
       this.$message.success("上传成功");
        console.log(res);
        this.load();
    },
    download(url){
        window.open(url);
    }
  },
};
</script>

<style scoped>
</style>