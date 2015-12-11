package com.bdbvip.user.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bdbvip.entity.TMemAccount;
import com.bdbvip.entity.TMemAddress;
import com.bdbvip.entity.TMemBase;
import com.bdbvip.entity.TMemBinduser;
import com.bdbvip.entity.TMemConsult;
import com.bdbvip.entity.TMemSaleConfig;
import com.bdbvip.entity.TMemScore;
import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TMemSm;
import com.bdbvip.entity.TMemSubscribe;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.utils.common.dao.IEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

public interface UserDAO extends IEntityDao<Integer> {

	TMemBase findUserById(String userId);
	
	void updateUser(TMemBase t);
	
	TMemBinduser findBindByUserId(int userid);
	
	int doUnbind(int userId);
	
	int updateAttation(TMemSubscribe subscribe);
	
	TMemSubscribe findSubByTouserAndUser(int touserid, int userid);
	
	TMemBase getUserByName(String uname);
	
	TMemAccount getTMemAccoutById(Integer id);
	
	TMemScore getTMemScoreById(Integer id);

	int updatePhoneNO(TMemBase base);	
	
	Serializable insertComplainSuggest(TOrdCompain complain);	
	
	TMemShop findTMemShopByName(String shopname);

	TMemBinduser findTMemBindUserByBindId(String bindid,String type);
	
	Page listUserAddress(int userId,Page page);
	
	Serializable saveUserAddress(TMemAddress address);
	
	int  deleteUserAddressByAddressID(int addressID);
	
	int setDefaultUserAddress(int addressID,String flag);

	void saveUserAddress(List<TMemAddress> addressList);
	
	Serializable saveSaleConfig(TMemSaleConfig config);
	
	TMemSaleConfig findHostingBidByParams(int userID,int touserID,String productCode);
	
	/**
	 * 
	  * @Title: geTMemSmsByUserId
	  * @param  touserid 用户id
	  * @return TMemSm  短信记录 
	  * @throws
	 */
	TMemSm geTMemSmsByUserId(int touserid);
	
	/**
	 * 修改消息记录
	  * @Title: updateTemSms
	  * @param tMemSm
	  * @return Serializable    
	  * @throws
	 */
	Serializable updateTemSms(TMemSm tMemSm);

	Page findMsgByStatus(int userId,String status,int pageNo,int pageSize);

	Page findMsgById(int msgid);
	
	Page listSuggest(Map<String,String> map);
	
	TMemSm geTMemSmsByTelephone(String telphone);
	
	int saveConsult(TMemConsult consult);
	
	
}
