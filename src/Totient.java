import edu.rit.pj2.Task;
import edu.rit.gpu.Kernel;
import edu.rit.gpu.Gpu;
import edu.rit.gpu.GpuLongVbl;
import edu.rit.gpu.Module;
import edu.rit.gpu.CacheConfig;


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

        long N = ValidateInput(args);

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
        kernel.setCacheConfig (CacheConfig.CU_FUNC_CACHE_PREFER_SHARED);
        kernel.ComputeTotient(N);

        // Print results.
        count.devToHost();
        System.out.println(count.item);

    }

    private long ValidateInput(String input[]) throws Exception {

        if (input.length != 1)
            throw new IllegalArgumentException("Arguments are not exactly equal to one.");

        if (!input[0].matches("[0-9]+"))
            throw new IllegalArgumentException("Argument is not a number and contains other characters or" +
                    " special characters.");
        long number;
        try{
            number = Long.parseLong(input[0]);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Value should be in the range from 1 to 9,223,372,036,854,775,808. ");
        }
        if ((number < 1))
            throw new IllegalArgumentException("Invalid number to calculate Totient. should be greater than 0. ");

        return number;
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
