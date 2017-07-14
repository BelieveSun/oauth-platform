package com.believe.sun.oauth.mapper;

import com.believe.sun.oauth.modle.User;

/**
 * Created by sungj on 17-6-19.
 */
public interface UserMapper {
    User findUserByName(String username);
}
