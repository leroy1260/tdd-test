package com.tddexample.service;

import com.tddexample.dao.Department;
import com.tddexample.dao.DepartmentType;
import com.tddexample.dao.EmployeeDepartmentInfo;

import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class DepartmentService {
    private List<Department> departmentList = Collections.EMPTY_LIST;
    private Random rand = new Random();

    public List<Department> createDepartments(final List<String> departments) {
        this.departmentList = IntStream.range(0, departments.size())
                .mapToObj(x -> new Department(x+1, DepartmentType.valueOf(departments.get(x))))
                .collect(toList());

        return Collections.unmodifiableList(this.departmentList);
    }

    public List<Integer> getDepartmentByName(List<String> names) {
        return Collections.unmodifiableList(departmentList.stream()
                .filter(department -> names.contains(department.getDepartmentType().toString()))
                .map(Department::getId)
                .collect(toList()));
    }

    public Department getDepartmentById(int id) {
        return departmentList.stream()
                .filter(department -> department.getId() == id)
                .findAny()
                .orElse(null);
    }

    public List<EmployeeDepartmentInfo> distributeEmployees(List<Integer> employees, List<Integer> totalDepartments){
        List<List<EmployeeDepartmentInfo>> result = new ArrayList<>();
        for(int i = 0; i < totalDepartments.size(); i++)
            result.add(new ArrayList<>());

        for(int i = 0; i < employees.size(); i++){
            EmployeeDepartmentInfo employeeDepartmentInfo = new EmployeeDepartmentInfo(employees.get(i), totalDepartments.get(rand.nextInt(totalDepartments.size())));
            result.get(i % totalDepartments.size()).add(employeeDepartmentInfo);
        }
        return Collections.unmodifiableList(result.stream().flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }
}
