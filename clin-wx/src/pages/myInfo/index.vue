<template>
  <div @click="" id="page">
    <div style="height: 88rpx; background: #464C5B;  color: white; align-items: center; align-content: center; line-height: 88rpx;">
      <van-row>
        <van-col span="17" offset="1"><div>此页面所有内容必填</div></van-col>
        <van-col span="5"><div @click="clickBack">取消修改</div></van-col>
      </van-row>
    </div>
    <div>
      <div style="line-height: 100rpx; border-top: 1rpx solid #e6e6e6;">
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
      <div>
        <div ><van-field value="点击上传头像" label="头像上传"  input-align="right" readonly @click="uploadImg"></van-field></div>
        <div style="float: right;">
          <image v-show="fileList.length>0" class="imageSize"  :src="fileList[0]" mode="aspectFit" @click="previewImage($event,index)"/>
        </div>
      </div>
      <div>
        <van-field :value="userInfo.USERNAME" label="登录账号"  input-align="right" readonly></van-field>
      </div>
      <div>
        <van-field :value="nickname" label="账号名称" maxlength="50" placeholder="请输入账号名称"  input-align="right" style="border: none!important;"
                  :error="nicknameFlag"  @blur="(e)=>vanFieldBlur(e)" @change="(val)=>vanFieldChange1(val)"></van-field>
      </div>
    </div>
    <div style="position: fixed; z-index: 9999;  text-align: center; color: white; bottom: 0rpx; width: 100%; height: 90rpx; line-height: 90rpx; background: #44DAB7;" @click="submit"> 保存</div>
    <van-toast id="van-toast" />
  </div>
</template>

<script>
import Toast from '../../../static/vant-weapp/toast/toast';
export default {
  data () {
    return {
      userInfo:{},
      nickname:undefined,
      fileList: [],
      imageUrl:this.utils.imageUrl,
      nicknameFlag:false,
    }
  },

  components: {
  },
  mounted() {
    let info=wx.getStorageSync('userInfo');
    this.userInfo=info;
    this.nickname=info.nickname;
  },
  methods: {
    vanFieldChange1(value){
      this.nicknameFlag= false;
    },
    vanFieldBlur(event){
      this.nickname= event.mp.detail.value;
    },
    previewImage(e,index) {
      let url='';
      url=this.fileList[0]
      wx.previewImage({
        // current: e.currentTarget.id, // 当前显示图片的http链接
        urls:url // 需要预览的图片http链接列表
      })
    },
    uploadImg(){
      let _this = this;
      wx.chooseImage({
        count:1,
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
          let type = res.tempFiles[0].path.substring(res.tempFiles[0].path.length-3);//上传的文件后缀名
          if(res.tempFiles[0].size>2048*1000){
            Toast.fail('图片太大了')
          }else{
            if(type.toLowerCase()==='png'||type.toLowerCase()==='jpg'){
              let tempFilePaths = res.tempFiles[0].path;
              _this.fileList = [];
              _this.fileList.push(res.tempFilePaths);
            }else{
              Toast.fail('只能上传jpg和png格式的图片')
            }
          }

        },
        fail: function () {
          console.log('fail');
        },
        complete: function () {
          console.log('commplete');
        }
      })
    },
    inputNickname(e){
      this.nickname = e.mp.detail;
    },
    clickBack(){
      wx.navigateBack();
    },
    submit(){
      if(this.nickname===''){
        this.nicknameFlag=true;
      }else{
        let _this = this;
        let tempFile=undefined;
        if( _this.fileList.length>0) {
          tempFile=_this.fileList[0][0];
          wx.uploadFile({
            url: _this.utils.publicUrlApi+'clin/file/uploadImg',    //接口
            filePath: tempFile,
            name: 'file',
            header: {
              "Content-Type": "multipart/form-data"
            },
            formData:{
              id:_this.userInfo.user_id,
              nickname:_this.nickname,
            },
            success: function(res){
              let data=JSON.parse(res.data);
              if(data.message==="success"){
                _this.userInfo.avatar=data.data;
                _this.userInfo.nickname=_this.nickname;
                _this.fileList = [];
                wx.setStorage({
                  key:"userInfo",
                  data:_this.userInfo
                });
              }
            }
          })
        }else{
          _this.utils.POST('clin/file/uploadImg', {id:_this.userInfo.user_id,nickname:_this.nickname}).then(l_res=>{
            if(l_res.data.status === 200){
              Toast.success("更新成功");
              _this.userInfo.nickname=_this.nickname;
              wx.setStorage({
                key:"userInfo",
                data:_this.userInfo
              });
            }else{
              Toast.fail(l_res.data.message);
            }
          }).catch(
            l_err=>{console.log(l_err);}
          );
        }
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
  }
}
</script>

<style scoped>
  /*#page{
    padding: .3rem;
  }*/

  .imageSize{
    width: 140rpx;
    height: 140rpx;
  }
</style>
