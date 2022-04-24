<!-- eslint-disable -->
<template>
  <el-form ref="dataForm" :rules="rules" :model="user">
    <el-form-item label="手机号码" prop="mobile">
      <el-input v-model.trim="user.mobile" maxlength="20" clearable disabled />
    </el-form-item>
    <el-form-item label="用户名" prop="name">
      <el-input v-model.trim="user.name" maxlength="20" clearable disabled />
    </el-form-item>
    <el-form-item :label="$t('sysuser.nickname')" prop="nickname">
      <el-input v-model.trim="user.nickname" maxlength="64" clearable />
    </el-form-item>
    <el-form-item label="地址街道" prop="address">
      <el-input v-model.trim="user.address" maxlength="40" clearable />
    </el-form-item>
    <el-form-item :label="$t('sysuser.profile')" prop="profile">
      <el-input v-model.trim="user.profile" type="textarea" maxlength="256" clearable />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">{{ $t('sysuser.updateaccount') }}</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateSysUserDetail } from '@/api/system'
import { getInfo } from '@/api/user'
import options from './region.js' // eslint-disable-line

export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          nickname: '',
          mobile: '',
          address: '',
          profile: ''
        }
      }
    }
  },
  data() {
    return {
      options: region, // eslint-disable-line
      rules: {
        nickname: [{ required: true, validator: this.checkNickname, trigger: ['blur', 'change'] }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = {}
          tempData.id = this.user.id
          tempData.nickname = this.user.nickname
          tempData.address = this.user.address
          tempData.profile = this.user.profile
          updateSysUserDetail(tempData).then(response => {
            if (response.message === 'success') {
              this.$message({
                message: this.$t('common.edit-success'),
                type: 'success',
                duration: 2000
              })
              getInfo().then(response => {}).catch(error => {
                console.error(error)
              })
            } else {
              this.handleWarning(response)
            }
          })
        }
      })
    },
    checkNickname(rule, value, callback) {
      if (!value) {
        return callback(new Error(this.$t('sysuser.nickname-required')))
      } else {
        callback()
      }
    },
    handleWarning(response) {
      this.$message({
        message: response.message || response,
        type: 'warning',
        duration: 2000
      })
    }
  }
}
</script>
