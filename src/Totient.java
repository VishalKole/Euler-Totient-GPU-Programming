public class Totient {

    public static void main(String[] args){

        Totient t = new Totient();
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
        while (q != 0) {
            int temp = q;
            q = p % q;
            p = temp;
        }
        return p;
    }

}
