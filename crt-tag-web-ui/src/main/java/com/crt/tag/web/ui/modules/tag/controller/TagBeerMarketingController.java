package com.crt.tag.web.ui.modules.tag.controller;

import com.crt.tag.web.ui.common.constant.JsonResult;
import com.crt.tag.web.ui.common.enums.ResultCode;
import com.crt.tag.web.ui.common.utils.ImportExcel;
import com.crt.tag.web.ui.modules.tag.repository.domain.TagBeerMarketing;
import com.crt.tag.web.ui.modules.tag.service.TagBeerMarketingService;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/beer/marketing")
@Log4j2
public class TagBeerMarketingController {

	@Autowired
	private TagBeerMarketingService tagBeerMarketingService;


	@RequestMapping(value = "/import")
	public JsonResult importData(MultipartFile inputFile) {
		String errorString = "";
		errorString = uploadExcel(inputFile);
		if (!"".equals(errorString)) {
			return new JsonResult(ResultCode.PARAMS_ERROR, errorString);
		} else {
			return new JsonResult(ResultCode.SUCCESS, "上传成功");
		}
	}

	/** 标签价格初始化数据导入 */
	private String uploadExcel(MultipartFile file) {
		String errorString = "";
		log.info("文件信息:" + new Gson().toJson(file));
		try {
			ImportExcel poi = new ImportExcel();
			List<List<String>> list = null;

			list = poi.read(file.getOriginalFilename(),file.getInputStream(), 0);
			if (poi.getErrorInfo() != null) {
			}

			try {
				if (list != null && list.size() > 0 ) {


						errorString = uploadTagPriceExcel(list);



				} else {
					errorString = "请上传包含内容的Excel文件";
				}
			} catch (Exception e) {
				log.error("", e);
				return "导入文件数据格式有误，请检查后重试";
			}
		} catch (IOException e) {
			log.error("", e);
			errorString = "网络不佳，请稍后再试！";
		}
		return errorString;
	}


	/** 导入标签价格excel数据 */
	public String uploadTagPriceExcel(List<List<String>> list) throws Exception {

		   List<String> cellList ;
			List<TagBeerMarketing> priceList = new ArrayList<>();

			for (int i = 0; i < list.size(); i++) {
				cellList = list.get(i);
				TagBeerMarketing tagBeerMarketing = new TagBeerMarketing();

				tagBeerMarketing.setDt(cellList.get(0).trim());
				tagBeerMarketing.setBuCode(cellList.get(1).trim());
				tagBeerMarketing.setShopCode(cellList.get(2).trim());
				tagBeerMarketing.setGoodsName(cellList.get(3).trim());
				tagBeerMarketing.setTotalItemValue(Double.parseDouble(cellList.get(4)));
				tagBeerMarketing.setTotalDiscValue(Double.parseDouble(cellList.get(5)));
               tagBeerMarketing.setDiscRate(Double.parseDouble(cellList.get(6)));
				tagBeerMarketing.setDiscLevel(cellList.get(7).trim());
				tagBeerMarketing.setOrderCnt(Integer.parseInt(cellList.get(8)));
				tagBeerMarketing.setMemberCnt(Integer.parseInt(cellList.get(9)));

				priceList.add(tagBeerMarketing);

			}
			log.info("标签价格导入数量" + priceList.size());
			// 分批次处理
			int count = 500;
			int times = 1;
			for (int i = 0; i < priceList.size(); i+=count) {
				int toIndex = i + count;
				if (priceList.size() < toIndex)
					toIndex = priceList.size();

				log.info("批量处理数据，第{}次处理索引:{}-{}", times++, i, toIndex);
				tagBeerMarketingService.insertList(priceList.subList(i, toIndex));
			}

		return "";
	}


}