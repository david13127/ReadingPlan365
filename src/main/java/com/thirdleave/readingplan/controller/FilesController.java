package com.thirdleave.readingplan.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FilesController {

    /**
     * @param file
     * @return String
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file) {
        if (file.isEmpty()) {
            return "false";
        }
        String fileName = file.getOriginalFilename();

        String path = "F:/test";
        File dest = new File(path + File.separator + fileName);
        // 判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            // 保存文件
            file.transferTo(dest);
            return "true";
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
            return "false";
        }
        catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 实现多文件上传
     */
    @RequestMapping(value = "multifileUpload", method = RequestMethod.POST)
    @ResponseBody
    public String multifileUpload(HttpServletRequest request) {

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");

        if (files.isEmpty()) {
            return "false";
        }

        String path = "F:/test";

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (file.isEmpty()) {
                return "false";
            }
            else {
                File dest = new File(path + "/" + fileName);
                // 判断文件父目录是否存在
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return "false";
                }
            }
        }
        return "true";
    }

    @RequestMapping("download")
    public String downLoad(HttpServletRequest request, HttpServletResponse response) {
        String filename = request.getParameter("filename");
        String filePath = request.getParameter("filePath");
        File file = new File(filePath + "/" + filename);

        // 判断文件父目录是否存在
        if (file.exists()) {
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

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

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
