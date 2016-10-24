package com.wzw.tax.service;

import java.util.List;

import com.wzw.tax.data.Bonus;

public interface BonusService {
	
	public String addBonus(Bonus bonus);
	
	public List<Bonus> getAll();

}
