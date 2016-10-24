package com.wzw.tax.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wzw.tax.data.Bonus;
import com.wzw.tax.service.BonusService;
import com.wzw.util.CalcTaxUtil;

@Controller
@RequestMapping("/tax/bonus")
public class BonusController {
	
	@Autowired
	BonusService bonusService;
	/**
	 * 查询数据
	 * 
	 */
	@RequestMapping("/query")
	@ResponseBody   //在这里将list数组通过框架转换成json返回到bonus.js中，然后显示到前端页面中
	public List<Bonus> query(HttpServletRequest request,HttpServletResponse response){//可以在这里添加一个断点，逐步执行并在firbug里观察
		List<Bonus> list=bonusService.getAll();
		
		//request.setAttribute("list", list);
		return list ;
	}
	
	/**
	 * 上传文件
	 * 获取文件，以流的方式读入，然后逐条计算税，然后存储到数据库中
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request,
            @RequestParam("file") MultipartFile file)throws Exception {
		if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            //获取文件的二进制流数据
        	byte[] bytes = file.getBytes();
        	//byte[]转String
        	String str=new String(bytes,"UTF-8");
            try {
               
            	/********上传文件****/
            	File uploadfolder=new File("d:/upload");  //上传目录
            	File uploadFile=new File(uploadfolder.getAbsolutePath()+"/"+fileName);  //上传文件
            	System.out.println(uploadFile.getAbsolutePath());
            	if(!uploadfolder.exists()){
            		boolean flag=uploadfolder.mkdirs();
            		if(!flag){ //如果创建失败抛出异常
            			throw new Exception("文件夹创建失败!!原因未知");
            		}
            	}
            	if(!uploadFile.exists()){
            		boolean flag=uploadFile.createNewFile();
            		if(!flag){ //如果创建失败抛出异常
            			throw new Exception("文档创建失败!!原因未知");
            		}
            	}
            	BufferedWriter writer=new BufferedWriter(new FileWriter(uploadFile));//创建一个输出流
            	writer.write(str);
            	writer.close();
            	/******上传完成*******/
            	//上传文件成功开始解析
            	
            	Scanner scanner = new Scanner(new FileInputStream(uploadFile));
            	double salary = 0;
            	double yearCash = 0;
            	Map<String, String> result = new HashMap<String, String>();
            	Bonus bonus = new Bonus();
            	while(scanner.hasNext()){
            		yearCash = scanner.nextDouble();
            		salary = scanner.nextDouble();
            		result = CalcTaxUtil.getOptSolution(yearCash, salary);
            		//bonus.setId(UUIDUtil.generateUUID());
            		bonus.setWage(salary);
            		bonus.setBonus(yearCash);
            		bonus.setOptYearBonus(Double.parseDouble(result.get("optYearBonus")));
            		bonus.setOptMonth1Bonus(Double.parseDouble(result.get("optMonth1Bonus")));
            		bonus.setOptMonth2Bonus(Double.parseDouble(result.get("optMonth2Bonus")));
            		bonus.setRealTotalBonus(Double.parseDouble(result.get("realTotalBonus")));
            		bonus.setTax(Double.parseDouble(result.get("optMinTax")));
            		/* 在此循环中将得到的数据插入到bonus表中
            		 * ***************************************************************/
            		String id=bonusService.addBonus(bonus);
            		System.out.println(id);
            	}
            	System.out.println("插入成功!");
                return "success:" + fileName;
            } catch (Exception e) {
                return "fail:上传文件失败";
            }
        } else {
            return "fail:未选择要上传的文件";
        }
	}

}
