package berlin.tablecodes.capex;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Capex}.
 *
 * @author Developers
 *
 */
public interface ICapex extends IEntityDao<Capex> {

    static final IFetchProvider<Capex> FETCH_PROVIDER = EntityUtils.fetch(Capex.class).with("key", "desc");
}
