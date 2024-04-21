package pl.aleksandra.piatek.logistics;

public class Nourishment {
    private final int id;
    private int amountOfFood;
    public Nourishment(int id, int amountOfFood) {
        this.id = id;
        this.amountOfFood = amountOfFood;
    }
    public void setAmountOfFood(int amountOfFood) {
        this.amountOfFood = amountOfFood;
    }
    public int getAmountOfFood() {
        return amountOfFood;
    }
}

