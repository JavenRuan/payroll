package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.ChangeEmployeeTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;

public class ChangeAddressTransaction extends ChangeEmployeeTransaction {
	private final String newAddress;

	public ChangeAddressTransaction(int id, String newAddress,
			PayrollDatabase database) {
		super(id, database);
		this.newAddress = newAddress;
	}

	protected void Change(Employee e) {
		e.setAddress(newAddress);
	}
}
