package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.model.profile.Profile.getModules;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.logic.commands.ShowCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.profile.course.*;
import seedu.address.model.profile.course.module.Module;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCourseCommandParser implements Parser<ShowCourseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCourseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE_NAME);

        // Get Semester
        String courseName = argMultimap.getValue(PREFIX_COURSE_NAME).get();
        CourseName modelCourseName = new CourseName(courseName);
        Course course = CourseManager.getCourse(modelCourseName);

        return new ShowCourseCommand(course);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

