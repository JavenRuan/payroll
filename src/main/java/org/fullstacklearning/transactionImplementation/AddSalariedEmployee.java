package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.AddEmployeeTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.PaymentClassification;
import org.fullstacklearning.payrolldomain.PaymentSchedule;
import org.fullstacklearning.payrollimplementation.MonthlySchedule;
import org.fullstacklearning.payrollimplementation.SalariedClassification;

public class AddSalariedEmployee extends AddEmployeeTransaction {
	private final double salary;

	public AddSalariedEmployee(int id, String name, String address,
			double salary, PayrollDatabase database) {
		super(id, name, address, database);
		this.salary = salary;
	}

	protected PaymentClassification MakeClassification() {
		return new SalariedClassification(salary);
	}

	protected PaymentSchedule MakeSchedule() {
		return new MonthlySchedule();
	}

}
