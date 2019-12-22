package berlin.assets;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;
/**
 * DAO implementation for companion object {@link IAssetOverseeing}.
 *
 * @author Developers
 *
 */
@EntityType(AssetOverseeing.class)
public class AssetOverseeingDao extends CommonEntityDao<AssetOverseeing> implements IAssetOverseeing {

    @Inject
    public AssetOverseeingDao(final IFilter filter) {
        super(filter);
    }
    
    @Override
    public AssetOverseeing new_() {
        final AssetOverseeing overseeing = super.new_();
        overseeing.getProperty("role").setRequired(true);
        overseeing.getProperty("businessUnit").setRequired(true);
        overseeing.getProperty("organisation").setRequired(true);
        return overseeing;
    }

    @Override
    protected IFetchProvider<AssetOverseeing> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}
