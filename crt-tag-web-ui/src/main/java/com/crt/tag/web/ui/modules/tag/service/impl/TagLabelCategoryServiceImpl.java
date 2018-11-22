package com.crt.tag.web.ui.modules.tag.service.impl;

import com.crt.tag.web.ui.common.service.CrudService;
import com.crt.tag.web.ui.modules.tag.repository.TagLabelCategoryDao;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagLabelCategory;
import com.crt.tag.web.ui.modules.tag.service.TagLabelCategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagLabelCategoryServiceImpl extends CrudService<TagLabelCategoryDao, TagLabelCategory>
		implements TagLabelCategoryService {


	@Autowired
	 TagLabelCategoryDao tagLabelCategoryDao;

	/**
	 * 分页查询
	 * @param tagLabelCategory
	 * @return
	 */
	@Override
	public Page<?> findList4Page(TagLabelCategory tagLabelCategory) {
		Page<?> page = PageHelper.startPage(tagLabelCategory.getPageNum(), tagLabelCategory.getPageSize(), true);
		List<TagLabelCategory> list = tagLabelCategoryDao.findList(tagLabelCategory);
		return page;
	}
}
