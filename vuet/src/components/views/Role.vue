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
      :data="tableData"
      border
      stripe
      :header-cell-style="{ background: '#ccc' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection"></el-table-column>
      <el-table-column prop="id" label="ID"> </el-table-column>
      <el-table-column prop="name" label="名称"> </el-table-column>
      <el-table-column prop="flag" label="唯一标识"> </el-table-column>
      <el-table-column prop="description" label="描述"> </el-table-column>
      <el-table-column label="操作" width="280">
        <template slot-scope="scope">
          <el-button
            type="info"
            slot="reference"
            @click="selectMenu(scope.row)"
            >分配菜单&nbsp;<i class="el-icon-menu"></i
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
        :page-sizes="[2, 5, 10, 15]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>

    <!-- 弹窗 -->
    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="55px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识">
          <el-input v-model="form.flag" autocomplete="off"></el-input>
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

    <el-dialog title="菜单分配" :visible.sync="menuVisible" width="30%">
      <el-form label-width="55px" size="small">
        <el-tree
          :data="menuData"
          show-checkbox
          @check-change="handleCheckChange"
          :props="props"
          node-key="id"
          :default-expanded-keys="expends"
          :default-checked-keys="checks"
          ref="tree"
        >
          <span class="custom-tree-node" slot-scope="{ data }">
            <span><i :class="data.icon"></i>{{ data.name }}</span>
          </span>
        </el-tree>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
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
      menuVisible: false,
      // 输入框
      name: "",
      // 提示框状态
      dialogFormVisible: false,
      form: {},
      // checkbox
      multipleSeletion: [],
      // 树形菜单的值
      menuData: [],
      props: {
        label: "name",
      },
      expends: [],
      checks: [],
      roleId: 0,
      roleFlag:''
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
    reset() {
      this.name = "";
      this.load();
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
      request.post("/role", this.form).then((res) => {
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
    saveRoleMenu() {
      this.request
        .post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys())
        .then((res) => {
          // console.log(res);
          if(res.code === '200'){
             this.$message.success("绑定成功");
             this.menuVisible = false;
            //  vuex
            // 操作管理员角色后需要重新登录
             if(this.roleFlag === 'ROLE_ADMIN'){
                this.$store.commit("logout")
             }
          }else{
            this.$message.error(res.meg);
          }

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
      request.delete("/role/" + row.id).then((res) => {
        console.log(res);
        this.load();
      });
    },
    load() {
      request
        .get("/role/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name,
          },
        })
        .then((res) => {
          console.log("123", res);
          this.tableData = res.records;
          this.total = res.total;
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
      request.post("/role/del/batch/", ids).then((res) => {
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
    selectMenu(role) {
      console.log("role",role)
      this.roleId = role.id;
      this.menuVisible = true;
      this.roleFlag = role.flag;
      // 路由菜单
      request
        .get("/menu", {
          params: {
            name: "",
          },
        })
        .then((res) => {
          this.menuData = res.data;

          // 把后台返回的id处理成id数组
          this.expends = this.menuData.map((v) => v.id);
        });

      this.request.get("/role/roleMenu/" + this.roleId).then((res) => {
        console.log(res);
         this.checks = res.data;
      });

      this.request.get("/menu/ids").then(r=>{
        const ids = r.data;
        ids.forEach(id=>{
          if(!this.checks.includes(id)){
            this.$nextTick(()=>{
              this.$refs.tree.setChecked(id,false);
            })
          }
        })
        this.menuVisible = true;
      })

    },
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate);
    },
  },
  created() {
    this.load();
  },
};
</script>