package seedu.address.model.profile.course;

import java.util.List;

import seedu.address.model.profile.course.module.ModularCredits;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Represents a Course's focus area.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CourseFocusArea {

    private final String focusAreaName;
    private final List<ModuleCode> modules;
    private final ModularCredits modularCredits;

    /**
     * Every field must be present and not null.
     */
    public CourseFocusArea(String focusAreaName, List<ModuleCode> modules, ModularCredits modularCredits) {
        this.focusAreaName = focusAreaName;
        this.modules = modules;
        this.modularCredits = modularCredits;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(focusAreaName + ":");
        for (ModuleCode moduleCode : modules) {
            output.append("\n");
            output.append(moduleCode);
        }
        return output.toString();
    }

    // Implement getter setter
    // equals
    // tostring
}
