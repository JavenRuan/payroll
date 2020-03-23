package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.ChangeMethodTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.PaymentMethod;
import org.fullstacklearning.payrollimplementation.MailMethod;

public class ChangeMailTransaction extends ChangeMethodTransaction {
	private final String address;

	public ChangeMailTransaction(int empId, String address,
			PayrollDatabase database) {
		super(empId, database);
		this.address = address;
	}

	@Override
	protected PaymentMethod getMethod() {
		return new MailMethod(address);
	}

}
