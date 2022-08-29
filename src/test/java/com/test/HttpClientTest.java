package com.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.yun.jt808.common.ServerConsts;
import com.yun.jt808.common.WebConsts;

public class HttpClientTest {

	public static String postMessage(String url,String jsonData){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		URI uri = null;
		try {
			uri = new URIBuilder().setPath(url).build();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse httpResp = null;
		StringEntity myEntity = new StringEntity(jsonData, ContentType.create(
				"text/x-json", "utf-8"));
		try {
			httpPost.setEntity(myEntity);
			httpResp = httpClient.execute(httpPost);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String str = null;
		try {
			str = EntityUtils.toString(httpResp.getEntity());
		} catch (ParseException e) {
			// TODO Auto-generated catch block]
			e.printStackTrace();
//			throw new BasicException("http响应解码错误",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			throw new BasicException("http网络错误",e);
		}
//		System.out.println("---------------"
//				+ str);
		
		if (httpPost != null) {
//			httpPost.releaseConnection();
		}
		if (httpClient != null) {
			httpClient = null;
		}
		
		return str;
	}
	
	@Test
	public void testSendTUpgrade(){
		
	}
	
	@Test
	public void testGetDouBan(){
		String url = "https://api.douban.com/v2/movie/subject/1764796";
		String result = postMessage(url, "");
		System.out.println(result);
	}
	
	@Test
	public void testQueryLocationPoints(){
		String url = "http://127.0.0.1:8777";
		String content = "{code:33281,content:\"We Will Get Out\"}";
		String result = postMessage(url, content);
		System.out.println(result);
	}
	
	@Test
	public void testUpdateVersion(){
		String url = "http://127.0.0.1:8777";
		int code = WebConsts.SEND_TERMINAL_UPGRADE_PACKAGE;
		String content = "{code:"+code+",content:\"We Will Get Out\"}";
		String result = postMessage(url, content);
		System.out.println(result);
	}
	
	@Test
	public void testSendTextToTerminal(){
		String url = "http://127.0.0.1:8777";
		int code = ServerConsts.CMD_SEND_TEXT_TO_TERMINAL;
		String content = "{code:"+code+",content:\"We Will Get Out\"}";
		String result = postMessage(url, content);
		System.out.println(result);
	}
	
	@Test
	public void testTerminalAttrQuery(){
		String url = "http://127.0.0.1:8777";
		int code = ServerConsts.CMD_TERMINAL_ATTR_QUERY;
		String content = "{code:"+code+",terminalPhone:\"186648836521\"}";
		String result = postMessage(url, content);
		System.out.println(result);
	}
	
}
