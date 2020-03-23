package org.fullstacklearning.abstracttransactions;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;
import org.fullstacklearning.payrolldomain.PaymentMethod;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {

	public ChangeMethodTransaction(int empId, PayrollDatabase database) {
		super(empId, database);
	}

	protected void Change(Employee e) {
		PaymentMethod method = getMethod();
		e.setMethod(method);
	}

	protected abstract PaymentMethod getMethod();

}