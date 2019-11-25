package berlin.tablecodes.assets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import berlin.assets.Asset;
import berlin.assets.AssetDao;
import berlin.assets.IAsset;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class AssetDaoTest extends AbstractDaoTestCase {

    @Test
    public void newly_saved_asset_has_number_generated() {
        final IAsset co$ = co$(Asset.class);
        final Asset asset = co$.new_().setDesc("some description");
        
        final Asset savedAsset = co$.save(asset);
        
        assertNotNull(savedAsset.getNumber());
        assertEquals("000001", savedAsset.getNumber());
    }
    
    @Test
    public void existing_assets_keep_their_original_numbers() {
        final IAsset co$ = co$(Asset.class);
        final Asset asset = co$.new_().setDesc("some description");
        
        final Asset savedAsset = co$.save(asset).setDesc("another description");
        assertTrue(savedAsset.isDirty());
        
        final Asset savedAsset2 = co$.save(savedAsset);
        assertEquals("000001", savedAsset2.getNumber());
    }
    
    @Test
    public void sequentially_created_assets_have_sequential_numbers() {
        final IAsset co$ = co$(Asset.class);
        final Asset asset1 = co$.save(co$.new_().setDesc("asset 1 description"));
        final Asset asset2 = co$.save(co$.new_().setDesc("asset 2 description"));
        
        assertEquals("000001", asset1.getNumber());
        assertEquals("000002", asset2.getNumber());
    }
    
    @Test
    public void new_asset_can_be_saved_after_the_first_failed_attempt() {
        final AssetDao co$ = co$(Asset.class);
        final Asset asset = co$.new_().setDesc("new desc");
        
        // the first attempt to save asset should fail
        try {
            co$.saveWithError(asset);
            fail("Should have failed the first saving attempt.");
        } catch (final Result ex) {
            assertEquals(AssetDao.ERR_FAILED_SAVE, ex.getMessage());
        }
        
        assertFalse(asset.isPersisted());
        assertEquals("000001", asset.getNumber());
        
        final Asset savedAsset = co$.save(asset);
        assertTrue(savedAsset.isPersisted());
        assertTrue(co$.entityExists(savedAsset));
        assertEquals("000001", savedAsset.getNumber());
    }
    
    @Test
    public void concurrent_saving_of_assets_is_supported_even_with_repeated_saving_after_failures() {
        final AssetDao co$ = co$(Asset.class);
        final Asset assetByUser1 = co$.new_().setDesc("new desc");
        
        // the first attempt to save asset should fail
        try {
            co$.saveWithError(assetByUser1);
            fail("Should have failed the first saving attempt.");
        } catch (final Exception ex) {
            assertEquals(AssetDao.ERR_FAILED_SAVE, ex.getMessage());
        }
        
        assertFalse(assetByUser1.isPersisted());
        assertFalse(co$.entityExists(assetByUser1));
        assertEquals("000001", assetByUser1.getNumber());
        
        // another user saves some asset concurrently
        final Asset assetSavedByUser2 = co$.save(co$.new_().setDesc("another new desc"));
        assertTrue(assetSavedByUser2.isPersisted());
        assertTrue(co$.entityExists(assetSavedByUser2));
        assertEquals("000001", assetSavedByUser2.getNumber());
        
        // another attempt by user 1 to save the failed asset
        final Asset savedAssetByUser1 = co$.save(assetByUser1);
        assertTrue(savedAssetByUser1.isPersisted());
        assertTrue(co$.entityExists(savedAssetByUser1));
        assertEquals("000002", savedAssetByUser1.getNumber());
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
