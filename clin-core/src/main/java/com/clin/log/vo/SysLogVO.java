package com.clin.log.vo;


import com.clin.core.vo.CommonVO;

import java.io.Serializable;

/**
 * <pre>
 * Title :  日志
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public class SysLogVO extends CommonVO implements Serializable {

	private static final long serialVersionUID = 3956287435612937606L;

	private String oper_name;
	private String title;

	private String sort;
	private String sequence;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
}
