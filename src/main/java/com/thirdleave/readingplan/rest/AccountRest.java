package com.thirdleave.readingplan.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: AccountRest
 * @author : david
 * @date Date : 2021年07月11日 21:17
 * @version V1.0
 */
@RestController
@RequestMapping(value = "/account")
public class AccountRest {
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String test() {
		return "test";
	}
}
