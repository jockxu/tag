package com.crt.tag.web.ui.common.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
//@JsonIgnoreProperties({"createTime", "updateTime"})
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
    private static final  String DATA_STATE_ENABLED="1";  // 数据可用
	private static final  String DATA_STATE_DISABLED="0"; // 数据不可用
	@JsonIgnore
	protected String dataState =DATA_STATE_ENABLED; // 数据状态
	@JsonIgnore
	protected Long createUserId; // 创建者ID

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createTime; // 数据创建日期
	@JsonIgnore
	protected Long updateUserId; // 更新者ID

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date updateTime; // 数据更新日期



}
