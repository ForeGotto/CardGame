package Test;

/**
 * Created by hcq on 2016/10/13.
 */
public class TopPile extends CardPile {
    static {
        pilePriority = 2;
    }

    public TopPile(int x, int y) {
        super(x, y);
    }

    /**
     * @TODO TopPile只展示顶部牌，当顶部无牌时，就展示背景
     */
    @Override
    public void show() {
        if (card.size()>0) {
            peek().setLocation(x,y);
        }
//        final int [] a = new int[2];
//        a[0] = x;
//        a[1] = y;
//        if (getSize() != 0) {
//            card.forEach(p -> p.setLocation(x, a[0]+=50));
//        }
    }

    /**
     * @TODO 最多只能向最后安放区添加一张牌
      * @param c
     * @return
     */
    @Override
    public boolean canAddCard(Card c) {
        if (c.getPile().getPilePriority() < getPilePriority()) {
            /**
            * @TODO 最多只能向最后安放区添加一张牌,应该在鼠标监听处实现
            **/
            if (card.peek().canBeLaidBelow(c)) {
                return true;
            }
        }
        return false;
    }
}
