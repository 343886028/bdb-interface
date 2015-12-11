package com.bdbvip.user.service;


import com.bdbvip.entity.TMemBankinfo;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern.Flag;



public interface CashBankService {
	

	/**
	 * 获取用户银行卡列表
	  * @Title: findBankList
	  * @param  userId
	  *         用户id
	  * @param  page
	  *         分页参数
	  * @return  Map<String, String>
	  *          page  页码
	  *          pagesize 每页多少条
	  *          total 总条数
	  *          list 银行卡列表  
	  * @throws  ServiceException
	 */
	public  Map<String, Object> findBankList(String userId, Page page) throws ServiceException;
	
	
	
	/**
	 * 设置/取消 默认银行卡号
	  * @Title: setBankDefaultOrNo
	  * @param  userId 用户id
	  * @param  bankId 银行卡号
	  * @param  flag 设置标识  DEFAULT 1  默认 NODEFAULT 取消默认 0
	  * @return  Map<String, String> 设置成功 1  设置失败 0     
	  * @throws @throws ServiceException 
	 */
	public  Map<String, Object> doBankDefaultOrNo(String userId,String bankId) throws ServiceException;
	
			
	
	/**
	 * 添加一张银行卡号
	  * @Title: saveBankInfo
	  * @param  tBankinfo 用户银行卡信息
	  * @return  Map<String, String> 添加设置成功1 添加失败 0 
	  * @throws
	 */
	
	public  Map<String, Object> saveBankInfo(TMemBankinfo tBankinfo)throws ServiceException;
	
	/**
	 * 设置默认银行卡号
	  * @Title: deleteBank
	  * @param  userId 用户id
	  * @param  bankId 银行卡号
	  * @return  Map<String, String> 删除成功 1  删除失败 0     
	  * @throws @throws ServiceException 
	 */
	
	public  Map<String, Object> deleteBank(String userId,String bankId)throws ServiceException;
	
	

}
