package model;

public class CardReaceiver {
    private int money;
    private int numberCard;
    private int password;

    public CardReaceiver(int money, int numberCard, int password) {
        this.money = money;
        this.numberCard = numberCard;
        this.password = password;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }



    public void setNumberCard(int numberCard) {
        this.numberCard = numberCard;
    }



    public void setPassword(int password) {
        this.password = password;
    }
}
