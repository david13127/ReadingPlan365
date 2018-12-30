package com.thirdleave.readingplan.service.po;

public class BookPO {

	private String bookID;

	private String bookName;

	private String kind;

	private String path;

	private String author;

	private String price;

	private String publish;

	private String description;

	private String remark;

	public BookPO() {
	}

	public BookPO(String bookID, String bookName) {
		this.bookID = bookID;
		this.bookName = bookName;
	}

	public BookPO(String bookID, String bookName, String kind) {
		this.bookID = bookID;
		this.bookName = bookName;
		this.kind = kind;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
