package com.thirdleave.readingplan.service;

import java.util.Date;

import com.thirdleave.readingplan.service.po.ResultPO;

public interface IUserBOService {

    ResultPO setUserID(String userID);

    ResultPO setUserName(String name);

    ResultPO setUserPassword(String password);

    ResultPO setUserSex(String sex);

    ResultPO setUserBirthday(Date birthday);

    ResultPO setUserRemark(String remark);

    ResultPO setUserEmailAddress(String emailAddress);
}
