import java.util.Objects;

public class PairEmployees {

    private int emp1;
    private int emp2;

    public PairEmployees(int emp1, int emp2) {
        this.emp1 = emp1;
        this.emp2 = emp2;
    }

    public int getEmp1() {
        return emp1;
    }

    public int getEmp2() {
        return emp2;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PairEmployees pair)) {
            return false;
        }
        return (pair.getEmp1() == this.getEmp1() && pair.getEmp2() == this.getEmp2()) ||
                (pair.getEmp1() == this.getEmp2() && pair.getEmp2() == this.getEmp1());
    }

    @Override
    public int hashCode() {
        if(emp1 < emp2)
            return Objects.hash(emp1, emp2);
        else
            return Objects.hash(emp2, emp1);
    }
}
