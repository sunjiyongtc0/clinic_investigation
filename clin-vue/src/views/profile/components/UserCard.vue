<template>
  <el-card style="margin-bottom:20px;">
    <div slot="header" class="clearfix">
      <span>{{ $t('sysuser.avatar') }}</span>
    </div>

    <div class="user-profile">
      <div class="box-center">
        <pan-thumb :image="user.avatar" :height="'100px'" :width="'100px'" :hoverable="false">
          <div>Hello</div>
        </pan-thumb>
        <br>
        <br>
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">
          {{ $t('route.avatarUpload') }}
        </el-button>
      </div>
      <div class="box-center">
        <div class="user-name text-center">登录账号：{{ user.name }}</div>
        <div class="user-role text-center text-muted">账户角色：{{ user.roleNames}}</div>
        <div class="user-role text-center text-muted">角色编号：{{ user.role | uppercaseFirst }}</div>
      </div>
    </div>

    <!--<div class="user-bio">-->
      <!--<div class="user-skills user-bio-section">-->
        <!--<div class="user-bio-section-header"><svg-icon icon-class="skill" /><span>技能</span></div>-->
        <!--<div class="user-bio-section-body">-->
          <!--<div class="progress-item">-->
            <!--<span>Vue</span>-->
            <!--<el-progress :percentage="70" />-->
          <!--</div>-->
          <!--<div class="progress-item">-->
            <!--<span>JavaScript</span>-->
            <!--<el-progress :percentage="18" />-->
          <!--</div>-->
          <!--<div class="progress-item">-->
            <!--<span>CSS</span>-->
            <!--<el-progress :percentage="12" />-->
          <!--</div>-->
          <!--<div class="progress-item">-->
            <!--<span>ESLint</span>-->
            <!--<el-progress :percentage="100" status="success" />-->
          <!--</div>-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->

    <image-cropper
      v-show="imagecropperShow"
      :key="imagecropperKey"
      :width="300"
      :height="300"
      url="/api/file/file/uploadFile"
      lang-type="zh"
      @close="close"
      @crop-upload-success="cropSuccess"
    />
  </el-card>
</template>

<script>
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          name: '',
          email: '',
          avatar: '',
          role: ''
        }
      }
    }
  },
  data() {
    return {
      imagecropperShow: false,
      imagecropperKey: 0
    }
  },
  methods: {
    cropSuccess(resData) {
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.$store.state.user.avatar = resData.data
      this.$nextTick(() => {
        this.$router.replace({
          path: '/redirect' + '/profile/index'
        })
      })
    },
    close() {
      this.imagecropperShow = false
    }
  }
}
</script>

<style lang="scss" scoped>
.box-center {
  margin: 0 auto;
  display: table;
}

.text-muted {
  color: #777;
}

.user-profile {
  .user-name {
    font-weight: bold;
  }

  .box-center {
    padding-top: 10px;
  }

  .user-role {
    padding-top: 10px;
    font-weight: 400;
    font-size: 14px;
  }

  .box-social {
    padding-top: 30px;

    .el-table {
      border-top: 1px solid #dfe6ec;
    }
  }

  .user-follow {
    padding-top: 20px;
  }
}

.user-bio {
  margin-top: 20px;
  color: #606266;

  span {
    padding-left: 4px;
  }

  .user-bio-section {
    font-size: 14px;
    padding: 15px 0;

    .user-bio-section-header {
      border-bottom: 1px solid #dfe6ec;
      padding-bottom: 10px;
      margin-bottom: 10px;
      font-weight: bold;
    }
  }
}
</style>
