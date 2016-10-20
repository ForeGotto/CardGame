package Test;

import java.util.Stack;

/**
 * Created by hcq on 2016/10/13.
 */
public abstract class CardPile {
    public Stack<Card> card;
    public int highLayer;
    int x;
    int y;

    public CardPile(int x, int y) {
        this.x = x;
        this.y = y;
        highLayer = 0;
        card = new Stack<>();
    }

    public abstract void show();

    public abstract boolean canAddCard(Card c);

    /**
     * 此函数用于向牌堆加牌，游戏中加牌前请先调用函数canAddCard()判断是否可以加牌
     * @param c
     */
    public void addCard(Card c) {
        card.add(c);
        highLayer+=1;
        c.setPile(this);
    }

    public void removeCard(Card c) {
        card.pop();
        highLayer-=1;
    }

    public Card peek() {
        return card.peek();
    }

    public int getHighLayer() {
        return highLayer;
    }

    public void setHighLayer(int highLayer) {
        this.highLayer = highLayer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract int getPilePriority();

    public int getSize() {
        return card.size();
    }

    public Card get(int index) {
        return card.get(index);
    }
}
