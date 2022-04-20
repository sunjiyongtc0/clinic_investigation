package com.clin.system.service.impl;

import com.clin.auth.utils.CurrentUserUtils;
import com.clin.core.result.PaginationBuilder;
import com.clin.core.utils.CollectionUtils;
import com.clin.core.utils.SequenceGenerator;
import com.clin.system.entity.SysUrl;
import com.clin.system.mapper.SysRoleMapper;
import com.clin.system.mapper.SysUrlMapper;
import com.clin.system.service.SysUrlService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * <pre>
 * Title :  接口信息的业务逻辑实现层
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-23
 **/
@Service
@Transactional
public class SysUrlServiceImpl implements SysUrlService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static SequenceGenerator sequenceGenerator = new SequenceGenerator();

	@Autowired
	private SysUrlMapper sysUrlMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;

	/**
	 * 查询接口分页
	 */
	@Override
	public Map<String, Object> querySysUrl(Integer currentPage, Integer pageSize, String url, String description, String menuCode, Long[] roleId,
										   String sorter) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("url", url);
		paramMap.put("description", description);
		paramMap.put("menuCode", menuCode);
		paramMap.put("roleId", roleId);
		if (StringUtils.isNotBlank(sorter)) {
			String sort = sorter.substring(0, sorter.lastIndexOf('_'));
			String sequence = "ascend".equals(sorter.substring(sorter.lastIndexOf('_') + 1)) ? "ASC" : "DESC";
			paramMap.put("sort", sort);
			paramMap.put("sequence", sequence);
		} else {
			paramMap.put("sort", "createTime");
			paramMap.put("sequence", "DESC");
		}
		Page<Object> page = PageHelper.startPage(currentPage, pageSize);
		List<LinkedHashMap<String, Object>> resultList = sysUrlMapper.querySysUrl(paramMap);

		String roleData = sysRoleMapper.queryRoleData("sysurl", CurrentUserUtils.getOAuth2AuthenticationDetailsInfo().get("name"));
		String[] roleDataArray = roleData == null ? null : roleData.split(",");
		if (roleDataArray != null && roleDataArray.length > 0) {// 处理数据权限
			return PaginationBuilder.buildResult(CollectionUtils.convertFilterList(resultList, roleDataArray), page.getTotal(), currentPage, pageSize);
		} else {
			return PaginationBuilder.buildResult(resultList, page.getTotal(), currentPage, pageSize);
		}
	}

	/**
	 * 根据角色ID查询对应的接口ID
	 */
	@Override
	public List<String> queryUrlIdByRoleId(Long roleId) {
		return sysUrlMapper.queryUrlIdByRoleId(roleId);
	}

//
//	/**
//	 * 查询接口的导出数据列表
//	 */
//	@Override
//	public List<LinkedHashMap<String, Object>> querySysUrlForExcel(Map<String, Object> paramMap) {
//		return sysUrlMapper.querySysUrl(paramMap);
//	}

	/**
	 * 新增接口
	 */
	@Override
	public void insertSysUrl(SysUrl sysUrl) {
		Integer existing = sysUrlMapper.getSysUrlByUrl(sysUrl.getUrl().trim());
		if (existing != null && existing > 0) {
			throw new IllegalArgumentException("接口已存在");
		}
		sysUrl.setId(sequenceGenerator.nextId());
		sysUrlMapper.insertSysUrl(sysUrl);
		logger.info("接口已新增： {}", sysUrl.getUrl());
	}

	/**
	 * 编辑接口
	 */
	@Override
	public void updateSysUrl(SysUrl sysUrl) {
		sysUrlMapper.updateSysUrl(sysUrl);
		logger.info("接口已编辑： {}", sysUrl.getUrl());
	}

	/**
	 * 删除接口
	 */
	@Override
	public void deleteSysUrl(Long[] id) {
		sysUrlMapper.deleteSysUrl(id);
	}

	/**
	 * 将接口授权给角色
	 */
	@Override
	public void insertUrlIdRoleId(Long urlId, Long[] roleId) {
		sysUrlMapper.deleteUrlRole(urlId);
		for (int i = 0; i < roleId.length; i++) {
			sysUrlMapper.insertUrlIdRoleId(Long.valueOf(sequenceGenerator.nextId()), urlId, roleId[i]);
		}
		// 刷新Redis里的缓存数据
		refreshRedis();
	}

	/**
	 * 根据接口ID查询对应的角色ID
	 */
	@Override
	public List<String> queryRoleIdByUrlId(Long urlId) {
		return sysUrlMapper.queryRoleIdByUrlId(urlId);
	}

	/**
	 * 将接口授权给角色
	 */
	@Override
	public void insertRoleIdUrlId(Long[] urlId, Long roleId) {
		sysUrlMapper.deleteRoleUrl(roleId);
		for (int i = 0; i < urlId.length; i++) {
			sysUrlMapper.insertUrlIdRoleId(Long.valueOf(sequenceGenerator.nextId()), urlId[i], roleId);
		}
		refreshRedis();
	}

	/**
	 * 刷新Redis里的缓存数据
	 */
	private void refreshRedis() {
		List<String> roleCodeList = sysRoleMapper.queryRoleCodeList();
		for (int i = 0; i < roleCodeList.size(); i++) {
			String roleCode = roleCodeList.get(i);
			List<String> url = sysUrlMapper.queryRoleUrl(roleCode);
//			redisUtils.psetex(ApplicationConstants.URL_ROLECODE_PREFIX + roleCode, url == null ? Collections.emptyList().toString() : url.toString());
		}
	}

}
