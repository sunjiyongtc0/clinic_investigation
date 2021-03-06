package com.clin.system.mapper;


import com.clin.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  用户信息的数据持久接口层
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
@Mapper
public interface SysUserMapper {


	@Select(" SELECT   r.role_name  FROM  sys_role r INNER JOIN  sys_role_user ru ON r.id=ru.role_id WHERE ru.user_id = #{id} limit 1")
	String getRoleCodeByUserId(long id);
	/**
	 * 查询用户分页或导出数据
	 *
	 * @param paramMap 参数Map
	 * @return
	 */
	List<LinkedHashMap<String, Object>> querySysUser(Map<String, Object> paramMap);


	List<LinkedHashMap<String, Object>> queryMpSysUser(Map<String, Object> paramMap);
//
//	/**
//	 * 根据用户ID查询用户名的数据列表
//	 *
//	 * @param id 用户ID
//	 * @return
//	 */
//	List<LinkedHashMap<String, Object>> queryUsername(Long[] id);
//
//	/**
//	 * 根据用户名查询用户ID的数据列表
//	 *
//	 * @param username 用户名
//	 * @return
//	 */
//	List<Long> querySysUserId(String[] username);

	/**
	 * 查询是否已存在此用户名或邮箱或手机号
	 *
	 * @param username 用户名
	 * @param email    邮箱
	 * @param mobile   手机号
	 * @return
	 */
	Integer getSysUserByIdentification(String username,String mobile);

	/**
	 * 查询是否已存在此邮箱或手机号
	 *
	 * @param id     用户ID
	 * @param email  邮箱
	 * @param mobile 手机号
	 * @return
	 */
	Integer getSysUserByIdMobile(Long id,  String mobile);

	/**
	 * 根据机构ID查询用户ID
	 *
	 * @param orgId  机构ID
	 * @param roleId 角色ID
	 * @param assign 是否分配（0是未分配，1是已分配）
	 * @return
	 */
	List<String> queryUserIdByOrgId(Long orgId, Long roleId, Short assign);

	/**
	 * 获取用户名和昵称
	 *
	 * @return
	 */
	List<LinkedHashMap<String, Object>> queryUsernameNickname();

	/**
	 * 新增用户
	 *
	 * @param sysUser 用户对象
	 * @return
	 */
	int insertSysUser(SysUser sysUser);

	/**
	 * 编辑用户
	 *
	 * @param sysUser 用户对象
	 * @return
	 */
	int updateSysUser(SysUser sysUser);

	/**
	 * 删除用户
	 *
	 * @param id 用户ID
	 * @return
	 */
	int deleteSysUser(Long[] id);



	/**
	 * 修改用户状态
	 *
	 * @param id 用户ID
	 * @return
	 */
	int statusSysUser(Long[] id);

}
