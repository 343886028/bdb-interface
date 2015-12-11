package com.bdbvip.login.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemBase;
import com.bdbvip.entity.TMemLog;
import com.bdbvip.entity.TMemSm;
import com.bdbvip.entity.TSysConfig;
import com.bdbvip.login.dao.LoginDAO;
import com.bdbvip.login.service.LoginService;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.sys.dao.SmsDAO;
import com.bdbvip.user.dao.UserDAO;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.Md5Util;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.date.DateUtil;
import com.bdbvip.utils.common.exception.ServiceException;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserDAO userDao;
	
	@Autowired
	LoginDAO loginDao;
	
	@Autowired
	SmsDAO smsDao;
	
	public Page findAllByStatus(TSysConfig config, Page page)
			throws ServiceException {
		try{
			return loginDao.findAllByStatus(config, page);
		}catch(Exception e){
			
		}
		return page;
	}

	/* (non-Javadoc)
	 * @see com.bdbvip.login.service.LoginService#save(com.bdbvip.utils.common.domain.entity.Entity)
	 */
	public Serializable saveEntity(Serializable entity) throws ServiceException {
		 return  loginDao.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.bdbvip.login.service.LoginService#validLoginPwd(com.bdbvip.pojo.MembaseVo)
	 */
	public MembaseVo validLoginPwd(MembaseVo vo) {
		//验证用户名，短信code,身份证号是否三个全部相同。通过，则按 cardno+code+uname+signstr返回成功加密串。
		TMemBase base = userDao.getUserByName(vo.getUname());
		if(base == null){
			vo.setErrorcode("1006");
			vo.setMsg(Constants.getParamterkey("login.login.username"));
			return vo;
		}
		if(!vo.getCardno().equalsIgnoreCase(base.getCardno())){
			vo.setErrorcode("9017");
			vo.setMsg(Constants.getParamterkey("common.param.notvalid"));
			return vo;
		}
		//获取发送的短信记录。
		TMemSm sm = new TMemSm();
		sm.setTouserid(base.getId());
		sm.setCreatetime(DateUtil.addMinutes(new Date(), -Constants.SMS_MAX_VALID));
		Page p = new Page();p.setCurrentPageNo(0);
		p = smsDao.findBySms(sm, p);
		boolean pass  = false;
		if(p!=null && p.getResult()!=null && !p.getResult().isEmpty()){
			List<TMemSm> smlist = p.getResult();
			for(TMemSm s : smlist){
				if(s.getCode().equals(vo.getCode())){
					pass = true;
					break;
				}
			}
		}else{
			vo.setErrorcode("1018");
			vo.setMsg(Constants.getParamterkey("login.login.smsinvalid"));
			return vo;
		}
		if(!pass){
			vo.setErrorcode("9017");
			vo.setMsg(Constants.getParamterkey("common.param.notvalid"));
			return vo;
		}
		//code+cardno+uname+signstr
		StringBuilder sb = new StringBuilder();
		sb.append(vo.getCode()).append(vo.getCardno()).append(vo.getUname()).append(Constants.SMS_SIGSTR);
		vo.setErrorcode("0");
		vo.setMsg(Md5Util.md5_32(sb.toString()));
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.bdbvip.login.service.LoginService#updateLoginPwd(com.bdbvip.pojo.MembaseVo)
	 */
	public MembaseVo updateLoginPwd(MembaseVo vo) {
		//验证用户名，短信code,身份证号是否三个全部相同。通过，则按 cardno+code+uname+signstr返回成功加密串。
			TMemBase base = userDao.getUserByName(vo.getUname());
			if(base == null){
				vo.setErrorcode("1006");
				vo.setMsg(Constants.getParamterkey("login.login.username"));
				return vo;
			}
			if(!vo.getCardno().equalsIgnoreCase(base.getCardno())){
				vo.setErrorcode("9017");
				vo.setMsg(Constants.getParamterkey("common.param.notvalid"));
				return vo;
			}
			//获取发送的短信记录。
			TMemSm sm = new TMemSm();
			sm.setTouserid(base.getId());
			sm.setCreatetime(DateUtil.addMinutes(new Date(), -Constants.SMS_MAX_VALID));
			Page p = new Page();p.setCurrentPageNo(0);
			p = smsDao.findBySms(sm, p);
			boolean pass  = false;
			if(p!=null && p.getResult()!=null && !p.getResult().isEmpty()){
				List<TMemSm> smlist = p.getResult();
				for(TMemSm s : smlist){
					if(s.getCode().equals(vo.getCode())){
						pass = true;
						break;
					}
				}
			}else{
				vo.setErrorcode("1018");
				vo.setMsg(Constants.getParamterkey("login.login.smsinvalid"));
				return vo;
			}
			if(pass){
				vo.setErrorcode("9017");
				vo.setMsg(Constants.getParamterkey("common.param.notvalid"));
				return vo;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(vo.getCode()).append(vo.getCardno()).append(vo.getUname()).append(Constants.SMS_SIGSTR);
			if(Md5Util.md5_32(sb.toString()).equalsIgnoreCase(vo.getSign())){
				//更新用户的新密码
				TMemLog log = new TMemLog();
				log.setCreatetime(new Date());
				log.setLogmsg("[updateLoginPwd找回密码]原密码->"+base.getPasswd()+";新密码->"+vo.getNewpwd());
				log.setLogtype("login");
				log.setUserid(base.getId());
				log.setOptuserid(String.valueOf(base.getId()));
				userDao.save(log);
				
				base.setPasswd(vo.getNewpwd());
				userDao.update(base);
				vo.setErrorcode("0");
				vo.setMsg("修改密码成功!");
			}else{
				vo.setErrorcode("9001");
				vo.setMsg("sign"+Constants.getParamterkey("common.md5valid"));
				return vo;
			}
			//code+cardno+uname+signstr
			return vo;
	}

}
