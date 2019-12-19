package berlin.tablecodes.assets;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import berlin.tablecodes.owners.BusinessUnit;
import berlin.tablecodes.owners.Organisation;
import berlin.tablecodes.owners.Role;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.dao.exceptions.EntityAlreadyExists;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class AssetTypeOwnershipTest extends AbstractDaoTestCase {

    @Test
    public void ownership_with_either_role_or_bu_or_org_is_acceptable() {
        final AssetType at1 = co(AssetType.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<AssetType>fetchFor("assetType").fetchModel(), "AT1");
        final Role r1 = co(Role.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<Role>fetchFor("role").fetchModel(), "R1");
        final BusinessUnit bu1 = co(BusinessUnit.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<BusinessUnit>fetchFor("businessUnit").fetchModel(), "BU1");
        final Organisation org1 = co(Organisation.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<Organisation>fetchFor("organisation").fetchModel(), "ORG1");
    
        final AssetTypeOwnership o1 = save(co(AssetTypeOwnership.class).new_()
                                                .setAssetType(at1)
                                                .setRole(r1)
                                                .setStartDate(date("2019-12-17 00:00:00")));
        
        final AssetTypeOwnership o2 = save(co(AssetTypeOwnership.class).new_()
                .setAssetType(at1)
                .setOrganisation(org1)
                .setStartDate(date("2019-12-18 00:00:00")));
        
        try {
            final AssetTypeOwnership o3 = save(co(AssetTypeOwnership.class).new_()
                    .setAssetType(at1)
                    .setBusinessUnit(bu1)
                    .setStartDate(date("2019-12-17 00:00:00")));
            fail("the error was expected due to duplicate ownerships");
        } catch (final EntityAlreadyExists ex) {
        }
    }
    
    @Test
    public void exclusivity_holds_for_ownership_properties_for_new_not_persisted_instance() {
        final AssetType at1 = co(AssetType.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<AssetType>fetchFor("assetType").fetchModel(), "AT1");
        final Role r1 = co(Role.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<Role>fetchFor("role").fetchModel(), "R1");
        final BusinessUnit bu1 = co(BusinessUnit.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<BusinessUnit>fetchFor("businessUnit").fetchModel(), "BU1");
        final Organisation org1 = co(Organisation.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<Organisation>fetchFor("organisation").fetchModel(), "ORG1");
    
        final AssetTypeOwnership ownership = co(AssetTypeOwnership.class).new_()
                                                .setAssetType(at1)
                                                .setStartDate(date("2019-12-17 00:00:00"));
        
        assertFalse(ownership.isValid().isSuccessful());
        assertNull(ownership.getRole());
        assertTrue(ownership.getProperty("role").isRequired());
        assertNull(ownership.getBusinessUnit());
        assertTrue(ownership.getProperty("businessUnit").isRequired());
        assertNull(ownership.getOrganisation());
        assertTrue(ownership.getProperty("organisation").isRequired());
        
        ownership.setRole(r1);
        assertTrue(ownership.isValid().isSuccessful());
        assertNotNull(ownership.getRole());
        assertTrue(ownership.getProperty("role").isRequired());
        assertNull(ownership.getBusinessUnit());
        assertFalse(ownership.getProperty("businessUnit").isRequired());
        assertNull(ownership.getOrganisation());
        assertFalse(ownership.getProperty("organisation").isRequired());
        
        ownership.setBusinessUnit(bu1);
        assertTrue(ownership.isValid().isSuccessful());
        assertNull(ownership.getRole());
        assertFalse(ownership.getProperty("role").isRequired());
        assertNotNull(ownership.getBusinessUnit());
        assertTrue(ownership.getProperty("businessUnit").isRequired());
        assertNull(ownership.getOrganisation());
        assertFalse(ownership.getProperty("organisation").isRequired());
        
        ownership.setOrganisation(org1);
        assertTrue(ownership.isValid().isSuccessful());
        assertNull(ownership.getRole());
        assertFalse(ownership.getProperty("role").isRequired());
        assertNull(ownership.getBusinessUnit());
        assertFalse(ownership.getProperty("businessUnit").isRequired());
        assertNotNull(ownership.getOrganisation());
        assertTrue(ownership.getProperty("organisation").isRequired());
    }
    
    @Test
    public void exclusivity_holds_for_ownership_properties_for_persisted_instance() {
        final AssetType at1 = co(AssetType.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<AssetType>fetchFor("assetType").fetchModel(), "AT1");
        final Role r1 = co(Role.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<Role>fetchFor("role").fetchModel(), "R1");
        final BusinessUnit bu1 = co(BusinessUnit.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<BusinessUnit>fetchFor("businessUnit").fetchModel(), "BU1");
        final Organisation org1 = co(Organisation.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.<Organisation>fetchFor("organisation").fetchModel(), "ORG1");
    
        save(co(AssetTypeOwnership.class).new_()
                .setAssetType(at1)
                .setRole(r1)
                .setStartDate(date("2019-12-17 00:00:00")));
        
        final AssetTypeOwnership ownership = co$(AssetTypeOwnership.class).findByKeyAndFetch(IAssetTypeOwnership.FETCH_PROVIDER.fetchModel(), at1, date("2019-12-17 00:00:00"));
        assertNotNull(ownership);
        
        assertTrue(ownership.isValid().isSuccessful());
        assertNotNull(ownership.getRole());
        assertTrue(ownership.getProperty("role").isRequired());
        assertNull(ownership.getBusinessUnit());
        assertFalse(ownership.getProperty("businessUnit").isRequired());
        assertNull(ownership.getOrganisation());
        assertFalse(ownership.getProperty("organisation").isRequired());
        
        ownership.setOrganisation(org1);
        assertTrue(ownership.isValid().isSuccessful());
        assertNull(ownership.getRole());
        assertFalse(ownership.getProperty("role").isRequired());
        assertNull(ownership.getBusinessUnit());
        assertFalse(ownership.getProperty("businessUnit").isRequired());
        assertNotNull(ownership.getOrganisation());
        assertTrue(ownership.getProperty("organisation").isRequired());
        
        assertNotNull(save(ownership).getOrganisation());
    }
    
    @Override
    public boolean saveDataPopulationScriptToFile() {
        return false;
    }

    @Override
    public boolean useSavedDataPopulationScript() {
        return true;
    }

    @Override
    protected void populateDomain() {
        super.populateDomain();

        final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
        constants.setNow(dateTime("2019-10-01 11:30:00"));

        if (useSavedDataPopulationScript()) {
            return;
        }
        
        final AssetClass ac1 = save(new_(AssetClass.class).setActive(true).setName("AC1").setDesc("first asset class"));
        save(new_(AssetType.class).setAssetClass(ac1).setActive(true).setName("AT1").setDesc("first asset type"));
        save(new_(Role.class).setName("R1").setDesc("first role"));
        save(new_(BusinessUnit.class).setName("BU1").setDesc("first business unit"));
        save(new_(Organisation.class).setName("ORG1").setDesc("first organisation"));
    }
}
