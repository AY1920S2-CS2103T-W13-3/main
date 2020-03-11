package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPEC;

import java.util.stream.Stream;

import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.Course;

/**
 * Parses input arguments and creates a new Profile Object.
 */
public class NewCommandParser implements Parser<NewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NewCommand
     * and returns a NewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COURSE, PREFIX_CURRENT_SEMESTER, PREFIX_SPEC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_COURSE, PREFIX_CURRENT_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Course course = ParserUtil.parseCourse(argMultimap.getValue(PREFIX_COURSE).get());
        String currentSemester = argMultimap.getValue(PREFIX_CURRENT_SEMESTER).get();

        String specialisation = null;
        if (arePrefixesPresent(argMultimap, PREFIX_SPEC)) {
            specialisation = argMultimap.getValue(PREFIX_SPEC).get();
        }

        Profile profile = new Profile(name, course, currentSemester, specialisation);

        return new NewCommand(profile);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
