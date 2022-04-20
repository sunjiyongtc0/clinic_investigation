<template>
  <div @click="" id="page">
    <div style="height: 88rpx; background: #464C5B;  color: white; align-items: center; align-content: center; line-height: 88rpx;">
      <van-row>
        <van-col span="17" offset="1"><div>此页面所有内容必填</div></van-col>
        <van-col span="5"><div @click="clickBack">取消录入</div></van-col>
      </van-row>
    </div>
    <div class="content">
      <van-cell-group>
        <van-field :value="user.nickname" label="账号名称" maxlength="11"
                   :placeholder="nickname" input-align="right"  :error="flag.nickname"
                   :border="false" @change="(val)=>vanFieldChange(val,'nickname')" @blur="(e)=>vanFieldBlur(e,'nickname')"
        ></van-field>
      </van-cell-group>
      <van-cell-group>
        <van-field :value="user.username" label="登录账号" maxlength="11" :error="flag.username"
                   :placeholder="username" input-align="right" style="border: none!important;"
                   :border="false" @blur="(val)=>vanFieldBlur(val,'username')" @change="(val)=>vanFieldChange(val,'username')"
        ></van-field>
      </van-cell-group>
      <van-cell-group>
        <van-field :value="user.password" label="账号密码" maxlength="18" password
                   :placeholder="password" input-align="right" :error="flag.password"
                   :border="false" @blur="(val)=>vanFieldBlur(val,'password')" @change="(val)=>vanFieldChange(val,'password')"
        ></van-field>
      </van-cell-group>
      <van-cell-group>
        <van-field :value="pwdC" label="确认密码" maxlength="18" password :error="flag.pwdC"
                   :placeholder="pwdCP" input-align="right"
                   :border="false" @blur="(val)=>vanFieldBlur(val,'pwdC')" @change="(val)=>vanFieldChange(val,'pwdC')"
        ></van-field>
      </van-cell-group>
      <van-cell-group>
        <van-field :value="user.mobile" label="手机号码" maxlength="11" type="number"
                   placeholder="请输入手机号码"  input-align="right" :error="flag.mobile"
                   :border="false" @blur="(val)=>vanFieldBlur(val,'mobile')"  @change="(val)=>vanFieldChange(val,'mobile')" />
      </van-cell-group>
      <van-cell-group>
        <div style="display: flex;">
          <div style="font-size: 30rpx; width: 40%; min-width: 150rpx; color: #323233; padding: 30rpx;">权限设置</div>
          <div style="padding: 30rpx; width: 60%;"><van-radio-group :value="user.roleId" @change="onChange($event)">
            <div v-for="(item,index) in chooseArr" :key="index" style=" height: 70rpx; line-height: 70rpx; margin-right: 20rpx; float: right; clear: both;">
              <van-radio :name="item.value"  checked-color="#44DAB7">
                <span style="color:#323233;">{{item.label}}</span>
              </van-radio>
            </div>
          </van-radio-group></div>
        </div>
        <div style="padding: 20rpx; font-size: 26rpx; color: #999999; text-align: right;">{{roleRule}}</div>
      </van-cell-group>

    </div>
    <div style="position: fixed; z-index: 9999;  text-align: center; color: white; bottom: 0rpx; width: 100%; height: 90rpx; line-height: 90rpx; background: #44DAB7;" @click="submit">提交</div>
    <van-toast id="van-toast" />
  </div>
</template>

<script>
  import Toast from '../../../static/vant-weapp/toast/toast';
  export default {
    data () {
      return {
        user:{nickname:'',username:'',mobile:'',password:'',roleId:''},
        flag:{nickname:false,username:false,mobile:false,password:false,roleId:false,pwdC:false},
        pwdC:'',//确认密码
        nickname:'请输入账号名称',
        username:'请输入登录账号',
        mobile:'请输入手机号',
        password:'请输入登录密码',
        pwdCP:'请再次输入登录密码',
        roleRule:'',
        chooseArr:[],
      }
    },
    components: {},
    mounted() {},
    methods: {
      getRoleNameCheckbox(){
        let vm = this;
        vm.utils.GET('clin/mp/sysuserdetail/queryRoleNameCheckbox', {}).then(l_res=>{
          if(l_res.data.status === 200) {
            this.chooseArr=l_res.data.data;
            this.user.roleId = this.chooseArr[0].value;
            if(this.chooseArr[0].label==='志愿者'){
              this.roleRule = '志愿者权限，可以进行数据的录入、数据修改申请等';
            }else if(this.chooseArr[0].label==='义诊负责人'){
              this.roleRule = '义诊负责人权限，可以进行数据的录入、数据修改申请等';
            }else if(this.chooseArr[0].label==='管理员'){
              this.roleRule = '管理员权限，可以进行数据的录入、数据修改申请等';
            }
          }else{
            Toast.fail(l_res.data.message);
          }
        })
      },
      onChange(event){
        let roleId=event.mp.detail;
        let msg="";
        this.chooseArr.forEach((row)=>{
          if(row.value===roleId){
            msg=row.label;
          }
        });
        if(msg==='志愿者'){
          this.roleRule = '志愿者权限，可以进行数据的录入、数据修改申请等';
        }else if(msg==='义诊负责人'){
          this.roleRule = '义诊负责人权限，可以进行数据的录入、数据修改申请等';
        }else if(msg==='管理员'){
          this.roleRule = '管理员权限，可以进行数据的录入、数据修改申请等';
        }
        this.user.roleId = event.mp.detail;
      },
      clickBack(){
        wx.navigateBack();
      },
      submit(){
        if(this.user.nickname===''){
          this.nickname = '请输入账号名称';
          this.flag.nickname=true;
          return;
        }
        if(this.user.nickname.length<2){
          this.user.nickname = '';
          this.nickname = '账号名称长度需在2~11之间';
          this.flag.nickname=true;
          return;
        }
        if(this.user.username===''){
          this.username = '请输入登录账号';
          this.flag.username=true;
          return;
        }
        if(this.user.username.length<2){
          this.user.username = '';
          this.username = '登录账号长度需在2~11之间';
          this.flag.username=true;
          return;
        }
        if(this.user.password===''){
          this.password = '请输入登录密码';
          this.flag.password=true;
          return;
        }
        if(this.user.password.length<6){
          this.user.password = '';
          this.password = '密码长度需在6~18之间';
          this.flag.password=true;
          return;
        }
        if(this.pwdC===''){
          this.pwdCP='请再次输入登录密码';
          this.flag.pwdC=true;
          return;
        }
        if(this.pwdC!==this.user.password){
          this.pwdC = '';
          this.pwdCP='两次输入的密码不一致';
          this.flag.pwdC=true;
          return;
        }
        if(this.user.mobile===''){
          this.flag.mobile=true;
          return;
        }
        let vm=this;
        let param={
          nickname:'',
          username:'',
          password:'',
          mobile:'',
          status:1,
          roleId:[]
        };
        param.nickname=this.user.nickname;
        param.username=this.user.username;
        param.password=this.user.password;
        param.mobile=this.user.mobile;
        param.roleId[0]=this.user.roleId;
        vm.utils.POSTJSON("clin/mp/sysuserdetail/insertSysUserAndRole",param)
          .then(l_res=>{
          if(l_res.data.status === 200) {
            Toast.success('数据保存成功');
            this.clickBack();
            }else{
              Toast.fail(l_res.data.message);
            }
          }).catch(
          l_err=>{console.log(l_err);}
        );
      },
      vanFieldChange(val,name){
        this.flag[name] =false;
      },
      vanFieldBlur(value,name){
        if(name==='pwdC'){
          this.pwdC = value.mp.detail.value;
        }else{
          this.user[name] = value.mp.detail.value;
        }
      },
    },
    created () {
    },
    onShow(){
      this.utils.isLoginOut();
    },
    onLoad(options){
      Object.assign(this.$data, this.$options.data());  //初始化data
      this.getRoleNameCheckbox();
    }
  }
</script>

<style scoped>
</style>
