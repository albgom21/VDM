package gdv.ucm.appnonogram;

public class Pair {
    int a;
    int b;
    Pair(int a, int b){
        this.a = a;
        this.b = b;
    }
    public int getFirst(){
        return this.a;
    }
    public int getSecond(){
        return this.b;
    }
}