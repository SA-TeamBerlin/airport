package berlin.assets;

import berlin.tablecodes.assets.AssetType;
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
import ua.com.fielden.platform.entity.annotation.Readonly;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Asset Number")
@CompanionObject(IAsset.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
@DescRequired
public class Asset extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Asset.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    public static boolean REGULATORY = false;
    public static boolean KEY_SERVICE = true;
    
    @IsProperty
    @MapTo
    @Title(value = "Number", desc = "A unique asset number, auto-generated.")
    @CompositeKeyMember(1)
    @Readonly
    private String number;
    
    @IsProperty
    @Title(value = "Fin Det", desc = "Financial details for this asset")
    private AssetFinDet finDet;

    @Observable
    protected Asset setFinDet(final AssetFinDet finDet) {
        this.finDet = finDet;
        return this;
    }

    public AssetFinDet getFinDet() {
        return finDet;
    }

    @IsProperty
    @MapTo
    private AssetType assetType;

    @Observable
    public Asset setAssetType(final AssetType assetType) {
        this.assetType = assetType;
        return this;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    @Observable
    public Asset setNumber(final String number) {
        this.number = number;
        return this;
    }

    public String getNumber() {
        return number;
    }
    
    public Asset setRegulatory(boolean isRegulatory) {
        Asset.REGULATORY = isRegulatory;
        return this;
    }
    
    public Asset setKeyService(boolean isKeyService) {
        Asset.KEY_SERVICE = isKeyService;
        return this;
    }
    
    public boolean getRegulatory() {
        return Asset.REGULATORY;
    }
    
    public boolean getKeyService() {
        return Asset.KEY_SERVICE;
    }

    @Override
    @Observable
    public Asset setDesc(final String desc) {
        super.setDesc(desc);
        return this;
    }
    
    @Override
    @Observable
    public Asset setActive(boolean active) {
        super.setActive(active);
        return this;
    }
}
