package berlin.tablecodes.assets;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import berlin.assets.Asset;
import berlin.assets.AssetFinDet;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class AssetTest extends AbstractDaoTestCase {

    @Test
    public void property_title_is_determined_correctly_even_without_title_annotation() {
        assertEquals(AssetType.ENTITY_TITLE, co$(Asset.class).new_().getProperty("assetType").getTitle());
    }
    
    @Test
    public void asset_fin_det_is_created_and_saved_at_the_same_time_as_asset() {
        final Asset asset = save(new_(Asset.class).setDesc("a demo asset"));
        assertTrue(co(Asset.class).entityExists(asset));
        assertTrue(co(AssetFinDet.class).entityExists(asset.getId()));
    }
    
    @Test
    public void no_fin_det_is_created_and_saved_when_existing_asset_gets_saved() {
        final Asset asset = save(new_(Asset.class).setDesc("a demo asset"));
        assertEquals(Long.valueOf(0), asset.getVersion());
        
        final AssetFinDet finDet = co(AssetFinDet.class).findById(asset.getId());
        assertEquals(Long.valueOf(0), finDet.getVersion());
        
        assertEquals(Long.valueOf(1), save(asset.setDesc("another description")).getVersion());
        assertEquals(Long.valueOf(0), co(AssetFinDet.class).findById(finDet.getId()).getVersion());
    }
    
    @Test
    public void duplicate_fin_det_for_the_same_asset_are_not_permitted() {
        final Asset asset = save(new_(Asset.class).setDesc("a demo asset"));
        final AssetFinDet newFinDet = new_(AssetFinDet.class).setKey(asset);
        try {
            save(newFinDet);
            fail("Should have failed due to duplicate instances");
        } catch (final Exception ex) {
        }
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
