<template>
  <div class="app-container">
    <div v-show="showList">
      <div class="filter-container">
        <span style="font-size: 14px; line-height: 30px;">患者姓名：</span><el-input v-model="listQuery.name" placeholder="输入患者姓名" clearable class="filter-input" style="width: 125px" @keyup.enter.native="handleFilter" maxlength="50" />
        <span style="font-size: 14px; line-height: 30px;">患者手机号：</span><el-input v-model="listQuery.telephone" placeholder="输入患者手机号" clearable class="filter-input" style="width: 125px" @keyup.enter.native="handleFilter" maxlength="50"  />
        <span style="font-size: 14px; line-height: 30px;">患者年龄：</span><el-input type="number" v-model="listQuery.agestart" placeholder="开始" clearable class="filter-input" style="width: 90px" @keyup.enter.native="handleFilter" maxlength="50" />
        - <el-input type="number" v-model="listQuery.ageend" placeholder="结束" clearable class="filter-input" style="width: 90px" @keyup.enter.native="handleFilter" maxlength="50" />
        <span style="font-size: 15px; line-height: 30px;">志愿者账号：</span> <el-input v-model="listQuery.username" placeholder="输入志愿者账号" clearable class="filter-input" style="width: 140px" @keyup.enter.native="handleFilter" maxlength="50" />
        <div style="float: right">
          <el-button v-waves type="primary" class="filter-item" icon="el-icon-search" @click="handleFilter">
            {{ $t('common.query') }}
          </el-button>
          <el-button v-waves class="filter-item" icon="el-icon-refresh-right" @click="handleReset">
            {{ $t('common.reset') }}
          </el-button>
          <el-button v-waves type="primary"  class="filter-item" icon="el-icon-refresh-right" @click="handleExport">
            {{ $t('common.export') }}
          </el-button>
        </div>
      </div>
      <el-table
        :key="tableKey"
        ref="multipleTable"
        v-loading="listLoading"
        :data="list"
        border
        fit
        highlight-current-row
        style="width: 100%"
        @sort-change="sortChange"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="40" />
        <el-table-column label="问卷编号" min-width="120px" prop="group_id" />
        <el-table-column label="患者姓名" width="80px" prop="name" />
        <el-table-column label="患者手机号" prop="telephone" />
        <el-table-column label="患者年龄" width="80px" prop="age" />
        <el-table-column label="患者性别" width="80px" prop="sex" />
        <el-table-column label="录入时间" width="140px"  prop="create_time" sortable="custom" :class-name="getSortClass('create_time')" />
        <el-table-column label="志愿者账号" prop="username" />
        <el-table-column :label="$t('common.operate')" min-width="120px"  align="center">
          <template slot-scope="{row,$index}">
            <el-button type="text" @click="handleDetail(row)">
              <i class="el-icon-edit-outline" />详情
            </el-button>
            <el-button type="text" @click="handleAdopt(row)">
              <i class="el-icon-edit-outline" />通过
            </el-button>
            <el-button type="text" @click="handleRefuse(row)">
              <i class="el-icon-edit-outline" />拒绝
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="getList" />
    </div>
    <bakDetail v-if="showDetail" :visible.sync="showDetail"></bakDetail><!--详情-->
  </div>
</template>

<script>
  import { queryExwaitList,refuse,adopt } from '@/api/examine'
  import {exportInv } from '@/api/investigation'
  import waves from '@/directive/waves' // waves directive
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination
  import qs from 'qs'
  import { saveAs } from 'file-saver'
  import mammoth from 'mammoth'
  import { getToken } from '@/utils/auth'
  import bakDetail from './bakDetail'

  export default {
    name: 'OnlineSysUser',
    components: { Pagination ,bakDetail },
    directives: { waves },
    data() {
      return {
        tableKey: 0,
        labelPosition: 'right',
        list: [],
        total: 0,
        listLoading: true,
        listQuery: {
          currentPage: 1,
          pageSize: 10,
          name: undefined,
          telephone: undefined,
          agestart: undefined,
          ageend: undefined,
          username: undefined,
          // status:'0',
          sorter: 'create_time_descend'
        },
        multipleSelection: [],
        downloadLoading: false,
        showList:true,
        showDetail:false,
        showDetailId:undefined
      }
    },
    created() {
      if (window.innerWidth < 700) {
        this.labelPosition = 'top'
      }
      this.getInitList()
    },
    methods: {
      handleDetail(row){
        this.showDetailId=row.id
        this.showDetail = true;
        this.showList=false;
      },
      handleAdopt(row){
        this.$confirm('确认通过审核？', '确认信息', {
          distinguishCancelAndClose: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(() => {
          let data={
            id:row.id,
            pdcBakId:row.pdcBakId
          }
          adopt(data).then(response => {
            if (response.message === 'success') {
              this.$message({
                message: '操作完成',
                type: 'success',
                duration: 2000
              })
              this.getInitList();
            } else {
              this.handleWarning(response)
            }
          })
        })
          .catch(action => {
          });
      },
      handleRefuse(row){
        this.$confirm('确认拒绝审核？', '确认信息', {
          distinguishCancelAndClose: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(() => {
          let data={
            id:row.id,
            pdcBakId:row.pdcBakId
          }
          refuse(data).then(response => {
            if (response.message === 'success') {
              this.$message({
                message: '操作完成',
                type: 'success',
                duration: 2000
              })
              this.getInitList();
            } else {
              this.handleWarning(response)
            }
          })
        })
          .catch(action => {
          });




      },
      getInitList() {
        this.getList()
      },
      getList() {
        this.listLoading = true
        queryExwaitList(this.listQuery).then(response => {
          if (response.message === 'success') {
            this.list = response.data.list
            this.total = response.data.pagination.total
            this.listLoading = false
          } else {
            this.handleWarning(response)
          }
        })
      },
      handleFilter() {
        this.listQuery.currentPage = 1
        this.getList()
      },
      handleReset() {
        this.listQuery.name = undefined
        this.listQuery.telephone = undefined
        this.listQuery.agestart = undefined
        this.listQuery.ageend = undefined
        this.listQuery.username = undefined
        this.handleFilter()
      },
      sortChange(data) {
        const { prop, order } = data
        if (prop === 'create_time') {
          this.sortByCreateTime(order)
        }
      },
      sortByCreateTime(order) {
        if (order === 'ascending') {
          this.listQuery.sorter = 'create_time_ascend'
        } else {
          this.listQuery.sorter = 'create_time_descend'
        }
        this.handleFilter()
      },
      handleSelectionChange(val) {
        this.multipleSelection = val
      },

      handleExport() {// 导出 Excel
        this.downloadLoading = true
        const listQueryData = this.handleCommonQuery()
        exportInv(qs.stringify(listQueryData)).then(response => {
          let myDate = new Date();
          let time = myDate.toLocaleDateString().split('/').join('-');
          saveAs(response,  '义诊列表-'+time+'.xlsx')
        })
        this.downloadLoading = false
      },
      handleCommonQuery() { // 导出的查询条件
        const listQueryData = {}
        listQueryData.name = this.listQuery.name
        listQueryData.telephone = this.listQuery.telephone
        listQueryData.agestart = this.listQuery.agestart
        listQueryData.ageend = this.listQuery.ageend
        listQueryData.username = this.listQuery.username
        listQueryData.status = this.listQuery.status
        return listQueryData
      },
      getSortClass: function(key) {
        const sorter = this.listQuery.sorter
        return sorter === `${key}_ascend` ? 'ascending' : 'descending'
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
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>

