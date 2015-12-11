package com.bdbvip.product.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TProdList;
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.interfaces.vo.SaleAuctionRegistVo;
import com.bdbvip.product.interfaces.vo.SaleTMemBiddingVo;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.product.service.AuctionService;
import com.bdbvip.product.service.ProductService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;

/**
 * 拍卖相关接口controller
 * 
 * @ClassName: SaleFace
 * @Description: TODO
 * @date 2015年12月3日 下午4:03:16
 */

@Controller
@RequestMapping("/interface/product")
public class ProductFace extends BaseAction {

	private final static Logger logger = Logger.getLogger(ProductFace.class);

	@Autowired
	ProductService productService;	
	/**
	 * 商品上传
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/ddddsubmit.shtml")
	@ResponseBody
	public Object submitProduct(HttpServletRequest request, HttpServletResponse response) {
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
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if(passflag && "".equals(params.get("token"))){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				if(passflag && !"product".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				if(passflag && params.get("proname").length()>50){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				//后续加入对所以参数的校验
				//通过校验,进行业务处理
				if(passflag){
					TProdList product = new TProdList();
					product.setProcode("");
					product.setTitle(params.get("title"));
					product.setType("");
					product.setPrice(new BigDecimal(params.get("price")));
					product.setMprice(new BigDecimal(params.get("mprice")));
					product.setPrice(new BigDecimal(params.get("offprice")));
					/*product.setImgurl(imgurl);
					product.setImgurl2(imgurl);
					product.setImgurl3(imgurl);
					product.setImgurl4(imgurl);
					product.setImgurl5(imgurl);
					product.setImgurl6(imgurl);
					product.setImgurl7(imgurl);
					product.setImgurl8(imgurl);
					product.setImgurl9(imgurl);
					product.setCertiedimg(certiedimg);
					product.setCertiedimg2(certiedimg2);*/
					product.setProtype("0");//默认0商品 1拍卖商品
					product.setCertiednon("");
					product.setIssend("");//默认0不包邮 1包邮
					product.setCount(null);
					product.setSaletitle(params.get("saletitle"));
					product.setUserid(Integer.valueOf(super.getUserid(params.get("token"))));
					
					product = (TProdList)productService.saveProduct(product);
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("status", "0");
					resultmap.put("msg", "上传成功");
					resultmap.put("procode", product.getProcode());
					
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
