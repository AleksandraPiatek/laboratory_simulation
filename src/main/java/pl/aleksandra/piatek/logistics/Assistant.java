package pl.aleksandra.piatek.logistics;

import java.util.ArrayList;
import java.util.List;

public class Assistant {
 int id;
 int amountOfFood;
 int position;
 static boolean directionDown;

    public Assistant(int id, int amountOfFood, int position) {
        this.id = id;
        this.amountOfFood = amountOfFood;
        this.position = position;
    }

    public static synchronized List<Boolean> assistantsMovement(Assistant currentAssistant, Thread thread, List<Assistant> assistantList){
        try {
            thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean canGoUp=true;
        boolean canGoDown=true;

        if (currentAssistant.getPosition() + 1 > 10) {
            canGoDown = false;
            directionDown=false;
        } else if (currentAssistant.getPosition() - 1 < 0) {
            canGoUp = false;
            directionDown=true;
        }
        for (Assistant assistant : assistantList) {
            if (currentAssistant.getPosition()+1 == assistant.getPosition()) {
                canGoDown = false;
            }
            if (currentAssistant.getPosition()-1 == assistant.getPosition()) {
                canGoUp = false;
            }
        }
         List<Boolean> booleans = new ArrayList<>();
        booleans.add(canGoDown);
        booleans.add(canGoUp);
        booleans.add(directionDown);
        return booleans;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public int getAmountOfFood() {
        return amountOfFood;
    }

    public void setAmountOfFood(int amountOfFood) {
        this.amountOfFood = amountOfFood;
    }
}
