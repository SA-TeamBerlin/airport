package berlin.tablecodes.assets;

import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.expr;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;

import java.util.Date;

import javax.xml.crypto.Data;

import berlin.assets.Asset;
import berlin.tablecodes.services.ServiceStatus;
import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.Calculated;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DateOnly;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.mutator.AfterChange;
import ua.com.fielden.platform.entity.annotation.titles.PathTitle;
import ua.com.fielden.platform.entity.annotation.titles.Subtitles;
import ua.com.fielden.platform.entity.query.model.EntityResultQueryModel;
import ua.com.fielden.platform.entity.query.model.ExpressionModel;
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
@KeyTitle("Key")
@CompanionObject(IAssetServiceStatus.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
public class AssetServiceStatus extends AbstractPersistentEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(AssetServiceStatus.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Title(value = "Asset", desc = "Asset is referenced in this service status")
    @CompositeKeyMember(1)
    private Asset asset;
    
    @IsProperty
    @MapTo
    @Title(value = "Start Data", desc = "The start data of the service")
    @CompositeKeyMember(2)
    @DateOnly
    private Date startDate;

    @Observable
    public AssetServiceStatus setAsset(final Asset asset) {
        this.asset = asset;
        return this;
    }

    public Asset getAsset() {
        return asset;
    }

    @Observable
    public AssetServiceStatus setStartDate(final Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }
    
    // here is new lines
    @IsProperty
    //@MapTo
    @Calculated
    @Title(value = "Service Status", desc = "Service status of an asset")
    @Subtitles({@PathTitle(path = "name", title = "Service Status")})
    private ServiceStatus currService;
    
    
    private static final EntityResultQueryModel <ServiceStatus> SerSubQuery = select(ServiceStatus.class).
                                                                        model(); 
     
    protected static final ExpressionModel currService_ = expr().model(select(ServiceStatus.class).where().
            notExists(SerSubQuery).model()).model();

    @Observable
    public AssetServiceStatus setCurrService(final ServiceStatus currService) {
        this.currService = currService;
        return this;
    }

    public ServiceStatus getCurrService() {
        return currService;
    }
    
}
