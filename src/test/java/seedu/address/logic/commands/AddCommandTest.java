package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_ADD_SUCCESS;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DEADLINE_INVALID_SEMESTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.Year;
import seedu.address.model.profile.course.AcceptedCourses;
import seedu.address.model.profile.course.AcceptedFocusArea;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.FocusArea;
import seedu.address.model.profile.course.module.Description;
import seedu.address.model.profile.course.module.ModularCredits;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.Preclusions;
import seedu.address.model.profile.course.module.PrereqTreeNode;
import seedu.address.model.profile.course.module.Prereqs;
import seedu.address.model.profile.course.module.SemesterData;
import seedu.address.model.profile.course.module.Title;
import seedu.address.model.profile.course.module.personal.Deadline;

//@@author wanxuanong

public class AddCommandTest {

    // No profile exists, hence modules cannot be added
    @Test
    public void execute_noExistingProfile_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        AddCommand addCommand = new AddCommand(moduleCode, semester, null, null, null, null);
        assertThrows(CommandException.class, MESSAGE_EMPTY_PROFILE_LIST, () ->
                addCommand.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStub()));
    }

    // No module code added, user inputs "add m/"
    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(
                new ModuleCode(null), 0, null, null, null, null));
    }

    //No semester added, user inputs "add m/CS1231 y/"
    @Test
    public void constructor_nullSemester_throwsNullPointerException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        assertThrows(NullPointerException.class, () -> new AddCommand(
                moduleCode, new Year(null).getSemester(), null, null, null, null));
    }

    //Invalid module, valid semester, user inputs "add m/123ABC y/2.1"
    @Test
    public void execute_invalidModuleCode_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(INVALID_MODCODE_DESC);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        AddCommand addCommandModule = new AddCommand(
                moduleCode, semester, null, null, null, null);
        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE, () ->
                addCommandModule.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }


    //Module has been added before (to a future semester)
    @Test
    public void execute_duplicateModule_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        int semester = new Year("1.2").getSemester();
        AddCommand addCommandModule = new AddCommand(
                moduleCode, semester, null, null, null, null);
        assertThrows(CommandException.class, MESSAGE_DUPLICATE_MODULE, () ->
                addCommandModule.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    //Valid module code, different capitalizations
    @Test
    public void execute_validModuleCode_success() {
        ModuleCode moduleCode = new ModuleCode("CS1231");
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        AddCommand addCommandAllCap = new AddCommand(
                moduleCode, semester, null, null, null, null);
        AddCommand addCommandVariety = new AddCommand(
                new ModuleCode("Cs1231"), semester, null, null, null, null);
        AddCommand addCommandNoCap = new AddCommand(
                new ModuleCode("cs1231"), semester, null, null, null, null);

        try {
            assertEquals(addCommandAllCap.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            assertEquals(addCommandVariety.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            assertEquals(addCommandNoCap.execute(
                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_ADD_SUCCESS, moduleCode));
        } catch (CommandException e) {
            fail();
        }
    }

    //Valid grade, user inputs "add m/MA1521, y/1.1, g/C+"
    @Test
    public void execute_validGrade_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        String grade = VALID_GRADE_BOB;
        AddCommand addCommandGrade = new AddCommand(
                moduleCode, semester, grade, null, null, null);
        try {
            assertEquals(addCommandGrade.execute(new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(),
                    new ModuleManagerStubCs()).getFeedbackToUser(), String.format(MESSAGE_ADD_SUCCESS, moduleCode));
        } catch (CommandException e) {
            fail();
        }
    }

    //Invalid date time, user inputs "add m/CS1101S y/1.1 t/assignment d/2020-30-30 23:60"
    //Tested in AddCommandParser
    //@Test
    //public void execute_invalidDateTime_throwsCommandException() {
    //    ModuleCode moduleCode = new ModuleCode("CS1101S");
    //    int semester = new Year(VALID_SEMESTER_BOB).getSemester();
    //    String task = VALID_TASK_BOB;
    //    LocalDate date = LocalDate.parse("2020-30-30");
    //    LocalTime time = LocalTime.parse("23:60");
    //    AddCommand addCommandDateTime = new AddCommand(
    //            moduleCode, semester, null, task, date, time);
    //    assertThrows(CommandException.class, Deadline.MESSAGE_CONSTRAINTS, () ->
    //           addCommandDateTime.execute(
    //                    new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    //}

    @Test
    public void execute_addTaskToNonCurrentSemester_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        String task = VALID_TASK_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);
        AddCommand addCommandTask = new AddCommand(
                moduleCode, semester, null, task, date, time);
        assertThrows(CommandException.class, MESSAGE_DEADLINE_INVALID_SEMESTER, () ->
                addCommandTask.execute(
                        new ProfileManagerWithNonEmptyProfile(), new CourseManagerStub(), new ModuleManagerStubCs()));
    }

    @Test
    public void equals() {
        LocalDate modelDate = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime modelTime = LocalTime.parse(VALID_DEADLINE_TIME_AMY, DateTimeFormatter.ofPattern("HH:mm"));

        AddCommand addAmyCommand = new AddCommand(new ModuleCode(VALID_MODCODE_AMY),
                new Year(VALID_SEMESTER_AMY).getSemester(), VALID_GRADE_AMY, VALID_TASK_AMY, modelDate, modelTime);
        AddCommand addBobCommand = new AddCommand(
                new ModuleCode(VALID_MODCODE_BOB), new Year(VALID_SEMESTER_BOB).getSemester(),
                VALID_GRADE_BOB, VALID_TASK_BOB, modelDate, modelTime);

        // same object -> returns true
        assertTrue(addAmyCommand.equals(addAmyCommand));

        // same values -> returns true
        AddCommand addAmyCommandCopy = new AddCommand(new ModuleCode(VALID_MODCODE_AMY),
                new Year(VALID_SEMESTER_AMY).getSemester(), VALID_GRADE_AMY, VALID_TASK_AMY, modelDate, modelTime);
        assertTrue(addAmyCommand.equals(addAmyCommandCopy));

        // same module code but remainder fields differ -> returns true
        AddCommand addBobCommandDiff = new AddCommand(new ModuleCode(VALID_MODCODE_BOB),
                new Year(VALID_SEMESTER_AMY).getSemester(), VALID_GRADE_AMY, VALID_TASK_AMY, modelDate, modelTime);
        assertTrue(addBobCommand.equals(addBobCommandDiff));

        // different types -> returns false
        assertFalse(addAmyCommand.equals(1));

        // null -> returns false
        assertFalse(addAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(addAmyCommand.equals(addBobCommand));
    }


    private class ProfileManagerStub extends ProfileManager {
        protected ObservableList<Profile> profileList = FXCollections.observableArrayList();
        protected FilteredList<Profile> filteredProfiles;
        protected ObservableList<Deadline> deadlineList;

        private ProfileManagerStub(List<Profile> profileList) {
            requireNonNull(profileList);
            this.profileList.setAll(profileList);
            filteredProfiles = new FilteredList<>(this.profileList);
        }

        public ProfileManagerStub() {
            this(new ArrayList<>());
        }

        @Override
        public Profile getFirstProfile() {
            return this.profileList.get(0);
        }

        @Override
        public ObservableList<Profile> getFilteredPersonList() {
            return filteredProfiles;
        }

        public void setPerson(Profile target, Profile editedProfile) {
            requireAllNonNull(target, editedProfile);
            int index = this.profileList.indexOf(target);
            this.profileList.set(index, editedProfile);
        }
    }

    private class ProfileManagerWithNonEmptyProfile extends ProfileManagerStub {
        private ProfileManagerWithNonEmptyProfile() {
            Module module = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            ObservableList<Profile> profileList = FXCollections.observableArrayList();
            Profile profile = new Profile(new Name("John"), new CourseName(
                    AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                    new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));
            profile.addModule(1, module);
            profileList.add(profile);
            this.profileList = profileList;
            filteredProfiles = new FilteredList<>(this.profileList);
        }
        @Override
        public boolean hasOneProfile() {
            return true;
        }
    }


    private class ProfileManagerWithEmptyProfile extends ProfileManagerStub {
        private ProfileManagerWithEmptyProfile() {
            ObservableList<Profile> profileList = FXCollections.observableArrayList();
            Profile profile = new Profile(new Name("John"), new CourseName(
                    AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                    new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));
            profileList.add(profile);
            this.profileList = profileList;
            filteredProfiles = new FilteredList<>(this.profileList);
        }

        @Override
        public boolean hasOneProfile() {
            return true;
        }
    }

    private class CourseManagerStub extends CourseManager {
        protected List<Course> courseList;

        private CourseManagerStub(List<Course> courseList) {
            requireNonNull(courseList);
            this.courseList = courseList;
        }

        public CourseManagerStub() {
            this(new ArrayList<>());
        }

        @Override
        public Course getCourse(CourseName courseName) throws ParseException {
            for (Course course : courseList) {
                if (course.getCourseName().equals(courseName)) {
                    return course;
                }
            }
            throw new ParseException("Course does not exist");
        }
    }

    private class ModuleManagerStub extends ModuleManager {
        protected List<Module> moduleList;

        private ModuleManagerStub(List<Module> moduleList) {
            requireNonNull(moduleList);
            this.moduleList = moduleList;
        }

        public ModuleManagerStub() {
            this(new ArrayList<>());
        }

        @Override
        public boolean hasModule(ModuleCode moduleCode) {
            for (Module module: moduleList) {
                if (module.getModuleCode().equals(moduleCode)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Module getModule(ModuleCode moduleCode) {
            requireNonNull(moduleCode);

            for (Module mod: moduleList) {
                if (mod.getModuleCode().equals(moduleCode)) {
                    return mod;
                }
            }
            // Code should not reach this line
            assert false;
            return null;
        }
    }


    private class ModuleManagerStubCs extends ModuleManagerStub {
        private ModuleManagerStubCs() {
            Module moduleCs = new Module(new ModuleCode("CS1231"), new Title(""), new Prereqs(""),
                    new Preclusions(""), new ModularCredits("4"), new Description(""),
                    new SemesterData(new ArrayList<>()), null);
            Module moduleBob = new Module(new ModuleCode(VALID_MODCODE_BOB), new Title(""), new Prereqs(""),
                    new Preclusions(""), new ModularCredits("4"), new Description(""),
                    new SemesterData(new ArrayList<>()), null);
            Module module = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            moduleList.add(moduleCs);
            moduleList.add(moduleBob);
            moduleList.add(module);
        }
    }
}
