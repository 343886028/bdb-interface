package com.bdbvip.sys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemSm;
import com.bdbvip.entity.TSysPartner;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.sys.dao.SmsDAO;
import com.bdbvip.sys.dao.SysDAO;
import com.bdbvip.sys.service.SysService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.Posturl;
import com.bdbvip.utils.ThreadUtil;
import com.bdbvip.utils.TradUtil;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.date.DateUtil;
import com.bdbvip.utils.common.exception.ServiceException;

@Service("sysService")
public class SysServiceImpl implements SysService {

	@Autowired
	SysDAO sysDao;
	
	@Autowired
	SmsDAO smsDao;
	
	public final Map<String, TSysPartner> findAll() throws ServiceException {
		Map<String,TSysPartner> rmap = new HashMap<String,TSysPartner>();
		try{
			List<TSysPartner> rlist = sysDao.findAll();
			if(null != rlist && !rlist.isEmpty()){
				for(TSysPartner p : rlist){
					rmap.put(p.getPartnername(),p);
				}
			}
		} catch(Exception e){
			throw new ServiceException(e.getMessage(),e);
		}
		return rmap;
	}

	/**
	 * @param sms
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	public Page findTMemSms(TMemSm sms,Page page) throws ServiceException {
		return smsDao.findBySms(sms, page);
	}

	/* (non-Javadoc)
	 * @see com.bdbvip.sys.service.SysService#createTMemSm(com.bdbvip.pojo.MembaseVo)
	 */
	public MembaseVo createTMemSm(MembaseVo vo) throws ServiceException {
		//查询一天，手机号是否达到5条。
		Page p = new Page();
		TMemSm sm = new TMemSm();
		sm.setCreatetime(DateUtil.addDays(new Date(),-1));
		sm.setTelphone(vo.getMobile());
		sm.setType(vo.getType());
		sm.setTouserid(Integer.valueOf(vo.getUserid()));
		p  = smsDao.findBySms(sm, p);
		if(p!=null && p.getResult()!=null && p.getResult().size()>5){
			vo.setErrorcode("1019");
			vo.setMsg(Constants.getParamterkey("login.sms.dovermaxsms"));
			return vo;
		}
		//默认十分钟内，超过三条就不再发送，
		sm.setCreatetime(DateUtil.addMinutes(new Date(), - Constants.SMS_MAX_VALID));
		p = smsDao.findBySms(sm, p);
		if(p!=null && p.getResult()!=null && p.getResult().size()>3){
			vo.setErrorcode("1020");
			vo.setMsg(Constants.getParamterkey("login.sms.movermaxsms"));
			return vo;
		}
		String code = vo.getCode();
		if(StringUtils.isBlank(code)){
			code = TradUtil.getRandom(6);
		}
		String sms = TradUtil.getSms(vo.getType(), code);
		//是否是测试，不实际发送短信。1 不实际发送，0 默认发送。
		boolean issend = "0".equals(Constants.getConfigkey("sms.istest"));
		if(issend){
			Map<String,String> data = new HashMap<String,String>();
			data.put("m",vo.getMobile());
			data.put("c",sms);
			ThreadUtil.pool.execute(new Thread(Posturl.postRequest(Constants.getConfigkey("sms.sendurl"), data)));
		}
		//写入记录，同时调用发送短信发送接口。
		sm.setCreatetime(new Date());
		sm.setCode(code);
		sm.setContent(sms);
		sm.setType(vo.getType());
		sm.setTouserid(Integer.valueOf(vo.getUserid()));
		sm.setValidtime(DateUtil.addMinutes(new Date(),  Constants.SMS_MAX_VALID));
		smsDao.save(sm);
		
		vo.setCode(code);
		vo.setErrorcode("0");
		vo.setMsg("短信发送成功");
		return vo;
	}

	public MembaseVo create2thTMemSm(MembaseVo vo) throws ServiceException {
		//取出最后一条发送的短信记录，同时赋给将要发送的短信。重新发送最后一条短信。
		Page p = new Page();
		TMemSm s = new TMemSm();
		s.setTelphone(vo.getMobile());
		s.setTouserid(Integer.valueOf(vo.getUserid()));
		s.setType(vo.getType());
		s.setCreatetime(DateUtil.addDays(new Date(),-1));
		p  = smsDao.findBySms(s, p);
		if(p!=null && p.getResult()!=null && p.getResult().size()>5){
			vo.setErrorcode("1019");
			vo.setMsg(Constants.getParamterkey("login.sms.dovermaxsms"));
			return vo;
		}
		//默认十分钟内，超过三条就不再发送，
		s.setCreatetime(DateUtil.addMinutes(new Date(), - Constants.SMS_MAX_VALID));
		p = smsDao.findBySms(s, p);
		if(p!=null && p.getResult()!=null && p.getResult().size()>3){
			vo.setErrorcode("1020");
			vo.setMsg(Constants.getParamterkey("login.sms.movermaxsms"));
			return vo;
		}
		p = smsDao.findBySms(s, p);
		if(p!=null && p.getResult()!=null && !p.getResult().isEmpty()){
			s = (TMemSm) p.getResult().get(0);
		}
		if(s!=null && !StringUtils.isBlank(s.getCode())){
			vo.setCode(s.getCode());
		}else{
			vo.setCode(TradUtil.getRandom(6));
		}
		String code = vo.getCode();
		if(StringUtils.isBlank(code)){
			code = TradUtil.getRandom(6);
		}
		String sms = TradUtil.getSms(vo.getType(), code);
		//是否是测试，不实际发送短信。1 不实际发送，0 默认发送。
		boolean issend = "0".equals(Constants.getConfigkey("sms.istest"));
		if(issend){
			Map<String,String> data = new HashMap<String,String>();
			data.put("m",vo.getMobile());
			data.put("c",sms);
			ThreadUtil.pool.execute(new Thread(Posturl.postRequest(Constants.getConfigkey("sms.sendurl"), data)));
		}
		//写入记录，同时调用发送短信发送接口。
		TMemSm sb  = new TMemSm();
		sb.setCreatetime(new Date());
		sb.setCode(code);
		sb.setContent(sms);
		sb.setType(vo.getType());
		sb.setTouserid(Integer.valueOf(vo.getUserid()));
		sb.setTelphone(vo.getMobile());
		sb.setValidtime(DateUtil.addMinutes(new Date(),  Constants.SMS_MAX_VALID));
		smsDao.save(sb);
		
		vo.setCode(code);
		vo.setErrorcode("0");
		vo.setMsg("短信发送成功");
		return vo;
	}

	
	 

	 
}
