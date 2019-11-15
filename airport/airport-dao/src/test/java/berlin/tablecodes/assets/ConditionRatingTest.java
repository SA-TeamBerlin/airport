package berlin.tablecodes.assets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import berlin.tablecodes.conditions.ConditionRating;
import berlin.tablecodes.conditions.IConditionRating;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class ConditionRatingTest extends AbstractDaoTestCase {

    @Test
    public void test() {
        final ConditionRating cr1 = co$(ConditionRating.class).findByKeyAndFetch(IConditionRating.FETCH_PROVIDER.fetchModel(), "CR1");
        assertTrue(cr1.isValid().isSuccessful());
        
        final ConditionRating cr2 = co(ConditionRating.class).findByKey("CR2");
        assertNotNull(cr2);
        assertEquals("CR2", cr2.getKey().toString());
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
        save(new_composite(ConditionRating.class, "CR1").setDesc("needs repair"));
        save(new_composite(ConditionRating.class, "CR2").setDesc("operational"));
    }
}
