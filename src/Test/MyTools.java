package Test;

import java.util.Random;

/**
 * Created by hcq on 2016/10/14.
 */
public class MyTools {
    public static final int cardSizeX = 170;
    public static final int cardSizeY = 200;
    public static final int cardVerticalOffset = 30;
    public static final int pileHorizontalGap = 240;
    public static final int topPileFirstX = 865;
    public static final int topPileFirstY = 100;
    public static final int movingPileFirstX = 145;
    public static final int movingPileFirstY = 400;
    public static final int dealerPileFirstX = 145;
    public static final int dealerPileFirstY = 100;
    public static final int frameSizeX = 170;
    public static final int frameSizeY = cardSizeY + 12 * cardVerticalOffset;
    public static int [] getDots() {
        Random random = new Random();
        int rank[] = new int[52];
        for (int i=0; i<52; i++) {
            rank[i] = Math.abs(random.nextInt(52));
//            new Card(i);
//            System.out.println(rank[i]);
        }
        int a[] = new int[52];
        for (int i=0; i<52; i++) {
            a[i] = i;
        }
        for (int i=0; i<52; i++) {
            int temp = a[i];
            a[i] = a[rank[i]];
            a[rank[i]] = temp;
        }
//        for (int i=0; i<52; i++) {
//            System.out.println(i+": "+a[i]);
//        }
        return a;
    }

    public static boolean isClickedOnDealer(int x, int y) {
        if (x>dealerPileFirstX && x<dealerPileFirstX+cardSizeX && y>dealerPileFirstY && y<dealerPileFirstY+cardSizeY) {
            return true;
        }
        return false;
    }

//    public static int[] cardSourceInfo(int x, int y) {
//
//    }
}
