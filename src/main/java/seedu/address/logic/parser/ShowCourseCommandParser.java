package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import seedu.address.logic.commands.ShowCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseName;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCourseCommandParser implements Parser<ShowCourseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     * @throws NoSuchElementException if the user input course does not exist
     */
    public ShowCourseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE_NAME);

        String courseName = argMultimap.getValue(PREFIX_COURSE_NAME).get();
        CourseName modelCourseName = new CourseName(courseName);
        try {
            Course course = CourseManager.getCourse(modelCourseName);
            return new ShowCourseCommand(course);
        } catch (NoSuchElementException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

