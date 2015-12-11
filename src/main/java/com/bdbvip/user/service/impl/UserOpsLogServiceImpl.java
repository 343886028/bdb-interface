package com.bdbvip.user.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemAccount;
import com.bdbvip.entity.TMemLog;
import com.bdbvip.pojo.CashAccountVo;
import com.bdbvip.user.dao.CashDAO;
import com.bdbvip.user.dao.UserOpsLogDAO;
import com.bdbvip.user.service.UserOpsLogService;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

import antlr.collections.List;

@Service("userOpsLogService")
public class UserOpsLogServiceImpl implements UserOpsLogService {

	@Autowired
	UserOpsLogDAO  userOpsLogDao;
	
	@Autowired
	CashDAO cashDao;

	
	/**
	 * 保存日志
	 */
	public void insertUserLog(TMemLog tLog) throws ServiceException {
          userOpsLogDao.saveOpsLog(tLog);
	}

	public boolean doUserthreeOps(Integer userid, String logType) {
		boolean flag = false;
		int count = 0;
		// 获取最后三条符合要求的操作记录
		Page page = userOpsLogDao.findLogByuseridAndLogType(userid, logType);
		if (page == null) {
			return flag;
		}

		for (int i = 0; i < page.getResult().size(); i++) {
			TMemLog tMemLog = (TMemLog) page.getResult().get(i);
			// 判断日志的msg 是否 是操作错误 如果是 count+1
			if (CashAccountVo.CASD_ACCOUNT_FAIL.equals(tMemLog.getLogmsg())) {
				count++;
			}
		}

		if (count >= CashAccountVo.CASH_ACCOUNT_ERROR_COUNT) {
			//锁定账户操作
			TMemAccount account=null;
			account = cashDao.findAccountByUserId(userid);
			if (account!=null) {
				account.setLocktime(new Date());
				account.setAccountflag(CashAccountVo.CASH_ACCOUNT_LOCK);
				cashDao.updateAccountByUserId(account);
			}else {
				account=new TMemAccount();
				account.setUserid(userid);
				account.setCashmoney(BigDecimal.valueOf(0));
				account.setFrozenmoney(BigDecimal.valueOf(0));
				account.setAccountflag(CashAccountVo.CASH_ACCOUNT_LOCK);
				account.setLocktime(new Date());
				cashDao.saveAccount(account);
			}
			flag = true;
		}

		return flag;
	}

}
