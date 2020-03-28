package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.stream.Stream;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEMESTER, PREFIX_COURSE_NAME,
                        PREFIX_MODULE, PREFIX_FOCUS_AREA);

        if (arePrefixesPresent(argMultimap, PREFIX_SEMESTER)) {
            String semester = argMultimap.getValue(PREFIX_SEMESTER).get();
            if (!ParserUtil.isInteger(semester)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
            }
            Integer intSemester = Integer.parseInt(semester);
            return new ShowCommand(intSemester); // returns int
        }

        if (arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)) {
            String name = argMultimap.getValue(PREFIX_COURSE_NAME).get();
            CourseName courseName = new CourseName(name);
            return new ShowCommand(courseName); // returns CourseName
        }

        if (arePrefixesPresent(argMultimap, PREFIX_MODULE)) {
            String name = argMultimap.getValue(PREFIX_MODULE).get();
            ModuleCode moduleCode = new ModuleCode(name);
            return new ShowCommand(moduleCode); // returns ModuleCode
        }

        if (arePrefixesPresent(argMultimap, PREFIX_FOCUS_AREA)) {
            String focusArea = argMultimap.getValue(PREFIX_FOCUS_AREA).get();
            return new ShowCommand(focusArea); // returns String
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

