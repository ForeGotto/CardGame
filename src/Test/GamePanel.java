package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.util.List;

/**
 * Created by hcq on 2016/10/14.
 */
public class GamePanel extends JFrame implements MouseListener, MouseMotionListener {
    public static GameInit gameInit;
    static {
        gameInit = new GameInit();
    }
    FrameCard[] frameCards = new FrameCard[11];
    JLayeredPane layeredPane;
    Stack<Card> movingCardList = new Stack<>();
    CardPile fromThisPile = null;
    CardPile toThisPile = null;
    JButton reInit;
    Point pressedPoint = null;
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
    public GamePanel() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setBounds(0,0,1920,1080);
        layeredPane = getLayeredPane();
        Container contentPane = getContentPane();
        setVisible(true);

        for (Card c : GameInit.cards) {
            layeredPane.add(c);
            c.setSize(MyTools.cardSizeX,MyTools.cardSizeY);
            c.addMouseListener(this);
            c.addMouseMotionListener(this);
            /**
             * 此处本来为解决首张牌始终出现在左上角而写
             * c.setLocation(1920,1080);
             */
            c.setLocation(1920,1080);
        }
        /**
         * 此处本来为解决首张牌始终出现在左上角而写
         * c.setLocation(1920,1080);
         */
        GameInit.cards[0].setLocation(1920,1080);
        for (int i=0; i<11; i++) {
            frameCards[i] = new FrameCard();
            add(frameCards[i]);
            layeredPane.setLayer(frameCards[i], 0);
            frameCards[i].setSize(MyTools.cardSizeX, MyTools.cardSizeY);
            if (i < 4) {
                frameCards[i].setLocation(MyTools.topPileFirstX+MyTools.pileHorizontalGap*(i)
                        ,MyTools.topPileFirstY);
            } else {
                frameCards[i].setLocation(MyTools.movingPileFirstX+MyTools.pileHorizontalGap*(i-4)
                        ,MyTools.movingPileFirstY);
            }
        }
        addMouseListener(this);
        display();
        reInit = new JButton("reinit");
        add(reInit);
        reInit.addMouseListener(this);
        reInit.setBounds(20,20,30,30);
        contentPane.add(reInit);
    }

    public void display() {

        for (int i=1; i<8; i++) {
            CardPile tmpPile = GameInit.piles[i];
            for (int j=0; j < tmpPile.getSize(); j++) {
                layeredPane.setLayer(tmpPile.get(j), j);
                System.out.println(tmpPile.get(j).spoint+" layer: "+layeredPane.getLayer(tmpPile.get(j)));
            }
        }
        for (MovingPile m : GameInit.movingPile) {
            m.peek().setFaceUp(true);
        }
        for (Card c : GameInit.dealerPile.card) {
            c.setFaceUp(false);
        }
        GameInit.showContent();
    }

    public void refresh() {
        GameInit.initialize();
        display();
    }


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
//        int x = e.getX();
//        int y = e.getY();
//        System.out.println(x+" "+y);
        DealerPile dealer = GameInit.dealerPile;
        int dealerSize = dealer.getSize();
        if (e.getSource() instanceof Card &&
                ((Card)e.getSource()).getPile().equals(GameInit.dealerPile) &&
                dealerSize>0) {
//            System.out.println("change cards");
            if (dealerSize > 1) {
                if (dealer.currentShow > 0) {
                    if (dealer.currentShow < dealerSize) {
                        layeredPane.setLayer(dealer.get(dealer.currentShow), 1);
                    }
                    dealer.currentShow--;
                    layeredPane.setLayer(dealer.get(dealer.currentShow), 2);
                } else {
                    dealer.currentShow = dealerSize;
                    layeredPane.setLayer(dealer.get(0),1);
                }
            } else {
                if (dealer.currentShow == dealerSize) {
                    dealer.currentShow--;
                } else {
                    dealer.currentShow = dealerSize;
                }
                layeredPane.setLayer(dealer.get(dealer.currentShow),2);
            }
            dealer.show();
        } else if (e.getSource().equals(reInit)) {
            refresh();
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
//        int x = e.getX();
//        int y = e.getY();
        if (e.getSource() instanceof Card && ((Card) e.getSource()).isFaceUp()) {
            Card c = (Card) e.getSource();
//            if (!(c.getPile() instanceof DealerPile)) {
//            }
            pressedPoint = SwingUtilities.convertPoint(c, e.getPoint(),this);
            System.out.printf("point:%s pile=%s\n", pressedPoint, c.getPile());
        }

//        Card c = (Card) e.getSource();
//        if (!(c.getPile() instanceof DealerPile)) {
//        }
//        pressedPoint = SwingUtilities.convertPoint(c, e.getPoint(),this);
//        System.out.printf("point:%s pile=%s\n", pressedPoint, c.getPile());

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
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
        System.out.printf("dragged point:%s\n",e.getPoint());
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

    public static void main(String[] args) {
        new GamePanel();
    }
}
