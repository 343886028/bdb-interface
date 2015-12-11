package com.bdbvip.user.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.bdbvip.entity.TMemAddress;
import com.bdbvip.entity.TMemBase;
import com.bdbvip.entity.TMemBinduser;
import com.bdbvip.entity.TMemConsult;
import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TMemSm;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.pojo.ShopVo;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

public interface UserService {
	/**
	 * 根据用户ID，查询用户资料
	 * @param config
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	public TMemBase findUserById(String userId)throws ServiceException ;
	
	/**
	 * 修改用户资料
	 * @param t
	 * @return
	 */
	public void updateUser(TMemBase t)throws ServiceException;
	
	/**
	 * 根据用户ID,查第三方绑定账户
	 */
	public TMemBinduser findBindByUserId(String userid);
	
	/**
	 * 第三方账户解绑
	 * Flag: 绑定的第三方账号  1为生效  0为未生效 ，解绑即未生效
	 * @param t
	 * @return
	 */
	public void doUnbind(String userId)throws ServiceException;
	
	/**
	 * 关注店铺 \取消关注店铺
	 * @param userid 
	 * @param t
	 * @return
	 */
	public int updateAttation(int touserid , int userid, String type);
	
	/**
	 * 店铺咨询
	 * @param t
	 * @return
	 */
	public Serializable updateConsult(TMemShop t)throws ServiceException;
	
	/**
	 * 设定托管竞价
	 * @param userid 用户ID
	 * @param touserid 店铺ID
	 * @param prodSaleID 拍品ID
	 * @param value 最大价格
	 * @param endDate 截止时间  与拍品结束时间一致
	 * @param prodCode 商品code
	 * @return
	 * @throws ServiceException
	 */
	public Serializable saveHostingBid(int userID,int touserID,String prodSaleID,BigDecimal maxValue,Date endDate,String prodCode)throws ServiceException;
	
	/**
	 * 更换手机号码
	 * @param base
	 * @return
	 */
	public int updatePhoneNO(String userid,String phoneno)throws ServiceException; 
	
	/**
	 * 提交建议-平台、店铺
	 * @param userid 用户ID
	 * @param touserid 店铺ID
	 * @param orderitemid orderItem ID
	 * @param orderno 订单号
	 * @param type 类型  0投诉  1建议
	 * @param comment 评论内容
	 * @param msgtype 
	 * @param imgurl
	 * @param imgurl2
	 * @param imgurl3
	 * @return
	 */
	public Serializable insertComplainSuggest(int userid,int touserid,int orderitemid,String orderno,String type,String comment,int msgtype,String imgurl,String imgurl2,String imgurl3);
	
	/**
	 * 站短信查询
	 * @return
	 */
	public Serializable listSms()throws ServiceException;
	
	/**
	 * 站短信查看
	 * @return
	 */
	public Serializable showSms()throws ServiceException;
	

	/**
	 * 根据用户名，数据库中是否存在相同的。
	 * @param username
	 * @return int 记录条数
	 */
	public TMemBase getUserByName(String username);
	
	/**
	 * 注册会员
	 * @param base
	 * @return
	 * @throws ServiceException
	 */
	public Serializable saveTMemBase(TMemBase base) throws ServiceException;
	
	/**
	 * 登录
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public MembaseVo doLogin(MembaseVo vo) throws ServiceException;
	
	/**
	 * 完善更新用户信息资料
	 * 1,昵称
	 * 2,头像
	 * 3，生日
	 * 4,性别
	 * 5，省市地址暂未实现。地址是否对应收货地址？
	 * @param vo
	 * @throws ServiceException
	 */
	public void updateTMemBase(MembaseVo vo) throws ServiceException;
	
	/**
	 * 申请店铺
	 * @param vo
	 * @throws ServiceException
	 */
	public ShopVo createTMemShop(ShopVo vo)throws ServiceException;
	
	/**
	 * 根据店铺名，查询是否有重名的。
	 * 
	 * @param shopname
	 * @throws ServiceException
	 */
	public TMemShop findTMemShop(String shopname) throws ServiceException;
	
	/**
	 * 验证用户是否完善用户名和密码
	  * @Title: complate
	  * @param  userId 用户id
	  * @return Map<String,String>     
	  *         Map<key,value> 
	  *         complete  "1"||"0" 完善||未完善
	  *         errorcode  
	  *         msg        
	  * @throws
	 */
	public Map<String, String> doComplete(String userId) throws ServiceException;
	
	/**
	 * 根据ID查寻站SMS消息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public Page findMsgById(int msgid) throws ServiceException;
	
	/**
	 * 根据status 查寻所有站短消息
	 * 0.未读
	 * 1.已读
	 * @return
	 * @throws ServiceException
	 */
	public Page findMsgByStatus(String userID,String status,int pageNo,int pageSize) throws ServiceException;
		
	/**
	 * 查询是否有已经绑定过的联名登录用户。
	 * 根据openid,type 
	 * 返回token
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public MembaseVo doMemBindUser(MembaseVo vo)throws ServiceException;
	/**
	 * 保存联名登录用户信息，同时返回登录后的token
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public MembaseVo doUnion(MembaseVo vo) throws ServiceException;

	/**
	 * 根据 userID 列出所有收货人地址
	 * to do : 考虑用户操作习惯，首先，假如有默认地址，默认地址要放在第一个位置，
	 * 							其次，按照当前"收货人姓名" "创建时间-降序"，
	 * 							最后，其余收货地址按照"创建时间-降序"就可以  
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page listUserAddress(int userId,Page page);
	
	/**
	 * 新增收货地址
	 * @param TMemAddress address
	 * @return
	 */
	public Serializable saveUserAddress(TMemAddress address);
	
	/**
	 * 根据地址ID，删除收货人地址
	 * @param addressID
	 * @return
	 */
	public int  deleteUserAddressByAddressID(int addressID);
	
	/**
	 * 设置默认收货地址
	 * Flag: 0 表示 未设置默认收货地址
	 *       1 表示 已设置默认收货地址
	 * to do : 当确认结算，进入到订单确定页面，要选择收货地址时--> 若未设置默认收货地址，系统应提示，您暂未设置默认收货地址，是否设置？
	 * @param addressID
	 * @param flag
	 * @return
	 */
	public void updateDefaultUserAddress(int userID , int addressID);
	
	
	/**
	 * 找回支付密码前置校验
	  * @Title: doValidForgetPwd 
	  * @param username  用户名
	  * @param code      短信验证码
	  * @param pwd       登录密码
	  * @param partner   平台接入标识
	  * @param @return 
	  * @return Map<String,String>    
	  * @throws
	 */
	public Map<String, String> doValidForgetPwd(String username,String code,String pwd,String partner) throws ServiceException;
	
	
	/**
	 * 找回密码修改密码操作
	  * @Title: doValidForgetPwd 
	  * @param username  用户名
	  * @param code      短信验证码
	  * @param pwd       登录密码
	  * @param newpwd    新密码
	  * @param sign      md5加密码
	  * @param partner   平台接入标识
	  * @return Map<String,String>    
	  * @throws
	 */
	
	public Map<String, String> doForgetPwd(String username,String code,String pwd,String newpwd,String sign,String partner) throws ServiceException;
 	
	/**
	 * 根据手机号获取最新短信
	 * @param telphone
	 * @return
	 */
	public TMemSm geTMemSmsByTelephone(String telphone);
	
	public int saveConsult(TMemConsult consult);
	
	/**
	 * 商家用户建议/投诉查询
	 * @param userid
	 * @return
	 */
	public Page listSuggest(Map<String,String> map);
}
