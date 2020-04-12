package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DuplicateDeadlineException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleList;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Personal;

//@@author joycelynteo

/**
 * Adds a profile to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module or task to the module.\n"
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_YEAR + "SEMESTER "
            + "(" + PREFIX_TASK + "TASK) "
            + "(" + PREFIX_DEADLINE + "DEADLINE) "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_YEAR + "4 "
            + "(" + PREFIX_TASK + "assignment) "
            + "(" + PREFIX_DEADLINE + "2020-03-16 23:59) ";

    public static final String MESSAGE_ADD_SUCCESS = "New Module added: %1$s";
    public static final String MESSAGE_EDIT_SUCCESS = "Existing module updated: %1$s";
    public static final String MESSAGE_DEADLINE_INVALID_SEMESTER = "Error: You can only add tasks to modules that "
            + "are already added in the current semester";
    public static final String MESSAGE_DUPLICATE_MODULE = "Error: Module already exists as %1$s, "
            + "please specify specify task name and deadline if you would like to add a task";
    public static final String MESSAGE_UNFULFILLED_PREREQS = "NOTE: You may not have fulfilled the prerequisites of "
            + "%1$s before semester %2$s\nPrerequisites: %3$s";

    private final Set<ModuleCode> toAdd;
    private int addSemester;
    private String addGrade;
    private ArrayList<Deadline> addDeadlines;

    public AddCommand(Set<ModuleCode> moduleCodes, int semester, String grade, ArrayList<Deadline> deadlines) {
        requireNonNull(moduleCodes);
        requireNonNull(semester);

        toAdd = moduleCodes;
        addSemester = semester;
        if (grade != null) {
            addGrade = grade;
        }
        if (deadlines.size() != 0) {
            addDeadlines = deadlines;
        }
    }

    @Override
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) throws CommandException {
        requireNonNull(profileManager);
        requireNonNull(courseManager);
        requireNonNull(moduleManager);


        if (!profileManager.hasOneProfile()) {
            throw new CommandException(MESSAGE_EMPTY_PROFILE_LIST);
        }
        Profile profile = profileManager.getFirstProfile();

        if (toAdd.size() > 1) {
            if (!moduleManager.hasModules(toAdd)) {
                throw new CommandException(MESSAGE_INVALID_MODULE);
            }
            for (ModuleCode moduleCode: toAdd) {
                AddCommand command = new AddCommand(Set.of(moduleCode), addSemester, null, new ArrayList<Deadline>());
                command.execute(profileManager, courseManager, moduleManager);
            }
            return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, toAdd), true);
        }

        ModuleCode moduleCodeToAdd = toAdd.iterator().next();
        boolean hasModule = false;
        Module moduleToAdd = null;

        // throws error if module code does not exist! DO NOT REMOVE!
        if (moduleManager.hasModule(moduleCodeToAdd)) {
            moduleToAdd = moduleManager.getModule(moduleCodeToAdd);
        } else {
            throw new CommandException(MESSAGE_INVALID_MODULE);
        }
        int semesterOfModule = 0;

        // Check whether this module has been added to Profile semester HashMap
        for (ModuleList semesterList: profile.getSemModHashMap().values()) {
            for (Module moduleInSem: semesterList) {
                if (moduleToAdd.isSameModule(moduleInSem)) {
                    hasModule = true;
                    moduleToAdd = moduleInSem;
                    HashMap<Integer, ModuleList> hashMap = profile.getSemModHashMap();
                    semesterOfModule = getKey(hashMap, semesterList);
                }
            }
        }

        Personal personal;
        if (hasModule) { // Module already added to semester
            personal = moduleToAdd.getPersonal();
            if (addGrade == null && addDeadlines == null) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_MODULE,
                        personal.getStatus()));
            }
        } else { // Module does not exist
            if (addSemester == 0) {
                throw new CommandException("Error: Please add this module to a semester first.");
            }
            // Create Personal object
            personal = new Personal();

        }

        int currentSemester = profile.getOverallSemester();

        if (addGrade != null && !hasModule) {
            personal.setGrade(addGrade);
        } else if (addGrade != null && hasModule) {
            throw new CommandException("To add grade to an existing module, use: edit m/MODULE g/GRADE");
        }

        if (addDeadlines != null) {
            // Check if the deadline is added to a module in the current semester
            if (hasModule && semesterOfModule != currentSemester) {
                // This module has been added but it is not in current semester
                throw new CommandException(MESSAGE_DEADLINE_INVALID_SEMESTER);
            }
            if (!hasModule && addSemester != currentSemester) {
                /*
                 This module has not been added anywhere
                 and semester given by user does not match current semester
                 */
                throw new CommandException(MESSAGE_DEADLINE_INVALID_SEMESTER);
            }

            if (!hasModule) {
                profile.addModule(addSemester, moduleToAdd);
                hasModule = true;
            }


            for (Deadline deadline : addDeadlines) {
                if (personal.hasDeadline(deadline)) {
                    throw new DuplicateDeadlineException();
                }

                personal.addDeadline(deadline);
                profileManager.addDeadline(deadline);
            }
        }

        // Set the status of the module
        if (addSemester < currentSemester) {
            personal.setStatus("completed");
        } else if (addSemester == currentSemester) {
            personal.setStatus("in progress");
        } else {
            personal.setStatus("not taken");
        }

        moduleToAdd.setPersonal(personal);

        String messageShown;
        if (!hasModule) {
            profile.addModule(addSemester, moduleToAdd);
            // Check if prerequisites of the module have been fulfilled
            if (moduleToAdd.getPrereqTreeNode() != null && !moduleToAdd.getPrereqTreeNode()
                    .hasFulfilledPrereqs(profile.getAllModuleCodesBefore(addSemester))) {
                messageShown = String.format(MESSAGE_UNFULFILLED_PREREQS, moduleCodeToAdd, addSemester,
                        moduleToAdd.getPrereqs());
            } else {
                messageShown = MESSAGE_ADD_SUCCESS;
            }
            profileManager.setDisplayedView(profile);
            profile.updateCap();
            return new CommandResult(String.format(messageShown, moduleCodeToAdd), true);
        } else {
            messageShown = MESSAGE_EDIT_SUCCESS;
        }

        profile.updateCap();
        return new CommandResult(String.format(messageShown, moduleCodeToAdd), false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    /**
     * Returns key of the given value
     */
    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
