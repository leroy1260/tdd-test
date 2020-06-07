package com.tddexample.dao;

public class EmployeeFinanceInfo {
    private final int empId;
    private final int salary;
    private final int basic;
    private final int pf;
    private final int gratuity;

    private EmployeeFinanceInfo(EmployeeFinanceBuilder builder) {
        this.salary = builder.ctc;
        this.empId = builder.empId;
        this.basic = builder.basic;
        this.pf = builder.pf;
        this.gratuity = builder.gratuity;
    }

    public int getEmpId() {
        return empId;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getCtc() {
        return salary;
    }

    public Integer getPf() {
        return pf;
    }

    public Integer getGratuity() {
        return gratuity;
    }

    public static int calculateBasic(int ctc, int per) {
        return (int) (ctc * 0.20);
    }

    public static int calculatePF(int basic, int per) {
        return (int) (basic * 0.10);
    }

    public static int calculateGratuity(int basic, int per) {
        return (int) (basic * 0.05);
    }

    @Override
    public String toString() {
        return "EmployeeFinanceInfo{" +"empId=" + empId +", salary=" + salary +", basic=" + basic +", pf=" + pf +", gratuity=" + gratuity +'}';
    }

    public static class EmployeeFinanceBuilder {
        private int empId;
        private int ctc;
        private int basic;
        private int pf;
        private int gratuity;

        public EmployeeFinanceBuilder withEmpId(int id) {
            this.empId = id;
            return this;
        }

        public EmployeeFinanceBuilder withCtc(int ctc) {
            this.ctc = ctc;
            return this;
        }

        public EmployeeFinanceBuilder withBasic(int basic) {
            this.basic = basic;
            return this;
        }

        public EmployeeFinanceBuilder withPf(int pf) {
            this.pf = pf;
            return this;
        }

        public EmployeeFinanceBuilder withGratuity(int gratuity) {
            this.gratuity =  gratuity ;
            return this;
        }

        public EmployeeFinanceInfo create() {
            return new EmployeeFinanceInfo(this);
        }
    }
}
