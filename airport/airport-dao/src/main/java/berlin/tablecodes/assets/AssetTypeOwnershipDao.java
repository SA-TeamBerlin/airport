package berlin.tablecodes.assets;

import com.google.inject.Inject;

import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
/**
 * DAO implementation for companion object {@link IAssetTypeOwnership}.
 *
 * @author Developers
 *
 */
@EntityType(AssetTypeOwnership.class)
public class AssetTypeOwnershipDao extends CommonEntityDao<AssetTypeOwnership> implements IAssetTypeOwnership {

    @Inject
    public AssetTypeOwnershipDao(final IFilter filter) {
        super(filter);
    }
    
    @Override
        public AssetTypeOwnership new_() {
            final AssetTypeOwnership ownership = super.new_();
            ownership.getProperty("role").setRequired(true);
            ownership.getProperty("businessUnit").setRequired(true);
            ownership.getProperty("organisation").setRequired(true);
            return ownership;
        }

    @Override
    protected IFetchProvider<AssetTypeOwnership> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}
