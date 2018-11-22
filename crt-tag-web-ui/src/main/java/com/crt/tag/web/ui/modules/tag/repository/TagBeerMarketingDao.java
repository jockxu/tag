package com.crt.tag.web.ui.modules.tag.repository;

import com.crt.tag.web.ui.common.persistence.CrudDao;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagBeerMarketing;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagLabelCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagBeerMarketingDao extends CrudDao<TagBeerMarketing> {

    int insertList(List<TagBeerMarketing> list);
}


