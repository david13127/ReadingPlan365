package com.thirdleave.readingplan.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirdleave.readingplan.constant.IRedisTableKey;
import com.thirdleave.readingplan.constant.IResultConstant;
import com.thirdleave.readingplan.service.IUserAuthorityService;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserAuthorityPO;
import com.thirdleave.readingplan.service.po.UserPO;
import com.thirdleave.readingplan.utils.ReflectUtils;
import com.thirdleave.readingplan.utils.redis.JedisUtils;
import com.thirdleave.readingplan.utils.redis.RedisKeyUtil;

@Service
@Transactional
public class UserAuthorityService implements IUserAuthorityService {

	@Override
	public void initAuthority(UserAuthorityPO authority) {
		authority.setInfoModifyLimit("true");
		authority.setLoginLimitHours("0");
		authority.setUploadLimit("false");
		authority.setVoiceLimitHours("0");
	}

	@Override
	public ResultPO addAuthority(UserPO user, UserAuthorityPO authority) {
		initAuthority(authority);
		ResultPO result = new ResultPO();
		String userID = user.getUserID();
		String authTableKey = RedisKeyUtil.getKey(IRedisTableKey.TBL_USERAUTH, IRedisTableKey.TBL_USER_ID, userID);
		Map<String, String> authInfo = null;
		try {
			authInfo = ReflectUtils.objectToMap(authority);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String status = JedisUtils.hmset(authTableKey, authInfo);
		if (IResultConstant.STATUS_OK.equals(status)) {
			UserAuthorityPO authorityDB = queryAuthorityByUser(user);
			if (authorityDB != null) {
				result.setStatus(status);
				result.setResult(authorityDB);
			} else {
				result.setStatus(IResultConstant.STATUS_ERROR);
				result.setMessage(status);
			}
		}

		return result;
	}

	@Override
	public ResultPO updateAuthority(UserPO user, UserAuthorityPO authority) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO updateAuthorityWithField(UserPO user, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAuthorityPO queryAuthorityByUser(UserPO user) {
		String userID = user.getUserID();
        String key = RedisKeyUtil.getKey(IRedisTableKey.TBL_USERAUTH, IRedisTableKey.TBL_USER_ID, userID);
        Map<String, String> userAuthorityInfo = JedisUtils.hgetAll(key);
        UserAuthorityPO userAuthorityDB = null;
        try {
            userAuthorityDB = (UserAuthorityPO)ReflectUtils.mapToObject(userAuthorityInfo, UserAuthorityPO.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return userAuthorityDB;
	}

}
