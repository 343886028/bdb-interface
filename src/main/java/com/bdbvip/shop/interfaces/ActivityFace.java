package com.bdbvip.shop.interfaces;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TProdActive;
import com.bdbvip.entity.TProdJoin;
import com.bdbvip.pojo.ActivityVo;
import com.bdbvip.shop.service.ActivityService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.dao.support.Page;

@Controller
@RequestMapping("/interface/activity")
public class ActivityFace extends BaseAction {
	
	@Autowired
	ActivityService activityService;
	
	/**
	 * 平台活动查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/query.shtml")
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				//传入参数验证
				boolean passflag = true;
				ActivityVo vo = JSON.parseObject(requestParams.get("data"), ActivityVo.class);
				if(passflag && !"activity".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				if(passflag && "".equals(vo.getToken())){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				if(passflag && StringUtils.isBlank("page")||StringUtils.isBlank("pagesize")){
					resultmap.put("status","9013");
					resultmap.put("msg",Constants.getParamterkey("common.param.isnull"));
					passflag = false;
				}
				//通过验证,进行业务处理
				if(passflag){
					Page resultPage = activityService.listActivity(vo);
					resultmap.put("data", resultPage.getResult());
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("total",resultPage.getTotalCount());
					resultmap.put("page", resultPage.getCurrentPageNo());
					resultmap.put("pagesize", resultPage.getPageSize());
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
	
	/**
	 * 参加平台活动
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/join.shtml")
	public Object join(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				boolean passflag = true;
				ActivityVo vo = JSON.parseObject(requestParams.get("data"), ActivityVo.class);
				//传入参数验证
				if(passflag && !"shop".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				if(passflag && "".equals(vo.getToken())){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				if(passflag && vo.getId()==null){
					resultmap.put("status","8001");
					resultmap.put("msg",Constants.getParamterkey("shop.activity.activityid.null"));
					passflag = false;
				}
				if(passflag && !super.isLogin(vo.getToken())){
					resultmap.put("status", "9010");
					resultmap.put("msg", Constants.getParamterkey("common.login.token"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					TProdActive active = activityService.getActiveById(vo.getId());
					//判断活动有效性
					if("1".equals(active.getStatus())&&"1".equals(active.getAduitflag())){
						//判断是否已经报名过了,审核中或者审核通过都不再允许报名
						TProdJoin tj = activityService.getJoinById(active.getId(),Integer.valueOf(super.getUserid(vo.getToken())));
						if(tj!=null&&tj.getStatus().equals("2")){
							resultmap.put("msg", Constants.getParamterkey("shop.activity.join.repeat"));
							resultmap.put("status", "8004");
						}else{
							TProdJoin join = new TProdJoin();
							join.setActiveid(active.getId());
							join.setActivename(active.getActivename());
							join.setActiveuserid(active.getFrouser());
							join.setStatus("0");//默认 0未审核 1 通过 2 未通过
							join.setUserid(Integer.parseInt(super.getUserid(vo.getToken())));
							join.setProid(vo.getProid());
							join.setRemark(vo.getRemark());
							activityService.saveTProdJoin(join);
							
							resultmap.put("msg", Constants.getParamterkey("shop.activity.join.success"));
							resultmap.put("status", "8003");
						}
					}else{
						resultmap.put("status", "8002");
						resultmap.put("msg", Constants.getParamterkey("shop.activity.join.invalid"));
					}
					resultmap.put("partnerid", requestParams.get("partnerid"));
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
	
	/**
	 * 平台活动审核状态查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryaduit.shtml")
	public Object queryAduit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				//传入参数验证
				boolean passflag = true;
				ActivityVo vo = JSON.parseObject(requestParams.get("data"), ActivityVo.class);
				if(passflag && !"activity".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				if(vo.getId()==null){
					resultmap.put("status","8001");
					resultmap.put("msg",Constants.getParamterkey("shop.activity.activityid.null"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					TProdActive active = activityService.getActiveById(vo.getId());
					if(active==null){
						resultmap.put("status","8005");
						resultmap.put("msg",Constants.getParamterkey("shop.activity.nonexistent"));
					}else{
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("msg", active.getRemark());
						resultmap.put("auditstatus", active.getAduitflag());//默认0 未审 1 已通过 2未通过
						
						String rkey  = super.md5str2(resultmap);
						resultmap.put("key",rkey);
					}
				}
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		return callback2(requestParams.get("callback"),resultmap,request,response);
	}
}
