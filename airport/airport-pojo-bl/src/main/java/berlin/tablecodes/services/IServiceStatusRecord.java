package berlin.tablecodes.services;

import ua.com.fielden.platform.dao.IEntityDao;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

public interface IServiceStatusRecord extends IEntityDao<ServiceStatusRecord>{

	static final IFetchProvider<ServiceStatusRecord> FETCH_PROVIDER = EntityUtils.fetch(ServiceStatusRecord.class).with("name", "desc");
}
