package org.fullstacklearning.transactionImplementation;

import org.fullstacklearning.abstracttransactions.ChangeAffiliationTransaction;
import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Affiliation;
import org.fullstacklearning.payrolldomain.Employee;
import org.fullstacklearning.payrolldomain.NoAffiliation;
import org.fullstacklearning.payrollimplementation.UnionAffiliation;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {
	public ChangeUnaffiliatedTransaction(int empId, PayrollDatabase database) {
		super(empId, database);
	}

	@Override
	protected Affiliation getAffiliation() {
		return new NoAffiliation();
	}

	protected void RecordMembership(Employee e) {
		Affiliation affiliation = e.getAffiliation();
		if (affiliation instanceof UnionAffiliation) {
			UnionAffiliation unionAffiliation = (UnionAffiliation) affiliation;
			int memberId = unionAffiliation.getMemberId();
			database.RemoveUnionMember(memberId);
		}
	}

}