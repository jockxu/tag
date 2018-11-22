package com.crt.tag.web.ui.common.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
public class ImportExcel {

	

	/** 总行数 */
	private int totalRows = 0;
	/** 总列数 */
	private int totalCells = 0;
	/** 错误信息 */
	private String errorInfo;

	/** 构造方法 */
	public ImportExcel() {

	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getTotalCells() {
		return totalCells;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public boolean validateExcel(String filePath) {
		/** 检查文件名是否为空或者是否是Excel格式的文件 */
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
			errorInfo = "文件名不是excel格式";
			return false;
		}
		/** 检查文件是否存在 */
		// File file = new File(filePath);
		// if (file == null || !file.exists()) {
		// errorInfo = "文件不存在";
		// return false;
		// }
		return true;
	}

	public List<List<String>> read(String filePath, Integer sheetIndex) {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		InputStream is = null;
		try {
			/** 验证文件是否合法 */
			if (!validateExcel(filePath)) {
				log.error(errorInfo);
				return null;
			}
			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (isExcel2007(filePath)) {
				isExcel2003 = false;
			}
			/** 调用本类提供的根据流读取的方法 */
			File file = new File(filePath);
			is = new FileInputStream(file);
			dataLst = read(is, isExcel2003, sheetIndex);
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					log.error(e.getMessage(), e);
				}
			}
		}
		/** 返回最后读取的结果 */
		return dataLst;
	}

	public List<List<String>> read(String filePath, InputStream inputStream, Integer sheetIndex) {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		InputStream is = null;
		try {
			/** 验证文件是否合法 */
			if (!validateExcel(filePath)) {
				log.error(errorInfo);
				return null;
			}
			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (isExcel2007(filePath)) {
				isExcel2003 = false;
			}
			/** 调用本类提供的根据流读取的方法 */
			is = inputStream;
			dataLst = read(is, isExcel2003, sheetIndex);
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					log.error(e.getMessage(), e);
				}
			}
		}
		/** 返回最后读取的结果 */
		return dataLst;
	}

	public List<List<String>> read(InputStream inputStream, boolean isExcel2003, Integer sheetIndex) {
		List<List<String>> dataLst = null;
		try {
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			if (isExcel2003) {
				wb = new HSSFWorkbook(inputStream);
			} else {
				wb = new XSSFWorkbook(inputStream);
			}
			dataLst = read(wb, sheetIndex);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return dataLst;
	}

	private List<List<String>> read(Workbook wb, Integer sheetIndex) {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		/** 默认得到第一个shell */
		sheetIndex = sheetIndex == null ? 0 : sheetIndex;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		/** 得到Excel的行数 */
		this.totalRows = sheet.getPhysicalNumberOfRows();
		/** 得到Excel的列数 */
		if (this.totalRows >= 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		/** 循环Excel的行 */
		for (int r = 0; r < this.totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			List<String> rowLst = new ArrayList<>();
			/** 循环Excel的列 */
			for (int c = 0; c < this.getTotalCells(); c++) {
				Cell cell = row.getCell(c);
				String value = "";
				if (null != cell) {

					switch (cell.getCellType()) {
					// 数值型
					case Cell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							// 如果是date类型则 ，获取该cell的date值
							Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = format.format(date);
						} else {// 纯数字
							BigDecimal big = new BigDecimal(cell.getNumericCellValue());
							value = big.toString();
							// 解决1234.0 去掉后面的.0
							if (null != value && !"".equals(value.trim())) {
								String[] item = value.split("[.]");
								if (1 < item.length && "0".equals(item[1])) {
									value = item[0];
								}
							}
						}
						break;
					// 字符串类型
					case Cell.CELL_TYPE_STRING:
						value = cell.getStringCellValue().toString();
						break;
					// 公式类型
					case Cell.CELL_TYPE_FORMULA:
						// 读公式计算值
						try {
							value = String.valueOf(cell.getNumericCellValue());
							if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
								value = cell.getStringCellValue().toString();
							}
						} catch (IllegalStateException e) {
							value = String.valueOf(cell.getRichStringCellValue());
							log.debug("===========excel单元格设定计算公式获取计算结果错误，原值：{}，表达式：{}", value,
									cell.getCellFormula());
						}
						break;
					// 布尔类型
					case Cell.CELL_TYPE_BOOLEAN:
						value = " " + cell.getBooleanCellValue();
						break;
					// 空值
					case Cell.CELL_TYPE_BLANK:
						value = "";
						log.error("excel出现空值");
						break;
					// 故障
					case Cell.CELL_TYPE_ERROR:
						value = "";
						log.error("excel出现故障");
						break;
					default:
						value = cell.getStringCellValue().toString();
					}
				}
				rowLst.add(value);
			}
			/** 保存第r行的第c列 */
			dataLst.add(rowLst);
		}
		return dataLst;
	}

	public boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	public boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	public static void main(String[] args) throws Exception {
		ImportExcel poi = new ImportExcel();
		List<List<String>> list = poi.read("E:/标签价格框架.xlsx", 2);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				System.out.print("第" + (i) + "行");
				List<String> cellList = list.get(i);
				for (int j = 0; j < cellList.size(); j++) {
					// 列值
					System.out.print("    " + cellList.get(j));
				}
				System.out.println();
			}
		}
	}
}
