<template>
  <div @click="" id="page" style="overflow-y: hidden;">
    <!--查看详情-->
      <div style="overflow-y: auto; height:calc(100vh - 100rpx)">
        <div v-show="showStep1">
          <div style="height: 88rpx; background: #464C5B;  color: white; align-items: center; align-content: center; line-height: 88rpx;">
            <van-row>
              <van-col span="2" offset="1"><div style="height: 40rpx; margin-top: 24rpx; line-height: 40rpx; text-align: center; width: 40rpx; border-radius: 50%; background: white; color: #464C5B;">1</div></van-col>
              <van-col span="20"><div>患者个人信息</div></van-col>
            </van-row>
          </div>
          <div style="margin: 20rpx 40rpx; border: none!important;" v-for="(obj,i) in pdcItems" :key="i">
            <div v-if="i===1" style="border-bottom: 1px solid #E4E4E4;"><!--性别-->
              <van-field :value="obj.answer" :label="obj.label" readonly input-align="right" style="border: none!important;"
                         :border="false"
              ></van-field>
            </div>
            <div v-else style="border-bottom: 1px solid #E4E4E4;">
                <van-field :value="obj.answer" :label="obj.label" readonly input-align="right" style="border: none!important;"
                           :border="false"
                ></van-field></div>
          </div>

          <div style="position: fixed; z-index:9;  text-align: center; color: white; bottom: 0rpx; width: 100%; height: 90rpx; line-height: 90rpx; background: #464C5B;" @click="submit(1)"> 查看问卷信息</div>
        </div>
        <div v-show="showStep2">
          <div style="height: 88rpx; background: #464C5B;  color: white; align-items: center; align-content: center; line-height: 88rpx;">
            <van-row>
              <van-col span="2" offset="1"><div style="height: 40rpx; margin-top: 24rpx; line-height: 40rpx; text-align: center; width: 40rpx; border-radius: 50%; background: white; color: #464C5B;">2</div></van-col>
              <van-col span="20"><div>问卷信息</div></van-col>
            </van-row>
          </div>
          <div style="margin: 20rpx 40rpx;" v-for="(obj,i) in questionList" :key="i">
            <div v-if="obj.questionType===1" style="border-bottom: 1px solid #E4E4E4;"><!--questionType=1 多选-->
              <div style="line-height: 40rpx; margin-top: 15rpx; margin-bottom: 15rpx;">{{obj.questionTitle}}</div>
              <van-checkbox-group :value="obj.answer" >
                <div v-for="(item,index) in obj.chooseArr" :key="index" style="line-height: 70rpx;">
                  <van-checkbox checked-color="#44DAB7" :name="item.itemId">
                    <span>{{item.itemName}}</span>
                  </van-checkbox>
                </div>
              </van-checkbox-group>
            </div>
            <div v-else-if="obj.questionType===0" style="border-bottom: 1px solid #E4E4E4;"><!--type=0 单选-->
              <div  style="line-height: 40rpx; margin-top: 15rpx; margin-bottom: 15rpx;">{{obj.questionTitle}}</div>
              <van-radio-group :value="obj.answer" >
                <div v-for="(item,index) in obj.chooseArr" :key="index" style="height: 70rpx; line-height: 70rpx;">
                  <van-radio :name="item.itemId"  checked-color="#44DAB7">
                    <span style="color:#323233;">{{item.itemName}}</span>
                  </van-radio>
                </div>
              </van-radio-group>
            </div>
          </div>
          <div style="position: fixed; z-index: 9999;  text-align: center; color: white; bottom: 0rpx; width: 100%; display: flex; height: 90rpx; line-height: 90rpx;">
            <div  style="width: 50%;  background: #464C5B;" @click="submit(2)">查看个人信息</div>
            <div  style="width: 50%;  background: #44DAB7;" @click="clickBack()">退出</div>
          </div>
        </div>
      </div>
    <van-toast id="van-toast" />
  </div>
</template>
<script>
import Toast from '../../../static/vant-weapp/toast/toast';
export default {
  data () {
    return {
      showDetailId:undefined,
      flag:false,
      chooseSex:1,
      radioArr:[{value:"0",label:'男'},{value:"1",label:'女'},],
      showStep1:true,
      showStep2:false,
      pdcItems:[],
      questionList:[],
      groupId:undefined,
      groupPdcItem:[],
      userInfo:{},
      pCode:'',
      code:'',
      addData:{
        phone:'',
      },
      isLoading:false,
      radio1:'1',
      userRole:0,
      answer:[],
      result:[],
      listArr:[],
    }
  },
  components: {
  },
  mounted() {
    this.getInfoData();
    let info=wx.getStorageSync('userInfo');
    this.userInfo=info;
  },
  methods: {
    onInput(event) {
      this.birthday= event.mp.detail;
    },
    clickBack(){
      wx.navigateBack();
    },
    getInfoData(){
      let vm = this;
      vm.utils.GET('clin/mp/inv/findDetailById', {id:this.showDetailId}).then(l_res=>{
          if(l_res.data.status === 200) {
            let res = l_res.data.data;

            let groupInfo=res.groupInfo;
            let pdcItems=res.pdcItems;
            let pdcInfo=res.pdcInfo;
            let questionList=res.questionList;
            let config=res.answer;

            let groupPdcItem=groupInfo.pdc_items.split(",");
            let newPdc=[];
            pdcItems.forEach((item)=>{
              if(item.value==='id'){
              }else{
                if(pdcInfo.hasOwnProperty(item.value)&&item.value!='create_time'){
                  if(groupPdcItem.indexOf(item.value)>-1){
                    item.answer=pdcInfo[item.value];
                    newPdc.push(item)
                  }
                }
              }
            })
            this.pdcItems=newPdc;

            let questionArr=[];
            questionList.forEach((row)=>{
              let answer='';
              if(config !=null){
                config.forEach((item)=>{
                  if(item.id==row.Id){
                    if(row.questionType==0) {
                      row.answer = item.value;
                    }else{
                      row.answer = item.value.split(",");
                    }
                  }
                })
              }
              questionArr.push(row);
            })
            this.questionList= questionArr;//患者回答回显
          }else{
            Toast.fail(l_res.data.message);
          }
      }).catch(
        l_err=>{console.log(l_err);}
        );
    },
    submit(type){
      if(type===1){//下一步
        this.showStep1=false;
        this.showStep2=true;
      }else if(type===2) {//返回上一步
        this.showStep1 = true;
        this.showStep2 = false;
      }
    },
    vanFieldChange1(value,i){
      this.pdcItems[i].flag=false;
      this.pdcItems[i].answer = value.mp.detail;
    },
    vanFieldChange(value,name){
      this.addData[name] = value.mp.detail;
    },
  },
  created () {
  },
  onShow(){
    this.utils.isLoginOut();
    this.showStep1=true;
    this.showStep2=false;
  },
  onLoad(options){
    this.showDetailId=options.id;
  }
}
</script>
<style scoped>

</style>
