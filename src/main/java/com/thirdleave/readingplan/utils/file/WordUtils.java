package com.thirdleave.readingplan.utils.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hpsf.Date;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordUtils {

	private XWPFDocument doc;// 文档

	private XWPFParagraph para;// 段落

	private Iterator<XWPFParagraph> iterator;// 段落迭代

	private XWPFTable table;// 表格

	private Iterator<XWPFTable> iteratorTable;// 表格迭代

	private List<XWPFTableRow> rows;

	private List<XWPFTableCell> cells;

	private List<XWPFParagraph> paras;

	private String url;

	/**
	 * 构造函数
	 * 
	 * @param stream
	 *            数据流
	 */
	public WordUtils(InputStream stream) {
		initialize(stream, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param url文件路径
	 */
	public WordUtils(String url) {
		initialize(null, url);
	}

	/**
	 * 构造函数
	 * 
	 * @param stream文件数据流
	 * @param filePath文本路径
	 */
	public void initialize(InputStream stream, String filePath) {
		try {
			if (stream == null) {
				InputStream is = new FileInputStream(filePath);
				this.doc = new XWPFDocument(is);
				close(is);
			} else {
				this.doc = new XWPFDocument(stream);
			}
			this.iterator = this.doc.getParagraphsIterator();
			this.iteratorTable = doc.getTablesIterator();
			this.url = filePath;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param params
	 *            参数 （需要替换的键值对）
	 */
	public void replaceInPara(Map<String, Object> params) {
		while (iterator.hasNext()) {
			this.para = iterator.next();
			this.replaceInPara(para, params);
		}
	}

	/**
	 * 替换变量
	 * 
	 * @param para
	 *            要替换的段落
	 * @param params
	 *            参数 （使用\n进行换行）
	 */
	private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
		List<XWPFRun> runs;
		Matcher matcher;
		if (matcher(para.getParagraphText()).find()) {
			runs = para.getRuns();
			for (int i = 0; i < runs.size(); i++) {
				XWPFRun run = runs.get(i);
				String runText = run.toString();
				matcher = matcher(runText);
				if (matcher.find()) {
					while ((matcher = this.matcher(runText)).find()) {
						runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
					}
					// 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
					// 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
					if (runText.indexOf("\n") > -1) {
						String[] text = runText.split("\n");
						this.para.removeRun(i);
						for (int f = text.length - 1; f >= 0; f--) {
							this.para.insertNewRun(i).setText(text[f]);
							if (f != 0) {
								this.para.insertNewRun(i).addCarriageReturn();// 硬回车
							}
						}
					} else {
						this.para.removeRun(i);
						this.para.insertNewRun(i).setText(runText);
					}
				}
			}
		}
	}

	/**
	 * 替换表格里面的变量
	 * 
	 * @param doc
	 *            要替换的文档
	 * @param params
	 *            参数
	 */
	public void replaceInTable(Map<String, Object> params) {
		while (iteratorTable.hasNext()) {
			table = iteratorTable.next();
			rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					paras = cell.getParagraphs();
					for (XWPFParagraph para : paras) {
						this.para = para;
						this.replaceInPara(this.para, params);
					}
				}
			}
		}
	}

	/**
	 * 表格内部插入行
	 * 
	 * @param tableNumber
	 *            表格序号(第几个表格)
	 * @param row
	 *            起始行号
	 * @param number
	 *            插入行数
	 */
	public void InsertNewTableRow(int tableNumber, int rown, int number) {
		try {
			Date date = new Date();
			writeFile("c:\\file2\\" + date.toString() + ".docx");
			initialize(null, this.url);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int count = 1;
		while (iteratorTable.hasNext()) {
			table = iteratorTable.next();
			if (count == tableNumber) {
				XWPFTableRow row = table.getRow(1);
				for (int f = 0; f < number; f++) {
					copyPro(row, table.insertNewTableRow(f + rown));
				}
				// table.addNewRowBetween(0, 1);
				break;
			}
			count++;
		}
	}

	/**
	 * 表格内插入数据
	 * 
	 * @param row
	 *            行号
	 * @param cell
	 *            列号
	 * @param content
	 *            内容
	 */
	public void setCell(int row, int cell, String content) {
		cells = table.getRow(row).getTableCells();
		XWPFTableCell cell2 = cells.get(cell);
		cell2.setText(content);
	}

	private void copyPro(XWPFTableRow sourceRow, XWPFTableRow targetRow) {
		// 复制行属性
		targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
		List<XWPFTableCell> cellList = sourceRow.getTableCells();
		if (null == cellList) {
			return;
		}
		// 添加列、复制列以及列中段落属性
		XWPFTableCell targetCell = null;
		for (XWPFTableCell sourceCell : cellList) {
			targetCell = targetRow.addNewTableCell();
			// 列属性
			targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
			// 段落属性
			targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
		}
	}

	/**
	 * 替换文章所有变量
	 * 
	 * @param params
	 */
	public void replaceInDoc(Map<String, Object> params) {
		replaceInPara(params);
		replaceInTable(params);
	}

	/**
	 * 正则匹配字符串
	 * 
	 * @param str
	 * @return
	 */
	private Matcher matcher(String str) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	/**
	 * 输出数据流
	 * 
	 * @param os
	 *            输出数据流
	 */
	private WordUtils write(OutputStream os) throws IOException {
		doc.write(os);
		close(os);
		return this;
	}

	/**
	 * 输出到客户端
	 * 
	 * @param fileName
	 *            输出文件名
	 */
	public WordUtils write(HttpServletResponse response, String fileName) throws IOException {
		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		write(response.getOutputStream());
		return this;
	}

	/**
	 * 输出到文件
	 * 
	 * @param fileName
	 *            输出文件名
	 */
	public WordUtils writeFile(String name) throws FileNotFoundException, IOException {
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		this.url = name;
		close(os);
		return this;
	}

	/**
	 * 关闭输入流
	 * 
	 * @param is
	 */
	private void close(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭输出流
	 * 
	 * @param os
	 */
	private void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
