package com.thirdleave.readingplan.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thirdleave.readingplan.controller.BookControl;
import com.thirdleave.readingplan.service.po.BookPO;
import com.thirdleave.readingplan.service.po.ResultPO;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/book")
public class BookRest {
	
	@Autowired
	private BookControl bookControl;
	
	@RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String downLoadBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResultPO result = bookControl.downLoadBook(request, response);
		return JSONObject.fromObject(result).toString();
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String upLoadBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResultPO result = bookControl.downLoadBook(request, response);
		return JSONObject.fromObject(result).toString();
	}
	
	@RequestMapping(value = "/addbook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addBook(@RequestBody BookPO book) {
		ResultPO addResult = bookControl.addBook(book);
		return JSONObject.fromObject(addResult).toString();
	}
	
}
