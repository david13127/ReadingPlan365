package com.thirdleave.readingplan.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirdleave.readingplan.service.IUserService;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserAuthorityPO;
import com.thirdleave.readingplan.service.po.UserPO;
import com.thirdleave.readingplan.utils.JedisUtils;
import com.thirdleave.readingplan.utils.ReflectUtils;
import com.thirdleave.readingplan.utils.redis.RedisKeyUtil;

@Service
@Transactional
public class UserService implements IUserService {

	@Override
	public UserPO findUserByID(String userID) {
		String key = RedisKeyUtil.getKey("user", "userID", userID);
		Map<String, String> userInfo = JedisUtils.hgetAll(key);
		UserPO userDB = null;
		try {
			userDB = (UserPO) ReflectUtils.mapToObject(userInfo, UserPO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDB;
	}

	@Override
	public ResultPO setUserID(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO setUserName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO setUserPassword(String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO setUserSex(String sex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO setUserBirthday(Date birthday) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO setUserRemark(String remark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO setUserEmailAddress(String emailAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO userRegister(UserPO user) {
		ResultPO registerResult = new ResultPO();
		String userID = user.getUserID();
		String userTablekey = RedisKeyUtil.getKey("user", "userID", userID);
		boolean hasKey = JedisUtils.exists(userTablekey);
		if (hasKey) {
			registerResult.setStatus("error");
			registerResult.setMessage("UserID/Name already exists");
		} else {
			Map<String, String> userInfo = null;
			try {
				userInfo = ReflectUtils.objectToMap(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String status = JedisUtils.hmset(userTablekey, userInfo);
			if ("OK".equals(status)) {
				UserPO userDB = findUserByID(user.getUserID());
				if (userDB != null) {
					registerResult.setStatus(status);
					registerResult.setResults(userDB);
				}
				else {
					registerResult.setStatus("error");
					registerResult.setMessage(status);
				}
			}
		}
		return registerResult;
	}

	@Override
	public ResultPO userLogin(UserPO user) {
		ResultPO loginResult = new ResultPO();
		String userID = user.getUserID();
		String password = user.getPassword();
		String userTablekey = RedisKeyUtil.getKey("user", "userID", userID);
		boolean hasKey = JedisUtils.exists(userTablekey);
		if (hasKey) {
			Map<String, String> userInfo = JedisUtils.hgetAll(userTablekey);
			UserPO userResult = null;
			try {
				userResult = (UserPO) ReflectUtils.mapToObject(userInfo, UserPO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (password.equals(userResult.getPassword())) {
				String authTableKey = RedisKeyUtil.getKey("userAuth", "userID", userID);
				Map<String, String> authInfo = JedisUtils.hgetAll(authTableKey);
				UserAuthorityPO authority = null;
				try {
					authority = (UserAuthorityPO) ReflectUtils.mapToObject(authInfo, UserAuthorityPO.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				int loginLimitHours = Integer.valueOf(authority.getLoginLimitHours());
				if (loginLimitHours <= 0) {
					loginResult.setStatus("OK");
					loginResult.setResults(userResult);
				} else {
					loginResult.setStatus("Limit");
					loginResult.setMessage("Login limited in " + loginLimitHours + " hours");
				}
			} else {
				loginResult.setStatus("error");
				loginResult.setMessage("Password error");
			}

		} else {
			loginResult.setStatus("error");
			loginResult.setMessage("UserID/Name error");
		}
		return loginResult;
	}

	@Override
	public ResultPO changePassword(String newPassword, String oldPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO userDelete(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO userModify(UserPO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO userAuthorityModify(UserPO user) {
		// TODO Auto-generated method stub
		return null;
	}
}
