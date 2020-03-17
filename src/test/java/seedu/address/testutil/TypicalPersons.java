package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.profile.Profile;

/**
 * A utility class containing a list of {@code Profile} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Profile ALICE = new PersonBuilder().withName("Alice Pauline")
            .withCourseName("Computer Science").build();
    public static final Profile BENSON = new PersonBuilder().withName("Benson Meier")
            .withCourseName("Computer Science").build();
    public static final Profile CARL = new PersonBuilder().withName("Carl Kurz")
            .withCourseName("Computer Science").build();
    public static final Profile DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withCourseName("Computer Science").build();
    public static final Profile ELLE = new PersonBuilder().withName("Elle Meyer")
            .withCourseName("Computer Science").build();
    public static final Profile FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withCourseName("Computer Science").build();
    public static final Profile GEORGE = new PersonBuilder().withName("George Best")
            .withCourseName("Computer Science").build();

    // Manually added
    public static final Profile HOON = new PersonBuilder().withName("Hoon Meier")
            .withCourseName("Computer Science").build();
    public static final Profile IDA = new PersonBuilder().withName("Ida Mueller")
            .withCourseName("Computer Science").build();

    // Manually added - Profile's details found in {@code CommandTestUtil}
    public static final Profile AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withCourseName(VALID_COURSE_CS).build();
    public static final Profile BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withCourseName(VALID_COURSE_CS).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Profile profile : getTypicalPersons()) {
            ab.addPerson(profile);
        }
        return ab;
    }

    public static List<Profile> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
