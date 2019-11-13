package berlin.tablecodes.assets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import berlin.tablecodes.services.IServiceStatus;
import berlin.tablecodes.services.ServiceStatus;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class ServiceStatusTest extends AbstractDaoTestCase {

    @Test
    public void test() {
        final ServiceStatus ss1 = co$(ServiceStatus.class).findByKeyAndFetch(IServiceStatus.FETCH_PROVIDER.fetchModel(), "SS1");
        assertTrue(ss1.isValid().isSuccessful());
        
        final ServiceStatus ss2 = co(ServiceStatus.class).findByKey("SS2");
        assertNotNull(ss2);
        assertEquals("SS2", ss2.getKey().toString());
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

        // populating the data base for the test case
        save(new_composite(ServiceStatus.class, "SS1").setDesc("planned"));
        save(new_composite(ServiceStatus.class, "SS2").setDesc("operational"));
    }

}
