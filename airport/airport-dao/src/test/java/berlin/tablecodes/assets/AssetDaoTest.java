package berlin.tablecodes.assets;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import berlin.assets.Asset;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.dao.IEntityDao;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class AssetDaoTest extends AbstractDaoTestCase {

    @Test
    public void test() {
        IEntityDao<Asset> co$ = co$(Asset.class);
        
        final Asset as2 = co$.new_().setNumber("AS2");
        as2.setDesc("Description");
        final Asset as2saved = co$.save(as2);
        assertNotNull(as2saved.getCreatedBy());
        assertNull(as2saved.getLastUpdatedBy());
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
        save(new_composite(Asset.class, "AS1").setDesc("desc"));
        save(new_composite(Asset.class, "AS2").setDesc("desc"));
    }

}
