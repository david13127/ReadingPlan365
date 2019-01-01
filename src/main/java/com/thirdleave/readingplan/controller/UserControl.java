package com.thirdleave.readingplan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.thirdleave.readingplan.constant.IResultConstant;
import com.thirdleave.readingplan.service.IUserService;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserPO;

@Controller
public class UserControl {

	private final static Logger LOG = LoggerFactory.getLogger(UserControl.class);

	@Autowired
	private IUserService userService;

	public ResultPO userLogin(UserPO user) {
		ResultPO resp = new ResultPO();
		ResultPO result = userService.userLogin(user);
		if ("OK".equals(result.getStatus())) {
			UserPO userFind = (UserPO) result.getResult();
			resp.setStatus(result.getStatus());
			resp.setResult(userFind);
			LOG.info(user.getUserID() + "登录成功");
		} else {
			resp.setStatus(result.getStatus());
			resp.setMessage(result.getMessage());
		}
		return resp;
	}

	public ResultPO userRegister(UserPO user) {
		ResultPO resp = new ResultPO();
		ResultPO result = userService.userRegister(user);
		if (IResultConstant.STATUS_OK.equals(result.getStatus())) {
			UserPO userFind = (UserPO) result.getResult();
			resp.setStatus(result.getStatus());
			resp.setResult(userFind);
			LOG.info(user.getUserID() + "注册成功");
		} else {
			resp.setStatus(result.getStatus());
			resp.setMessage(result.getMessage());
		}
		return resp;
	}
}
