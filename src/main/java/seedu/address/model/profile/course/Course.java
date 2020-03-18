package seedu.address.model.profile.course;

import java.util.List;

/**
 * Represents a profile's course in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Course {

    private final CourseName courseName;
    private List<CourseFocusArea> focusAreas;

    public Course(CourseName courseName, List<CourseFocusArea> focusAreas) {
        this.courseName = courseName;
        this.focusAreas = focusAreas;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(courseName);
        for (CourseFocusArea focusArea : focusAreas) {
            output.append("\n");
            output.append(focusArea);
        }
        return output.toString();
    }

    public CourseName getCourseName() {
        return this.courseName;
    }
}
