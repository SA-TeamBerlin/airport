package berlin.assets.definers;

import berlin.tablecodes.assets.AssetTypeOverseeing;

import ua.com.fielden.platform.entity.meta.IAfterChangeEventHandler;
import ua.com.fielden.platform.entity.meta.MetaProperty;

public class AssetTypeOverseeingExclusivityDefiner implements IAfterChangeEventHandler<Object> {

    @Override
    public void handle(final MetaProperty<Object> prop, final Object value) {
        final AssetTypeOverseeing overseeing = prop.getEntity();
        final boolean allEmpty = overseeing.getRole() == null && overseeing.getBusinessUnit() == null && overseeing.getOrganisation() == null;
        
        if (overseeing.getRole() == null) {
        	overseeing.getProperty("role").setRequired(allEmpty);
        }
        if (overseeing.getBusinessUnit() == null) {
        	overseeing.getProperty("businessUnit").setRequired(allEmpty);
        }
        if (overseeing.getOrganisation() == null) {
        	overseeing.getProperty("organisation").setRequired(allEmpty); 
        }
        
        if (value != null) {
            if ("role".equals(prop.getName())) {
            	overseeing.getProperty("businessUnit").setRequired(false);
            	overseeing.setBusinessUnit(null);
            	overseeing.getProperty("organisation").setRequired(false);
            	overseeing.setOrganisation(null);
            	overseeing.getProperty("role").setRequired(true);
            } else if ("businessUnit".equals(prop.getName())) {
            	overseeing.getProperty("role").setRequired(false);
            	overseeing.setRole(null);
            	overseeing.getProperty("organisation").setRequired(false);
            	overseeing.setOrganisation(null);
            	overseeing.getProperty("businessUnit").setRequired(true);
            } else if ("organisation".equals(prop.getName())) {
            	overseeing.getProperty("role").setRequired(false);
            	overseeing.setRole(null);
            	overseeing.getProperty("businessUnit").setRequired(false);
            	overseeing.setBusinessUnit(null);
            	overseeing.getProperty("organisation").setRequired(true);
            }
        }
    }
}
