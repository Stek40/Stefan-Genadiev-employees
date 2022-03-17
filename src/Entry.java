import java.time.LocalDate;

public class Entry {

    private int empID;
    private int ProjectID;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public Entry(int empID, int projectID, LocalDate dateFrom, LocalDate dateTo) {
        this.empID = empID;
        ProjectID = projectID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmpID() {
        return empID;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo == null ? LocalDate.now() : dateTo;
    }

    @Override
    public String toString() {
        return this.getEmpID() + " " + this.getProjectID() + " " + this.getDateFrom() + " " + this.getDateTo();
    }
}
