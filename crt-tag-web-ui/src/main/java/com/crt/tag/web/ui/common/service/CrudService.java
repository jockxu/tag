package com.crt.tag.web.ui.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.crt.tag.web.ui.common.persistence.CrudDao;
import com.crt.tag.web.ui.common.persistence.DataEntity;

@Transactional(readOnly = false)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService
        implements CrudFactor<T> {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(Long id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    public List<T> findAllList() {
        return dao.findAllList();
    }

    @Transactional(readOnly = false)
    public int save(T entity) {

        return dao.insert(entity);

    }

    @Transactional(readOnly = false)
    public int update(T entity) {

        return dao.update(entity);

    }
    @Transactional(readOnly = false)
    public int delete(T entity) {
        return dao.delete(entity);
    }

}
