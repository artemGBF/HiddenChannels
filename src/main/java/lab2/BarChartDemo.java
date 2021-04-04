package lab2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BarChartDemo extends ApplicationFrame {
    private static final long serialVersionUID = 1L;

    static {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    public BarChartDemo(String title, Map<Double, Integer> data) {
        super(title);
        setContentPane(createDemoPanel(data));
    }

    private JPanel createDemoPanel(Map<Double, Integer> data) {
        CategoryDataset dataset = createDataset(data);
        JFreeChart chart = createChart(dataset);
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        return chartPanel;
    }

    private CategoryDataset createDataset(Map<Double, Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<Double, Integer> entry : data.entrySet()) {
            Integer value = entry.getValue();
            if (value == null) {
                value = 0;
            }
            dataset.addValue(value, "", entry.getKey());
        }
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                null,
                null,                    // x-axis label
                "Время",                 // y-axis label
                dataset);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }
}
