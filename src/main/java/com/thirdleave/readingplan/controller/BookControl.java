package com.thirdleave.readingplan.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.thirdleave.readingplan.config.PropertiesConfig;
import com.thirdleave.readingplan.constant.IJsonKey;
import com.thirdleave.readingplan.constant.IResultConstant;
import com.thirdleave.readingplan.service.IBookService;
import com.thirdleave.readingplan.service.po.BookPO;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.utils.UuID;

@Controller
public class BookControl {

	@Autowired
	private IBookService bookService;

	@Autowired
	private FileController fileControl;

	
	public ResultPO upLoadBook(HttpServletRequest request) throws IOException {
		ResultPO result = new ResultPO();
		MultipartFile file= ((MultipartHttpServletRequest) request).getFile(IJsonKey.PARA_FILENAME);
		String kind = request.getParameter(IJsonKey.PARA_KIND);
		String booksPath = PropertiesConfig.getProperties(IJsonKey.PROP_BOOKSPATH);
		String targetPath = booksPath + File.separator + kind;
		result =  fileControl.fileUpload(file, targetPath);
		if (IResultConstant.STATUS_OK.equals(result.getStatus())) {
			BookPO newBook = new BookPO();
			newBook.setKind(kind);
			newBook.setBookName(file.getOriginalFilename());
			ResultPO addBook = addBook(newBook);
			result.setResult(addBook.getResult());
		}
		return result;
	}
	
	
	public ResultPO downLoadBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResultPO result = new ResultPO();
		String bookID = request.getParameter("bookID");
		BookPO bookDB = bookService.queryBookByID(bookID);
		if (bookDB == null) {
			result.setResult(IResultConstant.STATUS_ERROR);
			result.setMessage("书籍没有录入");
			return result;
		}
		String bookNme = new String(request.getParameter("bookName").getBytes(), "UTF-8");
		String bookPath = PropertiesConfig.getProperties("bookspath");
		File file = new File(bookPath + File.separator + "novel" + File.separator + bookNme);
		result =  fileControl.downLoad(request, response, file);
		result.setResult(bookDB);
		return result;
	}
	
	public ResultPO addBook(BookPO book) {
		ResultPO response = new ResultPO();
		String bookID = UuID.generateShortUuid();
		book.setBookID(bookID);
		book.setPath(PropertiesConfig.getProperties("bookspath") + File.separator + book.getKind());
		response = bookService.bookAdd(book);
		return response;
	}

}
