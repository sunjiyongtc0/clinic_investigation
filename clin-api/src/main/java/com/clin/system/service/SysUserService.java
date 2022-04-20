package com.clin.system.service;

import com.clin.system.entity.SysUser;
import com.clin.system.vo.SysUserVO;

import java.util.Map;

/**
 * <pre>
 * Title :  用户信息的业务逻辑接口层
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public interface SysUserService {

	String getRoleCodeByUserId(long id);


	/**
	 * 查询用户分页
	 *
	 * @param currentPage 当前页数
	 * @param pageSize    每页记录数
	 * @param username    用户名
	 * @param status      状态
	 * @param sorter      排序
	 * @return
	 */
//	Map<String, Object> querySysUser(Integer currentPage, Integer pageSize, String username, String status, String sorter);


	Map<String, Object> querySysUser(SysUserVO sysUserVO);


	Map<String, Object> queryMpSysUser( SysUserVO sysUserVO,String role_code);





//	/**
//	 * 根据用户名查询用户ID的数据列表
//	 *
//	 * @param username 用户名
//	 * @return
//	 */
//	List<Long> querySysUserId(String[] username);

//	/**
//	 * 查询在线用户分页
//	 *
//	 * @param currentPage 当前页数
//	 * @param pageSize    每页记录数
//	 * @param username    用户名
//	 * @param sorter      排序
//	 * @return
//	 */
//	Map<String, Object> queryOnlineSysUser(Integer currentPage, Integer pageSize, String username, String sorter);

//	/**
//	 * 根据参数查询用户列表
//	 *
//	 * @param paramMap 参数Map
//	 * @return
//	 */
//	List<LinkedHashMap<String, Object>> querySysUser(Map<String, Object> paramMap);
//
//	/**
//	 * 查询用户的导出数据列表
//	 *
//	 * @param paramMap 参数Map
//	 * @return
//	 */
//	List<LinkedHashMap<String, Object>> querySysUserForExcel(Map<String, Object> paramMap);
//
//	/**
//	 * 根据用户ID查询用户名的数据列表
//	 *
//	 * @param id 用户ID
//	 * @return
//	 */
//	List<LinkedHashMap<String, Object>> queryUsername(Long[] id);


	/**
	 * 新增用户
	 *
	 * @param sysUser 用户对象
	 */
	void insertSysUser(SysUser sysUser);

	/**
	 * 新增用户
	 *
	 * @param sysUser 用户对象
	 */
	void insertSysUserAndRole(SysUser sysUser);

	/**
	 * 将对应的角色授予用户
	 *
	 * @param roleId   角色ID
	 * @param userId   用户ID
	 * @param postCode 岗位编码
	 */
	void insertRoleIdUserId(Long[] roleId, Long userId );

	/**
	 * 编辑用户
	 *
	 * @param sysUser 用户对象
	 */
	void updateSysUser(SysUser sysUser);


	/**
	 * 编辑用户别名
	 *
	 * @param sysUser 用户对象
	 */
	void updateNickName(SysUser sysUser);

	/**
	 * 删除用户
	 *
	 * @param id 用户ID
	 */
	void deleteSysUser(Long[] id);


	void statusSysUser(Long[] id);

}
