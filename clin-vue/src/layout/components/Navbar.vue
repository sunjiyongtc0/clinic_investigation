<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />

    <div class="right-menu">
      <!--<template v-if="device!=='mobile'">-->
        <!--<error-log class="errLog-container right-menu-item hover-effect" />-->
        <!--<screenfull id="screenfull" class="right-menu-item hover-effect" />-->
        <!--<el-tooltip :content="$t('navbar.size')" effect="dark" placement="bottom">-->
          <!--<size-select id="size-select" class="right-menu-item hover-effect" />-->
        <!--</el-tooltip>-->
      <!--</template>-->

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="imgSrc" alt="Avatar" class="user-avatar" :onerror="errorImage">
          <!--<img src="https://cdn.pixabay.com/photo/2017/12/26/21/19/tech-3041437__340.jpg" alt="Avatar" class="user-avatar" :onerror="errorImage">-->
          <span>{{nickname}}({{ roleNames}})</span>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/profile/index">
            <el-dropdown-item>
              {{ $t('navbar.profile') }}
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="changePassword">
            <span style="display:block;">{{ $t('navbar.change-password') }}</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">{{ $t('navbar.logOut') }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <el-dialog v-el-drag-dialog :title="$t('navbar.change-password')" :visible.sync="passwordDialogFormVisible" :modal-append-to-body="false">
      <el-form ref="dataForm" :rules="rules" :model="temp" :label-position="labelPosition" label-width="120px" style="width: 100%">
        <el-form-item :label="$t('navbar.oldpassword')" prop="oldPassword">
          <el-input v-model="temp.oldPassword" type="password" :placeholder="$t('common.enter')"  maxlength="18" clearable />
        </el-form-item>
        <el-form-item :label="$t('navbar.newpassword')" prop="newPassword">
          <el-input v-model="temp.newPassword" type="password" :placeholder="$t('common.enter')"  maxlength="18" clearable />
        </el-form-item>
        <el-form-item :label="$t('navbar.confirm-newpassword')" prop="confirmNewPassword">
          <el-input v-model="temp.confirmNewPassword" type="password" :placeholder="$t('common.enter')" maxlength="31" clearable />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="passwordDialogFormVisible = false">
          {{ $t('common.cancel') }}
        </el-button>
        <el-button type="primary" @click="handleChangePassword()">
          {{ $t('common.confirm') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import LangSelect from '@/components/LangSelect'
import {  updatePassword } from '@/api/system'
import { queryCurrentAuthority } from '@/api/user'
import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui

export default {
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    SizeSelect,
    LangSelect
  },
  directives: { elDragDialog },
  data() {
    return {
      switchStatus: false,
      menuValue: '',
      postCodeValue: '',
      passwordDialogFormVisible: false,
      labelPosition: 'right',
      temp: { },
      rules: {
        oldPassword: [{ required: true, validator: this.checkOldPassword, trigger: ['blur', 'change'] }],
        newPassword: [{ required: true, validator: this.checkNewPassword, trigger: ['blur', 'change'] }],
        confirmNewPassword: [{ required: true, validator: this.checkConfirmNewPassword, trigger: ['blur', 'change'] }]
      },
      imgSrc:'',
      errorImage: 'this.src="../data/avatar.png"' // 默认头像
      // errorImage:'./../image/data1.png'
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'imgPath',
      'roleNames',
      'nickname',
      'device'
    ])
  },
  watch: {
    switchStatus(value) {
      if (value) {
        document.body.addEventListener('click', this.closeSwitch)
      } else {
        document.body.removeEventListener('click', this.closeSwitch)
      }
    }
  },
  created() {
    if (window.innerWidth < 700) {
      this.labelPosition = 'top'
    }
    // this.getList()
    this.imgSrc=this.imgPath+this.avatar;
  },
  methods: {
    clickSwitch() {
      this.switchStatus = !this.switchStatus
    },
    closeSwitch() {
      this.switchStatus = false
    },
    changePassword() {
      this.temp = { }
      this.passwordDialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleChangePassword() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.temp.newPassword !== this.temp.confirmNewPassword) {
            this.handleWarning(this.$t('navbar.password-notsame'))
            return
          }else if (this.temp.newPassword.length<6 ||this.temp.newPassword.length>18) {
            this.handleWarning('密码长度最小为6最大为18！')
            return
          }
          updatePassword(this.temp.oldPassword, this.temp.newPassword, this.$store.getters.id).then(response => {
            if (response.message === 'success') {
              this.passwordDialogFormVisible = false
              this.$message({
                message: this.$t('navbar.change-password') + this.$t('common.success'),
                type: 'success',
                duration: 2000
              })
            } else {
              this.handleWarning(response)
            }
          })
        }
      })
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    handleWarning(response) {
      this.$message({
        message: response.message || response,
        type: 'warning',
        duration: 2000
      })
    },
    checkOldPassword(rule, value, callback) {
      if (!value) {
        return callback(new Error(this.$t('navbar.oldpassword-required')))
      } else {
        callback()
      }
    },
    checkNewPassword(rule, value, callback) {
      if (!value) {
        return callback(new Error(this.$t('navbar.newpassword-required')))
      } else {
        callback()
      }
    },
    checkConfirmNewPassword(rule, value, callback) {
      if (!value) {
        return callback(new Error(this.$t('navbar.confirm-newpassword-required')))
      } else {
        callback()
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}

.header-switch {
  font-size: 0 !important;
  .switch-icon {
    cursor: pointer;
    font-size: 18px;
    vertical-align: middle;
    margin-bottom: 0px;
  }
  .header-switch-select {
    font-size: 18px;
    transition: width 0.2s;
    width: 0;
    overflow: hidden;
    background: transparent;
    border-radius: 0;
    display: inline-block;
    vertical-align: middle;
  }
  &.switch {
    .header-switch-select {
      width: 150px;
      margin-left: 10px;
    }
  }
}
</style>
