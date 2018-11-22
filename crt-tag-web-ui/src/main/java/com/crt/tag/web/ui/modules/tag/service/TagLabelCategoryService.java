package com.crt.tag.web.ui.modules.tag.service;

import com.crt.tag.web.ui.common.service.CrudFactor;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagLabelCategory;
import com.github.pagehelper.Page;

public interface TagLabelCategoryService extends CrudFactor<TagLabelCategory> {


     Page<?> findList4Page(TagLabelCategory userLog);
}
