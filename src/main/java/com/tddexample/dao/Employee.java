package com.tddexample.dao;

import java.util.concurrent.atomic.AtomicInteger;

public class Employee {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int empId;
    private final String name;
    private final String lastName = "NA";
    private final int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
        empId = count.incrementAndGet();
    }

    public int getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Employee{" +"empId=" + empId +", name='" + name + '\'' +", lastName='" + lastName + '\'' +", age=" + age +'}';
    }
}
