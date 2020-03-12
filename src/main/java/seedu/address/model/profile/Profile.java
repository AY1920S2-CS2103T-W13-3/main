package seedu.address.model.profile;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.profile.course.Course;

/**
 * Represents a Profile in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Profile {

    // Identity fields
    private static String currentSemester;
    private final Name name;
    private final Course course;
    private final String specialisation;

    /**
     * Every field must be present and not null.
     */
    public Profile(Name name, Course course, String currentSemester, String specialisation) {
        requireAllNonNull(name);
        requireAllNonNull(course);
        requireAllNonNull(currentSemester);
        this.name = name;
        this.course = course;
        this.currentSemester = currentSemester;
        this.specialisation = specialisation;
    }

    public Name getName() {
        return name;
    }

    public Course getCourse() {
        return course;
    }

    public static String getCurrentSemester() {
        return currentSemester;
    }

    public String getSpecialisation() {
        return specialisation;
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