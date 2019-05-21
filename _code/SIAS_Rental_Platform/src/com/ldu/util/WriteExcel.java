package com.ldu.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class WriteExcel {

    //Export the column name of the table
    private String[] rowName;
    //Each line as an Object object
    private List<Object[]>  dataList = new ArrayList<Object[]>();

    //Constructor, passing in the data to be exported
    public WriteExcel(String[] rowName,List<Object[]>  dataList){
        this.dataList = dataList;
        this.rowName = rowName;
    }

    /*
     * export data
     * */
    public InputStream export() throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook();						// Create a workbook object
        HSSFSheet sheet = workbook.createSheet("sheet1");		 			//Create a worksheet

        //sheet style definition【getColumnTopStyle()/getStyle()】
        HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//Get the column header style object
        HSSFCellStyle style = this.getStyle(workbook);					//Cell style object

        //Define the number of columns required
        int columnNum = rowName.length;
        HSSFRow rowRowName = sheet.createRow(0);				// Create a row at index 2 (the second row at the top of the top row)

        //Set the column header to the cell of the sheet
        for(int n=0;n<columnNum;n++){
            HSSFCell cellRowName = rowRowName.createCell(n);				//Create a cell with a corresponding number of column headers
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);				//Set the data type of the column header cell
            HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
            cellRowName.setCellValue(text);									//Set the value of the column header cell
            cellRowName.setCellStyle(columnTopStyle);						//Set column header cell style
        }

        //Set the queried data to the cell corresponding to the sheet
        for(int i=0;i<dataList.size();i++){

            Object[] obj = dataList.get(i);//Traverse each object
            HSSFRow row = sheet.createRow(i+1);//Create the required number of rows
            for(int j=0; j<obj.length; j++){
                HSSFCell  cell = null;   //Set the data type of the cell
                cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
                if(!"".equals(obj[j]) && obj[j] != null){
                    cell.setCellValue(obj[j].toString());						//Set the value of a cell
                }
                cell.setCellStyle(style);									//Set cell style
            }
        }
        //Let the column width automatically adapt to the exported column length
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //The current line has not been used
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if(colNum == 0){
                sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
            }else{
                sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
            }
        }

        String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
        String headStr = "attachment; filename=\"" + fileName + "\"";
//				        response = getResponse();
//				        response.setContentType("APPLICATION/OCTET-STREAM");
//				        response.setHeader("Content-Disposition", headStr);
//				        OutputStream out = response.getOutputStream();
//				        FileOutputStream out=new FileOutputStream("C:\\test.xls");
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] content=os.toByteArray();
        InputStream is=new ByteArrayInputStream(content);
        return is;
    }

    /*
     * Header cell style
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        //Set font
        HSSFFont font = workbook.createFont();
        //Set the font size
        font.setFontHeightInPoints((short)11);
        //Bold font
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //Set the font name
        font.setFontName("Courier New");
        //Set style;
        HSSFCellStyle style = workbook.createCellStyle();
        //Set the bottom border;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //Set the bottom border color;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //Set the left border;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //Set the left border color;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //Set the right border;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //Set the right border color;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //Set the top border;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //Set the top border color;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //The font set in the style application;
        style.setFont(font);
        //Set automatic line breaks;
        style.setWrapText(false);
        //Set the horizontally aligned style to centered;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //Set the vertically aligned style to centered;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    /*
   *Column data information cell style
   */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // Set font
        HSSFFont font = workbook.createFont();
        //Set the font size
        //font.setFontHeightInPoints((short)10);
        //Bold font
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //Set the font name
        font.setFontName("Courier New");
        //Set the style;
        HSSFCellStyle style = workbook.createCellStyle();
        //Set the bottom border;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //Set the bottom border color;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //Set the left border;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //Set the left border color;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //Set the right border;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //Set the right border color;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //Set the top border;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //Set the top border color;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //The font set in the style application;
        style.setFont(font);
        //Set automatic line breaks;
        style.setWrapText(false);
        //et the horizontally aligned style to centered;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //Set the vertically aligned style to centered;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }
    public static void main(String[] args) throws Exception {
        String[] rowsName = new String[]{"序号","状态","录入人","录入时间"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] obj1=new Object[4];
        obj1[0]="1";obj1[1]="ok";obj1[2]="hello";obj1[3]="wsz";
        dataList.add(obj1);
        Object[] obj2=new Object[4];
        obj2[0]="2";obj2[1]="dsa";obj2[2]="wolrd";obj2[3]="python";
        dataList.add(obj2);
        WriteExcel ex = new WriteExcel(rowsName, dataList);
        ex.export();
    }

}
