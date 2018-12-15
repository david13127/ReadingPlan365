package com.thirdleave.readingplan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.thirdleave.readingplan.constant.IResultConstant;
import com.thirdleave.readingplan.controller.po.Response;
import com.thirdleave.readingplan.service.IUserService;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserPO;

import net.sf.json.JSONObject;

@Controller
public class UserControl {

	@Autowired
	private IUserService userService;

	public String getUserName(UserPO user) {
		Response resp = new Response();
		UserPO userDB = userService.findUserByID(user.getUserID());
		if (userDB != null) {
			resp.setStatus(IResultConstant.STATUS_OK);
			List<UserPO> users = new ArrayList<UserPO>();
			users.add(userDB);
			resp.setResults(users);
		}
		else {
			resp.setStatus(IResultConstant.STATUS_ERROR);
		}
		return JSONObject.fromObject(resp).toString();
	}
	
	public String userLogin(UserPO user) {
		Response resp = new Response();
		ResultPO result = userService.userLogin(user);
		if ("OK".equals(result.getStatus())) {
			UserPO userFind = (UserPO) result.getResults();
			resp.setStatus(result.getStatus());
			List<UserPO> users = new ArrayList<UserPO>();
			users.add(userFind);
			resp.setResults(users);
		}
		else {
			resp.setStatus(result.getStatus());
			resp.setMessage(result.getMessage());
		}
		return JSONObject.fromObject(resp).toString();
	}
	public String userRegister(UserPO user) {
		Response resp = new Response();
		ResultPO result = userService.userRegister(user);
		if (IResultConstant.STATUS_OK.equals(result.getStatus())) {
			UserPO userFind = (UserPO) result.getResults();
			resp.setStatus(result.getStatus());
			List<UserPO> users = new ArrayList<UserPO>();
			users.add(userFind);
			resp.setResults(users);
		}
		else {
			resp.setStatus(result.getStatus());
			resp.setMessage(result.getMessage());
		}
		return JSONObject.fromObject(resp).toString();
	}
}
