package com.sjtu.bookswap.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class Utils {
	
	public static String saveFiles(byte[] file, String filePath, String fileName) {
		File targetFile = new File(filePath);  
	    
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }       
        
        String pathas = filePath+fileName;
        
        try {
        	FileOutputStream out = new FileOutputStream(pathas);
        	out.write(file);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        return pathas;
	}
	
	public static String convertToString(Date date){
		return AppSettings.dateFormat.format(date);
	}
	
	public static Date convertToDate(String date) {
		Date res = null;
		try {
			res = AppSettings.dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
}