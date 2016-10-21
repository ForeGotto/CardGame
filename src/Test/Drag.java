package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by hcq on 2016/10/8.
 */
public class Drag extends JComponent implements MouseListener, MouseMotionListener, ActionListener{
    protected int x;
    protected int y;
    protected boolean isPressed;

    public Drag() {
        super();
        x = y = 200;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public boolean includes(int x, int y) {
    if ((x <= this.x + 100) && (x >= this.x) && (y <= this.y + 200) && (y >= this.y)) {
        return true;
    }
    return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.yellow);
        g2d.fillRect(x,y,100,200);
        g2d.setColor(Color.gray);
        g2d.drawString("upsite", 20, 20);
        g.drawRect(x, y, 100,200);
        g2d.scale(1.0, -1.0);
        g2d.drawString("downsite",20,20);
//        System.out.println("here it paint");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getXOnScreen();
        y = e.getYOnScreen();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        int cx = e.getXOnScreen();
        int cy = e.getYOnScreen();
        System.out.println("("+x+","+y+")");
        if ((includes(cx, cy))) {
            x = cx;
            y = cy;
            repaint();
        }
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        repaint();
    }

    public static void main(String [] args) {
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 1920, 1080);
        Drag drag = new Drag();
        drag.setSize(100,200);
        frame.setGlassPane(drag);
        frame.setVisible(true);
        frame.getGlassPane().setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
