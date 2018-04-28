package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.coderslab.utils.DbUtil;

public class GroupDao {

	public static void saveToDB(Group group) throws SQLException {
		Connection conn = DbUtil.getConn();
		if(group.getId() ==0) {
			String sql = "INSERT INTO user_group (name) VALUES (?);";
			String columnNames[] = { "ID" };
			PreparedStatement prepStat = conn.prepareStatement(sql, columnNames);
			prepStat.setString(1, group.getName());
			prepStat.executeUpdate();
			ResultSet rs = prepStat.getGeneratedKeys();
			if (rs.next()) {
				group.setId(rs.getInt(1));
			}
		}
		else {
			String sql = "UPDATE user_group SET name = ?;";
			PreparedStatement prepStat = conn.prepareStatement(sql);
			prepStat.setString(1, group.getName());
			prepStat.executeUpdate();
			}
	}

	public static Group loadGroupById(int id) throws SQLException {
		Connection conn = DbUtil.getConn();
		String sql = "SELECT * FROM user_group WHERE ID = ?;";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		prepStat.setInt(1, id);
		ResultSet rs = prepStat.executeQuery();
		if (rs.next()) {
			Group group = new Group();
			group.setId(rs.getInt(1));
			group.setName(rs.getString(2));
			return group;
		}
		return null;

	}
	
	public static List<Group> loadAllGroups () throws SQLException {
		Connection conn = DbUtil.getConn();
		ArrayList<Group> groups = new ArrayList<>();
		String sql = "SELECT * FROM user_group";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		ResultSet rs = prepStat.executeQuery();
		while (rs.next()) {
			Group group = new Group();
			group.setId(rs.getInt(1));
			group.setName(rs.getString(2));
			groups.add(group);
			
		}
		return groups;
		
	}
	public static void delete(Group group) throws SQLException {
		Connection conn = DbUtil.getConn();
		String sql = "DELETE FROM user_group WHERE id = ?;";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		prepStat.setInt(1, group.getId());
		prepStat.executeUpdate();
		group.setId(0);
	}
}
