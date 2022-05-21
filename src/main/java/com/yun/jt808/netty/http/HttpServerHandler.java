package com.yun.jt808.netty.http;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.COOKIE;
import static io.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

import java.util.Set;

/**
* @Description: 消息处理类 
* @author James
* @date 2021年10月15日 下午12:47:59
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {

	private HttpConsole console;
	private HttpRequest request;
	/** Buffer that stores the response content */
	private final StringBuilder buf = new StringBuilder();
	private StringBuilder contentBuilder = new StringBuilder();
	private final static Response DEF_RESPONSE = new Response();
	private String method;
	
	private final static String METHOD_GET = "GET";
	private final static String METHOD_POST = "POST";
	
	/*static{
		defResponse.setContentType(contentType);
	}*/

	public HttpServerHandler(AbstractHttpDisplay abstractHttpDisplay) {
		console = new HttpConsole(abstractHttpDisplay);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@SuppressWarnings("deprecation")
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof HttpRequest) {
			HttpRequest request = this.request = (HttpRequest) msg;
			method = request.getMethod().name();

			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(ctx);
			}

			buf.setLength(0);
			contentBuilder.setLength(0);
			//buf.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
			//buf.append("===================================\r\n");

			buf.append("VERSION: ").append(request.getProtocolVersion()).append("\r\n");
			buf.append("HOSTNAME: ").append(HttpHeaders.getHost(request, "unknown")).append("\r\n");
			buf.append("REQUEST_URI: ").append(request.getUri()).append("\r\n\r\n");

			HttpHeaders headers = request.headers();
			if (!headers.isEmpty()) {
				String key, value;
				for (Map.Entry<String, String> h : headers) {
					key = h.getKey();
					value = h.getValue();
					buf.append("HEADER: ").append(key).append(" = ").append(value).append("\r\n");
				}
				buf.append("\r\n");
			}

			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
			Map<String, List<String>> params = queryStringDecoder.parameters();
			if (METHOD_GET.equals(method)) {
				Map<String, String> map = new HashMap<String, String>(params.size());
				if (!params.isEmpty()) {
					String key;
					List<String> vals;
					for (Entry<String, List<String>> p : params.entrySet()) {
						key = p.getKey();
						vals = p.getValue();
						for (String val : vals) {
							buf.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
						}
						//在赋值的时候用URLEncoder.encode(参数,"UTF-8")  ----> des="+ URLEncoder.encode("可以关闭吗2?","UTF-8")
						map.put(key, URLDecoder.decode(vals.get(0)));
					}
					buf.append("\r\n");
					contentBuilder.append(JSON.toJSON(map));
				}
			} else {
				if (!params.isEmpty()) {
					String key;
					List<String> vals;
					for (Entry<String, List<String>> p : params.entrySet()) {
						key = p.getKey();
						vals = p.getValue();
						for (String val : vals) {
							buf.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
						}
					}
					buf.append("\r\n");
				}

			}
			appendDecoderResult(buf, request);
		}

		if (msg instanceof HttpContent) {
			HttpContent httpContent = (HttpContent) msg;
			ByteBuf content = httpContent.content();
			if (content.isReadable()) {
				String str = content.toString(CharsetUtil.UTF_8);
				buf.append("CONTENT: ");
				buf.append(str);
				buf.append("\r\n");
				appendDecoderResult(buf, request);

				//contentBuilder.append(str);
				if (METHOD_POST.equals(method)) {
					contentBuilder.append(URLDecoder.decode(str));
				}
			}

			if (msg instanceof LastHttpContent) {
				buf.append("END OF CONTENT\r\n");

				LastHttpContent trailer = (LastHttpContent) msg;
				if (!trailer.trailingHeaders().isEmpty()) {
					buf.append("\r\n");
					for (String name : trailer.trailingHeaders().names()) {
						for (String value : trailer.trailingHeaders().getAll(name)) {
							buf.append("TRAILING HEADER: ");
							buf.append(name).append(" = ").append(value).append("\r\n");
						}
					}
					buf.append("\r\n");
				}
				/*
				 * String returnResult = contentBuilder.length() > 0 ? console.console(contentBuilder.toString()) : null;
				 * System.err.println("returnResult = "+returnResult);
				 * if(returnResult != null){
				 * if (!writeResponse(trailer, ctx, returnResult)) {
				 * // If keep-alive is off, close the connection once the content is fully written.
				 * ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
				 * }
				 * //writeResponse(trailer, ctx, returnResult);
				 * }
				 */

				Response response = contentBuilder.length() > 0 ? console.console(contentBuilder.toString()) : DEF_RESPONSE;
				//if (response != null) {
					if (!writeResponse(trailer, ctx, response)) {
						// If keep-alive is off, close the connection once the content is fully written.
						ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
						System.out.println("If keep-alive is off, close the connection once the content is fully written.");
					}
				//}
			}
		}
	}

	private static void appendDecoderResult(StringBuilder buf, HttpObject o) {
		DecoderResult result = o.getDecoderResult();
		if (result.isSuccess()) {
			return;
		}

		buf.append(".. WITH DECODER FAILURE: ");
		buf.append(result.cause());
		buf.append("\r\n");
	}

	private boolean writeResponse(HttpObject currentObj, ChannelHandlerContext ctx, Response response) {
		// Decide whether to close the connection or not.
		boolean keepAlive = HttpHeaders.isKeepAlive(request);
		// Build the response object.
		String context = Response.CONTENT_TYPE_JSON.equalsIgnoreCase(response.getContentType()) ? JSON.toJSONString(response)
				: response.getDesc();
		FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, currentObj.getDecoderResult().isSuccess() ? OK
				: BAD_REQUEST, Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));

		//fullHttpResponse.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		//fullHttpResponse.headers().set(CONTENT_TYPE, "text/" + response.getContentType() + "; charset=UTF-8");
		fullHttpResponse.headers().set(CONTENT_TYPE, response.getContentType());

		if (keepAlive) {
			// Add 'Content-Length' header only for a keep-alive connection.
			fullHttpResponse.headers().set(CONTENT_LENGTH, fullHttpResponse.content().readableBytes());
			// Add keep alive header as per:
			// - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
			fullHttpResponse.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}

		// Encode the cookie.
		String cookieString = request.headers().get(COOKIE);
		if (cookieString != null) {
			Set<Cookie> cookies = CookieDecoder.decode(cookieString);
			if (!cookies.isEmpty()) {
				// Reset the cookies if necessary.
				for (Cookie cookie : cookies) {
					fullHttpResponse.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
				}
			}
		} else {
			// Browser sent no cookie.  Add some.
			fullHttpResponse.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key1", "value1"));
			fullHttpResponse.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key2", "value2"));
		}

		// Write the response.
		ctx.write(fullHttpResponse);
		//ctx.write(response).addListener(ChannelFutureListener.CLOSE);

		return keepAlive;
	}

	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
		ctx.write(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof HttpRequest) {
			//System.err.println("1");
			HttpRequest request = this.request = (HttpRequest) msg;
			method = request.getMethod().name();

			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(ctx);
			}

			buf.setLength(0);
			contentBuilder.setLength(0);
			//buf.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
			//buf.append("===================================\r\n");

			buf.append("VERSION: ").append(request.getProtocolVersion()).append("\r\n");
			buf.append("HOSTNAME: ").append(HttpHeaders.getHost(request, "unknown")).append("\r\n");
			buf.append("REQUEST_URI: ").append(request.getUri()).append("\r\n\r\n");

			HttpHeaders headers = request.headers();
			if (!headers.isEmpty()) {
				String key, value;
				for (Map.Entry<String, String> h : headers) {
					key = h.getKey();
					value = h.getValue();
					buf.append("HEADER: ").append(key).append(" = ").append(value).append("\r\n");
				}
				buf.append("\r\n");
			}

			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
			Map<String, List<String>> params = queryStringDecoder.parameters();
			if (METHOD_GET.equals(method)) {
				Map<String, String> map = new HashMap<String, String>(params.size());
				if (!params.isEmpty()) {
					String key;
					List<String> vals;
					for (Entry<String, List<String>> p : params.entrySet()) {
						key = p.getKey();
						vals = p.getValue();
						for (String val : vals) {
							buf.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
						}
						//在赋值的时候用URLEncoder.encode(参数,"UTF-8")  ----> des="+ URLEncoder.encode("可以关闭吗2?","UTF-8")
						map.put(key, URLDecoder.decode(vals.get(0)));
					}
					buf.append("\r\n");
					contentBuilder.append(JSON.toJSON(map));
				}
			} else {
				if (!params.isEmpty()) {
					String key;
					List<String> vals;
					for (Entry<String, List<String>> p : params.entrySet()) {
						key = p.getKey();
						vals = p.getValue();
						for (String val : vals) {
							buf.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
						}
					}
					buf.append("\r\n");
				}

			}
			appendDecoderResult(buf, request);
		}

		if (msg instanceof HttpContent) {
			HttpContent httpContent = (HttpContent) msg;
			ByteBuf content = httpContent.content();
			if (content.isReadable()) {
				String str = content.toString(CharsetUtil.UTF_8);
				buf.append("CONTENT: ");
				buf.append(str);
				buf.append("\r\n");
				appendDecoderResult(buf, request);

				//contentBuilder.append(str);
				if (METHOD_POST.equals(method)) {
					contentBuilder.append(URLDecoder.decode(str));
				}
			}

			if (msg instanceof LastHttpContent) {
				buf.append("END OF CONTENT\r\n");

				LastHttpContent trailer = (LastHttpContent) msg;
				if (!trailer.trailingHeaders().isEmpty()) {
					buf.append("\r\n");
					for (String name : trailer.trailingHeaders().names()) {
						for (String value : trailer.trailingHeaders().getAll(name)) {
							buf.append("TRAILING HEADER: ");
							buf.append(name).append(" = ").append(value).append("\r\n");
						}
					}
					buf.append("\r\n");
				}
				/*
				 * String returnResult = contentBuilder.length() > 0 ? console.console(contentBuilder.toString()) : null;
				 * System.err.println("returnResult = "+returnResult);
				 * if(returnResult != null){
				 * if (!writeResponse(trailer, ctx, returnResult)) {
				 * // If keep-alive is off, close the connection once the content is fully written.
				 * ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
				 * }
				 * //writeResponse(trailer, ctx, returnResult);
				 * }
				 */

				Response response = contentBuilder.length() > 0 ? console.console(contentBuilder.toString()) : DEF_RESPONSE;
				//if (response != null) {
					if (!writeResponse(trailer, ctx, response)) {
						// If keep-alive is off, close the connection once the content is fully written.
						ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
						System.out.println("If keep-alive is off, close the connection once the content is fully written.");
					}
				//}
			}
		}
	}
}


