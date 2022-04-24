<template>
  <div class="app-container">
    <div v-if="user">
      <div style="font-size: 20px; font-weight: bold;">个人账户设置</div>
      <el-row :gutter="20">
        <el-col :span="8" >
          <div>
            <div style="margin-top: 30px; align-items: center; margin-left: 10px;"><img :src="imgSrc" style="height: 130px; width: 130px; border-radius: 50%;" /></div>
            <div>
              <el-upload
                class="upload-demo"
                ref="upload"
                action="../clin/file/uploadImg"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                name="file"
                :before-remove="beforeRemove"
                :on-change="handleAvatarSuccess"
                :auto-upload="false"
                multiple
                :limit="1"
                :on-exceed="handleExceed"
                accept='.jpg,.png'
                :file-list="fileList">
                <el-button size="small" style="margin-left: 26%; margin-top: 20px;"><i class="el-icon-top" style="font-size: 12px; padding-right: 5px; padding-top: 2px;"></i>更换头像</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2M</div>
              </el-upload>
            </div>
          </div>
        </el-col>
        <el-col :span="16" >
          <div style="padding-top: 40px;">
            <div class="heightRow">手机号码：{{user.mobile}}</div>
            <div class="heightRow">账号角色：{{user.roleNames}}</div>
            <div class="heightRow">登录账号：{{user.name}}</div>
<!--            <div class="heightRow">登录账号
              <div style="width: 300px; margin-top: 10px;">
                <el-input
                  type="text"
                  placeholder="请输入"
                  v-model="user.name"
                  maxlength="20"
                  show-word-limit >
                </el-input>
              </div>
            </div>-->
            <div class="heightRow marginTopSmall">账号名称
              <div style="width: 300px; margin-top: 10px;">
                <el-input
                  type="text"
                  placeholder="请输入"
                  v-model="user.nickname"
                  maxlength="10"
                  show-word-limit >
                </el-input>
              </div>
            </div>
            <div> <el-button size="small" type="primary" style="margin-top: 50px;" @click="updateInfo">更新信息</el-button></div>
          </div>
        </el-col>

      </el-row>
    </div>
  </div>
</template>
<script>
import {updateNickName} from '@/api/system'
import { mapGetters } from 'vuex'
import store from '@/store'
export default {
  name: 'Profile',
  data() {
    return {
      user: {},
      fileList:[],
      imgSrc:'../data/avatar.png',
    }
  },
  computed: {
    ...mapGetters([
      'id',
      'name',
      'nickname',
      'roleNames',
      'mobile',
      'profile',
      'avatar',
      'imgPath'
    ])
  },
  created() {
    this.getUser()
    this.imgSrc=this.imgPath+this.avatar;
  },
  methods: {
    handleAvatarSuccess(file, fileList){
      this.fileList=fileList;
    },
    handleRemove(file, fileList) {
      // console.log(file, fileList);
      this.fileList=[];
    },
    handlePreview(file) {
      // console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      this.fileList=[];
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    getUser() {
      // console.log(this.nickname,"getUser：this.nickname")
      this.user = {
        id: this.id,
        roleNames: this.roleNames,
        name: this.name,
        nickname: this.nickname,
        mobile: this.mobile,
        profile: this.profile,
        avatar: this.avatar
      }
    },
    updateInfo(){
      let  tempData =  new FormData();
      tempData.append("id",this.id);
      tempData.append("nickname",this.user.nickname);
      if(this.fileList[0]!=undefined ){
        let f=this.fileList[0].raw;
        let isLt2M=f.size/ 1024 / 1024 > 2;
        // let isLt2M=f.size/ 1024  > 200;
        if(isLt2M){
          this.$message.error('上传头像图片大小不能超过 2MB!');
          return ;
        }
        tempData.append("file",f);
      }
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });

      updateNickName(tempData).then(response => {
        loading.close();
        if (response.message === 'success') {
          this.$message({
            message: this.$t('common.edit-success'),
            type: 'success',
            duration: 2000
          })
          if(response.data==null||response.data==""){
            let newAvatar=undefined;
            let newNickname =this.user.nickname;
            // this.nickname=newNickname;
            let param = {avatar:newAvatar,nickname:newNickname}
            store.dispatch('user/changeAvatar',param )

          }else{
            let newAvatar=response.data;
            let newNickname =this.nickname;
            // this.nickname=newNickname;
            this.avatar=newAvatar
            this.imgSrc=this.imgPath+newAvatar;
            let param = {avatar:newAvatar,nickname:newNickname}
            store.dispatch('user/changeAvatar', param)
          }
          this.$refs.upload.clearFiles();
        } else {
          this.handleWarning(response)
        }
      })
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
<style>
  .app-container{padding: 40px;}
  .heightRow{height: 60px;}
  .marginTopSmall{margin-top: 30px;}
</style>
