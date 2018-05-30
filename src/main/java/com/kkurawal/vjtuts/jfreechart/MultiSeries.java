package com.kkurawal.vjtuts.jfreechart;

import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by konglie on 30/05/18.
 * for
 * https://www.facebook.com/groups/kurungkurawal/permalink/598339447200122/
 */
public class MultiSeries {
	static String chartTitle = "Multiple Series (Random Data)";

	public static void main(String[] args){


		final JFrame frame = new JFrame(chartTitle);
		frame.getContentPane().setLayout(new MigLayout("fillx"));

		JButton btnReload = new JButton("Reload Random Data");
		frame.getContentPane().add(btnReload, "top, center, wrap");

		final JPanel body = new JPanel(new MigLayout("fill", "grow, center")){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				int children = getComponentCount();
				if(children > 0){
					return;
				}

				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHint(
						RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2.setColor(Color.BLACK);
				g2.drawString("Drawing Chart ...", 15, 10);
			}
		};
		frame.getContentPane().add(body, "center, grow, wrap");

		frame.setMinimumSize(new Dimension(720, 480));
		frame.pack();
		frame.setTitle("Line chart");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				body.removeAll();
				body.revalidate();
				drawChartTo(body);
			}
		});

		drawChartTo(body);

	}

	static void drawChartTo(final JPanel panel){
//		panel.add(new JButton("T"));
//		if(1<2){return;}
		new Thread(new Runnable() {
			public void run() {
				final ChartPanel chartPanel = drawChart();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						panel.add(chartPanel);
						panel.repaint();
						panel.revalidate();
					}
				});
			}
		}).start();
	}

	static ChartPanel drawChart(){
		XYSeriesCollection dataset = new XYSeriesCollection();

		int numSeries = 3;
		for(int i = 0; i < numSeries; i++){
			dataset.addSeries(createSeries("Series " + i, 30));
		}

		JFreeChart chart = ChartFactory.createXYLineChart(
				chartTitle,
				"Random X",
				"Random Y",
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
		);

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(new TextTitle(chartTitle,
						new Font("Arial", java.awt.Font.BOLD, 18)
				)
		);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);

		return chartPanel;
	}

	static XYSeries createSeries(String name, int numData){
		XYSeries series = new XYSeries(name);
		for(int i = 0; i < numData; i++){
			series.add(random(10, 50), random(51,100));
		}
		return series;
	}

	static double random(int min, int max){
		return (Math.random() * (max - min)) + min;
	}
}
