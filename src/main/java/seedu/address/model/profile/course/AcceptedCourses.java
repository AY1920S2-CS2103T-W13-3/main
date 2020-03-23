package seedu.address.model.profile.course;

/**
 * Accepted courses stored as enumerations.
 */
public enum AcceptedCourses {
    COMPUTER_SCIENCE("COMPUTER SCIENCE"),
    BUSINESS_ANALYTICS("BUSINESS ANALYTICS"),
    INFORMATION_SYSTEMS("INFORMATION SYSTEMS"),
    INFORMATION_SECURITY("INFORMATION SECURITY");

    private String name;

    AcceptedCourses(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns true of enum contains given course.
     */
    public static boolean contains(String course) {
        for (AcceptedCourses c: AcceptedCourses.values()) {
            if (c.name.equals(course)) {
                return true;
            }
        }
        return false;
    }
}
