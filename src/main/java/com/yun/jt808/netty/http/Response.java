package com.yun.jt808.netty.http;
/**
* @Description: http 返回基类 
* @author James
* @date 2021年10月15日 下午12:52:17
 */
public class Response {

	public transient static final String CONTENT_TYPE_HTML = "text/html; charset=UTF-8";

	public transient static final String CONTENT_TYPE_PLAIN = "text/plain; charset=UTF-8";

	public transient static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";

	private transient String contentType = CONTENT_TYPE_JSON;

	private int code = 200;
	private String desc;
	private Object value;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
