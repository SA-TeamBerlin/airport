package berlin.tablecodes.assets;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link AssetTypeOverseeing}.
 *
 * @author Developers
 *
 */
public interface IAssetTypeOverseeing extends IEntityDao<AssetTypeOverseeing> {

    static final IFetchProvider<AssetTypeOverseeing> FETCH_PROVIDER = EntityUtils.fetch(AssetTypeOverseeing.class)
            .with("key", "assetType", "role", "businessUnit", "organisation", "startDate");
}
