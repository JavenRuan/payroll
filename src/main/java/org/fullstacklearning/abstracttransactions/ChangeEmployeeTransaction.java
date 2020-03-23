package org.fullstacklearning.abstracttransactions;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;
import org.fullstacklearning.transactionapplication.Transaction;

public abstract class ChangeEmployeeTransaction extends Transaction {

	private final int empId;

	public ChangeEmployeeTransaction(int empId, PayrollDatabase database) {
		super(database);
		this.empId = empId;
	}

	public void execute() throws Exception {
		Employee e = database.GetEmployee(empId);
		if (e != null)
			Change(e);
		else
			throw new Exception("No such employee.");
	}

	protected abstract void Change(Employee e);
}
