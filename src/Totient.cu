// Number of threads per block.
#define NT 1024

// Overall counter variable in global memory.
__device__ unsigned long long int devCount;

// Per-thread counter variables in shared memory.
__shared__ unsigned long long int shrCount [NT];

//Kernel function to compute the Totient value. It just takes the number on which the
//totient should be computed and computes the totient for the current thread number +
//size of the totals threads the GPU can create.
extern "C" __global__ void ComputeTotient(unsigned long long int N){

    //Variable declarations
    int thr, size, rank;
    unsigned long long int count=0, b, temp, a;

    //Rank and size computations
    thr = threadIdx.x;
    size = gridDim.x*NT;
    rank = blockIdx.x*NT + thr;

    //loop to compute the totient for current thread rank
    for (unsigned long long int x = rank; x < N; x += size){
    a=x;
    b=N;
        while (b > 0){
            temp = b;
            b = a % b;
            a = temp;
        }

        if(a==1)
        ++count;
    }

 // assigning the counter value to the shared memory variable
    shrCount[thr] = count;
    __syncthreads();

    //reduction of the shared variables in parallel
    for (int i = NT/2; i > 0; i >>= 1){
        if (thr < i)
        shrCount[thr] += shrCount[thr+i];
        __syncthreads();
    }

    // Adding the total of each block to global variable
    if (thr == 0)
    atomicAdd (&devCount, shrCount[0]);
}