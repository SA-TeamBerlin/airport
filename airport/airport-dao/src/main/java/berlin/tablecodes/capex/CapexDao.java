package berlin.tablecodes.capex;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;
/**
 * DAO implementation for companion object {@link ICapex}.
 *
 * @author Developers
 *
 */
@EntityType(Capex.class)
public class CapexDao extends CommonEntityDao<Capex> implements ICapex {

    @Inject
    public CapexDao(final IFilter filter) {
        super(filter);
    }

    @Override
    protected IFetchProvider<Capex> createFetchProvider() {
       return FETCH_PROVIDER;
    }
}
