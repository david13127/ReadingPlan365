package com.thirdleave.readingplan.service.po;

public class UserAuthorityPO {

    private String voiceLimitHours;

    private String loginLimitHours;

    private String infoModifyLimit;

    private String uploadLimit;

    public String getVoiceLimitHours() {
        return voiceLimitHours;
    }

    public void setVoiceLimitHours(String voiceLimitHours) {
        this.voiceLimitHours = voiceLimitHours;
    }

    public String getLoginLimitHours() {
        return loginLimitHours;
    }

    public void setLoginLimitHours(String loginLimitHours) {
        this.loginLimitHours = loginLimitHours;
    }

    public String getInfoModifyLimit() {
        return infoModifyLimit;
    }

    public void setInfoModifyLimit(String infoModifyLimit) {
        this.infoModifyLimit = infoModifyLimit;
    }

    public String getUploadLimit() {
        return uploadLimit;
    }

    public void setUploadLimit(String uploadLimit) {
        this.uploadLimit = uploadLimit;
    }

}
