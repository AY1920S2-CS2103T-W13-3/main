package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;

//@@author chanckben

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MODULE, PREFIX_TASK, PREFIX_GRADE);

        Name name;
        ModuleCode moduleCode;
        Deadline task;

        // Delete profile
        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            String strName = argMultimap.getValue(PREFIX_NAME).get();
            name = ParserUtil.parseName(strName);
            return new DeleteCommand(name);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_MODULE)) {
            String strModuleCode = argMultimap.getValue(PREFIX_MODULE).get().trim().toUpperCase();
            moduleCode = ParserUtil.parseModuleCode(strModuleCode);

            // Delete task
            if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
                String strDeadline = argMultimap.getValue(PREFIX_TASK).get();
                task = new Deadline(strModuleCode, strDeadline);
                return new DeleteCommand(moduleCode, task);
            }

            // Delete grade
            if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
                String grade = argMultimap.getValue(PREFIX_GRADE).get();
                return new DeleteCommand(moduleCode, grade);
            }

            // Delete module
            return new DeleteCommand(moduleCode);
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
