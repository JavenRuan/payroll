package org.fullstacklearning.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldatabaseImplementation.InMemoryPayrollDatabase;
import org.fullstacklearning.payrolldomain.*;
import org.fullstacklearning.payrollimplementation.*;
import org.fullstacklearning.transactionImplementation.*;
import org.fullstacklearning.util.DateUtil;
import org.junit.Before;
import org.junit.Test;

public class PayrollTest {
	private PayrollDatabase database;

	@Before
	public void before() {
		database = new InMemoryPayrollDatabase();
	}

	@Test
	public void testAddSalariedEmployee() {
		int empId = 1;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
				1000.00, database);
		t.execute();

		Employee e = database.GetEmployee(empId);
		assertEquals("Bob", e.getName());

		PaymentClassification pc = e.getClassification();
		assertTrue(pc instanceof SalariedClassification);
		SalariedClassification sc = (SalariedClassification) pc;

		assertEquals(1000.00, sc.getSalary(), .001);
		PaymentSchedule ps = e.getSchedule();
		assertTrue(ps instanceof MonthlySchedule);

		PaymentMethod pm = e.getMethod();
		assertTrue(pm instanceof HoldMethod);
	}

	@Test
	public void TestAddHourlyEmployee() {
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Micah", "Home",
				200.00, database);
		t.execute();

		Employee e = database.GetEmployee(empId);
		assertEquals("Micah", e.getName());

		PaymentClassification pc = e.getClassification();
		assertTrue(pc instanceof HourlyClassification);
		HourlyClassification hc = (HourlyClassification) pc;

		assertEquals(200.00, hc.getHourlyRate(), .001);
		PaymentSchedule ps = e.getSchedule();
		assertTrue(ps instanceof WeeklySchedule);

		PaymentMethod pm = e.getMethod();
		assertTrue(pm instanceof HoldMethod);
	}

	@Test
	public void TestAddCommissionedEmployee() {
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId,
				"Justin", "Home", 2500, 9.5, database);
		t.execute();

		Employee e = database.GetEmployee(empId);
		assertEquals("Justin", e.getName());

		PaymentClassification pc = e.getClassification();
		assertTrue(pc instanceof CommissionedClassification);
		CommissionedClassification cc = (CommissionedClassification) pc;

		assertEquals(2500, cc.getBaseRate(), .001);
		assertEquals(9.5, cc.getCommissionRate(), .001);
		PaymentSchedule ps = e.getSchedule();
		assertTrue(ps instanceof BiWeeklySchedule);

		PaymentMethod pm = e.getMethod();
		assertTrue(pm instanceof HoldMethod);
	}

	@Test
	public void DeleteEmplyee() {
		int empId = 4;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
				"Home", 2500, 3.2, database);
		t.execute();

		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		DeleteEmployeeTransaction dt = new DeleteEmployeeTransaction(empId,
				database);
		dt.execute();

		e = database.GetEmployee(empId);
		assertNull(e);
	}

	@Test
	public void TestTimeCardTransaction() throws Exception {
		int empId = 5;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		TimeCardTransaction tct = new TimeCardTransaction(
				DateUtil.getDate(2005, 7, 31), 8.0, empId, database);
		tct.execute();

		Employee e = database.GetEmployee(empId);
		assertNotNull(e);

		PaymentClassification pc = e.getClassification();
		assertTrue(pc instanceof HourlyClassification);
		HourlyClassification hc = (HourlyClassification) pc;

		TimeCard tc = hc.GetTimeCard(DateUtil.getDate(2005, 7, 31));
		assertNotNull(tc);
		assertEquals(8.0, tc.getHours(), .0);
	}

	@Test
	public void TestSalesReceiptTransaction() throws Exception {
		int empId = 5;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
				"Home", 2000, 15.25, database);
		t.execute();
		SalesReceiptTransaction tct = new SalesReceiptTransaction(DateUtil.getDate(
				2005, 7, 31), 250.00, empId, database);
		tct.execute();

		Employee e = database.GetEmployee(empId);
		assertNotNull(e);

		PaymentClassification pc = e.getClassification();
		assertTrue(pc instanceof CommissionedClassification);
		CommissionedClassification cc = (CommissionedClassification) pc;

		SalesReceipt sr = cc.GetSalesReceipt(DateUtil.getDate(2005, 7, 31));
		assertNotNull(sr);
		assertEquals(250.00, sr.getSaleAmount(), .001);
	}

	@Test
	public void AddServiceCharge() throws Exception {
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		UnionAffiliation af = new UnionAffiliation();
		e.setAffiliation(af);
		int memberId = 86; // Maxwell Smart
		database.AddUnionMember(memberId, e);
		ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId,
				DateUtil.getDate(2005, 8, 8), 12.95, database);
		sct.execute();
		ServiceCharge sc = af.GetServiceCharge(DateUtil.getDate(2005, 8, 8));
		assertNotNull(sc);
		assertEquals(12.95, sc.getAmount(), .001);
	}

	@Test
	public void TestChangeNameTransaction() throws Exception {
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		ChangeNameTransaction cnt = new ChangeNameTransaction(empId, "Bob",
				database);
		cnt.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		assertEquals("Bob", e.getName());
	}

	@Test
	public void TestChangeHourlyTransaction() throws Exception {
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance",
				"Home", 2500, 3.2, database);
		t.execute();
		ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empId, 27.52,
				database);
		cht.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		PaymentClassification pc = e.getClassification();
		assertNotNull(pc);
		assertTrue(pc instanceof HourlyClassification);
		HourlyClassification hc = (HourlyClassification) pc;
		assertEquals(27.52, hc.getHourlyRate(), .001);
		PaymentSchedule ps = e.getSchedule();
		assertTrue(ps instanceof WeeklySchedule);
	}

	@Test
	public void TestChangeSalaryTransaction() throws Exception {
		int empId = 4;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance",
				"Home", 2500, 3.2, database);
		t.execute();
		ChangeSalariedTransaction cst = new ChangeSalariedTransaction(empId,
				3000.00, database);
		cst.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		PaymentClassification pc = e.getClassification();
		assertNotNull(pc);
		assertTrue(pc instanceof SalariedClassification);
		SalariedClassification sc = (SalariedClassification) pc;
		assertEquals(3000.00, sc.getSalary(), .001);
		PaymentSchedule ps = e.getSchedule();
		assertTrue(ps instanceof MonthlySchedule);
	}

	@Test
	public void TestChangeCommisionTransaction() throws Exception {
		int empId = 5;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
				2500.00, database);
		t.execute();
		ChangeCommissionedTransaction cht = new ChangeCommissionedTransaction(
				empId, 1250.00, 5.6, database);
		cht.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		PaymentClassification pc = e.getClassification();
		assertNotNull(pc);
		assertTrue(pc instanceof CommissionedClassification);
		CommissionedClassification cc = (CommissionedClassification) pc;
		assertEquals(1250.00, cc.getBaseRate(), .001);
		assertEquals(5.6, cc.getCommissionRate(), .001);
		PaymentSchedule ps = e.getSchedule();
		assertTrue(ps instanceof BiWeeklySchedule);
	}

	@Test
	public void ChangeDirectMethod() throws Exception {
		int empId = 6;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Mike", "Home",
				3500.00, database);
		t.execute();
		ChangeDirectTransaction cddt = new ChangeDirectTransaction(empId,
				"bank1", "123", database);
		cddt.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		PaymentMethod method = e.getMethod();
		assertNotNull(method);
		assertTrue(method instanceof DirectDepositMethod);
	}

	@Test
	public void ChangeHoldMethod() throws Exception {
		int empId = 7;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Mike", "Home",
				3500.00, database);
		t.execute();
		new ChangeDirectTransaction(empId, "bank1", "123", database).execute();
		ChangeHoldTransaction cht = new ChangeHoldTransaction(empId, database);
		cht.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		PaymentMethod method = e.getMethod();
		assertNotNull(method);
		assertTrue(method instanceof HoldMethod);
	}

	@Test
	public void ChangeMailMethod() throws Exception {
		int empId = 8;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Mike", "Home",
				3500.00, database);
		t.execute();
		ChangeMailTransaction cmt = new ChangeMailTransaction(empId,
				"3.14 Pi St", database);
		cmt.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		PaymentMethod method = e.getMethod();
		assertNotNull(method);
		assertTrue(method instanceof MailMethod);
	}

	@Test
	public void ChangeUnionMember() throws Exception {
		int empId = 9;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		int memberId = 7743;
		ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId,
				memberId, 99.42, database);
		cmt.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		Affiliation affiliation = e.getAffiliation();
		assertNotNull(affiliation);
		assertTrue(affiliation instanceof UnionAffiliation);
		UnionAffiliation uf = (UnionAffiliation) affiliation;
		assertEquals(99.42, uf.getDues(), .001);
		Employee member = database.GetUnionMember(memberId);
		assertNotNull(member);
		assertEquals(e, member);
	}

	@Test
	public void ChangeUnaffiliatedMember() throws Exception {
		int empId = 10;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		int memberId = 7743;
		new ChangeMemberTransaction(empId, memberId, 99.42, database).execute();
		ChangeUnaffiliatedTransaction cut = new ChangeUnaffiliatedTransaction(
				empId, database);
		cut.execute();
		Employee e = database.GetEmployee(empId);
		assertNotNull(e);
		Affiliation affiliation = e.getAffiliation();
		assertNotNull(affiliation);
		assertTrue(affiliation instanceof NoAffiliation);
		Employee member = database.GetUnionMember(memberId);
		assertNull(member);
	}

	@Test
	public void PaySingleSalariedEmployee() {
		int empId = 1;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
				1000.00, database);
		t.execute();
		//Date payDate = DateUtil.getDate(2001, 11, 30);
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, 2001);
		ca.set(Calendar.MONTH, 11-1);
		ca.set(Calendar.DAY_OF_MONTH, 30);
		Date payDate = ca.getTime();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		Paycheck pc = pt.GetPaycheck(empId);
		System.out.println(pc);
		assertNotNull(pc);
		assertEquals(payDate, pc.getPayDate());
		assertEquals(1000.00, pc.getGrossPay(), .001);
		assertEquals("Hold", pc.getField("Disposition"));
		assertEquals(0.0, pc.getDeductions(), .001);
		assertEquals(1000.00, pc.getNetPay(), .001);
	}

	@Test
	public void PaySingleSalariedEmployeeOnWrongDate() {
		int empId = 1;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
				1000.00, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11, 29);
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		Paycheck pc = pt.GetPaycheck(empId);
		assertNull(pc);
	}

	@Test
	public void PayingSingleHourlyEmployeeNoTimeCards() {
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		//Date payDate = DateUtil.getDate(2020, 9-1, 25); // Friday
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, 2020);
		ca.set(Calendar.MONTH, 9-1);
		ca.set(Calendar.DAY_OF_MONTH, 25);
		Date payDate = ca.getTime();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		ValidatePaycheck(pt, empId, payDate, 0.0);
	}

	private void ValidatePaycheck(PaydayTransaction pt, int empid,
			Date payDate, double pay) {
		Paycheck pc = pt.GetPaycheck(empid);
		assertNotNull(pc);
		assertEquals(payDate, pc.getPayDate());
		assertEquals(pay, pc.getGrossPay(), .001);
		assertEquals("Hold", pc.getField("Disposition"));
		assertEquals(0.0, pc.getDeductions(), .001);
		assertEquals(pay, pc.getNetPay(), .001);
	}

	@Test
	public void PaySingleHourlyEmployeeOneTimeCard() throws Exception {
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11, 9); // Friday

		TimeCardTransaction tc = new TimeCardTransaction(payDate, 2.0, empId,
				database);
		tc.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		ValidatePaycheck(pt, empId, payDate, 30.5);
	}

	@Test
	public void PaySingleHourlyEmployeeOvertimeOneTimeCard() throws Exception {
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11, 9); // Friday

		TimeCardTransaction tc = new TimeCardTransaction(payDate, 9.0, empId,
				database);
		tc.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		ValidatePaycheck(pt, empId, payDate, (8 + 1.5) * 15.25);
	}

	@Test
	public void PaySingleHourlyEmployeeOnWrongDate() throws Exception {
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11, 8); // Thursday

		TimeCardTransaction tc = new TimeCardTransaction(payDate, 9.0, empId,
				database);
		tc.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();

		Paycheck pc = pt.GetPaycheck(empId);
		assertNull(pc);
	}

	@Test
	public void PaySingleHourlyEmployeeTwoTimeCards() throws Exception {
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11, 9); // Friday

		TimeCardTransaction tc = new TimeCardTransaction(payDate, 2.0, empId,
				database);
		tc.execute();
		Calendar cal = Calendar.getInstance();
		cal.setTime(payDate);
		cal.add(Calendar.DATE, -1);
		TimeCardTransaction tc2 = new TimeCardTransaction(cal.getTime(), 5.0,
				empId, database);
		tc2.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		ValidatePaycheck(pt, empId, payDate, 7 * 15.25);
	}

	@Test
	public void TestPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods()
			throws Exception {
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.25, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11, 9); // Friday
		Date dateInPreviousPayPeriod = DateUtil.getDate(2001, 10, 30);

		TimeCardTransaction tc = new TimeCardTransaction(payDate, 2.0, empId,
				database);
		tc.execute();
		TimeCardTransaction tc2 = new TimeCardTransaction(
				dateInPreviousPayPeriod, 5.0, empId, database);
		tc2.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		ValidatePaycheck(pt, empId, payDate, 2 * 15.25);
	}

	@Test
	public void PayingSingleCommissionedEmployeeNoReceipts() {
		int empId = 2;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
				"Home", 1500, 10, database);
		t.execute();
		//Date payDate = DateUtil.getDate(2001, 11, 16); // Payday
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, 2001);
		ca.set(Calendar.MONTH, 11-1);
		ca.set(Calendar.DAY_OF_MONTH, 16);
		Date payDate = ca.getTime();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		ValidatePaycheck(pt, empId, payDate, 1500.0);
	}

	@Test
	public void PaySingleCommissionedEmployeeOneReceipt() throws Exception {
		int empId = 2;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
				"Home", 1500, 10, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11, 16); // Payday

		SalesReceiptTransaction sr = new SalesReceiptTransaction(payDate,
				5000.00, empId, database);
		sr.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		ValidatePaycheck(pt, empId, payDate, 2000.00);
	}

	@Test
	public void PaySingleCommissionedEmployeeOnWrongDate() throws Exception {
		int empId = 2;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
				"Home", 1500, 10, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11-1, 9); // wrong friday

		SalesReceiptTransaction sr = new SalesReceiptTransaction(payDate,
				5000.00, empId, database);
		sr.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();

		Paycheck pc = pt.GetPaycheck(empId);
		assertNull(pc);
	}

	@Test
	public void PaySingleCommissionedEmployeeTwoReceipts() throws Exception {
		int empId = 2;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
				"Home", 1500, 10, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11, 16); // Payday

		SalesReceiptTransaction sr = new SalesReceiptTransaction(payDate,
				5000.00, empId, database);
		sr.execute();

		Calendar cal = Calendar.getInstance();
		cal.setTime(payDate);
		cal.add(Calendar.DATE, -1);
		SalesReceiptTransaction sr2 = new SalesReceiptTransaction(
				cal.getTime(), 3500.00, empId, database);
		sr2.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		ValidatePaycheck(pt, empId, payDate, 2350.00);
	}

	@Test
	public void TestPaySingleCommissionedEmployeeWithReceiptsSpanningTwoPayPeriods()
			throws Exception {
		int empId = 2;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill",
				"Home", 1500, 10, database);
		t.execute();
		Date payDate = DateUtil.getDate(2001, 11, 16); // Payday

		SalesReceiptTransaction sr = new SalesReceiptTransaction(payDate,
				5000.00, empId, database);
		sr.execute();
		Calendar cal = Calendar.getInstance();
		cal.setTime(payDate);
		cal.add(Calendar.DATE, -15);
		SalesReceiptTransaction sr2 = new SalesReceiptTransaction(
				cal.getTime(), 3500.00, empId, database);
		sr2.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		ValidatePaycheck(pt, empId, payDate, 2000.00);
	}

	@Test
	public void SalariedUnionMemberDues() throws Exception {
		int empId = 1;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
				1000.00, database);
		t.execute();
		int memberId = 7734;
		ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId,
				memberId, 9.42, database);
		cmt.execute();
		Date payDate = DateUtil.getDate(2001, 11, 30);
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		Paycheck pc = pt.GetPaycheck(empId);
		assertNotNull(pc);
		assertEquals(payDate, pc.getPayDate());
		assertEquals(1000.0, pc.getGrossPay(), .001);
		assertEquals("Hold", pc.getField("Disposition"));
		assertEquals(47.1, pc.getDeductions(), .001);
		assertEquals(1000.0 - 47.1, pc.getNetPay(), .001);
	}

	@Test
	public void HourlyUnionMemberServiceCharge() throws Exception {
		int empId = 1;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.24, database);
		t.execute();
		int memberId = 7734;
		ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId,
				memberId, 9.42, database);
		cmt.execute();
		Date payDate = DateUtil.getDate(2001, 11, 9);
		ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId,
				payDate, 19.42, database);
		sct.execute();
		TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId,
				database);
		tct.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		Paycheck pc = pt.GetPaycheck(empId);
		assertNotNull(pc);
		assertEquals(payDate, pc.getPayPeriodEndDate());
		assertEquals(8 * 15.24, pc.getGrossPay(), .001);
		assertEquals("Hold", pc.getField("Disposition"));
		assertEquals(9.42 + 19.42, pc.getDeductions(), .001);
		assertEquals((8 * 15.24) - (9.42 + 19.42), pc.getNetPay(), .001);
	}

	@Test
	public void ServiceChargesSpanningMultiplePayPeriods() throws Exception {
		int empId = 1;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
				15.24, database);
		t.execute();
		int memberId = 7734;
		ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId,
				memberId, 9.42, database);
		cmt.execute();
		Date payDate = DateUtil.getDate(2001, 11, 9);
		Date earlyDate = DateUtil.getDate(2001, 11, 2); // previous Friday
		Date lateDate = DateUtil.getDate(2001, 11, 16); // next Friday
		ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId,
				payDate, 19.42, database);
		sct.execute();
		ServiceChargeTransaction sctEarly = new ServiceChargeTransaction(
				memberId, earlyDate, 100.00, database);
		sctEarly.execute();
		ServiceChargeTransaction sctLate = new ServiceChargeTransaction(
				memberId, lateDate, 200.00, database);
		sctLate.execute();
		TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId,
				database);
		tct.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate, database);
		pt.execute();
		Paycheck pc = pt.GetPaycheck(empId);
		assertNotNull(pc);
		assertEquals(payDate, pc.getPayPeriodEndDate());
		assertEquals(8 * 15.24, pc.getGrossPay(), .001);
		assertEquals("Hold", pc.getField("Disposition"));
		assertEquals(9.42 + 19.42, pc.getDeductions(), .001);
		assertEquals((8 * 15.24) - (9.42 + 19.42), pc.getNetPay(), .001);
	}
	
}