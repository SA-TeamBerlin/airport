package berlin.assets;

import java.util.Date;

import berlin.assets.definers.AssetOwnershipExclusivityDefiner;
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
@CompanionObject(IAssetOwnership.class)
@MapEntityTo
public class AssetOwnership extends AbstractPersistentEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(AssetOwnership.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Title(value = "Asset", desc = "Asset")
    @CompositeKeyMember(1)
    private Asset asset;

    @Observable
    public AssetOwnership setAsset(final Asset asset) {
        this.asset = asset;
        return this;
    }

    public Asset getAsset() {
        return asset;
    }

    @IsProperty
    @MapTo
    @Title(value = "Role", desc = "Role for this asset")
    @AfterChange(AssetOwnershipExclusivityDefiner.class)
    private Role role;

    @Observable
    public AssetOwnership setRole(final Role role) {
        this.role = role;
        return this;
    }

    public Role getRole() {
        return role;
    }

    @IsProperty
    @MapTo
    @Title(value = "Business Unit", desc = "Business unit for this asset")
    @AfterChange(AssetOwnershipExclusivityDefiner.class)
    private BusinessUnit businessUnit;

    @Observable
    public AssetOwnership setBusinessUnit(final BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
        return this;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    @IsProperty
    @MapTo
    @Title(value = "Organisation", desc = "Organisation for this asset")
    @AfterChange(AssetOwnershipExclusivityDefiner.class)
    private Organisation organisation;

    @Observable
    public AssetOwnership setOrganisation(final Organisation organisation) {
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
    public AssetOwnership setStartDate(final Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }
}
