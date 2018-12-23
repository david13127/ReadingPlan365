package com.thirdleave.readingplan.service;

import com.thirdleave.readingplan.service.po.ResultPO;

import java.util.List;

import com.thirdleave.readingplan.service.po.BookPO;

public interface IBookService {

	ResultPO bookDelete(String bookID);

	ResultPO bookModify(BookPO book);

	ResultPO setBookPath(String path);
	
	ResultPO bookAdd(BookPO book);

	BookPO queryBookByID(String bookID);
	
	List<BookPO> queryBooksByKind(String kind);
}
