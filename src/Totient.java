public class Totient {

    public static void main(String[] args){


    }

    public int GCD(int p, int q){
        while (q != 0) {
            int temp = q;
            q = p % q;
            p = temp;
        }
        return p;
    }

}
