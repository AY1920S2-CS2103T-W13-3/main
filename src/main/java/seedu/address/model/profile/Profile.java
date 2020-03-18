package seedu.address.model.profile;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.CourseManager;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.personal.Deadline;

/**
 * Represents a Profile in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Profile {

    // Identity fields
    private static HashMap<Integer, ArrayList<Module>> moduleHash;
    private static String currentSemester;
    private final Name name;
    private final Course course;
    private final CourseName courseName;
    private final String specialisation;


    /**
     * Every field must be present and not null.
     */
    public Profile(Name name, CourseName courseName, String currentSemester, String specialisation) {
        requireAllNonNull(name);
        requireAllNonNull(courseName);
        requireAllNonNull(currentSemester);
        this.name = name;
        this.courseName = courseName;
        this.course = setCourse(courseName);
        this.currentSemester = currentSemester;
        this.specialisation = specialisation;
        HashMap<Integer, ArrayList<Module>> moduleHash = new HashMap<Integer, ArrayList<Module>>();
        this.moduleHash = moduleHash;
    }

    /**
     * Adds a module to the hashmap with the key being the semester
     */
    public static void addModule(Integer semester, Module module) {
        if (!moduleHash.isEmpty() && moduleHash.containsKey(semester)) {
            moduleHash.get(semester).add(module);
        } else {
            ArrayList<Module> semesterList = new ArrayList<Module>();
            semesterList.add(module);
            moduleHash.put(semester, semesterList);
        }
    }

    public Name getName() {
        return name;
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public static String getCurrentSemester() {
        return currentSemester;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public Course setCourse(CourseName courseName) {
        return CourseManager.getCourse(courseName);
    }

    public static ArrayList<Module> getModules(Integer semester) {
        return moduleHash.get(semester);
    }

    public Set<Map.Entry<Integer, ArrayList<Module>>> getMappings() {
        return moduleHash.entrySet();
    }

    public static HashMap<Integer, ArrayList<Module>> getHashMap() {
        return moduleHash;
    }

    public List<Deadline> getDeadlines() {
        int sem = Integer.parseInt(currentSemester);
        List<Module> modules = moduleHash.get(sem); // Deadlines should only be from the current semester
        List<Deadline> deadlineList = new ArrayList<>();
        for (Module module: modules) {
            deadlineList.addAll(module.getDeadlines());
        }
        return deadlineList;
    }


    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Profile otherProfile) {
        if (otherProfile == this) {
            return true;
        }

        return otherProfile != null
                && otherProfile.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Profile)) {
            return false;
        }

        Profile otherProfile = (Profile) other;
        return otherProfile.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
