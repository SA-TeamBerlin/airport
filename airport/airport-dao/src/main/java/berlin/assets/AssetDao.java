package berlin.assets;

import java.awt.IllegalComponentStateException;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;

import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.keygen.IKeyNumber;
import ua.com.fielden.platform.keygen.KeyNumber;

/**
 * DAO implementation for companion object {@link IAsset}.
 *
 * @author Developers
 *
 */
@EntityType(Asset.class)
public class AssetDao extends CommonEntityDao<Asset> implements IAsset {
    public static final String DEFAULT_ASSET_NUMBER = "NEXT NUMBER WILL BE GENERATED UPON SAVE";

    @Inject
    public AssetDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    public Asset save(final Asset asset) {
        // implement a solution for a failed transaction where ID was already assigned
        final boolean isDefaultNumber = DEFAULT_ASSET_NUMBER.equals(asset.getNumber());
        try {
            if (!isDefaultNumber) {
                final IKeyNumber coKeyNumber = co(KeyNumber.class);
                final Integer nextNumber = coKeyNumber.nextNumber("ASSET_NUMBER");
                asset.setNumber(nextNumber.toString());
            }
            
            final Asset savedAsset = super.save(asset);
            return savedAsset;
            //throw new IllegalComponentStateException("This is to force the rollback of a database transaction.");
        } catch (final Exception ex) {
            if (!isDefaultNumber) {
                asset.setNumber(DEFAULT_ASSET_NUMBER);
            }
            throw Result.failure(ex);
        }
    }

    @Override
    public Asset new_() {
        final Asset asset = super.new_();
        asset.setNumber(DEFAULT_ASSET_NUMBER);
        return asset;
    }

    @Override
    @SessionRequired
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    public int batchDelete(final List<Asset> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<Asset> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}
