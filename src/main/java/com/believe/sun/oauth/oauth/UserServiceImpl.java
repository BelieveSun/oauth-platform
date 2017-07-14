package com.believe.sun.oauth.oauth;

import com.believe.sun.oauth.mapper.UserMapper;
import com.believe.sun.oauth.modle.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * Created by sungj on 17-6-7.
 */
@Service("userDetailService")
public class UserServiceImpl implements UserService {

    private UserCache userCache;


    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(RedisCacheManager redisCacheManager, UserMapper userMapper) {
        try {
            this.userCache = new SpringCacheBasedUserCache(redisCacheManager.getCache("oauth"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
//        UserDetails user = userCache.getUserFromCache(username);
        UserDetails user = null;
        if (user == null) {
            user = userMapper.findUserByName(username);
        }

        Assert.notNull(user, "UserMapper " + userMapper
                + " returned null for username " + username + ". "
                + "This is an interface contract violation");

//        userCache.putUserInCache(user);

        return user;
    }
}
