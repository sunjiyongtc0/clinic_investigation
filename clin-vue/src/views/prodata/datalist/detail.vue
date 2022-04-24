<!--义诊详情-->
<template>
  <div class="warp-main" v-show="visible">
    <div style="float: right;"><el-button  size="small" @click="handleClose()">返 回</el-button></div>
    <div class="displayFlex">
      <div class=""><img src="../../../image/infoIcon.png" style="margin-top: 4px;" height="22" width="30"/></div>
      <div class="edit-title-label">患者个人信息</div>
    </div>
    <div class="marginLeftAndTop">
        <el-row>
          <div style="line-height: 40px; " v-for="(item,i) in infoArr" :key="i">
            <el-col :span="8"><div class="">{{item.label}}:{{item.value}}</div></el-col>
          </div>
        </el-row>
    </div>
    <div>
      <div class="displayFlex">
        <div class="">
          <img src="../../../image/questionIcon.png" style="margin-top: 4px;" height="26" width="26"/></div>
        <div class="edit-title-label">问卷详情</div>
      </div>

      <div class="contentBox">
        <div class="titleHead">
          <el-row>
            <el-col :span="12"><div class="">问题</div></el-col>
            <el-col :span="12"><div class="">答案</div></el-col>
          </el-row>
        </div>
        <div class="rowBox" v-for="(item,i) in questionArr" :key="i">
          <el-row>
            <el-col :span="12"><div class="">{{item.questionTitle}}</div></el-col>
            <el-col :span="12"><div class="">{{item.answer}}</div></el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import { findDetailById } from '@/api/investigation'

  export default {
    props: {
      visible: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        infoArr:[],
        questionArr:[]
      }
    },
    watch:{},
    methods: {
      getEditData(){
        findDetailById(this.$parent.showDetailId).then(response => {
          if (response.message === 'success') {
            let pdcInfo=response.data.pdcInfo;
            this.pdcId=pdcInfo.id;
            this.pdId=response.data.pdId;
            this.userId=response.data.userId;
            let groupInfo=response.data.groupInfo;
            this.groupId=groupInfo.id;
            let pdcItems=groupInfo.pdc_items;
            let pdcDetail=response.data.pdcItems;
            this.pdcItemsDic=pdcDetail;
            let infoArr=[];
            pdcItems.split(",").forEach((item,index,array)=> {
              let it={}
              it.value=pdcInfo[item];
              pdcDetail.forEach((row)=>{
                if(row.value=== item){
                  it.label=row.label;
                }
              })
              infoArr.push(it);
            })
            this.infoArr =infoArr;//患者信息回显
            let questionList=response.data.questionList;
            let config=response.data.answer;
            let questionArr=[];

            questionList.forEach((row)=>{
              let answer='';
              if(config !=null){
                config.forEach((item)=>{
                  if(item.id==row.Id){
                    row.chooseArr.forEach((choose)=>{
                      item.value.split(",").forEach((i)=>{
                        if(choose.itemId==i){
                          answer+=choose.itemName+";";
                        }
                      })
                    })
                  }
                })
              }
              row.answer=answer;
              questionArr.push(row);
            })
            this.questionArr = questionArr;//患者回答回显
            // console.log(this.questionArr)
          } else {
            this.handleWarning(response)
          }
        })
        // console.log(this.questionArr)
      },
      handleClose(){
        this.$parent.showList = true;
        this.$parent.showDetail = false;
      },
    },
    created: function(){
      this.getEditData();
    }
  }
</script>
<style >
  .warp-main{
    font-size: 14px;
    font-weight: 500;
    padding: 0px 20px 20px 40px;
  }
  .displayFlex{display: flex; margin-top: 30px;}
  .edit-title-label{line-height: 35px; margin-left: 10px; font-size: 16px; font-weight: 600;}
  .titleHead{padding-left: 30px; background: #FAFAFA; height: 50px; line-height: 50px; font-size: 14px; }
  .rowBox{padding-left: 30px; line-height: 40px; border-bottom: 1px solid #EBEBEB;}
  .contentBox{width: 90%; margin-top: 30px; }
  .marginLeftAndTop{margin-left: 30px; margin-top: 20px;}
</style>
