<template>
  <div class="ulogin">
    <div class="containerMainPage ulogin-main">
      <div class="text-center"  style="width: 100%; align-items: center;">
        <img :src="imageUrl+'logo1.svg'" style="height: 300rpx; width: 300rpx;" />
      </div>
      <div class="ulogin-main-btn">
        <van-cell-group>
          <van-field
            :value="username"
            label="用户名:"
            maxlength="11"
            placeholder="请输入用户名"
            @blur="blurUsername" />
          <van-field
            :value="password"
            label="密码:"
            maxlength="18"
            placeholder="请输入密码"
            password
            @blur="blurPassword"
          />
        </van-cell-group>

        <div class="ulogin-main-btn-con" style="text-align: right; margin-bottom: 150rpx;">忘记密码请联系管理员</div>
        <van-button  color="#1ABC9C" block round @click="passwordLogin">登录</van-button>
      </div>
      <div class="ulogin-main-btn-con">仅供内部员工登录</div>

    </div>
    <van-toast id="van-toast" />
  </div>
</template>
<script>
  import Toast from '../../../static/vant-weapp/toast/toast';
  export default {
    data(){
      return{
        username:'',
        password:'',
        options: {
          groupId:'',
        },
        userInfo:{},
        proUserInfo:{},
        disableTime:'',
        navBarPadddingTop:'',
        wx:{
          sessionKey:'',
        },
        imageUrl:this.utils.imageUrl,
        startUpShow:true,
      }
    },
    computed:{
    },
    methods:{
      blurUsername(e){
        this.username = e.mp.detail.value;
      },
      blurPassword(e){
        this.password = e.mp.detail.value;
      },
      /*返回上一层及*/
      Back(){
        wx.navigateBack({//返回
          delta: 1
        })
      },
      passwordLogin(){
        let vm = this;
        wx.login({
          success (res) {
            if (res.code) {
              if(!vm.username){
                Toast.fail('请输入用户名');
                return false
              }
              if(!vm.password){
                Toast.fail('请输入密码');
                return false
              }
              wx.showLoading({
                title: "加载中..."
              });
              let param = {
                username:vm.username,
                password:vm.password,
              }
              vm.utils.PostLogin('clin/mp/users/loginin', param).then(l_res=>{
                wx.hideLoading();
                if(l_res.data.status === 200){
                  wx.setStorage({
                    key:"userInfo",
                    data:l_res.data.data
                  });
                  wx.navigateTo({url: '../index/main'});
                  // if(wx.getStorageSync('userInfo') && (JSON.stringify(wx.getStorageSync('userInfo')) != "{}")){
                  //
                  // }
                }else{
                  Toast.fail(l_res.data.message);
                }
              }).catch(l_err=>{console.log(l_err);});
            } else {
              console.log('登录失败！' + res.message)
            }
          }
        })
      },
    },
    mounted(){
      this.utils.setMyNavBarMetaPaddint(this,'navBarPadddingTop');
      this.navBarPadddingTop = this.navBarPadddingTop.substr(0,this.navBarPadddingTop.length -2)+'rpx';
    },
    onShow() {
      // wx.navigateTo({url: '../index/main'});
    },
    onLoad(options){
      if(wx.getStorageSync('userInfo') && (JSON.stringify(wx.getStorageSync('userInfo')) != "{}")){
        wx.navigateTo({url: '../index/main'});
      }
    }
  }
</script>
<style>
  page{
    background-color: #FFF;
  }
  .ulogin{
    font-size: 16px;
    background-color: #FFF;
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
  .ulogin-main{
    margin: 30% 40rpx 10% 40rpx;

  }
  .ulogin-main-btn{
    margin-top: 50rpx;
  }
  .ulogin-main-btn-con{
    text-align: center;
    font-size: 14px;
    color: #999; margin-top: 60rpx;
  }
  .div1{
    width: 100%;
    display: flex;
    padding: 24rpx 40rpx;
    justify-content: flex-end
  }
  .text-center{text-align: center;}
</style>

