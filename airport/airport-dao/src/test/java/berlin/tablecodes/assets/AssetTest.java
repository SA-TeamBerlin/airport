package berlin.tablecodes.assets;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.from;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.orderBy;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;
import static ua.com.fielden.platform.utils.EntityUtils.fetch;

import java.util.List;

import org.junit.Test;

import berlin.assets.Asset;
import berlin.assets.AssetFinDet;
import berlin.assets.IAssetFinDet;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.dao.QueryExecutionModel;
import ua.com.fielden.platform.entity.query.model.EntityResultQueryModel;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class AssetTest extends AbstractDaoTestCase {

    @Test
    public void property_title_is_determined_correctly_even_without_title_annotation() {
        assertEquals(AssetType.ENTITY_TITLE, co$(Asset.class).new_().getProperty("assetType").getTitle());
    }
    
    @Test
    public void can_find_assets_by_fin_det_information() {
        final Asset asset1 = save(new_(Asset.class).setDesc("a demo asset 1"));
        final Asset asset2 = save(new_(Asset.class).setDesc("a demo asset 2"));
        final Asset asset3 = save(new_(Asset.class).setDesc("a demo asset 3"));
        
        final AssetFinDet finDet1 = co$(AssetFinDet.class).findById(asset1.getId(), IAssetFinDet.FETCH_PROVIDER.fetchModel());
        save(finDet1.setInitCost(Money.of("120.00")).setAcquireDate(date("2019-12-07 00:00:00")));
        final AssetFinDet finDet2 = co$(AssetFinDet.class).findById(asset2.getId(), IAssetFinDet.FETCH_PROVIDER.fetchModel());
        save(finDet2.setInitCost(Money.of("100.00")).setAcquireDate(date("2019-11-01 00:00:00")));
        final AssetFinDet finDet3 = co$(AssetFinDet.class).findById(asset3.getId(), IAssetFinDet.FETCH_PROVIDER.fetchModel());
        save(finDet3.setInitCost(Money.of("10.00")).setAcquireDate(date("2018-11-01 00:00:00")));
        
        final QueryExecutionModel<Asset, EntityResultQueryModel<Asset>> qFindWithLessOrEq100 = 
                from(select(Asset.class).where().prop("finDet.initCost").le().val(Money.of("100.00")).model())
                .with(fetch(Asset.class).with("number", "desc", "finDet.initCost", "finDet.acquireDate").fetchModel())
                .with(orderBy().prop("finDet.initCost").asc().model()).model();
        
        final List<Asset> assets = co(Asset.class).getAllEntities(qFindWithLessOrEq100);
        assertEquals(2, assets.size());
        
        assertEquals(Money.of("10.00"), assets.get(0).getFinDet().getInitCost());
        assertEquals("000003", assets.get(0).getNumber());
        assertEquals(Money.of("100.00"), assets.get(1).getFinDet().getInitCost());
        assertEquals("000002", assets.get(1).getNumber());

        final QueryExecutionModel<Asset, EntityResultQueryModel<Asset>> qFindWithGreater100 = 
                from(select(Asset.class).where().prop("finDet.initCost").gt().val(Money.of("100.00")).model())
                .with(fetch(Asset.class).with("number", "desc", "finDet.initCost", "finDet.acquireDate").fetchModel())
                .with(orderBy().prop("finDet.initCost").asc().model()).model();
        
        final List<Asset> assetsMoreExpensiveThan100 = co(Asset.class).getAllEntities(qFindWithGreater100);
        assertEquals(1, assetsMoreExpensiveThan100.size());
        
        assertEquals(Money.of("120.00"), assetsMoreExpensiveThan100.get(0).getFinDet().getInitCost());
        assertEquals("000001", assetsMoreExpensiveThan100.get(0).getNumber());
    }
    
    @Override
    public boolean saveDataPopulationScriptToFile() {
        return false;
    }

    @Override
    public boolean useSavedDataPopulationScript() {
        return false;
    }

    @Override
    protected void populateDomain() {
        super.populateDomain();

        final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
        constants.setNow(dateTime("2019-10-01 11:30:00"));

        if (useSavedDataPopulationScript()) {
            return;
        }
    }
}
