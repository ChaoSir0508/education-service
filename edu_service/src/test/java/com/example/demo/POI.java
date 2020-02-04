package com.example.demo;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 测试poi操作
 */
public class POI {

    @Test
    void poixls() throws IOException {



        HSSFWorkbook sheets = new HSSFWorkbook();
        HSSFSheet sheet = sheets.createSheet("教师");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("王超");
        FileOutputStream outputStream = new FileOutputStream("E:\\temp\\a.xls");
        sheets.write(outputStream);

    }

}
