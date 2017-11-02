public class test {


    public static void main(String[] s){

        int thr, size, rank;
        long count=0, b, temp, a;

        thr = 11;
        size = 1024;
        rank =  thr;

        long N=100;

        for (int x = rank; x < N; x += size){
            a=x;
            b=N;

           // while(a != b){
           //    if( a > b){
          //          a = a - b;}
              //  else{
             //       b = b - a;}
           // }

               while (b != 0){
                   temp = b;
                 b = a % b;
                  a = temp;
               }

            if(a==1){
                ++count;}
        }

        System.out.println(count);
    }
}
