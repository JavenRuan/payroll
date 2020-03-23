package org.fullstacklearning.payrollimplementation;

import org.fullstacklearning.payrolldomain.Paycheck;
import org.fullstacklearning.payrolldomain.PaymentClassification;

public class SalariedClassification extends PaymentClassification {
	private final double salary;

	public SalariedClassification(double salary) {
		this.salary = salary;
	}

	public double calculatePay(Paycheck paycheck) {
		return salary;
	}

	public double getSalary() {
		return salary;
	}

	public String toString() {
		return String.format("%f", salary);
	}
}
