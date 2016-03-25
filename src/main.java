import ui.mainForm;

import javax.swing.*;

/**
 * Created by orb1t_ua on 17.03.16.
 */
public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                mainForm gui = new mainForm();
            }
        });
    }
}