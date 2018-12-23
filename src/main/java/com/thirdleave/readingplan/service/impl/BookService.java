package com.thirdleave.readingplan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirdleave.readingplan.constant.IRedisTableKey;
import com.thirdleave.readingplan.constant.IResultConstant;
import com.thirdleave.readingplan.service.IBookService;
import com.thirdleave.readingplan.service.po.BookPO;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.utils.ReflectUtils;
import com.thirdleave.readingplan.utils.redis.JedisUtils;
import com.thirdleave.readingplan.utils.redis.RedisKeyUtil;

@Service
@Transactional
public class BookService implements IBookService {

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
		boolean hasKey = JedisUtils.exists(bookTablekey);
		if (hasKey) {
			registerResult.setStatus(IResultConstant.STATUS_ERROR);
			registerResult.setMessage("BookID/Name already exists");
		} else {
			Map<String, String> bookInfo = null;
			try {
				bookInfo = ReflectUtils.objectToMap(book);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String status = JedisUtils.hmset(bookTablekey, bookInfo);
			if (IResultConstant.STATUS_OK.equals(status)) {
				BookPO bookDB = queryBookByID(book.getBookID());
				if (bookDB != null) {
					registerResult.setStatus(status);
					registerResult.setResult(bookDB);
				} else {
					registerResult.setStatus(IResultConstant.STATUS_ERROR);
					registerResult.setMessage(status);
				}
			}
		}
		return registerResult;
	}

	@Override
	public BookPO queryBookByID(String bookID) {
		String key = RedisKeyUtil.getKey(IRedisTableKey.TBL_BOOK, IRedisTableKey.TBL_BOOK_ID, bookID);
		Map<String, String> bookInfo = JedisUtils.hgetAll(key);
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
