package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseName;


/**
 * Creates a new CourseList object which contains Module objects.
 */
public class CourseList {

    private ArrayList<Course> courseList = new ArrayList<>();

    public CourseList() {}

    /**
     * Adds a course to the course list.
     */
    public void addCourse(Course course) {
        courseList.add(course);
    }

    /**
     * Returns the Course with course name {@code courseName} in the course list, if it exists.
     * @throws NoSuchElementException No module in the module list contains {@code moduleCode}.
     */
    public Course getCourse(CourseName courseName) throws NoSuchElementException {
        requireNonNull(courseName);
        for (Course course : courseList) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        throw new NoSuchElementException("The course " + courseName + " does not exist!");
    }

    /**
     * Returns the module with module code {@code moduleCode} in the module list, if it exists.
     * @throws NoSuchElementException No module in the module list contains {@code moduleCode}.
     */
    /*
    public Module getModuleWithModuleCode(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        if (!hasModuleWithModuleCode(moduleCode)) {
            throw new NoSuchElementException("Module with module code " + moduleCode.toString() + " does not exist");
        }
        for (Module mod: moduleList) {
            if (mod.getModuleCode().equals(moduleCode)) {
                return mod;
            }
        }
        // Code should not reach this line
        assert false;
        return null;
    }

     */
}
