package berlin.tablecodes.assets;

import static berlin.assets.validators.AssetFinDetAcquireDateWithinProjectPeriodValidator.ERR_OUTSIDE_PROJECT_PERIOD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import berlin.assets.Asset;
import berlin.assets.AssetFinDet;
import berlin.assets.IAssetFinDet;
import berlin.tablecodes.projects.Project;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class AssetFinDetTest extends AbstractDaoTestCase {

    @Test 
    public void acquired_date_cannot_be_outside_of_project_with_open_period() {
        final Asset asset = save(new_(Asset.class).setDesc("a demo asset"));
        final Project project = save(new_(Project.class).setName("PROJECT 1").setStartDate(date("2019-12-08 00:00:00")).setDesc("project description"));
        
        final AssetFinDet finDet = co$(AssetFinDet.class).findById(asset.getId(), IAssetFinDet.FETCH_PROVIDER.fetchModel());
        finDet.setProject(project);
        assertTrue(finDet.isValid().isSuccessful());
        
        finDet.setAcquireDate(date("2019-12-10 00:00:00"));
        assertTrue(finDet.isValid().isSuccessful());
        
        finDet.setAcquireDate(date("2019-10-10 00:00:00"));
        assertFalse(finDet.isValid().isSuccessful());
        assertEquals(date("2019-12-10 00:00:00"), finDet.getAcquireDate());
        assertEquals(ERR_OUTSIDE_PROJECT_PERIOD, finDet.isValid().getMessage());
    }

    @Test
    public void acquired_date_cannot_be_outside_of_project_period_with_closed_perdiod() {
        final Asset asset = save(new_(Asset.class).setDesc("a demo asset"));
        final Project project = save(new_(Project.class).setName("PROJECT 1")
                .setStartDate(date("2019-12-08 00:00:00"))
                .setFinishDate(date("2020-12-08 00:00:00"))
                .setDesc("project description"));
        
        final AssetFinDet finDet = co$(AssetFinDet.class).findById(asset.getId(), IAssetFinDet.FETCH_PROVIDER.fetchModel());
        finDet.setProject(project);
        assertTrue(finDet.isValid().isSuccessful());
        
        finDet.setAcquireDate(date("2019-12-10 00:00:00"));
        assertTrue(finDet.isValid().isSuccessful());
        
        finDet.setAcquireDate(date("2020-12-10 00:00:00"));
        assertFalse(finDet.isValid().isSuccessful());
        assertEquals(date("2019-12-10 00:00:00"), finDet.getAcquireDate());
        assertEquals(ERR_OUTSIDE_PROJECT_PERIOD, finDet.isValid().getMessage());
    }

    @Test 
    public void acquired_date_is_revalidate_upon_project_change() {
        final Asset asset = save(new_(Asset.class).setDesc("a demo asset"));
        final Project project = save(new_(Project.class).setName("PROJECT 1").setStartDate(date("2019-12-08 00:00:00")).setDesc("project description"));
        
        final AssetFinDet finDet = co$(AssetFinDet.class).findById(asset.getId(), IAssetFinDet.FETCH_PROVIDER.fetchModel());
        finDet.setAcquireDate(date("2019-12-05 00:00:00"));
        assertTrue(finDet.isValid().isSuccessful());
        
        finDet.setProject(project);
        assertFalse(finDet.isValid().isSuccessful());
        assertEquals(project, finDet.getProject());
        assertEquals(ERR_OUTSIDE_PROJECT_PERIOD, finDet.isValid().getMessage());
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
    
    @Test
    public void empty_acquire_date_is_defaulted_to_project_start_date_upon_project_assignment() {
        final Asset asset = save(new_(Asset.class).setDesc("a demo asset"));
        final AssetFinDet finDet = co$(AssetFinDet.class).findById(asset.getId(), IAssetFinDet.FETCH_PROVIDER.fetchModel());
        assertNull(finDet.getAcquireDate());
        assertNull(finDet.getProject());
        
        final Project project = save(new_(Project.class).setName("PROJECT 1").setStartDate(date("2019-12-08 00:00:00")).setDesc("Project description"));
        finDet.setProject(project);
        assertEquals(date("2019-12-08 00:00:00"), finDet.getAcquireDate());
    }

    @Test
    public void non_empty_acquire_date_is_not_changed_to_project_start_date_upon_project_assignment() {
        final Asset asset = save(new_(Asset.class).setDesc("a demo asset"));
        final AssetFinDet finDet = save(co$(AssetFinDet.class).findById(asset.getId(), IAssetFinDet.FETCH_PROVIDER.fetchModel()).setAcquireDate(date("2019-12-10 00:00:00")));
        assertNotNull(finDet.getAcquireDate());
        assertNull(finDet.getProject());
        
        final Project project = save(new_(Project.class).setName("PROJECT 1").setStartDate(date("2019-12-08 00:00:00")).setDesc("Project description"));
        finDet.setProject(project);
        assertEquals(date("2019-12-10 00:00:00"), finDet.getAcquireDate());
        assertFalse(finDet.getProperty("acquireDate").isDirty());
    }

    @Test
    public void acquire_date_does_not_get_mutated_upon_fin_det_retrieval() {
        final Project project = save(new_(Project.class).setName("PROJECT 1").setStartDate(date("2019-12-08 00:00:00")).setDesc("Project description"));
        final Asset asset = save(new_(Asset.class).setDesc("a demo asset"));
        final AssetFinDet finDet = save(co$(AssetFinDet.class).findById(asset.getId(), IAssetFinDet.FETCH_PROVIDER.fetchModel())
                .setAcquireDate(date("2019-12-10 00:00:00"))
                .setProject(project));
        assertNotNull(finDet.getAcquireDate());
        assertNotNull(finDet.getProject());
        
        assertFalse(finDet.getProperty("acquireDate").isDirty());
        assertFalse(finDet.getProperty("project").isDirty());
        
        assertEquals(date("2019-12-10 00:00:00"), finDet.getAcquireDate());
        assertEquals(date("2019-12-08 00:00:00"), finDet.getProject().getStartDate());
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
