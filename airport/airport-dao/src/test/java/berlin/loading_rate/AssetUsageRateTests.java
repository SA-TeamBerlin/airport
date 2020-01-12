package berlin.loading_rate;
import berlin.assets.Asset;
import berlin.assets.IAsset;
import org.junit.Test;


import berlin.tablecodes.assets.AssetClass;
import berlin.tablecodes.assets.AssetType;
import berlin.test_config.AbstractDaoTestCase;
import ua.com.fielden.platform.dao.IEntityDao;
import berlin.assets.validators.RateValidator;

import static groovy.util.GroovyTestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AssetUsageRateTests extends AbstractDaoTestCase {


    @Test
    public void is_correct_load_rate() {
        final IEntityDao<AssetType> co1$ = co$(AssetType.class);
        final AssetType at1 = co1$.findByKey("AT1");

        final IAsset co2$ = co$(Asset.class);

        final Asset asset = co2$.new_().setDesc("some asset desc").setAssetType(at1);

        asset.setLoadingRate("zero");
        assertFalse(asset.isValid().isSuccessful());
        assertEquals(RateValidator.NOT_A_NUMBER, asset.isValid().getMessage());

        asset.setLoadingRate("120");
        assertFalse(asset.isValid().isSuccessful());
        assertEquals(RateValidator.INCORRECT_RANGE, asset.isValid().getMessage());


        asset.setLoadingRate("50");
        assertTrue(asset.isValid().isSuccessful());
    }


}