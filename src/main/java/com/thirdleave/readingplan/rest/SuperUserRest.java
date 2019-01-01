package com.thirdleave.readingplan.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.thirdleave.readingplan.controller.BookParseControl;
import com.thirdleave.readingplan.service.IBookIndexService;
import com.thirdleave.readingplan.service.IUserService;
import com.thirdleave.readingplan.service.po.BookPO;
import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserPO;

@Controller
@RequestMapping("/admin")
public class SuperUserRest {
	private final static Logger LOG = LoggerFactory.getLogger(SuperUserRest.class);

	@Value("${bookspath}")
	private String uploadPath;
	@Autowired
	private IUserService userService;

	@Autowired
	private IBookIndexService bookIndexService;

	@Autowired
	private BookParseControl bookParseControl;

	@GetMapping(value = "/indexPage")
	public String loginPage() {
		return "index";
	}

	@GetMapping(value = "/uploadPage")
	public String bookUploadPage() {
		return "book/bookUpload";
	}

	@GetMapping(value = "/downloadPage")
	public ModelAndView bookDownloadPage() {
		ModelAndView mv = new ModelAndView();
		ModelMap modelMap = mv.getModelMap();
		List<BookPO> testList = new ArrayList<BookPO>();
		testList.add(new BookPO("b1", "book1", "历史"));
		testList.add(new BookPO("b2", "book2", "小说"));
		testList.add(new BookPO("b3", "book3", "科技"));
		modelMap.addAttribute("bookList", testList);
		mv.setViewName("book/bookDownload");
		return mv;
	}

	@RequestMapping(value = "/bookupload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bookupload(HttpServletRequest request) {
		String kind = request.getParameter("kind");
		System.out.println(kind);
		MultipartHttpServletRequest mhsr = null;
		if (request instanceof MultipartHttpServletRequest) {
			mhsr = (MultipartHttpServletRequest) request;
		}
		List<MultipartFile> multiFiles = mhsr.getFiles("bookfile");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (MultipartFile uploadFile : multiFiles) {
			String fullFileName = uploadFile.getOriginalFilename();
			int index1 = fullFileName.lastIndexOf(File.separator);
			String fileName = fullFileName.substring(index1 + 1, fullFileName.length());
			try {
				String targetPath = uploadPath + File.separator + kind;
				File file = new File(targetPath + File.separator + fullFileName);
				// 判断文件父目录是否存在
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdir();
				}
				uploadFile.transferTo(file);
				BookPO book = bookParseControl.parseBook(file.getAbsolutePath());
				bookIndexService.save(book);
				resultMap.put(fileName, true);
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put(fileName, false);
				LOG.error(fileName + "上传失败," + e.getMessage());
			}
		}
		return resultMap;
	}

	@RequestMapping("/adminLogin")
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
}