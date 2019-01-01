package com.thirdleave.readingplan.service.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "book", type = "book", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class BookPO {

	@Id
	private String bookID;

	@Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
	private String bookName;

	private String kind;

	private String path;

	private String author;

	private String price;

	private String publish;

	@Field(type = FieldType.Text, analyzer = "standard1", searchAnalyzer = "standard")
	private String description;

	@Field(type = FieldType.Text, analyzer = "standard2", searchAnalyzer = "standard")
	private String content;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
