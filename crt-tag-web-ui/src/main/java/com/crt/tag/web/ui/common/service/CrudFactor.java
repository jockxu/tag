package com.crt.tag.web.ui.common.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface CrudFactor<T> {
	
	 T get(Long id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	 T get(T entity);
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	 List<T> findList(T entity);
	
	 List<T> findAllList();
	
	@Transactional(readOnly = false)
	 int save(T entity);
	@Transactional(readOnly = false)
	 int update(T entity);
	@Transactional(readOnly = false)
	 int delete(T entity);

}
