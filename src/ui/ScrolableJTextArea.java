package ui;

import javax.swing.*;
import java.awt.*;

public class ScrolableJTextArea extends JPanel {
    private boolean DEBUG = false;

    public JTextArea textArea;

    public void append( String line ) {
        textArea.append ( line );
    }

    public ScrolableJTextArea ( String line) {
        super(new GridLayout(1,0));


        textArea = new JTextArea();
        textArea.setSize(200,200);
        textArea.setLineWrap(false);
        textArea.setEditable(false);
        textArea.append ( line );
        textArea.setVisible(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane ( textArea );
        //Add the scroll pane to this panel.
        add ( scrollPane );
    }

}

