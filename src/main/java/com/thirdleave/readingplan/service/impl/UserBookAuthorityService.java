package com.thirdleave.readingplan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirdleave.readingplan.constant.IRedisTableKey;
import com.thirdleave.readingplan.constant.IResultConstant;
import com.thirdleave.readingplan.service.IUserBookAuthorityService;
import com.thirdleave.readingplan.service.po.BookPO;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserBookAuthorityPO;
import com.thirdleave.readingplan.service.po.UserPO;
import com.thirdleave.readingplan.utils.ReflectUtils;
import com.thirdleave.readingplan.utils.redis.JedisUtils;
import com.thirdleave.readingplan.utils.redis.RedisKeyUtil;

@Service
@Transactional
public class UserBookAuthorityService implements IUserBookAuthorityService {

	@Override
	public void initAuthority(UserBookAuthorityPO authority) {
		authority.setReadLimit("true");
		authority.setModifyLimit("false");
		authority.setShareLimit("false");
		authority.setDownloadLimit("false");
		authority.setDeleteLimit("false");
	}

	@Override
	public ResultPO addAuthority(UserPO user, BookPO book, UserBookAuthorityPO authority) {
		initAuthority(authority);
		ResultPO result = new ResultPO();
		String userID = user.getUserID();
		String bookID = book.getBookID();
		String authTableKey = RedisKeyUtil.getKey(IRedisTableKey.TBL_USER_BOOK_AUTH,
				IRedisTableKey.TBL_USER_BOOK_AUTH_MAJOR_KEY, userID + "_&_" + bookID);
		Map<String, String> authInfo = null;
		try {
			authInfo = ReflectUtils.objectToMap(authority);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String status = JedisUtils.hmset(authTableKey, authInfo);
		if (IResultConstant.STATUS_OK.equals(status)) {
			UserBookAuthorityPO authorityDB = queryAuthorityByUserAndBook(user, book);
			if (authorityDB != null) {
				result.setStatus(status);
				result.setResult(authorityDB);
			} else {
				result.setStatus(IResultConstant.STATUS_ERROR);
				result.setMessage(status);
			}
		}
		return null;
	}

	@Override
	public ResultPO updateAuthority(UserPO user, BookPO book, UserBookAuthorityPO authority) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO updateAuthorityWithBook(UserPO user, BookPO book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO updateAuthorityWithBookFiled(UserPO user, BookPO book, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBookAuthorityPO queryAuthorityByUserAndBook(UserPO user, BookPO book) {
		String userID = user.getUserID();
		String bookID = book.getBookID();
		String key = RedisKeyUtil.getKey(IRedisTableKey.TBL_USER_BOOK_AUTH, IRedisTableKey.TBL_USER_BOOK_AUTH_MAJOR_KEY,
				userID + "_&_" + bookID);
		Map<String, String> userBookAuthorityInfo = JedisUtils.hgetAll(key);
		UserBookAuthorityPO userBookAuthorityDB = null;
		try {
			userBookAuthorityDB = (UserBookAuthorityPO) ReflectUtils.mapToObject(userBookAuthorityInfo,
					UserBookAuthorityPO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBookAuthorityDB;
	}

	@Override
	public List<UserBookAuthorityPO> queryAuthorityByUser(UserPO user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserBookAuthorityPO> queryAuthorityByBook(BookPO book) {
		// TODO Auto-generated method stub
		return null;
	}

}
