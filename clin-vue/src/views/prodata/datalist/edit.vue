<!--义诊编辑-->
<template>
  <div class="warp-main" v-show="visible">
<!--    <div style="float: right;"><el-button  size="small"  @click="handleClose()">返 回</el-button></div>-->
    <div class="displayFlex">
      <div class=""><img src="../../../image/infoIcon.png" style="margin-top: 4px;" height="22" width="30"/></div>
      <div class="edit-title-label">患者个人信息</div>
    </div>
    <div class="marginLeftAndTop">
      <el-row>
        <div style="line-height: 40px; " v-for="(item,i) in infoArr" :key="i">

          <el-col :span="8"><div class="">{{item.label}}：<el-input v-model="item.value" class="widthInput" :placeholder="'请输入'+item.label" :disabled="i<3"></el-input></div></el-col>
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
            <el-col :span="10"><div class="">问题</div></el-col>
            <el-col :span="14"><div class="">答案</div></el-col>
          </el-row>
        </div>
        <div class="rowBox" v-for="(item,i) in questionArr" :key="i">
          <el-row>
            <el-col :span="10"><div class="">{{item.questionTitle}}</div></el-col>
            <el-col :span="14">
            <div v-if="item.questionType===1">
                  <el-checkbox-group v-model="item.answer">
                    <el-checkbox  v-for="(obj,j) in item.chooseArr" :key="j" :label="obj.itemId">{{obj.itemName}}</el-checkbox>
                  </el-checkbox-group>
            </div>
            <div class="" v-else>
                <el-radio  v-for="(obj,j) in item.chooseArr" :key="j" v-model="item.answer" :label="obj.itemId">{{obj.itemName}}</el-radio>
            </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
    <div class="marginLeftAndTop">
      <el-button  size="small" type="primary" @click="submit" :disabled="isSave">提交修改申请</el-button>
      <el-button  size="small" @click="handleClose()">取 消</el-button></div>
  </div>
</template>

<script>
  import { findDetailById } from '@/api/investigation'
  import { createExamine } from '@/api/examine'

  export default {
    props: {
      visible: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        pdcId:undefined,
        groupId:undefined,
        pdId:undefined,
        userId:undefined,
        pdcItemsDic:[],
        infoArr:[],
        questionArr:[],
        isSave:false
      }
    },
    watch:{},
    methods: {
      submit(){
        let info={};//存储用户信息配置
        this.infoArr.forEach((row)=>{
          this.pdcItemsDic.forEach((item)=> {
            if(item.label==row.label){
              info[item.value]=row.value;
            }
          })
        })
        info.id=this.pdcId;
        let config=[];//存储选项配置
        let score=0;//存储得分配置
        this.questionArr.forEach((row)=>{
          let j={};
          j.id=row.Id;
          if(row.questionType==0) {
            j.value = row.answer+"";
            row.chooseArr.forEach((choose) => {
              if (choose.itemId == row.answer) {
                score += choose.score;
              }
            })
          }else{
            j.value = row.answer.join(",");
            row.answer.forEach((an) => {
              row.chooseArr.forEach((choose) => {
                if (choose.itemId == an) {
                  score += choose.score;
                }
              })
            })
          }
          config.push(j);
        })

        let data={};
        data.pdc=info;
        data.config=config;
        data.groupId=this.groupId;
        data.score=score;
        data.userId=this.userId;
        data.pdId=this.pdId;
        createExamine(data).then(response => {
          if (response.message === 'success') {
            this.$message({
              message: '操作完成',
              type: 'success',
              duration: 2000
            })
            this.isSave=true;
            this.handleClose();
            this.$parent.back();
          }else{
            this.handleWarning(response)
          }
        })

      },
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
              let answer=undefined;
              if(config !=null){
                config.forEach((item)=>{
                  if(item.id==row.Id){
                    if(row.questionType==1){
                      answer=item.value.split(",");
                      // answer=item.value.split(",").map(Number);//字符串数组转化为数字数组
                    }else{
                      // answer=parseInt(item.value);
                      answer=item.value;
                    }
                  }
                })
              }else{
                if(row.questionType==1){
                  answer=[];
                }
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
        this.$parent.showEdit= false;
      },
      handleWarning(response) {
        this.$message({
          message: response.message || response,
          type: 'warning',
          duration: 2000
        })
      }
    },
    created: function(){
      this.getEditData();
    }
  }

  // this.infoArr = [{value:'张三',label:'姓名'},{value:'男',label:'性别'},{value:'1995-10',label:'出生年月'},{value:'自由职业',label:'职业'},
  //   {value:'13000000000',label:'联系电话'},{value:'130/230',label:'血压（左侧或右侧）'},{value:'6.5',label:'血糖'},{value:'未饮酒',label:'饮酒情况'},]
  // this.questionArr = [{type:1,answer:['疾病2','疾病3'],answerArr:['外周动脉疾病','疾病2','疾病3'],title:'有无以下疾病（多选）',},
  //   {type:0,answer:'否',answerArr:['否','疾病2'],title:'是否经常锻炼？',},
  //   {type:0,answer:'无障碍',answerArr:['无障碍','疾病2'],title:'判断力是否出现了障碍？',},
  //   {type:0,answer:'少动',answerArr:['少动','不感兴趣','疾病2'],title:'不爱活动？或对事情不感兴趣？',},
  //   {type:0,answer:'很少重复',answerArr:['很少重复','疾病2'],title:'是否会不断重复同一件事或同一句话？',},
  //   {type:0,answer:'有困难',answerArr:['有困难','疾病2'],title:'学习新东西使用方法时，是否会有困难？',},
  //   {type:0,answer:'有时',answerArr:['有时','疾病2'],title:'是否有时会记不清当前的月份或年份？',},
  //   {type:0,answer:'有难度',answerArr:['有难度','疾病2'],title:'处理复杂的个人事情时，是否存在困难？',},
  //   {type:0,answer:'有时',answerArr:['有时','疾病2'],title:'是否会忘记与某人的约定',},]
</script>
<style >
  .warp-main{
    font-size: 14px;
    font-weight: 500;
    padding: 0px 20px 20px 40px;
  }
  .widthInput{width: 250px;}
  .el-checkbox__label{
    margin-left: 0px!important; margin-right: 10px!important;
  }
  .displayFlex{display: flex; margin-top: 30px;}
  .edit-title-label{line-height: 35px; margin-left: 10px; font-size: 16px; font-weight: 600;}
  .titleHead{padding-left: 30px; background: #FAFAFA; height: 50px; line-height: 50px; font-size: 14px; }
  .rowBox{padding-left: 30px; line-height: 40px; border-bottom: 1px solid #EBEBEB;}
  .contentBox{width: 90%; margin-top: 30px; }
  .marginLeftAndTop{margin-left: 30px; margin-top: 20px;}
</style>
