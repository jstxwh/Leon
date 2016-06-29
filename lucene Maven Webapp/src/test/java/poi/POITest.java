/**
 * 
 */
package poi;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * <p><b>Description:</b> POI相关API</p>
 * <p><b>Date:</b> 2016年6月29日上午11:29:52</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
public class POITest {
	private static Workbook wb;
	@Before
	public void before() throws IOException{
		wb=new XSSFWorkbook("D:\\Personal\\资料.xlsx");
	}
	/**
	 * <p><b>Description:</b> 简单的遍历Excel表格操作，后缀为xlsx</p>
	 */
	@Test
	public void listExcel(){
		int numOfSheet = wb.getNumberOfSheets();
		System.out.println(numOfSheet);//查看有多少个Sheet
		Sheet sheet = wb.getSheet("PT");
		Row row = sheet.getRow(12);//指定遍历某一行数据
		Iterator<Cell> cellIterator = row.cellIterator();
		while(cellIterator.hasNext()){
			Cell cell = cellIterator.next();
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				System.out.println(cell.getStringCellValue());
			if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
				System.out.println(cell.getNumericCellValue());
			if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				System.out.println();
		}
	}
	/**
	 * <p><b>Description:</b> 完全遍历整个Excel的Sheet表</p>
	 */
	@Test
	public void listHighExcel(){
//		Sheet sheet = wb.getSheet("PT");
		Sheet sheet = wb.getSheetAt(1);
		int rowNum = sheet.getFirstRowNum();
		System.out.println(rowNum);
		Iterator<Row> iterator = sheet.iterator();
		iterator.next();
		while(iterator.hasNext()){
			Row row = iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext()){
				Cell cell = cellIterator.next();
				if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
					System.out.print("\t");
				if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
					System.out.print(cell.getNumericCellValue()+"\t");
				if(cell.getCellType()==Cell.CELL_TYPE_STRING)
					System.out.print(cell.getStringCellValue()+"\t");
			}
			System.out.println();
		}
	}
	@After
	public void after() throws IOException{
		wb.close();//只有当没有其他程序使用的时候才可以调用关闭
	}
}
