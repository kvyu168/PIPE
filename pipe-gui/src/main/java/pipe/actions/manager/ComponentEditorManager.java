package pipe.actions.manager;

import pipe.actions.gui.DeleteAction;
import pipe.actions.gui.GuiAction;
import pipe.actions.gui.edit.*;
import pipe.controllers.PipeApplicationController;
import pipe.views.PipeApplicationView;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * These components are responsible for editing and managing items
 * on the petri net canvas when pressed
 */
public class ComponentEditorManager {
    /**
     * Action copies whatever is selected at the time
     */
    private final CopyAction copyAction;

    /**
     * Action taht cuts whatever is selected
     */
    private final CutAction cutAction;

    /**
     * Action that pastes the current selection
     */
    private final PasteAction pasteAction;

    /**
     * Action that deletes the current selection
     */
    private final DeleteAction deleteAction;

    /**
     * Action that undoes the last edit
     */
    private final UndoAction undoAction;

    /**
     * Action that redoes the previous undo
     */
    private final RedoAction redoAction;

    private Map<GuiAction, Boolean> editEnabledStatus = new HashMap<>();

    /**
     *
     * Creates actions for editing the petri net
     *
     * @param controller PIPE application controller
     */
    public ComponentEditorManager(PipeApplicationController controller) {
        copyAction = new CopyAction(controller);
        pasteAction = new PasteAction(controller);
        cutAction = new CutAction(controller);
        deleteAction = new DeleteAction(controller);
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        storeEnabledStatus();
    }

    /**
     *
     * @return inorder iterable of the actions this class is responsible for managing
     */
    public Iterable<GuiAction> getActions() {
        editEnabledStatus.keySet();
        return Arrays.asList(cutAction, copyAction, pasteAction, deleteAction, undoAction, redoAction);
    }

    /**
     * Disables actions and stores their current state
     * ready for re-enabling
     */
    public void disableActions() {
        storeEnabledStatus();
        for (GuiAction action : getActions()) {
            action.setEnabled(false);
        }
    }

    /**
     * Restores actions to their previous states
     */
    public void reEnableActions() {
        for (GuiAction action : getActions()) {
            action.setEnabled(editEnabledStatus.get(action));
        }
    }

    /**
     * Stores the current actions enabled status in the map
     */
    private void storeEnabledStatus() {
        for (GuiAction action : getActions()) {
            editEnabledStatus.put(action, action.isEnabled());
        }
    }

    public void setUndoActionEnabled(boolean flag) {
        undoAction.setEnabled(flag);
    }

    public void setRedoActionEnabled(boolean flag) {
        redoAction.setEnabled(flag);
    }
}
