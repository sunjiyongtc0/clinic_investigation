let pageSize=20,$openid = wx.getStorageSync('openid_procurement'),$userInfo = wx.getStorageSync('userInfo'),$userRole =wx.getStorageSync('userInfo').roleCode ;
// const publicUrl = 'https://www.yaaiya.com/';
const publicUrl = 'https://www.yaaiya.com/';
const publicUrlApi = publicUrl,
  imageUrl = 'https://www.yaaiya.com/data/';//存储图片的服务器位置
/**成功信息提示 */
function showSuccessToast(msg) {
  wx.showToast({
    title: msg,
    duration: 1000,
    icon: 'success'
  });
}
/**错误信息提示 */
function showErrorToast(msg) {
  wx.showToast({
    title: msg,
    duration: 3000,
    icon: 'none'
  });
}
/**加载对话框 */
function showLoadingDialog() {
  wx.showLoading({
    title: '数据加载中',
    mask: true
  });
}
/**取消加载对话框 */
function hideLoadingDialog() {
  wx.hideLoading();
}

function POST(url, params) {
  //加载loading
  // wx.showLoading({
  //   title: '加载中...',
  //   mask: true
  // })
  let promise = new Promise(function (resolve, reject) {
    wx.request({
      withCredentials: true,
      url:publicUrl+url,
      data: params,
      header: { 'authorization': 'N5401NkkZmdooLrn6kTSxKxGyQc',
        'rolecode': wx.getStorageSync('userInfo').role_code,
        'Content-Type': 'application/x-www-form-urlencoded'},
      method: 'POST',
      success: (res) => {
        resolve(res)
      },
      fail: (res) => {
        reject(res)
      },
      complete: () => {
        wx.hideLoading()
      }
    })
  });
  return promise;
}


function POSTJSON(url, data) {

  let promise = new Promise(function (resolve, reject) {
    wx.request({
      withCredentials: true,
      url:publicUrl+url,
      data,
      header: { 'authorization': 'N5401NkkZmdooLrn6kTSxKxGyQc',
        'rolecode': wx.getStorageSync('userInfo').role_code,
        'Content-Type': 'application/json' },
      method: 'POST',
      success: (res) => {
        resolve(res)
      },
      fail: (res) => {
        reject(res)
      },
      complete: () => {
        wx.hideLoading()
      }
    })
  });
  return promise;
}

function GET(url, params) {
  let promise = new Promise(function (resolve, reject) {
    wx.request({
      url:publicUrl+url,
      data: params,
      header: { 'authorization': 'N5401NkkZmdooLrn6kTSxKxGyQc',
        'rolecode': wx.getStorageSync('userInfo').role_code,
        'Content-Type': 'application/x-www-form-urlencoded'
         },
      method: 'GET',
      success: (res) => {
        resolve(res)
      },
      fail: (res) => {
        reject(res)
      }
    })
  });
  return promise
}


function PostLogin(url, params) {
  let promise = new Promise(function (resolve, reject) {
    wx.request({
      withCredentials: true,
      url:publicUrl+url,
      data: params,
      header: { 'authorization': 'N5401NkkZmdooLrn6kTSxKxGyQc',
        'Content-Type': 'application/x-www-form-urlencoded'},
      method: 'POST',
      success: (res) => {
        resolve(res)
      },
      fail: (res) => {
        reject(res)
      },
      complete: () => {
        wx.hideLoading()
      }
    })
  });
  return promise;
}

function roleFilter(val){//时间格式转换时间戳
  let value='';
  if(val===''||val===null || !val){
    return '';
  }else if(val==='ROLE_MANAGER'){
    value=0;
  }

  return value;
}
function setMyNavBarMetaPaddint(vm,val) {
  let navbarHeightPx = (88/750 * wx.getSystemInfoSync().windowWidth);
  wx.getSystemInfo({
    success (res) {
      vm[val] = (res.statusBarHeight + navbarHeightPx).toFixed(0) + 'px';
    }
  });
}
//与底部固定按钮距离
function publicDistanceBottom(vm,val) {
  vm[val] = 100*this.unitConversionValue() ;
}
//iphone 全面屏 安全距离
function publicDistanceBottomIphone(vm,val) {
  if(vm && val){
    vm[val] = 68*this.unitConversionValue() ;
  }else {
    return 68*this.unitConversionValue();
  }
}
function unitConversionValue(){
  let Numerical='';
  wx.getSystemInfo({
    success (res) {
      Numerical=Number((res.screenWidth/750).toFixed(3)); //rpx 和 px 之间的倍数值
    }
  });
  return Numerical
}
//解决计算精度问题
let accAdd = function(num1, num2) {//加法
  num1 = Number(num1);
  num2 = Number(num2);
  var dec1, dec2, times;
  try { dec1 = countDecimals(num1)+1; } catch (e) { dec1 = 0; }
  try { dec2 = countDecimals(num2)+1; } catch (e) { dec2 = 0; }
  times = Math.pow(10, Math.max(dec1, dec2));
  var result = (accMul(num1, times) + accMul(num2, times)) / times;
  return getCorrectResult("add", num1, num2, result);
};
let accSub = function(num1, num2) {//减法
  num1 = Number(num1);
  num2 = Number(num2);
  let dec1, dec2, times;
  try { dec1 = countDecimals(num1)+1; } catch (e) { dec1 = 0; }
  try { dec2 = countDecimals(num2)+1; } catch (e) { dec2 = 0; }
  times = Math.pow(10, Math.max(dec1, dec2));
  let result = Number((accMul(num1, times) - accMul(num2, times)) / times);
  return getCorrectResult("sub", num1, num2, result);
};
let accMul = function(num1, num2) {//乘法
  num1 = Number(num1);
  num2 = Number(num2);
  let times = 0, s1 = num1.toString(), s2 = num2.toString();
  try { times += countDecimals(s1); } catch (e) { }
  try { times += countDecimals(s2); } catch (e) { }
  let result = convertToInt(s1) * convertToInt(s2) / Math.pow(10, times);
  return getCorrectResult("mul", num1, num2, result);
};
let accDiv = function(num1, num2) {//除法
  num1 = Number(num1);
  num2 = Number(num2);
  let t1 = 0, t2 = 0, dec1, dec2;
  try { t1 = countDecimals(num1); } catch (e) { }
  try { t2 = countDecimals(num2); } catch (e) { }
  dec1 = convertToInt(num1);
  dec2 = convertToInt(num2);
  let result = accMul((dec1 / dec2), Math.pow(10, t2 - t1));
  return getCorrectResult("div", num1, num2, result);
};
let getCorrectResult = function(type, num1, num2, result) {//确认计算结果无误，以防万一
  let temp_result = 0;
  switch (type) {
    case "add":
      temp_result = num1 + num2;
      break;
    case "sub":
      temp_result = num1 - num2;
      break;
    case "div":
      temp_result = num1 / num2;
      break;
    case "mul":
      temp_result = num1 * num2;
      break;
  }
  if (Math.abs(result - temp_result) > 1) {
    return temp_result;
  }
  return result;
};
let convertToInt = function(num) {//将小数转成整数
  num = Number(num);
  let newNum = num;
  let times = countDecimals(num);
  let temp_num = num.toString().toUpperCase();
  if (temp_num.split('E').length === 2) {
    newNum = Math.round(num * Math.pow(10, times));
  } else {
    newNum = Number(temp_num.replace(".", ""));
  }
  return newNum;
};
let countDecimals = function(num) {//计算小数位的长度
  let len = 0;
  try {
    num = Number(num);
    let str = num.toString().toUpperCase();
    if (str.split('E').length === 2) { // scientific notation
      let isDecimal = false;
      if (str.split('.').length === 2) {
        str = str.split('.')[1];
        if (parseInt(str.split('E')[0]) !== 0) {
          isDecimal = true;
        }
      }
      let x = str.split('E');
      if (isDecimal) {
        len = x[0].length;
      }
      len -= parseInt(x[1]);
    } else if (str.split('.').length === 2) { // decimal
      if (parseInt(str.split('.')[1]) !== 0) {
        len = str.split('.')[1].length;
      }
    }
  } catch(e) {
    throw e;
  } finally {
    if (isNaN(len) || len < 0) {
      len = 0;
    }
    return len;
  }
};
/* 判断是否退出登录*/
function isLoginOut(){
  if(!wx.getStorageSync('userInfo')){
    wx.navigateTo({url: "../../pages/login/main"});
  }
}


/*判断登录 子页面*/
function isopenidOne() {
  if( wx.getStorageSync('openid_procurement')===''){
    wx.navigateTo({url: "../../loginUser/main"});
    return false;
  }
  return true
}
export default {
  pageSize,
  $openid,
  $userInfo,
  $userRole,
  publicUrlApi,
  imageUrl,
  showSuccessToast,
  showErrorToast,
  showLoadingDialog,
  hideLoadingDialog,
  setMyNavBarMetaPaddint,
  publicDistanceBottom,
  publicDistanceBottomIphone,
  unitConversionValue,
  accAdd,
  accSub,
  accMul,
  accDiv,
  isopenidOne,
  isLoginOut,
  roleFilter,
  POST,
  PostLogin,
  POSTJSON,
  GET,
}
