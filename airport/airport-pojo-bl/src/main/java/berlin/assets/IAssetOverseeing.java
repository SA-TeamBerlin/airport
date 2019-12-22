package berlin.assets;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link AssetOverseeing}.
 *
 * @author Developers
 *
 */
public interface IAssetOverseeing extends IEntityDao<AssetOverseeing> {

    static final IFetchProvider<AssetOverseeing> FETCH_PROVIDER = EntityUtils.fetch(AssetOverseeing.class).with(
        "key", "asset", "role", "businessUnit", "organisation", "startDate");
}
