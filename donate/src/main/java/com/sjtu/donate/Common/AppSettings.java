package com.sjtu.donate.Common;

import java.text.SimpleDateFormat;

public class AppSettings {
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final String IMAGE_SAVE_PATH = "D://imgData//";
	
	public static final String PRODUCT_IMAGE_PATH = "D://imgData//";
	
	public static final int DONATE_REWARD = 50;
	
	public static final String ERROR_BALANCE = "Error: balance not enough.";
	
	public static final String ERROR_PRODUCT_AMOUNT = "Error: product amount not enough.";
	
    public static final String ERROR_DUPLICATED_ACCOUNT = "Error: accout already exits.";
    
    public static final String ERROR_LOGIN = "Error: wrong account or password.";
    
    public static final String ERROR_WRONG_USER_ID = "Error: wrong userId.";
    
    public static final boolean BUILD_DATABASE = false;
    
    public static int LOG_FLAG = 1;
	
}
