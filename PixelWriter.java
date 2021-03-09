import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;
public class PixelWriter implements KeyListener,MouseListener{
    private JFrame mainFrame;
    private BufferedImage buffer;
    private GoLProcessor processor;
    private Point cursor;
    private int xMax;
    private int yMax;
    public PixelWriter(int xRes, int yRes, GoLProcessor main) {
        mainFrame = new JFrame("Game of Life");
        processor = main;
        cursor = new Point(0, 0);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addKeyListener(this);
        mainFrame.addMouseListener(this);
        buffer = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_ARGB);
	xMax = xRes;
	yMax = yRes;
    }
    public void run() {
        mainFrame.add(new JLabel(new ImageIcon(buffer)));
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
    public void writePixel(int xCor, int yCor, int r, int g, int b) {
        buffer.setRGB(xCor, yCor, new Color(r,g,b).getRGB());
    }
    public void repaint() {
        buffer.setRGB(cursor.x, cursor.y, new Color(0,255,0).getRGB());
        mainFrame.repaint();
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (cursor.y != 0) cursor.setLocation(cursor.x, cursor.y - 1);
            buffer.setRGB(cursor.x, cursor.y, new Color(0,255,0).getRGB());
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (cursor.y != yMax - 1) cursor.setLocation(cursor.x, cursor.y + 1);
            buffer.setRGB(cursor.x, cursor.y, new Color(0,255,0).getRGB());
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (cursor.x != 0) cursor.setLocation(cursor.x - 1, cursor.y);
            buffer.setRGB(cursor.x, cursor.y, new Color(0,255,0).getRGB());
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (cursor.x != xMax - 1) cursor.setLocation(cursor.x + 1, cursor.y);
	    buffer.setRGB(cursor.x, cursor.y, new Color(0,255,0).getRGB());
        }
    }
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 's') {
            processor.snap();
            //System.out.println(e.getKeyChar());
        } else if (e.getKeyChar() == 'e') {
            System.exit(0);
        } else if (e.getKeyChar() == 'r') {
    		try {
			String interString = JOptionPane.showInputDialog("Input random interval 0-1 (higher = less life)");
			double interval;
			if (interString == null || interString.equals("")) {
			    interval = .5;
			} else {
				 interval = Double.parseDouble(interString);
			}		
			processor.randomize(interval);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        } else if (e.getKeyChar() == ' ') {
            if (processor.getState()) {
                processor.pause(true);
            } else processor.pause(false);
        } else if (e.getKeyChar() == 't') {
            processor.singleTick();
        } else if (e.getKeyChar() == 'p') {
            processor.togglePixel(cursor.x, cursor.y);
        }

    }
    public void keyReleased(KeyEvent e) {

    }
    public void mouseEntered(MouseEvent e) {
        
    }
    public void mouseExited(MouseEvent e) {
        
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
        cursor = new Point(e.getX()-5,e.getY() - 30);
        //System.out.println(e.getX() + " " + e.getY());
    }
    public void mouseReleased(MouseEvent e) {
        
    }
}