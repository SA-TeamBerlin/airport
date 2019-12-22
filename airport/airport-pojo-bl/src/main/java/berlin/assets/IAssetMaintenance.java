package berlin.assets;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link AssetMaintenance}.
 *
 * @author Developers
 *
 */
public interface IAssetMaintenance extends IEntityDao<AssetMaintenance> {

    static final IFetchProvider<AssetMaintenance> FETCH_PROVIDER = EntityUtils.fetch(AssetMaintenance.class).with(
        "key", "asset", "role", "businessUnit", "organisation", "startDate");
}
