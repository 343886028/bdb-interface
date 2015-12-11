package com.bdbvip.user.dao;

import java.io.Serializable;
import java.util.Map;
import java.util.List;

import com.bdbvip.entity.TMemBankinfo;
import com.bdbvip.entity.TMemCashTradeRecord;
import com.bdbvip.entity.TMemDrawRecord;
import com.bdbvip.entity.TMemPayRecord;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;



public interface CashBankDAO {
	
	/**
	 * 保存银行卡信息
	  * @Title: saveBankInfo 
	  * @param  tBankinfo  银行卡信息实例
	  * @return     
	  * @throws
	 */
	public Serializable saveBankInfo(TMemBankinfo tBankinfo);
	
	
	/**
	 * 修改银行卡默认标识
	  * @Title: doBankDefaultSet 
	  * @param  tBankinfo  银行卡信息实例
	  * @return     
	  * @throws
	 */
	public Serializable updateBank(TMemBankinfo tBankinfo);
	
	/**
	 * 删除银行卡信息
	  * @Title: deleteBank
	  * @param  tBankinfo  银行卡信息
	  * @throws
	 */
	public void deleteBank(TMemBankinfo tBankinfo);	
	
	
	/**
	 * 根据用户id和 分页参数获取用户银行卡列表
	  * @Title: findBankList
	  * @param  userId 用户id
	  * @param  page   页面分装对象
	  * @return Page   
	  * @throws
	 */
	
	public Page findBankList(Integer userId, Page page);
	
	/**
	 * 根据用户id和银行卡号获取银行卡信息
	  * @Title: findBankByUseridAndBankno
	  * @param  userId  用户Id
	  * @param  bankNo  银行卡号
	  * @return TMemBankinfo    
	  * @throws
	 */
	public TMemBankinfo findBankByUseridAndBankno (Integer userId,String bankNo);
	
	
	/**
	 * 修改所有银行卡信息
	  * @Title: updateBankAll
	  * @param  tBankinfos 修改银行卡对象列表 
	  * @throws
	 */
	public void updateBankAll(List<TMemBankinfo> tBankinfos);
	
	/**
	 *  用户id获取 获取所有的银行卡信息
	  * @Title: findBanKAllByUserId
	  * @param  userId 用户id
	  * @return List<TMemBankinfo>    
	  * @throws
	 */
	public List<TMemBankinfo> findBanKAllByUserId(Integer userId);
	
	
	/**
	 * 根据用户id 获取  默认银行卡号信息
	  * @Title: findBankByUseridAndDefault
	  * @param  userId  用户Id
	  * @return TMemBankinfo    
	  * @throws
	 */
	public TMemBankinfo findBankByUseridAndDefault (Integer userId);
	
	/**
	 * 使用用户id获取该用户银行卡数
	  * @Title: findBankCountByUserId
	  * @param @param userId 用户id
	  * @return int  银行卡个数   
	  * @throws
	 */
	public int findBankCountByUserId(Integer userId);
	
	
     
	/**
	 * 根据银行卡号获取该卡数量
	  * @Title: findBankCountByBankNo
	  * @param  bankNo  银行卡号
	  * @throws
	 */
	public int findBankCountByBankNo(String bankNo);
	
	
	
	
	
		
	
	
		

}
