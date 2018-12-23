package com.thirdleave.readingplan.service;

import java.util.List;

import com.thirdleave.readingplan.service.po.BookPO;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserBookAuthorityPO;
import com.thirdleave.readingplan.service.po.UserPO;

public interface IUserBookAuthorityService {
	
	void initAuthority(UserBookAuthorityPO authority);
	
	ResultPO addAuthority(UserPO user, BookPO book, UserBookAuthorityPO authority);
	
	ResultPO updateAuthority(UserPO user, BookPO book, UserBookAuthorityPO authority);
	
	ResultPO updateAuthorityWithBook(UserPO user, BookPO book);
	
	ResultPO updateAuthorityWithBookFiled(UserPO user, BookPO book, String field);
	
	UserBookAuthorityPO queryAuthorityByUserAndBook(UserPO user, BookPO book);
	
	List<UserBookAuthorityPO> queryAuthorityByUser(UserPO user);
	
	List<UserBookAuthorityPO> queryAuthorityByBook(BookPO book);
}
