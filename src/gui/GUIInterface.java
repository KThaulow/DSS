/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*; 
import Utils.Settings;
import java.awt.Checkbox;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Fuglsang
 */
public class GUIInterface extends JPanel {
    private JFrame frame = new JFrame();
    private JSplitPane  splitPaneV;
    private List<GUIComponentAirport> fillAirports;
    private List<GUIComponentAircraft> fillAircrafts; 
    private Grid grid; 
         
    
    public GUIInterface() {   
        fillAirports = new ArrayList<>(Settings.NUMBER_OF_AIRPORT_AGENTS);
        fillAircrafts = new ArrayList<>(Settings.NUMBER_OF_AIRCRAFT_AGENTS); 
        grid = new Grid(810, 560, fillAirports, fillAircrafts);
        SettingsPanel settingsPanel = new SettingsPanel(290, 560, grid); 
        
//        grid.fillAirport(new GUIComponentAirport(new Point(78, 1), "A"));
//        grid.fillAirport(new GUIComponentAirport(new Point(63, 27), "B"));
//        grid.fillAirport(new GUIComponentAirport(new Point(29, 48), "C"));
//        grid.fillAirport(new GUIComponentAirport(new Point(2, 5), "D"));
//        grid.fillAirport(new GUIComponentAirport(new Point(15, 15), "E"));
//
//        grid.fillAircraft(new GUIComponentAircraft(new Point(3, 5), 150, 980));
//        grid.fillAircraft(new GUIComponentAircraft(new Point(3, 5), 300, 1050));
//        grid.fillAircraft(new GUIComponentAircraft(new Point(10, 20), 50, 700));
//        grid.fillAircraft(new GUIComponentAircraft(new Point(40, 24), 150, 900));
//        grid.fillAircraft(new GUIComponentAircraft(new Point(64, 17), 200, 900));
        
        splitPaneV = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, grid, settingsPanel);        
        splitPaneV.setDividerLocation(815);
        
        frame.setSize(1100, 560);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(splitPaneV); 
        frame.setVisible(true); 
    }
    
    public void drawAirport(double x, double y, String name) {
        GUIComponentAirport airport = new GUIComponentAirport(new Point2D.Double(x, y), name); 
        fillAirports.add(airport);
        grid.repaint();
    }

    public void fillAircraft(GUIComponentAircraft aircraft) {
        fillAircrafts.add(aircraft); 
        grid.repaint();
    }
    
    private static class SettingsPanel extends JPanel implements ItemListener {
        private Grid grid; 
        private JCheckBox gridCheckBox; 
        
        public SettingsPanel(int width, int height, Grid grid) {
            this.grid = grid; 
            setSize(width, height);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            JLabel header = new JLabel("Settings"); 
            header.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(header); 
            
            JLabel filledSeatsCostWeight = new JLabel("Switch grid on/off"); 
            filledSeatsCostWeight.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(filledSeatsCostWeight); 
            
            gridCheckBox = new JCheckBox("Grid"); 
            gridCheckBox.setSelected(true);
            gridCheckBox.addItemListener(this);
            add(gridCheckBox); 
            
//            JSlider filledSeadCostSlider = new JSlider(-50, 50); 
//            filledSeadCostSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
//            filledSeadCostSlider.setMajorTickSpacing(10);            
//            filledSeadCostSlider.setMinorTickSpacing(5);
//            filledSeadCostSlider.setPaintTicks(true);
//            filledSeadCostSlider.setPaintLabels(true);
//            add(filledSeadCostSlider); 
            
        }
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            Object source = e.getItemSelectable();
            
            if(source == gridCheckBox) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    grid.drawTheGrid(false);
                } else {
                    grid.drawTheGrid(true);
                }
                grid.repaint(); 
            }
        }
    }
    
    private static class Grid extends JPanel { 
        private List<GUIComponentAirport> fillAirports;
        private List<GUIComponentAircraft> fillAircrafts;
        private boolean drawGrid = true; 
         
         public Grid(int width, int height, List<GUIComponentAirport> airports, List<GUIComponentAircraft> aircrafts) {
            setSize(width, height); 
            fillAircrafts = aircrafts; 
            fillAirports = airports; 
            repaint(); 
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g; 
            
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRect(10, 10, 800, 500);

            if(drawGrid) {
                for (int i = 10; i <= 800; i += 10) {
                    g2.drawLine(i, 10, i, 510);
                }

                for (int i = 10; i <= 500; i += 10) {
                    g2.drawLine(10, i, 810, i);
                }
            }
            
            for (GUIComponentAirport fillAirport : fillAirports) {
                float cellX = (float) (10 + (fillAirport.airportCoordinates().x * 10));
                float cellY = (float) (10 + (fillAirport.airportCoordinates().y * 10));
                g2.setPaint(new Color(0, 0, 0));
                g2.draw(new Rectangle2D.Double(cellX, cellY, 10.0, 10.0)); //fillRect(cellX, cellY, 10, 10);
                
                g2.setColor(Color.BLACK);
                g2.drawString(fillAirport.airportName(), cellX, cellY);
            }
            
            for (GUIComponentAircraft aircraft : fillAircrafts) {
                int cellX = 10 + (aircraft.airCraftCoordinates().x * 10);
                int cellY = 10 + (aircraft.airCraftCoordinates().y * 10);
                g2.setColor(Color.BLUE);
                g2.fillRect(cellX, cellY, 10, 10);
                
                g2.setColor(Color.BLACK);
                g2.drawString("C: " + aircraft.capacity() + " S: " + aircraft.speed(), cellX, cellY);
                
            }
            
        }
        
        public void drawTheGrid(boolean drawTheGrid) {
            drawGrid = drawTheGrid;             
        }
    }

 }

