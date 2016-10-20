package Test;

/**
 * @TODO 在监听里设置
 * Created by hcq on 2016/10/13.
 */
public class MovingPile extends CardPile {


    static int pilePriority = 1;

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
//        if (c.getPile().getPilePriority() <= getPilePriority()) {
//            System.out.println("优先级没问题");
//            if (card.get(getSize()-1).canBeLaidBelow(c)) {
//                return true;
//            }
//            System.out.println("问题在canBeLaidBelow(c)");
//        }
//        return false;
        if (c.getPile().getPilePriority() <= getPilePriority() && card.peek().canBeLaidBelow(c)) return true;
        else return false;
    }

    @Override
    public int getPilePriority() {
        return pilePriority;
    }
}
