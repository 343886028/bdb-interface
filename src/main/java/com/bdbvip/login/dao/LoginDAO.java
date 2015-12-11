package com.bdbvip.login.dao;

import com.bdbvip.entity.TSysConfig;
import com.bdbvip.utils.common.dao.IEntityDao;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.DaoException;

public interface LoginDAO extends IEntityDao<Integer> {

	
	public Page findAllByStatus(TSysConfig config,Page page) throws DaoException;

}
