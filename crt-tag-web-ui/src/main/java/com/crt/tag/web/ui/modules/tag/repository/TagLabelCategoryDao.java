package com.crt.tag.web.ui.modules.tag.repository;

import com.crt.tag.web.ui.common.persistence.CrudDao;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagLabelCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagLabelCategoryDao extends CrudDao<TagLabelCategory> {

}


