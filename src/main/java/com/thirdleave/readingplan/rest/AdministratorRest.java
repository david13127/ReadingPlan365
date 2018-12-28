package com.thirdleave.readingplan.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thirdleave.readingplan.service.IUserService;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserPO;

@Controller
@RequestMapping("/admin")
public class AdministratorRest {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}
	@ResponseBody
	@RequestMapping(value = "/greeting")
	public ModelAndView test(ModelAndView mv) {
		mv.setViewName("/greeting");
		mv.addObject("name", "欢迎使用Thymeleaf!");
		return mv;
	}

	// 无论是@RestController还是@Controller都不影响返回页面
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView getListaUtentiView() {
		ModelMap model = new ModelMap();
		model.addAttribute("name", "Spring Boot");
		return new ModelAndView("login", model);
	}

	@RequestMapping("/register")
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		System.out.println(">>> UserController - register!");
		return mv;
	}

	@RequestMapping("/userLogin")
	public ModelAndView userLogin(String username, String password, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		UserPO user = new UserPO();
		user.setName(username);
		user.setPassword(password);
		ResultPO result = userService.userLogin(user);
		if ("OK".equals(result.getStatus())) {
			UserPO userFind = (UserPO) result.getResult();
			session.setAttribute("user", userFind);

			mv.setViewName("redirect:/index.jsp");
			System.out.println("UserController -- 登录成功！");
			return mv;
		} else {
			mv.addObject("msg", "账号或密码错误！");
			mv.setViewName("login");
			System.out.println("UserController -- 登录失败！");
			return mv;
		}
	}

	@RequestMapping("/userRegister")
	public ModelAndView userRegister(String userID, String name, String password) {
		ModelAndView mv = new ModelAndView();
		UserPO user = new UserPO();
		user.setUserID(userID);
		user.setName(name);
		user.setPassword(password);
		ResultPO result = userService.userRegister(user);
		if ("OK".equals(result.getStatus())) {
			mv.addObject("msg", "注册成功!");
			mv.setViewName("login");
			return mv;
		} else {
			mv.addObject("msg", "账号错误！");
			mv.setViewName("login");
			System.out.println("UserController -- 注册失败！");
			return mv;
		}

	}
}
