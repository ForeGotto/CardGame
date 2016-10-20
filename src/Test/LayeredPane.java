package Test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static java.lang.Thread.sleep;

/**
 * 测试JLayeredPane的特性
 * 首先必须用add(Compoent c)添加组件，此时组件被添加到DEFAULT_LAYER
 * 接着可以用setLayer(Component c, int layer)设置组件的层
 *
 *
 * Created by hcq on 2016/10/8.
 */
public class LayeredPane extends JFrame implements MouseListener, MouseMotionListener {
    static JButton button;
    static JLayeredPane layer;
    int x;
    int y;


    public LayeredPane() {
        super();
        setBounds(0,0,1920,1080);
//        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layer = getLayeredPane();
        button = new JButton("todrag");
        layer.add(button);
        System.out.println(layer.getLayer(button));
        layer.setLayer(button, JLayeredPane.DEFAULT_LAYER);
        System.out.println(layer.getLayer(button));
        button.setBounds(500,300,200,300);
        button.setVisible(true);
        button.addMouseListener(this);
        button.addMouseMotionListener(this);
    }

    public static void main(String [] args) {
        (new LayeredPane()).setVisible(true
        );
        layer.setLayer(button, JLayeredPane.DRAG_LAYER);
        System.out.println(layer.getLayer(button));
        JButton bb = new JButton("fixed");
        layer.add(bb);
        System.out.println(layer.getLayer(bb));
        layer.setLayer(bb, JLayeredPane.MODAL_LAYER);
        System.out.println(layer.getLayer(bb));
        layer.setLayer(bb, JLayeredPane.DEFAULT_LAYER);
        bb.setBounds(800, 400, 200, 300);
        bb.setVisible(true);
        bb.setOpaque(false);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(button)) {
            x = e.getX();
            y = e.getY();
            layer.setLayer(button, JLayeredPane.DRAG_LAYER);
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override

    public void mouseReleased(MouseEvent e) {
        if (e.getSource().equals(button)) {
            layer.setLayer(button, JLayeredPane.DEFAULT_LAYER+1);
        }
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
        if (e.getSource().equals(button)) {
            Point p = SwingUtilities.convertPoint(button, e.getPoint(), this);
            button.setLocation(new Point(p.x-x, p.y-y));
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

    }
}




