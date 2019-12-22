package berlin.tablecodes.assets;

import com.google.inject.Inject;

import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
/**
 * DAO implementation for companion object {@link IAssetTypeMaintenance}.
 *
 * @author Developers
 *
 */
@EntityType(AssetTypeMaintenance.class)
public class AssetTypeMaintenanceDao extends CommonEntityDao<AssetTypeMaintenance> implements IAssetTypeMaintenance {

    @Inject
    public AssetTypeMaintenanceDao(final IFilter filter) {
        super(filter);
    }
    
    @Override
        public AssetTypeMaintenance new_() {
            final AssetTypeMaintenance maintenance = super.new_();
            maintenance.getProperty("role").setRequired(true);
            maintenance.getProperty("businessUnit").setRequired(true);
            maintenance.getProperty("organisation").setRequired(true);
            return maintenance;
        }

    @Override
    protected IFetchProvider<AssetTypeMaintenance> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}
