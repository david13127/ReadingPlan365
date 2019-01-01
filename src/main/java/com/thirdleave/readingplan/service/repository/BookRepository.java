package com.thirdleave.readingplan.service.repository;

import com.thirdleave.readingplan.service.po.BookPO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<BookPO, String> {

	List<BookPO> findByBookID(String bookID);

	Page<BookPO> findByAuthor(String author, Pageable pageable);

	Page<BookPO> findByBookName(String bookName, Pageable pageable);

	Page<BookPO> findByBookNameLike(String bookName, Pageable pageable);

	Page<BookPO> findByContentLike(String content, Pageable pageable);
	
	Page<BookPO> findByBookNameNot(String bookName, Pageable pageable);

	Page<BookPO> findByKind(String kind, Pageable pageable);

	Page<BookPO> findByPriceBetween(Long priceFrom, Long priceTo, Pageable pageable);
}
