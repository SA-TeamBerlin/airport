package berlin.webapp.config.assets;

import static berlin.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static java.lang.String.format;
import static ua.com.fielden.platform.web.PrefDim.mkDim;

import java.util.Optional;

import com.google.inject.Injector;

import berlin.assets.Asset;
import berlin.common.LayoutComposer;
import berlin.common.StandardActions;
import berlin.main.menu.assets.MiAsset;
import ua.com.fielden.platform.web.PrefDim.Unit;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
/**
 * {@link Asset} Web UI configuration.
 *
 * @author Developers
 *
 */
public class AssetWebUiConfig {

    public final EntityCentre<Asset> centre;
    public final EntityMaster<Asset> master;

    public static AssetWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new AssetWebUiConfig(injector, builder);
    }

    private AssetWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link Asset}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Asset> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkVarGridForCentre(3, 2);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Asset.class);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Asset.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Asset.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Asset.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(Asset.class, standardEditAction);

        final EntityCentreConfig<Asset> ecc = EntityCentreBuilder.centreFor(Asset.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("this").asMulti().autocompleter(Asset.class).also()
                .addCrit("desc").asMulti().text().also()
                .addCrit("loadingRate").asMulti().text().also()
                .addCrit("finDet.initCost").asRange().decimal().also()
                .addCrit("finDet.acquireDate").asRange().date()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("this").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Asset.ENTITY_TITLE))
                    .withAction(standardEditAction).also()
                    .addProp("desc").minWidth(200).also()
                    .addProp("loadingRate").minWidth(150).also()
                    .addProp("finDet.initCost").width(150).also()
                    .addProp("finDet.acquireDate").width(150).also()
                    .addProp("currAssetServiceStatus.startDate").order(2).desc().width(150)     
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiAsset.class, MiAsset.class.getSimpleName(), ecc, injector, null);
    }

    /**
     * Creates entity master for {@link Asset}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<Asset> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkVarGridForMasterFitWidth(1, 1, 1, 1);

        final IMaster<Asset> masterConfig = new SimpleMasterBuilder<Asset>().forEntity(Asset.class)
                .addProp("number").asSinglelineText().also()
                .addProp("loadingRate").asMultilineText().also()
                .addProp("desc").asMultilineText().also()
                .addProp("currAssetServiceStatus.startDate").asDatePicker().also()             
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 300, Unit.PX))
                .done();

        return new EntityMaster<>(Asset.class, masterConfig, injector);
    }
}