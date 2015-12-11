package com.bdbvip.sys.dao;

import java.util.List;

import com.bdbvip.entity.TSysPartner;
import com.bdbvip.utils.common.exception.DaoException;

public interface SysDAO {

	public List<TSysPartner> findAll() throws DaoException;
}
