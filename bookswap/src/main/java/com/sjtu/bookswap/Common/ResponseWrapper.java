package com.sjtu.bookswap.Common;

import com.alibaba.fastjson.JSONObject;

public class ResponseWrapper {
	
	public static JSONObject getResult(boolean isSuccess, Object data) {
		JSONObject res = new JSONObject();
		if(isSuccess) {
			res.put("status", "success");
		}
		else {
			res.put("status", "fail");
		}
		res.put("data", data);
		return res;
	}

}
