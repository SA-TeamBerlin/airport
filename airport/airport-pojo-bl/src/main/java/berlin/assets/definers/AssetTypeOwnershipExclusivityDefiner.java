package berlin.assets.definers;

import berlin.tablecodes.assets.AssetTypeOwnership;
import ua.com.fielden.platform.entity.meta.IAfterChangeEventHandler;
import ua.com.fielden.platform.entity.meta.MetaProperty;

public class AssetTypeOwnershipExclusivityDefiner implements IAfterChangeEventHandler<Object> {

    @Override
    public void handle(final MetaProperty<Object> prop, final Object value) {
        final AssetTypeOwnership ownership = prop.getEntity();
        final boolean allEmpty = ownership.getRole() == null && ownership.getBusinessUnit() == null && ownership.getOrganisation() == null;
        
        if (ownership.getRole() == null) {
            ownership.getProperty("role").setRequired(allEmpty);
        }
        if (ownership.getBusinessUnit() == null) {
            ownership.getProperty("businessUnit").setRequired(allEmpty);
        }
        if (ownership.getOrganisation() == null) {
            ownership.getProperty("organisation").setRequired(allEmpty); 
        }
        
        if (value != null) {
            if ("role".equals(prop.getName())) {
                ownership.getProperty("businessUnit").setRequired(false);
                ownership.setBusinessUnit(null);
                ownership.getProperty("organisation").setRequired(false);
                ownership.setOrganisation(null);
                ownership.getProperty("role").setRequired(true);
            } else if ("businessUnit".equals(prop.getName())) {
                ownership.getProperty("role").setRequired(false);
                ownership.setRole(null);
                ownership.getProperty("organisation").setRequired(false);
                ownership.setOrganisation(null);
                ownership.getProperty("businessUnit").setRequired(true);
            } else if ("organisation".equals(prop.getName())) {
                ownership.getProperty("role").setRequired(false);
                ownership.setRole(null);
                ownership.getProperty("businessUnit").setRequired(false);
                ownership.setBusinessUnit(null);
                ownership.getProperty("organisation").setRequired(true);
            }
        }
    }
}
