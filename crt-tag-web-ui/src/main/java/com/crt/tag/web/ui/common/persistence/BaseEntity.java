package com.crt.tag.web.ui.common.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@JsonIgnore
	private Integer pageNum;
	@JsonIgnore
	private Integer pageSize;
	@JsonIgnore
	private String search;
	@JsonIgnore
	private String sort;
	@JsonIgnore
	private String order;
	@JsonIgnore
	private Integer offset;
	@JsonIgnore
	private Integer limit;
	@JsonIgnore
	protected Map<String, String> sqlMap;

}
