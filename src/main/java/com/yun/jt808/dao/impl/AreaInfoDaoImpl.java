package com.yun.jt808.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yun.jt808.common.enums.AreaType;
import com.yun.jt808.dao.AreaInfoDao;
import com.yun.jt808.po.AreaInfo;
import com.yun.jt808.utils.ConnectionManager;
/**
 * 
* @Description: 区域信息DAO接口实现
* @author James
* @date 2021年5月2日 上午10:34:50
 */
public class AreaInfoDaoImpl implements AreaInfoDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public List<AreaInfo> getAllAreaInfos() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select * from area_info where parentId is not null";
		
		try {
			conn = ConnectionManager.getAppDBConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			List<AreaInfo> ailist = new ArrayList<>();
			while(rs.next()){
				AreaInfo areaInfo = new AreaInfo();
				areaInfo.setId(rs.getInt("id"));
				areaInfo.setCoordinateSets(rs.getString("coordinateSets"));
				areaInfo.setCreateTime(rs.getDate("createTime"));
				areaInfo.setName(rs.getString("name"));
				areaInfo.setNumber(rs.getString("number"));
				ailist.add(areaInfo);
			}
			return ailist;
		} catch (SQLException e) {
			logger.error("获取所以区域信息异常 e = {}" , e);
		}finally{
			ConnectionManager.closeConnection(null, ps, conn, rs);
		}
		return null;
	}


	@Override
	public List<AreaInfo> getAreaInfoListByType(AreaType t) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select * from area_info where type = ?";
		
		try {
			conn = ConnectionManager.getAppDBConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getIndex());
			rs = ps.executeQuery();
			List<AreaInfo> ailist = new ArrayList<>();
			while(rs.next()){
				AreaInfo areaInfo = new AreaInfo();
				areaInfo.setId(rs.getInt("id"));
				areaInfo.setCoordinateSets(rs.getString("coordinateSets"));
				areaInfo.setCreateTime(rs.getDate("createTime"));
				areaInfo.setName(rs.getString("name"));
				areaInfo.setNumber(rs.getString("number"));
				areaInfo.setOperator(rs.getString("operator"));
				areaInfo.setPersonLiable(rs.getString("personLiable"));
				areaInfo.setPhone(rs.getString("phone"));
				areaInfo.setState(rs.getInt("state"));
				areaInfo.setType(rs.getInt("type"));
				areaInfo.setUpdateTime(rs.getDate("updateTime"));
				ailist.add(areaInfo);
			}
			return ailist.size() == 0 ? null : ailist;
		} catch (SQLException e) {
			logger.error("获取所以区域信息异常 e = {}" , e);
		}finally{
			ConnectionManager.closeConnection(null, ps, conn, rs);
		}
		return null;
	}


	@Override
	public List<AreaInfo> getAreaInfoList() {
		// TODO Auto-generated method stub
		return null;
	}

}
