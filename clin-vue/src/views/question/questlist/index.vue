<template>
  <div class="app-container">
   <div v-show="showList">
     <div class="filter-container">
       问卷名称：<el-input v-model="listQuery.name" class="filter-input" maxlength="50"  placeholder="请输入问卷名称" clearable  @keyup.enter.native="getList" />
       问卷编号：<el-input v-model="listQuery.code" class="filter-input" maxlength="50"  placeholder="请输入问卷编号" clearable  @keyup.enter.native="getList" />
       <el-button size="small" type="primary" @click="getList">查 询</el-button>
       <el-button size="small" @click="resetQuery">重 置</el-button>
       <el-button size="small" type="primary" @click="toAdd">+新增问卷</el-button>
     </div>
     <div class="list-row1"></div>
     <div>
       <el-row :gutter="20">
         <div v-for="(item,i) in list">
           <el-col :xl="6" :md="8" :sm="12" :xs="12"><div class="list-row2" >
             <el-row >
               <el-col :span="6"> <img src="../../../image/icon1.png" class="list-row2-img"/></el-col>
               <el-col :span="18">
                 <div v-if="item.is_enable===0" class="list-row2-col1">停</div>
                 <div class="list-row2-col2">{{item.question_name}}</div>
                 <div class="list-row2-col3">问卷序号：{{item.id}}</div>
                 <div class="list-row2-col4">问卷创建时间：{{item.create_time}}</div>
               </el-col>
             </el-row>
             <el-row v-if="item.is_enable===1">
               <el-col :span="12"><div class="list-row3-col1"  @click="toCopy(item.id)">复制问卷</div></el-col>
               <el-col :span="12"><div class="list-row3-col3" @click="toDetail(item.id)">详 情</div></el-col>
             </el-row>
             <el-row v-else>
               <el-col :span="8"><div class="list-row3-col1" @click="toCopy(item.id)">复制问卷</div></el-col>
               <el-col :span="8"><div class="list-row3-col2" @click="enableQuestion(item.id)">启用此问卷</div></el-col>
               <el-col :span="8"><div class="list-row3-col3" @click="toDetail(item.id)">详 情</div></el-col>
             </el-row>
           </div></el-col>
         </div>
       </el-row>
     </div>
   </div>
    <edit v-if="showEdit" :visible.sync="showEdit"></edit><!--编辑-->
    <detail v-if="showDetail" :visible.sync="showDetail"></detail><!--详情-->
    <copy v-if="showCopy" :visible.sync="showCopy"></copy><!--复制-->
  </div>
</template>
<script>
  import {getQuestionList ,setQuestionEnable} from  '@/api/questlist.js'
  import edit from './edit'
  import detail from './detail'
  import copy from './copy'
  export default {
    name: 'MyNotification',
    components: {
      detail,
      edit,
      copy
    },
    data() {
      return {
        showDetailId:undefined,
        tableKey: 0,
        list: [],
        total: 0,
        listLoading: true,
        listQuery: {
         name:'',code:'',
        },
        multipleSelection: [],
        typeOptions: [],
        priorityOptions: [],
        temp: {},
        dialogFormVisible: false,
        advancedSearch: false,
        showList:true,
        showDetail:false,
        showEdit:false,
        showCopy:false,
      }
    },
    created() {
      this.getList()
    },
    methods: {
      enableQuestion(id){
        this.$confirm('确认启用此问卷？', '确认信息', {
          distinguishCancelAndClose: true,
          confirmButtonText: '确定',
          cancelButtonText: '放弃'
        }).then(() => {
          setQuestionEnable(id).then(response => {
            if (response.message === 'success') {
              this.getList()
            } else {
              this.handleWarning(response)
            }
          })
        })
          .catch(action => {
          });
      },
      handleWarning(response) {
          this.$message({
            message: response.message || response,
            type: 'warning',
            duration: 2000
          })
          this.disabledStatus = false
        },
      toAdd(){
        this.showList = false;
        this.showEdit = true;
      },
      toDetail(id){
        this.showDetailId=id;
        this.showList = false;
        this.showDetail = true;
      },
      toCopy(id){
        this.showDetailId=id;
        this.showList = false;
        this.showCopy = true;
      },
      back(){
        this.showEdit = false;
        this.showDetail = false;
        this.showCopy = false;
        this.showList=true;
        this.resetQuery();
      },
      resetQuery(){
        this.listQuery.name= '';
        this.listQuery.code='';
        this.getList();
      },
      getList() {
        getQuestionList(this.listQuery).then(response => {
          this.list = response.data;
        })
      },
    }
  }
</script>
<style>
  .list-row1{border-top: 1px solid #E9E9E9; margin: 20px 0 30px 0;}
  .list-row2{width: 100%; min-height: 170px; border: 1px solid #E9E9E9;}
  .list-row2-img{margin-left: 20px; margin-top: 20px; height: 50px; width: 50px;}
  .list-row2-col1{float: right; margin-top: 15px; margin-right: 15px; height: 45px; width: 45px; background: #F2F2F2; line-height: 45px; text-align: center; border-radius: 50%;}
  .list-row2-col2{font-size: 16px; font-weight: bold; line-height: 40px; margin-top: 20px;}
  .list-row2-col3{font-size: 12px; color: #8D8D8D; line-height: 25px;}
  .list-row2-col4{font-size: 12px; color: #8D8D8D; line-height: 25px; margin-bottom: 15px;}
  .list-row3-col1{background: #66CC99; color: white; height: 45px; line-height: 45px; font-size: 14px; text-align: center;}
  .list-row3-col2{background: #FCB953; color: white; height: 45px; line-height: 45px; font-size: 14px; text-align: center;}
  .list-row3-col3{background: #0099FF; color: white; height: 45px; line-height: 45px; font-size: 14px; text-align: center;}
</style>
