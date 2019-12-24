package berlin.tablecodes.assets;

import berlin.tablecodes.validators.LongerThanValidator;
import berlin.tablecodes.validators.NoSpacesValidator;
import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.UpperCase;
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.entity.annotation.mutator.IntParam;
import ua.com.fielden.platform.entity.validation.annotation.Final;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@CompanionObject(IAssetClass.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
@DescRequired
@KeyTitle("Asset Class")
public class AssetClass extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(AssetClass.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    @IsProperty
    @MapTo
    @Title(value = "Name", desc = "Asset class name")
    @CompositeKeyMember(1)
    @BeforeChange({
        @Handler(NoSpacesValidator.class),
        @Handler(value = LongerThanValidator.class, integer = @IntParam(name = "minLength", value = 3))})
    @UpperCase
    private String name;

    @IsProperty
    @MapTo
    @Title(value = "Criticaly", desc = "Indicated how critical assets of this class are.")
    @Final
    private Integer criticality;

    //(c) Yaroslav Boiko: it is special realization for getReportAboutError
    @Title(value = "Type", desc = "Indicated if asset is regulatory(True) or not(False)")
    private boolean type;

    //(c) Yaroslav Boiko: it is special realization for getReportAboutError
    @Title(value = "OccuredError", desc = "Indicated if it was not resolved problem with asset. If there is a problem, then the occuredError is True vice versa False")
    private boolean occuredError;

    //(c) Yaroslav Boiko: The method which helps to understand for users that they have a problem with their assets
    public String getReportAboutError() {
      String message;
      if (this.type & this.occuredError) {
        message = "Your ServiceStatus is interrupted";
      } else if (this.type == false){
        message = "Sorry.. but your Asset is not regulatory";
      } else  {
        message = "Congrutulations.. Today without problem";
      }
      return message;
    }

    @Observable
    public AssetClass setType(final boolean type) {
        this.type = type;
        return this;
    }

    public boolean getType() {
        return type;
    }

    @Observable
    public AssetClass setOccuredError(final boolean occuredError) {
        this.occuredError = occuredError;
        return this;
    }

    public boolean getOccuredError() {
        return occuredError;
    }
    
    @Observable
    public AssetClass setCriticality(final Integer criticality) {
        this.criticality = criticality;
        return this;
    }

    public Integer getCriticality() {
        return criticality;
    }

    @Observable
    public AssetClass setName(final String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    @Observable
    public AssetClass setDesc(String desc) {
        super.setDesc(desc);
        return this;
    }

    @Override
    @Observable
    public AssetClass setActive(boolean active) {
        super.setActive(active);
        return this;
    }
}
