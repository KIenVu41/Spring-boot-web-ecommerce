package com.dev.controller.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dev.model.CouponDTO;

public class CouponExcelExporter {
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<CouponDTO> listCoupons;
     
    public CouponExcelExporter(List<CouponDTO> listCoupons) {
        this.listCoupons = listCoupons;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Coupons");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Coupon ID", style);      
        createCell(row, 1, "Code", style);       
        createCell(row, 2, "discountPercentage", style);
        createCell(row, 3, "expiredDate", style); 
        createCell(row, 4, "Bill ID", style);
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if(value instanceof Date){     	
        	cell.setCellValue((Date) value);
        }else {
        
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (CouponDTO coupon : listCoupons) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, coupon.getId(), style);
            createCell(row, columnCount++, coupon.getCode(), style);
            createCell(row, columnCount++, coupon.getDiscountPercentage(), style);
            createCell(row, columnCount++, coupon.getExpiredDate(), style);
            createCell(row, columnCount++, coupon.getBill().getId(), style);
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}
