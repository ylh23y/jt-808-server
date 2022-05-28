package com.yun.jt808.netty.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import com.yun.jt808.common.enums.AlarmDetailEnums;
import com.yun.jt808.common.enums.DeviceState;
import com.yun.jt808.dao.DaoFactory;
import com.yun.jt808.po.AlarmDetailInfo;
import com.yun.jt808.po.CarTransportTimes;
import com.yun.jt808.po.CarryingCapacityInfo;
import com.yun.jt808.po.LocationInfoReport;
import io.netty.channel.Channel;
/**
* @Description: session 管理
* @author James
* @date 2021年4月24日 下午7:25:43
 */
public class SessionManager {
	
	public static Map<String, Session> channelSessions = new HashMap<>();

	private static SessionManager sessionManager = null;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static AtomicInteger currentChannelNums = new AtomicInteger(0);
	
	private Map<String,List<LocationInfoReport>> tempAlarmInfoMap = new ConcurrentHashMap<>();

	public static ConcurrentHashMap<String, CarryingCapacityInfo> loadTaskMap = new ConcurrentHashMap<>();
	
	public static ConcurrentHashMap<String, Map<AlarmDetailEnums,AlarmDetailInfo>> alarmDetailMap = new ConcurrentHashMap<>();
	
	public static ConcurrentHashMap<String, CarTransportTimes> carTransportTimesMap = new ConcurrentHashMap<>();
	
	public static SessionManager getObject() {
		if (sessionManager == null) {
			sessionManager = new SessionManager();
		}
		return sessionManager;
	}

	public Map<String, List<LocationInfoReport>> getTempAlarmInfoMap() {
		return tempAlarmInfoMap;
	}

	public void setTempAlarmInfoMap(Map<String, List<LocationInfoReport>> tempAlarmInfoMap) {
		this.tempAlarmInfoMap = tempAlarmInfoMap;
	}

	public void addSession(Session s) {
		if (s.getChannel() == null) {
			return;
		}
		if (s.getLicensePlate() == null || s.getSimPhone() == null) {
			return;
		}
		String id = s.getChannel().id().toString();
		if (channelSessions.get(id) == null) {
			channelSessions.put(id, s);
			//更新状态
			DaoFactory.getDao().getDeviceStatusInfoDao().updateOnlineState(s,DeviceState.ONLINE);
		}
	}
	
	/**
	 * 通过sim 号码获取Session
	 * @param simPhone sim号
	 * @return session
	 */
	public Session getSessionBySimPhone(String simPhone){
		for(Session s : channelSessions.values())
		{
			if(simPhone.equals(s.getSimPhone())){
				return s;
			}
		}
		return null;
	}

	public Session getSession(String channelId) {
		logger.info("channelSessions size {}", channelSessions.size());
		return channelSessions.get(channelId);
	}

	public Map<String, Session> getChannelSessions() {
		return channelSessions;
	}

	public void setChannelSessions(Map<String, Session> channelSessions) {
		this.channelSessions = channelSessions;
	}

	/**
	 * 客户端离线操作
	 * @param channel
	 */
	public void offline(Channel channel) {
		//channel is null return
		if(channel == null) {
			return;
		}
		//channel id is null return
		String id = channel.id().toString();
		if(id == null) {
			return;
		}
		// session is null retrun
		Session s = channelSessions.get(id);
		if(s == null) {
			return;
		}
		//update dev line state
		DaoFactory.getDao().getDeviceStatusInfoDao().updateOnlineState(s,DeviceState.OFFLINE);
		//remove channelSessions entity
		if(logger.isDebugEnabled()){
			logger.debug("before clear session ：{}",channelSessions.toString());
		}
		//do remove
		channelSessions.remove(id);
		if(logger.isDebugEnabled()){
			logger.debug("after clear session ：{}",channelSessions.toString());
		}
	}

	private static volatile SessionManager instance = null;
	/**
	 * netty生成的sessionID和Session的对应关系
	 */
	private Map<String, Session> sessionIdMap;
	/**
	 * 终端手机号和netty生成的sessionID的对应关系
	 */
	private Map<String, String> phoneMap;
	
	public SessionManager() {
		this.sessionIdMap = new ConcurrentHashMap<>();
		this.phoneMap = new ConcurrentHashMap<>();
	}

	public static SessionManager getInstance() {
		if (instance == null) {
			synchronized (SessionManager.class) {
				if (instance == null) {
					instance = new SessionManager();
				}
			}
		}
		return instance;
	}
	
	public boolean containsKey(String sessionId) {
		return sessionIdMap.containsKey(sessionId);
	}

	public boolean containsSession(Session session) {
		return sessionIdMap.containsValue(session);
	}

	public Session findBySessionId(String id) {
		//打印Session
		return sessionIdMap.get(id);
	}

	public Session findByTerminalPhone(String phone) {
		String sessionId = this.phoneMap.get(phone);
		if (sessionId == null){
			return null;
		}
		return this.findBySessionId(sessionId);
	}

	public synchronized Session put(String key, Session value) {
		if (value.getTerminalPhone() != null && !"".equals(value.getTerminalPhone().trim())) {
			this.phoneMap.put(value.getTerminalPhone(), value.getId());
		}
		return sessionIdMap.put(key, value);
	}

	public synchronized Session removeBySessionId(String sessionId) {
		if (sessionId == null){
			return null;
		}
		Session session = sessionIdMap.remove(sessionId);
		if (session == null){
			return null;
		}
		if (session.getTerminalPhone() != null){
			this.phoneMap.remove(session.getTerminalPhone());
		}
		return session;
	}

	// public synchronized void remove(String sessionId) {
	// if (sessionId == null)
	// return;
	// Session session = sessionIdMap.remove(sessionId);
	// if (session == null)
	// return;
	// if (session.getTerminalPhone() != null)
	// this.phoneMap.remove(session.getTerminalPhone());
	// try {
	// if (session.getChannel() != null) {
	// if (session.getChannel().isActive() || session.getChannel().isOpen()) {
	// session.getChannel().close();
	// }
	// session = null;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public Set<String> keySet() {
		return sessionIdMap.keySet();
	}

	public void forEach(BiConsumer<? super String, ? super Session> action) {
		sessionIdMap.forEach(action);
	}

	public Set<Entry<String, Session>> entrySet() {
		return sessionIdMap.entrySet();
	}

	public List<Session> toList() {
		return this.sessionIdMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
	}

}
