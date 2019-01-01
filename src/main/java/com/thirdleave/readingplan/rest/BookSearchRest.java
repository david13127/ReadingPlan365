package com.thirdleave.readingplan.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thirdleave.readingplan.service.IBookIndexService;
import com.thirdleave.readingplan.service.po.BookPO;

@RestController
@RequestMapping(value = "/bookIndex")
public class BookSearchRest {

	@Autowired
	IBookIndexService bookIndexService;

	@PostMapping(value = "add")
	public BookPO save(@RequestBody BookPO book) {
		return bookIndexService.save(book);
	}

	@GetMapping("/find/bookID")
	public List<BookPO> findById(@RequestParam String bookID) {
		return bookIndexService.findByBookID(bookID);
	}

	@GetMapping("/find/bookName")
	public List<BookPO> findByName(@RequestParam String bookName) {
		return bookIndexService.findByBookName(bookName);
	}

	@GetMapping("/find/bookName/like")
	public List<BookPO> findByNameLike(@RequestParam String bookName) {
		return bookIndexService.findByBookNameLike(bookName);
	}

	@GetMapping("/find/content/like")
	public List<BookPO> findByContentLike(@RequestParam String content) {
		return bookIndexService.findByContentLike(content);
	}

	@GetMapping("/find/bookName/notLike")
	public List<BookPO> findByNameNot(@RequestParam String bookName) {
		return bookIndexService.findByBookNameNot(bookName);
	}

	@GetMapping("/find/price/between")
	public List<BookPO> findByPriceBetween(@RequestParam Long priceFrom, @RequestParam Long priceTo) {
		return bookIndexService.findByPriceBetween(priceFrom, priceTo);
	}
}
