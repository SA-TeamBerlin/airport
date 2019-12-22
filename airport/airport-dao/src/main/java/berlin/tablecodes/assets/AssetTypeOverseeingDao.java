package berlin.tablecodes.assets;

import com.google.inject.Inject;

import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
/**
 * DAO implementation for companion object {@link IAssetTypeOverseeing}.
 *
 * @author Developers
 *
 */
@EntityType(AssetTypeOverseeing.class)
public class AssetTypeOverseeingDao extends CommonEntityDao<AssetTypeOverseeing> implements IAssetTypeOverseeing {

    @Inject
    public AssetTypeOverseeingDao(final IFilter filter) {
        super(filter);
    }
    
    @Override
        public AssetTypeOverseeing new_() {
            final AssetTypeOverseeing overseeing = super.new_();
            overseeing.getProperty("role").setRequired(true);
            overseeing.getProperty("businessUnit").setRequired(true);
            overseeing.getProperty("organisation").setRequired(true);
            return overseeing;
        }

    @Override
    protected IFetchProvider<AssetTypeOverseeing> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}
