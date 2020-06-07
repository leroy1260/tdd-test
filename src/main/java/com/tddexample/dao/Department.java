package com.tddexample.dao;


public class Department {

    private final int id;
    private final DepartmentType departmentType;

    public Department(int id, DepartmentType departmentType) {
        this.id = id;
        this.departmentType = departmentType;
    }

    public int getId() {
        return id;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentType=" + departmentType +
                '}';
    }
}
