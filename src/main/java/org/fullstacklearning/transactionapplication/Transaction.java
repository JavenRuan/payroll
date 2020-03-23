package org.fullstacklearning.transactionapplication;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;

public abstract class Transaction {

	protected final PayrollDatabase database;

	public Transaction(PayrollDatabase database) {
		this.database = database;
	}

	public abstract void execute() throws Exception;
}
