package berlin.tablecodes.assets;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link AssetTypeMaintenance}.
 *
 * @author Developers
 *
 */
public interface IAssetTypeMaintenance extends IEntityDao<AssetTypeMaintenance> {

    static final IFetchProvider<AssetTypeMaintenance> FETCH_PROVIDER = EntityUtils.fetch(AssetTypeMaintenance.class)
            .with("key", "assetType", "role", "businessUnit", "organisation", "startDate");
}
