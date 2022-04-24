<!--问卷列表-问卷编辑-->
<template>
  <div class="warp-main" v-show="visible">
    <div class="displayFlex">
        <div class="edit-title-icon">1</div>
        <div class="edit-title-label">问卷信息</div>
    </div>
    <div class="lineHeight70">
      <span class="colorRed" >* </span><span> 问卷名称：</span><el-input
        type="text"
        placeholder="请输入问卷名称"
        v-model="obj.name"
        style="width: 300px;"
        maxlength="20"
        show-word-limit></el-input>
    </div>
    <div>
      <div class="displayFlex">
        <div class="edit-title-icon">2</div>
        <div class="edit-title-label">患者个人信息</div>
      </div>
      <div class="smallTitle">可以任意选择患者个人信息选项，多选（其中姓名、性别、出生年月必选）</div>

      <div class="contentBox">
        <div class="titleHead">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="checkAllFun">个人信息</el-checkbox></div>
          <el-checkbox-group v-model="checkedInfo" @change="handleCheckedChange">
            <div class="rowBox" v-for="(obj,i) in infoArr"><el-checkbox :label="obj.value" :key="i" :disabled="i<3">{{obj.label}}</el-checkbox></div>
          </el-checkbox-group>
      </div>
    </div>
    <div>
      <div class="displayFlex">
        <div class="edit-title-icon">3</div>
        <div class="edit-title-label">问卷问题管理</div>
      </div>
      <div class="smallTitle">可以修改、新增、删除问卷信息表单</div>

      <div class="contentBox">
        <div class="titleHead">
          <el-row>
            <el-col :span="2"><div class="paddingSmall">问题</div></el-col>
            <el-col :span="8"><div class=""> <span style="color: blue;" @click="addQuestionRow">+添加新项</span></div></el-col>
            <el-col :span="10"><div class="">选项</div></el-col>
            <el-col :span="4"><div class="">操作</div></el-col>
          </el-row>
        </div>
        <div class="rowBox" v-for="(item,i) in questionArr" :key="i">
          <el-row>
            <el-col :span="10"><div class="paddingSmall">{{item.questionTitle}}</div></el-col>
            <el-col :span="10"><div class="">{{item.questionType===0?'【单选】':'【多选】'}} <span v-for="(obj,j) in item.chooseArr" :key="j"><span v-if="j!==0"> , </span>{{obj.itemName}}</span></div></el-col>
            <el-col :span="4">
              <el-row><el-col :span="12"><div style="color: blue;" @click="editQuestion(i,item)">编辑</div></el-col>
                <el-col :span="12"><div style="color: blue;" @click="deleteQuestion(i)">删除</div></el-col></el-row>
            </el-col>
          </el-row>
        </div>

      </div>
    </div>
    <div class="marginLeftAndTop">
      <el-button  size="small" type="primary" @click="submit" :disabled="isSave">保 存</el-button>
      <el-button  size="small" @click="handleClose()">取 消</el-button></div>

    <el-dialog
      :title="editTitle"
      :visible.sync="dialogVisible"
      width="800px"
      :before-close="handleCancel">
      <div style="height: 50px; line-height: 50px; padding-left: 30px;">
        <el-row>
          <el-col :span="3"><span style="color: red;">*</span>问题内容：</el-col>
          <el-col :span="21"> <el-input class="width300" v-model="questionObj.questionTitle" placeholder="请输入内容" maxlength="150"></el-input></el-col>
        </el-row>
        <el-row>
          <el-col :span="3"><span style="color: red;">*</span>答案类型：</el-col>
          <el-col :span="21">
            <el-radio-group v-model="questionObj.questionType">
              <el-radio :label=0>单选</el-radio>
              <el-radio :label=1>多选</el-radio>
            </el-radio-group>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="3"><span style="color: red;">*</span>答案选项：</el-col>
          <div v-for="(obj,i) in questionObj.chooseArr">
            <el-col :span="21" :offset="i===0?0:3">
              <el-input class="width200" v-model="obj.itemName" placeholder="请填写答案" maxlength="100"></el-input>
              <el-input type="number" class="width80" v-model="obj.score" placeholder="请填写分数" maxlength="50" ></el-input>
              <span style="color: blue;" @click="toDeleteRow(i)">删除</span>
            </el-col>
          </div>
          <el-col :span="21" :offset="3">
            <div v-show="showError" style="color:red;">{{errorText}}</div>
          </el-col>
          <el-col :span="21" :offset="3" v-show="questionObj.chooseArr.length<10">
            <span style="color: blue;" @click="toAddRow">添加新项</span>
          </el-col>
        </el-row>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="toSubmit" >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import {saveQuestion,findTitleById} from  '@/api/questlist.js'
  export default {
    props: {
      visible: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        rules:{
          salesAuditResult:[{ required: true, message: '销售审核不能为空'}],
          salesAuditDetail:[{ required: true, message: '销售反馈详情不能为空'}],
        },
        merchants:{id:'',merchantsName:'',merchantsPhone:''},
        obj:{name:'',},
        checkAll: true,
        isIndeterminate: false,
        infoArrAll:['name','sex','birth','occupation','telephone','pressure','sugar','alcohol','smoke','diet','weight','height'],
        infoArr:[{value:'name',label:'姓名'},{value:'sex',label:'性别'},{value:'birth',label:'出生年月'},{value:'occupation',label:'职业'},
          {value:'telephone',label:'联系电话'},{value:'pressure',label:'血压（左侧或右侧）'},{value:'sugar',label:'血糖'},{value:'alcohol',label:'饮酒情况'},{value:'smoke',label:'吸烟情况'},{value:'diet',label:'饮食情况'},{value:'weight',label:'体重'},{value:'height',label:'身高'}],
        checkedInfo:['name','sex','birth','occupation','telephone','pressure','sugar','alcohol','smoke','diet','weight','height'],
        questionArr:[],
        dialogVisible:false,//是否显示弹层
        editTitle:'添加新项',
        questionObj: {questionTitle:'', questionType:undefined, chooseArr:[{itemName:'',score:''},{itemName:'',score:''},]},//问题答案列表},
        showError:false,
        cleanTime:{},
        errorText:'',
        editRow:'',
        isSave:false
      }
    },
    watch:{
      /*merchants:{
        handler(val){//手机号码字段数字验证
          val.merchantsPhone=this.isNotzero(val.merchantsPhone);
        },
        deep:true // 必须加这个属性
      },*/
    },
    methods: {
      deepCloneObj (obj){//深拷贝
        let str, newobj = obj.constructor === Array ? [] : {};
        if(typeof obj !== 'object'){
          return;
        } else if(window.JSON){
          str = JSON.stringify(obj); //系列化对象
          newobj = JSON.parse(str); //还原
        } else {
          for(let i in obj){
            newobj[i] = typeof obj[i] === 'object' ?
              this.deepCloneObj(obj[i]) : obj[i];
          }
        }
        return newobj;
      },
      editQuestion(index,row){
        this.editRow = index;
        this.questionObj = this.deepCloneObj(row);
        this.editTitle = '修改新项';
        this.dialogVisible = true;
      },
      deleteQuestion(index){
        this.questionArr.splice(index,1)
      },
      toSubmit(){
        this.errorText = '';
        clearTimeout(this.cleanTime);
        if(this.questionObj.questionTitle ===''){
          this.errorText='请输入问题内容';
          this.showError = true;
          this.cleanTime= setTimeout(()=>{
            this.showError = false;
          },3000)
        }else if(this.questionObj.questionType===undefined){
          this.errorText='请选择答案类型';
          this.showError = true;
          this.cleanTime= setTimeout(()=>{
            this.showError = false;
          },3000)
        }else{
          let flag=true;
          for(let i=0; i<this.questionObj.chooseArr.length; i++){
            let item = this.questionObj.chooseArr[i];
            if(item.itemName===''){
              this.showError = true;
              this.errorText='答案选项不能为空';
              flag = false;
              break;
            }else if(item.score===''){
              this.showError = true;
              this.errorText='答案分数不能为空';
              flag = false;
              break;
            }
          }
          this.$nextTick(() => {
            if(flag){
              if(this.editRow!==''){
                this.questionArr[this.editRow] = this.questionObj;
              }else{
                this.questionArr.push(this.questionObj)
              }
              this.dialogVisible = false;
            }else{
              this.cleanTime= setTimeout(()=>{
                this.showError = false;
              },3000)
            }
          })
        }
      },
      toAddRow(){
        this.editRow = '';
        this.questionObj.chooseArr.push({itemName:'',score:''})
      },
      toDeleteRow(index){
        clearTimeout(this.cleanTime)
        if(this.questionObj.chooseArr.length>2){
          //删除
          this.questionObj.chooseArr.splice(index,1)
        }else{
          this.errorText='答案选项不能少于2个';
          this.showError = true;
          this.cleanTime= setTimeout(()=>{
            this.showError = false;
          },3000)
        }
      },
      handleCancel(done) {
        this.$confirm('输入的问题还未保存，是否确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      addQuestionRow(){//type 0单选 1多选
        this.questionObj = {questionTitle:'', questionType:undefined, chooseArr:[{itemName:'',score:''},{itemName:'',score:''},]};
        this.editTitle = '添加新项';
        this.dialogVisible = true;
      },
      submit(){
        let infos=this.checkedInfo;
        let questionSetting={
          questionName : this.obj.name,
          pdcItems : infos.join(","),
          questionTitle :this.questionArr
        };
        if(this.obj.name.length<1){
          this.$message({
            message: '请输入问卷名称！',
            type: 'warning',
            duration: 2000
          })
          return
        }
        this.isSave=true;
        saveQuestion(questionSetting).then(response => {
          if (response.message === 'success') {
            this.$message({
              message: this.$t('common.add-success'),
              type: 'success',
              duration: 2000
            })
            this.handleClose();
          } else {
            this.isSave=false;
            this.handleWarning(response)
          }
        })


      },
      handleCheckedChange(value) {
        let checkedCount = value.length;
        this.checkedInfo = value;
        this.checkAll = checkedCount === this.infoArr.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.infoArr.length;
      },
      checkAllFun(val){
        this.checkedInfo = val ? this.infoArrAll : ['name','sex','birth'];
        this.isIndeterminate = !val;
      },
      handleClose(){
        this.isSave=false;
        this.$parent.back();
      },
      getQuestion(){
        // this.questionArr = [];
        findTitleById(this.$parent.showDetailId).then(response => {
          if (response.message === 'success') {
            // this.obj.name=response.data.name;
            let checkedInfo=response.data.checkedInfo;
            this.checkedInfo=checkedInfo.split(',');
            this.questionArr =response.data.questionArr;
          } else {
            this.handleWarning(response)
          }
        })
      }
    },
    handleWarning(response) {
      this.$message({
        message: response.message || response,
        type: 'warning',
        duration: 2000
      })
      this.disabledStatus = false
    },
    created: function(){
      this.getQuestion();
    }
  }
</script>
<style >
  .backBtn{ float: right;}
  .el-checkbox__label {
    margin-left: 50px!important;
    color: black;
  }
  .el-checkbox__input.is-checked + .el-checkbox__label {
    color: black;
  }
  .colorRed{color: red;}
  .warp-main{padding: 30px 20px 20px 40px;}
  .displayFlex{display: flex; margin-top: 30px;}
  .edit-title-icon{ width: 35px; text-align: center; font-size: 16px; line-height: 35px; height: 35px; border-radius: 50%; background: #1890FF; color: white;}
  .edit-title-label{line-height: 35px; margin-left: 10px; font-size: 16px; font-weight: 600;}
  /*.marginAnd*/
  .lineHeight70{line-height:70px;}
  .smallTitle{clear: both; color: #8D8D8D; font-size: 12px; margin-left: 45px; margin-top: 5px;}
  .titleHead{padding-left: 30px; background: #FAFAFA; height: 50px; line-height: 50px; font-size: 14px; }
  .rowBox{padding-left: 30px; line-height: 40px; border-bottom: 1px solid #EBEBEB;}
  .contentBox{width: 90%; margin-top: 30px; }
  .marginLeftAndTop{margin-left: 30px; margin-top: 20px;}
  .paddingSmall{padding-left: 20px;}
  .width300{width: 400px;}
  .width200{width: 260px;}
  .width80{width: 100px;}
</style>
