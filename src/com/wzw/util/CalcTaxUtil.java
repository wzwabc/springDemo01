package com.wzw.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcTaxUtil {
	// 税率
	private final static double[] TAX_RAT = { 0.00, 0.03, 0.1, 0.2, 0.25, 0.3, 0.35, 0.45 };

	// 月工资等级
	private final static double[] MONTH_SALARY_LEVEL = { 3500, 1500 + 3500, 4500 + 3500, 9000 + 3500, 35000 + 3500, 55000 + 3500, 80000 + 3500, 80000 * 12 + 3500 };

	// 最小梯度值，用于计算税率时去除精度误差
	private final static double PRECISION = 1e-6;

	public static Map<String, String> getOptSolution(double yearCash, double monthSalary) {
		// 年终奖等级
		Map<Double, Double> yearLevel = getYearLevel(yearCash, monthSalary);

		// 获取达到各个等级的边界，所需要分配的年终奖集合
		List<Double> reduceFromYearCash = getReduceFromYear(yearCash, monthSalary, yearLevel);

		return getBestWay(yearCash, monthSalary, reduceFromYearCash);
	}

	private static Map<String, String> getBestWay(double yearCash, double monthSalary, List<Double> reduceFromYearCash) {
		double bestCutOffFromYearCash = 0;
		double minTax = getTotalTax(yearCash, monthSalary, 0);
		
		// 将分配方案从小到大排序，保证税额相同时，取分配出的年终奖最少的方案
		Collections.sort(reduceFromYearCash);
		for (int i = 0; i < reduceFromYearCash.size(); i++) {
			// 获取分配后的总税额
			double totalTax = getTotalTax(yearCash, monthSalary, reduceFromYearCash.get(i));
			if (totalTax < minTax) {
				minTax = totalTax;
				bestCutOffFromYearCash = reduceFromYearCash.get(i);
			}
		}
		// 最优方案中，应发年终奖为
		double bestYearCash = yearCash - bestCutOffFromYearCash;

		// 最优方案中，年终奖产生和税额
		double finalYearTax = getYearTax(bestYearCash, monthSalary);

		// 按两个月分配
		// 每月分配到的额度为
		double firstMonth = bestCutOffFromYearCash / 2;
		double secondMonth = firstMonth;
		// 分配到年终奖后的月税，减去原月税，即为由年终奖产生的税
		double finalMonthTaxByYear = getMonthTax(monthSalary + firstMonth) - getMonthTax(monthSalary);

		// 按一个月分配
		double oneMonth = bestCutOffFromYearCash;
		if (getMonthTax(monthSalary + oneMonth)+getMonthTax(monthSalary) <= getMonthTax(monthSalary + firstMonth)*2) {
			firstMonth = bestCutOffFromYearCash;
			secondMonth = 0.0;
		}
		// 最少年终奖税为
		double finalMinYearTax = finalYearTax + finalMonthTaxByYear * 2;
		double realTotalBonus = yearCash - finalMinYearTax;
		
		bestCutOffFromYearCash = Double.parseDouble(String.format("%.2f", bestCutOffFromYearCash));
		bestYearCash = yearCash - bestCutOffFromYearCash;
		firstMonth = Double.parseDouble(String.format("%.2f", firstMonth));
		secondMonth = bestCutOffFromYearCash-firstMonth;
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("optYearBonus", String.format("%.2f", bestYearCash));
		result.put("optMonth1Bonus", String.format("%.2f", firstMonth));
		result.put("optMonth2Bonus", String.format("%.2f", secondMonth));
		result.put("optMinTax", String.format("%.2f", finalMinYearTax));
		result.put("realTotalBonus", String.format("%.2f", realTotalBonus));
		result.put("wage", String.format("%.2f", yearCash));
		result.put("tax", String.format("%.2f", yearCash-realTotalBonus));
		return result;
	}

	private static double getTotalTax(double yearCash, double monthSalary, double cutOffFromYearCash) {
		double yearTax = getYearTax(yearCash - cutOffFromYearCash, monthSalary);
		double monthTax = getMonthTax(monthSalary + cutOffFromYearCash / 2);

		return yearTax + monthTax * 2;
	}

	// 根据月计税工资额获取应交税金
	public static double getMonthTax(double salary) {
		// 月工资应纳税所得额
		double shouldPayTaxOfSalary = salary - 3500;

		if (shouldPayTaxOfSalary - 80000 > PRECISION)
			return shouldPayTaxOfSalary * 0.45 - 13505;

		if (shouldPayTaxOfSalary - 55000 > PRECISION)
			return shouldPayTaxOfSalary * 0.35 - 5505;

		if (shouldPayTaxOfSalary - 35000 > PRECISION)
			return shouldPayTaxOfSalary * 0.3 - 2755;

		if (shouldPayTaxOfSalary - 9000 > PRECISION)
			return shouldPayTaxOfSalary * 0.25 - 1005;

		if (shouldPayTaxOfSalary - 4500 > PRECISION)
			return shouldPayTaxOfSalary * 0.2 - 555;

		if (shouldPayTaxOfSalary - 1500 > PRECISION)
			return shouldPayTaxOfSalary * 0.1 - 105;

		if (shouldPayTaxOfSalary - 0 > PRECISION)
			return shouldPayTaxOfSalary * 0.03;

		return 0;
	}

	// 根据年终奖和当月工资，获取年终奖应该交税金
	public static double getYearTax(double yearCash, double curMonthSalary) {
		// 获取应交税所得额
		double shouldPayTaxOfYearCash = yearCash;
		if (curMonthSalary < 3500) {
			shouldPayTaxOfYearCash = (yearCash - (3500 - curMonthSalary));
		}

		// 获取每月平均分配到的额度
		double averageShouldPayTaxOfYearCash = shouldPayTaxOfYearCash / 12;
		if (averageShouldPayTaxOfYearCash - 80000 > PRECISION)
			return shouldPayTaxOfYearCash * 0.45 - 13505;

		if (averageShouldPayTaxOfYearCash - 55000 > PRECISION)
			return shouldPayTaxOfYearCash * 0.35 - 5505;

		if (averageShouldPayTaxOfYearCash - 35000 > PRECISION)
			return shouldPayTaxOfYearCash * 0.3 - 2755;

		if (averageShouldPayTaxOfYearCash - 9000 > PRECISION)
			return shouldPayTaxOfYearCash * 0.25 - 1005;

		if (averageShouldPayTaxOfYearCash - 4500 > PRECISION)
			return shouldPayTaxOfYearCash * 0.2 - 555;

		if (averageShouldPayTaxOfYearCash - 1500 > PRECISION)
			return shouldPayTaxOfYearCash * 0.1 - 105;

		if (averageShouldPayTaxOfYearCash > PRECISION)
			return shouldPayTaxOfYearCash * 0.03;

		return 0.0;
	}

	// 获取达到等级边界所需要的所有值
	public static List<Double> getReduceFromYear(double yearCash, double monthSalary, Map<Double, Double> yearLevel) {
		List<Double> reduceFromYearCash = new ArrayList<Double>();
		// 获取年终奖税率
		double YearTaxRat = getYearTaxRat(yearCash, monthSalary);
		for (int i = 0; i < TAX_RAT.length; i++) {
			if (TAX_RAT[i] >= YearTaxRat)
				break;
			// 边界的值为
			double nextLevel = yearLevel.get(TAX_RAT[i]);
			// 需要分配出去的年终奖为
			double cutOffFromYearCash = yearCash - nextLevel;

			reduceFromYearCash.add(cutOffFromYearCash);

		}

		// 获取月工资税率
		double monthTaxRat = getMonthTaxRat(monthSalary);
		for (int i = TAX_RAT.length; i > 0; i--) {
			if (TAX_RAT[i - 1] < monthTaxRat)
				break;

			// 等级的边界值
			double nextLevel = MONTH_SALARY_LEVEL[i - 1];
			// 可以分配的额度为
			double couldAdd = nextLevel - monthSalary;
			// 需要分配出去的年终奖为
			double cutOffFromYearCash = couldAdd * 2;
			reduceFromYearCash.add(cutOffFromYearCash);
		}

		return reduceFromYearCash;
	}

	// 根据年终奖和月工资，获取年终奖的等级
	private static Map<Double, Double> getYearLevel(double yearCash, double monthSalary) {
		Map<Double, Double> rtnMap = new HashMap<Double, Double>();
		// 年终奖中不需要交税的金额
		double noTax = 0;
		if (monthSalary < 3500) {
			noTax = 3500 - monthSalary;
		}

		rtnMap.put(0.45,  100000000.00*12.00 + noTax);
        rtnMap.put(0.35,  80000*12.00 + noTax);
        rtnMap.put(0.3,  55000*12.00 + noTax);
        rtnMap.put(0.25,  35000*12.00 + noTax);
        rtnMap.put(0.2,  9000*12.00 + noTax);
        rtnMap.put(0.1,  4500*12.00  + noTax);
        rtnMap.put(0.03,  1500*12.00 + noTax);
        rtnMap.put(0.00,  noTax);

		return rtnMap;
	}

	// 根据年终奖获取税率
	private static double getYearTaxRat(double yearCash, double monthSalary) {

		// 获取应纳税所得额
		double shouldPatTaxCash = yearCash;
		if (monthSalary < 3500) {
			shouldPatTaxCash = shouldPatTaxCash - (3500 - monthSalary);
		}

		double avg = shouldPatTaxCash / 12.0;

		if (avg - 80000 > PRECISION)
			return 0.45;

		if (avg - 55000 > PRECISION)
			return 0.35;

		if (avg - 35000 > PRECISION)
			return 0.3;

		if (avg - 9000 > PRECISION)
			return 0.25;

		if (avg - 4500 > PRECISION)
			return 0.2;

		if (avg - 1500 > PRECISION)
			return 0.1;

		if (avg > PRECISION)
			return 0.03;

		return 0.0;
	}

	// 根据月工资获取税率
	private static double getMonthTaxRat(double monthSalary) {
		// 应纳税所得额
		double shouldPatTaxOfSalary = monthSalary - 3500;
		if (shouldPatTaxOfSalary - 80000 > PRECISION)
			return 0.45;

		if (shouldPatTaxOfSalary - 55000 > PRECISION)
			return 0.35;

		if (shouldPatTaxOfSalary - 35000 > PRECISION)
			return 0.3;

		if (shouldPatTaxOfSalary - 9000 > PRECISION)
			return 0.25;

		if (shouldPatTaxOfSalary - 4500 > PRECISION)
			return 0.2;

		if (shouldPatTaxOfSalary - 1500 > PRECISION)
			return 0.1;

		if (shouldPatTaxOfSalary - 0 > PRECISION)
			return 0.03;

		return 0.0;
	}
}
