package com.bdbvip.utils;


public class CommUtil {
	
	//注册验证码Cookie里面的KEY --REG
	public static final String VALIDATE_CODE_COOKIE_NAME = "_bdbvalidcode"; 
 
	//注册验证码密钥[加密规则  当前生成的验证码]
	public static final String VALIDATE_CODE_FOR_REG_KEY = "20151118-!@#$1234689QWewqdsdfs";
	
	 
	
	//Cookie中存的判断当前用户是否登录的 key
    public	static final String LOGIN_TOKEN_CODE = "_token";
    //Cookie中存储渠道的key
    public static final String CHANNEL_NO = "_chno";
    //Cookie中的渠道
    public static final String CHANNEL = "_ch";
    //Cookie中存储source的key
	public static final String SOURCE = "_source";
	public static final String DOMAIN = "_domain";
    //NickName,UserId
     public static final String  LOGINCOOKIESTR = "_bdbnu";
   
     
     
    // qq彩贝id
    public static final String 	OPENID = "_openid";
    // 接入口
    public static final String 	ATTACH = "_attach";
    
 
    //正式环境所有的域名
    public static final String NET_NAMES = "passport,tools,www,kj,zj";
    
    //大于3次就需要输验证码 在登陆的时候当密码输入错误次数大于等于3次就需要输入验证码
    public static final int ERRORCOUNT = 3 ;
    
    
    
    
    
    
    /*****************************本站注册 返回信息  **********************************/
    /**
	 * 前台调用必需PassportConstants.getProdSysDictMap("410001", true)(检查异常时是否提示);
	 * 如果以后接口或是其它地方调用时,需要相同的提示语而且不检查异常时是否提示,
	 * 新增加变量,值:PassportConstants.getProdSysDictMap("410001", false)(不检查异常时是否提示)
	 */
    
    
    
    /**
	 * 操作异常,请重新操作(异常不提示时,替换内容)
	 */
    public static final String OPERATION_ERROR_PASSPORT_WEB = "操作异常,请重新操作";
    
    
	/*****************************本站注册 返回信息  **********************************/	    
    
    
	/*****************************GJ注册 返回信息  (本站登入不要用)**********************************/
    
    /**
     * 支付宝用户ID为空
     */
    public static final String REG_RETURN_ALIPAYUSERID_IS_NULL = "支付宝用户ID为空";
    /**
     * token为空
     */
    public static final String REG_RETURN_TOKEN_IS_NULL = "token为空";
    /**
     * token已失效
     */
    public static final String REG_RETURN_TOKEN_LOSE = "token已失效";
    /**
     * token信息错误
     */
    public static final String REG_RETURN_TOKEN_IS_ERROR = "token信息错误";
    /**
     * token有效
     */
    public static final String REG_RETURN_TOKEN_VALID = "token有效";
    /**
     * 当前登陆信息有误
     */
    public static final String REG_RETURN_LOGIN_ERROR= "当前登陆信息有误";
   
       
    
    /**
     * 用户名为空
     */
    public static final String REG_RETURN_USERNAME_IS_NULL = "用户名为空";
    
    /**
     * 用户名只能是中英文数字
     */
    public static final String REG_RETURN_USERNAME_IS_ERROR = "用户名只能是中英文数字";
    
    /**
     * 用户名长度在4~50个字符之间
     */
    public static final String REG_RETURN_USERNAME_LENGTH = "用户名长度在4~50个字符之间";
    /**
     * 用户名含非法字符
     */
    public static final String REG_RETURN_USERNAME_ILLEGAL = "用户名含非法字符";
    /**
     * 用户名重复,请重新输入
     */
    public static final String REG_RETURN_USERNAME_SAME = "用户名重复,请重新输入";
    /**
     * 密码为空
     */
    public static final String REG_RETURN_PASSWORD_IS_NULL = "密码为空";
    /**
     * 密码长度不正确
     */
    public static final String REG_RETURN_PASSWORD_LENGTH = "密码长度不正确";
    
    /**
     * 传入用户信息为空
     */
    public static final String REG_RETURN_MEMBER_IS_NULL = "传入用户信息为空";
    /**
     * 用户信息不存在
     */
    public static final String REG_RETURN_MEMBER_IS_NON = "用户信息不存在";
    /**
     * 当前用户被锁定
     */
    public static final String REG_RETURN_MEMBER_IS_LOCK = "当前用户被锁定";
    /**
     * 当前用户已经进行了实名认证
     */
    public static final String REG_RETURN_MEMBER_IS_SMRZ = "当前用户已经进行了实名认证";
    /**
     * 当前用户未完成实名认证
     */
    public static final String REG_RETURN_MEMBER_NOT_SMRZ = "当前用户未完成实名认证";
    /**
     * 当前账户已绑定了银行卡
     */
    public static final String REG_RETURN_MEMBER_BANDNO_BINDING = "当前账户已绑定了银行卡";
    /**
     * 真实姓名不能为空
     */
    public static final String REG_RETURN_REALNAME_NOTNULL = "真实姓名不能为空";
    /**
     * 真实姓名必需为中文汉字
     */
    public static final String REG_RETURN_REALNAME_IN_ZWHZ = "真实姓名必需为中文汉字";
    /**
     * 用户身份证号码不能为空
     */
    public static final String REG_RETURN_CARDNO_NOTNULL = "用户身份证号码不能为空";
    /**
     * 身份证号码异常
     */
    public static final String REG_RETURN_CARDNO_IS_ERROR = "身份证号码异常";
    /**
     * 用户身份证号码与真实姓名不匹配
     */
    public static final String REG_RETURN_CARDNO_REALNAME_ERROR = "用户身份证号码与真实姓名不匹配";
    /**
     * 手机号码不能为空
     */
    public static final String REG_RETURN_MOBILE_NOTNULL = "手机号码不能为空";
    /**
     * 手机号码格式不正确
     */
    public static final String REG_RETURN_MOBILE_FORMAT_ERROR = "手机号码格式不正确";
    /**
     * 手机已绑定其它用户
     */
    public static final String REG_RETURN_MOBILE_BINDING = "手机已绑定其它用户";
    /**
     * 银行卡号不能为空
     */
    public static final String REG_RETURN_BANDNO_NOTNULL = "银行卡号不能为空";
    /**
     * 银行名称不能为空
     */
    public static final String REG_RETURN_BANDNAME_NOTNULL = "银行名称不能为空";
    /**
     * 银行代码不能为空
     */
    public static final String REG_RETURN_BANDCODE_NOTNULL = "银行代码不能为空";
    /**
     * 银行信息不能为空
     */
    public static final String REG_RETURN_BANDINFO_NOTNULL = "银行信息不能为空";
    /**
     * 省份不能为空
     */
    public static final String REG_RETURN_PROVINCE_NOTNULL = "省份不能为空";
    
    /**
     * 城市不能为空
     */
    public static final String REG_RETURN_CITY_NOTNULL = "城市不能为空";
    
    
    /**
     * 开户银行能为空
     */
    public static final String REG_RETURN_OPENBANK_NOTNULL = "开户银行能为空";
    /**
     * 设备IMEI不能为空
     */
    public static final String REG_RETURN_IMEI_NOTNULL = "设备IMEI不能为空";
    /**
     * 渠道号不能为空
     */
    public static final String REG_RETURN_CHANNELNO_NOTNULL = "渠道号不能为空";
    /**
     * 平台信息不能为空
     */
    public static final String REG_RETURN_CHANNEL_NOTNULL = "平台信息不能为空";
    /**
     * 平台信息有误
     */
    public static final String REG_RETURN_CHANNEL_IS_ERROR = "平台信息有误";
    /**
     * 手机机型不能为空
     */
    public static final String REG_RETURN_PHONETYPE_NOTNULL = "手机机型不能为空";
    /**
     * 手机分辨率不能为空
     */
    public static final String REG_RETURN_RESOLUTION_NOTNULL = "手机分辨率不能为空";
    
    /**
     * 反馈内容不能为空
     */
    public static final String REG_RETURN_FEEDBACK_NOTNULL = "反馈内容不能为空";
    /**
     * 反馈信息内容大于200个字符
     */
    public static final String REG_RETURN_FEEDBACK_OVERLENGTH = "反馈信息内容大于200个字符";
    /**
     * 联系方式大于50个字符
     */
    public static final String REG_RETURN_CONTACT_WAY_OVERLENGTH = "联系方式大于50个字符";

    /**
     * macAddr信息不能为空
     */
    public static final String REG_RETURN_MACADDER_NOTNULL = "macAddr信息不能为空";
    
    /**
     * md5Str串不能为空
     */
    public static final String REG_RETURN_MD5STR_NOTNULL = "md5Str串不能为空";
    
    /**
     * 加密密钥不对
     */
    public static final String REG_RETURN_MD5STR_IS_ERROR = "加密密钥不对";
    
    /**
     * 一天只能向一个手机号码发送3次验证码
     */
    public static final String REG_RETURN_MSG_THREE_OVERRUN = "一天只能向一个手机号码发送3次验证码";
    
    /**
     * 网络异常
     */
    public static final String REG_RETURN_DAO_ERROR = "网络异常";
    /**
     * 请求参数为空
     */
    public static final String REG_RETURN_REQUEST_IS_NULL = "请求参数为空";
    
    /**
     * 请发送验证码到手机上
     */
    public static final String REG_RETURN_SEND_VERIFICATION = "请发送验证码到手机上";
    /**
     * 验证码错误
     */
    public static final String REG_RETURN_VERIFICATION_ERROR = "验证码错误";
    
    /**
     * 验证码已失效
     */
    public static final String REG_RETURN_VERIFICATION_LOSE = "验证码已失效";
    /**
     * 验证码为空
     */
    public static final String REG_RETURN_VERIFICATION_IS_NULL = "验证码为空";
    
    
    /**
     * 绑定成功
     */
    public static final String REG_RETURN_BINDING_SUCCED = "绑定成功";
    /**
     * 绑定成功,登入失败
     */
    public static final String REG_RETURN_BINDING_SUCCED_NOTLOGIN = "绑定成功,登入失败";
    /**
     * 注销成功
     */
    public static final String REG_RETURN_LOGOUT_SUCCED = "注销成功";
    /**
     * 操作成功
     */
    public static final String REG_RETURN_OPERATE_SUCCED = "操作成功";
    /**
     * 注册成功
     */
    public static final String REG_RETURN_LOGIN_SUCCED = "注册成功";
    /**
     * 成功
     */
    public static final String REG_RETURN_SUCCED = "成功";
    /**
     * 短信发送成功
     */
    public static final String REG_RETURN_SMS_SEND_SUCCED = "短信发送成功";
    /**
     * 短信发送失败
     */
    public static final String REG_RETURN_SMS_SEND_ERROR = "短信发送失败";
    /**
     * 快捷登陆失败
     */
    public static final String REG_RETURN_SHORTCUT_LOGIN_ERROR = "快捷登陆失败";
    
    
    /**
     * 未配制无线充值优惠活动
     */
    public static final String REG_RETURN_ACTIVITY_BASE_IS_NULL = "未配制无线充值优惠活动";
    
    /**
     * 当前渠道号不享受优惠
     */
    public static final String REG_RETURN_CHANNELNO_BASE_NOT_ACTIVITY = "当前渠道号不享受优惠";
    
    
    /**
     * 未完善真实姓名或身份证号或手机号
     */
    public static final String REG_RETURN_REALNAME_CARDNO_MOBILE_OR_NULL = "未完善真实姓名或身份证号或手机号";
    
    /**
     * 未完善真实姓名或身份证号
     */
    public static final String REG_RETURN_REALNAME_CARDNO_OR_NULL = "未完善真实姓名或身份证号";
    /**
     * 未完善手机号
     */
    public static final String REG_RETURN_MOBILE_OR_NULL = "未完善手机号";
    
    /**
     * 享受优惠次数已达上限
     */
    public static final String REG_RETURN_FAVORABLE_OVERRUN = "享受优惠次数已达上限";
    
    /**
     * 享受优惠次数已达上限
     */
    public static final String REG_RETURN_PLAYTYPE_FAVORABLE_OVERRUN = "享受优惠次数已达上限";
    /**
     * 已超出活动有效时间
     */
    public static final String REG_RETURN_CURRENTTIME_OVERRUN_ACTIVITY = "已超出活动有效时间";
    
    
    /**
     * 已享受过充值优惠
     */
    public static final String REG_RETURN_FAVORABLE_IS_YES = "已享受过充值优惠";
    /**
     * 未享受过充值优惠
     */
    public static final String REG_RETURN_FAVORABLE_IS_NO = "未享受过充值优惠";
    
    
    
/*****************************GJ注册验证 返回信息  **********************************/    
    
    
    
}
