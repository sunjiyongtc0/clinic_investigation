<template>
  <div class="app-container">
    <div>
    <el-row>
      <el-col :span="6">
        <div class="row1-box-1">
          <div class="lineHeight">总问卷数量</div>
          <div class="row1-box-2">{{countNum}}</div>
          <div class="marginTopMini"><img src="../../../image/data1.png" class="row1-box-img" /></div>
          <div class="row1-box-3">今日新增  {{todayNum}}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="row1-box-1">
          <div class="lineHeight">志愿者数量</div>
          <div class="row1-box-2">{{userNum}}</div>
          <div class="marginTopMini"><img src="../../../image/data2.png" class="row1-box-img" /></div>
          <div class="row1-box-3">平均调查问卷数    {{avgNum}}/人</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div id="main1" style="width:300px; height: 300px;"></div>
      </el-col>
      <el-col :span="6">
        <div style="margin-top: 70px; line-height: 30px; " v-for="(item,i) in ageDataArr" :key="i">
          <el-col :span="2"><div :style="'background:'+colorArr[i]" style="margin-top: 7px;  height: 10px; width: 10px; border-radius: 50%;"></div></el-col>
          <el-col :span="6"><div style="color: #5A5A5A; letter-spacing:2px;">{{item.name}}<span style="color: #929292;">|</span></div></el-col>
          <el-col :span="6"><div style="color: #929292;">{{item.prop}}%</div></el-col>
          <el-col :span="9"><div style="color: #5A5A5A;">{{item.value}}人</div></el-col>
        </div>
      </el-col>
    </el-row>
  </div>

    <div>
      <div class="row2-box">
        <el-row>
          <el-col :span="12"><div class="row2-box-1">问卷结果分析</div></el-col>
          <el-col :span="12">
            <div class="block floatRight">
              <span class="demonstration"></span>
              <el-date-picker
                v-model="value2"
                type="daterange"
                value-format="yyyy-MM-dd"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </div>
          </el-col>
        </el-row>
      </div>
      <el-row>
        <el-col :span="6">
          <div id="main2" class="row2-box-pie1"></div>
        </el-col>
        <el-col :span="8">
          <div style="margin-top: 70px; line-height: 30px; " v-for="(item,i) in scoreDataArr" :key="i">
            <el-col :span="2"><div :style="'background:'+colorArr[i]" style="margin-top: 7px;  height: 10px; width: 10px; border-radius: 50%;"></div></el-col>
            <el-col :span="6"><div style="color: #5A5A5A; letter-spacing:2px;">{{item.name}}<span style="color: #929292;">|</span></div></el-col>
            <el-col :span="6"><div style="color: #929292;">{{item.prop}}%</div></el-col>
            <el-col :span="9"><div style="color: #5A5A5A;">{{item.value}}人</div></el-col>
          </div>
        </el-col>
        <el-col :span="5">
          <div id="main3" class="row2-box-pie2"></div>
        </el-col>
        <el-col :span="5">
          <div id="main4" class="row2-box-pie2"></div>
        </el-col>
      </el-row>
    </div>

    <div>
      <div class="row2-box">
        <el-row>
          <el-col :span="12"><div class="row2-box-1">问卷数量统计</div></el-col>
          <el-col :span="12">
            <div class="block floatRight">
              <span class="demonstration"></span>
              <el-date-picker
                v-model="value3"
                type="daterange"
                value-format="yyyy-MM-dd"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </div>
          </el-col>
        </el-row>
      </div>
      <el-row>
        <el-col :span="16">
          <div id="main5" class="row3-box-bar"></div>
        </el-col>
        <el-col :span="8">
          <div style="line-height: 50px; font-size: 18px; margin-top: 10px">志愿者义诊数排名</div>
          <div style="line-height:40px; " v-for="(item,i) in dataArr2" :key="i">
            <el-col :span="3"><div :style="i<3?'background:#314659; color: white;':'background:#F0F2F5; color: #555556;'" style="font-size: 14px; margin-top: 10px;  height: 20px; width: 20px; border-radius: 50%; text-align: center; line-height: 20px;">{{i+1}}</div></el-col>
            <el-col :span="12"><div style="color: #5A5A5A; letter-spacing:2px;">{{item.nickname}}</div></el-col>
            <el-col :span="9"><div style="color: #5A5A5A;">{{item.con}}</div></el-col>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
  import * as echarts from 'echarts'
  import { getStatisticsData ,getScoreData,getCountData} from '@/api/report'

  export default {
    name: 'MyNotification',
    components: {
    },
    data() {
      return {
        countNum:undefined,
        todayNum:undefined,
        userNum:undefined,
        avgNum:undefined,
        colorArr:['#3AA0FF','#36CBCB','#4DCB73','#FAD337','#F2637B','#975FE4','#42b983'],
        ageDataArr:[],
        scoreDataArr:[],
        smokeDataArr:[],
        smokePorp:undefined,
        alcoholDataArr:[],
        alcoholPorp:undefined,
        value2:undefined,
        value3:undefined,
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [
            {
            text: '本周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '本月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '全年',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        xData:[],
        yData:[],
        dataArr2:[]
      }
    },
    mounted() {
      this.initCharts1();
      this.initCharts2();
      this.initCharts3();
      this.initCharts4();
      this.initCharts5();
    },
    watch:{
      value2:'timeChange',
      value3:'countChange'
    },
    created() {
      getStatisticsData().then(response => {
        if (response.message === 'success') {
          let data=response.data;
              this.countNum=data.countNum;
              this.todayNum=data.todayNum;
              this.userNum=data.userNum;
              this.avgNum=data.avgNum;
              let age=data.age;
                  age.forEach((row)=>{
                    this.ageDataArr.push({name:row.title,prop:(row.num*100/this.countNum).toFixed(1),value:row.num})
                  })
          this.initCharts1();
        } else {
          //this.handleWarning(response)
        }
      })

      let end = new Date();
      let start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
      this.value2=[start.Format('yyyy-MM-dd'),end.Format('yyyy-MM-dd')];
      this.value3=[start.Format('yyyy-MM-dd'),end.Format('yyyy-MM-dd')];
    },
    methods: {
      timeChange(){
            getScoreData(this.value2[0],this.value2[1]).then(response => {
              if (response.message === 'success') {
                this.scoreDataArr=[];
                this.smokeDataArr=[];
                this.alcoholDataArr=[];
                let data=response.data;
                let count=data.con;
                data.score.forEach((row)=>{
                  this.scoreDataArr.push({name:row.title,value:row.num,prop:(row.num*100/count).toFixed(1)})
                })
                this.initCharts2();
                this.smokeDataArr=[{name:'吸烟',value:data.smoke},{name:'不吸烟',value:count-data.smoke}];
                this.smokePorp=(data.smoke*100/count).toFixed(1)=='NaN'?'0%':(data.smoke*100/count).toFixed(1)+"%"
                this.initCharts3();
                this.alcoholDataArr=[{name:'饮酒',value:data.alcohol},{name:'不饮酒',value:count-data.alcohol}];
                this.alcoholPorp=(data.alcohol*100/count).toFixed(1)=='NaN'?'0%':(data.alcohol*100/count).toFixed(1)+"%"
                this.initCharts4();
              } else {
                //this.handleWarning(response)
              }
            })
      },
      countChange(){
        getCountData(this.value3[0],this.value3[1]).then(response => {
          if (response.message === 'success') {
            this.xData=response.data.distribution.xData;
            this.yData=response.data.distribution.yData;
            this.initCharts5()
            this.dataArr2=response.data.rank;
          }
        })
      },
      initCharts1(){ //年龄占比
        let myChart = echarts.init(document.getElementById('main1'));
        let option;
        option = {
          title: {
            text: '患者年龄占比',  //图形标题，配置在中间对应效果图的80%
            left: "center",
            top: "45%",
            textStyle: {
              fontWeight:'500',
              // color: "rgb(50,197,233)",
              fontSize: 20,
              align: "center"
            }
          },
          color:this.colorArr,
          series: [
            {
              name: 'Age Prop',
              type: 'pie',
              radius: ['50%', '75%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 0,
                borderColor: '#fff',
                borderWidth: 2
              },
              label: {
                show: false,
                position: 'center'
              },
              labelLine: {
                show: false
              },
              data: this.ageDataArr
            }
          ]
        };
        option && myChart.setOption(option);
      },
      initCharts2(){
        let myChart = echarts.init(document.getElementById('main2'));
        let option;
        option = {
          title: {
            text: '健康分值占比',  //图形标题，配置在中间对应效果图的80%
            left: "center",
            top: "45%",
            textStyle: {
              fontWeight:'500',
              // color: "rgb(50,197,233)",
              fontSize: 20,
              align: "center"
            }
          },
          color:this.colorArr,
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['60%', '80%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 0,
                borderColor: '#fff',
                borderWidth: 2
              },
              label: {
                show: false,
                position: 'center'
              },
              labelLine: {
                show: false
              },
              data: this.scoreDataArr
            }
          ]
        };
        option && myChart.setOption(option);
      },
      initCharts3(){
        let myChart = echarts.init(document.getElementById('main3'));
        let option;
        let pre =  this.smokePorp;
        option = {
          title: {
            text: '吸烟占比:'+pre,  //图形标题，配置在中间对应效果图的80%
            left: "center",
            top: "45%",
            textStyle: {
              fontWeight:'500',
              // color: "rgb(50,197,233)",
              fontSize: 14,
              align: "center"
            }
          },
          color:['#5599FE','#F3F6F8'],
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['60%', '50%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 0,
                borderColor: '#fff',
                borderWidth: 2
              },
              label: {
                show: false,
                position: 'center'
              },
              labelLine: {
                show: false
              },
              data: this.smokeDataArr
            }
          ]
        };
        option && myChart.setOption(option);
      },
      initCharts4(){
        let myChart = echarts.init(document.getElementById('main4'));
        let option;
        let pre = this.alcoholPorp;
        option = {
          title: {
            text: '饮酒占比:'+pre,  //图形标题，配置在中间对应效果图的80%
            left: "center",
            top: "47%",
            textStyle: {
              fontWeight:'500',
              // color: "rgb(50,197,233)",
              fontSize: 14,
              align: "center"
            }
          },
          color:['#5599FE','#F3F6F8'],
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['60%', '50%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 0,
                borderColor: '#fff',
                borderWidth: 2
              },
              label: {
                show: false,
                position: 'center'
              },
              labelLine: {
                show: false
              },
              data: this.alcoholDataArr
            }
          ]
        };
        option && myChart.setOption(option);
      },
      initCharts5(){
        let myChart = echarts.init(document.getElementById('main5'));
        let option;
        option = {
          title: {//
            text: '问卷趋势',  //图形标题，配置在中间对应效果图的80%
            /*left: "center",
            top: "45%",*/
            textStyle: {
              fontWeight:'500',
              // color: "rgb(50,197,233)",
              fontSize: 18,
              align: "center"
            }
          },
          color:'#3BA1FF',
          xAxis: {
            type: 'category',
            data: this.xData
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: this.yData,
              type: 'bar'
            }
          ]
        };
        option && myChart.setOption(option);
      },
    }
  }




  Date.prototype.Format = function (fmt) {
    var o = {
      "M+": this.getMonth() + 1, //月份
      "d+": this.getDate(), //日
      "H+": this.getHours(), //小时
      "m+": this.getMinutes(), //分
      "s+": this.getSeconds(), //秒
      "q+": Math.floor((this.getMonth() + 3) / 3), //季度
      "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
  }
</script>
<style>
  .floatRight{float: right; margin: 0 15px 5px 0;}
  .row1-box-1{border: 1px solid #E9E9E9; width: 90%; height: 230px; padding: 20px; margin-top: 30px;}
  .lineHeight{line-height: 30px;}
  .row1-box-2{height: 50px; line-height: 50px; font-size: 29px; font-weight: 500;}
  .marginTopMini{margin-top: 10px;}
  .row1-box-img{width:90%; margin-left: 5%; height: 30px;}
  .row1-box-3{margin-top:30px; line-height:50px; border-top: 1px solid #E9E9E9;}
  .row2-box{ margin-top:10px; line-height: 40px; border-bottom: 1px solid #E8E8E8; width: 100%;}
  .row2-box-1{border-bottom: 2px solid #3AA0FF; padding-bottom: 5px; width: 120px; text-align: center; color: #3AA0FF;  }
  .row2-box-pie1{margin-top: 10px; width:300px; height: 300px;}
  .row2-box-pie2{margin-top: 20px; width:300px; height: 300px;}
  .row3-box-bar{margin-top: 20px; width:90%; height: 350px;}
</style>
