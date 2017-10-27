// Number of threads per block.
#define NT 1024

// Overall counter variable in global memory.
__device__ unsigned long long int devCount;

// Per-thread counter variables in shared memory.
__shared__ unsigned long long int shrCount [NT];

extern "C" __global__ void ComputeTotient(unsigned long long int P,
                                              unsigned long long int Q)
       {

    int thr, size, rank;
    thr = threadIdx.x;
    size = gridDim.x*NT;
    rank = blockIdx.x*NT + thr;



       }