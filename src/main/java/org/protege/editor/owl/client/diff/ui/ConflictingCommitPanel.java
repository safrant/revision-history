package org.protege.editor.owl.client.diff.ui;

import static com.google.common.base.Preconditions.checkNotNull;

import org.protege.editor.owl.OWLEditorKit;

import org.semanticweb.owlapi.model.OWLOntologyChange;

import java.awt.BorderLayout;
import java.util.Date;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ConflictingCommitPanel extends JPanel {
    private final Set<OWLOntologyChange> changes;
    private final Date commitDate;
    private final String commitAuthor;
    private final String commitComment;
    private final int conflictNr;
    private OWLEditorKit editorKit;
    private JList<OWLOntologyChange> changeList = new JList<>();

    /**
     * Constructor
     *
     * @param editorKit OWL editor kit
     * @param changes   Set of ontology changes
     * @param commitDate   Commit date
     * @param commitAuthor Commit author
     * @param commitComment    Commit comment
     */
    public ConflictingCommitPanel(OWLEditorKit editorKit, Set<OWLOntologyChange> changes, Date commitDate, String commitAuthor, String commitComment, int conflictNr) {
        this.editorKit = checkNotNull(editorKit);
        this.changes = checkNotNull(changes);
        this.commitDate = checkNotNull(commitDate);
        this.commitAuthor = checkNotNull(commitAuthor);
        this.commitComment = checkNotNull(commitComment);
        this.conflictNr = checkNotNull(conflictNr);
        configureList();
        setLayout(new BorderLayout());
        setBackground(GuiUtils.WHITE_BACKGROUND);
        addConflictDetails();
    }

    private void configureList() {
        changeList.setCellRenderer(new ChangeListCellRenderer(editorKit));
        changeList.setSelectionModel(new DisabledListItemSelectionModel());
        changeList.setFixedCellHeight(18);
        changeList.setFocusable(false);
    }

    private void addConflictDetails() {
        int fontSize = getFont().getSize();
        String dateStr = GuiUtils.getShortenedFormattedDate(commitDate);
        String headerText = "<html>Conflicting commit " + conflictNr + " by <strong>" + commitAuthor.toString() + "</strong> on <strong>" + dateStr + "</strong><br>" + // TODO: To review later
                "<p style=\"padding-top:3;color:gray;font-size:" + (fontSize-1) + ";\"><nobr>" + commitComment + "</nobr></p></html>";

        JLabel headerLbl = new JLabel(headerText, GuiUtils.getIcon(GuiUtils.WARNING_ICON_FILENAME, 33, 33), SwingConstants.LEFT);
        headerLbl.setBorder(new EmptyBorder(0, 0, 8, 0));
        headerLbl.setIconTextGap(8);
        add(headerLbl, BorderLayout.NORTH);

        changeList.setListData(changes.toArray(new OWLOntologyChange[changes.size()]));
        add(changeList, BorderLayout.CENTER);
    }
}