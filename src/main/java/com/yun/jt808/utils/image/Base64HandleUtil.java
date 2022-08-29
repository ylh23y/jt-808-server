package com.yun.jt808.utils.image;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * 
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午1:54:30
 */
public class Base64HandleUtil {

	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author: 
	 * @CreateTime: 
	 * @param imgStr base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @return
	*/
	public static boolean generateImage(String imgStr, String path) {
		if(imgStr == null){
			return false;
		}
		Base64 base64 = new Base64();
		try{
			byte[] b = base64.decode(imgStr);
			for(int i=0; i<b.length; ++i)
			{
				if(b[i] < 0){
					b[i] += 256;
				}
			}
			
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		}catch (Exception e) {
			return false;
		}
	}

}
