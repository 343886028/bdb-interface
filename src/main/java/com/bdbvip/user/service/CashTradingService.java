package com.bdbvip.user.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.ui.velocity.VelocityEngineFactory;

import com.bdbvip.entity.TMemCashTradeRecord;
import com.bdbvip.entity.TMemDrawRecord;
import com.bdbvip.entity.TMemPayRecord;

/**
 * 涉及现金和非现金交易  充值  提现操作
  * @ClassName: TradingService
  * @Description: TODO
  * @date 2015年11月26日 上午10:34:28
 */
public interface CashTradingService {
	
	
	/**
	 * 保存用户交易流水
	  * @Title: saveTMemCashTradeRecord
	  * @param  cashTradeRecord
	  * @return Map<String,String>    
	  * @throws
	 */
	public Map<String, Object> saveTMemCashTradeRecord(TMemCashTradeRecord cashTradeRecord);
	
	
	/**
	 * 保存充值记录
	  * @Title: savePayRecords
	  * @param tPayRecord
	  * @return Map<String,String>    
	  * @throws
	 */
	public Map<String, Object> savePayRecords(TMemPayRecord tPayRecord);
	
	
	/**
	 * 保存提现记录
	  * @Title: saveDrawRecords
	  * @param tPayRecord
	  * @return Map<String,String>    
	  * @throws
	 */
	public Map<String, Object> saveDrawRecords(TMemDrawRecord tDrawRecord);
	
	
	/**
	 * 提交保证金
	 * @param money
	 *          保证金金额
	 * @param useridOut
	 *          保证金支出用户
	 * @param useridIn
	 *          保证金收入用户
	 */          
	public boolean doSubmitDeposit(BigDecimal money,Integer useridOut,Integer useridIn);
		
	

}
