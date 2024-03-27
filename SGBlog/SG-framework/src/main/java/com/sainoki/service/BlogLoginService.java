package com.sainoki.service;

import com.sainoki.domain.ResponseResult;
import com.sainoki.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
