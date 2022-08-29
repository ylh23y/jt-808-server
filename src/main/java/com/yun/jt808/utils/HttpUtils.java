package com.yun.jt808.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
/**
 * 
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:53:16
 */
public class HttpUtils {
	
	// 电子邮件发送网关接口  
    public static final String NETGATE_EMAIL_URL = "http://xxx.xxxx.com.cn:8090/api/sendemail.php";  
    // 短信发送网关接口  
    public static final String NETGATE_SMS_URL = "http://xxx.xxxx.com.cn:8090/api/sendsms.php";  
  
    public static final String APP_TYPE = "jssjApp";  
    public static final String MSG_TYPE_EMAIL = "2";// 邮件  
    public static final String MSG_TYPE_SMS = "1";// 短信  
    public static final String SOURCE = "netgate";// 返回信息来源  
    public static final String STATUS_SUCCESS = "1";// 返回成功标志  
  
    private static final String APPLICATION_JSON = "application/json";  
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";  
    private static final String CHARSET_UTF_8 = "UTF-8";  
    
	 /** 
     * 发送json数据到服务器网关接口 
     *  
     * @param url 
     * @param param 
     * @return 
     */  
    public static String postJson(String url, Map<String,Object> map) {  
        try {  
            HttpPost httpPost = new HttpPost(url);  
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);  
            // 绑定到请求 Entry  
            if(map != null){
            	StringEntity se = new StringEntity(map.toString(), CHARSET_UTF_8);  
                se.setContentType(CONTENT_TYPE_TEXT_JSON);  
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));  
                httpPost.setEntity(se); 
            }
            // 发送请求  
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);  
            // 得到应答的字符串，这也是一个 JSON 格式保存的数据  
            String resp = EntityUtils.toString(httpResponse.getEntity(), CHARSET_UTF_8);  
            System.out.println(resp);  
            return resp;  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String content = postJson("http://192.168.0.202:8080/default/com.deuwise.zpytsfsys.dongtu.soilWorkAnalysis.getSoilWorkInfos.biz.ext", null);
		System.out.println(content);
	}
}
