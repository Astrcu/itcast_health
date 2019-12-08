package com.itheima;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class POIDemo {

    //读取文件
    /*public static void main1(String[] args) throws Exception{
        //创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\Thompson\\Desktop\\test.xlsx");
        //获取工作表对象
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取行对象
        //XSSFRow row = sheet.getRow(0);
        //System.out.println(row);
        //获取单元格
        //获取行中的单元格
        //XSSFCell cell = row.getCell(1);
        //System.out.println(cell);
        //获取最后一行的行号
        int lastRowNum = sheet.getLastRowNum();
        //System.out.println(lastRowNum);
        for (int i = 0; i <=lastRowNum; i++) {
            //遍历得到每一行
            XSSFRow row = sheet.getRow(i);
            //获取这一行一共多少列
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                if (row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                }
                System.out.println(row.getCell(j));

            }
        }
    }*/

    //写数据
    public static void main(String[] args) throws Exception{
        //创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建表对象
        XSSFSheet xssfSheet = workbook.createSheet("test");
        //创建行对象
        XSSFRow row = xssfSheet.createRow(5);
        //创建单元格
        XSSFCell cell1 = row.createCell(5);
        //给Cell赋值
        cell1.setCellValue("辛丑丑");
        //创建单元格
        XSSFCell cell2 = row.createCell(6);
        //给Cell赋值
        cell2.setCellValue("哈哈哈");

        //创建输出流对象
        OutputStream os = new FileOutputStream(new File("C:\\Users\\Thompson\\Desktop\\test.xlsx"));

        workbook.write(os);

        os.flush();
        os.close();
        workbook.close();

    }
}
