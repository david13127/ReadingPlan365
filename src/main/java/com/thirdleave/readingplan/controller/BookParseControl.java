package com.thirdleave.readingplan.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.thirdleave.readingplan.service.po.BookPO;
import com.thirdleave.readingplan.utils.UuID;

import net.sf.json.JSONObject;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;

@Controller
public class BookParseControl {

	@Value("${tempfile}")
	private String tempfilePath;

	public BookPO parseBook(String path) {
		int index1 = path.lastIndexOf(File.separator);
		int index2 = path.lastIndexOf(".");
		String fileName = path.substring(index1 + 1, path.length());
		String fileType = path.substring(index2 + 1, path.length());
		String bookID = UuID.generateShortUuid();
		BookPO book = new BookPO(bookID, fileName);
		String content = "";
		switch (fileType) {
		case "txt":
			content = parseTXT(path);
			break;
		case "epub":
			content = parseEpub(path);
			break;
		case "pdf":
			content = parsePDF(path);
			break;
		case "docx":
			content = parseDocx(path);
			break;
		case "doc":
			content = parseDoc(path);
			break;
		default:
			break;
		}
		book.setContent(content);
		return book;
	}

	private String parseTXT(String path) {

		File file = new File(path);
		int index1 = path.lastIndexOf(File.separator);
		int index2 = path.lastIndexOf(".");
		String fileName = path.substring(index1 + 1, index2);
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		JSONObject bookObj = new JSONObject();
		try {
			fileInputStream = new FileInputStream(file);
			String charset = getCharset(path);
			inputStreamReader = new InputStreamReader(fileInputStream, charset);
			bufferedReader = new BufferedReader(inputStreamReader);

			StringBuffer sb = new StringBuffer();
			String text = null;
			while ((text = bufferedReader.readLine()) != null) {
				sb.append(text);
			}
			String content = sb.toString();
			JSONObject contentObj = new JSONObject();
			contentObj.put("content", content);
			bookObj.put(fileName, contentObj);
			writeTempFile(fileName, bookObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null && inputStreamReader != null && fileInputStream != null) {
				try {
					bufferedReader.close();
					inputStreamReader.close();
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bookObj.toString();
	}

	private String parseDoc(String path) {
		int index1 = path.lastIndexOf(File.separator);
		int index2 = path.lastIndexOf(".");
		String fileName = path.substring(index1 + 1, index2);

		JSONObject bookObj = new JSONObject();
		String content = "";

		File file = new File(path);
		FileInputStream fis = null;
		HWPFDocument doc = null;
		try {
			fis = new FileInputStream(file);
			doc = new HWPFDocument(fis);
			Range rang = doc.getRange();
			content = rang.text();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null && doc != null) {
				try {
					doc.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		JSONObject contentObj = new JSONObject();
		contentObj.put("content", content);
		bookObj.put(fileName, contentObj);
		writeTempFile(fileName, bookObj.toString());
		return bookObj.toString();
	}

	private String parseDocx(String path) {
		int index1 = path.lastIndexOf(File.separator);
		int index2 = path.lastIndexOf(".");
		String fileName = path.substring(index1 + 1, index2);

		JSONObject bookObj = new JSONObject();
		String content = "";

		File file = new File(path);
		FileInputStream fis = null;
		XWPFDocument xdoc = null;
		XWPFWordExtractor extractor = null;
		try {
			fis = new FileInputStream(file);
			xdoc = new XWPFDocument(fis);
			extractor = new XWPFWordExtractor(xdoc);
			content = extractor.getText();
			JSONObject contentObj = new JSONObject();
			contentObj.put("content", content);
			bookObj.put(fileName, contentObj);
			writeTempFile(fileName, bookObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (extractor != null && xdoc != null & fis != null) {
				try {
					extractor.close();
					xdoc.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bookObj.toString();
	}

	private String parseEpub(String path) {
		int index1 = path.lastIndexOf(File.separator);
		int index2 = path.lastIndexOf(".");
		String fileName = path.substring(index1 + 1, index2);
		JSONObject bookObj = new JSONObject();
		JSONObject contentObj = new JSONObject();
		try {
			EpubReader epubReader = new EpubReader();
			MediaType[] lazyTypes = { MediatypeService.CSS, MediatypeService.GIF, MediatypeService.JPG,
					MediatypeService.PNG, MediatypeService.MP3, MediatypeService.MP4 };
			Book book = epubReader.readEpubLazy(path, "UTF-8", Arrays.asList(lazyTypes));

			// 通过spine获取线性的阅读菜单，此菜单依照阅读顺序排序
			List<SpineReference> spineReferences = book.getSpine().getSpineReferences();
			// 获取对应resource
			spineReferences.forEach(reference -> {
				Resource resource = reference.getResource();
				try {
					String referenceContent = new String(resource.getData());
					contentObj.put(resource.getId(), referenceContent);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			bookObj.put(fileName, contentObj);
			writeTempFile(fileName, bookObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookObj.toString();
	}

	public String parsePDF(String path) {

		int index1 = path.lastIndexOf(File.separator);
		int index2 = path.lastIndexOf(".");
		String fileName = path.substring(index1 + 1, index2);
		JSONObject bookObj = new JSONObject();
		RandomAccessBufferedFileInputStream infile = null;
		try {
			infile = new RandomAccessBufferedFileInputStream(path);// 创建一个文件输入流
			// 新建一个PDF解析器对象
			PDFParser parser = new PDFParser(infile);
			// 对PDF文件进行解析
			parser.parse();
			// 获取解析后得到的PDF文档对象
			PDDocument pdfdocument = parser.getPDDocument();
			// 新建一个PDF文本剥离器
			PDFTextStripper stripper = new PDFTextStripper();
			// 从PDF文档对象中剥离文本
			String content = stripper.getText(pdfdocument);
			JSONObject contentObj = new JSONObject();
			contentObj.put("content", content);
			bookObj.put(fileName, contentObj);
			writeTempFile(fileName, bookObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (infile != null) {
				try {
					infile.close();
				} catch (IOException e1) {
				}
			}
		}
		return bookObj.toString();
	}

	private void writeTempFile(String fileName, String content) {
		// 输出txt文本路径
		String targetPath = tempfilePath + File.separator + fileName + ".txt";
		File targetFile = new File(targetPath);
		if (!targetFile.getParentFile().isFile() && !targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		PrintWriter writer = null;
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(targetPath);
			writer = new PrintWriter(os);
			writer.write(content);// 写入文件内容
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null && os != null) {
				try {
					os.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getCharset(String fileName) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
		case 0xfeff:
			code = "UTF-16BE";
		default:
			code = "GBK";
		}
		if (bin != null) {
			bin.close();
		}
		return code;
	}

}
