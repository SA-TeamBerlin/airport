package berlin.tablecodes.assets;
import org.junit.Test;

import ua.com.fielden.platform.dao.IEntityDao;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.utils.IUniversalConstants;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;

/**
 * This is a test case for {@link AssetClass}.
 * 
 * @author TG-Demo-Team
 *
 */
public class DemonstratingMetaPropertiesTest extends AbstractDaoTestCase {

    @Test
    public void required_by_definition_cannot_be_changed() {
        final IEntityDao<AssetClass> co$ = co$(AssetClass.class);
        final AssetClass ac = co$.new_();
        
         final MetaProperty<String> mpName = ac.getProperty("name");
         final String initialValue = mpName.getValue();
         
         final String prevValue = mpName.getPrevValue();
         
         final boolean isAssigned = mpName.isAssigned();

         ac.setName("new value");

         
         final String initialValue1 = mpName.getValue();
         
         final String prevValue1 = mpName.getPrevValue();
         
         final boolean isAssigned1 = mpName.isAssigned();

         ac.setName("some other name");
         
         final String initialValue2 = mpName.getValue();
         
         final String prevValue2 = mpName.getPrevValue();
         
         final boolean isAssigned2 = mpName.isAssigned();

         ac.setName("dd");
        //final IEntityDao<AssetClass> co = co(AssetClass.class);
    }

    
    @Override
    protected void populateDomain() {
        // Need to invoke super to create a test user that is responsible for data population 
        super.populateDomain();

        // Here is how the Test Case universal constants can be set.
        // In this case the notion of now is overridden, which makes it possible to have an invariant system-time.
        // However, the now value should be after AbstractDaoTestCase.prePopulateNow in order not to introduce any date-related conflicts.
        final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
        constants.setNow(dateTime("2019-10-01 11:30:00"));

        // If the use of saved data population script is indicated then there is no need to proceed with any further data population logic.
        if (useSavedDataPopulationScript()) {
            return;
        }

        // AssetClass population for the test case
//        save(new_composite(AssetClass.class, "AC1").setDesc("The first asset class"));
//        save(new_composite(AssetClass.class, "AC2").setDesc("The second asset class").setCriticality(3));
    }

}

