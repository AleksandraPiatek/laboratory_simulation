package pl.aleksandra.piatek.logistics;

public class Randomize {
    public static int randomize(int min, int max){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }
}
