<template>
  <div @click="" id="page" style="overflow-y: hidden;">
    <!--问卷调查编辑-->
    <div style="overflow-y: auto; height:calc(100vh - 100rpx)">

      <div v-show="showStep1">
        <div style="height: 88rpx; background: #464C5B;  color: white; align-items: center; align-content: center; line-height: 88rpx;">
          <van-row>
            <van-col span="2" offset="1"><div style="height: 40rpx; margin-top: 24rpx; line-height: 40rpx; text-align: center; width: 40rpx; border-radius: 50%; background: white; color: #464C5B;">1</div></van-col>
            <van-col span="15"><div>第一步，患者个人信息</div></van-col>
            <van-col span="5"><div @click="clickBack">取消修改</div></van-col>
          </van-row>
        </div>
        <div style="margin: 20rpx 40rpx; border: none!important;" v-for="(obj,i) in pdcItems">
          <div v-if="i===1" style="border-bottom: 1px solid #E4E4E4;"><!--性别单选-->
            <van-cell title="性别" use-label-slot :border="flag">
              <van-radio-group
                :value="obj.answer"
                @change="onChange11"
                direction="horizontal">
                <div style="float: right; margin-left: 40rpx;" v-for="(radio,r) in radioArr" :key="r"><van-radio :name=radio.value>{{radio.label}}</van-radio></div>
              </van-radio-group>
            </van-cell>
          </div>
          <div v-else-if="i===2" style="border-bottom: 1px solid #E4E4E4;"><!--年月日选择-->
            <van-field :value="obj.answer" :label="obj.label" :error=obj.flag input-align="right"
                       placeholder="请选择日期" right-icon="arrow"
                       :border="false" disabled @click="chooseDate"
            ></van-field></div>
          <div v-else style="border-bottom: 1px solid #E4E4E4;">
            <van-field :value="obj.answer" :label="obj.label" :maxlength="obj.label==='联系电话'?11:50" :type="obj.label==='联系电话'?'number':''"
                       :placeholder="'请输入'+obj.label" :error=obj.flag input-align="right" style="border: none!important;"
                       :border="false" @blur="(e)=>vanFieldBlur(e,i)" @change="(val)=>vanFieldChange1(val,i)"
            ></van-field></div>
        </div>

        <div style="position: fixed; z-index:9;  text-align: center; color: white; bottom: 0rpx; width: 100%; height: 90rpx; line-height: 90rpx; background: #44DAB7;" @click="submit(1)"> 下一步</div>

      </div>


      <div v-show="showStep2">
        <div style="height: 88rpx; background: #464C5B;  color: white; align-items: center; align-content: center; line-height: 88rpx;">
          <van-row>
            <van-col span="2" offset="1"><div style="height: 40rpx; margin-top: 24rpx; line-height: 40rpx; text-align: center; width: 40rpx; border-radius: 50%; background: white; color: #464C5B;">2</div></van-col>
            <van-col span="15"><div>第二步，问卷信息</div></van-col>
            <van-col span="5"><div @click="clickBack">取消修改</div></van-col>
          </van-row>
        </div>
        <div style="margin: 20rpx 40rpx;" v-for="(obj,i) in questionList">
          <div v-if="obj.questionType===1" style="border-bottom: 1px solid #E4E4E4;"><!--questionType=1 多选-->
            <div style="line-height: 40rpx; margin-top: 15rpx; margin-bottom: 15rpx;">{{obj.questionTitle}}</div>
            <van-checkbox-group :value="obj.answer" @change="onChangeBox($event,i)">
              <div v-for="(item,index) in obj.chooseArr"  style="line-height: 70rpx;">
                <van-checkbox checked-color="#44DAB7" :name="item.itemId">
                  <span>{{item.itemName}}</span>
                </van-checkbox>
              </div>
            </van-checkbox-group>
          </div>
          <div v-else-if="obj.questionType===0" style="border-bottom: 1px solid #E4E4E4;"><!--type=0 单选-->
            <div  style="line-height: 40rpx; margin-top: 15rpx; margin-bottom: 15rpx;">{{obj.questionTitle}}</div>
            <van-radio-group :value="obj.answer" @change="onChange($event,i)">
              <div v-for="(item,index) in obj.chooseArr" :key="index" style="height: 70rpx; line-height: 70rpx;">
                <van-radio :name="item.itemId"  checked-color="#44DAB7">
                  <span style="color:#323233;">{{item.itemName}}</span>
                </van-radio>
              </div>
            </van-radio-group>
          </div>
        </div>

        <div style="position: fixed; z-index: 9999;  text-align: center; color: white; bottom: 0rpx; width: 100%; display: flex; height: 90rpx; line-height: 90rpx;">
          <div  style="width: 50%;  background: #464C5B;" @click="submit(2)">上一步</div>
          <div style="width: 50%;  background: #44DAB7;" @click="submit(3)">提交</div>
        </div>

      </div>
    </div>

    <van-popup :show="showDate" position="bottom" custom-style="height: 50%;"  @close="onClose">
      <van-datetime-picker
        type="date"
        :value="birthday"
        title="选择出生年月"
        :min-date="minDate"
        :max-date="maxDate"
        @confirm="confirmDate"
        @cancel="onClose"
      ></van-datetime-picker>
    </van-popup>
    <van-toast id="van-toast" />
  </div>
</template>

<script>
  import Toast from '../../../static/vant-weapp/toast/toast';
  export default {
    data () {
      return {
        showDetailId:undefined,
        pdcId:undefined,
        pdId:undefined,
        groupId:undefined,
        flag:false,
        chooseSex:1,
        radioArr:[{value:"0",label:'男'},{value:"1",label:'女'},],
        birthday: new Date().getTime(),
        minDate: new Date(1900, 1, 1).getTime(),
        maxDate: new Date().getTime(),
        showDate:false,
        showStep1:true,
        showStep2:false,
        pdcItems:[],
        questionList:[],
        groupPdcItem:[],
        userInfo:{},
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
      // this.getSetting();//判断用户是否授权登录过
      this.getInfoData();
      let info=wx.getStorageSync('userInfo');
      this.userInfo=info;
    },
    methods: {
      onClose(){
        this.showDate = false;
        this.birthday='';
      },
      confirmDate(e){
        this.birthday=e.mp.detail;
        this.pdcItems[2].answer = this.todateDate(e.mp.detail);
        this.showDate = false;
      },
      todateDate(timeStamp) {//时间戳转换日期格式
        if (timeStamp===''|| timeStamp===null||timeStamp===undefined || timeStamp==='0' || timeStamp===0) return '';
        let date = new Date();
        date.setTime(timeStamp);
        let y = date.getFullYear();
        let m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        let d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        return timeStamp = y + '-' + m + '-' + d;
      },
      cancelDate(){},
      chooseDate(){
        this.showDate =true;
      },
      getInfoData(){
        let vm = this;
        vm.utils.GET('clin/mp/inv/findDetailById', {id:this.showDetailId}).then(l_res=>{
          if(l_res.data.status === 200) {
            let res = l_res.data.data;
            this.pdId = res.pdId;
            let groupInfo=res.groupInfo;
            let questionList=res.questionList;
            let config=res.answer;
            let pdcItems=res.pdcItems;
            let pdcInfo=res.pdcInfo;
            this.groupId=groupInfo.id;
            this.groupPdcItem=groupInfo.pdc_items.split(",");
            let newPdc=[];
            pdcItems.forEach((item)=>{
              if(item.value==='id'){
                this.pdcId=pdcInfo.id;
              }else{
                if(pdcInfo.hasOwnProperty(item.value)&&item.value!='create_time'){
                  if(this.groupPdcItem.indexOf(item.value)>-1){
                    item.answer=item.value=='sex'?(pdcInfo[item.value]=='男'?"0":"1"):pdcInfo[item.value];
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
      clickBack(){
        wx.navigateBack();
      },

      onChangeBox(event,i){
        this.questionList[i].answer = event.mp.detail;
      },
      onChange1(event,i){
        this.pdcItems[i].answer = event.mp.detail;
      },
      onChange11(event){
        this.pdcItems[1].answer = event.mp.detail;
      },
      onChange(event,i){
        this.questionList[i].answer = event.mp.detail;
      },
      submit(type){
        if(type===1){//下一步
          let param = {};
          for(let i=0; i<this.pdcItems.length; i++){
            let item = this.pdcItems[i].value;
            if(this.pdcItems[i].answer && this.pdcItems[i].answer!==null && this.pdcItems[i].answer!==undefined &&this.pdcItems[i].answer!==''){
              param[item] = this.pdcItems[i].answer;
            }else{
              //判断是否为必填项
              if(this.groupPdcItem.indexOf(item)>-1){
                this.pdcItems[i].flag=true;
                return;
              }
            }
          }
          this.showStep1=false;
          this.showStep2=true;
        }else if(type===2){//返回上一步
          this.showStep1=true;
          this.showStep2=false;
        }else if(type===3){//提交
          this.isLoading = true;
          let param = {};
          let total= 0;
          for(let i=0; i<this.pdcItems.length; i++){
            let item = this.pdcItems[i].value;
            if(this.pdcItems[i].answer && this.pdcItems[i].answer!==null && this.pdcItems[i].answer!==undefined &&this.pdcItems[i].answer!==''){
              param[item] = this.pdcItems[i].answer;
            }else{
              //判断是否为必填项
              if(this.groupPdcItem.indexOf(item)>-1){
                this.pdcItems[i].flag=true;
                return;
              }
            }
          }
          this.updateData(param)
        }
      },
      updateData(param){
        let info={};//存储用户信息配置
        info=param;
        info.id=this.pdcId;
        let config=[];//存储选项配置
        let score=0;//存储得分配置
        this.questionList.forEach((row)=>{
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
            if(row.answer==undefined||row.answer.length<1||row.answer==""){
              j.value="";
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
          }
          config.push(j);
        })
        let data={};
        data.pdc=info;
        data.config=config;
        data.score=score;
        data.groupId=this.groupId;
        data.pdId=this.pdId;
        data.userId=this.userInfo.user_id;
        let vm = this;
        vm.utils.POSTJSON('clin/mp/inv/createExamine',data).then(l_res=>{
          if(l_res.data.status===200){
            Toast.success('数据保存成功');
            vm.clickBack();
          }else{
            Toast.fail(l_res.data.message);
          }
        })
      },
      vanFieldChange1(value,i){
        if(this.pdcItems[i].answer=='') {
          this.pdcItems[i].flag = false;
          this.pdcItems[i].answer = value.mp.detail;
        }
      },
      vanFieldBlur(value,i){
        this.pdcItems[i].answer = value.mp.detail.value;
        if(this.pdcItems[i].answer==''){
          this.pdcItems[i].flag = true;
        }
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
