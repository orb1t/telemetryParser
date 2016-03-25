package ui;

import util.ReadCVS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by orb1t_ua on 17.03.16.
 */
public class mainForm extends JFrame {
    private JPanel PanelMain;
    private JComboBox x_plane_combo;
    //private JComboBox y_plane_combo;
    private JTextField txCsvPath;
    private JButton btLoadCsv;
    private JTabbedPane tabPanel;

    private JFileChooser fc = new JFileChooser();
    private ScrolableJTextArea txCsv;
    private CsvJTable tblCsv;
    private GraphPanel gfxCsv;

    ReadCVS obj = null;
    private JPanel mainPanel;

    public mainForm(){
        super("orPayload Logs Reader v.0.0.8");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        x_plane_combo = new JComboBox();
        //y_plane_combo = new JComboBox();


        btLoadCsv.addActionListener ( (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pathTmp = "";
                if ( txCsvPath.getText().length() == 0 ) {

                    int returnVal = fc.showOpenDialog(mainForm.this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        //obj = ReadCVS.getInstance();//new ReadCVS(file.getName());


                        pathTmp = file.getAbsolutePath();// + file.file + file.getName();
                        txCsvPath.setText(pathTmp);
                    }
                } else {

                    //obj = new ReadCVS(txCsvPath.getText());
                    pathTmp = txCsvPath.getText();


                }
                obj = ReadCVS.getInstance();
                obj.parse(pathTmp);
                //obj.dumpCSV();



                for (String st : ReadCVS.getHeaderItems()){
                    x_plane_combo.addItem(st);
                    //y_plane_combo.addItem(st);
                };

                tabPanel.setSize( 875, 500 );

                txCsv = new ScrolableJTextArea ( obj.toString() );
                //obj.fillTextArea(txCsv);
                txCsv.setSize( 800, 400 );
                txCsv.setOpaque(true);
                tabPanel.add ( "CSV Text", txCsv );
                txCsv.setVisible(true);


                tblCsv = new CsvJTable ( obj );
                tblCsv.setSize( 800, 400 );
                tblCsv.setOpaque(true);
                tabPanel.add ( "CSV Table", tblCsv );
                tblCsv.setVisible(true);

                gfxCsv = new GraphPanel ( obj );
                gfxCsv.setOpaque ( true );
                tabPanel.add ( "CSV GFX", gfxCsv );
                gfxCsv.setVisible(true);
            }
        }
            ));

        setContentPane ( PanelMain );

        pack();
        setVisible ( true );
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
