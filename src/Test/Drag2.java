package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *  拖拽的实现
 *  主要由监听鼠标事件实现
 *  注意 为了保证光标相对于组件的坐标不变，
 *  在改变组件位置的时候应该考虑  光标在初始时相对于组件的坐标
 *
 *
 * @x used to get location of mouse cursor,set in @mousePressed(mouseEvent e),
 * used in @mouseDragged(mouseEvent e)
 * @y used to get location of mouse cursor,set in @mousePressed(mouseEvent e),
 * used in @mouseDragged(mouseEvent e)
 * Created by hcq on 2016/10/8.
 */
public class Drag2 extends JFrame implements MouseMotionListener, MouseListener {
    JButton button;
    int x;
    int y;

    /**
     * Constructs a new frame that is initially invisible.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *                           returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public Drag2() {
        super();
        button = new JButton("ToDrag");
        setBounds(0, 0, 1920, 1080);
        setLayout(null);
        Container container = getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.add(button);
        button.setBounds(500, 600, 200, 300);
        button.setVisible(true);
        button.addMouseMotionListener(this);
        button.addMouseListener(this);
    }

    public static void main(String [] args) {
        (new Drag2()).setVisible(true);

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
        x = e.getX();
        y = e.getY();
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Point p = e.getLocationOnScreen();
        System.out.printf("current component: %s", getComponentAt(p).getClass());
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
            Point p = e.getPoint();
//            System.out.printf("dragPoint: %s", p);
            int cx = (int) p.getX();
            int cy = (int) p.getY();
//            System.out.println(cx+" "+cy);
            Point point = SwingUtilities.convertPoint(button, p, this);
//            System.out.println("before translate:"+point);
            point.translate(-x, -y);
            button.setLocation(point);
//            System.out.println("after translate"+point);
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
