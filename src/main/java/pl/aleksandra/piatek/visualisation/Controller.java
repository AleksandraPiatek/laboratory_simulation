package pl.aleksandra.piatek.visualisation;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import pl.aleksandra.piatek.logistics.Assistant;
import pl.aleksandra.piatek.logistics.Nourishment;
import pl.aleksandra.piatek.logistics.Organism;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private TextField amountOfAssistants;
    @FXML
    private Label d, warning;
    @FXML
    private Label a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,o11;
    List<Organism> organismList = new ArrayList<>();
    List<Nourishment> nourishmentList = new ArrayList<>();
    List<Assistant> assistantList = new ArrayList<>();
    Boolean finish = false;


    public void onConfirmButtonClick(ActionEvent actionEvent) {
        organismList.clear();
        nourishmentList.clear();
        assistantList.clear();
        finish = false;

        int assistantsAmount = Integer.parseInt(amountOfAssistants.getText());
        if (assistantsAmount > 10 || assistantsAmount < 0) {
            warning.setText("Please enter value from 0 to 10");
            warning.setTextFill(Color.color(1, 0, 0));
        } else {
            warning.setText("");
            for (int i = 0; i < 11; i++){
                nourishmentList.add(new Nourishment(i+1, 0));
                organismList.add(new Organism(i+1, 5));
                setNourishment(i + 1, 0);
                setOrganisms(i + 1, 5);
                if (i < assistantsAmount) {
                    setAssistants(i + 1, i + 1, 50, false);
                    assistantList.add(new Assistant(i + 1, 50, i));
                } else {
                    setAssistants(i + 1, 0, 0, true);
                }
            }
            eatingVisualisation();
            assistantsVisualisation(assistantsAmount);
        }
    }
    public void onEndButtonClick(ActionEvent actionEvent) {
            finish=true;
        }
    public void eatingVisualisation() {
        Runnable organism = () -> {
                int index = Integer.parseInt(Thread.currentThread().getName()) - 1;
                while (organismList.get(index).getStamina() > 0 &&!finish) {
                   List<List> lists =  Organism.eat(organismList, nourishmentList, index);
                   organismList = lists.get(0);
                   nourishmentList = lists.get(1);
                    Platform.runLater(() -> {
                        for (int i = 1; i < 12; i++) {
                            setOrganisms(i, organismList.get(i - 1).getStamina());
                            setNourishment(i, nourishmentList.get(i - 1).getAmountOfFood());
                        }
                    });
                }
        };
        for(int i=1;i<12; i++){
            Thread thread = new Thread(organism);
            thread.setName(String.valueOf(i));
            thread.start();
        }
    }
    public void assistantsVisualisation(int assistantsAmount){
        Runnable assistant =() ->{
            while(!finish) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Assistant currentAssistant = assistantList.get(Integer.parseInt(Thread.currentThread().getName())-1);
                if (currentAssistant.getAmountOfFood()<20) {
                    try {
                        distribute(currentAssistant.getId(), currentAssistant.getPosition(), Thread.currentThread());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                  if(nourishmentList.get(currentAssistant.getPosition()).getAmountOfFood()<5){
                    currentAssistant.setAmountOfFood(currentAssistant.getAmountOfFood()-(10-nourishmentList.get(currentAssistant.getPosition()).getAmountOfFood()));
                     assistantList.get(currentAssistant.getId()-1).setAmountOfFood(currentAssistant.getAmountOfFood());
                     nourishmentList.get(currentAssistant.getPosition()).setAmountOfFood(10);
                }
                  Platform.runLater(()->{
                      setAssistants(currentAssistant.getPosition()+1, currentAssistant.getId(), currentAssistant.getAmountOfFood(), false);
                  });
                assistantMovingVisualisation(currentAssistant, Thread.currentThread());
                Platform.runLater(() -> {
                    for (int i = 1; i <12; i++) {
                     setNourishment(i, nourishmentList.get(i-1).getAmountOfFood());
                    }
                });
            }
        };
        for(int i=1;i<assistantsAmount+1; i++){
            Thread thread = new Thread(assistant);
            thread.setName(String.valueOf(i));
            thread.start();
        }
    }
    public synchronized void assistantMovingVisualisation(Assistant currentAssistant, Thread thread){
       List<Boolean> booleans =Assistant.assistantsMovement(currentAssistant, thread, assistantList);
       boolean canGoDown = booleans.get(0), canGoUp=booleans.get(1), directionDown=booleans.get(2);
        if(canGoDown && directionDown){
            currentAssistant.setPosition(currentAssistant.getPosition()+1);
            assistantList.get(currentAssistant.getId()-1).setPosition(currentAssistant.getPosition());
            Platform.runLater(() ->{
                setAssistants(currentAssistant.getPosition(), 0,0, true);
                setAssistants(currentAssistant.getPosition()+1, currentAssistant.getId(), currentAssistant.getAmountOfFood(), false );
            });
        }
        if(canGoUp && !directionDown){
            currentAssistant.setPosition(currentAssistant.getPosition()-1);
            assistantList.get(currentAssistant.getId()-1).setPosition(currentAssistant.getPosition());
            Platform.runLater(() ->{
                setAssistants(currentAssistant.getPosition()+2, 0,0, true );
                setAssistants(currentAssistant.getPosition()+1, currentAssistant.getId(), currentAssistant.getAmountOfFood(), false );
            });
        }
    }
    public synchronized void distribute(int assistantsId,int assistantsPosition, Thread thread) throws InterruptedException {
            thread.sleep(500);
        Platform.runLater(() -> {
            d.setText(String.valueOf(assistantsId));
            setAssistants(assistantsPosition+1, assistantsId, 50, false);
            assistantList.get(assistantsId-1).setAmountOfFood(50);
        });
            thread.sleep(1000);
    }
    @FXML
    public void setNourishment(int id, int amountOfFood){
        switch(id){
            case 1: n1.setText(String.valueOf(amountOfFood));
                break;
            case 2: n2.setText(String.valueOf(amountOfFood));
                break;
            case 3: n3.setText(String.valueOf(amountOfFood));
                break;
            case 4: n4.setText(String.valueOf(amountOfFood));
                break;
            case 5: n5.setText(String.valueOf(amountOfFood));
                break;
            case 6: n6.setText(String.valueOf(amountOfFood));
                break;
            case 7: n7.setText(String.valueOf(amountOfFood));
                break;
            case 8: n8.setText(String.valueOf(amountOfFood));
                break;
            case 9: n9.setText(String.valueOf(amountOfFood));
                break;
            case 10: n10.setText(String.valueOf(amountOfFood));
                break;
            case 11: n11.setText(String.valueOf(amountOfFood));
                break;
        }
    }
    @FXML
    public void setOrganisms(int id, int stamina){
        switch(id){
            case 1: o1.setText(String.valueOf(stamina));
                break;
            case 2: o2.setText(String.valueOf(stamina));
                break;
            case 3: o3.setText(String.valueOf(stamina));
                break;
            case 4: o4.setText(String.valueOf(stamina));
                break;
            case 5: o5.setText(String.valueOf(stamina));
                break;
            case 6: o6.setText(String.valueOf(stamina));
                break;
            case 7: o7.setText(String.valueOf(stamina));
                break;
            case 8: o8.setText(String.valueOf(stamina));
                break;
            case 9: o9.setText(String.valueOf(stamina));
                break;
            case 10: o10.setText(String.valueOf(stamina));
                break;
            case 11: o11.setText(String.valueOf(stamina));
                break;
        }
    }
    @FXML
    public void setAssistants(int labelId, int assistantsId, int amountOfFood, boolean blank){
        switch(labelId){
            case 1: if(!blank) a1.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                    else a1.setText("");
                break;
            case 2: if(!blank) a2.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                    else a2.setText("");
                break;
            case 3: if(!blank) a3.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                     else a3.setText("");
                break;
            case 4: if(!blank) a4.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                    else a4.setText("");
                break;
            case 5: if(!blank) a5.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                     else a5.setText("");
                break;
            case 6: if(!blank) a6.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                     else a6.setText("");
                break;
            case 7: if(!blank) a7.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                    else a7.setText("");
                      break;
            case 8: if(!blank) a8.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                    else a8.setText("");
                break;
            case 9: if(!blank) a9.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                    else a9.setText("");
                break;
            case 10: if(!blank) a10.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                      else a10.setText("");
                break;
            case 11: if(!blank) a11.setText(String.valueOf(assistantsId +"_"+amountOfFood));
                     else a11.setText("");
                     break;
            }
    }
}