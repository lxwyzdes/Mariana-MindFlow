<template>
    <div>
        <div style="padding:10px">
            <el-card>
                <div v-for="item in videos" :key="item.id" style="margin:10px 0;padding:10px 0;color:#666;border-bottom:1px dashed #ccc">
                    <!-- @click="detail(item.id)" -->
                    <span style="font-size:18px" class="item">{{item.name}}</span>
                    <span style="float:right;font-size:12px;margin-top:5px">文件大小：{{item.size}}kb</span>
                </div>
            </el-card>
        </div>
        <router-view/>
    </div>
</template>

<script>
export default {
    data(){
        return{
            videos:[]
        }
    },
    methods:{
        detail(id){
            // this.$router.push({path:"/videoDetail",query:{id:id}})
        }
    },
    created(){
        this.request.get("/echarts/file/front/all").then(res=>{
            console.log(res);
            this.videos = res.data.filter(v => v.type === 'mp3')
        })
    }
}
</script>
<style scoped>
.item{
    cursor: pointer;
}
.item:hover{
    color: #3aBee6;
}
</style>