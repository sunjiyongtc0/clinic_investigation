<template>
  <div class="app-container">
    <el-container>
      <div ref="mainDiv" style="width: 95%; overflow: scroll">
        <div class="filter-container">
          <span style="font-size: 14px; line-height: 30px;">登录账号：</span><el-input v-model="listQuery.username" placeholder="请输入登录账号" clearable class="filter-input filter-item" style="width: 140px" @keyup.enter.native="handleFilter" />
          <div style="float: right">
            <el-button v-waves type="primary" class="filter-item" icon="el-icon-search" @click="handleFilter">
              {{ $t('common.query') }}
            </el-button>
            <el-button v-waves class="filter-item" style="margin-left: 5px" icon="el-icon-refresh-right" @click="handleReset">
              {{ $t('common.reset') }}
            </el-button>
            <!--<el-button v-waves type="primary" class="filter-item" style="margin-left: 5px" icon="el-icon-search" @click="clickSwitch">-->
              <!--{{ $t('common.advanced-search') }}-->
            <!--</el-button>-->
            <el-button type="primary" class="filter-item" style="margin-left: 5px;padding-right: 5px;" icon="el-icon-plus" @click="handleCreate">
             新建账号
            </el-button>
          </div>
          <div v-if="advancedSearch" style="float: left">
            <span style="font-size: 14px; line-height: 30px;">账号名称：</span><el-input v-model="listQuery.nickname" placeholder="请输入账号名称" clearable class="filter-input filter-item" style="width: 140px" @keyup.enter.native="handleFilter" />
            <span style="font-size: 14px; line-height: 30px;">手机号码：</span><el-input v-model="listQuery.mobile" placeholder="请输入手机号码" clearable class="filter-input filter-item" style="width: 140px" @keyup.enter.native="handleFilter" />
            <!--<el-select v-model="listQuery.roleId" :placeholder="$t('sysuser.roleId')" clearable class="filter-input filter-item" style="width: 150px">-->
              <!--<el-option v-for="item in roleOptions" :key="item.value" :label="item.name" :value="item.value" />-->
            <!--</el-select>-->
            <span style="font-size: 14px; line-height: 30px;">状态：</span><el-select v-model="listQuery.status" :placeholder="$t('sysuser.status')" clearable class="filter-input filter-item" style="width: 100px">
              <el-option v-for="item in statusOptions" :key="item.key" :label="item.display_name" :value="item.key" />
            </el-select>
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
          :filter-method="filterHandler"
          style="width: 100%"
          @sort-change="sortChange"
          @selection-change="handleSelectionChange"
        >
          <!--<el-table-column type="selection" width="40" />-->
          <el-table-column label="账号名称" prop="nickname" />
          <el-table-column label="登录账号" prop="username" />
          <el-table-column label="手机号码" prop="mobile" min-width="90px" />
          <!--<el-table-column label="账户角色" prop="roleIdCn" />-->
          <el-table-column
            prop="roleIdCn"
            label="账户角色"
            width="100"
            :filter-method="filterTag"
            :filters="[{ text: '志愿者', value: '志愿者' }, { text: '义诊负责人', value: '义诊负责人' }, { text: '管理员', value: '管理员' }]"
            filter-placement="bottom-end">
            <template slot-scope="scope">
              <span>{{ scope.row.roleIdCn }}</span>
            </template>
          </el-table-column>

          <el-table-column label="录入时间" min-width="120px" prop="createTime" sortable="custom" :class-name="getSortClass('createTime')">
            <template slot-scope="{row}">
              <span>{{ row.createTime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column :label="$t('sysuser.status')" class-name="status-col">
            <template slot-scope="{row}">
              <el-tag :type="row.status | statusFilter">
                <span v-if="row.status === 1">{{ $t('common.able') }}</span>
                <span v-else>{{ $t('common.disable') }}</span>
              </el-tag>
            </template>
          </el-table-column>

          <el-table-column :label="$t('common.operate')" align="center" min-width="200px">
            <template slot-scope="{row,$index}">
              <el-button type="text"  v-if="roleCode==='ROLE_EAC_ADMIN'"  @click="handleUpdate(row)">
                <i class="el-icon-edit-outline" />{{ $t('common.edit') }}
              </el-button>
              <el-button type="text" @click="handleDetail(row)">
                <i class="el-icon-edit-outline" />详情
              </el-button>
              <el-popover :ref="'popover-' + row.id" placement="top" width="160" title="确定[停用/启用]此用户" trigger="click">
                <div>
                  <el-button size="mini" @click="$refs[`popover-` + row.id].doClose()">{{ $t('common.cancel') }}</el-button>
                  <el-button type="primary" size="mini" @click="$refs[`popover-` + row.id].doClose();handleDelete(row,$index)">{{ $t('common.confirm') }}</el-button>
                </div>
                <el-button slot="reference" type="text" style="margin-left: 10px">
                  <i class="el-icon-delete" />{{row.status==1?'停用':'启用'}}
                </el-button>
              </el-popover>
              <el-button type="text" @click="handleAuthorizeRole(row)" style="margin-left: 10px">
                <i class="el-icon-edit-outline" />账户授权
              </el-button>
              <el-popover  v-if="roleCode==='ROLE_EAC_ADMIN'"   :ref="'mypopover-' + row.id" placement="top" width="160" title="确定重置密码" trigger="click">
                <div>
                  <el-button size="mini" @click="$refs[`mypopover-` + row.id].doClose()">{{ $t('common.cancel') }}</el-button>
                  <el-button type="primary" size="mini" @click="$refs[`mypopover-` + row.id].doClose();retrievePassword(row,$index)">{{ $t('common.confirm') }}</el-button>
                </div>
                <el-button slot="reference" type="text" style="margin-left: 10px">
                  <i class="el-icon-delete" />重置密码
                </el-button>
              </el-popover>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="getList" />

      </div>
    </el-container>

    <el-dialog v-el-drag-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" :label-position="labelPosition" label-width="90px" style="width: 100%">
        <el-form-item label="账号名称" prop="nickname">
          <el-input v-model="temp.nickname"  :placeholder="$t('common.enter')" minlength="3" maxlength="64" clearable :disabled="dialogStatus==='detail'"  />
        </el-form-item>
        <el-form-item label="登录账号" prop="username">
          <el-input v-model="temp.username" :placeholder="$t('common.enter')" :disabled="dialogStatus!='create'" minlength="3" maxlength="64" clearable />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='create'" label="账号密码" prop="password">
          <el-input v-model="temp.password" :placeholder="$t('common.enter')" show-password minlength="6" maxlength="31" clearable />
        </el-form-item>

        <el-form-item label="手机号码">
          <el-row>
            <el-col :span="24" :xs="24">
              <el-form-item prop="mobile">
                <el-input v-model="temp.mobile" :placeholder="$t('common.enter')" class="input-with-select" maxlength="20" clearable :disabled="dialogStatus==='detail'"  />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form-item>

        <el-form-item :label="$t('sysuser.status')" prop="status" :disabled="dialogStatus==='detail'" >
          <el-radio-group v-model="temp.status">
            <el-radio :label="1" :disabled="dialogStatus==='detail'">{{ $t('common.able') }}</el-radio>
            <el-radio :label="0" :disabled="dialogStatus==='detail'">{{ $t('common.disable') }}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item v-if="dialogStatus!='update'"  label="账号权限" prop="roleId" :disabled="dialogStatus==='detail'" >
          <el-radio-group v-if="dialogStatus==='create'"  v-model="checkedButton"  @change="toChange">
              <el-radio v-for="item in roleNameCheckbox" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
          </el-radio-group>
          <el-radio-group v-else v-model="temp.roleId[0]" @change="toChange"  :disabled="dialogStatus==='detail'">
              <el-radio v-for="item in roleNameCheckbox" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
          </el-radio-group>
            <div style="padding: 10px; font-size: 14px; color: #999999;" v-if="dialogStatus!='detail'"  >{{roleRule}}</div>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t('common.cancel') }}
        </el-button>
        <el-button type="primary" :disabled="disabledStatus" @click="dialogStatus==='create'?createData():(dialogStatus==='update'?updateData():dialogFormVisible = false)">
          {{ $t('common.confirm') }}
        </el-button>
      </div>
    </el-dialog>


    <el-dialog v-el-drag-dialog :title="$t('common.authorize')" :visible.sync="authorizeDialogFormVisible">
      <el-radio-group v-model="checkedButton" @change="toChange">
        <el-radio v-for="item in roleNameCheckbox" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
      </el-radio-group>
      <div style="padding: 10px; font-size: 14px; color: #999999;">{{roleRule}}</div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="authorizeDialogFormVisible = false">
          {{ $t('common.cancel') }}
        </el-button>
        <el-button type="primary" @click="createAuthorize()">
          {{ $t('common.confirm') }}
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { queryRoleNameCheckbox,querySysUser, addSysUser, insertSysUserAndRole,updateSysUser, queryRoleName,  deleteSysUser, exportSysUser, authorizeRoleToUser } from '@/api/system'
import { retrievePassword } from '@/api/user'
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
        username: undefined,
        nickname: undefined,
        mobile: undefined,
        roleId: undefined,
        status: undefined,
        orgId: undefined,
        orgName: undefined,
        sorter: 'createTime_descend'
      },
      // allListQuery: {
      //   currentPage: 1,
      //   pageSize: 10000
      // },
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
        status: 1
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
      dialogStatus: 'create',
      textMap: {
        update: this.$t('common.edit'),
        create: '新建'
      },
      rules: {
        nickname: [{ required: true, validator: this.checkNickname, trigger: ['blur', 'change'] }],
        username: [{ required: true, validator: this.checkUsername, trigger: ['blur', 'change'] }],
        password: [{ required: true, validator: this.checkPassword, trigger: ['blur', 'change'] }],
        mobile: [{ required: true, validator: this.checkMobile, trigger: ['blur', 'change'] }],
        status: [{ required: true }]
      },
      downloadLoading: false,
      advancedSearch: true,
      roleRule:'',
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
    clickSwitch() {
      this.advancedSearch = !this.advancedSearch
    },
    getInitList() {
      queryRoleNameCheckbox().then(response => {
        this.roleNameCheckbox = response.data;
      })
      queryRoleName().then(response => {
        this.roleOptions = response.data
      })
      this.getList()
    },
    getList() {
      this.listLoading = true
      querySysUser(this.listQuery).then(response => {
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
    handleLeftIcon() {
      this.$refs.asideDiv.style.width = '10px'
      this.$refs.asideDiv.style.minWidth = '10px'
      this.$refs.mainDiv.style.width = '98%'
      this.leftIcon = false
      this.rightIcon = true
    },
    handleRightIcon() {
      this.$refs.asideDiv.style.width = '120px'
      this.$refs.asideDiv.style.minWidth = '120px'
      this.$refs.mainDiv.style.width = '88%'
      this.leftIcon = true
      this.rightIcon = false
    },
    handleFilter() {
      this.listQuery.currentPage = 1
      this.listQuery.orgId = undefined
      this.listQuery.orgName = undefined
      this.getList()
    },
    handleReset() {
      this.listQuery.username = undefined
      this.listQuery.nickname = undefined
      this.listQuery.mobile = undefined
      this.listQuery.roleId = undefined
      this.listQuery.status = undefined
      this.listQuery.orgId = undefined
      this.listQuery.orgName = undefined
      this.$refs.multipleTable.clearFilter();
      this.getList()
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    filterTag(value, row) {
      return row.roleIdCn === value;
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'createTime') {
        this.sortByCreateTime(order)
      }
    },
    sortByCreateTime(order) {
      if (order === 'ascending') {
        this.listQuery.sorter = 'createTime_ascend'
      } else {
        this.listQuery.sorter = 'createTime_descend'
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
        status: 1
      }
    },
    handleCreate() {
      this.resetTemp()
      this.orgName = undefined
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.disabledStatus = true
          let checkedButton=this.checkedButton;
          if(checkedButton===undefined||checkedButton===''||checkedButton===0){
            checkedButton=this.roleNameCheckbox[0].value;
            console.log(checkedButton)
          }
          this.temp.roleId =[checkedButton];
          // this.temp.roleId =[this.checkedButton];
          // addSysUser(this.temp).then(response => {
            insertSysUserAndRole(this.temp).then(response => {
            if (response.message === 'success') {
              this.list.unshift(this.temp)
              this.disabledStatus = false
              this.dialogFormVisible = false
              this.$message({
                message: this.$t('common.add-success'),
                type: 'success',
                duration: 2000
              })
              this.handleReset()
            } else {
              this.handleWarning(response)
            }
          })
        }
      })
    },
    handleDetail(row) {
      this.temp = Object.assign({}, row) // copy obj
      if (row.roleId) {
        this.temp.roleId = row.roleId.split(',');
      }
      this.dialogStatus = 'detail';
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      if (row.roleId) {
        this.temp.roleId = row.roleId.split(',')
      }
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // const tempData = Object.assign({}, this.temp)
          const tempData = {}
          tempData.id = this.temp.id
          tempData.username = this.temp.username
          tempData.nickname = this.temp.nickname
          tempData.mobile = this.temp.mobile
          tempData.prefix = this.temp.prefix
          tempData.roleId = this.temp.roleId
          tempData.orgId = this.temp.orgId
          tempData.status = this.temp.status
          this.disabledStatus = true
          updateSysUser(tempData).then(response => {
            if (response.message === 'success') {
              this.disabledStatus = false
              this.dialogFormVisible = false
              this.$message({
                message: this.$t('common.edit-success'),
                type: 'success',
                duration: 2000
              })
              this.handleReset()
            } else {
              this.handleWarning(response)
            }
          })
        }
      })
    },
    hideSysUrlComponent(e) {
      if (e === 1) {
        this.viewUrlDialogFormVisible = false
      }
    },
    sysUrlComponentMsg(e) {
      const urlArray = []
      for (const data of e) {
        urlArray.push(data.id)
      }
    },
    toChange(val){
      let msg="";
      this.roleNameCheckbox.forEach((row)=>{
        if(row.value===val){
          msg=row.label;
        }
      });
      if(msg==='志愿者'){
        this.roleRule = '志愿者权限，可以进行数据的录入、数据修改申请等';
      }else if(msg==='义诊负责人'){
        this.roleRule = '义诊负责人权限，可以进行数据的录入、数据修改申请等';
      }else if(msg==='管理员'){
        this.roleRule = '管理员权限，可以进行数据的录入、数据修改申请等';
      }
    },
    handleAuthorizeRole(row) {
      this.authorizeUser=row.id;
      this.checkedButton=row.roleId;
      this.toChange(this.checkedButton);
      this.authorizeDialogFormVisible = true
    },
    createAuthorize() {
      var authdata={
        roleId :[this.checkedButton],
        userId:this.authorizeUser
      };
      authorizeRoleToUser(authdata).then(response => {
        if (response.message === 'success') {
          this.authorizeDialogFormVisible = false
          this.$message({
            message: this.$t('common.authorize-success'),
            type: 'success',
            duration: 2000
          })
          this.handleReset()
        } else {
          this.handleWarning(response)
        }
      })

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
      deleteSysUser(deleteArray).then(response => {
        if (response.message === 'success') {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 2000
          })
          this.handleReset()
        } else {
          this.handleWarning(response)
        }
      })
    },
    uploadData() {
      if (this.$refs.upload.uploadFiles.length === 0) {
        this.handleWarning(this.$t('file.isnotempty'))
        return
      }
      this.$refs.upload.submit()
    },
    handleSuccess(response, file, fileList) {
      if (response.status !== 200) {
        this.handleWarning(response.message)
        this.$refs.upload.clearFiles()
      } else {
        this.$message({
          message: this.$t('common.import') + this.$t('common.success'),
          type: 'success',
          duration: 2000
        })
        this.importDialogFormVisible = false
        this.handleReset()
      }
    },
    handleExport(command) {
      if (command === 'excel') {
        this.handleDownload(0)
      }
    },
    handleCommonQuery() { // 导出的查询条件
      const listQueryData = {}
      listQueryData.username = this.listQuery.username
      listQueryData.nickname = this.listQuery.nickname
      listQueryData.mobile = this.listQuery.mobile
      listQueryData.roleId = this.listQuery.roleId
      listQueryData.status = this.listQuery.status
      return listQueryData
    },
    handleDownload(isTemplate) { // 导出 Excel
      this.downloadLoading = true
      const listQueryData = this.handleCommonQuery()
      listQueryData.isTemplate = isTemplate // 1为模板，0不为模板
      exportSysUser(qs.stringify(listQueryData)).then(response => {
        saveAs(response, this.$t('route.sysuser') + '.xlsx')
      })
      this.downloadLoading = false
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
    },
    checkUsername(rule, value, callback) {
      if (!value) {
        return callback(new Error('请输入登录账号'))
      } else {
        if (value.length >= 2 && value.length < 12) {
          callback()
        } else {
          return callback(new Error('登录账号长度最小为2最大为11！'))
        }
      }
    },
    checkNickname(rule, value, callback) {
      if (!value) {
        return callback(new Error('请输入账号名称'))
      } else {
        if (value.length >= 2 && value.length < 12) {
          callback()
        } else {
          return callback(new Error('账号名称长度最小为2最大为11！'))
        }
      }
    },
    checkPassword(rule, value, callback) {
      if (!value) {
        return callback(new Error(this.$t('sysuser.password-required')))
      } else {
        if (value.length > 5 && value.length < 18) {
          callback()
        } else {
          return callback(new Error('密码长度最小为6最大为18！'))
        }
      }
    },
    checkMobile(rule, value, callback) {
      if (!value) {
        return callback(new Error(this.$t('sysuser.mobile-required')))
      } else {
        const reg = /^1\d{10}$/
        if (reg.test(value)) {
          callback()
        } else {
          return callback(new Error(this.$t('sysuser.mobile-format')))
        }
      }
    },
    retrievePassword(row,index){

      retrievePassword(row.id).then(response => {
        if (response.message === 'success') {
          this.$message({
            message: this.$t('common.edit-success'),
            type: 'success',
            duration: 2000
          })
        } else {
          this.handleWarning(response)
        }
      })
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
