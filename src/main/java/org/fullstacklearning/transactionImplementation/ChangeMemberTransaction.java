package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.ChangeAffiliationTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Affiliation;
import org.fullstacklearning.payrolldomain.Employee;
import org.fullstacklearning.payrollimplementation.UnionAffiliation;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction {
	private final int memberId;
	private final double dues;

	public ChangeMemberTransaction(int empId, int memberId, double dues,
			PayrollDatabase database) {
		super(empId, database);
		this.memberId = memberId;
		this.dues = dues;
	}

	@Override
	protected Affiliation getAffiliation() {
		return new UnionAffiliation(memberId, dues);
	}
	
	protected void RecordMembership(Employee e) {
		database.AddUnionMember(memberId, e);
	}

	
}
