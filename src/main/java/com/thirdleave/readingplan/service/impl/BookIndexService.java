package com.thirdleave.readingplan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thirdleave.readingplan.service.IBookIndexService;
import com.thirdleave.readingplan.service.po.BookPO;
import com.thirdleave.readingplan.service.repository.BookRepository;

@Service
public class BookIndexService implements IBookIndexService {
	private static final Integer pageNum = 0;

	private static final Integer pageSize = 10;

	Pageable pageable = PageRequest.of(pageNum, pageSize);

	@Autowired
	BookRepository bookRepository;

	@Override
	public BookPO save(BookPO book) {
		return bookRepository.save(book);
	}

	@Override
	public List<BookPO> findByBookID(String bookID) {
		return bookRepository.findByBookID(bookID);
	}

	@Override
	public List<BookPO> findByAuthor(String author) {
		return bookRepository.findByAuthor(author, pageable).getContent();
	}

	@Override
	public List<BookPO> findByBookName(String bookName) {
		return bookRepository.findByBookName(bookName, pageable).getContent();
	}

	@Override
	public List<BookPO> findByBookNameLike(String bookName) {
		return bookRepository.findByBookNameLike(bookName, pageable).getContent();
	}

	@Override
	public List<BookPO> findByContentLike(String content) {
		return bookRepository.findByBookNameLike(content, pageable).getContent();
	}

	@Override
	public List<BookPO> findByBookNameNot(String bookName) {
		return bookRepository.findByBookNameNot(bookName, pageable).getContent();
	}

	@Override
	public List<BookPO> findByKind(String kind) {
		return bookRepository.findByKind(kind, pageable).getContent();
	}

	@Override
	public List<BookPO> findByPriceBetween(Long priceFrom, Long priceTo) {
		return bookRepository.findByPriceBetween(priceFrom, priceTo, pageable).getContent();
	}
}
