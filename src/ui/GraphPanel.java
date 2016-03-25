package ui;


import util.ReadCVS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel implements ActionListener {

    private int width = 800;
    private int heigth = 400;
    private int padding = 32;
    private int labelPadding = 32;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int pointHeight = 4;
    private int numberYDivisions = 10;

    private Double[] x_axis_plot_data;  // !! must be a List...
    private Double[] y_axis_plot_data;
    private ReadCVS in_data;


    // create an empty combo box with items of type String
    JComboBox<String> y_plane_combo = new JComboBox<String>();

    ReadCVS obj;
    private Double maxScore;

    public void GraphPanel(ReadCVS obj) {
        this.obj = obj;

    }

    public void getPlotData ( int x_plane , int y_plane ) {
        int maxDataPoints = ReadCVS.getRowsCount();//csvRowsCount;
        //Double
                maxScore = Double.valueOf(0);

        y_axis_plot_data = new Double [ maxDataPoints ];
        x_axis_plot_data = new Double [ maxDataPoints ];

        for (int i = 0; i < maxDataPoints - 1; i++) {
            //in_data.

            String tmp = ReadCVS.getData().get(i).get(y_plane);//in_data.csvData [ i ] [ x_plane ];
            y_axis_plot_data[ i ] = Double.valueOf( tmp );

            tmp = ReadCVS.getData().get(i).get(x_plane);//in_data.csvData [ i ] [ y_plane ];
            x_axis_plot_data [ i ] =  Double.valueOf( tmp );

            if ( maxScore < y_axis_plot_data[ i ] ) {
                maxScore = y_axis_plot_data[ i ];
            }
        }

        //invalidate();
        //this.repaint();
    }


    public GraphPanel ( ReadCVS data ) {
        this.in_data = data;

        // add items to the combo box
        for (int i = 0; i < ReadCVS.getColumnCount(); i++){
            y_plane_combo.addItem(ReadCVS.getHeaderItems().get(i));
        }
        y_plane_combo.setSelectedIndex ( 0 );
        y_plane_combo.addActionListener ( this );

        getPlotData ( 0, 0 );

        add(y_plane_combo, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Double xScale = ( Double.valueOf ( getWidth() ) - ( 2 * padding ) - labelPadding ) / ( y_axis_plot_data.length - 1 );
        Double yScale = ( Double.valueOf ( getHeight ( ) ) - 2 * padding - labelPadding ) / Math.round( getMaxScore ( ) - getMinScore ( ) );

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < x_axis_plot_data.length - 1; i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) (Math.round( getMaxScore() - y_axis_plot_data[ i ] ) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) { //TODO: make numberYDivisions user configurable via ui
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;

                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = Double.toString ( i * maxScore/*y_axis_plot_data.length*/ / numberYDivisions );//Double.toString ( y_axis_plot_data [ i ] );//String.format ( "%1$,.2d", ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "" );
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        //
        int x0 = 0;
        int y0 = 0;
        int x1 = 0;
        int y1 = 0;
        for (int i = 0; i < x_axis_plot_data.length + 1; i++) {
                x0 = i * (getWidth() - padding * 2 - labelPadding) / (x_axis_plot_data.length - 1) + padding + labelPadding;
                x1 = x0;
                y0 = getHeight() - padding - labelPadding;
                y1 = y0 - pointWidth;
                if ((i % ((int) ((x_axis_plot_data.length / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = Double.toString ( i );//x_axis_plot_data [ i ] );// + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                g2.drawLine(x0, y0, x1, y1);
            }
        }
        g2.drawString(Double.toString ( x_axis_plot_data.length - 1), x0 - g2.getFontMetrics().stringWidth( Double.toString ( x_axis_plot_data.length - 1)) / 2, y0 + g2.getFontMetrics().getHeight() + 3);

        // create x and y axes 
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            g2.setColor(lineColor);
            g2.setStroke(GRAPH_STROKE);
            int xx1 = graphPoints.get(i).x;
            int yy1 = graphPoints.get(i).y;
            int xx2 = graphPoints.get(i + 1).x;
            int yy2 = graphPoints.get(i + 1).y;
            g2.drawLine(xx1, yy1, xx2, yy2);

            g2.setStroke(oldStroke);
            g2.setColor(pointColor);
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    private Double getMinScore() {
        Double minScore = Double.MAX_VALUE;
        for (Double score : y_axis_plot_data) {
            if ( score != null ) minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private Double getMaxScore() {
        Double maxScore = Double.MIN_VALUE;
        for (Double score : y_axis_plot_data) {
            if ( score != null ) maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

    public void setY_axis_plot_data(Double[] y_axis_plot_data) {
        this.y_axis_plot_data = y_axis_plot_data;
        invalidate();
        this.repaint();
    }

    public Double[] getY_axis_plot_data() {
        return y_axis_plot_data;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        getPlotData ( y_plane_combo.getSelectedIndex(), y_plane_combo.getSelectedIndex() );

        invalidate();
        this.repaint();
    }
}