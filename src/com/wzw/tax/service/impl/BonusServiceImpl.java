package com.wzw.tax.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzw.tax.dao.BonusMapper;
import com.wzw.tax.data.Bonus;
import com.wzw.tax.service.BonusService;
import com.wzw.util.UUIDUtil;

@Service("bonusService")
public class BonusServiceImpl implements BonusService {
	
	@Autowired
	BonusMapper bonusMapper;

	@Override
	public String addBonus(Bonus bonus) {
		String id=UUIDUtil.generateUUID();
		bonus.setId(id);
		try{
			bonusMapper.addBonus(bonus);
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		return id;
	}
	
	@Override
	public List<Bonus> getAll() {
		List<Bonus>  bonusList=null;
		try{
			bonusList=new ArrayList<Bonus>();
			bonusList=bonusMapper.findAll();
		}catch(Exception e){
			
		}
		return bonusList;
	}

}
