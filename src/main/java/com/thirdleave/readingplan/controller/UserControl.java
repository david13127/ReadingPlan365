package com.thirdleave.readingplan.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.thirdleave.readingplan.constant.IResultConstant;
import com.thirdleave.readingplan.service.IUserService;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserPO;
import com.thirdleave.readingplan.utils.LogUtil;

import net.sf.json.JSONObject;

@Controller
public class UserControl {

	private final static Logger LOG = LogUtil.getLogger(UserControl.class);

	@Autowired
	private IUserService userService;

	public String getUserName(UserPO user) {
		ResultPO resp = new ResultPO();
		UserPO userDB = userService.queryUserByID(user.getUserID());
		if (userDB != null) {
			resp.setStatus(IResultConstant.STATUS_OK);
			List<UserPO> users = new ArrayList<UserPO>();
			users.add(userDB);
			resp.setResult(users);
		} else {
			resp.setStatus(IResultConstant.STATUS_ERROR);
		}
		return JSONObject.fromObject(resp).toString();
	}

	public String userLogin(UserPO user) {
		ResultPO resp = new ResultPO();
		ResultPO result = userService.userLogin(user);
		if ("OK".equals(result.getStatus())) {
			UserPO userFind = (UserPO) result.getResult();
			resp.setStatus(result.getStatus());
			resp.setResult(userFind);
		} else {
			resp.setStatus(result.getStatus());
			resp.setMessage(result.getMessage());
		}
		LOG.info(user.getUserID() + "登录成功");
		return JSONObject.fromObject(resp).toString();
	}

	public String userRegister(UserPO user) {
		ResultPO resp = new ResultPO();
		ResultPO result = userService.userRegister(user);
		if (IResultConstant.STATUS_OK.equals(result.getStatus())) {
			UserPO userFind = (UserPO) result.getResult();
			resp.setStatus(result.getStatus());
			resp.setResult(userFind);
		} else {
			resp.setStatus(result.getStatus());
			resp.setMessage(result.getMessage());
		}
		return JSONObject.fromObject(resp).toString();
	}
}
