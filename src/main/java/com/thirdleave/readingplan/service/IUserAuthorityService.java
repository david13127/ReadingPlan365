package com.thirdleave.readingplan.service;

import com.thirdleave.readingplan.service.po.ResultPO;
import com.thirdleave.readingplan.service.po.UserAuthorityPO;
import com.thirdleave.readingplan.service.po.UserPO;

public interface IUserAuthorityService {
	
	void initAuthority(UserAuthorityPO authority);
			
	ResultPO addAuthority(UserPO user, UserAuthorityPO authority);
	
	ResultPO updateAuthority(UserPO user, UserAuthorityPO authority);
	
	ResultPO updateAuthorityWithField(UserPO user, String field);
	
	UserAuthorityPO queryAuthorityByUser(UserPO user);
}
