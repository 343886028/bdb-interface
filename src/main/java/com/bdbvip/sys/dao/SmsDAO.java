package com.bdbvip.sys.dao;

import com.bdbvip.entity.TMemSm;
import com.bdbvip.utils.common.dao.IEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

public interface SmsDAO extends IEntityDao<Integer> {
	public Page findBySms(TMemSm sm,Page page);
}
