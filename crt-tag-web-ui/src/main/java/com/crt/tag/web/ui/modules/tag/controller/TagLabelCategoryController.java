package com.crt.tag.web.ui.modules.tag.controller;

import com.crt.tag.web.ui.common.constant.JsonResult;
import com.crt.tag.web.ui.common.enums.ResultCode;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagLabelCategory;
import com.crt.tag.web.ui.modules.tag.service.TagLabelCategoryService;
import com.github.pagehelper.Page;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tagLabelCategory")
@Log4j2
public class TagLabelCategoryController {

	@Autowired
	private TagLabelCategoryService tagLabelCategoryService;

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public JsonResult get(@PathVariable  Long id) {
		TagLabelCategory tagLabelCategory = tagLabelCategoryService.get(id);
		return new JsonResult(ResultCode.SUCCESS, tagLabelCategory);
	}

	@RequestMapping(value = "/findList", method = RequestMethod.GET)
	public JsonResult findList(TagLabelCategory tagLabelCategory) {
		List<TagLabelCategory> list = tagLabelCategoryService.findList(tagLabelCategory);
		return new JsonResult(ResultCode.SUCCESS, list);
	}

	@RequestMapping(value = "/findList4Page", method = RequestMethod.GET)
	public JsonResult findList4Page(TagLabelCategory tagLabelCategory) {
		Page<?> list4Page = tagLabelCategoryService.findList4Page(tagLabelCategory);
		Map<String, Object> bakMap = new HashMap<>();
		bakMap.put("total", list4Page.getTotal());
		bakMap.put("rows", list4Page.getResult());
		return new JsonResult(ResultCode.SUCCESS, bakMap);
	}

	@RequestMapping(value = "/findAllList", method = RequestMethod.GET)
	public JsonResult findListAll() {
		List<TagLabelCategory> allList = tagLabelCategoryService.findAllList();
		return new JsonResult(ResultCode.SUCCESS, allList);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResult save(TagLabelCategory tagLabelCategory) {
//		TagLabelCategory tagLabelCategory = new TagLabelCategory();
//        tagLabelCategory.setParentId(1);
//        tagLabelCategory.setCategoryCode("0005");
//        tagLabelCategory.setParentIds("0,1");
//        tagLabelCategory.setCategoryName("会员简历属性");
//        tagLabelCategory.setCategoryLevel(1L);
//         tagLabelCategory.setCategorySort(1L);
//         tagLabelCategory.setCategoryIcon("glyphicon-star");
//         tagLabelCategory.setCategoryRemark("会员简历属性");
    //      tagLabelCategory.setIsCategoryShow("1");
       //  tagLabelCategory.setIsCategoryLeaf("0");
        tagLabelCategory.setCreateUserId(1L);
         tagLabelCategory.setCreateTime(new Date());
         tagLabelCategory.setUpdateUserId(1L);
         tagLabelCategory.setUpdateTime(new Date());
//         tagLabelCategory.setCategoryType("2");
         int code = tagLabelCategoryService.save(tagLabelCategory);
         log.info("save  return value: {}",code);
         if(code<1)
			 return new JsonResult(ResultCode.PARAMS_ERROR);
		return new JsonResult(ResultCode.SUCCESS);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(TagLabelCategory tagLabelCategory) {
		 int code = tagLabelCategoryService.update(tagLabelCategory);
		if(code<1)
			return new JsonResult(ResultCode.PARAMS_ERROR);
		return new JsonResult(ResultCode.SUCCESS);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResult delete(TagLabelCategory tagLabelCategory) {
		int code = tagLabelCategoryService.delete(tagLabelCategory);
		if(code<1)
			return new JsonResult(ResultCode.PARAMS_ERROR);
		return new JsonResult(ResultCode.SUCCESS);
	}



}