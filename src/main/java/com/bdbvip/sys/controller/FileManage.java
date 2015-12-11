package com.bdbvip.sys.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.DESADESecuritys;
import com.bdbvip.utils.common.BaseAction;

/**
 * 上传文件  下载文件
 * @author wyong
 */
@Controller
@RequestMapping("/sms")
public class FileManage extends BaseAction{

    private final static String  ROOT_PATH = Constants.getParamterkey("common.file.path");
    
    private static final Logger logger = Logger
            .getLogger(FileManage.class);

    /**
     * 文件上传
     * dir  /base/{userid}
     * 		/shop/{year}/{month}/{day}/{userid}
     * 		
     * @param name
     * @param file
     * @param dir
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(
                             @RequestParam("name") String name,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam(value="dir",required = false,defaultValue = "/tmp") String dir,
                             @RequestParam(value="bool",required = false,defaultValue = "true") boolean bool,
                             @RequestParam(value="encoding",required = false,defaultValue = "") String encoding,
                             @RequestParam(value="service",required=true) String service,
                           
                             @RequestParam(value="partnerid",required=true)String partnerid,
                             @RequestParam(value="callback",required=false,defaultValue="") String callback,HttpServletRequest request,HttpServletResponse response ) {
        
    	if(logger.isInfoEnabled()){
        	logger.info("[name:"+name+",filename:"+file.getOriginalFilename()+",size:"+file.getSize()+",dir:"+dir+",bool:"+bool+",encoding:"+encoding+"]");
        }
    	
		Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("partnerid",partnerid);
		boolean passflag = true;
		 
		if(passflag && StringUtils.isBlank(partnerid)){
			resultmap.put("status","9998");
			resultmap.put("msg",Constants.getParamterkey("common.param.error"));
			passflag = false;
		}
		if(passflag && (StringUtils.isBlank(service)||!"sms".equals(service))){
			resultmap.put("status","9007");
			resultmap.put("msg",Constants.getParamterkey("common.param.service"));
			passflag = false;
		}
    	if(passflag && (StringUtils.isNotBlank(dir) && Constants.getConfigkey("common.file.allow.path").indexOf(dir)== -1)){
    		resultmap.put("status","9011");
			resultmap.put("msg",Constants.getParamterkey("common.file.path"));
			passflag = false;
    	}
    	if(passflag){
	        dir=(dir==null||"".equals(dir))?"":(File.separator+dir);
	        String dirPath = ROOT_PATH+dir;
	        if (!file.isEmpty()) {
	            try {
	                String realName = this.copyFile(file.getInputStream(), dirPath, name,bool,encoding);
	                logger.info("[name:"+name+",returnName:"+dir+File.separator+realName+"]");
	                resultmap.put("status","0");
	                resultmap.put("msg","上传成功");
	                resultmap.put("path",dir+File.separator+realName);
	                return  super.callback(callback, resultmap, request, response);
	            } catch (IOException e) {
	                logger.error("文件上传",e);
	            }
	        }else{
	        	resultmap.put("status","9012");
	        	resultmap.put("msg",Constants.getParamterkey("common.file.nofiles"));
	            logger.info("[name:"+name+"] 文件为空");
	            return super.callback(callback, resultmap, request, response);
	        }
    	}
        return super.callback(callback, resultmap, request, response);
    }



    /**
     * 多文件上传
     * @param request
     * @param dir
     * @return
     */
    @RequestMapping(value = "/multipartUpload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload2(
            MultipartHttpServletRequest request,
            @RequestParam(value="dir",required = false,defaultValue = "/tmp") String dir,
            @RequestParam(value="bool",required = false,defaultValue = "true") boolean bool,
            @RequestParam(value="encoding",required = false,defaultValue = "") String encoding,
            @RequestParam(value="service",required=true) String service,
            @RequestParam(value="partnerid",required=true)String partnerid,
            @RequestParam(value="callback",required=false,defaultValue="") String callback,HttpServletResponse response) {

    	Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("partnerid",partnerid);
		boolean passflag = true;
		 
		if(passflag && StringUtils.isBlank(partnerid)){
			resultmap.put("status","9998");
			resultmap.put("msg",Constants.getParamterkey("common.param.error"));
			passflag = false;
		}
		if(passflag && (StringUtils.isBlank(service)||!"sms".equals(service))){
			resultmap.put("status","9007");
			resultmap.put("msg",Constants.getParamterkey("common.param.service"));
			passflag = false;
		}
    	if(passflag && (StringUtils.isNotBlank(dir) && Constants.getConfigkey("common.file.allow.path").indexOf(dir)== -1)){
    		resultmap.put("status","9011");
			resultmap.put("msg",Constants.getParamterkey("common.file.path"));
			passflag = false;
    	}
    	if(passflag){
	        List<String> realNames = new ArrayList<String>();
	        List<MultipartFile> files = request.getFiles("file");
	        logger.info("[size:"+files.size()+",dir:"+dir+",bool:"+bool+",encoding:"+encoding+"]"+files);
	        dir=dir==null||"".equals(dir)?"":(File.separator+dir);
	        String dirPath = ROOT_PATH+dir;
	        StringBuilder pathstr = new StringBuilder();
	        for (int i = 0; i < files.size(); i++) {
	            if (!files.get(i).isEmpty()) {
	                String realName=null;
	                try {
	                   realName = this.copyFile(files.get(i).getInputStream(), dirPath, files.get(i).getOriginalFilename(),bool,encoding);
	                   realName = dir+File.separator+realName;
	                } catch (IOException e) {
	                   logger.error("多文件上传",e);
	                }
	                realNames.add(realName);
	            }
	        }
	        if(realNames!=null && !realNames.isEmpty()){
	        	resultmap.put("status","0");
	        	resultmap.put("msg",files.size()+"个文件上传成功");
	        	resultmap.put("path",StringUtils.join(realNames, "#"));
	        }
    	}
        return callback(callback,resultmap,request,response);
    }

    /**
     * 文件下载
     * @param response
     * @param request
     * @param pathDecrypt 加密内容  DESADESecuritys.encodeBASE64(DESADESecuritys.encryptEde('/t/t/1.txt'))
     *                    download/t/t/1.txt
     *                    download?pathDecrypt= /t/t/1.txt&flag=1
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response, HttpServletRequest request, String pathDecrypt, @RequestParam(value="flag",required = false,defaultValue = "0") String flag) {
        logger.info("[pathDecrypt:"+pathDecrypt+",flag:"+flag+"]");
        OutputStream os = null;
        response.reset();
        if(pathDecrypt==null||"".equals(pathDecrypt)) return;

        String filePath=null;
        try {
            pathDecrypt=pathDecrypt.replaceAll(" ","");
            if(flag!=null&&!"1".equals(flag))
            filePath=DESADESecuritys.decryptEde(DESADESecuritys.decodeBASE64(pathDecrypt));
            else filePath=pathDecrypt.trim();
        }catch (Exception e){
            logger.error("文件下载",e);

        }

        if(filePath==null||"".equals(filePath)) return;
        filePath = ROOT_PATH+filePath;

        String realName="";
        if(filePath.lastIndexOf("/")>-1){
            realName=filePath.substring(filePath.lastIndexOf("/")+1);
        }
        response.setHeader("Content-Disposition", "attachment; filename="+realName);
        response.setContentType("application/octet-stream; charset=utf-8");
        try {
            logger.info("下载路径"+filePath);
            os = response.getOutputStream();
            os.write(FileUtils.readFileToByteArray(new File(filePath)));
            os.flush();
        } catch (IOException e) {
            logger.error("文件下载",e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("文件下载",e);
                }
            }
        }
    }




    /**
     * 移动文件
     * @param srcFile 源文件路径 windows\abc.txt
     * @param destPath 移动路径  temp\ww
     * @return
     */
    @RequestMapping("/moveFile")
    @ResponseBody
    public String  moveFile( String srcFile, String destPath){
        logger.info("[srcFile:"+srcFile+",destPath:"+destPath+"]");
       if(srcFile==null||"".equals(srcFile)||destPath==null||"".equals(destPath))
           return null;
        File fileStr= new File(ROOT_PATH+srcFile);
        try{
        File destFile = new File(ROOT_PATH+destPath, fileStr.getName());
       if (!destFile.exists()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
           destFile.createNewFile();
        }
            FileUtils.copyFile(fileStr,destFile);
            logger.info("[srcFile:"+srcFile+",destPath:"+destPath+"] 复制文件成功");
            return fileStr.getName();
        }catch (Exception e){
            logger.error("移动文件",e);
        }
        return null;
    }

    /**
     * 写文件到当前目录的download目录中
     * @param in
     * @param fileName
     * @param bool
     * @throws java.io.IOException
     */
    private String copyFile(InputStream in, String dir, String fileName,boolean bool,String encoding) throws IOException {
        String pre = "";
        if(fileName.indexOf(".")>-1)
        pre=fileName.substring(fileName.lastIndexOf("."));
        String realName = null;
        if(bool){
            realName = UUID.randomUUID().toString() + pre;
        }else{
             realName=fileName;
        }
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();

        }
        if(encoding==null||"".equals(encoding)){
            FileUtils.copyInputStreamToFile(in, file);
        }else{
            copyInputStreamToFile(in,file,encoding);
        }


        return realName;
    }

    private   void copyInputStreamToFile(InputStream in,File file,String encoding) {
        InputStreamReader  isr=null;
        OutputStreamWriter out=null;
        try {
            isr =new InputStreamReader(in);
            out =new OutputStreamWriter(
                    new FileOutputStream(file), encoding);
            char [] buff=new char[1024 * 4];
            int data = 0;
            while ((data=isr.read(buff)) != -1) {
                out.write(buff,0,data);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(out!=null) out.close();
                if(isr!=null)  isr.close();
                if(in!=null) in.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }



}

