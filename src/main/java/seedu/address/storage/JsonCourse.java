package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseFocusArea;
import seedu.address.model.profile.course.CourseName;

/**
 * Jackson-friendly version of {@link JsonCourse}.
 */
class JsonCourse {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course's %s field is missing!";

    private final String courseName;
    private final List<JsonCourseFocusArea> focusAreas;

    @JsonCreator
    public JsonCourse(@JsonProperty("courseName") String courseName,
                      @JsonProperty("focusArea") List<JsonCourseFocusArea> focusAreas) {
        this.courseName = courseName;
        this.focusAreas = focusAreas;
    }

    /**
     * Converts this Jackson-friendly module object into a {@code Course} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the module.
     */
    public Course toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        // Note that some fields such as prerequisite and preclusion are optional fields and are thus omitted
        if (courseName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Course"));
        } else if (focusAreas == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Focus Area"));
        }

        CourseName modelCourseName = new CourseName(courseName);
        List<CourseFocusArea> courseFocusAreas = new ArrayList<>();
        for (JsonCourseFocusArea focusArea : focusAreas) {
            CourseFocusArea modelFocusArea = focusArea.toModelType();
            courseFocusAreas.add(modelFocusArea);
        }

        return new Course(modelCourseName, courseFocusAreas);
    }
}


