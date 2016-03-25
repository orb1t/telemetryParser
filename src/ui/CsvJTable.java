package ui;

import util.ReadCVS;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CsvJTable extends JPanel {
    private ReadCVS _obj = null;

    public CsvJTable ( ReadCVS obj ) {
        super(new GridLayout(1,0));

        _obj = ReadCVS.getInstance();

        TableModel dataModel = new AbstractTableModel() {

            public String getColumnName(int col) {
                return ReadCVS.getHeaderItems().get(col);
            }

            public int getColumnCount() {
                return ReadCVS.getColumnCount();
            }

            public int getRowCount() {
                return ReadCVS.getRowsCount();
            }

            public Object getValueAt(int row, int col) {
                return ReadCVS.getData().get(row).get(col);
            }
        };

        JTable table = new JTable(dataModel);
        table.setPreferredScrollableViewportSize(new Dimension(1200, 70));
        table.setFillsViewportHeight(true);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        //Add the scroll pane to this panel.
        add(scrollPane);
    }

}