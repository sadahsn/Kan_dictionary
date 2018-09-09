package com.kan.dic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExtractWords {

	public static void main(String[] args) throws IOException {
		readFromXml();
	}

	private static void readFromXml() throws IOException {
		StringBuffer insertStatement_WORDS_LEX_INFO = new StringBuffer();
		StringBuffer insertStatement_WORD_GLOSS = new StringBuffer();
		StringBuffer insertStatement_SYNONYMOUS = new StringBuffer();
		FileInputStream fis = new FileInputStream(new File(".\\src\\main\\resources\\1-akhi.xls"));
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() >= 1) {
				String rootWord = "";
				String variation = "";
				String example = "";
				String lexTag = "";
				String postag = "";
				double synCount = 0;
				String synonym = "";
				String glossary = "";
				
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() > 0) {
						switch (cell.getColumnIndex()) {
						case 0-2:
							break;
						case 3:
							if (cell.getStringCellValue().trim().length() > 0) {
								rootWord = cell.getStringCellValue();
							} else {
								rootWord = null;
							}
							break;
						case 4:
							if (cell.getStringCellValue().trim().length() > 0) {
								variation = cell.getStringCellValue();
							} else {
								variation = null; 
							}
							
							break;
						case 5:
							if (cell.getStringCellValue().trim().length() > 0) {
								postag = cell.getStringCellValue();
							} else {
								postag = null; 
							}
							break;
						case 6:
							break;
							
						case 7:
							if (cell.getStringCellValue().trim().length() > 0) {
								glossary = cell.getStringCellValue();
							} else {
								glossary = null; 
							}
							break;
						case 8:
							if (cell.getStringCellValue().trim().length() > 0) {
								example = cell.getStringCellValue();
							} else {
								example = null; 
							}
							break;
						case 9:
							if (cell.getNumericCellValue() > 0) {
								synCount = cell.getNumericCellValue();
							} else {
								synCount = 0; 
							}
							break;
						case 10:
							break;
						case 11:
							if (cell.getStringCellValue().trim().length() > 0) {
								synonym = cell.getStringCellValue();
							} else {
								synonym = null; 
							}
							break;
							
						default:
							break;
						}

					}
				}
				
				insertStatement_WORDS_LEX_INFO.append("INSERT INTO [application].[WORDS_LEX_INFO]   ([LETTER_ID],[ROOT_WORD],[VARIATION],[EXAMPLE],[LEX_TAG],[POS_TAG],[SYN_COUNT])  VALUES (1, N'"+rootWord+"', N'"+variation+"', N'"+example+"', '"+lexTag+"', N'"+postag+"',"+synCount+")\n");
				insertStatement_WORD_GLOSS.append("INSERT INTO [application].[WORD_GLOSS] ([WORD_ID],[GLOSS])  VALUES  (1, N'"+glossary+"')\n");
				insertStatement_SYNONYMOUS.append("INSERT INTO [application].[SYNONYMOUS] ([WORD_ID],[SYNONYMOUS_WORD]) VALUES (1,N'"+synonym+"')\n");
			}
			
		}
		writeFeedDataToFile(new File("insertStatement_WORDS_LEX_INFO.txt"), insertStatement_WORDS_LEX_INFO.toString()); // Writing Valid insert data.
		writeFeedDataToFile(new File("insertStatement_WORD_GLOSS.txt"), insertStatement_WORDS_LEX_INFO.toString()); // Writing Valid insert data.
		writeFeedDataToFile(new File("insertStatement_SYNONYMOUS.txt"), insertStatement_WORDS_LEX_INFO.toString()); // Writing Valid insert data.
	}
	
	private static void writeFeedDataToFile(File f, String fileData)
			throws IOException {
		FileWriter fw = new FileWriter(f);
		fw.write(fileData);
		fw.close();
	}
}
