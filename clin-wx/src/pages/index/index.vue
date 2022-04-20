<template>
  <div @click="" id="page">
    <van-nav-bar  :title="navTitle" fixed></van-nav-bar>
    <div class="content">
      <div style="margin-top: .3rem;  overflow-x: hidden;" v-if="active===0"><!--首页-->
        <div v-if="roleAgentFlag" style=" overflow-y: auto!important; height: 90vh;"><!--有统计-->
          <div class="role0-row1-box" @click="toPageEdit">
            <div class="role0-row1-1"><img src="../../../static/images/index1.png" style="height: 150rpx; width: 150rpx;" /></div>
            <div class="role0-row1-2">数据录入</div>
            <div class="fontSize30" >根据页面提示录入患者信息</div>
          </div>
          <div class="role0-row2-box" style="margin-top: 50rpx;  margin-left:2%; width:96%; height:130rpx;">
            <div style="float: left; width: 50%; background: #F1B36D; height: 130rpx; line-height: 130rpx; text-align: center; border-bottom-left-radius:15rpx; border-top-left-radius:15rpx; ">总问卷  <span style="font-size: 40rpx; margin-left: 20rpx; font-weight: bold; color: white;">{{countNum}}份</span></div>
            <div style="float: right; width: 50%; background: #2589E1; height: 130rpx; line-height: 130rpx; text-align: center; border-bottom-right-radius:15rpx; border-top-right-radius:15rpx; ">总志愿者  <span style="font-size: 40rpx; margin-left: 20rpx; font-weight: bold; color: white;">{{userNum}}人</span></div>
          </div>
          <van-divider />
          <van-tabs :active="echartsActive" @change="onChangeActive" >
            <van-tab title="年龄分析" >
            </van-tab>
            <van-tab title="性别分析" >
            </van-tab>
          </van-tabs>

          <div  style="width:800rpx; height:800rpx;  margin-left: -4%;  margin-top: 1rem;">
            <mpvue-echarts :echarts="echarts" :onInit="initPieChart" canvasId="pieChart"/>
          </div>
          <!--<div  style="margin-top: -50rpx; width:600rpx; height:600rpx; margin-left: 10%; margin-bottom: .2rem; ">-->
            <!--<mpvue-echarts :echarts="echarts" :onInit="initSexPieChart" canvasId="pieSex"/>-->
          <!--</div>-->

        </div>
        <div v-else class="role1-row-box"><!--无统计权限，只能录入数据-->
          <div class="role1-row1-1">
            <img :src="imageUrl+'logo1.svg'" class="role1-row1-img" /></div>
          <div class="role1-row1-2"  @click="toPageEdit">
            <div class="role0-row1-1"><img src="../../../static/images/index1.png" style="height: 150rpx; width: 150rpx;" /></div>
            <div class="role0-row1-2">数据录入</div>
            <div class="fontSize30" >根据页面提示录入患者信息</div>
          </div>
        </div>
      </div>
      <div class="page" v-else-if="active===1"><!--义诊数据-->
        <div>
          <van-search
            :value="searchValue1"
            shape="round"
            placeholder="输入患者姓名/手机号码/志愿者账号搜索"
            use-action-slot
            @change="onChange1"
            @search="onSearch1"
          >
            <view slot="action" @tap="onClick1"><div style="padding-top: 6px;"><van-icon size="26" name="filter-o" /></div></view>
          </van-search>
        </div>
        <div style="overflow-y: auto; ">
          <scroll-view scroll-y="true" :style="{ height: tabViewHeight+ 'px'}" :scroll-top='scrollTopNum' @scrolltolower="scrollGoodsMoreInfo">
            <div v-if="infoList.length>0">
            <div class="index2_listBox" v-for="(obj,i) in infoList" :key="i">
            <van-row @click="findInvDetail(obj)">
              <van-col :span="18" offset="1">
                <div style="line-height: 60rpx; color: #666666; font-size: 34rpx;">{{obj.name}}</div>
                <div style="line-height: 60rpx; color: #999999; font-size: 28rpx;">{{obj.telephone}}</div>
              </van-col>
              <van-col :span="5" >
                <div style="line-height: 120rpx; color: #999999; font-size: 28rpx;">{{obj.age+" / "+obj.sex}}</div>
              </van-col>
            </van-row>
            <van-divider />
            <van-row  >
              <van-col :span="18" offset="1">
                <div style="display: flex;">
                  <div style="line-height:60rpx; color: #999999; font-size: 28rpx;">录入时间 {{obj.create_time}} </div>
                  <div style="margin-left: 30rpx; margin-top: 4rpx;" v-if="obj.status===0"> <van-icon  style=" line-height:60rpx;" name="edit" color="#44DAB7" size="56rpx" @click="findInvEdit(obj)" />
                  </div>
                </div>
              </van-col>


              <van-col :span="5" >
                <div style="line-height:60rpx; color: #44DAB7;">{{obj.score}}分</div>
              </van-col>
            </van-row>
          </div>
            <div class="textCenter" v-if="showMoreInfo">
              <div>正在加载更多...</div>
            </div>
            <div class="textCenter" v-if="showMoreCarryOutInfo">
              <div>已无更多数据...</div>
            </div>
          </div>
          <div v-else class="textCenter">暂无数据</div>
            </scroll-view>
          </div>
      </div>
      <div class="page" v-else-if="roleFlag && active===2"><!--数据审核-->
        <div>
          <!--搜索样式-->
          <van-search
            :value="searchValue2"
            shape="round"
            placeholder="输入患者姓名/手机号码/志愿者账号搜索"
            use-action-slot
            @change="onChange2"
            @search="onSearch2"
          >
            <view slot="action" @tap="onClick2"><div style="padding-top: 6px;"><van-icon size="26" name="filter-o" /></div></view>
          </van-search>
        </div>
        <div style="overflow-y: auto; ">
          <scroll-view scroll-y="true" :style="{ height: tabViewHeight+ 'px'}" :scroll-top='scrollTopNum' @scrolltolower="scrollGoodsMoreExwait">
        <!--<div style="height: calc(90vh-200rpx)">-->
          <!--<div style="margin-top: 30rpx; height: 350rpx; width: 92%; margin-left: 4%; border-radius: 20rpx; background: white;" v-for="(obj,i) in exwaitList" :key="i">-->
            <div v-if="exwaitList.length>0">
              <div class="index2_listBox" v-for="(obj,i) in exwaitList" :key="i">
            <van-row @click="findExwaitDetail(obj)">
              <van-col :span="16" offset="1">
                <div style="line-height: 80rpx;">患者姓名：{{obj.name}}</div>
                <div style="line-height: 80rpx;">手机号码：{{obj.telephone}}</div>
              </van-col>
              <van-col :span="6" >
                <div style="line-height: 160rpx;">{{obj.age+" / "+obj.sex}}</div>
              </van-col>
            </van-row>
            <!--分割线样式？？？-->
            <van-row  @click="findExwaitDetail(obj)">
              <van-col :span="16" offset="1">
                <div style="line-height: 80rpx;">录入时间：{{obj.create_time}}</div>
              </van-col>
              <van-col :span="6" >
                <div style="line-height: 80rpx; color: #44DAB7;">得分：{{obj.score}}</div>
              </van-col>
            </van-row>
            <van-row v-if="obj.status==0">
              <van-col :span="6" offset="12">
                <div class="rowBtn" @click="btnClick(obj,1)">通过</div>
              </van-col>

              <van-col :span="6" >
                <div class="rowBtn" @click="btnClick(obj,2)">拒绝</div>
              </van-col>
            </van-row>

            <van-row>
              <van-col :span="6" offset="12" v-if="obj.status==2">
                <van-tag   type="success">已通过</van-tag>
              </van-col>

              <van-col :span="6" offset="12" v-if="obj.status==1">
                <van-tag   type="warning">已拒绝</van-tag>
              </van-col>
            </van-row>
          </div>

              <div class="textCenter" v-if="showMoreExwait">
                <div>正在加载更多...</div>
              </div>
              <div class="textCenter" v-if="showMoreCarryOutExwait">
                <div>已无更多数据...</div>
              </div>
            </div>
            <div v-else class="textCenter">暂无数据</div>
          </scroll-view>
        </div>
      </div>
      <div style="margin-top: .3rem;" v-else-if="(roleFlag && active===3) || (!roleFlag && active===2)">
        <div class="index4_row1">
          <van-row>
            <van-col span="8" offset="2">
              <img :src="imageUrl+userInfo.avatar"  style="margin-top: 20rpx; border-radius: 50%; width: 160rpx; height: 160rpx;"/></van-col>
            <van-col span="14"><div style="margin-top:20rpx; line-height:60rpx;"> {{userInfo.nickname}}</div>
              <div style="line-height:50rpx;"> {{userInfo.mobile}}</div>
              <div style="line-height:40rpx;"> {{userInfo.role_name}}</div></van-col>
          </van-row></div>
        <div>
          <div v-if="managerFlag" class="index4_row2" @click="index4Page(1)">账号管理</div><!--管理员才有账号管理权限-->
          <div class="index4_row2" @click="index4Page(2)">个人信息</div>
          <div class="index4_row2" @click="index4Page(3)">修改密码</div>
          <div class="index4_row2" @click="index4Page(4)">退出登录</div>
        </div>
      </div>
    </div>
    <van-tabbar :active="active" active-color="#44DAB7"  inactive-color="#BFBFBF" @change="onChange">
      <van-tabbar-item v-for="(tab,i) in tabArr" :key="i" :icon="tab.icon" >{{tab.name}}</van-tabbar-item>
    </van-tabbar>

    <van-popup :show="showAge" position="bottom" custom-style="height: 40%;" closeable @close="closeAge">
      <div style="padding: 60rpx 30rpx; text-align: center;">
        <div style="display: flex; line-height: 120rpx; margin-left: 50rpx;"><div>初始年龄：</div><div><van-stepper :value="infoParam.agestart" @change="changeStart" min="0" max="145" step="5" integer  /></div></div>
        <div style="display: flex ;line-height: 120rpx; margin-left: 50rpx;"><div>结束年龄：</div><div><van-stepper :value="infoParam.ageend" @change="changeEnd" step="5" :min="infoParam.agestart+5" max="150" integer  /></div></div>
        <div style="display: flex; margin-top: 30rpx;">
          <div style="width: 50%; margin-right: 10%;  height: 80rpx; border-radius: 20rpx; line-height: 80rpx; background: #E4E4E4; color: #999999;" @click="resetAge">重置查询</div>
          <div style="width:50%; height: 80rpx; border-radius: 20rpx; line-height: 80rpx; background: #44DAB7; color: white; " @click="selectAge">查询</div>
        </div>
      </div>
    </van-popup>
    <van-popup :show="showStatus" position="bottom" custom-style="height: 50%;"  @close="onClose">
      <van-picker
        show-toolbar
        title="状态选择"
        @cancel="showStatus=false"
        @confirm="toChangeStatus"
        :columns="statusCol" default-index="0" />
    </van-popup>
    <van-toast id="van-toast" />
    <van-dialog id="van-dialog" />
  </div>
</template>

<script>
import Toast from '../../../static/vant-weapp/toast/toast';
import Dialog from '../../../static/vant-weapp/dialog/dialog';
import * as echarts from 'echarts/dist/diy_echarts.min'
import mpvueEcharts from 'mpvue-echarts'
let pieChart = null;
/*饼图*/
export default {
  data () {
    return {
      echarts,
      pageSize:5,
      showAge:false,//是否显示年龄筛选
      statusCol:['全部','未审核','已审核'],
      exwaitStatus:'',
      showStatus:false,//是否显示状态筛选
      showMoreInfo:false,
      showMoreCarryOutInfo:false,
      showMoreExwait:false,
      showMoreCarryOutExwait:false,
      countNum:undefined,
      userNum:undefined,
      tabViewHeight:'',
      infoList:[],
      infoParam:{
        name:'',
        agestart:'',
        ageend:'',
        roleCode:undefined,
        userId:undefined,
        currentPage:1,
        pageSize:5
      },
      exwaitList:[],
      exwaitParam:{
        name:'',
        status:'', // ''表示全部，0表示未审核，-1表示已审核，1表示拒绝，2表示通过
        roleCode:undefined,
        userId:undefined,
        currentPage:1,
        pageSize:5
      },
      userInfo:{},
      roleFlag:false,
      roleAgentFlag:false,
      managerFlag:false,
      isShowAge:true,
      echartsActive:0,
      scrollInfoStatus:true,
      scrollExwaitStatus:true,
      code:'',
      addData:{
        phone:'',
      },
      imageUrl:this.utils.imageUrl,
      isLoading:false,
      radio1:'1',
      userRole:0,//用户角色
      active: 0,
      navTitle:'百家义诊',
      searchValue1:'',
      tabArr:[{name:'首页', icon:'wap-home',},{name:'义诊数据', icon:'column',},{name:'数据审核', icon:'label',},{name:'我的', icon:'manager',},]
    }
  },
  components: {
    mpvueEcharts
  },
  mounted() {
    if(!this.roleFlag){//判断是否有数据审核权限
      this.tabArr.splice(2,1);
    }
    this.$nextTick(()=>{
      this.getInitData();
      this.pieAgeOption();
    });
    let _this = this;
    wx.getSystemInfo({
      success (res) {
        _this.tabViewHeight=res.windowHeight-res.statusBarHeight-170;
      }
    });
  },
  methods: {
    resetAge(){
      this.infoParam.agestart = '';
      this.infoParam.ageend = '';
      this.infoParam.currentPage = 1;
      this.infoList = [];
      this.getInfoList();
      this.showAge = false;
    },
    changeStart(val){
      this.infoParam.agestart = val.mp.detail;
    },
    changeEnd(val){
      this.infoParam.ageend = val.mp.detail;
    },
    selectAge(){
      this.infoParam.currentPage = 1;
      this.infoList = [];
      this.getInfoList();
      this.showAge = false;
    },
    closeAge(){//关闭显示年龄筛选
      this.infoParam.agestart = '';
      this.infoParam.ageend = '';
      this.showAge = false;
    },
    toChangeStatus(val){//数据审核状态筛选
      this.showStatus = false;
      this.exwaitList=[];
      let exwaitStatus =  val.mp.detail.index;
      this.exwaitParam.status=exwaitStatus===1?0:exwaitStatus===2?-1:'';
      this.exwaitParam.currentPage = 1;
      this.getExwaitList();
    },
    scrollGoodsMoreInfo(){
      let totoPage=Math.ceil(Number(this.infoParam.total)/Number(this.infoParam.pageSize));  //总页数
      if(this.infoParam.currentPage>=totoPage){
        this.showMoreCarryOutInfo = true;
        return;
      }else if(this.scrollInfoStatus){
        this.showMoreInfo = true;
        this.showMoreCarryOutInfo = false;
        this.scrollInfoStatus=false;
        setTimeout(()=>{
          if(this.infoParam.currentPage<totoPage){
            this.infoParam.currentPage++;
            this.getInfoList();
            this.showMoreInfo=false;
          }
        },1000)
      }else {
        console.log("禁止加载")
      }
    },
    scrollGoodsMoreExwait(){
      let totoPage=Math.ceil(Number(this.exwaitParam.total)/Number(this.exwaitParam.pageSize));  //总页数
      if(this.exwaitParam.currentPage>=totoPage){
        this.showMoreCarryOutExwait = true;
        return;
      }else if(this.scrollExwaitStatus){
        this.showMoreExwait = true;
        this.showMoreCarryOutExwait = false;
        this.scrollExwaitStatus=false;
        setTimeout(()=>{
          if(this.exwaitParam.currentPage<totoPage){
            this.exwaitParam.currentPage++;
            this.getExwaitList();
            this.showMoreExwait=false;
          }
        },1000)
      }else{
        console.log("禁止加载")
      }
    },
    onChangeActive(e){
      let a=this.echartsActive==0?1:0;
      this.echartsActive=a;
      if(a==0){
        this.pieAgeOption();
      }else{
        this.pieSexOption();
      }
    },
    onChange1(e) {
      this.infoParam.name=e.mp.detail;
    },
    onSearch1() {
      this.infoList=[];
      this.infoParam.currentPage=1;
      this.infoParam.pageSize=5;
      this.getInfoList();
    },
    onClick1() {//显示年龄筛选
      this.showAge = true;
      this.infoParam.agestart = 0;
      this.infoParam.ageend = 150;
    },
    onChange2(e) {
      this.exwaitParam.name=e.mp.detail;
    },
    onSearch2() {
      this.exwaitList=[];
      this.exwaitParam.currentPage=1;
      this.exwaitParam.pageSize=5;
      this.getExwaitList();
    },
    onClick2() {
      this.showStatus = true;
    },
    btnClick(obj,val){
      let message = val===1?'确认通过':'确认拒绝'
      Dialog.confirm({
        title: '',
        message: message,
      }).then(() => {
        if(val===1){
          this.handleAdopt(obj);
        }else{
          this.handleRefuse(obj);
        }
        })
        .catch(() => {
          // on cancel
        });
    },
    handleAdopt(row){
      let vm = this;
      let data={
        id:row.id,
        pdcBakId:row.pdcBakId
      }
      vm.utils.GET('clin/mp/examine/adopt', data).then(l_res=>{
        if(l_res.data.status === 200){
          this.getExwaitList();
        }else{
          Toast.fail(l_res.data.message);
        }
      }).catch(
        l_err=>{console.log(l_err);}
      );
    },
    handleRefuse(row){
      let vm = this;
      let data={
        id:row.id,
        pdcBakId:row.pdcBakId
      }
      vm.utils.GET('clin/mp/examine/refuse', data).then(l_res=>{
        if(l_res.data.status === 200){
          this.getExwaitList();
        }else{
          Toast.fail(l_res.data.message);
        }
      }).catch(
        l_err=>{console.log(l_err);}
      );
    },
    getInitData(){
      let vm = this;
      //获取统计问卷及志愿者数
      vm.utils.GET('clin/mp/analy/getStatisticsData', null).then(l_res=>{
        wx.hideLoading()
        if(l_res.data.status === 200){
          this.userNum=l_res.data.data.userNum;
          this.countNum=l_res.data.data.countNum;
        }else{
          Toast.fail(l_res.data.message);
        }
      }).catch(
        l_err=>{console.log(l_err);}
      );
    },
    initPieChart (canvas, width, height) {
      pieChart = echarts.init(canvas, null, {
        width: width,
        height: height
      });
      canvas.setChart(pieChart);
      this.pieAgeOption();
      return pieChart
    },
    pieAgeOption(){
      let vm = this;
      let ageData=[];
      //获取用户年龄分布
      vm.utils.POST('clin/mp/analy/getAgeData', null).then(l_res=>{
        if(l_res.data.status === 200){
          let ages=l_res.data.data;
          ages.forEach((row)=>{
            ageData.push({name:row.title,value:row.num});
          })
          var option = {
            title: {
              text: '患者年龄占比',  //图形标题
              top:'8%',
              left: "center",
            },
            tooltip: {
              trigger: 'item',
              formatter: '{b} : {c} ({d}%)'
            },
            series: [
              {
                name: 'Access From',
                type: 'pie',
                radius: '45%' ,
                emphasis: {//在鼠标悬浮到图形元素上时，一般会出现高亮的样式
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                },
                data: ageData
              }
            ]

          };
          pieChart.setOption(option);
        }else{
          Toast.fail(l_res.data.message);
        }
      }).catch(
        l_err=>{console.log(l_err);}
      );
    },
    pieSexOption(){
      let vm = this;
      let sexData=[];
      //获取用户年龄分布
      vm.utils.POST('clin/mp/analy/getSexData', null).then(l_res=>{
        if(l_res.data.status === 200){
          let sexs=l_res.data.data;
          sexs.forEach((row)=>{
            sexData.push({name:row.title,value:row.num});
          })
          var option = {
            title: {
              text: '患者性别占比',  //图形标题
              top:'8%',
              left: "center",
            },
            tooltip: {
              trigger: 'item',
              formatter: '{b} : {c} ({d}%)'
            },
            series: [
              {
                name: 'Access From',
                type: 'pie',
                radius: '60%' ,
                emphasis: {//在鼠标悬浮到图形元素上时，一般会出现高亮的样式
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                },
                data: sexData
              }
            ]
          };
          pieChart.setOption(option);
        }else{
          Toast.fail(l_res.data.message);
        }
      }).catch(
        l_err=>{console.log(l_err);}
      );
    },
    getInfoList(){
      let vm = this;
      vm.utils.GET('clin/mp/inv/queryPdcInfos', this.infoParam).then(l_res=>{
        if(l_res.data.status === 200){
          let res=l_res.data.data;
          let current=res.pagination.current;
          this.infoParam.total =res.pagination.total;   //总条数
          let list=res.list;
          if(current>1){
            this.infoList=this.infoList.concat(list);
          }else{
            this.infoList=list;
          }
        }else{
          Toast.fail(l_res.data.message);
        }
        this.scrollInfoStatus=true;
      }).catch(
        l_err=>{console.log(l_err);}
      );
    },
    findInvDetail(data){
      wx.navigateTo({url: '../infoDetail/main?id='+data.id});
    },
    findInvEdit(data){
      wx.navigateTo({url: '../infoEdit/main?id='+data.id});
    },
    getExwaitList(){
      let vm = this;
      vm.utils.GET('clin/mp/examine/queryExwaitList', this.exwaitParam).then(l_res=>{
        if(l_res.data.status === 200){
          let res=l_res.data.data;
          let current=res.pagination.current;
          this.exwaitParam.total =res.pagination.total;   //总条数
          let list=res.list;
          if(current>1){
            this.exwaitList=this.exwaitList.concat(list);
          }else{
            this.exwaitList=list;
          }
        }else{
          Toast.fail(l_res.data.message);
        }
        this.scrollExwaitStatus=true;
      }).catch(
        l_err=>{console.log(l_err);}
      );
    },
    findExwaitDetail(data){
      if(data.status==0) {
        wx.navigateTo({url: '../exwaitDetail/main?id=' + data.id});
      }
    },
    index4Page(type){
      if(type===1){
        //账号管理
        wx.navigateTo({url: '../account/main'});
      }else if(type===2){
        //个人信息
        wx.navigateTo({url: '../myInfo/main'});
      }else if(type===3){
        //修改密码
        wx.navigateTo({url: '../updatePwd/main'});
      }else if(type===4){
        //退出登录
        wx.removeStorage({
          key: 'userInfo',
          success (res) { wx.navigateTo({url:'../login/main'})}
        })
      }
    },
    toPageEdit(){
      wx.navigateTo({url: '../infoAdd/main'});
    },
    onChange(event){
      this.active = event.mp.detail;
      if(this.active===1){
        this.navTitle = '义诊数据';
        this.infoParam.name='';
        this.infoParam.agestart='';
        this.infoParam.ageend='';
        this.infoParam.currentPage=1;
        this.infoParam.pageSize=5;
        this.infoList = [];
        this.getInfoList();
      }else if(this.active===2){
        this.navTitle = '数据审核';
        this.exwaitParam.name = '';
        this.exwaitParam.status = '';
        this.exwaitParam.currentPage=1;
        this.exwaitParam.pageSize=5;
        this.exwaitList=[];
        this.getExwaitList();
      }else if(this.active===3){
        this.navTitle = '我的';
      } else {//首页
        this.navTitle = '百家义诊';
        this.$nextTick(()=>{
          wx.stopPullDownRefresh();
          wx.showLoading();
          this.getInitData();
          this.pieAgeOption();
        });
      }
    },
  },
  created () {
  },
  onShow(){
    this.utils.isLoginOut();
    let info=wx.getStorageSync('userInfo');
    if(info){
      this.roleFlag= (info.role_code==='ROLE_MANAGER'||info.role_code==='ROLE_EAC_ADMIN');
      this.roleAgentFlag=(info.role_code==='ROLE_MANAGER'||info.role_code==='ROLE_AGENT'||info.role_code==='ROLE_EAC_ADMIN');
      this.managerFlag=(info.role_code==='ROLE_MANAGER'||info.role_code==='ROLE_EAC_ADMIN');
      this.userInfo=info
      this.infoParam.roleCode= info.role_code;
      this.infoParam.userId=info.user_id;
      this.exwaitParam.roleCode= info.role_code;
      this.exwaitParam.userId=info.user_id;
      this.infoParam.name='';
      this.infoParam.agestart='';
      this.infoParam.ageend='';
      this.infoParam.currentPage=1;
      this.infoParam.pageSize=5;
      this.infoList = [];
      this.getInfoList();
    }
  },
  onPullDownRefresh(){
    this.$nextTick(()=>{
      wx.stopPullDownRefresh();
      wx.showLoading()
      this.getInitData();
      this.pieAgeOption();
    });
  },
  onLoad(options){
    Object.assign(this.$data, this.$options.data());  //初始化data
  }
}
</script>
<style scoped>
  #page{overflow-y: hidden; height: 100vh;}
  .page{background: #FAFAFA; }
  .content{
    padding-top:10vh;
    padding-bottom:10vh;
    height: 80vh;
    overflow-y: hidden;
    background: #FAFAFA;
  }
  .role0-row1-box{padding: 20rpx 20rpx 20rpx 40rpx;  margin-left:2%; width:88%; height:200rpx; border-radius: 15rpx; background: #44DAB7; color: white;}
  .role0-row1-1{float: right; margin-top: 25rpx;}
  .role0-row1-2{font-size: 40rpx; font-weight: 600; line-height: 130rpx; letter-spacing: 12rpx;}
  .fontSize30{font-size: 30rpx; }
  .role1-row-box{background: #344E86; width: 100%; height: 90vh; margin: 0; overflow: hidden;}
  .role1-row1-1{margin-left: 140rpx; }
  .role1-row1-img{padding-top: 150rpx; margin-left: 80rpx; height: 300rpx; width: 300rpx; }
  .role1-row1-2{padding: 20rpx 20rpx 20rpx 40rpx; margin-top: 100rpx;  margin-left:2%; width:88%; height:200rpx; border-radius: 15rpx; background: #44DAB7; color: white;}
  .index4_row1{padding: 20rpx 20rpx 20rpx 40rpx;  margin-left:2%; width:88%; height:200rpx; border-radius: 15rpx; background: #44DAB7;}
  .index4_row2{padding-left: 30rpx; border-bottom: 1rpx solid #E6E6E6; line-height: 100rpx; }
  .rowBtn{height: 50rpx; width: 140rpx; font-size: 26rpx; text-align: center; color: #44DAB7; border: 1rpx solid #44DAB7; border-radius: 30rpx; line-height: 50rpx;}
  .index2_listBox{margin-top: 30rpx; min-height:250rpx; padding: 20rpx; width: 90%; margin-left:2%; border-radius: 20rpx; background: white;}
  .textCenter{text-align: center; padding-top: 40rpx; color: #999999; font-size: 28rpx;}
</style>
