package org.fullstacklearning.transactionImplementation;

import java.util.Date;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;
import org.fullstacklearning.payrollimplementation.HourlyClassification;
import org.fullstacklearning.payrollimplementation.TimeCard;
import org.fullstacklearning.transactionapplication.Transaction;

public class TimeCardTransaction extends Transaction {
	private final Date date;
	private final double hours;
	private final int empId;

	public TimeCardTransaction(Date date, double hours, int empId, PayrollDatabase database) {
		super(database);
		this.date = date;
		this.hours = hours;
		this.empId = empId;
	}

	public void execute() throws Exception {
		Employee e = database.GetEmployee(empId);
		if (e != null) {
			HourlyClassification hc = (HourlyClassification) (e.getClassification());
			if (hc != null)
				hc.AddTimeCard(new TimeCard(date, hours));
			else
				throw new Exception("Tried to add timecard to" + "non-hourly employee");
		} else
			throw new Exception("No such employee.");
	}

}