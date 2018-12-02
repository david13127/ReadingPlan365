package com.thirdleave.readingplan.service.po;

public class BookAuthorityPO {
	private String downLoadLimit;
	private String modifyLimit;
	private String shareLimit;
	private String deleteLimit;

	public String getDownLoadLimit() {
		return downLoadLimit;
	}

	public void setDownLoadLimit(String downLoadLimit) {
		this.downLoadLimit = downLoadLimit;
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
