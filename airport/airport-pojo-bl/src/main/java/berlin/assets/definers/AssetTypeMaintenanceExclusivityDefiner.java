package berlin.assets.definers;

import berlin.tablecodes.assets.AssetTypeMaintenance;
import ua.com.fielden.platform.entity.meta.IAfterChangeEventHandler;
import ua.com.fielden.platform.entity.meta.MetaProperty;

public class AssetTypeMaintenanceExclusivityDefiner implements IAfterChangeEventHandler<Object> {

    @Override
    public void handle(final MetaProperty<Object> prop, final Object value) {
        final AssetTypeMaintenance maintenance = prop.getEntity();
        final boolean allEmpty = maintenance.getRole() == null && maintenance.getBusinessUnit() == null && maintenance.getOrganisation() == null;
        
        if (maintenance.getRole() == null) {
        	maintenance.getProperty("role").setRequired(allEmpty);
        }
        if (maintenance.getBusinessUnit() == null) {
        	maintenance.getProperty("businessUnit").setRequired(allEmpty);
        }
        if (maintenance.getOrganisation() == null) {
        	maintenance.getProperty("organisation").setRequired(allEmpty); 
        }
        
        if (value != null) {
            if ("role".equals(prop.getName())) {
            	maintenance.getProperty("businessUnit").setRequired(false);
            	maintenance.setBusinessUnit(null);
            	maintenance.getProperty("organisation").setRequired(false);
            	maintenance.setOrganisation(null);
            	maintenance.getProperty("role").setRequired(true);
            } else if ("businessUnit".equals(prop.getName())) {
            	maintenance.getProperty("role").setRequired(false);
            	maintenance.setRole(null);
            	maintenance.getProperty("organisation").setRequired(false);
            	maintenance.setOrganisation(null);
            	maintenance.getProperty("businessUnit").setRequired(true);
            } else if ("organisation".equals(prop.getName())) {
            	maintenance.getProperty("role").setRequired(false);
            	maintenance.setRole(null);
            	maintenance.getProperty("businessUnit").setRequired(false);
            	maintenance.setBusinessUnit(null);
            	maintenance.getProperty("organisation").setRequired(true);
            }
        }
    }
}
