import edu.rit.pj2.Task;
import edu.rit.gpu.Kernel;
import edu.rit.gpu.Gpu;
import edu.rit.gpu.GpuLongVbl;
import edu.rit.gpu.Module;
import edu.rit.gpu.CacheConfig;

/**
 * Class Totient computes the Totient value for a long value N
 */
public class Totient extends Task {

    /**
     * Interface that relates the Java function to the kernel function
     */
    private static interface TotientKernel extends Kernel {
        public void ComputeTotient (long N);
    }

    /**
     * function sets the parameters to the kernel function and calls the kernel
     *
     * @param args the Long integer on which the Totient is computed
     * @throws Exception if input is invalid
     */
    public void main(String[] args) throws Exception {

        //validates the input and throws error if not correct.
        long N = ValidateInput(args);

        // Initialize GPU.
        Gpu gpu = Gpu.gpu();

        //ensures compute capability is met
        gpu.ensureComputeCapability(2, 0);

        // Set up GPU counter variable.
        Module module = gpu.getModule("Totient.ptx");
        GpuLongVbl count = module.getLongVbl("devCount");

        //transferring the variable to the GPU
        count.item = 0;
        count.hostToDev();

        //sets the kernel and the class file which should compute the totient
        TotientKernel kernel = module.getKernel(TotientKernel.class);
        kernel.setBlockDim(1024);
        kernel.setGridDim(gpu.getMultiprocessorCount());

        //Priority is given to the shared memory as it requires only shared and no cache
        kernel.setCacheConfig (CacheConfig.CU_FUNC_CACHE_PREFER_SHARED);

        //calls kernel to compute
        kernel.ComputeTotient(N);

        // Print results
        count.devToHost();
        System.out.println(count.item);

    }

    /**
     * Validates the input of the function.
     * @param input the String input given to the program
     * @return returns Long integer N if valid
     * @throws Exception if the input is not valid
     */
    private long ValidateInput(String input[]) throws Exception {

        //Argument is only one
        if (input.length != 1)
            throw new IllegalArgumentException("Arguments are not exactly equal to one.");

        //it is only numerical in characters
        if (!input[0].matches("[0-9]+"))
            throw new IllegalArgumentException("Argument is not a number and contains other characters or" +
                    " special characters.");
        long number;
        try{
            //checks if it is Long integer
            number = Long.parseLong(input[0]);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Value should be in the range from 1 to 9,223,372,036,854,775,808. ");
        }

        //checks if the value is negative
        if ((number < 1))
            throw new IllegalArgumentException("Invalid number to calculate Totient. should be greater than 0. ");

        return number;
    }

    /**
     * restricting the core to 1
     * @return 1 always
     */
    protected static int coresRequired() {
        return 1;
    }

    /**
     * restricting the GPU to 1
     * @return 1 always
     */
    protected static int gpusRequired() {
        return 1;
    }
}
