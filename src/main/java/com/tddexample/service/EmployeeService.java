package com.tddexample.service;

import com.tddexample.dao.Employee;
import com.tddexample.dao.EmployeeDepartmentInfo;
import com.tddexample.dao.EmployeeFinanceInfo;
import com.tddexample.util.CommonUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class EmployeeService {

    private List<Employee> employeeList = Collections.EMPTY_LIST;
    private List<EmployeeFinanceInfo> employeesFinanceList = Collections.EMPTY_LIST;
    private Random rand = new Random();

    public List<Employee> createEmployees(int total, int ageFrom, int ageTo) {
        List<Integer> getAges = CommonUtils.inclusiveRange(ageFrom, ageTo);
        this.employeeList = IntStream.range(0, total)
                .boxed()
                .map(create(rand, getAges))
                .collect(toList());

        return Collections.unmodifiableList(this.employeeList);
    }

    private Function<Integer, Employee> create(Random rand, List<Integer> ages) {
        return x -> new Employee(UUID.randomUUID().toString(), ages.get(rand.nextInt(ages.size())));
    }

    public List<Integer> getEmployeeIds() {
        if(!employeeList.isEmpty()) {
            List<Integer> empIds = employeeList.stream().map(Employee::getEmpId).collect(toList());
            Collections.shuffle(empIds);
            return  Collections.unmodifiableList(empIds);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public List<EmployeeFinanceInfo> createEmployeesFinanceInfo(List<Integer> empIds, int salFrom, int salTo) {
        List<Integer> getSalaries = CommonUtils.inclusiveRangeWithStep500(salFrom, salTo);
        this.employeesFinanceList = empIds.stream()
                .map(createNewEmployeeFinanceInfo(getSalaries))
                .collect(toList());
        return Collections.unmodifiableList(this.employeesFinanceList);
    }

    private Function<Integer, EmployeeFinanceInfo> createNewEmployeeFinanceInfo(List<Integer> salaries) {
        return empId -> {
            int ctc = salaries.get(rand.nextInt(salaries.size()));
            int basic = EmployeeFinanceInfo.calculateBasic(ctc, 20);
            int pf = EmployeeFinanceInfo.calculateBasic(basic, 10);
            int gratuity = EmployeeFinanceInfo.calculateGratuity(basic, 10);
            return new EmployeeFinanceInfo.EmployeeFinanceBuilder()
                    .withEmpId(empId)
                    .withCtc(ctc)
                    .withBasic(basic)
                    .withPf(pf)
                    .withGratuity(gratuity)
                    .create();
        };
    }

    public List<Integer> filterEmpByAgeGt(int age) {
        return Collections.unmodifiableList(employeeList.stream().filter(emp -> emp.getAge() > age).map(Employee::getEmpId).collect(toList()));
    }

    public List<Integer> filterEmpByCTCGt(int ctc, List<Integer> empList) {
        return Collections.unmodifiableList(employeesFinanceList.stream()
                .filter(emp -> empList.contains(emp.getEmpId()) && emp.getCtc() > ctc)
                .map(EmployeeFinanceInfo::getEmpId)
                .collect(toList()));
    }

    public List<Integer> filterEmpByGratuityLt(int gratiuty, List<Integer> empList) {
        return Collections.unmodifiableList(employeesFinanceList.stream()
                .filter(emp -> empList.contains(emp.getEmpId()) && emp.getGratuity() < gratiuty)
                .map(EmployeeFinanceInfo::getEmpId)
                .collect(toList()));
    }

    public Integer getDepartmentWithMaxEmpFrom(List<Integer> employees, List<EmployeeDepartmentInfo> employeeDepartmentInfos) {
        return employeeDepartmentInfos.stream()
                .filter(empDep -> employees.contains(empDep.getEmpId()))
                .collect(
                        Collectors.groupingBy(EmployeeDepartmentInfo::getDepartmentId, Collectors.counting())
                )
                .entrySet()
                .stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    public List<Employee> filterEmpByIds(List<Integer> empIds) {
        return employeeList.stream()
                .filter(emp -> empIds.contains(emp.getEmpId()))
                .collect(toList());
    }
}
