package com.clin.system.service;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * <pre>
 * Title :  区域信息的业务逻辑接口层
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public interface SysRegionService {

	/**
	 * 查询区域的数据分页
	 *
	 * @param currentPage 当前页数
	 * @param pageSize    每页记录数
	 * @param regionName  区域名称
	 * @param regionCode  区域代码
	 * @param regionType  区域类型
	 * @return
	 */
	Map<String, Object> querySysRegion(Integer currentPage, Integer pageSize, String regionName, String regionCode, String regionType);

	/**
	 * 查询区域的树数据
	 *
	 * @return
	 */
	LinkedHashMap<String, Object> querySysRegionTree();

	/**
	 * 查询区域类型的下拉框数据
	 *
	 * @return
	 */
	LinkedHashMap<String, Object> queryRegionType();

//	/**
//	 * 查询区域的导出数据列表
//	 *
//	 * @param paramMap 参数Map
//	 * @return
//	 */
//	List<LinkedHashMap<String, Object>> querySysRegionForExcel(Map<String, Object> paramMap);
//
//	/**
//	 * 查询全部省份数据
//	 *
//	 * @return
//	 */
//	LinkedHashMap<String, Object> queryProvince();
//
//	/**
//	 * 根据省份代码查询对应地市数据
//	 *
//	 * @param province 省份代码
//	 * @return
//	 */
//	LinkedHashMap<String, Object> queryCity(String province);
//
//	/**
//	 * 新增区域
//	 *
//	 * @param sysRegion 区域对象
//	 */
//	void insertSysRegion(SysRegion sysRegion);
//
//	/**
//	 * 编辑区域
//	 *
//	 * @param sysRegion 区域对象
//	 */
//	void updateSysRegion(SysRegion sysRegion);
//
//	/**
//	 * 删除区域
//	 *
//	 * @param regionCode 区域代码
//	 */
//	void deleteSysRegion(String[] regionCode);
//
//	/**
//	 * 导入区域
//	 *
//	 * @param file 文件资源
//	 */
//	void importSysRegion(MultipartFile file);

}
