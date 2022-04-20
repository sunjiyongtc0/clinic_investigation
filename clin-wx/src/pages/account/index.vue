<template>
  <div class="page">
    <div>
      <van-search
        :value="searchValue1"
        shape="round"
        placeholder="输入手机号码/志愿者账号搜索"
        @change="onChange1"
        @search="onSearch1"
      >
      </van-search>
    </div>
    <div>
      <scroll-view scroll-y="true" :style="{ height: tabViewHeight+ 'px'}" :scroll-top='scrollTopNum' @scrolltolower="scrollGoodsMore">
        <div v-if="dataArr.length>0">
          <div class="listBox" v-for="(obj,i) in dataArr" :key="i">
            <van-row>
              <van-col :span="16" offset="1">
                <div style="line-height: 80rpx;">{{obj.nickname}}</div>
                <div style="line-height: 80rpx;">{{obj.mobile}}</div>
              </van-col>
              <van-col :span="6" >
                <div style="line-height: 80rpx; color: #999999;">{{obj.status===1?'正常':'已停用'}}</div>
              </van-col>
            </van-row>
            <!--分割线样式？？？-->
            <van-row>
              <van-col :span="16" offset="1">
                <div style="line-height: 80rpx;">录入时间:  {{obj.createTime}}</div>
              </van-col>
              <van-col :span="6" >
                <div style="line-height: 80rpx; color: #44DAB7;">{{obj.roleIdCn}}</div>
              </van-col>
            </van-row>
            <van-row>
              <!--<van-col :span="6" offset="6">
                <div class="rowBtn" @click="btnClick(1,obj)">编辑</div>
              </van-col>-->
              <van-col :span="6" offset="12">
                <div class="rowBtn" @click="btnClick(2,obj)">账号权限</div>
              </van-col>
              <van-col :span="6" >
                <div class="rowBtn color999" @click="btnClick(3,obj)">{{obj.status===1?'停用':'启用'}}</div>
              </van-col>
            </van-row>
          </div>
          <div class="textCenter" v-if="showMore">
            <div>正在加载更多...</div>
          </div>
          <div class="textCenter" v-if="showMoreCarryOut">
            <div>已无更多数据...</div>
          </div>
        </div>
        <div v-else class="textCenter">暂无数据</div>
      </scroll-view>
    </div>
    <div style="position: fixed; z-index:9;  text-align: center; color: white; bottom: 0rpx; width: 100%; height: 90rpx; line-height: 90rpx; background: #44DAB7;" @click="toAddPage">新增账号</div>


    <van-popup :show="showRole" position="bottom" custom-style="height: 60%;"  @close="onClose">
       <div>
         <div style="height: 90rpx; line-height: 90rpx; font-size:30rpx; padding: 0 30rpx; background: #44DAB7; color: white;">
           更改账号权限
           <div style="float: right;"><van-icon name="cross" @click="onClose"/></div>
         </div>
         <div style="line-height: 70rpx; border-top: 1rpx solid #e6e6e6; padding:50rpx;">
           <van-row>
             <van-col span="8" offset="2">
               <img :src="imageUrl+userInfo.avatar"  style="width: 150rpx; height: 150rpx;"/>
             </van-col>
             <van-col span="13">
               <div>{{userInfo.nickname}}</div>
               <div>{{userInfo.mobile}}</div>
             </van-col>
           </van-row>
         </div>
         <div style="padding: 50rpx;">
           <van-radio-group :value="userInfo.roleId" @change="onChange($event,i)">
             <div v-for="(item,index) in chooseArr" :key="index" style="height: 70rpx; line-height: 70rpx; margin-right: 20rpx; float: right;">
               <van-radio :name="item.value"  checked-color="#44DAB7">
                 <span style="color:#323233;">{{item.label}}</span>
               </van-radio>
             </div>
           </van-radio-group>
           <div style="clear: both; color: #999999; font-size:28rpx;">{{roleRule}}</div>
         </div>
       </div>
      <div class="rowBtn-block" @click="AuthClick()">修改权限</div>
    </van-popup>

    <van-toast id="van-toast" />
    <van-dialog id="van-dialog" />
  </div>
</template>

<script>
import Toast from '../../../static/vant-weapp/toast/toast';
import Dialog from '../../../static/vant-weapp/dialog/dialog';
export default {
  data () {
    return {
      userParam:{
        username:'',
        currentPage:1,
        pageSize:5
      },
      userInfo:{},
      showMore:false,
      showMoreCarryOut:false,
      roleRule:'',
      chooseArr:[{name:'志愿者', label:'志愿者'},
        {name:'义诊负责人', label:'义诊负责人'},
        {name:'管理员', label:'管理员'},],
      showRole:false,
      imageUrl:this.utils.imageUrl,
      dataArr:[],
      tabViewHeight:'',
      scrollTopNum:0,
      total:0,
      pageSize:5,
      currentPage:1,
      scrollStatus:true,
      searchValue1:'',
    }
  },
  components: {
  },
  mounted() {
    let _this = this;
    wx.getSystemInfo({
      success (res) {
        _this.tabViewHeight=res.windowHeight-res.statusBarHeight-85;
      }
    });
    this.getList();
    this.getRoleNameCheckbox();
  },
  methods: {
    toAddPage(){
      wx.navigateTo({url: '../userAdd/main'});
    },
    getRoleNameCheckbox(){
      let vm = this;
      vm.utils.GET('clin/mp/sysuserdetail/queryRoleNameCheckbox', this.userParam).then(l_res=>{
          if(l_res.data.status === 200) {
            this.chooseArr=l_res.data.data;

          }else{
            Toast.fail(l_res.data.message);
          }
      })
    },
    onChange1(e) {
      this.userParam.username=e.mp.detail;
    },
    onSearch1() {
      this.userParam.currentPage=1;
      this.userParam.pageSize=5;
      this.getList();
    },
    getList(){
      let vm = this;
      vm.utils.GET('clin/mp/sysuser/querySysUser', this.userParam).then(l_res=>{
          if(l_res.data.status === 200) {
            let res=l_res.data.data;
            this.page=res.pagination.pageSize;
            let current=res.pagination.current;
            this.currentPage=current;
            this.total =res.pagination.total;   //总条数
            let list=res.list;
            let datas=[];
            list.forEach((item)=>{
              item.createTime=timeFormat(item.createTime);
              datas.push(item);
            })
            if(current>1){
              this.dataArr=this.dataArr.concat(datas);
            }else{
              this.dataArr=datas;
            }
            this.scrollStatus=true;
          }
      });
    },
    scrollGoodsMore(){
      let totoPage=Math.ceil(Number(this.total)/Number(this.pageSize));  //总页数
      if(this.currentPage>=totoPage){
        this.showMoreCarryOut = true;
        return;
      }else if(this.scrollStatus){
        this.showMoreCarryOut = false;
        this.showMore = true;
        this.scrollStatus=false;
        setTimeout(()=>{
          if(this.currentPage<totoPage){
            this.userParam.currentPage++;
            this.getList();
            this.showMore=false;
          }
        },1000)
      }else{
        console.log("禁止加载")
      }
    },
    onClose(){
      this.showRole = false;
    },
    onChange(event){
      let roleId=event.mp.detail;
      let msg="";
      this.chooseArr.forEach((row)=>{
        if(row.value===roleId){
          msg=row.label;
        }
      })
      if(msg==='志愿者'){
        this.roleRule = '志愿者权限，可以进行数据的录入、数据修改申请等';
      }else if(msg==='义诊负责人'){
        this.roleRule = '义诊负责人权限，可以进行数据的录入、数据修改申请等';
      }else if(msg==='管理员'){
        this.roleRule = '管理员权限，可以进行数据的录入、数据修改申请等';
      }
      this.userInfo.roleId = event.mp.detail;
    },
    AuthClick(){
      let authdata={
        roleId :[this.userInfo.roleId],
        userId:this.userInfo.id
      };
      let vm = this;
      vm.utils.POSTJSON('clin/mp/sysuserdetail/authorizeRoleToUser', authdata).then(l_res=>{
        if(l_res.data.status === 200) {
          this.showRole=false;
          this.onSearch1();
        }
      });
    },
    btnClick(type,row){
      let vm=this;
      this.userInfo = row;
      if(type===1){//编辑
      }else if(type===2){//账号权限
        if(this.userInfo.roleIdCn==='志愿者'){
          this.roleRule = '志愿者权限，可以进行数据的录入、数据修改申请等';
        }else if(this.userInfo.roleIdCn==='义诊负责人'){
          this.roleRule = '义诊负责人权限，可以进行数据的录入、数据修改申请等';
        }else if(this.userInfo.roleIdCn==='管理员'){
          this.roleRule = '管理员权限，可以进行数据的录入、数据修改申请等';
        }
        this.showRole=true;
      }else if(type===3){//启用停用
         var val=row.status;
        let message = val===1?'确认停用':'确认启用'
        Dialog.confirm({
          title: '',
          message: message,
        }).then(() => {
          vm.utils.POST("clin/mp/sysuserdetail/deleteSysUser",{id:row.id}).then(l_res=> {
            if (l_res.data.status === 200) {
                this.onSearch1();
            }else{
                Toast.fail(l_res.data.message);
            }
          });

        }).catch(() => {
        });
      }
    },
    vanFieldChange(value,name){
      this.user[name] = value.mp.detail;
    },
  },
  created () {
  },
  onShow(){
    this.utils.isLoginOut();
    this.onSearch1();
  },
  onLoad(options){
    Object.assign(this.$data, this.$options.data());  //初始化data
  }
}



function timeFormat(time){
  var date= new Date(time);
  var year=date.getFullYear();
  var month=date.getMonth()+1;
  var day=date.getDate();
  if(month<10){
    month="0"+month;
  }
  if(day<10){
    day="0"+day;
  }
  var newDate=year+"-"+month+"-"+day;
  return newDate;
}
</script>
<style scoped>
  .page{background:#FAFAFA;}
  .rowBtn{height: 50rpx; width: 150rpx; font-size: 30rpx;  text-align: center; color: #44DAB7; border: 1rpx solid #1ABC9C; border-radius: 20rpx; line-height: 50rpx;}
  .rowBtn-block{height: 80rpx; width:86%; margin-left: 7%; font-size: 38rpx;  text-align: center; color: white; background: #1ABC9C; border-radius: 20rpx; line-height: 80rpx;}
  .color999{color: #999999!important; border: 1rpx solid #999999!important;}
  .listBox{margin-top: 30rpx; background: white; min-height: 320rpx; width: 92%; margin-left: 4%; border-radius: 20rpx;}
  .textCenter{text-align: center; padding-top: 40rpx; padding-bottom: 30rpx; color: #999999; font-size: 28rpx;}
</style>
