package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.course.Course;

/**
 * Displays details requested by user.
 */
public class ShowCourseCommand extends Command {

    public static final String COMMAND_WORD = "showCourse";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the module requirement for this course. "
            + "Parameters: "
            + PREFIX_COURSE_NAME + "COURSE_NAME" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COURSE_NAME + "Computer Science";

    public static final String MESSAGE_SUCCESS = "All module requirements for this course are shown: \n %1$s";

    private final Course toShow;

    /**
     * Creates an ShowCourseCommand to show the specified {@code Course}
     * @param course
     */
    public ShowCourseCommand(Course course) {
        requireNonNull(course);
        toShow = course;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toShow));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCourseCommand // instanceof handles nulls
                && toShow.equals(((ShowCourseCommand) other).toShow));
    }
}
