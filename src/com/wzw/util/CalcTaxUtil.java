package com.wzw.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcTaxUtil {
	// ˰��
	private final static double[] TAX_RAT = { 0.00, 0.03, 0.1, 0.2, 0.25, 0.3, 0.35, 0.45 };

	// �¹��ʵȼ�
	private final static double[] MONTH_SALARY_LEVEL = { 3500, 1500 + 3500, 4500 + 3500, 9000 + 3500, 35000 + 3500, 55000 + 3500, 80000 + 3500, 80000 * 12 + 3500 };

	// ��С�ݶ�ֵ�����ڼ���˰��ʱȥ���������
	private final static double PRECISION = 1e-6;

	public static Map<String, String> getOptSolution(double yearCash, double monthSalary) {
		// ���ս��ȼ�
		Map<Double, Double> yearLevel = getYearLevel(yearCash, monthSalary);

		// ��ȡ�ﵽ�����ȼ��ı߽磬����Ҫ��������ս�����
		List<Double> reduceFromYearCash = getReduceFromYear(yearCash, monthSalary, yearLevel);

		return getBestWay(yearCash, monthSalary, reduceFromYearCash);
	}

	private static Map<String, String> getBestWay(double yearCash, double monthSalary, List<Double> reduceFromYearCash) {
		double bestCutOffFromYearCash = 0;
		double minTax = getTotalTax(yearCash, monthSalary, 0);
		
		// �����䷽����С�������򣬱�֤˰����ͬʱ��ȡ����������ս����ٵķ���
		Collections.sort(reduceFromYearCash);
		for (int i = 0; i < reduceFromYearCash.size(); i++) {
			// ��ȡ��������˰��
			double totalTax = getTotalTax(yearCash, monthSalary, reduceFromYearCash.get(i));
			if (totalTax < minTax) {
				minTax = totalTax;
				bestCutOffFromYearCash = reduceFromYearCash.get(i);
			}
		}
		// ���ŷ����У�Ӧ�����ս�Ϊ
		double bestYearCash = yearCash - bestCutOffFromYearCash;

		// ���ŷ����У����ս�������˰��
		double finalYearTax = getYearTax(bestYearCash, monthSalary);

		// �������·���
		// ÿ�·��䵽�Ķ��Ϊ
		double firstMonth = bestCutOffFromYearCash / 2;
		double secondMonth = firstMonth;
		// ���䵽���ս������˰����ȥԭ��˰����Ϊ�����ս�������˰
		double finalMonthTaxByYear = getMonthTax(monthSalary + firstMonth) - getMonthTax(monthSalary);

		// ��һ���·���
		double oneMonth = bestCutOffFromYearCash;
		if (getMonthTax(monthSalary + oneMonth)+getMonthTax(monthSalary) <= getMonthTax(monthSalary + firstMonth)*2) {
			firstMonth = bestCutOffFromYearCash;
			secondMonth = 0.0;
		}
		// �������ս�˰Ϊ
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

	// �����¼�˰���ʶ��ȡӦ��˰��
	public static double getMonthTax(double salary) {
		// �¹���Ӧ��˰���ö�
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

	// �������ս��͵��¹��ʣ���ȡ���ս�Ӧ�ý�˰��
	public static double getYearTax(double yearCash, double curMonthSalary) {
		// ��ȡӦ��˰���ö�
		double shouldPayTaxOfYearCash = yearCash;
		if (curMonthSalary < 3500) {
			shouldPayTaxOfYearCash = (yearCash - (3500 - curMonthSalary));
		}

		// ��ȡÿ��ƽ�����䵽�Ķ��
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

	// ��ȡ�ﵽ�ȼ��߽�����Ҫ������ֵ
	public static List<Double> getReduceFromYear(double yearCash, double monthSalary, Map<Double, Double> yearLevel) {
		List<Double> reduceFromYearCash = new ArrayList<Double>();
		// ��ȡ���ս�˰��
		double YearTaxRat = getYearTaxRat(yearCash, monthSalary);
		for (int i = 0; i < TAX_RAT.length; i++) {
			if (TAX_RAT[i] >= YearTaxRat)
				break;
			// �߽��ֵΪ
			double nextLevel = yearLevel.get(TAX_RAT[i]);
			// ��Ҫ�����ȥ�����ս�Ϊ
			double cutOffFromYearCash = yearCash - nextLevel;

			reduceFromYearCash.add(cutOffFromYearCash);

		}

		// ��ȡ�¹���˰��
		double monthTaxRat = getMonthTaxRat(monthSalary);
		for (int i = TAX_RAT.length; i > 0; i--) {
			if (TAX_RAT[i - 1] < monthTaxRat)
				break;

			// �ȼ��ı߽�ֵ
			double nextLevel = MONTH_SALARY_LEVEL[i - 1];
			// ���Է���Ķ��Ϊ
			double couldAdd = nextLevel - monthSalary;
			// ��Ҫ�����ȥ�����ս�Ϊ
			double cutOffFromYearCash = couldAdd * 2;
			reduceFromYearCash.add(cutOffFromYearCash);
		}

		return reduceFromYearCash;
	}

	// �������ս����¹��ʣ���ȡ���ս��ĵȼ�
	private static Map<Double, Double> getYearLevel(double yearCash, double monthSalary) {
		Map<Double, Double> rtnMap = new HashMap<Double, Double>();
		// ���ս��в���Ҫ��˰�Ľ��
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

	// �������ս���ȡ˰��
	private static double getYearTaxRat(double yearCash, double monthSalary) {

		// ��ȡӦ��˰���ö�
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

	// �����¹��ʻ�ȡ˰��
	private static double getMonthTaxRat(double monthSalary) {
		// Ӧ��˰���ö�
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
