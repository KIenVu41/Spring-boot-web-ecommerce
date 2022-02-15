package com.dev.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dev.model.UserDTO;

public class UserExcelExporter {
	 	private XSSFWorkbook workbook;
	    private XSSFSheet sheet;
	    private List<UserDTO> listUsers;
	     
	    public UserExcelExporter(List<UserDTO> listUsers) {
	        this.listUsers = listUsers;
	        workbook = new XSSFWorkbook();
	    }
	 
	 
	    private void writeHeaderLine() {
	        sheet = workbook.createSheet("Users");
	         
	        Row row = sheet.createRow(0);
	         
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setBold(true);
	        font.setFontHeight(16);
	        style.setFont(font);
	         
	        createCell(row, 0, "User ID", style);      
	        createCell(row, 1, "E-mail", style);       
	        createCell(row, 2, "Full Name", style);
	        createCell(row, 3, "Phone", style); 
	        createCell(row, 4, "Address", style);    
	        createCell(row, 5, "Gender", style);
	        createCell(row, 6, "Enabled", style);
	        createCell(row, 7, "Role", style);
	         
	    }
	     
	    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	        sheet.autoSizeColumn(columnCount);
	        Cell cell = row.createCell(columnCount);
	        if (value instanceof Integer) {
	            cell.setCellValue((Integer) value);
	        } else if (value instanceof Boolean) {
	            cell.setCellValue((Boolean) value);
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
	                 
	        for (UserDTO user : listUsers) {
	            Row row = sheet.createRow(rowCount++);
	            int columnCount = 0;
	             
	            createCell(row, columnCount++, user.getId(), style);
	            createCell(row, columnCount++, user.getEmail(), style);
	            createCell(row, columnCount++, user.getName(), style);
	            createCell(row, columnCount++, user.getPhone(), style);
	            createCell(row, columnCount++, user.getAddress(), style);
	            createCell(row, columnCount++, user.getGender(), style);
	            createCell(row, columnCount++, user.getEnabled(), style);
	            createCell(row, columnCount++, user.getRole(), style);
	          
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