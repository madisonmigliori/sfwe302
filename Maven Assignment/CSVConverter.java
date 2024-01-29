package com.assignment2;

//maven package libraries
import org.apache.poi.ss.usermodel.Table;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

//file reader libraries
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class CSVConverter{
    /**
     * @param args
     * @throws DocumentException 
     */
    public static void main(String[] args) throws IOException, DocumentException{
        if (args.length != 2) {
            System.out.println("Enter: java -jar assignment2.jar <PDF or XLS> <file name>");
            System.exit(1);
        }
        String outputType = args[0].toUpperCase();
        String fileName = args[1];

        //verify type is PDF/XLS
        if(!outputType.equals("PDF")&& !outputType.equals("XLS")){
            System.out.println("Invalid type. Please use either PDF or XLS.");
            System.exit(1);
        }

        File csvFile = new File(fileName);

        //make sure csv file was added
        if(!csvFile.exists()){
            System.out.println("CSV file not found");
            System.exit(1);
        }
        //PDF generates
        if(outputType.equals("PDF")){
            generatePDF(csvFile);
        
        }
        //XLS generates
        if(outputType.equals("XLS")){
            generateXLS(csvFile);
        }
        System.out.println("File generated successfully :)." );

    }

    private static void generatePDF(File csvFile) throws DocumentException, IOException{
       Document doc = new Document();
       PdfWriter.getInstance(doc, new FileOutputStream("assignment2_out.pdf"));
    
       doc.open();

       PdfPTable table = new PdfPTable(5);

       try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            try {
                while ((line = br.readLine()) != null) {    
                    // Split the CSV line by the delimiter
                    String[] data = line.split(";");

                    // Adding cells to the table
                    for (String value : data) {
                        table.addCell((value));
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            doc.add(table);
            doc.close();
            br.close();
        }
    }

    private static void generateXLS(File csvFile) throws FileNotFoundException, IOException{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Assignment 2");
 
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
             String line;
             int rowNum = 0;
                 while ((line = br.readLine()) != null) {
                    //Adding rows to table
                    Row row = sheet.createRow(rowNum++);
                     // Split the CSV line by the semicolon
                     String[] data = line.split(";");
                    
                     int cellNum = 0;
                     // Adding cells to the table
                     for (String value : data) {
                        Cell cell = row.createCell(cellNum++);
                        cell.setCellValue(value);
                     }
                 }
             try(FileOutputStream outputFile = new FileOutputStream("assignment2_out.xlsx")){
             workbook.write(outputFile);
             }

             catch (IOException e) {
                e.printStackTrace();
                }
            workbook.close();
            br.close();
        }
 

    }
}
