package com.tddexample.service;

import com.tddexample.dao.Department;
import com.tddexample.dao.DepartmentType;
import com.tddexample.dao.EmployeeDepartmentInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceTest {
    private DepartmentService service = null;
    private List<String> departmentNames = Arrays.asList("IT","INFRA","HR","ADMIN","FIN");
    private List<Integer> empIds = Arrays.asList(545,5,668,66,6,32,2,9,7);
    private List<Department> departmentList = Collections.EMPTY_LIST;

    @BeforeEach
    void setUp() {
        service = new DepartmentService();
        departmentList = generateDepartments();
    }

    @AfterEach
    void tearDown() {
        service = null;
        departmentList = Collections.emptyList();
    }

    @Test
    void createDepartments() {
        List<Department> departmentList = service.createDepartments(departmentNames);
        List<String> expectedNames = departmentList.stream().map(d-> d.getDepartmentType().toString()).collect(Collectors.toList());

        Collections.sort(expectedNames);
        Collections.sort(departmentNames);

        assertEquals(departmentList.size(),departmentNames.size());
        assertTrue(expectedNames.equals(departmentNames));
    }

    @Test
    void getDepartmentByName() {
        List<String> namesToSearch = Arrays.asList("HR","FIN");
        service.createDepartments(namesToSearch);

        List<Integer> deptIds = service.getDepartmentByName(namesToSearch);

        assertEquals(deptIds.size(),namesToSearch.size());
    }

    @Test
    void getDepartmentById() {
        service.createDepartments(departmentNames);

        Department department = service.getDepartmentById(2);

        assertNotNull(department);
    }

    @Test
    void getNullDepartmentForWrongId() {
        service.createDepartments(departmentNames);

        Department department = service.getDepartmentById(35135);

        assertNull(department);
    }

    @Test
    void distributeEmployees() {
        List<EmployeeDepartmentInfo> empDepInfo = service.distributeEmployees(empIds, Arrays.asList(1,2));
        assertEquals(empIds.size(), empDepInfo.size());
    }

    private List<Department> generateDepartments() {
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department(1, DepartmentType.ADMIN));
        departmentList.add(new Department(2, DepartmentType.FIN));
        departmentList.add(new Department(3, DepartmentType.HR));
        departmentList.add(new Department(4, DepartmentType.INFRA));
        departmentList.add(new Department(5, DepartmentType.IT));
        return departmentList;
    }
}