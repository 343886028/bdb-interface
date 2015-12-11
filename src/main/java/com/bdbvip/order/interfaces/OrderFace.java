package com.bdbvip.order.interfaces;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TMemCashTradeRecord;
import com.bdbvip.entity.TMemMsg;
import com.bdbvip.entity.TOrdBase;
import com.bdbvip.entity.TOrdItem;
import com.bdbvip.order.service.OrderService;
import com.bdbvip.user.service.CashService;
import com.bdbvip.user.service.UserService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.DateUtils;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.date.DateUtil;
import com.bdbvip.utils.common.exception.ServiceException;

@Controller
@RequestMapping("/interface/order")
public class OrderFace extends BaseAction {
	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	@Autowired
	CashService cashService;

	/**
	 * 订单查询
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/query.shtml")
	@ResponseBody
	public Object query(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.index.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.query.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					if (flag && (StringUtils.isBlank(params.get("page")) || StringUtils.isBlank(params.get("pagesize")))) {
						resultmap.put("status", "2002");
						resultmap.put("msg", Constants.getParamterkey("user.user.invalidpage"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						TOrdBase orderCondition = new TOrdBase();
						orderCondition.setUserid(Integer.valueOf(super.getUserid(params.get("token"))));
						if (StringUtils.isNotBlank(params.get("btime"))) {
							orderCondition.setCreatetime(DateUtils.string2Date(params.get("createtime"), DateUtils.simpleDateFormat3));
						}
						if (StringUtils.isNotBlank(params.get("orderno"))) {
							orderCondition.setOrderno(params.get("orderno"));
						}
						if (StringUtils.isNotBlank(params.get("orderstatus"))) {
							orderCondition.setOrderstatus(params.get("orderstatus"));
						}
						if (StringUtils.isNotBlank(params.get("touserid"))) {
							orderCondition.setTouserid(Integer.valueOf(params.get("touserid")));
						}
						if (StringUtils.isNotBlank(params.get("money"))) {
							orderCondition.setOrdermoney(new BigDecimal(Integer.valueOf(params.get("money"))));
						}
						Page page = orderService.getOrderListByCondition(orderCondition, Integer.valueOf(params.get("page")), Integer.valueOf(params.get("pagesize")));
						List<TOrdBase> orderList = (List<TOrdBase>) page.getResult();
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("total", String.valueOf(page.getTotalCount()));
						resultmap.put("page", String.valueOf(page.getCurrentPageNo()));
						resultmap.put("pagesize", String.valueOf(page.getPageSize()));
						resultmap.put("list", JSON.toJSONString(orderList));
						resultmap.put("msg", "查询成功");
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.query] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 订单详情查询
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/detail/query.shtml")
	@ResponseBody
	public Object getOrderDetail(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.getOrderDetail.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.getOrderDetail.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					if (flag && (StringUtils.isBlank(params.get("page")) || StringUtils.isBlank(params.get("pagesize")))) {
						resultmap.put("status", "2002");
						resultmap.put("msg", Constants.getParamterkey("user.user.invalidpage"));
						flag = false;
					}
					// orderNO
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						Page page = orderService.getOrderItemListByOrderNO(params.get("orderno"), Integer.valueOf(params.get("page")), Integer.valueOf(params.get("pagesize")));
						List<TOrdItem> orderItemList = (List<TOrdItem>) page.getResult();
						resultmap.put("parnterid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("total", String.valueOf(page.getTotalCount()));
						resultmap.put("page", String.valueOf(page.getCurrentPageNo()));
						resultmap.put("pagesize", String.valueOf(page.getPageSize()));
						resultmap.put("list", JSON.toJSONString(orderItemList));
						resultmap.put("msg", "查询成功");
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.getOrderDetail] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 订单付款 -- to do
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/paymoney.shtml")
	@ResponseBody
	public Object getOrderDetail2(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.getOrderDetail.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.getOrderDetail.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// orderNO订单号
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					// order money付款金额
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					if (flag) {// --to do touserid
						// service 支付业务
						Page page = orderService.getOrderItemListByOrderNO(params.get("orderno"), Integer.valueOf(params.get("page")), Integer.valueOf(params.get("pagesize")));
						List<TOrdItem> orderItemList = (List<TOrdItem>) page.getResult();
						resultmap.put("parnterid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("total", String.valueOf(page.getTotalCount()));
						resultmap.put("page", String.valueOf(page.getCurrentPageNo()));
						resultmap.put("pagesize", String.valueOf(page.getPageSize()));
						resultmap.put("list", JSON.toJSONString(orderItemList));
						resultmap.put("msg", "查询成功");
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.getOrderDetail] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 确认收货
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/ordreceive.shtml")
	@ResponseBody
	public Object receiveOrder(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.receiveOrder.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.receiveOrder.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// orderNO
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					// type
					if (flag && StringUtils.isBlank(params.get("type"))) {
						resultmap.put("status", "2031");
						resultmap.put("msg", Constants.getParamterkey("user.order.receive.invalidtype"));
						flag = false;
					}

					if (flag) {
						// 1.根据订单号和用户ID 查询该订单是否存在
						TOrdBase ordBase = orderService.findOrderByOrderCodeAndUserID(super.getUserid(params.get("token")), params.get("orderno"));
						if (ordBase == null) {
							resultmap.put("status", "2040");
							resultmap.put("msg", Constants.getParamterkey("user.order.cancel.invalidorder"));// 订单号不存在
							flag = false;
						} else {
							if (!ordBase.getSendflag().equals(1)) {
								resultmap.put("status", "2044");
								resultmap.put("msg", Constants.getParamterkey("user.order.remindsend.sendorderonly"));
								flag = false;
							} else {
								// 2.只有已发货订单，才能收货
								/**
								 * --- to do : 定时默认收货。任务调度：jdk自带的方式
								 * 和SpringQuartz方式
								 * 如果商家发货后7天用户不确认收货，也没有申请延期付款则系统自动确认收货
								 * （系统自动确认收货和用户主动确认收货状态都为
								 * 【已收货】，但需要区分是系统还是用户主动确认），订单进入下一流程；
								 */

								/**
								 * ---- to do : 遍历
								 * 所有订单的item，获取item的receiveFlag--收货状态 订单的
								 * receiveflag: 默认 为0, 表示所有的item均未收货; 1,
								 * 表示部分item收货； 2, 表示所有item全部收货。
								 */
								List<TOrdItem> orderItems = orderService.getOrderItemListByOrderNO(params.get("orderno"));
								List<String> itemReceiveFlags = new ArrayList<String>();
								String receiveFlag = "";
								int count = 0;
								for (TOrdItem item : orderItems) {
									itemReceiveFlags.add(item.getReceiveflag());
									if ("1".equals(item.getReceiveflag()))
										count++;
								}
								if (count == orderItems.size()) {
									receiveFlag = "2";
								} else if (count > 0 && count < orderItems.size()) {
									receiveFlag = "1";
								} else {
									receiveFlag = "0";
								}

								orderService.doOrderRecieve(params.get("orderno"),receiveFlag);
								resultmap.put("parnterid", requestParams.get("partnerid"));
								resultmap.put("status", "0");
								resultmap.put("msg", "您的订单已确认收货，卖家将会收到您已支付的订单金额！");
								String rkey = super.md5str(resultmap);
								resultmap.put("key", rkey);
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.receiveOrder] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 延迟收货
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/orddelay.shtml")
	@ResponseBody
	public Object delayOrder(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.delayOrder.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.delayOrder.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// orderNO
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					// days
					if (flag && StringUtils.isBlank(params.get("days"))) {
						resultmap.put("status", "2031");
						resultmap.put("msg", Constants.getParamterkey("user.order.receive.invaliddays"));
						flag = false;
					}
					if (flag) {
						// 1.根据订单号和用户ID 查询该订单是否存在
						TOrdBase ordBase = orderService.findOrderByOrderCodeAndUserID(super.getUserid(params.get("token")), params.get("orderno"));
						if (ordBase == null) {
							resultmap.put("status", "2040");
							resultmap.put("msg", Constants.getParamterkey("user.order.cancel.invalidorder"));// 订单号不存在
							flag = false;
						} else {
							if (!ordBase.getSendflag().equals(1)) {
								resultmap.put("status", "2044");
								resultmap.put("msg", Constants.getParamterkey("user.order.remindsend.sendorderonly"));
								flag = false;
							} else {
								// 2.只有已发货订单，才能收货/延迟收货
								/**
								 * 由于物流等其他原因，导致买家迟迟未能收到货物，这种情形下，买家可以申请延迟收货。确保：
								 * 未收货的情况下，支付的钱不会打给卖家。(财货两空)
								 **/
								if (Integer.valueOf(params.get("days")).compareTo(Integer.valueOf("15")) > 0 || Integer.valueOf(params.get("days")).compareTo(Integer.valueOf("0")) < 0) {
									resultmap.put("status", "2046");
									resultmap.put("msg", Constants.getParamterkey("user.order.receive.delay15dayslimited"));
									flag = false;
								} else {
									orderService.doOrderDelay(params.get("orderno"), Integer.valueOf(params.get("days")).compareTo(Integer.valueOf("15")));
									resultmap.put("parnterid", requestParams.get("partnerid"));
									resultmap.put("status", "0");
									resultmap.put("msg", "您的订单已提交延迟收货，请关注您的订单物流信息！");
									String rkey = super.md5str(resultmap);
									resultmap.put("key", rkey);
								}
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.delayOrder] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 订单发货（店铺）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sendpro.shtml")
	@ResponseBody
	public Object sendGoods(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.sendGoods.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.sendGoods.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// orderNO订单编号
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					// sendNO快递单号
					if (flag && StringUtils.isBlank(params.get("sendno"))) {
						resultmap.put("status", "2032");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					// sendname快递公司
					if (flag && StringUtils.isBlank(params.get("sendname"))) {
						resultmap.put("status", "2033");
						resultmap.put("msg", Constants.getParamterkey("user.order.sendgoods.invalidsendname"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						orderService.doSendGoods(params.get("orderno"), params.get("orderno"), params.get("orderno"), new BigDecimal(params.get("sendmoney")), params.get("sendflag"));
						resultmap.put("parnterid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("msg", "发货成功");
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.sendGoods] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 提醒发货
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/remind.shtml")
	@ResponseBody
	public Object remindSendGoods(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.remindSendGoods.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.remindSendGoods.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// orderNO
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					if (flag) {
						// 1.根据订单号和用户ID 查询该订单是否存在
						TOrdBase ordBase = orderService.findOrderByOrderCodeAndUserID(super.getUserid(params.get("token")), params.get("orderno"));
						if (ordBase == null) {
							resultmap.put("status", "2040");
							resultmap.put("msg", Constants.getParamterkey("user.order.cancel.invalidorder"));// 订单号不存在
							flag = false;
						} else {
							// 2.检查订单状态是否为：未发货。
							if (!ordBase.getSendflag().equals(0)) {
								resultmap.put("status", "2040");
								resultmap.put("msg", Constants.getParamterkey("user.order.cancel.invalidorder"));// 订单号不存在
								flag = false;
							} else {
								// 3.订单存在，且订单状态为未发货, 提醒发货
								/**
								 * msgcontent:提醒发货内容, status有效标识 1, msgtype
								 * 消息类型,type 1针对店家
								 **/
								TMemMsg newMsg = new TMemMsg();
								newMsg.setFromuserid(Integer.valueOf(super.getUserid(params.get("token"))));
								newMsg.setTouserid(orderService.getOrderByCode("orderno").getTouserid());
								/** 查找交易记录信息 --> 交易流水号、交易时间 **/
								TMemCashTradeRecord tradeRecord = cashService.findTradeByOrderNO(params.get("orderno"));
								newMsg.setTradeno(tradeRecord.getTradeno());
								newMsg.setMsgcontent("【订单号： " + ordBase.getOrderno() + "】 已于" + DateUtil.string2Date(tradeRecord.getCreatetime().toString(), DateUtil.simpleDateFormat3)
										+ " 付款成功，请尽快发货!（点击此信息可以跳转到这个订单去查看订单信息、处理订单）；");
								newMsg.setCreatetime(new Date());
								newMsg.setStatus("1");
								newMsg.setMsgtype("1");
								newMsg.setType("1");
								orderService.doRemindSendGoods(newMsg);
								resultmap.put("parnterid", requestParams.get("partnerid"));
								resultmap.put("status", "0");
								resultmap.put("msg", "已成功提醒卖家发货！");
								String rkey = super.md5str(resultmap);
								resultmap.put("key", rkey);
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.remindSendGoods] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 订单评价
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/interface/order/evaluate.shtml")
	@ResponseBody
	public Object evaluate(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.evaluate.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.evaluate.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					if (flag && (StringUtils.isBlank(params.get("page")) || StringUtils.isBlank(params.get("pagesize")))) {
						resultmap.put("status", "2002");
						resultmap.put("msg", Constants.getParamterkey("user.user.invalidpage"));
						flag = false;
					}
					// orderNO
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						Page page = orderService.getOrderItemListByOrderNO(params.get("orderno"), Integer.valueOf(params.get("page")), Integer.valueOf(params.get("pagesize")));
						List<TOrdItem> orderItemList = (List<TOrdItem>) page.getResult();
						resultmap.put("parnterid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("total", String.valueOf(page.getTotalCount()));
						resultmap.put("page", String.valueOf(page.getCurrentPageNo()));
						resultmap.put("pagesize", String.valueOf(page.getPageSize()));
						resultmap.put("list", JSON.toJSONString(orderItemList));
						resultmap.put("msg", "查询成功");
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.evaluate] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 订单投诉
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/interface/order/complain.shtml")
	@ResponseBody
	public Object complain(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.complain.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.complain.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// orderNO
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					// orderItemId
					if (flag && StringUtils.isBlank(params.get("orderitemid"))) {
						resultmap.put("status", "2036");
						resultmap.put("msg", Constants.getParamterkey("user.order.invalidorderitemid"));
						flag = false;
					}
					// type 0,投诉 1,建议
					if (flag && StringUtils.isBlank(params.get("type"))) {
						resultmap.put("status", "2037");
						resultmap.put("msg", Constants.getParamterkey("user.order.complain.invalidtype"));
						flag = false;
					}
					// comment
					if (flag && StringUtils.isBlank(params.get("comment"))) {
						resultmap.put("status", "2039");
						resultmap.put("msg", Constants.getParamterkey("user.order.complain.invalidcomment"));
						flag = false;
					}
					// touserid
					if (flag && StringUtils.isBlank(params.get("touserid"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.user.order.complain.invalidtouserid"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						orderService.doOrderComplain(Integer.valueOf(super.getUserid(params.get("token"))), Integer.valueOf(params.get("touserid")), params.get("orderno"),
								Integer.valueOf(params.get("orderitemid")), params.get("type"), params.get("comment"), params.get("imgurl"), params.get("imgurl2"), params.get("imgurl3"));
						resultmap.put("parnterid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("msg", "查询成功");
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.complain] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 取消（终止）订单
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/detail/cancel.shtml")
	@ResponseBody
	public Object cancelOrder(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.cancelOrder.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.cancelOrder.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// orderNO
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					if (flag) {
						// 1.根据订单号和用户ID 查询该订单是否存在
						TOrdBase ordBase = orderService.findOrderByOrderCodeAndUserID(super.getUserid(params.get("token")), params.get("orderno"));
						if (ordBase == null) {
							resultmap.put("status", "2040");
							resultmap.put("msg", Constants.getParamterkey("user.order.cancel.invalidorder"));// 订单号不存在
							flag = false;
						} else {
							// 2.检查订单的支付状态是否为：未支付。只有未支付的订单状态，才可以进行"取消订单"操作。
							if (!ordBase.getPaystatus().equals(0)) {
								resultmap.put("status", "2041");
								resultmap.put("msg", Constants.getParamterkey("user.order.cancel.unpaidordersonly"));
								flag = false;
							} else {
								// 3.订单存在，且订单状态为'未支付'，进一步判断该订单是否为'拍卖订单'？拍卖订单不能取消！！！
								// -- to do . 拍卖订单
								orderService.doCancelOrder(params.get("orderno"));
								resultmap.put("parnterid", requestParams.get("partnerid"));
								resultmap.put("status", "0");
								resultmap.put("msg", "查询成功");
								String rkey = super.md5str(resultmap);
								resultmap.put("key", rkey);
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.cancelOrder] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}

	/**
	 * 修改订单价格（店铺）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/updateordermoney.shtml")
	@ResponseBody
	public Object modifyOrderPrice(Model model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.modifyOrderPrice.show] ==> is coming....");
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String> resultmap = new TreeMap<String, String>();
		// 解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		// 验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if (log.isInfoEnabled()) {
			log.info("[OrderFace.modifyOrderPrice.resultstr] ==> " + resultstr);
		}
		try {
			// 0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				@SuppressWarnings("unchecked")
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					// 验证service
					if (flag && !"order".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// orderNO
					if (flag && StringUtils.isBlank(params.get("orderno"))) {
						resultmap.put("status", "2030");
						resultmap.put("msg", Constants.getParamterkey("user.order.detail.invalidorderno"));
						flag = false;
					}
					// old money
					if (flag && StringUtils.isBlank(params.get("oldordermoney"))) {
						resultmap.put("status", "2034");
						resultmap.put("msg", Constants.getParamterkey("user.order.modifyprice.invalidoldmoney"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						orderService.updateOrderPrice(params.get("orderno"), params.get("oldordermoy"));
						resultmap.put("parnterid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("msg", "订单价格已修改成功为：" + params.get("oldordermoney"));
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
			log.error(e.getMessage(), e);
			log.error("[OrderFace.getOrderDetail] ==> " + Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}
}
