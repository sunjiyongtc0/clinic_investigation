<!--义诊详情-->
<template>
  <div class="warp-main" v-show="visible">
    <div style="float: right;"><el-button  size="small" @click="handleClose()">返 回</el-button></div>
    <div class="displayFlex">
      <div class=""><img src="../../../image/infoIcon.png" style="margin-top: 4px;" height="22" width="30"/></div>
      <div class="edit-title-label">待审核患者个人信息</div>
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
        <div class="edit-title-label">待审核问卷详情</div>
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
  import { findBakDetailById } from '@/api/examine'

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
        questionArr:[],
        // infoArr:[{value:'张三',label:'姓名'},{value:'男',label:'性别'},{value:'1995-10',label:'出生年月'},{value:'自由职业',label:'职业'},
        //   {value:'13000000000',label:'联系电话'},{value:'130/230',label:'血压（左侧或右侧）'},{value:'6.5',label:'血糖'},{value:'未饮酒',label:'饮酒情况'},],
        // questionArr:[{answer:'外周动脉疾病；疾病2；疾病3；',questionTitle:'有无以下疾病（多选）',},
        //   {answer:'否',questionTitle:'是否经常锻炼？',},
        //   {answer:'无障碍',questionTitle:'判断力是否出现了障碍？',},
        //   {answer:'少动，不感兴趣',questionTitle:'不爱活动？或对事情不感兴趣？',},
        //   {answer:'很少重复',questionTitle:'是否会不断重复同一件事或同一句话？',},
        //   {answer:'有困难',questionTitle:'学习新东西使用方法时，是否会有困难？',},
        //   {answer:'有时',questionTitle:'是否有时会记不清当前的月份或年份？',},
        //   {answer:'有难度',questionTitle:'处理复杂的个人事情时，是否存在困难？',},
        //   {answer:'有时',questionTitle:'是否会忘记与某人的约定',},
        // ],
      }
    },
    watch:{},
    methods: {
      getEditData(){
        findBakDetailById(this.$parent.showDetailId).then(response => {
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
          } else {
            this.handleWarning(response)
          }
        })
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
