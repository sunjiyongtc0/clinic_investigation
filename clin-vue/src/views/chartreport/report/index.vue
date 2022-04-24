<template><!--数据分析-->
  <div class="app-container">
    <div>
      <el-row>
        <el-col :span="10">
          <div style="line-height: 50px; padding: 40px 10px 10px 50px; ">
            <div>时间范围： <el-date-picker
              v-model="query1.time"
              type="daterange"
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker></div>
            <div>患者年龄：
              <el-input type="number" v-model="query1.age1" style="width: 100px;" placeholder="开始"></el-input>
              至
              <el-input type="number" v-model="query1.age2" style="width: 100px;" placeholder="结束"></el-input>
            </div>
            <div>患者性别：
              <el-radio-group v-model="query1.sex">
                <el-radio :label=-1>全部</el-radio>
                <el-radio :label=0>男</el-radio>
                <el-radio :label=1>女</el-radio>
              </el-radio-group>
            </div>
            <div>
              <el-button size="small" type="primary" @click="queryData(1)">查 询</el-button>
              <el-button size="small" @click="resetQuery(1)">重 置</el-button>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div id="main1" class="row2-box-pie1"></div>
        </el-col>
        <el-col :span="8">
          <div style="margin-top: 70px; line-height: 30px; " v-for="(item,i) in dataArr1" :key="i">
            <el-col :span="2"><div :style="'background:'+colorArr[i]" style="margin-top: 7px;  height: 10px; width: 10px; border-radius: 50%;"></div></el-col>
            <el-col :span="6"><div style="color: #5A5A5A; letter-spacing:2px;">{{item.name}}<span style="color: #929292;">|</span></div></el-col>
            <el-col :span="6"><div style="color: #929292;">{{(item.value*100/dataCount1).toFixed(1)}}%</div></el-col>
            <el-col :span="9"><div style="color: #5A5A5A;">{{item.value}}人</div></el-col>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-divider></el-divider>
    <div>
      <el-row>
        <el-col :span="10">
          <div style="line-height: 50px; padding: 40px 10px 10px 50px; ">
            <div>时间范围： <el-date-picker
              v-model="query2.time"
              type="daterange"
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker></div>
            <div>健康分值：
                <el-input type="number" v-model="query2.score1" style="width: 100px;" placeholder="开始"></el-input>
                至
                <el-input type="number" v-model="query2.score2" style="width: 100px;" placeholder="结束"></el-input>
            </div>
            <div>患者性别：
              <el-radio-group v-model="query2.sex">
                <el-radio :label=-1>全部</el-radio>
                <el-radio :label=0>男</el-radio>
                <el-radio :label=1>女</el-radio>
              </el-radio-group>
            </div>
            <div>
              <el-button size="small" type="primary" @click="queryData(2)">查 询</el-button>
              <el-button size="small" @click="resetQuery(2)">重 置</el-button>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div id="main2" class="row2-box-pie1"></div>
        </el-col>
        <el-col :span="8">
          <div style="margin-top: 70px; line-height: 30px; " v-for="(item,i) in dataArr2" :key="i">
            <el-col :span="2"><div :style="'background:'+colorArr[i]" style="margin-top: 7px;  height: 10px; width: 10px; border-radius: 50%;"></div></el-col>
            <el-col :span="6"><div style="color: #5A5A5A; letter-spacing:2px;">{{item.name}}<span style="color: #929292;">|</span></div></el-col>
            <el-col :span="6"><div style="color: #929292;">{{(item.value*100/dataCount2).toFixed(1)}}%</div></el-col>
            <el-col :span="9"><div style="color: #5A5A5A;">{{item.value}}人</div></el-col>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-divider></el-divider>
    <div>
      <el-row>
        <el-col :span="10">
          <div style="line-height: 50px; padding: 40px 10px 10px 50px; ">
            <div>时间范围： <el-date-picker
              v-model="query3.time"
              type="daterange"
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker></div>
            <div>健康分值：
              <el-input type="number" v-model="query3.score1" style="width: 100px;" placeholder="开始"></el-input>
              至
              <el-input type="number" v-model="query3.score2" style="width: 100px;" placeholder="结束"></el-input>
            </div>
              <div>患者年龄：
                <el-input type="number" v-model="query3.age1" style="width: 100px;" placeholder="开始"></el-input>
                至
                <el-input type="number" v-model="query3.age2" style="width: 100px;" placeholder="结束"></el-input>
              </div>
            <div>
              <el-button size="small" type="primary"  @click="queryData(3)">查 询</el-button>
              <el-button size="small" @click="resetQuery(3)">重 置</el-button>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div id="main3" class="row2-box-pie1"></div>
        </el-col>
        <el-col :span="8">
          <div style="margin-top: 70px; line-height: 30px; " v-for="(item,i) in dataArr3" :key="i">
            <el-col :span="2"><div :style="'background:'+colorArr1[i]" style="margin-top: 7px;  height: 10px; width: 10px; border-radius: 50%;"></div></el-col>
            <el-col :span="6"><div style="color: #5A5A5A; letter-spacing:2px;">{{item.name}}<span style="color: #929292;">|</span></div></el-col>
            <el-col :span="6"><div style="color: #929292;">{{(item.value*100/dataCount3).toFixed(1)}}%</div></el-col>
            <el-col :span="9"><div style="color: #5A5A5A;">{{item.value}}人</div></el-col>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
  import * as echarts from 'echarts'
  import { getScoreData ,getAgeData,getSexData} from '@/api/analy'
  export default {
    name: 'MyNotification',
    components: {
    },
    data() {
      return {
        options: [{ value: '60', label: '60岁'}, {value: '61', label: '61岁'}, {value: '62', label: '62岁'}, {value: '63', label: '63岁'}],
        query1:{
          sex:-1,
          time:undefined,
          age1:0,
          age2:0},
        query2:{
          sex:-1,
          time:undefined,
          score1:0,
          score2:0},
        query3:{
          score1:0,
          score2:0,
          time:undefined,
          age1:0,
          age2:0},
        difTime:[],
        colorArr:['#3AA0FF','#36CBCB','#4DCB73','#FAD337','#F2637B','#975FE4','#42b983'],
        colorArr1:['#FF0099','#5599FE'],
        dataArr1:[],
        dataCount1:0,
        dataArr2:[],
        dataCount2:0,
        dataCount3:0,
        dataArr3:[]
      }
    },
    mounted() {
      this.initCharts1();
      this.initCharts2();
      this.initCharts3();
    },
    created(){
      let end = new Date();
      let start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
      this.difTime=[start.Format('yyyy-MM-dd'),end.Format('yyyy-MM-dd')];
      for(let i=1;i<=3;i++){
        this.resetQuery(i)
        this.queryData(i)
      }
      },
    methods: {
      queryData(type){
        if(type===1){
          getScoreData(this.query1).then(response => {
            if (response.message === 'success') {
              let data=response.data;
              let dataCount1=0;
              this.dataArr1=[]
              data.forEach((row)=>{
                dataCount1+=row.num
                this.dataArr1.push({name:row.title,value:row.num});
              })
              this.dataCount1=dataCount1;
              this.initCharts1();
            } else {
              //this.handleWarning(response)
            }
          })
        }else if(type===2){
          getAgeData(this.query2).then(response => {
            if (response.message === 'success') {
              let data=response.data;
              let dataCount2=0;
              this.dataArr2=[]
              data.forEach((row)=>{
                dataCount2+=row.num
                this.dataArr2.push({name:row.title,value:row.num});
              })
              this.dataCount2=dataCount2;
              this.initCharts2();
            } else {
              //this.handleWarning(response)
            }
          })
        }else if(type===3){
          getSexData(this.query3).then(response => {
            if (response.message === 'success') {
              let data=response.data;
              let dataCount3=0;
              this.dataArr3=[]
              data.forEach((row)=>{
                dataCount3+=row.num
                this.dataArr3.push({name:row.title,value:row.num});
              })
              this.dataCount3=dataCount3;
              this.initCharts3();
            } else {
              //this.handleWarning(response)
            }
          })
        }
      },
      resetQuery(type){
        if(type===1){
          this.query1={ sex:-1, time:this.difTime,  age1:0, age2:100};
        }else if(type===2){
          this.query2={ sex:-1, time:this.difTime,  score1:0, score2:100};
        }else if(type===3){
          this.query3={  time:this.difTime,  age1:0, age2:100,  score1:0, score2:100};
        }
      },
      initCharts1(){
        let myChart = echarts.init(document.getElementById('main1'));
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
              data: this.dataArr1
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
              data: this.dataArr2
            }
          ]
        };
        option && myChart.setOption(option);
      },
      initCharts3(){
        let myChart = echarts.init(document.getElementById('main3'));
        let option;
        option = {
          title: {
            text: '性别占比',  //图形标题，配置在中间对应效果图的80%
            left: "center",
            top: "45%",
            textStyle: {
              fontWeight:'500',
              // color: "rgb(50,197,233)",
              fontSize: 20,
              align: "center"
            }
          },
          color:this.colorArr1,
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
              data: this.dataArr3
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
  .row2-box-pie1{margin-top: 10px; width:300px; height: 300px;}
</style>
