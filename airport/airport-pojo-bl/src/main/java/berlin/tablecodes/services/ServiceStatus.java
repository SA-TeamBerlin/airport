package berlin.tablecodes.services;

import berlin.assets.Asset;
import berlin.assets.AssetMaintenance;
import berlin.tablecodes.assets.AssetType;
import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.DateOnly;
import java.util.Date;

import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Service Status")
@CompanionObject(IServiceStatus.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
@DescRequired
public class ServiceStatus extends AbstractPersistentEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(ServiceStatus.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Title(value = "Service Status", desc = "Desc")
    @CompositeKeyMember(1)
    private ServiceStatus name;

    @IsProperty
    @MapTo
    @Title(value = "Asset", desc = "Associated asset")
    @CompositeKeyMember(1)
    private Asset asset;

    @IsProperty
    @MapTo
    @Title(value = "Start Date", desc = "The start date of the service")
    @CompositeKeyMember(2)
    @DateOnly
    private Date startDate;

    @Observable
    public ServiceStatus setName(final ServiceStatus name) {
        this.name = name;
        return this;
    }

    public ServiceStatus getName() {
        return name;
    }

    @Override
    @Observable
    public ServiceStatus setDesc(String desc) {
        super.setDesc(desc);
        return this;
    }

    @Observable
    public ServiceStatus setAsset(final Asset asset) {
        this.asset = asset;
        return this;
    }

    public Asset getAsset() {
        return asset;
    }


    @Observable
    public ServiceStatus setStartDate(final Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    @Observable
    public ServiceStatus setServiceStatus(final ServiceStatus serviceStatus) {
        this.name = serviceStatus;
        return this;
    }

    public ServiceStatus getServiceStatus() {
        return name;
    }



}
