package berlin.tablecodes.assets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import berlin.tablecodes.conditions.ConditionRating;
import berlin.tablecodes.conditions.IConditionRating;
import berlin.tablecodes.services.IServiceStatus;
import berlin.tablecodes.services.ServiceStatus;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class ConditionRatingTest extends AbstractDaoTestCase {

    @Test
    public void test() {
        final ConditionRating cr1 = co$(ConditionRating.class).findByKeyAndFetch(IConditionRating.FETCH_PROVIDER.fetchModel(), 1);
        assertTrue(cr1.isValid().isSuccessful());
        
        final ConditionRating cr2 = co(ConditionRating.class).findByKey(3);
        assertNotNull(cr2);
        assertEquals("3", cr2.getKey().toString());
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
        
        save(new_composite(ConditionRating.class, 1).setDesc("to be repaired"));
        save(new_composite(ConditionRating.class, 3).setDesc("operational"));
    }
}
