package com.thirdleave.readingplan.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thirdleave.readingplan.service.WebSocketServer;
@RestController
public class SocketController {

	@RequestMapping(value = "/chat", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> pushVideoListToWeb(@RequestBody Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			WebSocketServer.sendInfo("有新客户呼入,sltAccountId:" + param.get("userID"));
			result.put("operationResult", true);
		} catch (IOException e) {
			result.put("operationResult", true);
		}
		return result;

	}
}
