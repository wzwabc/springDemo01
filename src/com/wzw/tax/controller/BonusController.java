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
	 * ��ѯ����
	 * 
	 */
	@RequestMapping("/query")
	@ResponseBody   //�����ｫlist����ͨ�����ת����json���ص�bonus.js�У�Ȼ����ʾ��ǰ��ҳ����
	public List<Bonus> query(HttpServletRequest request,HttpServletResponse response){//�������������һ���ϵ㣬��ִ�в���firbug��۲�
		List<Bonus> list=bonusService.getAll();
		
		//request.setAttribute("list", list);
		return list ;
	}
	
	/**
	 * �ϴ��ļ�
	 * ��ȡ�ļ��������ķ�ʽ���룬Ȼ����������˰��Ȼ��洢�����ݿ���
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request,
            @RequestParam("file") MultipartFile file)throws Exception {
		if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            //��ȡ�ļ��Ķ�����������
        	byte[] bytes = file.getBytes();
        	//byte[]תString
        	String str=new String(bytes,"UTF-8");
            try {
               
            	/********�ϴ��ļ�****/
            	File uploadfolder=new File("d:/upload");  //�ϴ�Ŀ¼
            	File uploadFile=new File(uploadfolder.getAbsolutePath()+"/"+fileName);  //�ϴ��ļ�
            	System.out.println(uploadFile.getAbsolutePath());
            	if(!uploadfolder.exists()){
            		boolean flag=uploadfolder.mkdirs();
            		if(!flag){ //�������ʧ���׳��쳣
            			throw new Exception("�ļ��д���ʧ��!!ԭ��δ֪");
            		}
            	}
            	if(!uploadFile.exists()){
            		boolean flag=uploadFile.createNewFile();
            		if(!flag){ //�������ʧ���׳��쳣
            			throw new Exception("�ĵ�����ʧ��!!ԭ��δ֪");
            		}
            	}
            	BufferedWriter writer=new BufferedWriter(new FileWriter(uploadFile));//����һ�������
            	writer.write(str);
            	writer.close();
            	/******�ϴ����*******/
            	//�ϴ��ļ��ɹ���ʼ����
            	
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
            		/* �ڴ�ѭ���н��õ������ݲ��뵽bonus����
            		 * ***************************************************************/
            		String id=bonusService.addBonus(bonus);
            		System.out.println(id);
            	}
            	System.out.println("����ɹ�!");
                return "success:" + fileName;
            } catch (Exception e) {
                return "fail:�ϴ��ļ�ʧ��";
            }
        } else {
            return "fail:δѡ��Ҫ�ϴ����ļ�";
        }
	}

}
