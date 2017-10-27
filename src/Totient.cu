// Number of threads per block.
#define NT 1024

// Overall counter variable in global memory.
__device__ unsigned long long int devCount;

// Per-thread counter variables in shared memory.
__shared__ unsigned long long int shrCount [NT];

extern "C" __global__ void ComputeTotient(unsigned long long int N){

    int thr, size, rank;
    unsigned long long int count;

    thr = threadIdx.x;
    size = gridDim.x*NT;
    rank = blockIdx.x*NT + thr;

    count = 0;
    int temp;

    for (unsigned long long int i = rank; i < N; i += size){
        while (rank != 0) {
            temp = rank;
            rank = p % rank;
            p = temp;
        }
        if(p==1)
        ++count;
    }

 // Shared memory parallel reduction within thread block.
    shrCount[thr] = count;
    syncthreads();

    for (int i = NT/2; i > 0; i >>= 1){

        if (thr < i)
        shrCount[thr] += shrCount[thr+i];
        syncthreads();
    }

    // Atomic reduction into overall counter.
    if (thr == 0)
    atomicAdd (&devCount, shrCount[0]);

}