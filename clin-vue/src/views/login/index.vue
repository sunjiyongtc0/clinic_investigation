<template>
  <div class="login-container">
    <div class="login-form">
      <div class="title-container">
        <h3 class="title">
          <div style="position: absolute; left: 42px; top: -8px;"><img src="../../image/logo-icon.png" /></div>百家义诊管理平台登录
       </h3>
      </div>
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" autocomplete="on" label-position="left">
        <el-form-item prop="username">
              <span class="svg-container">
                <svg-icon icon-class="user" />
              </span>
          <el-input
            ref="username"
            v-model="loginForm.username"
            :placeholder="$t('login.username')"
            name="username"
            type="text"
            tabindex="2"
            autocomplete="on"
          />
        </el-form-item>
        <div style="width: 100%; height: 20px;"></div>
        <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
          <el-form-item prop="password">
                <span class="svg-container">
                  <svg-icon icon-class="password" />
                </span>
            <el-input
              :key="passwordType"
              ref="password"
              v-model="loginForm.password"
              :type="passwordType"
              :placeholder="$t('login.password')"
              name="password"
              tabindex="3"
              autocomplete="on"
              @keyup.native="checkCapslock"
              @blur="capsTooltip = false"
            />
            <span class="show-pwd" @click="showPwd">
                  <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
                </span>
          </el-form-item>
        </el-tooltip>
        <div style="width: 100%; height: 20px;"></div>
        <el-row>
          <el-col :xs="16" :sm="16" :md="16" :lg="16" :xl="16">
            <el-form-item prop="charCaptcha">
              <el-input
                ref="charCaptcha"
                v-model="loginForm.charCaptcha"
                :placeholder="$t('common.verificationCode')"
                name="charCaptcha"
                type="text"
                maxlength="4"
                tabindex="4"
                autocomplete="on"
                @keyup.enter.native="handleLogin"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="7" :sm="7" :md="7" :lg="7" :xl="7" style="margin-left: 10px;">
            <img :src="imgUrl" :alt="$t('common.verificationCode')" width="100%" height="48px" @click="handleImg">
          </el-col>
        </el-row>

      </el-form>

      <div style="width: 100%; height: 20px;"></div>
      <el-button :loading="loading" type="primary" size="small" style="width:100%;margin-bottom:30px; height: 42px; font-size: 20px;" @click.native.prevent="handleLogin">
        {{ $t('login.logIn') }}
      </el-button>
      <div style="color: #D1C9CC; text-align: center; ">忘记密码请联系主管理员</div>
    </div>
    <div class="globalFooter">
      <div class="copyright">Copyright &copy;2022 沈阳昱鑫科技有限公司出品</div>
    </div>
  </div>
</template>

<script>
import { compareCaptcha } from '@/api/user'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error(this.$t('sysuser.username-required')))
      } else if (value.length > 0 && value.length < 2) {
        callback(new Error('登录账户长度最小为2'))
      } else {
        callback()
      }
    }
    const validateCharCaptcha = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error(this.$t('common.verificationCode-required')))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error(this.$t('sysuser.password-required')))
      } else if (value.length > 0 && value.length < 6) {
        callback(new Error('密码长度最小为6'))
      } else {
        callback()
      }
    }

    return {
      loginForm: {
        username: this.username || '',
        password: this.password || '',
        charCaptcha: this.charCaptcha || ''
      },
      loginRules: {
        username: [{ required: true, validator: validateUsername, trigger: 'blur' }],
        password: [{ required: true, validator: validatePassword, trigger: 'blur' }],
        charCaptcha: [{ required: true, validator: validateCharCaptcha, trigger: 'blur' }]
      },
      passwordType: 'password',
      capsTooltip: false,
      loading: false,
      redirect: undefined,
      otherQuery: {},
      activeName: 'first',
      codeStatus: false,
      totalTime: 60,
      codeContent: this.$t('login.getVerificationCode'),
      imgUrl: '/clin/captcha/generateImageCaptcha?' + Math.random()
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  mounted() {
    if (this.loginForm.username === '') {
      this.$refs.username.focus()
    } else if (this.loginForm.password === '') {
      this.$refs.password.focus()
    }
  },
  destroyed() {
  },
  methods: {
    checkCapslock(e) {
      const { key } = e
      this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
    },
    handleImg() {
      this.imgUrl = '/clin/captcha/generateImageCaptcha?' + Math.random()
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          compareCaptcha(this.loginForm.charCaptcha).then(response => {
            if (response.message === 'success') {
              this.$store.dispatch('user/login', this.loginForm)
                .then(() => {
                  this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
                })
                .catch(() => {})
            } else {
              this.handleWarning(response)
              this.handleImg()
            }
            this.loading = false
          })
        } else {
          console.log('error credentials submit!!')
          return false
        }
      })
        // this.$refs.loginForm.validate(valid => {
        //   if (valid) {
        //     this.loading = true
        //     this.$store.dispatch('user/login', this.loginForm)
        //       .then(() => {
        //         this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
        //       })
        //       .catch(() => {})
        //       this.loading = false
        //   } else {
        //     console.log('error credentials submit!!')
        //     return false
        //   }
        // })
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    },
    handleWarning(response) {
      this.$message({
        message: response.message || response,
        type: 'warning',
        duration: 2000
      })
    },
    handleClick(tab, event) {
    }
  }
}
</script>

<style lang="scss">

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: black;
  }
}
.el-form-item--small .el-form-item__error {
  padding-top: 8px;
}
/* reset element-ui css */
.login-container {
  background-image: url(../../image/login-bg.jpg);
  min-width: 1000px;/*为了适配小尺寸有滚动条*/
  min-height: 800px;
  background-size: 100% 100%;
  overflow: auto;

  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: black;
      height: 47px;
      caret-color: black;/*光标颜色*/

      &:-webkit-autofill {
        /*box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;*/
        -webkit-box-shadow: 0 0 0px 1000px white inset !important;
        box-shadow: 0 0 0px 1000px white inset !important;
        -webkit-text-fill-color: black !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    /*background: rgba(0, 0, 0, 0.1);*/
    background:white;
    border-radius: 5px;
    color: #454545;
  }
}

.el-tabs__item {
  color: white;
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 140px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: #E6E6E6;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 24px;
      color: #8A8B8D;

      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }

    .desc {
      margin-top: 0px;
      margin-bottom: 20px;
      color: white;
      font-size: 14px;
    }

    .set-language {
      color: #fff;
      position: absolute;
      top: 3px;
      font-size: 18px;
      right: 0px;
      cursor: pointer;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}

.globalFooter {
  margin: 48px 0 24px 0;
  padding: 0 16px;
  text-align: center;

  .links {
    margin-bottom: 8px;

    a {
      color: fade(#000, 45%);
      transition: all 0.3s;

      &:not(:last-child) {
        margin-right: 40px;
      }

      &:hover {
        color: fade(#000, 65%);
      }
    }
  }

  .copyright {
    color: rgba(0,0,0,0.45);
    font-size: 14px;
  }
}

.forgotregister {
  text-align: left;
  font-size: 14px;
  color: white;

  .register {
    float: right;
  }
}
/*.login-container .el-input input:-webkit-autofill {
   -webkit-box-shadow: 0 0 0px 1000px white inset !important;
   box-shadow: 0 0 0px 1000px white inset !important;
   -webkit-text-fill-color: #d3dce6 !important;
}*/
</style>
