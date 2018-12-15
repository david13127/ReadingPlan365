package com.thirdleave.readingplan.constant;

public interface IRedisTableKey {

    public final static String TBL_USER = "user";

    public final static String TBL_USER_USERID = "userID";

    public final static String TBL_USER_PASSWORD = "password";

    public final static String TBL_USER_NAME = "name";

    public final static String TBL_USER_SEX = "sex";

    public final static String TBL_USER_BIFTHDATE = "birthDate";

    public final static String TBL_USER_REMARK = "remark";

    public final static String TBL_USER_EMAIL = "emailAddress";

    public final static String TBL_USERAUTH = "userAuth";

    public final static String TBL_USERAUTH_VOICELIMIT = "voiceLimitHours";

    public final static String TBL_USERAUTH_LOGINLIMIT = "loginLimitHours";

    public final static String TBL_USERAUTH_INFOMODIFYLIMIT = "infoModifyLimit";

    public final static String TBL_USERAUTH_UPLOADLIMIT = "uploadLimit";

    public final static String TBL_BOOK = "book";

    public final static String TBL_BOOK_ID = "bookID";

    public final static String TBL_BOOK_NAME = "bookName";

    public final static String TBL_BOOK_AUTHOR = "author";

    public final static String TBL_BOOK_PRICE = "price";

    public final static String TBL_BOOK_PUBLISH = "publish";

    public final static String TBL_USER_BOOK_AUTH = "userBookAuth";

    public final static String TBL_USER_BOOK_AUTH_MAJOR_KEY = TBL_USER_USERID + ":" + TBL_BOOK_ID;

    public final static String TBL_USER_BOOK_AUTH_DOWNLOADLIMIT = "downLoadLimit";

    public final static String TBL_USER_BOOK_AUTH_ODIFYLIMIT = "odifyLimit";

    public final static String TBL_USER_BOOK_AUTH_SHARELIMIT = "shareLimit";

    public final static String TBL_USER_BOOK_AUTH_DELETELIMIT = "downLoadLimit";
}
