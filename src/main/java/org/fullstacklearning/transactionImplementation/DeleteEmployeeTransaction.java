package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.transactionapplication.Transaction;

public class DeleteEmployeeTransaction extends Transaction {
	private final int id;

	public DeleteEmployeeTransaction(int id, PayrollDatabase database) {
		super(database);
		this.id = id;
	}

	public void execute() {
		database.DeleteEmployee(id);
	}
}