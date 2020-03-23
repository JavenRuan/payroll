package org.fullstacklearning.abstracttransactions;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;
import org.fullstacklearning.payrolldomain.PaymentClassification;
import org.fullstacklearning.payrolldomain.PaymentMethod;
import org.fullstacklearning.payrolldomain.PaymentSchedule;
import org.fullstacklearning.payrollimplementation.HoldMethod;
import org.fullstacklearning.transactionapplication.Transaction;

public abstract class AddEmployeeTransaction extends Transaction {
	private final int empid;
	private final String name;
	private final String address;

	public AddEmployeeTransaction(int empid, String name, String address,
			PayrollDatabase database) {
		super(database);
		this.empid = empid;
		this.name = name;
		this.address = address;
	}

	protected abstract PaymentClassification MakeClassification();

	protected abstract PaymentSchedule MakeSchedule();

	@Override
	public void execute() {
		PaymentClassification pc = MakeClassification();
		PaymentSchedule ps = MakeSchedule();
		PaymentMethod pm = new HoldMethod();
		Employee e = new Employee(empid, name, address);
		e.setClassification(pc);
		e.setSchedule(ps);
		e.setMethod(pm);
		database.AddEmployee(e);
	}

	public String toString() {
		return String.format("%s  id:%d   name:%s   address:%s", getClass()
				.getName(), empid, name, address);
	}
}