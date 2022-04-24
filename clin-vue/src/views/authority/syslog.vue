<template>
  <div class="app-container">
    <el-container>
      <div ref="mainDiv" style="width: 95%; overflow: scroll">
        <div class="filter-container">
          <span style="font-size: 14px; line-height: 30px;">操作账户：</span><el-input v-model="listQuery.oper_name" placeholder="请输入操作账户" clearable class="filter-input filter-item" style="width: 140px" @keyup.enter.native="handleFilter" />
          <span style="font-size: 14px; line-height: 30px;">模块标题：</span><el-input v-model="listQuery.title" placeholder="请输入模块标题" clearable class="filter-input filter-item" style="width: 140px" @keyup.enter.native="handleFilter" />
          <div style="float: right">
            <el-button v-waves type="primary" class="filter-item" icon="el-icon-search" @click="handleFilter">
              {{ $t('common.query') }}
            </el-button>
            <el-button v-waves class="filter-item" style="margin-left: 5px" icon="el-icon-refresh-right" @click="handleReset">
              {{ $t('common.reset') }}
            </el-button>
            <!--<el-button type="danger" plain class="filter-item" style="margin-left: 5px;" icon="el-icon-delete" @click="handleDelete">-->
              <!--{{ $t('common.delete') }}-->
            <!--</el-button>-->
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
          :row-style="{height:'30px'}"
          :cell-style="{padding:'0px'}"
          :header-cell-style="{padding:'0px'}"
          style="width: 100%"
          @sort-change="sortChange"
          @selection-change="handleSelectionChange"
        >
          <!--<el-table-column type="selection" width="40" />-->
          <el-table-column label="操作用户" prop="oper_name" min-width="80px" />
          <el-table-column label="主机IP" prop="oper_ip"  min-width="80px" />
          <el-table-column label="模块标题" prop="title"  min-width="120px" />
          <el-table-column :label="$t('sysuser.status')" class-name="status-col" min-width="60px">
            <template slot-scope="{row}">
              <el-tag :type="row.status | statusFilter">
                <span v-if="row.status === 1">异常</span>
                <span v-else>正常</span>
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="错误消息" prop="error_msg"  min-width="120px" />
          <el-table-column label="操作时间" min-width="120px" prop="oper_time" sortable="custom" :class-name="getSortClass('oper_time')">
            <template slot-scope="{row}">
              <span>{{ row.oper_time | formatDate }}</span>
            </template>
          </el-table-column>
          <!--<el-table-column :label="$t('common.operate')" align="center" min-width="80px">-->
            <!--<template slot-scope="{row,$index}">-->
              <!--<el-popover :ref="'popover-' + row.id" placement="top" width="160" :title="$t('common.confirm-delete')" trigger="click">-->
                <!--<div>-->
                  <!--<el-button size="mini" @click="$refs[`popover-` + row.id].doClose()">{{ $t('common.cancel') }}</el-button>-->
                  <!--<el-button type="primary" size="mini" @click="$refs[`popover-` + row.id].doClose();handleDelete(row,$index)">{{ $t('common.confirm') }}</el-button>-->
                <!--</div>-->
                <!--<el-button slot="reference" type="text" style="margin-left: 10px">-->
                  <!--<i class="el-icon-delete" />{{ $t('common.delete') }}-->
                <!--</el-button>-->
              <!--</el-popover>-->
            <!--</template>-->
          <!--</el-table-column>-->
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="getList" />

      </div>
    </el-container>
  </div>
</template>

<script>
import { queryLogData,deleteSysLog} from '@/api/system'
import waves from '@/directive/waves' // waves directive
import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import SysUrlComponent from '@/components/SysUrl'
import qs from 'qs'
import { saveAs } from 'file-saver'
import mammoth from 'mammoth'
import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'
export default {
  name: 'SysUser',
  computed: {
    ...mapGetters([
      'roles',
    ])
  },
  components: { Pagination,  'sysurl-component': SysUrlComponent },
  directives: { waves, elDragDialog },
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        0: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      tabMapOptions: [
        { label: this.$t('sysuserpost.postInfo'), key: 'sysuserpost' }
      ],
      activeName: 'sysuserpost',
      parentMsg: [],
      leftIcon: true,
      rightIcon: false,
      tableKey: 0,
      labelPosition: 'right',
      list: [],
      total: 0,
      singleMulti: undefined,
      listLoading: true,
      disabledStatus: false,
      listQuery: {
        currentPage: 1,
        pageSize: 10,
        oper_name: undefined,
        title:undefined,
        sorter: 'oper_time_descend'
      },
      roleList: [],
      grantRoleList: [],
      postCodeOptions: [],
      roleOptions: [],
      orgName: '',
      statusOptions: [
        { key: '1', display_name: this.$t('common.able') },
        { key: '0', display_name: this.$t('common.disable') }
      ],
      temp: {
        id: undefined,
        status: 1,
        prefix: '86'
      },
      multipleSelection: [],
      dialogFormVisible: false,
      authorizeDialogFormVisible: false,
      checkedButton: 0,
      roleNameCheckbox: [],
      authorizeUser:0,
      roleCode:undefined,
      // authorizeRoleDialogFormVisible: false,
      viewUrlDialogFormVisible: false,
      importDialogFormVisible: false,
      dataObj: { },
      elUploadHeaders: { 'Authorization': 'Bearer ' + getToken() },
      dialogStatus: '',
      textMap: {
        update: this.$t('common.edit'),
        create: this.$t('common.edit.vue')
      },
      rules: {
        username: [{ required: true, validator: this.checkUsername, trigger: ['blur', 'change'] }],
        password: [{ required: true, validator: this.checkPassword, trigger: ['blur', 'change'] }],
        mobile: [{ required: true, validator: this.checkMobile, trigger: ['blur', 'change'] }],
        status: [{ required: true }]
      },
      downloadLoading: false,
      advancedSearch: false
    }
  },
  created() {
    if (window.innerWidth < 700) {
      this.labelPosition = 'top'
    }
    this.getInitList()
    this.roleCode=this.roles[0];
  },
  methods: {
    getInitList() {
      this.getList()
    },
    getList() {
      this.listLoading = true
      queryLogData(this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.pagination.total
        this.listLoading = false
        this.$nextTick(() => {
          if (this.list && this.list.length > 0) {
            this.$refs.multipleTable.toggleRowSelection(this.list[this.list.length - 1], true)
          }
        })
      })
    },
    handleFilter() {
      this.listQuery.currentPage = 1
      // this.listQuery.oper_name = undefined
      this.getList()
    },
    handleReset() {
      this.listQuery.oper_name = undefined
      this.listQuery.title = undefined
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'oper_time') {
        this.sortByCreateTime(order)
      }
    },
    sortByCreateTime(order) {
      if (order === 'ascending') {
        this.listQuery.sorter = 'oper_time_ascend'
      } else {
        this.listQuery.sorter = 'oper_time_descend'
      }
      this.handleFilter()
    },
    handleSelectionChange(val) {
      if (val.length > 1) {
        this.$refs.multipleTable.clearSelection()
        this.$refs.multipleTable.toggleRowSelection(val.pop())
      } else {
        this.multipleSelection = val
        this.parentMsg = val
      }
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        status: 1,
        prefix: '86'
      }
    },
    handleDelete(row, index) {
      if (row.id) {
        this.delete(row.id)
      } else {
        if (this.multipleSelection.length === 0) {
          this.handleWarning(this.$t('common.choose-delete'))
          return
        }
        this.$confirm(this.$t('common.confirm-batch-delete'), this.$t('common.batch-delete'), {
          confirmButtonText: this.$t('common.confirm'),
          cancelButtonText: this.$t('common.cancel'),
          type: 'warning'
        }).then(() => {
          this.delete(this.multipleSelection.map(item => { return item.id }).join(','))
        }).catch(() => {})
      }
    },
    delete(deleteArray) {
      deleteSysLog(deleteArray).then(response => {
        if (response.message === 'success') {
          this.$message({
            message: this.$t('common.delete-success'),
            type: 'success',
            duration: 2000
          })
          this.handleReset()
        } else {
          this.handleWarning(response)
        }
      })
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
      this.disabledStatus = false
    }

  }
}
</script>
<style>
  .aside-table td,.aside-table th.is-leaf {
    border: none;
  }
  .aside-table::before {
    width: 0;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
  .allQueryClass input.el-input__inner {
    padding: 10px;
  }
</style>
<style scoped>
  .pagination-container {
    padding: 10px;
    margin: 0px;
  }
</style>
