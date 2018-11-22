package com.crt.tag.web.ui.modules.tag.repository.domain;

import com.crt.tag.web.ui.common.persistence.DataEntity;
import lombok.Data;

@Data
public class TagBeerMarketing extends DataEntity<TagBeerMarketing>{
	
	private static final long serialVersionUID = 1L;


	private String dt;
	private String buCode;
	private String shopCode;
	private String goodsName;
	private Double totalItemValue;
	private Double totalDiscValue;
	private Double discRate;
	private String discLevel;
	private Integer orderCnt;
	private Integer memberCnt;


}
