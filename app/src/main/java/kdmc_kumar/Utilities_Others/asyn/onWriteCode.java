package kdmc_kumar.Utilities_Others.asyn;

/**
 * Created by Ponnusamy on 12/20/2017.
 * DemoTemplate
 */

public abstract class onWriteCode<E> {

    protected onWriteCode() {
    }

    public abstract E onExecuteCode() throws Exception;

    public abstract E onSuccess(E result) throws Exception;
}



