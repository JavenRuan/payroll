package org.fullstacklearning.payrollimplementation;

import java.util.Date;
import java.util.Hashtable;

import org.fullstacklearning.payrolldomain.Paycheck;
import org.fullstacklearning.payrolldomain.PaymentClassification;
import org.fullstacklearning.util.DateUtil;

public class HourlyClassification extends PaymentClassification {
	private double hourlyRate;
	private Hashtable<Date, TimeCard> timeCards = new Hashtable<Date, TimeCard>();

	public HourlyClassification(double rate) {
		this.hourlyRate = rate;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	public TimeCard GetTimeCard(Date date) {
		return timeCards.get(date);
	}

	public void AddTimeCard(TimeCard card) {
		timeCards.put(card.getDate(), card);
	}

	public double calculatePay(Paycheck paycheck) {
		double totalPay = 0.0;
		for (TimeCard timeCard : timeCards.values()) {
			if (DateUtil.IsInPayPeriod(timeCard.getDate(),
					paycheck.getPayPeriodStartDate(),
					paycheck.getPayPeriodEndDate()))
				totalPay += CalculatePayForTimeCard(timeCard);
		}
		return totalPay;
	}

	private double CalculatePayForTimeCard(TimeCard card) {
		double overtimeHours = Math.max(0.0, card.getHours() - 8);
		double normalHours = card.getHours() - overtimeHours;
		return hourlyRate * normalHours + hourlyRate * 1.5 * overtimeHours;
	}

	public String toString() {
		return String.format("%f/hr", hourlyRate);
	}
}

