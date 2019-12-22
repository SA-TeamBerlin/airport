package berlin.assets;

import berlin.assets.Asset;
import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

public class RegulatoryAssetReporter extends AbstractPersistentEntity<Asset>{
 
    @IsProperty
    @MapTo
    @Title(value = "Asset", desc = "The class of this asset.")
    private Asset asset;
 
 public void reportProblem() {
        if(asset.getRegulatory()) {
         System.out.println("Please check this Asset and repair it ASAP");
        }
    }
}