package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.ChangeMethodTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.PaymentMethod;
import org.fullstacklearning.payrollimplementation.DirectDepositMethod;

public class ChangeDirectTransaction extends ChangeMethodTransaction {
	private final String bank;
	private final String account;

	public ChangeDirectTransaction(int empId, String bank, String account,
			PayrollDatabase database) {
		super(empId, database);
		this.bank = bank;
		this.account = account;
	}

	@Override
	protected PaymentMethod getMethod() {
		return new DirectDepositMethod(bank, account);
	}
}

