package com.tddexample;

import com.tddexample.dao.*;
import com.tddexample.service.DepartmentService;
import com.tddexample.service.EmployeeService;
import com.tddexample.util.CommonUtils;

import java.util.*;

public class App {
    public static void main(String[] args) {

        EmployeeService empService = new EmployeeService();
        DepartmentService depService = new DepartmentService();

        // Create Departments
        List<String> departments = Arrays.asList("IT","INFRA","HR","ADMIN","FIN");
        List<Department> departmentList = depService.createDepartments(departments);

        // Create Employees
        empService.createEmployees(1000,18, 60);
        List<Integer> employeeIds = empService.getEmployeeIds();

        // Create EmployeeFinanceInfo
        List<EmployeeFinanceInfo> empFinances = empService.createEmployeesFinanceInfo(employeeIds, 10000, 100000);

        // Split employees into 2 parts and divide them across departments
        // Split into 2 halves
        List<Integer>[] employeeParts = CommonUtils.split(employeeIds);
        List<Integer> empPart1Departments = depService.getDepartmentByName(Arrays.asList("IT","FIN"));
        List<Integer> empPart2Departments = depService.getDepartmentByName(Arrays.asList("INFRA","HR","ADMIN"));

        // divide each employee parts across departments
        List<EmployeeDepartmentInfo> employeesWith2Departments = depService.distributeEmployees(employeeParts[0],empPart1Departments);
        List<EmployeeDepartmentInfo> employeesWith3Departments = depService.distributeEmployees(employeeParts[1], empPart2Departments);

        // merge both employee department infos
        List<EmployeeDepartmentInfo> employeeDepartmentInfos = CommonUtils.union(employeesWith2Departments, employeesWith3Departments);

        // Query 1 : Part 1 - Get all emps with age > 40
        List<Integer> empGt40 = empService.filterEmpByAgeGt(40);

        // Query 1 : Part 2 - Get all employees with  ctc > 30000 from emp whose age gt 40
        List<Integer> empCtcGt30000 = empService.filterEmpByCTCGt(30000, empGt40);

        // Query 1 : result
        List<Employee> employeesFound = empService.filterEmpByIds(empCtcGt30000);
        employeesFound.forEach(System.out::println);

        // Query 2 : Part 1 - Get all employees with age > 35
        List<Integer> empGt35 = empService.filterEmpByAgeGt(35);

        // Query 2 : Part 2 - Get all employees with gratuity < 800 and whose age gt 35
        List<Integer> empGratuityLt800 = empService.filterEmpByGratuityLt(30000, empGt35);

        // Query 2 : Part 3 - find dept with max emp with age > 35 & gratuity < 800
        int departmentId = empService.getDepartmentWithMaxEmpFrom(empGratuityLt800, employeeDepartmentInfos);

        // Query 2 : result
        Department department = depService.getDepartmentById(departmentId);
        System.out.println("Department with max emp is " + department.getDepartmentType().toString());
    }
}
