package com.thirdleave.readingplan.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thirdleave.readingplan.controller.UserControl;
import com.thirdleave.readingplan.service.po.UserPO;

@RestController
@RequestMapping(value = "/user")
public class UserRest {

    @Autowired
    private UserControl userControl;

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String userLogin(@RequestParam(value = "userID") String userID, @RequestParam(value = "password") String password) {
        UserPO user = new UserPO();
        user.setUserID(userID);
        user.setPassword(password);
        return userControl.userLogin(user);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String userRegister(@RequestBody UserPO user) {
        return userControl.userRegister(user);
    }
    
}
