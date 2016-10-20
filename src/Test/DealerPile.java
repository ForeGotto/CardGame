package Test;

/**
 * 此类的重点：currentShow,highPile
 * @TODO 此类处理方式：初始化时将所有牌设置在第一层
 * @TODO 在显示时，先把前一张显示的牌设置为第0层，将要显示的牌设置到第一层，如果不止有一张牌，把第二张牌设置到第0层
 * Created by hcq on 2016/10/13.
 */
public class DealerPile extends CardPile {

    static {
        pilePriority = 0;
    }

    int currentShow;

    /**
     * currentShow代表当前显示的牌的索引，当需要翻牌时，在监听方法中将currentShow减一，highLayer+1并调用本类的show()
     * 本类中的show()显示下一张要显示的牌
     * @TODO 此处currentShow应设置为24以初始化，在重新开始游戏时也该把currentShow设置为24
     * @param x
     * @param y
     */
    public DealerPile(int x, int y) {
        super(x, y);
        currentShow = 24;
    }


    /**
     * @TODO 此处应该添加显示两份牌的代码，还有只剩一张牌时的显示代码
     *
     * 此处显示机制：
     * 初始化完成后currentShow设置为24，此时，显示栈顶牌的背面
     * @TODO 每点击一次，curentShow减1，此时显示currentShow所代表的牌的正面，要在mouseClicked()中完成
     * @TODO 当currenetShow从0减到-1后，在监听里把currentShow设置为24，并调用show()
     */
    @Override
    public void show() {
//        System.out.println("currentShow:"+currentShow+"highLayer: "+highLayer);
        if (card.size() > 1) {
            if (currentShow == 0) {
                get(currentShow).setFaceUp(true);
                get(currentShow).setLocation(x+MyTools.pileHorizontalGap,y);
            }
            if (currentShow == getSize()) {
                get(currentShow-1).setFaceUp(false);
                for (Card c : card) {
                    c.setLocation(x,y);
                }
            }
            if (currentShow<getSize() && currentShow>0) {
                get(currentShow).setFaceUp(true);
                get(currentShow).setLocation(x+MyTools.pileHorizontalGap,y);
                get(currentShow-1).setFaceUp(false);
                get(currentShow-1).setLocation(x,y);
            }
        } else if (card.size() == 1) {
            if (currentShow == getSize()) {
                get(currentShow-1).setFaceUp(false);
                get(currentShow-1).setLocation(x,y);
            }
            if (currentShow == 0) {
                get(currentShow).setFaceUp(true);
                get(currentShow).setLocation(x+MyTools.pileHorizontalGap,y);
            }
        }
    }

    @Override
    public boolean canAddCard(Card c) {
        return false;
    }
}
