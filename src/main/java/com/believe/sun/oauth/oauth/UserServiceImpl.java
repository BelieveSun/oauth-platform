package com.believe.sun.oauth.oauth;

import com.believe.sun.oauth.mapper.UserMapper;
import com.believe.sun.oauth.modle.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * Created by sungj on 17-6-7.
 */
@Service("userDetailService")
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userMapper.findUserByName(username);
        Assert.notNull(user, "UserMapper " + userMapper
                + " returned null for username " + username + ". "
                + "This is an interface contract violation");


        return user;
    }
}
