package com.wzw.tax.dao;


import java.util.List;

import com.wzw.tax.data.Bonus;

public interface BonusMapper {
	
	public void addBonus(Bonus bonus);
	
	public List<Bonus> findAll();

}
