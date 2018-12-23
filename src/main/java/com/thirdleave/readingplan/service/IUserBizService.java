package com.thirdleave.readingplan.service;

import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserPO;

public interface IUserBizService {

    ResultPO userRegister(UserPO user);

    ResultPO userLogin(UserPO user);

    ResultPO changePassword(String newPassword, String oldPassword);

    ResultPO userDelete(String userID);

    ResultPO userModify(UserPO user);
}
