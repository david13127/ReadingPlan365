package com.thirdleave.readingplan.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.thirdleave.readingplan.constant.IResultConstant;
import com.thirdleave.readingplan.service.po.ResultPO;

@Controller
public class FileController {

	/**
	 * @param file
	 * @return String
	 */
	public ResultPO fileUpload(MultipartFile file, String path) {
		ResultPO result = new ResultPO();
		if (file.isEmpty()) {
			result.setStatus(IResultConstant.STATUS_ERROR);
			result.setMessage("没有选择文件");
		}
		String fileName = file.getOriginalFilename();

		File dest = new File(path + File.separator + fileName);
		// 判断文件父目录是否存在
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdir();
		}
		try {
			// 保存文件
			file.transferTo(dest);
			result.setStatus(IResultConstant.STATUS_OK);
			result.setMessage("上传成功");
		} catch (Exception e) {
			result.setStatus(IResultConstant.STATUS_ERROR);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 实现多文件上传
	 */
	public ResultPO multifileUpload(HttpServletRequest request, String path) {
		ResultPO result = new ResultPO();
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileName");

		if (files.isEmpty()) {
			result.setStatus(IResultConstant.STATUS_ERROR);
			result.setMessage("没有选择文件");
		}

		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			if (file.isEmpty()) {
				result.setStatus(IResultConstant.STATUS_ERROR);
				result.setMessage("文件为空");
			} else {
				File dest = new File(path + "/" + fileName);
				// 判断文件父目录是否存在
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdir();
				}
				try {
					file.transferTo(dest);
				} catch (Exception e) {
					result.setStatus(IResultConstant.STATUS_ERROR);
					result.setMessage(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		result.setStatus(IResultConstant.STATUS_OK);
		result.setMessage("上传成功");
		return result;
	}

	public ResultPO downLoad(HttpServletRequest request, HttpServletResponse response, File file) throws IOException {
		ResultPO result = new ResultPO();
		// 判断文件父目录是否存在
		if (file.exists()) {
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			response.setContentType(request.getServletContext().getMimeType(fileName));
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			byte[] buffer = new byte[1024];
			// 文件输入流
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			// 输出流
			OutputStream os = null;
			try {
				os = response.getOutputStream();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer);
					i = bis.read(buffer);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				result.setStatus(IResultConstant.STATUS_OK);
				result.setMessage("下载成功");
			}
			try {
				bis.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			result.setResult(IResultConstant.STATUS_ERROR);
			result.setMessage("文件不存在");
			return result;
		}
		return result;
	}

}
