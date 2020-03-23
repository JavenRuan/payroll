package org.fullstacklearning.payrolldatabase;

import java.util.ArrayList;
import java.util.List;

import org.fullstacklearning.payrolldomain.Employee;

public interface PayrollDatabase {
	void AddEmployee(Employee employee);

	Employee GetEmployee(int id);

	void DeleteEmployee(int id);

	void AddUnionMember(int id, Employee e);

	Employee GetUnionMember(int id);

	void RemoveUnionMember(int memberId);

	ArrayList<Integer> GetAllEmployeeIds();

	List<Employee> GetAllEmployees();

}