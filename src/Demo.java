import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Demo {

    public static void main(String[] args) {

        String fileName = "emp_data.csv";

        Scanner sc = new Scanner(System.in);
        //System.out.print("Enter filename: ");
        //fileName = sc.next();

        ArrayList<Entry> entries = new ArrayList<>();
        HashMap<PairEmployees, Integer> pairs = new HashMap<>();
        readCSV(fileName, entries, pairs);

        Map.Entry<PairEmployees,Integer> result = pairs.entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue())).get();
        System.out.println("RESULT : " + result.getKey().getEmp1() + " " + result.getKey().getEmp2() + " " + result.getValue());

        System.out.println("\nALL : ");
        for (Map.Entry<PairEmployees, Integer> e: pairs.entrySet()) {
            System.out.println(e.getKey().getEmp1() + " " + e.getKey().getEmp2() + " " + e.getValue());
        }
    }

    public static void readCSV(String fileName, ArrayList<Entry> entries, HashMap<PairEmployees, Integer> pairs) {
        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(fileName));
            List<String> data;

            reader.readLine();
            while ((line = reader.readLine()) != null) {

                data = Arrays.stream(line.split(",")).map(s -> s.trim()).toList();
                Entry e = new Entry(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(1)),
                        LocalDate.parse(data.get(2)), data.get(3).equals("NULL") ? null : LocalDate.parse(data.get(3)));

                updatePairs(pairs, entries, e);
            }
        } catch (IOException e) {
            if (e.getCause() instanceof FileNotFoundException) {
                System.out.println("file not existing");
            }
            e.printStackTrace();
        }
    }

    private static void updatePairs(HashMap<PairEmployees, Integer> pairs, ArrayList<Entry> entries, Entry newE) {
        int daysWorkedTogether;
        int eID;
        int newEID = newE.getEmpID();
        PairEmployees newPair;

        for (Entry e : entries) {
            eID = e.getEmpID();
            daysWorkedTogether = daysWorkedTogether(e, newE);
            newPair = new PairEmployees(eID, newEID);
            if(daysWorkedTogether > 0) {
                if(!pairs.containsKey(newPair)) {
                    pairs.put(newPair, daysWorkedTogether);
                }
                else {
                    pairs.put(newPair, pairs.get(newPair) + daysWorkedTogether);
                }
            }
        }
        entries.add(newE);
    }

    private static int daysWorkedTogether(Entry e1, Entry e2) {
        if(e1.getProjectID() != e2.getProjectID()) return 0;
        //4 possible intervals between the ends and the starts of the 2 periods of worktime
        //[e1 start/end][e2 start/end]
        int startStart = (int)ChronoUnit.DAYS.between(e1.getDateFrom(), e2.getDateFrom());
        int endEnd = (int)ChronoUnit.DAYS.between(e1.getDateTo(), e2.getDateTo());
        int startEnd = (int)ChronoUnit.DAYS.between(e1.getDateFrom(), e2.getDateTo());
        int endStart = (int)ChronoUnit.DAYS.between(e1.getDateTo(), e2.getDateFrom());

        int e1StartEnd = (int)ChronoUnit.DAYS.between(e1.getDateFrom(), e1.getDateTo());
        int e2StartEnd = (int)ChronoUnit.DAYS.between(e2.getDateFrom(), e2.getDateTo());

        if(startStart >= 0 && endStart <= 0) {
            if(endEnd >= 0) {
                return endStart*(-1) + 1;
            }
            else {
                return e2StartEnd + 1;
            }
        }
        if(startStart <= 0 && startEnd >= 0) {
            if(endEnd <= 0) {
                return startEnd + 1;
            }
            else {
                return e1StartEnd + 1;
            }
        }
        return 0;
    }
}
