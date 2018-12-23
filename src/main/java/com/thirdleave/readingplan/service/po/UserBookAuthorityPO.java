package com.thirdleave.readingplan.service.po;

public class UserBookAuthorityPO {

	private String readLimit;
	
	private String downloadLimit;

	private String modifyLimit;

	private String shareLimit;

	private String deleteLimit;

	public String getReadLimit() {
		return readLimit;
	}

	public void setReadLimit(String readLimit) {
		this.readLimit = readLimit;
	}
	
	public String getDownloadLimit() {
		return downloadLimit;
	}

	public void setDownloadLimit(String downloadLimit) {
		this.downloadLimit = downloadLimit;
	}

	public String getModifyLimit() {
		return modifyLimit;
	}

	public void setModifyLimit(String modifyLimit) {
		this.modifyLimit = modifyLimit;
	}

	public String getShareLimit() {
		return shareLimit;
	}

	public void setShareLimit(String shareLimit) {
		this.shareLimit = shareLimit;
	}

	public String getDeleteLimit() {
		return deleteLimit;
	}

	public void setDeleteLimit(String deleteLimit) {
		this.deleteLimit = deleteLimit;
	}

}
