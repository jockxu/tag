package com.crt.tag.web.ui.modules.tag.service;

import com.crt.tag.web.ui.common.service.CrudFactor;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagBeerMarketing;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagLabelCategory;
import com.github.pagehelper.Page;

import java.util.List;

public interface TagBeerMarketingService extends CrudFactor<TagBeerMarketing> {


     int insertList(List<TagBeerMarketing> list);
}
