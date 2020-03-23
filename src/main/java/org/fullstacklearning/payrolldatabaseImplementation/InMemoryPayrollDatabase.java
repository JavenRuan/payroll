package org.fullstacklearning.payrolldatabaseImplementation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.fullstacklearning.payrolldatabase.PayrollDatabase;
import org.fullstacklearning.payrolldomain.Employee;

public class InMemoryPayrollDatabase implements PayrollDatabase {
	private static Hashtable<Integer, Employee> employees = new Hashtable<Integer, Employee>();
	private static Hashtable<Integer, Employee> unionMembers = new Hashtable<Integer, Employee>();

	public void AddEmployee(Employee employee) {
		employees.put(employee.getEmpid(), employee);
	}

	// etc...
	public Employee GetEmployee(int id) {
		return employees.get(id);
	}

	public void DeleteEmployee(int id) {
		employees.remove(id);
	}

	public void AddUnionMember(int id, Employee e) {
		unionMembers.put(id, e);
	}

	public Employee GetUnionMember(int id) {
		return unionMembers.get(id);
	}

	public void RemoveUnionMember(int memberId) {
		unionMembers.remove(memberId);
	}

	public ArrayList<Integer> GetAllEmployeeIds() {
		return new ArrayList<Integer>(employees.keySet());
	}

	public List<Employee> GetAllEmployees() {
		return new ArrayList<Employee>(employees.values());
	}

	public void clear() {
		employees.clear();
		unionMembers.clear();
	}
}
