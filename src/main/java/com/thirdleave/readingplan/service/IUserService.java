package com.thirdleave.readingplan.service;

import com.thirdleave.readingplan.service.po.UserPO;

public interface IUserService extends IUserBOService, IUserBizService {

    UserPO findUserByID(String userID);
}
