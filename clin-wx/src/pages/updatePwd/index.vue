<template>
  <div @click="" id="page">
    <div style="height: 88rpx; background: #464C5B; font-size: 28rpx; color: white; align-items: center; align-content: center; line-height: 88rpx;">
      <van-row>
        <van-col span="17" offset="1"><div>密码由大小写字母、数字、字符随机组成</div></van-col>
        <van-col span="5"><div @click="clickBack">取消修改</div></van-col>
      </van-row>
    </div>
    <div >
      <div style="line-height: 100rpx; ">
        <van-cell-group>
          <van-field type="password"  :value="param.password" label="原密码" maxlength="50"  placeholder="请输入原密码" input-align="right" :error=flag.password
                     @blur="(e)=>vanFieldBlur(e,'password')" @change="(val)=>vanFieldChange(val,'password')"
          ></van-field>
        </van-cell-group>
      </div>
      <div style="line-height: 100rpx;">
        <van-cell-group>
          <van-field type="password" :value="param.newPassword" label="修改密码" maxlength="50" :placeholder="newPwd" input-align="right" :error="flag.newPassword"
                     @blur="(e)=>vanFieldBlur(e,'newPassword')" @change="(val)=>vanFieldChange(val,'newPassword')"
          ></van-field>
        </van-cell-group>
      </div>
      <div style="line-height: 100rpx;">
        <van-cell-group>
          <van-field type="password" :value="param.newPassword1" label="再次确认" maxlength="50" :placeholder="newPwd1" input-align="right" :error="flag.newPassword1"
                     @blur="(e)=>vanFieldBlur(e,'newPassword1')" @change="(val)=>vanFieldChange(val,'newPassword1')"
          ></van-field>
        </van-cell-group>
      </div>
    </div>
    <div style="position: fixed; z-index: 9999;  text-align: center; color: white; bottom: 0rpx; width: 100%; height: 90rpx; line-height: 90rpx; background: #44DAB7;" @click="submit">确认修改</div>
    <van-toast id="van-toast" />
  </div>
</template>
<script>
  import Toast from '../../../static/vant-weapp/toast/toast';
  export default {
    data () {
      return {
        param:{password:'',newPassword:'',newPassword1:'',},
        flag:{password:false,newPassword:false,newPassword1:false,},
        newPwd:'请输入需要修改的密码',
        newPwd1:'请再次确认密码',
      }
    },

    components: {
    },
    mounted() {},
    methods: {
      clickBack(){
        wx.navigateBack();
      },
      submit(){
        let vm = this;
        if(vm.param.password===''){
          this.flag.password= true;
          return false
         /* wx.showToast({
            title:'请输入密码',
            icon: 'none',
            duration: 1000
          });*/
        }
        if(vm.param.newPassword===''){
          this.newPwd = '请输入需要修改的密码';
          this.flag.newPassword= true;
          return false
        }
        if(vm.param.newPassword.length<6 || vm.param.newPassword.length>18){
          this.param.newPassword = '';
          this.newPwd = '密码长度需在6~18之间';
          this.flag.newPassword= true;
          return false
        }
        if(vm.param.newPassword1===''){
          this.newPwd1 = '请输入确认密码';
          this.flag.newPassword1= true;
          return false
        }
        if(vm.param.newPassword!==vm.param.newPassword1){
          this.param.newPassword1= '';
          this.newPwd1 = '两次输入的密码不一致';
          this.flag.newPassword1= true;
          return false
        }
        let param = {
          id:wx.getStorageSync('userInfo').user_id,
          password:vm.param.password,
          newPassword:vm.param.newPassword,
        }
        vm.utils.POST('clin/mp/sysuserdetail/updatePassword', param).then(l_res=>{
          if(l_res.data.status === 200){
            Toast('密码修改成功，请重新登录');
            wx.removeStorage({
              key: 'userInfo',
              success (res) { wx.navigateTo({url:'../login/main'})}
            })
          }else{
            Toast.fail(l_res.data.message);
          }
        }).catch(l_err=>{console.log(l_err);});
      },
      vanFieldBlur(value,name){
        this.param[name] = value.mp.detail.value;
      },
      vanFieldChange(value,name){
        this.flag[name] = false;
      },
    },
    created () {},
    onShow(){
      this.utils.isLoginOut();
    },
    onLoad(options){
      Object.assign(this.$data, this.$options.data());  //初始化data
    }
  }
</script>

<style scoped>
</style>
