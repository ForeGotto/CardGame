package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 代表一张牌，属性有：(总点数，点数，字面点数)，(花色，颜色，是否朝上),(当前牌堆优先级)
 * 特别注意(当前牌堆)属性
 * @TODO 在CardPile类中添加牌时，一定要先设置当前牌堆属性，并且将判断是否可以挪牌的部分代码改到牌堆类中
 * @TODO 在判断是否可以挪牌时，首先比较两张牌的优先级，优先级低的牌堆可以向优先级高的牌堆挪牌(这一部分在CardPile类中完成)
 * @TODO 优先级设置为: 最后安放区优先级最高，底部挪牌区次之，左部发牌区最次(这一部分在CardPile类中完成)
 * @TODO 如果优先级允许挪，判断需要挪牌的数量，不可能向最后安放区挪一张以上的牌(这一部分在CardPile类中完成)
 * @TODO 就判断颜色是否允许(这一部分代码已在Card类中完成,但应注意,向最后安放区挪牌时的规则与向底部挪牌区相反)
 * @TODO 如果花色也允许，就判断点数是否允许(这一部分代码已在Card类中完成)
 *
 *
 * Created by hcq on 2016/10/13.
 */
public class Card extends JComponent{
//    static final int HEART = 0;
//    static final int DIAMOND = 1;
//    static final int SPADE = 2;
//    static final int CLUB = 3;

    private int dot;
    private int point;
    public String spoint;
    private int suit;
    private int color;
    private boolean faceUp;
    private CardPile pile;

    public Card() {
        super();
    }

    /**
     * 此处设置所有牌背面朝上
     * @param p_dot
     */
//    public Card(int p_dot) {
//        super();
//        setProperties(p_dot);
//        faceUp = true;
//    }
//
//    public void setProperties(int p_dot) {
//        if (p_dot>-1 && p_dot<52) {
//            setDot(p_dot);
//        } else {
//            System.out.println("ERROR! parameter of Card() should be in [0,51]");
//        }
//    }
    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoker super's implementation you must honor the opaque property,
     * that is
     * if this component is opaque, you must completely fill in the background
     * in a non-opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identify transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color currentColor = color==0 ? Color.RED : Color.BLACK;
        Font newfont = g.getFont().deriveFont(25f);
        g.setFont(newfont);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (faceUp){
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.white);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(),20,20);
            g2.setColor(currentColor);
            g2.drawString(spoint,10,25);
            g2.rotate(Math.toRadians(180),getWidth()/2,getHeight()/2);
            g2.drawString(spoint,10,25);
        } else {
            g2d.setColor(Color.gray);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(),20,20);
            g2d.setColor(Color.BLACK);
            int[] x1 = {85,45,125,85};
            int[] y1 = {50,119,119,50};
            int[] y2 = {142,73,73,142};
            g2d.fillPolygon(x1,y1,4);
            g2d.fillPolygon(x1,y2,4);
        }
        g2d.setColor(Color.GREEN);
        g2d.drawRoundRect(0,0,getWidth()-1,getHeight()-1,15,15);
    }

    public int getPoint() {
        return point;
    }

    public int getSuit() {
        return suit;
    }

    public int getColor() {
        return color;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public CardPile getPile() {
        return pile;
    }

    /**
     * 设置this的牌序
     * 同时设置this的花色，颜色，点数，牌面显示字符
     * 注意，此处将this设置为朝下
     * @param dot
     */
    public void setDot(int dot) {
        this.dot = dot;
        point = dot % 13;
        suit = dot / 13;
        color = suit /2;
        String tmpstr = null;
        switch (suit) {
            case 0:
                tmpstr = "\u2665";
                break;
            case 1:
                tmpstr = "\u2666";
                break;
            case 2:
                tmpstr = "\u2660";
                break;
            case 3:
                tmpstr = "\u2663";
                break;
        }
        if (point < 9 && point>0) {
            spoint = String.valueOf(point+1) + "  " + tmpstr;
        } else {
            switch (point) {
                case 0:
                    spoint = "A "+tmpstr;
                    break;
                case 9:
                    spoint = "10 "+tmpstr;
                    break;
                case 10:
                    spoint = "J  "+tmpstr;
                    break;
                case 11:
                    spoint = "Q "+tmpstr;
                    break;
                case 12:
                    spoint = "K "+tmpstr;
                    break;
            }
        }
//        System.out.printf("%d %d %d %d %s\n",dot,point,suit,color,spoint);
    }

    public void setPile(CardPile pile) {
        this.pile = pile;
    }

    /**
     * 判断两张牌大小是否匹配
     * 主要被canBeLaidBelow(Card c)调用
     * @param c
     * @return 如果this小于c的点数，则返回真
     */
    boolean isPointSuit(Card c) {
        switch (getPile().getPilePriority()) {
            case 0:
                return false;
            case 1:
                return this.point == c.getPoint()+1;
            case 2:
                return this.point == c.getPoint()-1;
            default:
                return false;
        }
    }

    /**
     * 判断两张牌颜色是否匹配
     *主要被canBeLaidBelow(Card c)调用
     * @param c
     * @return 如，this在TopPile中，则颜色相同匹配，如this在MovingPile中，则颜色相反匹配
     */
    boolean isColorSuit(Card c) {
//        System.out.printf("目标牌堆类:%s, 目标牌堆优先级:%d\n",getPile().getClass(),getPile().getPilePriority());
        switch (this.getPile().getPilePriority()) {
//            case 0:
//                System.out.println("ERROR! can't move card to dealer pile");
//                return false;
            case 1:
//                System.out.println("正确选择了所属牌堆");
//                System.out.printf("moving card:%s to table pile :%s\n",c.spoint,spoint);
                return this.color != c.getColor();
            case 2:
//                System.out.println("错误的选择了所属牌堆");
//                System.out.printf("moving card:%s to top pile :%s\n",c.spoint,spoint);
                return this.suit == c.getSuit();
            default:
                return false;
        }
    }

    /**
     * 判断this能否放到所给牌之下（即所给参数能否放到当前牌堆顶部）
     * 主要调用isColorSuit(Card c)，isPointSuit(Card c)
     * @param c
     * @return
     */
    boolean canBeLaidBelow(Card c) {
//        if (this.isColorSuit(c)) {
//            if (this.isPointSuit(c)) {
//                return true;
//            }
//            System.out.println("问题在isPointSuit(c)");
//        }
//        System.out.println("问题在isColorSuit(c)");
//        return false;
        return ((this.isColorSuit(c)) && (this.isPointSuit(c)));
    }

//    public static void main(String[] args) {
//        Card a = new Card(13);
//        Card b = new Card(37);
//        System.out.println(a.canBeLaidBelow(b));
//    }
}
