import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class XYLogarithmicChart_AWT extends JFrame {

    public static ChartPanel chartPanel;

    String xAxisLabelName(int draw, String para)
    {
        if(draw > Reader.units.size()-1) return para;
        para = para + "["+Reader.units.get(draw)+"]";
        return para;
    }
    public XYLogarithmicChart_AWT(int draw, String para) {
        super("");
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "" ,
                "Depth[M]",
                xAxisLabelName(draw, para),
                createDataset(draw, para) ,
                PlotOrientation.HORIZONTAL ,
                true , true , false);

        chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new Dimension( 500 , 2000 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        LogarithmicAxis yAxis = new LogarithmicAxis(para);
        plot.setRangeAxis(yAxis);
        plot.getDomainAxis().setLowerBound(Reader.values.get(0));
        plot.getDomainAxis().setUpperBound(Reader.values.get(Reader.values.size()-Reader.parameters.size()));
        chartPanel.getChart().getXYPlot().getDomainAxis().setInverted(true); // odwrocenie osi OX

        double min = Reader.values.get(0);
        double max = Double.NEGATIVE_INFINITY;
        for (int i = draw; i < Reader.values.size(); i+=(Reader.parameters.size()))
        {
            if(Reader.values.get(i) == -999.000 || Reader.values.get(i) == 999.000) continue;
            if(Reader.values.get(i) < min) min = Reader.values.get(i);
            else if(Reader.values.get(i) > max) max = Reader.values.get(i);
        }

        plot.getRangeAxis().setLowerBound(min);
        plot.getRangeAxis().setUpperBound(max);
        plot.getRenderer().setSeriesPaint(0, new Color(0xFF, 0x00, 0xFF));
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(0.5f));
    }

    private XYDataset createDataset( int draw, String para) {
        final XYSeries seria = new XYSeries( para );
        for (int i = draw; i < Reader.values.size(); i+=(Reader.parameters.size()))
        {
            if(Reader.values.get(i) == -999.000 || Reader.values.get(i) == 999.000) continue;
            seria.add(Reader.values.get(i-draw), Reader.values.get(i));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( seria );
        return dataset;
    }

}