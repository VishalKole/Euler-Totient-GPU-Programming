public class Totient1 {

    public static void main(String[] args){

        Totient1 t = new Totient1();
        int sum =0;
        int N=100;
        for(int i=0;i<N;i++){
            if( t.GCD(i,N)==1 ){
                ++sum;
            }
        }
        System.out.println(sum);

    }

    public int GCD(int p, int q){
        int temp;
        while (q != 0) {
            temp = q;
            q = p % q;
            p = temp;
        }
        return p;
    }

}
