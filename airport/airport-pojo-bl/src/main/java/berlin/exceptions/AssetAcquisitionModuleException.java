package berlin.exceptions;

public class AssetAcquisitionModuleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AssetAcquisitionModuleException(final String msg) {
        super(msg);
    }

    public AssetAcquisitionModuleException(final String msg, final Exception ex) {
        super(msg, ex);
    }

}
