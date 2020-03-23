package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.ChangeMethodTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.PaymentMethod;
import org.fullstacklearning.payrollimplementation.HoldMethod;

public class ChangeHoldTransaction extends ChangeMethodTransaction {

	public ChangeHoldTransaction(int empId, PayrollDatabase database) {
		super(empId, database);
	}

	@Override
	protected PaymentMethod getMethod() {
		return new HoldMethod();
	}

}

