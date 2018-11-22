package com.crt.tag.web.ui.modules.tag.repository.domain;

import com.crt.tag.web.ui.common.persistence.DataEntity;
import lombok.Data;

@Data
public class TagLabelCategory extends DataEntity<TagLabelCategory>{
	
	private static final long serialVersionUID = 1L;


	private Integer parentId;//父级ID
	private String categoryCode;//分类编号
	private String parentIds;//所有父级IDS
	private String categoryName;//名称
	private Long categoryLevel;//所处的树级别
	private Long categorySort;//排序
	private String categoryIcon;//图标
	private String categoryRemark;//备注信息
	private String isCategoryShow;//是否在菜单中显示
	private String isCategoryLeaf;//是否是子节点
	private String categoryType;//分类类型
	private String outterCode;//外部编码
	private Integer actionType;//操作类型（用于提供ES查询）
	private String actionDescBuName;//来源bu名称
	private String itemCode ;// 维度标识

	
}
