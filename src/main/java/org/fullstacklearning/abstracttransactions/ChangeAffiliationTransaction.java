package org.fullstacklearning.abstracttransactions;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Affiliation;
import org.fullstacklearning.payrolldomain.Employee;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {
	public ChangeAffiliationTransaction(int empId, PayrollDatabase database) {
		super(empId, database);
	}

	protected void Change(Employee e) {
		RecordMembership(e);
		Affiliation affiliation = getAffiliation();
		e.setAffiliation(affiliation);
	}

	protected abstract Affiliation getAffiliation();

	protected abstract void RecordMembership(Employee e);
}
