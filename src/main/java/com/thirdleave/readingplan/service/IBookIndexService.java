package com.thirdleave.readingplan.service;

import java.util.List;

import com.thirdleave.readingplan.service.po.BookPO;

public interface IBookIndexService {

	BookPO save(BookPO book);

	List<BookPO> findByBookID(String bookID);

	List<BookPO> findByAuthor(String author);

	List<BookPO> findByBookName(String bookName);

	List<BookPO> findByBookNameLike(String bookName);
	
	List<BookPO> findByContentLike(String content);
	
	List<BookPO> findByBookNameNot(String bookName);

	List<BookPO> findByKind(String kind);

	List<BookPO> findByPriceBetween(Long priceFrom, Long priceTo);
}
