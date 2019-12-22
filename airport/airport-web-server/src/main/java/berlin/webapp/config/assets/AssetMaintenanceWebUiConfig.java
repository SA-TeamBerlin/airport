package berlin.webapp.config.assets;

import static berlin.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static java.lang.String.format;
import static ua.com.fielden.platform.web.PrefDim.mkDim;

import java.util.Optional;

import com.google.inject.Injector;

import berlin.assets.Asset;
import berlin.assets.AssetMaintenance;
import berlin.common.LayoutComposer;
import berlin.common.StandardActions;
import berlin.main.menu.assets.MiAssetMaintenance;
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
 * {@link AssetMaintenance} Web UI configuration.
 *
 * @author Developers
 *
 */
public class AssetMaintenanceWebUiConfig {

    public final EntityCentre<AssetMaintenance> centre;
    public final EntityMaster<AssetMaintenance> master;

    public static AssetMaintenanceWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new AssetMaintenanceWebUiConfig(injector, builder);
    }

    private AssetMaintenanceWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link AssetMaintenance}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<AssetMaintenance> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkVarGridForCentre(2, 3);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(AssetMaintenance.class);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(AssetMaintenance.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(AssetMaintenance.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(AssetMaintenance.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(AssetMaintenance.class, standardEditAction);

        final EntityCentreConfig<AssetMaintenance> ecc = EntityCentreBuilder.centreFor(AssetMaintenance.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("asset").asMulti().autocompleter(Asset.class).also()
                .addCrit("startDate").asRange().date().also()
                .addCrit("role").asMulti().autocompleter(Role.class).also()
                .addCrit("businessUnit").asMulti().autocompleter(BusinessUnit.class).also()
                .addCrit("organisation").asMulti().autocompleter(Organisation.class)
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("asset").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", AssetMaintenance.ENTITY_TITLE))
                    .withActionSupplier(builder.getOpenMasterAction(Asset.class)).also()
                .addProp("startDate").order(2).desc().width(150).also()
                .addProp("role").minWidth(100).also()
                .addProp("businessUnit").minWidth(100).also()
                .addProp("organisation").minWidth(100)
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiAssetMaintenance.class, MiAssetMaintenance.class.getSimpleName(), ecc, injector, null);
    }

    /**
     * Creates entity master for {@link AssetMaintenance}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<AssetMaintenance> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkGridForMasterFitWidth(5, 1);

        final IMaster<AssetMaintenance> masterConfig = new SimpleMasterBuilder<AssetMaintenance>().forEntity(AssetMaintenance.class)
        		 .addProp("asset").asAutocompleter().also()
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

        return new EntityMaster<>(AssetMaintenance.class, masterConfig, injector);
    }
}