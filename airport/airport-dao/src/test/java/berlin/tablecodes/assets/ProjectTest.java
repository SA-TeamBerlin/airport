package berlin.tablecodes.assets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import berlin.tablecodes.projects.Project;
import berlin.test_config.AbstractDaoTestCase;
import berlin.test_config.UniversalConstantsForTesting;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.utils.IUniversalConstants;

public class ProjectTest extends AbstractDaoTestCase {

    @Test
    public void project_start_and_finish_do_not_permit_inverted_periods() {
        final Project project = co(Project.class).new_().setName("PROJECT 1").setDesc("Project 1 description");
        project.setStartDate(date("2019-12-08 00:00:00")).setFinishDate(date("2019-10-08 00:00:00"));
        
        assertFalse(project.isValid().isSuccessful());
        assertTrue(project.getProperty("startDate").isValid());
        assertFalse(project.getProperty("finishDate").isValid());
        assertNull(project.getFinishDate());
        
        project.setStartDate(date("2019-09-08 00:00:00"));
        assertTrue(project.getProperty("startDate").isValid());
        assertTrue(project.getProperty("finishDate").isValid());
        assertEquals(date("2019-09-08 00:00:00"), project.getStartDate());
        assertEquals(date("2019-10-08 00:00:00"), project.getFinishDate());
        assertTrue(project.isValid().isSuccessful());
        
        final Project savedProject = save(project);
        assertTrue(co(Project.class).entityExists(savedProject));
    }
    
    @Test
    public void start_date_is_requried_for_projects() {
        final Project project = co(Project.class).new_().setName("PROJECT 1").setDesc("Project 1 description");
        final Result validationResult = project.isValid();
        assertFalse(validationResult.isSuccessful());
        assertEquals("Required property [Start date] is not specified for entity [Project].", validationResult.getMessage());
        
        project.setStartDate(date("2019-10-01 00:00:00"));
        assertTrue(project.isValid().isSuccessful());
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
