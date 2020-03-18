package seedu.address.model;

import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseName;

import static java.util.Objects.requireNonNull;

/**
 * Represents the in-memory model of the course list data.
 */
public class CourseManager {

    private static CourseList courseList;

    public CourseManager(CourseList courseList) {
        requireNonNull(courseList);
        this.courseList = courseList;
    }

    public CourseManager() {
        this(new CourseList());
    }

    public static Course getCourse(CourseName courseName) {
        return courseList.getCourse(courseName);
    }
}
