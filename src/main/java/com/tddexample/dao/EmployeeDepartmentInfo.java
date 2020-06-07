package com.tddexample.dao;

public class EmployeeDepartmentInfo {

    private final int empId;
    private final int departmentId;

    public EmployeeDepartmentInfo(int empId, int departmentId) {
        this.empId = empId;
        this.departmentId = departmentId;
    }

    public int getEmpId() {
        return empId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    @Override
    public String toString() {
        return "EmployeeDepartmentInfo{" +"empId=" + empId +", departmentId=" + departmentId +'}';
    }
}
