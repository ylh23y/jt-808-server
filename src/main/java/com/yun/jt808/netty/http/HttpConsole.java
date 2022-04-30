package com.yun.jt808.netty.http;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
* @Description: http消息处理
* @author James
* @date 2021年10月15日 下午12:47:38
 */
public class HttpConsole {
	
	private AbstractHttpDisplay abstractHttpDisplay;
	private Response response404;
	
	public HttpConsole(AbstractHttpDisplay abstractHttpDisplay){
		this.abstractHttpDisplay = abstractHttpDisplay;
		
		response404 = new Response();
		response404.setCode(404);
	}
	
	public Response console(String txt){
		//System.err.println("==================================="+txt);
		if(!(txt.startsWith("{") && txt.endsWith("}"))){
			String[] split = txt.split("&");
			int len = split.length;
			Map<String,String> map = new HashMap<String, String>(len);
			String[] split2;
			for(int i = 0; i < len; i++){
				split2 = split[i].split("=");
				if(split2.length == 2){
					map.put(split2[0], split2[1]);
				}
			}
			txt = JSON.toJSONString(map);
			//System.err.println("===================================2"+txt);
			//{"serverId":88,"userType":0,"loginTime":1417423395,"openId":"open_1","code":10004}
		}
		Request request = JSON.parseObject(txt, Request.class);
		//String result = null;
		AbstractHttpHandler abstractHttpHandler = abstractHttpDisplay.getHttpHandler(request.getCode());
		Response response = null;
		if(abstractHttpHandler != null){
			response = abstractHttpHandler.handle(txt);
			//result = JsonManager.getGson().toJson(response);
		}else{
			//result = JsonManager.getGson().toJson(response404);
			response = response404;
		}
		return response;
	}
}
