<template>
  <div class="app-container">
    <div v-show="showList">
      <div class="filter-container">
        <span style="font-size: 14px; line-height: 30px;">患者姓名：</span>
        <el-input v-model="listQuery.name" placeholder="输入患者姓名" clearable class="filter-input" style="width: 125px" @keyup.enter.native="handleFilter"  maxlength="50" />
        <!--<span style="font-size: 14px; line-height: 30px;">患者手机号：</span>-->
        <!--<el-input v-model="listQuery.telephone" placeholder="输入患者手机号" clearable class="filter-input" style="width: 125px" @keyup.enter.native="handleFilter" maxlength="50" />-->
        <span style="font-size: 14px; line-height: 30px;">患者年龄：</span><el-input type="number" v-model="listQuery.agestart" placeholder="开始" clearable class="filter-input" style="width: 90px" @keyup.enter.native="handleFilter" maxlength="50" />
        - <el-input type="number" v-model="listQuery.ageend" placeholder="结束" clearable class="filter-input" style="width: 90px" @keyup.enter.native="handleFilter" maxlength="50" />
        <span style="font-size: 14px; line-height: 30px;">健康值范围：</span><el-input type="number" v-model="listQuery.scorestart" placeholder="开始" clearable class="filter-input" style="width: 90px" @keyup.enter.native="handleFilter" maxlength="50" />
        - <el-input type="number" v-model="listQuery.scoreend" placeholder="结束" clearable class="filter-input" style="width: 90px" @keyup.enter.native="handleFilter" maxlength="50" />
        <span style="font-size: 15px; line-height: 30px;">志愿者账号：</span>
        <el-input v-model="listQuery.username" placeholder="输入志愿者账号" clearable class="filter-input" style="width: 140px" @keyup.enter.native="handleFilter" maxlength="50" />
        <div style="float: right">
          <el-button v-waves type="primary" class="filter-item" icon="el-icon-search" @click="handleFilter">
            {{ $t('common.query') }}
          </el-button>
          <el-button v-waves class="filter-item" icon="el-icon-refresh-right" @click="handleReset">
            {{ $t('common.reset') }}
          </el-button>
          <el-button v-waves type="primary"  v-if="roles[0]!='ROLE_VOLUNTEER'" class="filter-item" icon="el-icon-refresh-right" @click="handleExport">
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
        <el-table-column label="录入时间" width="140px"  prop="create_time" sortable="custom" :class-name="getSortClass('create_time')">
            <template slot-scope="{row}">
              <span>{{ row.create_time | formatDate }}</span>
            </template>
          </el-table-column>
        <el-table-column label="健康值"  width="100px" prop="score" sortable="custom"  :class-name="getSortClass('score')" />
        <el-table-column label="志愿者账号" prop="username" />
        <el-table-column :label="$t('common.operate')" min-width="120px"  align="center">
          <template slot-scope="{row,$index}">
            <el-button type="text" @click="handleUpdate(row,1)">
              <i class="el-icon-edit-outline" />详情
            </el-button>
            <el-button type="text" :disabled="row.status==2"  @click="handleUpdate(row,2)">
              <i class="el-icon-edit-outline" />{{ $t('common.edit') }}
            </el-button>
            <el-popover :ref="'popover-' + row.id" placement="top" width="160" :title="$t('common.confirm-delete')" trigger="click">
              <div>
                <el-button size="mini" @click="$refs[`popover-` + row.id].doClose()">{{ $t('common.cancel') }}</el-button>
                <el-button type="primary" size="mini" @click="$refs[`popover-` + row.id].doClose();handleDelete(row,$index)">{{ $t('common.confirm') }}</el-button>
              </div>
              <el-button slot="reference" type="text" style="margin-left: 10px">
                <i class="el-icon-delete" />{{ $t('common.delete') }}
              </el-button>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="getList" />

    </div>
    <detail v-if="showDetail" :visible.sync="showDetail"></detail><!--详情-->
    <edit v-if="showEdit" :visible.sync="showEdit"></edit><!--详情-->
  </div>
</template>

<script>
  import { queryPdcInfos ,deleteInv,exportInv } from '@/api/investigation'
  import waves from '@/directive/waves' // waves directive
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination
  import qs from 'qs'
  import { saveAs } from 'file-saver'
  import mammoth from 'mammoth'
  import { getToken } from '@/utils/auth'
  import detail from './detail'
  import edit from './edit'
  import { mapGetters } from 'vuex'

  export default {
    name: 'OnlineSysUser',
    components: { Pagination , detail ,edit },
    directives: { waves },
    data() {
      return {
        tableKey: 0,
        labelPosition: 'right',
        list: [],
        total: 0,
        showDetailId:undefined,
        listLoading: true,
        listQuery: {
          currentPage: 1,
          pageSize: 10,
          name: undefined,
          telephone: undefined,
          agestart: undefined,
          ageend: undefined,
          scorestart: undefined,
          scoreend: undefined,
          username: undefined,
          sorter: ''
        },
        multipleSelection: [],
        downloadLoading: false,
        showList:true,
        showDetail:false,
        showEdit:false,
      }
    },
    computed: {
      ...mapGetters([
        'roles'
      ])
    },
    created() {
      if (window.innerWidth < 700) {
        this.labelPosition = 'top'
      }
      this.getInitList()
    },
    methods: {
      handleUpdate(row,type){//1查看,2编辑
        this.showDetailId=row.id
        if(type===2){
          this.showEdit = true;
        }else{
          this.showDetail = true;
        }
        this.showList=false;
      },
      back(){
        this.showDetail = false;
        this.showList=true;
        this.getList();
      },
      getInitList() {
        this.getList()
      },
      getList() {
        this.listLoading = true
        queryPdcInfos(this.listQuery).then(response => {
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
        this.listQuery.scorestart = undefined,
        this.listQuery.scoreend = undefined,
        this.listQuery.username = undefined
        this.handleFilter()
      },
      sortChange(data) {
        const { prop, order } = data
        // console.log(prop,order)
        if (prop === 'create_time') {
          this.sortByCreateTime(order)
        }
        if (prop === 'score') {
          this.sortByScore(order)
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
      sortByScore(order) {
        if (order === 'ascending') {
          this.listQuery.sorter = 'score_ascend'
        } else {
          this.listQuery.sorter = 'score_descend'
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
        listQueryData.scorestart = this.listQuery.scorestart
        listQueryData.scoreend = this.listQuery.scoreend
        listQueryData.username = this.listQuery.username
        listQueryData.status = this.listQuery.status
        return listQueryData
      },
      getSortClass: function(key) {
        const sorter = this.listQuery.sorter
        return sorter === `${key}_ascend` ? 'ascending' : 'descending'
      },
      handleDelete(row){
        deleteInv(row.id).then(response => {
          if (response.message === 'success') {
            this.getList()
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
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>

