/**
 * 
 */
package util;

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * <p><b>Description:</b> POI相关工具类</p>
 * <p><b>Date:</b> 2016年6月29日下午4:35:22</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class POIUtil {
	public static String getCellValue(Cell cell){
		if(cell==null)
			return "";
		switch(cell.getCellType()){
			case Cell.CELL_TYPE_NUMERIC://代表数字0
				if(DateUtil.isCellDateFormatted(cell))//如果是日期格式
					return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cell.getDateCellValue());
				else
					return cell.getNumericCellValue()+"";
			case Cell.CELL_TYPE_STRING://代表数字1	
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_FORMULA://代表数字2
				return cell.getCellFormula();
			case Cell.CELL_TYPE_BLANK://代表数字3
				return "";
			case Cell.CELL_TYPE_BOOLEAN://代表数字4
				return cell.getBooleanCellValue()+"";
			default: //cell.CELL_TYPE_ERROR,代表数字5
				throw new RuntimeException("解析的文档格式错误");
		}
	}
}
