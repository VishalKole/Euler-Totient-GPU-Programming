import edu.rit.pj2.Task;
import edu.rit.gpu.Kernel;
import edu.rit.gpu.Gpu;
import edu.rit.gpu.GpuLongVbl;
import edu.rit.gpu.Module;
import edu.rit.pj2.Task;

public class Totient extends Task {

    long N=100;

    public void main(String[] args) throws Exception{

        // Initialize GPU.
        Gpu gpu = Gpu.gpu();
        gpu.ensureComputeCapability (2, 0);

        // Set up GPU counter variable.
        Module module = gpu.getModule ("/f/Totient.ptx");
        GpuLongVbl count = module.getLongVbl ("devCount");


    }

    /**
     * Specify that this task requires one core.
     */
    protected static int coresRequired()
    {
        return 1;
    }

    /**
     * Specify that this task requires one GPU accelerator.
     */
    protected static int gpusRequired()
    {
        return 1;
    }
}
