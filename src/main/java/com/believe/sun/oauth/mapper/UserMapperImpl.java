package com.believe.sun.oauth.mapper;

import com.believe.sun.oauth.modle.Role;
import com.believe.sun.oauth.modle.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sungj on 17-6-16.
 */
@Repository
public class UserMapperImpl implements UserMapper{


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserMapperImpl(@Qualifier(value = "userDataSource") DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public User findUserByName(String username){
        final String sql = "select * from user where account = ? and status >= 0";
        final List<User> users = jdbcTemplate.query(sql, new String[]{username},new UserRowMapper());
        if(users != null && users.size() > 0){
            User user = users.get(0);
            String roleIds = user.getRoleIds();
            List<Role> roleNames = new ArrayList<>();
            for(String roleId : roleIds.split(",")){
                Role role = findRoleById(Integer.valueOf(roleId));
                if(role == null){
                    throw new RuntimeException("can't find roleId:"+roleId);
                }
                roleNames.add(role);
            }
            user.setRoles(roleNames);
            return user;
        }
        return null;
    }


    public Role findRoleById(Integer id){
        final String sql = "select * from role where id = ?";
        return jdbcTemplate.queryForObject(sql, new Integer[]{id}, new RolesRowMapper());
    }

    private class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setAccount(rs.getString("account"));
            user.setCellphone(rs.getString("cellphone"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            user.setIdentity(rs.getString("identity"));
            user.setRoleIds(rs.getString("roles"));
            user.setHeadimage(rs.getString("headimage"));
            user.setStatus(rs.getInt("status"));
            user.setBabyid(rs.getString("babyid"));
            user.setSex(rs.getInt("sex"));
            user.setAge(rs.getInt("age"));
            user.setRealname(rs.getString("realname"));
            return user;
        }
    }

    private class RolesRowMapper implements RowMapper<Role>{
        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setRole(rs.getString("role"));
            role.setDescriotion(rs.getString("description"));
            role.setPermissionId(rs.getString("permission_id"));
            return role;
        }
    }
}
