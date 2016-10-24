package com.wzw.tax.data;

public class Bonus {
	private String id;
	
	private double wage;
	
	private double bonus;
	
	
	private double optYearBonus;
	
	private double optMonth1Bonus;
	
	private double optMonth2Bonus;
	
	private double realTotalBonus;
	
	private double tax;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getWage() {
		return wage;
	}

	public void setWage(double wage) {
		this.wage = wage;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public double getOptYearBonus() {
		return optYearBonus;
	}

	public void setOptYearBonus(double optYearBonus) {
		this.optYearBonus = optYearBonus;
	}

	public double getOptMonth1Bonus() {
		return optMonth1Bonus;
	}

	public void setOptMonth1Bonus(double optMonth1Bonus) {
		this.optMonth1Bonus = optMonth1Bonus;
	}

	public double getOptMonth2Bonus() {
		return optMonth2Bonus;
	}

	public void setOptMonth2Bonus(double optMonth2Bonus) {
		this.optMonth2Bonus = optMonth2Bonus;
	}

	public double getRealTotalBonus() {
		return realTotalBonus;
	}

	public void setRealTotalBonus(double realTotalBonus) {
		this.realTotalBonus = realTotalBonus;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}
	
}
