package berlin.webapp.config.tablecodes.assets;

import static berlin.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static java.lang.String.format;
import static ua.com.fielden.platform.web.PrefDim.mkDim;

import java.util.Optional;

import com.google.inject.Injector;

import berlin.common.LayoutComposer;
import berlin.common.StandardActions;
import berlin.main.menu.tablecodes.assets.MiAssetTypeOwnership;
import berlin.tablecodes.assets.AssetType;
import berlin.tablecodes.assets.AssetTypeOwnership;
import berlin.tablecodes.owners.BusinessUnit;
import berlin.tablecodes.owners.Organisation;
import berlin.tablecodes.owners.Role;
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
 * {@link AssetTypeMaintenance} Web UI configuration.
 *
 * @author Developers
 *
 */
public class AssetTypeMaintenanceWebUiConfig {

    public final EntityCentre<AssetTypeMaintenance> centre;
    public final EntityMaster<AssetTypeMaintenance> master;

    public static AssetTypeMaintenanceWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new AssetTypeMaintenanceWebUiConfig(injector, builder);
    }

    private AssetTypeMaintenanceWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link AssetTypeMaintenance}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<AssetTypeMaintenance> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkVarGridForCentre(2, 3);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(AssetTypeMaintenance.class);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(AssetTypeMaintenance.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(AssetTypeMaintenance.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(AssetTypeMaintenance.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(AssetTypeMaintenance.class, standardEditAction);

        final EntityCentreConfig<AssetTypeMaintenance> ecc = EntityCentreBuilder.centreFor(AssetTypeMaintenance.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("assetType").asMulti().autocompleter(AssetType.class).also()
                .addCrit("startDate").asRange().date().also()
                .addCrit("role").asMulti().autocompleter(Role.class).also()
                .addCrit("businessUnit").asMulti().autocompleter(BusinessUnit.class).also()
                .addCrit("organisation").asMulti().autocompleter(Organisation.class)
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("assetType").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", AssetTypeMaintenance.ENTITY_TITLE))
                    .withActionSupplier(builder.getOpenMasterAction(AssetType.class)).also()
                .addProp("startDate").order(2).desc().width(150).also()
                .addProp("role").minWidth(100).also()
                .addProp("businessUnit").minWidth(100).also()
                .addProp("organisation").minWidth(100)
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiAssetTypeMaintenance.class, MiAssetTypeMaintenance.class.getSimpleName(), ecc, injector, null);
    }

    /**
     * Creates entity master for {@link AssetTypeMaintenance}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<AssetTypeMaintenance> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkGridForMasterFitWidth(5, 1);

        final IMaster<AssetTypeMaintenance> masterConfig = new SimpleMasterBuilder<AssetTypeMaintenance>().forEntity(AssetTypeMaintenance.class)
                .addProp("assetType").asAutocompleter().also()
                .addProp("startDate").asDatePicker().also()
                .addProp("role").asAutocompleter().also()
                .addProp("businessUnit").asAutocompleter().also()
                .addProp("organisation").asAutocompleter().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
                .done();

        return new EntityMaster<>(AssetTypeMaintenance.class, masterConfig, injector);
    }
}