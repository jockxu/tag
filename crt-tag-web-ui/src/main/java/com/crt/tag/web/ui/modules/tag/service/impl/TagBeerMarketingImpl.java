package com.crt.tag.web.ui.modules.tag.service.impl;

import com.crt.tag.web.ui.common.service.CrudService;
import com.crt.tag.web.ui.modules.tag.repository.TagBeerMarketingDao;
import com.crt.tag.web.ui.modules.tag.repository.TagLabelCategoryDao;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagBeerMarketing;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagLabelCategory;
import com.crt.tag.web.ui.modules.tag.service.TagBeerMarketingService;
import com.crt.tag.web.ui.modules.tag.service.TagLabelCategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagBeerMarketingImpl extends CrudService<TagBeerMarketingDao, TagBeerMarketing>
		implements TagBeerMarketingService {

	@Autowired
	TagBeerMarketingDao tagBeerMarketingDao;

	@Override
	public int insertList(List<TagBeerMarketing> list) {
		return tagBeerMarketingDao.insertList(list);
	}

}
