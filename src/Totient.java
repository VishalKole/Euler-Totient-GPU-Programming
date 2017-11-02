import edu.rit.pj2.Task;
import edu.rit.gpu.Kernel;
import edu.rit.gpu.Gpu;
import edu.rit.gpu.GpuLongVbl;
import edu.rit.gpu.Module;

public class Totient extends Task {

    /**
     * Kernel function interface.
     */
    private static interface TotientKernel
            extends Kernel {
        public void ComputeTotient
                (long N);
    }

    public void main(String[] args) throws Exception {

        long N = Long.parseLong(args[0]);

        // Initialize GPU.
        Gpu gpu = Gpu.gpu();
        gpu.ensureComputeCapability(2, 0);

        // Set up GPU counter variable.
        Module module = gpu.getModule("Totient.ptx");
        GpuLongVbl count = module.getLongVbl("devCount");

        count.item = 0;
        count.hostToDev();
        TotientKernel kernel = module.getKernel(TotientKernel.class);
        kernel.setBlockDim(1024);
        kernel.setGridDim(gpu.getMultiprocessorCount());
        kernel.ComputeTotient(N);

        // Print results.
        count.devToHost();
        System.out.println(count.item);


        System.out.println("final");
    }

    /**
     * Specify that this task requires one core.
     */
    protected static int coresRequired() {
        return 1;
    }

    /**
     * Specify that this task requires one GPU accelerator.
     */
    protected static int gpusRequired() {
        return 1;
    }
}
