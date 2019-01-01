package com.thirdleave.readingplan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirdleave.readingplan.constant.IRedisTableKey;
import com.thirdleave.readingplan.constant.IResultConstant;
import com.thirdleave.readingplan.service.IBookService;
import com.thirdleave.readingplan.service.po.BookPO;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.utils.ReflectUtils;
import com.thirdleave.readingplan.utils.redis.RedisKeyUtil;
import com.thirdleave.readingplan.utils.redis.RedisUtils;

@Service
@Transactional
public class BookService implements IBookService {

	@Autowired
	private RedisUtils redisUtils;
	
	@Override
	public ResultPO bookDelete(String bookID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO bookModify(BookPO book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO bookAdd(BookPO book) {
		ResultPO registerResult = new ResultPO();
		String bookID = book.getBookID();
		String bookTablekey = RedisKeyUtil.getKey(IRedisTableKey.TBL_BOOK, IRedisTableKey.TBL_BOOK_ID, bookID);
		boolean hasKey = redisUtils.hasKey(bookTablekey);
		if (hasKey) {
			registerResult.setStatus(IResultConstant.STATUS_ERROR);
			registerResult.setMessage("BookID/Name already exists");
		} else {
			Map<String, Object> bookInfo = null;
			try {
				bookInfo = ReflectUtils.objectToMap(book);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean status = redisUtils.hmset(bookTablekey, bookInfo);
			if (status) {
				BookPO bookDB = queryBookByID(book.getBookID());
				if (bookDB != null) {
					registerResult.setStatus(IResultConstant.STATUS_OK);
					registerResult.setResult(bookDB);
				} else {
					registerResult.setStatus(IResultConstant.STATUS_ERROR);
					registerResult.setMessage("添加失败");
				}
			}
		}
		return registerResult;
	}

	@Override
	public BookPO queryBookByID(String bookID) {
		String key = RedisKeyUtil.getKey(IRedisTableKey.TBL_BOOK, IRedisTableKey.TBL_BOOK_ID, bookID);
		Map<Object, Object> bookInfo = redisUtils.hmget(key);
		BookPO bookDB = null;
		try {
			bookDB = (BookPO) ReflectUtils.mapToObject(bookInfo, BookPO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookDB;
	}

	@Override
	public List<BookPO> queryBooksByKind(String kind) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultPO setBookPath(String path) {
		// TODO Auto-generated method stub
		return null;
	}

}
