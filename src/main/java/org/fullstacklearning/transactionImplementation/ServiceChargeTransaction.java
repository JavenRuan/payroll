package org.fullstacklearning.transactionImplementation;

import java.util.Date;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;
import org.fullstacklearning.payrollimplementation.ServiceCharge;
import org.fullstacklearning.payrollimplementation.UnionAffiliation;
import org.fullstacklearning.transactionapplication.Transaction;

public class ServiceChargeTransaction extends Transaction {

	private final int memberId;
	private final Date time;
	private final double charge;

	public ServiceChargeTransaction(int id, Date time, double charge,
			PayrollDatabase database) {
		super(database);
		this.memberId = id;
		this.time = time;
		this.charge = charge;
	}

	public void execute() throws Exception {
		Employee e = database.GetUnionMember(memberId);
		if (e != null) {
			UnionAffiliation ua = null;
			if (e.getAffiliation() instanceof UnionAffiliation)
				ua = (UnionAffiliation) e.getAffiliation();
			if (ua != null)
				ua.AddServiceCharge(new ServiceCharge(time, charge));
			else
				throw new Exception("Tries to add service charge to union"
						+ "member without a union affiliation");
		} else
			throw new Exception("No such union member.");
	}
}
