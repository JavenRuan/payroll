package org.fullstacklearning.transactionImplementation;

import java.util.Date;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;
import org.fullstacklearning.payrollimplementation.CommissionedClassification;
import org.fullstacklearning.payrollimplementation.SalesReceipt;
import org.fullstacklearning.transactionapplication.Transaction;

public class SalesReceiptTransaction extends Transaction {
	private final Date date;
	private final double saleAmount;
	private final int empId;

	public SalesReceiptTransaction(Date time, double saleAmount, int empId, PayrollDatabase database) {
		super(database);
		this.date = time;
		this.saleAmount = saleAmount;
		this.empId = empId;
	}

	public void execute() throws Exception {
		Employee e = database.GetEmployee(empId);

		if (e != null) {
			CommissionedClassification hc = (CommissionedClassification) e.getClassification();

			if (hc != null)
				hc.AddSalesReceipt(new SalesReceipt(date, saleAmount));
			else
				throw new Exception("Tried to add sales receipt to" + "non-commissioned employee");
		} else
			throw new Exception("No such employee.");

	}
}
