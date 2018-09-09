package com.sjtu.bookswap.Common;

import java.text.SimpleDateFormat;

public class AppSettings {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final String IMAGE_SAVE_PATH = "D://imgData//";
	
	public static final String PRODUCT_IMAGE_PATH = "D://imgData//";
	
	public static final int DONATE_REWARD = 50;
	
    public static final String ERROR_DUPLICATED_ACCOUNT = "Error: accout already exits.";
    
    public static final String ERROR_LOGIN = "Error: wrong account or password.";
    
    public static final String ERROR_WRONG_USER_ID = "Error: wrong userId.";
    
    public static final String ERROR_INVALID_ID = "Error: invalid input id.";
    
    public static final boolean BUILD_DATABASE = true;
    
    public static int LOG_FLAG = 2;
}
