package berlin.tablecodes.assets;

import java.util.Date;

import berlin.assets.definers.AssetTypeMaintenanceExclusivityDefiner;
import berlin.tablecodes.owners.BusinessUnit;
import berlin.tablecodes.owners.Organisation;
import berlin.tablecodes.owners.Role;
import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DateOnly;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.mutator.AfterChange;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Key")
@CompanionObject(IAssetTypeMaintenance.class)
@MapEntityTo
public class AssetTypeMaintenance extends AbstractPersistentEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(AssetTypeMaintenance.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Title(value = "Asset Type", desc = "Asset type")
    @CompositeKeyMember(1)
    private AssetType assetType;

    @Observable
    public AssetTypeMaintenance setAssetType(final AssetType assetType) {
        this.assetType = assetType;
        return this;
    }

    public AssetType getAssetType() {
        return assetType;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Role", desc = "Role of the owner for this asset type")
    @AfterChange(AssetTypeMaintenanceExclusivityDefiner.class)
    private Role role;

    @Observable
    public AssetTypeMaintenance setRole(final Role role) {
        this.role = role;
        return this;
    }

    public Role getRole() {
        return role;
    }

    @IsProperty
    @MapTo
    @Title(value = "Business Unit", desc = "Business unit for this asset type")
    @AfterChange(AssetTypeMaintenanceExclusivityDefiner.class)
    private BusinessUnit businessUnit;

    @Observable
    public AssetTypeMaintenance setBusinessUnit(final BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
        return this;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Organisation", desc = "Organisation that owns this asset type")
    @AfterChange(AssetTypeMaintenanceExclusivityDefiner.class)
    private Organisation organisation;

    @Observable
    public AssetTypeMaintenance setOrganisation(final Organisation organisation) {
        this.organisation = organisation;
        return this;
    }

    public Organisation getOrganisation() {
        return organisation;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Start date", desc = "Start date")
    @CompositeKeyMember(2)
    @DateOnly
    private Date startDate;

    @Observable
    public AssetTypeMaintenance setStartDate(final Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }
}
