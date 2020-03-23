package org.fullstacklearning.abstracttransactions;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;
import org.fullstacklearning.payrolldomain.PaymentClassification;
import org.fullstacklearning.payrolldomain.PaymentSchedule;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {
	public ChangeClassificationTransaction(int id, PayrollDatabase database) {
		super(id, database);
	}

	protected void Change(Employee e) {
		e.setClassification(getClassification());
		e.setSchedule(getSchedule());
	}

	protected abstract PaymentClassification getClassification();

	protected abstract PaymentSchedule getSchedule();
}
