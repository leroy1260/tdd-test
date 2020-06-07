package com.tddexample.service;

import com.tddexample.dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private EmployeeService service = null;
    private List<Integer> empIds = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
    private List<EmployeeDepartmentInfo> empDptInfo = Collections.EMPTY_LIST;
    private List<Employee> employeeList = Collections.EMPTY_LIST;

    @BeforeEach
    void setUp() {
        service = new EmployeeService();
    }

    @AfterEach
    void tearDown() {
        service = null;
        employeeList.clear();
    }

    @Test
    void createEmployees() {
        List<Employee> employees = service.createEmployees(10, 5, 15);

        assertEquals(10, employees.size());
        assertFalse(employees.stream().allMatch(e -> e.getAge() < 5));
        assertFalse(employees.stream().allMatch(e -> e.getAge() > 15));
    }

    @Test
    void getEmployeeIds() {
        List<Employee> employees = service.createEmployees(10, 5, 15);
        List<Integer> expectedEmpIds = service.getEmployeeIds();

        assertEquals(10, expectedEmpIds.size());
        expectedEmpIds.forEach(id -> assertTrue(empIds.contains(id)));
    }

    @Test
    void createEmployeesFinanceInfo() {
        List<EmployeeFinanceInfo> employees = service.createEmployeesFinanceInfo(empIds, 1000, 10000);

        assertFalse(employees.stream().allMatch(e -> e.getSalary() < 1000));
        assertFalse(employees.stream().allMatch(e -> e.getSalary() > 10000));
    }

    @Test
    void filterEmpByAgeGt() {
        service.createEmployees(10, 5, 15);
        List<Integer> expectedIds = service.filterEmpByAgeGt(10);

        assertNotNull(expectedIds);

        List<Integer> foundIds = service.filterEmpByAgeGt(15);
        assertEquals(0, foundIds.size());
    }

    @Test
    void filterEmpByCTCGt() {
        List<EmployeeFinanceInfo> employees = service.createEmployeesFinanceInfo(empIds, 1000, 10000);

        List<Integer> expectedEmpIds = service.filterEmpByCTCGt(9000, empIds);

        assertNotNull(expectedEmpIds);

        List<Integer> ids = service.filterEmpByCTCGt(10000, empIds);

        assertEquals(0, ids.size());
    }

    @Test
    void filterEmpByGratuityLt() {
        List<EmployeeFinanceInfo> employees = service.createEmployeesFinanceInfo(empIds, 1000, 10000);

        List<Integer> expectedEmpIds = service.filterEmpByGratuityLt(500, empIds);

        assertNotNull(expectedEmpIds);

        List<Integer> ids = service.filterEmpByGratuityLt(10, empIds);

        assertEquals(0, ids.size());
    }

    @Test
    void getDepartmentWithMaxEmpFrom() {
        this.empDptInfo = generateEmployeeDeptInfo();
        Integer departmentId = service.getDepartmentWithMaxEmpFrom(empIds, empDptInfo);
        assertEquals(5, departmentId);
        assertNotEquals(1, departmentId);
    }

    @Test
    void filterEmpByIds() {
        service.createEmployees(10, 5, 15);
        List<Integer> testIds = service.getEmployeeIds();

        List<Employee> employeeList = service.filterEmpByIds(Arrays.asList(testIds.get(0)));

        assertEquals(1, employeeList.size());
    }

    private List<EmployeeDepartmentInfo> generateEmployeeDeptInfo() {
        empDptInfo = new ArrayList<>();
        empDptInfo.add(new EmployeeDepartmentInfo(5,3));
        empDptInfo.add(new EmployeeDepartmentInfo(7,1));
        empDptInfo.add(new EmployeeDepartmentInfo(2,5));
        empDptInfo.add(new EmployeeDepartmentInfo(1,4));
        empDptInfo.add(new EmployeeDepartmentInfo(8,1));
        empDptInfo.add(new EmployeeDepartmentInfo(10,2));
        empDptInfo.add(new EmployeeDepartmentInfo(4,5));
        empDptInfo.add(new EmployeeDepartmentInfo(3,3));
        empDptInfo.add(new EmployeeDepartmentInfo(6,4));
        empDptInfo.add(new EmployeeDepartmentInfo(9,5));
        return empDptInfo;
    }
}