package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModuleList;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseFocusArea;
import seedu.address.model.profile.course.module.Module;

/**
 * Displays details requested by user.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": The following items can be shown:\n"
            + "Course, with " + PREFIX_COURSE_NAME + "COURSE\n"
            + "Course Focus Area, with " + PREFIX_FOCUS_AREA + "FOCUS_AREA\n"
            + "Module, with " + PREFIX_MODULE + "MODULE_CODE\n"
            + "Modules taken in a semester, with " + PREFIX_CURRENT_SEMESTER + "SEMESTER_NUMBER\n";

    public static final String MESSAGE_SUCCESS_COURSE = "Course requirements for this course are: \n%1$s";
    public static final String MESSAGE_SUCCESS_FOCUS_AREA = "Modules in this focus area are:\n%1$s";
    public static final String MESSAGE_SUCCESS_MODULE = "The details for this module are:\n%1$s";
    public static final String MESSAGE_SUCCESS_MODULE_LIST = "All modules taken in semester are shown: "
            +"\nEnter [show m/MODULE_CODE] to find out more about the module";
    public static final String MESSAGE_SUCCESS_NAME = "Here is your academic overview: ";


    private final Object toShow;

    /**
     * Creates a ShowCommand to show the specified object
     * @param toShow - Can be any object from this list: (Course,
     *               CourseFocusArea, Module, List of Modules in a semester)
     */
    public ShowCommand(Object toShow) {
        requireNonNull(toShow);
        this.toShow = toShow;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String message = "";
        if (toShow instanceof Course) {
            message = MESSAGE_SUCCESS_COURSE; //no panel yet
        } else if (toShow instanceof CourseFocusArea) {
            message = MESSAGE_SUCCESS_FOCUS_AREA; //no panel yet
        } else if (toShow instanceof Module) {
            message = MESSAGE_SUCCESS_MODULE;
        } else if (toShow instanceof ModuleList) { //show module list
            FilteredList<Module> filteredModules = new FilteredList<>(((ModuleList) toShow).getModuleList());
            model.setDisplayedView(filteredModules); //show module list
            message = MESSAGE_SUCCESS_MODULE_LIST;
            return new CommandResult(String.format(message, toShow), true);
        } else if (toShow instanceof Name) { //show overview
            message = MESSAGE_SUCCESS_NAME;
            return new CommandResult(String.format(message, toShow), true);
        }

        return new CommandResult(String.format(message, toShow), false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && toShow.equals(((ShowCommand) other).toShow));
    }
}
