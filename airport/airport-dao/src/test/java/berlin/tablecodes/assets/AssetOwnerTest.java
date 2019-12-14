package berlin.tablecodes.assets;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import berlin.tablecodes.owners.BusinessUnit;
import berlin.tablecodes.owners.Organisation;
import berlin.tablecodes.owners.Role;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class AssetOwnerTest extends AbstractDaoTestCase {

    @Test
    public void assert_that_role_creation_is_successful() {
        final Role role = co(Role.class).new_().setName("Role 1").setDesc("Role 1 description");
        assertTrue(role.isValid().isSuccessful());
    }
    
    @Test
    public void assert_that_business_unit_creation_is_successful() {
        final BusinessUnit businessUnit = co(BusinessUnit.class).new_().setName("BusinessUnit 1").setDesc("Business unit 1 description");
        assertTrue(businessUnit.isValid().isSuccessful());
    }
    
    @Test
    public void assert_that_organisation_creation_is_successful() {
        final Organisation organisation = co(Organisation.class).new_().setName("Organisation 1").setDesc("Organisation 1 description");
        assertTrue(organisation.isValid().isSuccessful());
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
