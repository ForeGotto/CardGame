package Test;

import javax.swing.*;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by hcq on 2016/10/13.
 */
public class GameInit {
    public static Card cards[] = new Card[52];
    public static CardPile piles[] = new CardPile[12];
    public static DealerPile dealerPile;
    public static MovingPile movingPile[] = new MovingPile[7];
    public static TopPile topPile[] = new TopPile[4];
    static {
        dealerPile = new DealerPile(MyTools.dealerPileFirstX,MyTools.dealerPileFirstY);
        for (int i=0; i<52; i++) {
            cards[i] = new Card();
        }
        for (int i=0; i<7; i++) {
            movingPile[i] = new MovingPile(MyTools.movingPileFirstX+i*MyTools.pileHorizontalGap, MyTools.movingPileFirstY);
        }
        for (int i=0; i<4; i++) {
            topPile[i] = new TopPile(MyTools.topPileFirstX+i*MyTools.pileHorizontalGap, MyTools.topPileFirstY);
        }
        piles[0] = dealerPile;
        for (int i=1; i<8; i++) {
            piles[i] = movingPile[i-1];
        }
        for (int i=8; i<12; i++) {
            piles[i] = topPile[i-8];
        }
        initialize();
    }

    /**
     *初始化时，向牌堆加牌直接
     */
    public static void initialize() {
        dealerPile.currentShow = 24;
        int [] a = MyTools.getDots();
        for (int i=0; i<52; i++) {
            cards[i].setDot(a[i]);
            System.out.printf("index: %d dot: %d point:%s\n",i,a[i],cards[i].spoint);
        }
        //首先把所有牌堆清空，以便重新初始化
        for (CardPile cp : piles) {
            cp.card.removeAllElements();
        }
        int cursor;
        for (cursor=0; cursor<24; cursor++) {
            dealerPile.addCard(cards[cursor]);
        }
        for (int i=0; i<7; i++) {
            for (int j=0; j<=i; j++) {
                movingPile[i].addCard(cards[cursor++]);
            }
        }
        for (Card c : dealerPile.card) {
            System.out.printf("dealerPile: %s\n", c.spoint);
        }
        System.out.println("dealerPile size:"+dealerPile.getSize());
        for (MovingPile p : movingPile) {
            for (Card c : p.card) {
                System.out.printf("movingPile: %s\n", c.spoint);
            }
        }
    }

    public static void showContent() {
        System.out.println(dealerPile.card.size());
        System.out.println(movingPile[5].card.size());
        dealerPile.show();
        for (MovingPile movingP : movingPile) {
            movingP.show();
        }
        for (TopPile topP : topPile) {
            topP.show();
        }
    }

//    public static void main(String[] args) {
//        GameInit.initialize();
//        JFrame f = new JFrame();
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setLayout(null);
//        f.setBounds(0,0,1920,1080);
//        Card c = new Card(10,1);
//        f.getContentPane().add(c);
//        c.setBounds(200,200,200,300);
//        f.setVisible(true);
//        try {
//            sleep(1000);
//            c.setFaceUp(true);
//            c.setLocation(400,400);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Card d = new Card(11,1);
//        System.out.println(c.canBeLaidBelow(d));
//    }
}
