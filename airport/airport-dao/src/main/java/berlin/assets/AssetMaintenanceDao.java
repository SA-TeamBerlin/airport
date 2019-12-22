package berlin.assets;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;
/**
 * DAO implementation for companion object {@link IAssetMaintenance}.
 *
 * @author Developers
 *
 */
@EntityType(AssetMaintenance.class)
public class AssetMaintenanceDao extends CommonEntityDao<AssetMaintenance> implements IAssetMaintenance {

    @Inject
    public AssetMaintenanceDao(final IFilter filter) {
        super(filter);
    }
    
    @Override
    public AssetMaintenance new_() {
        final AssetMaintenance maintenance = super.new_();
        maintenance.getProperty("role").setRequired(true);
        maintenance.getProperty("businessUnit").setRequired(true);
        maintenance.getProperty("organisation").setRequired(true);
        return maintenance;
    }

    @Override
    protected IFetchProvider<AssetMaintenance> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}
