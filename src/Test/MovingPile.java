package Test;

/**
 * @TODO 在监听里设置
 * Created by hcq on 2016/10/13.
 */
public class MovingPile extends CardPile {

    static {
        pilePriority = 1;
    }

    public MovingPile(int x, int y) {
        super(x, y);
    }

    @Override
    public void show() {
        for (int i=0; i<card.size(); i++) {
            card.get(i).setLocation(x, y+i*MyTools.cardVerticalOffset);
        }
    }

    @Override
    public boolean canAddCard(Card c) {
        if (c.getPile().getPilePriority() <= getPilePriority() && card.peek().canBeLaidBelow(c)) return true;
        else return false;
    }
}
