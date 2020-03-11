package seedu.address.model.profile.course.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Module's semester data in the address book.
 * Guarantees:
 */
public class SemesterData {

    public final List<Integer> semesters;

    /**
     * Constructs a {@code SemesterData}.
     *
     * @param semNumber A valid semester number
     */
    public SemesterData(List<String> semesters) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        this.semesters = new ArrayList<>();
        semesters.forEach(sem -> this.semesters.add(Integer.parseInt(sem)));
    }

    @Override
    public String toString() {
        return this.semesters.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SemesterData // instanceof handles nulls
                && semesters.equals(((SemesterData) other).semesters)); // state check
    }

    // methods to be implemented
    // isValidCode()
    // hashCode()
}
