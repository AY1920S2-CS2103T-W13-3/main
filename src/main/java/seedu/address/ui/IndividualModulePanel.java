package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.profile.course.module.Module;

/**
 * Panel containing information related to specific Module.
 */
public class IndividualModulePanel extends UiPart<Region> {
    private static final String FXML = "IndividualModulePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private VBox individualModulePanel;
    @FXML
    private Label moduleCode;
    @FXML
    private Label moduleTitle;
    @FXML
    private Label preReqs;
    @FXML
    private Label modularCredits;
    @FXML
    private Label description;
    @FXML
    private Label semData;



    public IndividualModulePanel(Module module) {
        super(FXML);
        moduleCode.setText(module.getModuleCode().toString());
        moduleTitle.setText(module.getTitle().toString().toUpperCase());
        preReqs.setText("Prerequisite: \n" + module.getPrereqs().prereqs);
        modularCredits.setText("Modular Credits: " + module.getModularCredits().toString());
        description.setText("Module Description: \n" + module.getDescription().description);
        semData.setText("Semester Data: \nThis module is available in Semesters "
                + module.getSemesterData().toString());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Profile} using a {@code DeadlineCard}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleCard(module).getRoot());
            }
        }
    }

}
