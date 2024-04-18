package pl.edu.pwr.apiatek.logistics;

import java.util.ArrayList;
import java.util.List;

public class Organism {
    final int id;
    int stamina;
    public Organism(int id, int stamina) {
        this.id = id;
        this.stamina = stamina;
    }
    public int getStamina() {
        return stamina;
    }
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public static List<List> eat(List<Organism> organismList, List<Nourishment> nourishmentList, int index){
        if (organismList.get(index).getStamina() < 5) {
            try {
                Thread.sleep(Randomize.randomize(1000, 10000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (nourishmentList.get(index).getAmountOfFood() > 0) {
                nourishmentList.get(index).setAmountOfFood(nourishmentList.get(index).getAmountOfFood() - 1);
                organismList.get(index).setStamina(organismList.get(index).getStamina() + 1);
            } else {
                organismList.get(index).setStamina(organismList.get(index).getStamina() - 1);
            }
        } else {
            try {
                Thread.sleep(Randomize.randomize(1000, 5000));
                organismList.get(index).setStamina(organismList.get(index).getStamina() - 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        List<List> lists = new ArrayList<>();
        lists.add(organismList);
        lists.add(nourishmentList);
        return lists;
    }
}
