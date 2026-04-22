package comparison;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Student implements Comparable<Student> {

    private String name;
    private List<Integer> marks;
    private int id;

    @Override
    public int compareTo(Student o) {
        if(o == null)
            return -1; // Nulls last
        int idCompare = Integer.compare(this.id, o.id);
        if (idCompare != 0)
            return idCompare;
        //Note: do null checks for name
        int nameCompare = this.name.compareTo(o.name);
        if (nameCompare != 0)
            return nameCompare;

        //Note: do null checks for list
        int size1 = this.marks.size();
        int size2 = o.marks.size();
        int minSize = Math.min(size1, size2);
        int compare = 0;
        for (int i = 0; i < minSize; i++) {
            int mark1 = this.marks.get(i);
            int mark2 = o.marks.get(i);
            compare = Integer.compare(mark1, mark2);
            if (compare != 0)
                return compare;
        }

        if (size1 != size2) {
            return size1 - size2;
        }
        return compare;
    }

}