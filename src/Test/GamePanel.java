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
//    FrameCard[] frameCards = new FrameCard[11];
    FrameCard[] frameCards = new FrameCard[13];
    JLayeredPane layeredPane;
    Stack<Card> movingCardList = new Stack<>();
    CardPile fromThisPile = null;
    CardPile toThisPile = null;
    JButton reInit;
//    Point pressedPoint = null;
    Stack<Point> imigratePoint = new Stack<>();
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
        layeredPane.setDoubleBuffered(true);
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
//        GameInit.cards[0].setLocation(1920,1080);
//        for (int i=0; i<11; i++) {
//            frameCards[i] = new FrameCard();
//            add(frameCards[i]);
//            layeredPane.setLayer(frameCards[i], 0);
//            frameCards[i].setSize(MyTools.frameSizeX, MyTools.frameSizeY);
//            if (i < 4) {
//                frameCards[i].setLocation(MyTools.topPileFirstX+MyTools.pileHorizontalGap*(i)
//                        ,MyTools.topPileFirstY);
//            } else {
//                frameCards[i].setLocation(MyTools.movingPileFirstX+MyTools.pileHorizontalGap*(i-4)
//                        ,MyTools.movingPileFirstY);
//            }
//        }
        for (int i=0; i<13; i++) {
            frameCards[i] = new FrameCard();
            add(frameCards[i]);
            layeredPane.setLayer(frameCards[i], 0);
            if (i == 0) {
                frameCards[i].setSize(MyTools.frameSizeX, MyTools.cardSizeY);
                frameCards[i].setLocation(MyTools.dealerPileFirstX+MyTools.pileHorizontalGap,MyTools.dealerPileFirstY);
            }
            if (i > 0 && i < 8) {
                frameCards[i].setSize(MyTools.frameSizeX, MyTools.frameSizeY);
                frameCards[i].setLocation(MyTools.movingPileFirstX+MyTools.pileHorizontalGap*(i-1)
                        ,MyTools.movingPileFirstY);
            }
            if(i > 7 && i < 12){
                frameCards[i].setSize(MyTools.frameSizeX, MyTools.cardSizeY);
                frameCards[i].setLocation(MyTools.topPileFirstX+MyTools.pileHorizontalGap*(i-8)
                        ,MyTools.topPileFirstY);
            }
            if (i == 12) {
                frameCards[i].setSize(MyTools.frameSizeX,MyTools.cardSizeY);
                frameCards[i].setLocation(MyTools.dealerPileFirstX, MyTools.dealerPileFirstY);
            }
        }
        addMouseListener(this);
        display();
        reInit = new JButton("重新开始");
        add(reInit);
        reInit.addMouseListener(this);
        reInit.setBounds(100,900,100,30);
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
//        for (MovingPile m : GameInit.movingPile) {
//            m.peek().setFaceUp(true);
//        }
//        for (Card c : GameInit.dealerPile.card) {
//            c.setFaceUp(false);
//        }
        for (CardPile acardPile : GameInit.piles) {
            for (Card acard : acardPile.card) {
                acard.setFaceUp(false);
            }
        }
        for (MovingPile m : GameInit.movingPile) {
            m.peek().setFaceUp(true);
        }

        GameInit.showContent();
        repaint();
    }

    public void refresh() {
        GameInit.initialize();
        display();
    }

    public int ifRealesedAtAnyPile(Point apoint) {
//        for (int i=1; i<12; i++) {
//            if (frameCards[i].contains(SwingUtilities.convertPoint(this,apoint,frameCards[i]))) {
//                System.out.printf("frameIndex:%d\n", i);
//                return i<4?i+8:i-3;
//            }
//        }
        for (int i=1; i<12; i++) {
            if (frameCards[i].contains(SwingUtilities.convertPoint(this,apoint,frameCards[i]))) {
                System.out.printf("frameIndex:%d\n", i);
                return i;
            }
        }
        return -1;
    }

    public boolean ifDragSuccess(Point apoint) {
        int pileIndex = ifRealesedAtAnyPile(apoint);

        if (pileIndex == -1 || movingCardList.empty()) {
            return false;
        }
        toThisPile = GameInit.piles[pileIndex];
        System.out.printf("pileIndex: %d; pileClass: %s\n",pileIndex,toThisPile.getClass());
        if (pileIndex >= 1 && pileIndex <=7) {
            if (toThisPile.getSize() > 0) {
                return toThisPile.canAddCard(movingCardList.get(0));
            } else {
                return movingCardList.get(0).getPoint() == 12;
            }
        }
        if (pileIndex >= 8 && pileIndex <= 11 && movingCardList.size()==1) {
            if (toThisPile.getSize() > 0) {
                return toThisPile.canAddCard(movingCardList.get(0));
            } else {
                return movingCardList.get(0).getPoint() == 0;
            }
        }
        return false;
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

//        if (MyTools.isClickedOnDealer(e.getXOnScreen(), e.getYOnScreen()) && dealerSize>0) {
        if (frameCards[12].contains(SwingUtilities.convertPoint(this, e.getLocationOnScreen(),frameCards[12]))
                && dealerSize>0) {
            System.out.println("正在发牌区点击"+dealer.currentShow);
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
            int op = JOptionPane.showConfirmDialog(null,"确认重新开始？",null,JOptionPane.OK_CANCEL_OPTION);
            if (op == JOptionPane.OK_OPTION) {
                refresh();
            }
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
            if (! (c.getPile() instanceof TopPile)) {
//                pressedPoint = e.getPoint();
//                Point pressedPoint = SwingUtilities.convertPoint(c, e.getPoint(),this);
//                System.out.printf("point:%s pile=%s\n", pressedPoint, c.getPile());
                fromThisPile = c.getPile();
                Point apoint = e.getPoint();
                if (fromThisPile instanceof DealerPile) {
                    movingCardList.add(c);
                    layeredPane.setLayer(c,JLayeredPane.DRAG_LAYER);
                    imigratePoint.add(apoint);
                }
                if (fromThisPile instanceof MovingPile) {
                    int startIndex = fromThisPile.card.indexOf(c);
                    int layerCount = JLayeredPane.DRAG_LAYER - fromThisPile.getSize() + startIndex;
                    int cx = apoint.x;
                    int cy = apoint.y;
                    for (int i=startIndex; i<fromThisPile.getSize(); i++) {
                        movingCardList.add(fromThisPile.get(i));
//                        imigratePoint.add(SwingUtilities.convertPoint(c, apoint, fromThisPile.get(i)));
                        imigratePoint.add(new Point(cx,cy));
                        cy += MyTools.cardVerticalOffset;
                        layeredPane.setLayer(fromThisPile.get(i),layerCount++);
                    }
                }

            }
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
        System.out.println(ifDragSuccess(e.getLocationOnScreen()));
        if (ifDragSuccess(e.getLocationOnScreen())) {
//            fromThisPile.card.removeAll(movingCardList);
            for (Card acard : movingCardList) {
                fromThisPile.removeCard(acard);
                System.out.printf("before add highLayer is:%d\n", toThisPile.highLayer);
                toThisPile.addCard(acard);
                System.out.printf("after add highLayer is:%d\n", toThisPile.highLayer);

//                System.out.printf("remove %s from %s\n",acard.spoint, fromThisPile.getClass());
//                for (Card bcard : fromThisPile.card) {
//                    System.out.println(bcard.spoint);
//                }
                System.out.println("\n");
                layeredPane.setLayer(acard, toThisPile.highLayer);
            }
            if (fromThisPile instanceof MovingPile && fromThisPile.getSize()>0) {
                fromThisPile.peek().setFaceUp(true);
                fromThisPile.peek().repaint();
            }
            movingCardList.removeAllElements();
            fromThisPile = null;
            toThisPile = null;
        } else {
            if (fromThisPile instanceof DealerPile) {
                layeredPane.setLayer(movingCardList.get(0), 2);
            }
            if (fromThisPile instanceof MovingPile) {
                fromThisPile.setHighLayer(fromThisPile.getHighLayer()-movingCardList.size()+1);
                for (Card acard : movingCardList) {
                    layeredPane.setLayer(acard, fromThisPile.highLayer);
                    fromThisPile.setHighLayer(fromThisPile.getHighLayer()+1);
                }
            }
            movingCardList.removeAllElements();
            fromThisPile = null;
            toThisPile = null;
        }
        GameInit.showContent();
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
        if (!movingCardList.empty()) {
            Point dragPoint = SwingUtilities.convertPoint(movingCardList.get(0), e.getPoint(), this);
//            System.out.printf("dragged point before translate:%s\n",dragPoint);
            for (int i=0; i<movingCardList.size(); i++) {
                Point p = (Point) dragPoint.clone();
                p.translate(-imigratePoint.get(i).x, -imigratePoint.get(i).y);
//                System.out.printf("dragged point after translate:%s\n",p);
                movingCardList.get(i).setLocation(p);
            }
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

    public static void main(String[] args) {
        new GamePanel();
    }
}
