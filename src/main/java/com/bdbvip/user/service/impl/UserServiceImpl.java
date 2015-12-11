package com.bdbvip.user.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemAccount;
import com.bdbvip.entity.TMemAddress;
import com.bdbvip.entity.TMemBase;
import com.bdbvip.entity.TMemBaseinfo;
import com.bdbvip.entity.TMemBinduser;
import com.bdbvip.entity.TMemConsult;
import com.bdbvip.entity.TMemLog;
import com.bdbvip.entity.TMemLoginLog;
import com.bdbvip.entity.TMemPass;
import com.bdbvip.entity.TMemSaleConfig;
import com.bdbvip.entity.TMemScore;
import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TMemShopKind;
import com.bdbvip.entity.TMemSm;
import com.bdbvip.entity.TMemSubscribe;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.entity.TSysPartner;
import com.bdbvip.pojo.CashAccountVo;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.pojo.ShopVo;
import com.bdbvip.shop.dao.ShopDAO;
import com.bdbvip.user.dao.CashDAO;
import com.bdbvip.user.dao.UserDAO;
import com.bdbvip.user.service.CashService;
import com.bdbvip.user.service.UserOpsLogService;
import com.bdbvip.user.service.UserService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.DateUtils;
import com.bdbvip.utils.Md5Util;
import com.bdbvip.utils.MemcacheUtil;
import com.bdbvip.utils.TokenUtil;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDao;

	@Autowired
	CashDAO cashDao;

	@Autowired
	ShopDAO shopDao;

	@Autowired
	UserOpsLogService userOpsLogService;

	@Autowired
	CashService cashService;

	public TMemBase findUserById(String userId) throws ServiceException {
		return userDao.findUserById(userId);
	}

	public void updateUser(TMemBase t) {
		userDao.updateUser(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bdbvip.user.service.UserService#getUserByName(java.lang.String)
	 */
	public TMemBase getUserByName(String username) {
		return userDao.getUserByName(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bdbvip.user.service.UserService#saveTMemBase(com.bdbvip.entity.
	 * TMemBase )
	 */
	public Serializable saveTMemBase(TMemBase base) throws ServiceException {
		// 判断用户名是否已经存在
		TMemBase o = userDao.getUserByName(base.getUsername());

		if (o!=null) {
//			throw new ServiceException(
//					Constants.getParamterkey("login.reg.unameexists"));
		}
		userDao.save(base);

		TMemAccount account = new TMemAccount();
		account.setUserid(base.getId());
		account.setAccountflag("0");
		account.setCashmoney(new BigDecimal(0));
		account.setFrozenmoney(new BigDecimal(0));
		userDao.save(account);

		TMemScore score = new TMemScore();
		score.setUserid(base.getId());
		score.setScore(0);
		userDao.save(score);

		TMemBaseinfo info = new TMemBaseinfo();
		info.setUserid(base.getId());
		userDao.save(info);

		return base;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bdbvip.user.service.UserService#doLogin(com.bdbvip.pojo.MembaseVo)
	 */
	@SuppressWarnings("unchecked")
	public MembaseVo doLogin(MembaseVo vo) throws ServiceException {
		// 获取用户名对应的对象
		TMemBase o = userDao.getUserByName(vo.getUname());
		if (null == o) {
			vo.setErrorcode("1006");
			vo.setMsg(Constants.getParamterkey("login.login.username"));
			return vo;
		}
		// 根据一定的规则，生成唯一token
		TSysPartner tsp = Constants.getPartner(vo.getPartnerid());
		if (null == tsp) {
			vo.setErrorcode("9998");
			vo.setMsg(Constants.getParamterkey("common.param.error"));
			return vo;
		}

		// 记录用户登录日志
		TMemLoginLog loginlog = new TMemLoginLog();
		loginlog.setClient(vo.getSource());
		loginlog.setCreatetime(new Date());
		loginlog.setIp(vo.getRequestip());
		loginlog.setType("0");
		loginlog.setUserid(o.getId());
		// 校验密码
		if (!o.getPasswd().equalsIgnoreCase(vo.getPwd())) {
			// 记录用户登录日志
			loginlog.setFlag("0");
			loginlog.setRemark("密码错误");
			userDao.save(loginlog);

			// 查询，记录用户登录密码输入错误次数。

			vo.setErrorcode("1008");
			vo.setMsg(Constants.getParamterkey("login.login.validpwd"));
			return vo;
		}
		String token = TokenUtil.makeSafeTokenStr(String.valueOf(o.getId()), o.getUsername(), o.getPasswd(), "",
				tsp.getSignstr());
		vo.setToken(token);

		// 保存登录日志
		loginlog.setFlag("1");
		loginlog.setRemark("登录成功");
		userDao.save(loginlog);

		// 查询用户账户及积分情况
		TMemAccount account = userDao.getTMemAccoutById(o.getId());

		TMemScore score = userDao.getTMemScoreById(o.getId());

		TMemShop shop = shopDao.findTMemShopByUserid(o.getId());
		Map<String, String> usermap = new HashMap<String, String>();
		usermap.put("_userid", String.valueOf(o.getId()));
		usermap.put("_username", o.getUsername());
		usermap.put("_cashamount", account.getCashmoney().setScale(2).toString());
		usermap.put("_forzenamount", account.getFrozenmoney().setScale(2).toString());
		usermap.put("_amount", account.getCashmoney().add(account.getFrozenmoney()).setScale(2).toString());
		usermap.put("_score", String.valueOf(score.getScore()));
		usermap.put("_regsource", o.getChannel());
		usermap.put("_regchannelno", o.getChannelno());
		usermap.put("_sigstr", tsp.getSignstr());
		usermap.put("_shopid", shop != null ? String.valueOf(shop.getId()) : "");// 用户登录，是否是店铺商家
																					// shopid不为空时，为商铺用户，否则是用户登录。
		String hours = Constants.getConfigkey("login.max.hour");
		if (StringUtils.isBlank(hours)) {
			hours = "2";
		}
		int hour = Integer.valueOf(hours);
		// 查询的对象保存放入memcached中。用户ID,用户名，注册来源，注册推荐人，账户余额（现金，非现金）
		MemcacheUtil.put(token, usermap, hour * 60 * 60 * 1000);

		
		//返回给前端用户信息
		if(StringUtils.isBlank(vo.getImgurl())){
			vo.setImgurl("default.jpg");
		}else{
			vo.setImgurl(o.getImgurl());
		}
		if(null!=shop){
			vo.setShopid(String.valueOf(shop.getId()));
		}else{
			vo.setShopid("-1");
		}
		vo.setUserlevel(String.valueOf(o.getLevels()));
		vo.setErrorcode("0");
		vo.setMsg("登录成功");
		return vo;
	}

	public void doUnbind(String userId) {
		userDao.doUnbind(Integer.valueOf(userId));
	}

	public int updateAttation(int touserid, int userid, String type) {
		TMemSubscribe subscribe = userDao.findSubByTouserAndUser(touserid, userid);
		if (subscribe == null) {// 从未关注过
			if (type.equals("0")) { // 表示从未关注过，就直接点取消关注。
				return 0;
			}
			subscribe = new TMemSubscribe();
			subscribe.setCreatetime(new Date());
			subscribe.setFromuserid(userid);
			subscribe.setTouser(touserid);
			subscribe.setStatus(type);
		} else { // 表示曾经关注过
			if (subscribe.getStatus().equals(type)) { // "您已经关注过了" 或 "您已经取消关注了"
														// 请不要重复执行。
				return 0;
			}
		}
		return userDao.updateAttation(subscribe);
	}

	public Serializable updateConsult(TMemShop t) {
		// TODO Auto-generated method stub
		return null;
	}

	public Serializable saveHostingBid(int userID, int touserID, String prodSaleID, BigDecimal maxValue, Date endDate,
			String prodCode) {
		// 查询自动竞价
		TMemSaleConfig saleConfig = userDao.findHostingBidByParams(userID, touserID, prodSaleID);
		if (saleConfig == null) {
			TMemSaleConfig config = new TMemSaleConfig();
			config.setUserid(userID);
			config.setTouserid(touserID);
			config.setMaxvalues(maxValue);
			config.setCreatetime(new Date());
			try {
				TMemBase base = findUserById(String.valueOf(userID));
				config.setUsername(base.getUsername());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			config.setProductcode(prodCode);
			config.setEndtime(endDate);

			return userDao.saveSaleConfig(config);
		} else {
			saleConfig.setMaxvalues(maxValue);
			return userDao.update(saleConfig);
		}

	}

	public int updatePhoneNO(String userid, String phoneno) {
		TMemBase base = userDao.findUserById(userid);
		base.setTelphone(phoneno);
		return userDao.updatePhoneNO(base);
	}

	public Serializable insertComplainSuggest(int userid, int touserid, int orderitemid, String orderno, String type,
			String comment, int msgtype, String imgurl, String imgurl2, String imgurl3) {
		TOrdCompain complain = new TOrdCompain();
		complain.setFromuserid(userid);
		complain.setTouserid(touserid);
		complain.setOrderitemid(orderitemid);
		complain.setOrderno(orderno);
		complain.setType(type);
		complain.setComment(comment);
		complain.setFlag(msgtype);
		complain.setCreatetime(new Date());
		complain.setImgurl(imgurl);
		complain.setImgurl2(imgurl2);
		complain.setImgurl3(imgurl3);
		return userDao.insertComplainSuggest(complain);
	}

	public Serializable listSms() {
		// TODO Auto-generated method stub
		return null;
	}

	public Serializable showSms() {
		// TODO Auto-generated method stub
		return null;
	}

	public TMemBinduser findBindByUserId(String userid) {
		return userDao.findBindByUserId(Integer.valueOf(userid));
	}

	public void updateTMemBase(MembaseVo vo) throws ServiceException {
		TMemBase base = userDao.findUserById(vo.getUserid());
		if (null != base) {
			boolean isedit = false;
			if (StringUtils.isNotBlank(vo.getImgurl())) {
				base.setImgurl(vo.getImgurl());
				isedit = true;
			}
			if (StringUtils.isNotBlank(vo.getBirthday())) {
				base.setBirthdary(DateUtils.string2Date(vo.getBirthday(), DateUtils.simpleDateFormat1));
				isedit = true;
			}
			if (StringUtils.isNotBlank(vo.getSex())) {
				base.setSex(vo.getSex());
				isedit = true;
			}
			if (StringUtils.isNotBlank(vo.getNickname())) {
				base.setNickname(vo.getNickname());
				isedit = true;
			}
			if (isedit) {
				userDao.updateUser(base);
			}
		}
	}

	public ShopVo createTMemShop(ShopVo vo) throws ServiceException {
		TMemShop shop = userDao.findTMemShopByName(vo.getShopname());
		if (shop == null) {
			if (StringUtils.isNotBlank(vo.getRealname())) {
				TMemBase base = userDao.findUserById(vo.getUserid());
				if (base != null && StringUtils.isBlank(base.getRealname())) {
					TMemLog mlog = new TMemLog();
					StringBuilder msg = new StringBuilder();
					msg.append("[userService.createTMemshop]原真实姓名" + base.getRealname() + "=>" + vo.getRealname()
							+ "|userid=" + vo.getUserid());
					base.setRealname(vo.getRealname());
					userDao.update(base);
					mlog.setCreatetime(new Date());
					mlog.setLogmsg(msg.toString());
					mlog.setLogtype("login");
					mlog.setOptuserid("0");
					mlog.setUserid(Integer.valueOf(vo.getUserid()));
					userDao.save(mlog);
				}
			}
			shop.setCreatetime(new Date());
			shop.setImgurl(vo.getCardimg());
			shop.setQualitystar(0);
			shop.setSendstar(0);
			shop.setServicestar(0);
			shop.setShowpara(0);
			shop.setShopname(vo.getShopname());
			shop.setStatus("1");
			shop.setShopstar(0);
			shop.setUserid(Integer.valueOf(vo.getUserid()));
			userDao.save(shop);
			vo.setId(shop.getId());

			List<TMemShopKind> kindlist = new ArrayList<TMemShopKind>();
			// 添加经营种类。
			if (StringUtils.isNotBlank(vo.getKindscode())) {
				String[] kinds = StringUtils.split(vo.getKindscode(), "#");
				for (String s : kinds) {
					String tmp = Constants.kindsmap.get(s);
					if (StringUtils.isNotBlank(tmp)) {
						TMemShopKind k = new TMemShopKind();
						k.setCreatetime(new Date());
						k.setKindscode(s);
						k.setKindsname(Constants.kindsmap.get(s));
						kindlist.add(k);
					}
				}
			}
			if (!kindlist.isEmpty()) {
				userDao.updateAll(kindlist);
			}
		}
		return vo;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bdbvip.user.service.UserService#findTMemShop(java.lang.String)
	 */
	public TMemShop findTMemShop(String shopname) throws ServiceException {
		return userDao.findTMemShopByName(shopname);
	}

	public Map<String, String> doComplete(String userId) throws ServiceException {
		Map<String, String> result = new TreeMap<String, String>();
		TMemBase tBase = userDao.findUserById(userId);
		if (tBase != null) {
			if (null == tBase.getRealname() || "".equals(tBase.getRealname())) {
				result.put("complete", "0");
				result.put("status", "60030");
				result.put("msg", Constants.getParamterkey("user.cash.bank.complete.realname.null"));
				return result;
			}
			if (null == tBase.getCardno() || "".equals(tBase.getCardno())) {
				result.put("complete", "0");
				result.put("status", "60031");
				result.put("msg", Constants.getParamterkey("user.cash.bank.complete.cardno.null"));
				return result;
			}
			result.put("complete", "1");
			result.put("status", "0");
			result.put("msg", "");
			return result;

		}
		result.put("status", "60033");
		result.put("msg", Constants.getParamterkey("user.cash.bank.complete.params.error"));
		return result;
	}

	public Page findMsgById(int msgid) throws ServiceException {
		return userDao.findMsgById(msgid);
	}

	public Page findMsgByStatus(String userID, String status, int pageNo, int pageSize) throws ServiceException {
		return userDao.findMsgByStatus(Integer.valueOf(userID), status, pageNo, pageSize);
	}

	public MembaseVo doMemBindUser(MembaseVo vo) throws ServiceException {
		TMemBinduser binduser  = userDao.findTMemBindUserByBindId(vo.getOpenid(), vo.getType());
		if(null != binduser){		

			vo.setUserid(String.valueOf(binduser.getUserid()));

			// 记录用户登录日志
			TMemLoginLog loginlog = new TMemLoginLog();
			loginlog.setClient(vo.getSource());
			loginlog.setCreatetime(new Date());
			loginlog.setIp(vo.getRequestip());
			loginlog.setType("0");
			loginlog.setUserid(Integer.valueOf(vo.getUserid()));
			// 校验密码
			TMemBase base = userDao.findUserById(vo.getUserid());
			vo.setUname(base.getUsername());

			Map<String, TSysPartner> tspmap = (Map<String, TSysPartner>) MemcacheUtil.get(Constants.PARTNER);
			TSysPartner tsp = tspmap.get(vo.getPartnerid());

			String token = TokenUtil.makeSafeTokenStr(String.valueOf(base.getId()), base.getUsername(),
					base.getPasswd(), "", tsp.getSignstr());
			vo.setToken(token);

			// 保存登录日志
			loginlog.setFlag("1");
			loginlog.setRemark(vo.getOpenid() + "|" + vo.getType() + "联名登录成功");
			userDao.save(loginlog);

			// 查询用户账户及积分情况
			TMemAccount account = userDao.getTMemAccoutById(base.getId());

			TMemScore score = userDao.getTMemScoreById(base.getId());
			TMemShop shop = shopDao.findTMemShopByUserid(base.getId());

			Map<String, String> usermap = new HashMap<String, String>();
			usermap.put("_userid", String.valueOf(base.getId()));
			usermap.put("_username", base.getUsername());
			usermap.put("_cashamount", account.getCashmoney().setScale(2).toString());
			usermap.put("_forzenamount", account.getFrozenmoney().setScale(2).toString());
			usermap.put("_amount", account.getCashmoney().add(account.getFrozenmoney()).setScale(2).toString());
			usermap.put("_score", String.valueOf(score.getScore()));
			usermap.put("_regsource", base.getChannel());
			usermap.put("_regchannelno", base.getChannelno());
			usermap.put("_sigstr", tsp.getSignstr());
			usermap.put("_shopid", shop != null ? String.valueOf(shop.getId()) : "");// 用户登录，是否是店铺商家
																						// shopid不为空时，为商铺用户，否则是用户登录。
			String hours = Constants.getConfigkey("login.max.hour");
			if (StringUtils.isBlank(hours)) {
				hours = "2";
			}
			int hour = Integer.valueOf(hours);
			// 查询的对象保存放入memcached中。用户ID,用户名，注册来源，注册推荐人，账户余额（现金，非现金）
			MemcacheUtil.put(token, usermap, hour * 60 * 60 * 1000);

			vo.setErrorcode("0");
			vo.setMsg("联名登录登录成功");

			return vo;
		}
		return null;
	}

	public MembaseVo doUnion(MembaseVo vo) throws ServiceException {
		// 判断用户名是否已经存在
		TMemBase base = userDao.getUserByName(vo.getUname());
		if (base != null) {
			throw new ServiceException(Constants.getParamterkey("login.reg.unameexists"));
		}
		base = new TMemBase();
		base.setUsername(vo.getUname());
		base.setAccountflag("1");
		base.setAccountflag("0");
		base.setChannel(vo.getSource() == null ? "" : vo.getSource());
		base.setChannelno(vo.getComment() == null ? "" : vo.getComment());
		base.setLevels(0);
		base.setRegtime(new Date());
		base.setRegurl(vo.getRequesturl());
		base.setRegip(vo.getRequestip());
		base.setUsername(vo.getUname());
		base.setPasswd(vo.getPwd());
		base.setTelphone(vo.getMobile());
		userDao.save(base);

		// 记录用户登录日志
		TMemLoginLog loginlog = new TMemLoginLog();
		loginlog.setClient(vo.getSource());
		loginlog.setCreatetime(new Date());
		loginlog.setIp(vo.getRequestip());
		loginlog.setType("0");
		loginlog.setUserid(base.getId());
		loginlog.setFlag("1");
		loginlog.setRemark("绑定账户并登录成功");
		userDao.save(loginlog);

		TMemAccount account = new TMemAccount();
		account.setUserid(base.getId());
		account.setAccountflag("0");
		account.setCashmoney(new BigDecimal(0));
		account.setFrozenmoney(new BigDecimal(0));
		userDao.save(account);

		TMemScore score = new TMemScore();
		score.setUserid(base.getId());
		score.setScore(0);
		userDao.save(score);

		TMemBaseinfo info = new TMemBaseinfo();
		info.setUserid(base.getId());
		userDao.save(info);

		Map<String, TSysPartner> tspmap = (Map<String, TSysPartner>) MemcacheUtil.get(Constants.PARTNER);
		TSysPartner tsp = tspmap.get(vo.getPartnerid());

		String token = TokenUtil.makeSafeTokenStr(String.valueOf(base.getId()), base.getUsername(), base.getPasswd(),
				"", tsp.getSignstr());
		vo.setToken(token);
		TMemShop shop = shopDao.findTMemShopByUserid(base.getId());
		// 绑定登录成功
		Map<String, String> usermap = new HashMap<String, String>();
		usermap.put("_userid", String.valueOf(base.getId()));
		usermap.put("_username", base.getUsername());
		usermap.put("_cashamount", account.getCashmoney().setScale(2).toString());
		usermap.put("_forzenamount", account.getFrozenmoney().setScale(2).toString());
		usermap.put("_amount", account.getCashmoney().add(account.getFrozenmoney()).setScale(2).toString());
		usermap.put("_score", String.valueOf(score.getScore()));
		usermap.put("_regsource", base.getChannel());
		usermap.put("_regchannelno", base.getChannelno());
		usermap.put("_sigstr", tsp.getSignstr());
		usermap.put("_shopid", shop != null ? String.valueOf(shop.getId()) : "");// 用户登录，是否是店铺商家
																					// shopid不为空时，为商铺用户，否则是用户登录。
		String hours = Constants.getConfigkey("login.max.hour");
		if (StringUtils.isBlank(hours)) {
			hours = "2";
		}
		int hour = Integer.valueOf(hours);
		// 查询的对象保存放入memcached中。用户ID,用户名，注册来源，注册推荐人，账户余额（现金，非现金）
		MemcacheUtil.put(token, usermap, hour * 60 * 60 * 1000);
		vo.setUserid(String.valueOf(base.getId()));
		vo.setErrorcode("0");
		vo.setMsg("联名登录登录成功");
		return vo;
	}

	public Page listUserAddress(int userId, Page page) {
		return userDao.listUserAddress(userId, page);
	}

	public Serializable saveUserAddress(TMemAddress address) {
		return userDao.saveUserAddress(address);
	}

	public int deleteUserAddressByAddressID(int addressID) {
		return userDao.deleteUserAddressByAddressID(addressID);
	}

	public void updateDefaultUserAddress(int userID, int addressID) {
		List<TMemAddress> addressList = (listUserAddress(userID, null)).getResult();
		for (TMemAddress address : addressList) {
			if (address.getAddressid() == addressID) {
				address.setFlag("1");
			} else {
				address.setFlag("0");
			}
		}
		userDao.saveUserAddress(addressList);
	}

	/**
	 * 找回支付密码前置校验
	 */
	public Map<String, String> doValidForgetPwd(String username, String code, String pwd, String partner)
			throws ServiceException {

		Map<String, String> result = new TreeMap<String, String>();
		TMemBase tBase = userDao.getUserByName(username);

		if (null == tBase) {
			result.put("status", "60018");
			result.put("msg", Constants.getParamterkey("user.cash.forget.pass.error"));
			result.put("sign", "");
			return result;
		}

		// 金融账户是否锁定
		if (cashService.validCashAccountByUserid(tBase.getId())) {
			result.put("status", "600192");
			result.put("msg", Constants.getParamterkey("user.cash.forget.pass.lock"));
			return result;
		}

		// 用户钱包模块操作是否连续错误三次
		if (userOpsLogService.doUserthreeOps(tBase.getId(), CashAccountVo.CASH_ACCOUNT_LOGTYPE)) {
			result.put("status", "60042");
			result.put("msg", Constants.getParamterkey("user.cash.account.three.error"));
			result.put("sign", "");
			return result;
		}

		// 用户操作日志行为
		TMemLog tLog = new TMemLog();
		tLog.setCreatetime(new Date());
		// 用户操作日志类型为 重置密码前验证
		tLog.setLogtype(CashAccountVo.CASH_ACCOUNT_RESET_VALID);
		tLog.setUserid(tBase.getId());

		if (!StringUtils.equals(tBase.getPasswd(), pwd.toLowerCase())) {
			// 设置用户日志msg 操作标识为失败
			tLog.setLogmsg(CashAccountVo.CASD_ACCOUNT_FAIL);
			userOpsLogService.insertUserLog(tLog);

			result.put("status", "60018");
			result.put("msg", Constants.getParamterkey("user.cash.forget.pass.error"));
			result.put("sign", "");
			return result;
		}

		TMemSm tSms = userDao.geTMemSmsByUserId(tBase.getId());
		if (tSms == null) {
			result.put("status", "60019");
			result.put("msg", Constants.getParamterkey("user.cash.forget.pass.code.error"));
			result.put("sign", "");
			return result;
		} else if (!StringUtils.equals(tSms.getCode(), code.trim())) {
			// 添加操作失败日志
			tLog.setLogmsg(CashAccountVo.CASD_ACCOUNT_FAIL);
			userOpsLogService.insertUserLog(tLog);

			result.put("status", "60019");
			result.put("msg", Constants.getParamterkey("user.cash.forget.pass.code.error"));
			result.put("sign", "");
			return result;
		} else if (tSms.getValidtime().compareTo(new Date()) <= 0) {
			result.put("status", "600191");
			result.put("msg", Constants.getParamterkey("user.cash.forget.pass.code.over"));
			result.put("sign", "");
			return result;
		} else {
			// 根据一定的规则，生成唯一token
			 
			TSysPartner tsp = Constants.getPartner(partner);
			if (null == tsp) {
				result.put("status", "9998");
				result.put("msg", Constants.getParamterkey("common.param.error"));
				return result;
			}


			String tcode = tSms.getCode() + "$";
			tSms.setCode(tcode);
			userDao.updateTemSms(tSms);

			// 操作成功 添加日志
			tLog.setLogmsg(CashAccountVo.CASH_ACCOUNT_SUCCESS);
			userOpsLogService.insertUserLog(tLog);

			result.put("status", "0");
			result.put("msg", "");
			result.put("sign", Md5Util.md5_32(code + pwd + username + tsp.getSignstr()).toLowerCase());
			return result;
		}

	}

	/**
	 * 找回密码修改密码操作
	 */
	public Map<String, String> doForgetPwd(String username, String code, String pwd, String newpwd, String sign,
			String partner) throws ServiceException {
		Map<String, String> result = new TreeMap<String, String>();

		TMemBase tBase = userDao.getUserByName(username);

		if (null == tBase) {
			result.put("status", "60018");
			result.put("msg", Constants.getParamterkey("user.cash.forget.pass.error"));
			result.put("sign", "");
			return result;
		}

		// 金融账户是否锁定
		if (cashService.validCashAccountByUserid(tBase.getId())) {
			result.put("status", "600192");
			result.put("msg", Constants.getParamterkey("user.cash.forget.pass.lock"));
			return result;
		}

		// 用户钱包模块操作是否连续错误三次
		if (userOpsLogService.doUserthreeOps(tBase.getId(), CashAccountVo.CASH_ACCOUNT_LOGTYPE)) {
			result.put("status", "60042");
			result.put("msg", Constants.getParamterkey("user.cash.account.three.error"));
			result.put("sign", "");
			return result;
		}

		// 用户操作日志行为
		TMemLog tLog = new TMemLog();
		tLog.setCreatetime(new Date());
		// 用户操作日志类型为 重置密码前验证
		tLog.setLogtype(CashAccountVo.CASH_ACCOUNT_RESET);
		tLog.setUserid(tBase.getId());

		// 根据一定的规则，生成唯一token
		 
		TSysPartner tsp = Constants.getPartner(partner);
		if (null == tsp) {
			result.put("status", "9998");
			result.put("msg", Constants.getParamterkey("common.param.error"));
			return result;
		}

		// 传入数据经行md5加密
		String tsign = Md5Util.md5_32(code + pwd + username + tsp.getSignstr()).toLowerCase();

		// md5 加密验证通过 才能操作修改密码操作

		if (StringUtils.equals(tsign, sign)) {

			TMemPass tPass = new TMemPass();
			tPass.setUserid(tBase.getId());
			tPass.setPaypass(newpwd.toLowerCase());
			tPass.setDrawpass(newpwd.toLowerCase());
			tPass.setClientpass(newpwd.toLowerCase());

			cashDao.updatePayPwd(tPass);

			tLog.setLogmsg(CashAccountVo.CASH_ACCOUNT_SUCCESS);
			userOpsLogService.insertUserLog(tLog);

			result.put("status", "0");
			result.put("msg", "");
			return result;
		}

		tLog.setLogmsg(CashAccountVo.CASD_ACCOUNT_FAIL);
		userOpsLogService.insertUserLog(tLog);

		result.put("status", "9001");
		result.put("msg", Constants.getParamterkey("common.md5valid"));

		return result;
	}

	/**
	 * 商家用户建议/投诉查询
	 */
	public Page listSuggest(Map<String,String> map) {
		return userDao.listSuggest(map);
	}
	public TMemSm geTMemSmsByTelephone(String telphone) {
		return userDao.geTMemSmsByTelephone(telphone);
	}

	public int saveConsult(TMemConsult consult) {
		return userDao.saveConsult(consult);
	}

}
