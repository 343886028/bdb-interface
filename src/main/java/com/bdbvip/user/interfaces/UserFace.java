package com.bdbvip.user.interfaces;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TMemBase;
import com.bdbvip.entity.TMemBinduser;
import com.bdbvip.entity.TMemConsult;
import com.bdbvip.entity.TMemSm;
import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.product.service.ProductService;
import com.bdbvip.product.service.SellingService;
import com.bdbvip.user.service.UserService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.date.DateUtil;
import com.bdbvip.utils.common.exception.ServiceException;

@Controller
@RequestMapping("/interface/user")
public class UserFace extends BaseAction {
	@Autowired
	UserService userService;
	@Autowired
	SellingService sellingService;

	/**
	 * 用户基本资料查询
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 *         {‘partnerid’:’1003’,’errorcode’:’0’,’msg’:{‘level
	 *         :’1’,’imgurl’:’xxx
	 *         ’,’birthday’:’2015-12-01’,’nickname’:’xxx’,’cardno
	 *         ’:’xxx’,’username
	 *         ’:’xxx’,’sex’:’1’,’othercount’:’wx#12242|qq#dsds|
	 *         wb#13232’},’key’:’123243453511231’}
	 * 
	 */
	@RequestMapping("/index.shtml")
	@ResponseBody
	public Object index(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.index.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[UserFace.index.resultstr] ==> "+resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"user".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						String userid = super.getUserid(params.get("token"));
						if (userid != null && !userid.equals("")) {
							TMemBase tmembase = userService.findUserById(userid);
							if (tmembase != null) {
								Map<String, Object> msgmap = new TreeMap<String, Object>();
								msgmap.put("level", tmembase.getLevels());
								msgmap.put("imgurl", tmembase.getImgurl());
								msgmap.put("birthday", tmembase.getBirthdary());
								msgmap.put("nickname", tmembase.getNickname());
								msgmap.put("cardno", tmembase.getCardno());
								msgmap.put("username", tmembase.getUsername());
								msgmap.put("sex", tmembase.getSex());
								TMemBinduser binduser = userService.findBindByUserId(userid);
								if (binduser != null) {
									StringBuffer sb = new StringBuffer();
									if (binduser.getType() == 0) {
										sb.append("wx");
									} else if (binduser.getType() == 1) {
										sb.append("qq");
									} else if (binduser.getType() == 2) {
										sb.append("wb");
									} else if (binduser.getType() == 3) {
										sb.append("zfb");
									}
									sb.append("#").append(binduser.getBindid());
									msgmap.put("othercount", sb.toString());
									resultmap.put("partnerid", requestParams.get("partnerid"));
									resultmap.put("status", "0");
									resultmap.put("msg", JSON.toJSONString(msgmap));
									String rkey = super.md5str(resultmap);
									resultmap.put("key", rkey);
								}
							} else {
								throw new Exception("用户不存在！");
							}
						}
					}
				}
			} else {
				String[] str = StringUtils.split(resultstr, "#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		} catch (Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.info(e.getMessage(),e);;
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 基本资料修改
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/update.shtml")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response) {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.update.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[UserFace.update.resultstr] ==> "+resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"user".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						TMemBase base = new TMemBase();
						base.setId(Integer.valueOf(super.getUserid(params.get("token"))));
						base.setImgurl(params.get("imgurl"));
						base.setNickname(params.get("nickname"));
						base.setSex(params.get("sex"));
						base.setBirthdary(DateUtil.string2Date(params.get("birthday"), new SimpleDateFormat("yyyy-mm-dd")));
						base.setCardno(params.get("cardno"));
						userService.updateUser(base);

						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("msg", "更改成功");
						String rkey = super.md5str(resultmap);
						resultmap.put("key", rkey);
					}
				}
			} else {
				String[] str = StringUtils.split(resultstr, "#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		} catch (Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.info(e.getMessage(),e);
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 第三方账户解绑
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/unbind.shtml")
	@ResponseBody
	public Object unbind(Model model, HttpServletRequest request, HttpServletResponse response) {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.unbind.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[UserFace.unbind.resultstr] ==> "+resultstr);
		}

		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"user".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// 验证bindId
					if (flag && StringUtils.isBlank(params.get("bindid"))) {
						resultmap.put("status", "2003");
						resultmap.put("msg", Constants.getParamterkey("user.user.bindId.error|"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						userService.doUnbind(super.getUserid(params.get("token")));
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("msg", "解除成功");
						String rkey = super.md5str(resultmap);
						resultmap.put("key", rkey);
					}
				}
			} else {
				String[] str = StringUtils.split(resultstr, "#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		} catch (Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			log.error("[UserFace.unbind] ==> "+Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 关注店铺 / 取消关注 1表示关注 0表示取消关注
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/attention.shtml")
	@ResponseBody
	public Object attention(Model model, HttpServletRequest request, HttpServletResponse response) {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.attention.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[UserFace.attention.resultstr] ==> "+resultstr);
		}
		
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"user".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// 验证touserid 店铺id
					if (flag && StringUtils.isBlank(params.get("touserid"))) {
						resultmap.put("status", "2004");
						resultmap.put("msg", Constants.getParamterkey("user.user.touserid.error"));
						flag = false;
					}
					// 验证attentionType
					if (flag && StringUtils.isBlank(params.get("attentiontype"))) {
						resultmap.put("status", "2024");
						resultmap.put("msg", Constants.getParamterkey("user.user.attention.invalidattetype"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						userService.updateAttation(Integer.valueOf(params.get("touserid")), Integer.valueOf(super.getUserid(params.get("token"))), params.get("atteType")
								);
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("msg", "操作成功");
						String rkey = super.md5str(resultmap);
						resultmap.put("key", rkey);
					}
				}
			} else {
				String[] str = StringUtils.split(resultstr, "#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		} catch (Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			log.error("[UserFace.attention ==> ]"+Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 店铺咨询
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/consult.shtml")
	@ResponseBody
	public Object consult(Model model, HttpServletRequest request, HttpServletResponse response) {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.consult.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[UserFace.consult.resultstr] ==> "+resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"user".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// 验证 IP 合法性
					if (flag) {
						if (StringUtils.isBlank(params.get("ip"))) {
							resultmap.put("status", "2027");
							resultmap.put("msg", Constants.getParamterkey("user.user.consult.invalidip"));
							flag = false;
						} else {
							String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
									+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
							if (!Pattern.compile(ip).matcher(params.get("ip")).matches()) {
								resultmap.put("status", "2027");
								resultmap.put("msg", Constants.getParamterkey("user.user.consult.invalidip"));
								flag = false;
							}
						}

					}
					// 验证content
					if(StringUtils.isBlank(params.get("content"))) {
						resultmap.put("status", "2028");
						resultmap.put("msg", Constants.getParamterkey("user.user.consult.contentisnull"));
						flag = false;
					} else {
						if(params.get("content").length()>50) {
							resultmap.put("status", "2029");
							resultmap.put("msg", Constants.getParamterkey("user.user.consult.contentinvalid"));
							flag = false;
						}
					}
					if(flag) {
						// service处理业务逻辑
						TMemConsult consult = new TMemConsult();
						consult.setContent(params.get("content"));
						consult.setIp(params.get("ip"));
						consult.setShopid(Integer.valueOf(params.get("shopid")));
						consult.setTouserid(Integer.valueOf(params.get("touserid")));
						consult.setUserid(Integer.valueOf(super.getUserid(params.get("token"))));
						
						userService.saveConsult(consult);
						resultmap.put("partnerid", requestParams.get("partnerid"));
						String rkey = super.md5str(resultmap);
						resultmap.put("key", rkey);
						resultmap.put("status", "0");
						resultmap.put("msg", "咨询成功");
					}
				}
			} else {
				String[] str = StringUtils.split(resultstr, "#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		} catch (Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			log.error("[UserFace.unbind] ==> "+Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 设定自动竞拍
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sethostingbid.shtml")
	@ResponseBody
	public Object setHostingBid(Model model, HttpServletRequest request, HttpServletResponse response) {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.sethostingbid.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[UserFace.sethostingbid.resultstr] ==> "+resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"user".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// 验证prodsaleid
					if (flag && StringUtils.isBlank(params.get("prodsaleid"))) {
						resultmap.put("status", "2012");
						resultmap.put("msg", Constants.getParamterkey("invalidprodsaleid"));
						flag = false;
					}
					// 验证maxvalue
					if (flag && StringUtils.isBlank(params.get("maxvalue"))) {
						resultmap.put("status", "2013");
						resultmap.put("msg", Constants.getParamterkey("user.user.hostbid.invalidmaxvalue"));
						flag = false;
					}
					// service处理业务逻辑
					if (flag) {
						// 根据拍品ID 查拍品详细信息 -- 店铺 & 结束时间 &
						TProdSaleList saleList = sellingService.findProdListByProdSaleID(params.get("prodsaleid"));
						if (saleList != null) {
							userService.saveHostingBid(Integer.valueOf(super.getUserid(params.get("token"))), Integer.valueOf(saleList.getUserid()), params.get("prodsaleid"),
									new BigDecimal(params.get("maxvalue")), saleList.getSaleendtime(), saleList.getProcode());
							resultmap.put("partnerid", requestParams.get("partnerid"));
							resultmap.put("status", "0");
							resultmap.put("msg", "设定自动竞拍成功！");
							String rkey = super.md5str(resultmap);
							resultmap.put("key", rkey);
						} else {
							log.error("商品编号：" + params.get("productcode") + "，该商品未参与拍卖活动！");
							throw new Exception("商品编号：" + params.get("productcode") + "，该商品未参与拍卖活动！");
						}
					} else {
						resultmap.put("status", "9999");
						resultmap.put("msg", Constants.getParamterkey("common.syserror"));
						log.error(Constants.getParamterkey("common.syserror"));
					}
					String rkey = super.md5str(resultmap);
					resultmap.put("key", rkey);
				}
			} else {
				String[] str = StringUtils.split(resultstr, "#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		} catch (Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			log.error("[UserFace.unbind] ==> "+Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 更换手机账号
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/changephoneno.shtml")
	@ResponseBody
	public Object changePhoneNO(Model model, HttpServletRequest request, HttpServletResponse response) {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.changephoneno.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[UserFace.changephoneno.resultstr] ==> "+resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"user".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// 手机号
					if (flag && StringUtils.isBlank(params.get("mobile"))) {
						resultmap.put("status", "2005");
						resultmap.put("msg", Constants.getParamterkey("user.user.change.invalidno"));
						flag = false;
					}
					// 验证码
					if (flag) {
						if (StringUtils.isBlank(params.get("code")) || params.get("code").length() != 6) {
							resultmap.put("status", "2006");
							resultmap.put("msg", "user.user.invalidcode.error");
							flag = false;
						} else {// 核对验证码
							TMemSm sms = userService.geTMemSmsByTelephone(params.get("mobile"));
							if (sms != null && !sms.getCode().equals(params.get("code"))) {
								resultmap.put("status", "2006");
								resultmap.put("msg", "user.user.invalidcode.error");
								flag = false;
							}
						}
					}
					// 密码
					if (flag && (StringUtils.isBlank(params.get("passwd")) || params.get("passwd").length() != 32)) {
						resultmap.put("status", "2007");
						resultmap.put("msg", "user.user.change.invalidpwd");
						flag = false;
					}
					// 新手机号
					if (flag && (StringUtils.isBlank(params.get("newmobile")) || !params.get("newmobile").matches("^1\\d{10}$"))) {
						resultmap.put("status", "2005");
						resultmap.put("msg", Constants.getParamterkey("user.user.change.invalidno"));
						flag = false;
					}
					// 新验证码
					if (flag) {
						if (StringUtils.isBlank(params.get("newcode")) || params.get("newcode").length() != 6) {
							resultmap.put("status", "2026");
							resultmap.put("msg", "user.user.change.invalidnewcode");
							flag = false;
						} else {// 核对验证码
							TMemSm sms = userService.geTMemSmsByTelephone(params.get("newcode"));
							if (sms != null && !sms.getCode().equals(params.get("newcode"))) {
								resultmap.put("status", "2026");
								resultmap.put("msg", "user.user.change.invalidnewcode");
								flag = false;
							}
						}
					}
					if (flag) {
						resultmap.put("partnerid", requestParams.get("partnerid"));
						// service处理业务逻辑
						userService.updatePhoneNO(super.getUserid(params.get("token")), params.get("newmobile"));
						String rkey = super.md5str(resultmap);
						resultmap.put("key", rkey);
						resultmap.put("status", "0");
						resultmap.put("msg", "成功：您的手机号码已成功更换为： " + params.get("newmobile") + "，以后将以此手机号作为登录账号！");
					} else {
						resultmap.put("msg", "失败：系统错误，请稍后再试！");
					}
				}
			} else {
				String[] str = StringUtils.split(resultstr, "#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		} catch (Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			log.error("[UserFace.unbind] ==> "+Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 提交建议 - 平台、店铺 投诉&建议
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/complainsuggest.shtml")
	@ResponseBody
	public Object complainsuggest(Model model, HttpServletRequest request, HttpServletResponse response) {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.complainsuggest.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[UserFace.complainsuggest.resultstr] ==> "+resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"user".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// 验证comment
					if (flag && (StringUtils.isBlank(params.get("comment")) || params.get("comment").length() > 200)) {
						resultmap.put("status", "2008");
						resultmap.put("msg", Constants.getParamterkey("user.user.comment.invalidcomment"));
						flag = false;
					}
					// 验证type
					if (flag && (StringUtils.isBlank(params.get("type")) || (!params.get("type").equals("1") || !params.get("type").equals("0")))) {
						resultmap.put("status", "2009");
						resultmap.put("msg", Constants.getParamterkey("user.user.comment.invalidtype"));
						flag = false;
					}
					// 验证msgtype
					if (flag && (StringUtils.isBlank(params.get("msgtype")))) {
						resultmap.put("status", "2010");
						resultmap.put("msg", Constants.getParamterkey("user.user.comment.invalidmsgtype"));
						flag = false;
					}
					// 验证touserid
					if (flag && (StringUtils.isBlank(params.get("touserid")))) {
						resultmap.put("status", "2004");
						resultmap.put("msg", Constants.getParamterkey("user.user.touserid.error"));
						flag = false;
					}
					// 验证orderitemid
					if (flag && (StringUtils.isBlank(params.get("orderitemid")))) {
						resultmap.put("status", "2011");
						resultmap.put("msg", Constants.getParamterkey("user.user.comment.invalidorderitemid"));
						flag = false;
					}
					// service处理业务逻辑
					if (flag) {
						String imgurl = "";
						String imgurl2 = "";
						String imgurl3 = "";
						if (params.containsKey("imgurl") && params.get("imgurl") != null) {
							imgurl = params.get("imgurl");
						}
						if (params.containsKey("imgurl2") && params.get("imgurl") != null) {
							imgurl2 = params.get("imgurl2");
						}
						if (params.containsKey("imgurl3") && params.get("imgurl") != null) {
							imgurl3 = params.get("imgurl3");
						}
						userService.insertComplainSuggest(Integer.valueOf(super.getUserid(params.get("userid"))), Integer.valueOf(params.get("touserid")),
								Integer.valueOf(params.get("orderitemid")), params.get("orderno"), params.get("type"), params.get("comment"),
								Integer.valueOf(params.get("flag")), imgurl, imgurl2, imgurl3);
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("msg", "成功：您的【建议|投诉|问题】已经成功投递到【商家|客服】，处理结果将以系统消息的方式告知，请您耐心等待！");
						String rkey = super.md5str(resultmap);
						resultmap.put("key", rkey);
					}
				}
			} else {
				String[] str = StringUtils.split(resultstr, "#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		} catch (Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			log.error("[UserFace.unbind] ==> "+Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 验证用户资料是否已经完善
	 * 
	 * @Title: complete
	 * @param request
	 *            http请求
	 * @param response
	 *            http响应
	 * @return Map<String,String>
	 * @throws
	 */
	@RequestMapping("/complete.shtml")
	@ResponseBody
	public Map<String, String> complete(HttpServletRequest request, HttpServletResponse response) {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.complete.show] ==> is coming....");
		}
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, String> result = new TreeMap<String, String>();

		if(log.isInfoEnabled()) {
			log.info("[UserFace.complete.resultstr] ==> "+result);
		}	
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));

			@SuppressWarnings("unchecked")
			Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
			if (resultFlag.startsWith("0#")) {
				if (null == (params.get("token")) || "".equals(params.get("token"))) {
					result.put("status", "9009");
					result.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					String rkey = super.md5str(result);
					result.put("key", rkey);

					return result;
				}

				// 执行Service 返回map 添加进result

				result.putAll(userService.doComplete(super.getUserid(params.get("token"))));
				// result.putAll(userService.doComplete("10"));
				String rkey = super.md5str(result);
				result.put("key", rkey);
				return result;
			} else {
				log.error(resultFlag);
				result.put("status", resultFlag.split("#")[0]);
				result.put("msg", resultFlag.split("#")[1]);
				String rkey = super.md5str(result);
				result.put("key", rkey);
			}
		} catch (Exception e) {
			log.error("[User.UserFace.complete]====>" + Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str(result);
			result.put("key", rkey);
		}
		return result;
	}

	@RequestMapping("/validforgetpwd.shtml")
	@ResponseBody
	public Object dovalidForget(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, String> result = new TreeMap<String, String>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));
			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));

				// 判断data 中是否包含token
				if (StringUtils.isBlank(params.get("token"))) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}
				if (StringUtils.isBlank(params.get("username"))) {
					result.put("status", "1006");
					result.put("msg", Constants.getParamterkey("login.login.username"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}
				if (StringUtils.isBlank(params.get("code"))) {
					result.put("status", "6019");
					result.put("msg", Constants.getParamterkey("user.cash.forget.pass.code.error"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}
				if (StringUtils.isBlank(params.get("passpwd"))) {
					result.put("status", "1007");
					result.put("msg", Constants.getParamterkey("login.login.pwd"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}

				// 业务逻辑处理

				result.putAll(userService.doValidForgetPwd(params.get("username").toString(),params.get("code").toString() , params.get("passpwd").toString(),requestParams.get("partnerid").toString()));


				result.put("partnerid", requestParams.get("partnerid"));
				String rkey = super.md5str(result);
				result.put("key", rkey);
				return callback(requestParams.get("callback"), result, request, response);

			}
			log.info("[Cash.User.doValidForget]====>" + resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str(result);
			result.put("key", rkey);
			return callback(requestParams.get("callback"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str(result);
			result.put("key", rkey);
			log.error("[Cash.User.doValidForget]====>" + Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return callback(requestParams.get("callback"), result, request, response);
	}

	@RequestMapping("/forgetpwd.shtml")
	@ResponseBody
	public Object doForgetPwd(HttpServletRequest request, HttpServletResponse response) {
		if(log.isInfoEnabled()) {
			log.info("[UserFace.forgetpwd.show] ==> is coming....");
		}
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, String> result = new TreeMap<String, String>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));

			if(log.isInfoEnabled()) {
			log.info("[UserFace.forgetpwd.result] ==> "+result);
		}
			result.put("partnerid", requestParams.get("partnerid"));
			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));

				// 判断data 中是否包含token
				if (StringUtils.isBlank(params.get("token"))) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}
				if (StringUtils.isBlank(params.get("username"))) {
					result.put("status", "1006");
					result.put("msg", Constants.getParamterkey("login.login.username"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}
				if (StringUtils.isBlank(params.get("code"))) {
					result.put("status", "6019");
					result.put("msg", Constants.getParamterkey("user.cash.forget.pass.code.error"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}
				if (StringUtils.isBlank(params.get("passpwd"))) {
					result.put("status", "1007");
					result.put("msg", Constants.getParamterkey("login.login.pwd"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}

				if (StringUtils.isBlank(params.get("newpwd"))) {
					result.put("status", "6017");
					result.put("msg", Constants.getParamterkey("ser.cash.paypwd.params.error"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}

				if (StringUtils.isBlank(params.get("sign"))) {
					result.put("status", "9001");
					result.put("msg", Constants.getParamterkey("common.md5valid"));
					String rkey = super.md5str(result);
					result.put("key", rkey);
					return callback(requestParams.get("callback"), result, request, response);
				}
				super.getPartnerid();
				// 业务逻辑处理
				result.putAll(userService.doForgetPwd(params.get("username"), params.get("code"), params.get("passpwd"), params.get("newpwd"),
						params.get("sign"), requestParams.get("partnerid")));

				// result.putAll(cashService.doPwdisExist("2"));
				result.put("partnerid", requestParams.get("partnerid"));
				String rkey = super.md5str(result);
				result.put("key", rkey);
				return callback(requestParams.get("callback"), result, request, response);

			}
			log.info("[Cash.User.doValidForget]====>" + resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str(result);
			result.put("key", rkey);
			return callback(requestParams.get("callback"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str(result);
			result.put("key", rkey);
			log.error("[Cash.User.doValidForget]====>" + Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		 return callback(requestParams.get("callback"), result, request, response);
	}
	
	/**
	 * 商家用户建议/投诉查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/suggest.shtml")
	@ResponseBody
	public Object listSuggest(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				boolean passflag = true;
				Map<String, String> data = (Map<String, String>) JSON.parse(requestParams.get("data"));
				//token校验
				if(passflag && "".equals(data.get("token"))){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				//service校验
				if(passflag && !"shop".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				if(StringUtils.isBlank("page")||StringUtils.isBlank("pagesize")){
					resultmap.put("status","9013");
					resultmap.put("msg",Constants.getParamterkey("common.param.isnull"));
					passflag = false;
				}
				//是否登录校验
				if(!super.isLogin(data.get("token"))){
					resultmap.put("status", "9010");
					resultmap.put("msg", Constants.getParamterkey("common.login.token"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					Page resultPage = userService.listSuggest(data);
					resultmap.put("data", resultPage.getResult());
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("total",String.valueOf(resultPage.getTotalCount()));
					resultmap.put("page", String.valueOf(resultPage.getCurrentPageNo()));
					resultmap.put("pagesize", String.valueOf(resultPage.getPageSize()));
					resultmap.put("msg", "成功");
					resultmap.put("status", "0");
					
					String rkey  = super.md5str2(resultmap);
					resultmap.put("key",rkey);
				}
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		return callback2(requestParams.get("callback"),resultmap,request,response);
	}
}
