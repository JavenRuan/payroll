package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.ChangeEmployeeTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {
	private final String newName;

	public ChangeNameTransaction(int id, String newName,
			PayrollDatabase database) {
		super(id, database);
		this.newName = newName;
	}

	protected void Change(Employee e) {
		e.setName(newName);
	}

}
