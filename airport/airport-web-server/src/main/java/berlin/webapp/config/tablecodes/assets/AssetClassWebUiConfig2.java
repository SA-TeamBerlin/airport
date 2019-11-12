package berlin.webapp.config.tablecodes.assets;

import static ua.com.fielden.platform.web.PrefDim.mkDim;
import static berlin.common.StandardScrollingConfigs.standardEmbeddedScrollingConfig;
import static berlin.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static berlin.common.StandardActions.actionAddDesc;
import static berlin.common.StandardActions.actionEditDesc;
import static java.lang.String.format;
import static ua.com.fielden.platform.dao.AbstractOpenCompoundMasterDao.enhanceEmbededCentreQuery;
import static ua.com.fielden.platform.entity_centre.review.DynamicQueryBuilder.createConditionProperty;

import java.util.Optional;

import com.google.inject.Injector;

import berlin.tablecodes.assets.AssetClass;
import berlin.tablecodes.assets.AssetType;
import berlin.main.menu.tablecodes.assets.MiAssetClassMaster_AssetType;
import berlin.tablecodes.assets.master.menu.actions.AssetClassMaster_OpenAssetType_MenuItem;
import berlin.tablecodes.assets.ui_actions.OpenAssetClassMasterAction;
import berlin.tablecodes.assets.ui_actions.producers.OpenAssetClassMasterActionProducer;
import berlin.tablecodes.assets.master.menu.actions.AssetClassMaster_OpenMain_MenuItem;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.compound.Compound;
import ua.com.fielden.platform.web.view.master.api.compound.impl.CompoundMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.PrefDim;
import ua.com.fielden.platform.web.PrefDim.Unit;
import berlin.common.LayoutComposer;
import berlin.common.StandardActions;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import static ua.com.fielden.platform.web.centre.api.context.impl.EntityCentreContextSelector.context;

import ua.com.fielden.platform.web.centre.CentreContext;
import ua.com.fielden.platform.web.centre.IQueryEnhancer;
import ua.com.fielden.platform.entity.query.fluent.EntityQueryProgressiveInterfaces.ICompleted;
import ua.com.fielden.platform.entity.query.fluent.EntityQueryProgressiveInterfaces.IWhere0;
import berlin.main.menu.tablecodes.assets.MiAssetClass;
import ua.com.fielden.platform.web.view.master.EntityMaster;
/**
 * {@link AssetClass} Web UI configuration.
 *
 * @author Developers
 *
 */
public class AssetClassWebUiConfig2 {

    private final Injector injector;

    public final EntityCentre<AssetClass> centre;
    public final EntityMaster<AssetClass> master;
    public final EntityMaster<OpenAssetClassMasterAction> compoundMaster;
    public final EntityActionConfig editAssetClassAction;
    private final EntityActionConfig newAssetClassAction;

    public static AssetClassWebUiConfig2 register(final Injector injector, final IWebUiBuilder builder) {
        return new AssetClassWebUiConfig2(injector, builder);
    }

    private AssetClassWebUiConfig2(final Injector injector, final IWebUiBuilder builder) {
        this.injector = injector;

        final PrefDim dims = mkDim(960, 640, Unit.PX);
        editAssetClassAction = Compound.openEdit(OpenAssetClassMasterAction.class, AssetClass.ENTITY_TITLE, actionEditDesc(AssetClass.ENTITY_TITLE), dims);
        newAssetClassAction = Compound.openNew(OpenAssetClassMasterAction.class, "add-circle-outline", AssetClass.ENTITY_TITLE, actionAddDesc(AssetClass.ENTITY_TITLE), dims);
        builder.registerOpenMasterAction(AssetClass.class, editAssetClassAction);

        centre = createAssetClassCentre(builder);
        builder.register(centre);

        master = createAssetClassMaster();
        builder.register(master);

        compoundMaster = CompoundMasterBuilder.<AssetClass, OpenAssetClassMasterAction>create(injector, builder)
            .forEntity(OpenAssetClassMasterAction.class)
            .withProducer(OpenAssetClassMasterActionProducer.class)
            .addMenuItem(AssetClassMaster_OpenMain_MenuItem.class)
                .icon("icons:picture-in-picture")
                .shortDesc(OpenAssetClassMasterAction.MAIN)
                .longDesc(AssetClass.ENTITY_TITLE + " main")
                .withView(master)
            .also()
            .addMenuItem(AssetClassMaster_OpenAssetType_MenuItem.class)
                .icon("icons:view-module")
                .shortDesc(OpenAssetClassMasterAction.ASSETTYPES)
                .longDesc(AssetClass.ENTITY_TITLE + " " + OpenAssetClassMasterAction.ASSETTYPES)
                .withView(createAssetTypeCentre())
            .done();
        builder.register(compoundMaster);
    }

    /**
     * Creates entity centre for {@link AssetClass}.
     *
     * @return
     */
    private EntityCentre<AssetClass> createAssetClassCentre(final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkGridForCentre(1, 2);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(AssetClass.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(AssetClass.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityCentreConfig<AssetClass> ecc = EntityCentreBuilder.centreFor(AssetClass.class)
                //.runAutomatically()
                .addFrontAction(newAssetClassAction)
                .addTopAction(newAssetClassAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("this").asMulti().autocompleter(AssetClass.class).also()
                .addCrit("desc").asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("this").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", AssetClass.ENTITY_TITLE))
                    .withAction(editAssetClassAction).also()
                .addProp("desc").minWidth(300)
                .addPrimaryAction(editAssetClassAction)
                .build();

        return new EntityCentre<>(MiAssetClass.class, MiAssetClass.class.getSimpleName(), ecc, injector, null);
    }

    /**
     * Creates entity master for {@link AssetClass}.
     *
     * @return
     */
    private EntityMaster<AssetClass> createAssetClassMaster() {
        final String layout = LayoutComposer.mkGridForMasterFitWidth(1, 2);

        final IMaster<AssetClass> masterConfig = new SimpleMasterBuilder<AssetClass>().forEntity(AssetClass.class)
                .addProp("key").asSinglelineText().also()
                .addProp("desc").asMultilineText().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .done();

        return new EntityMaster<AssetClass>(AssetClass.class, masterConfig, injector);
    }

    private EntityCentre<AssetType> createAssetTypeCentre() {
        final Class<AssetType> root = AssetType.class;
        final String layout = LayoutComposer.mkVarGridForCentre(2, 1);

        final EntityActionConfig standardNewAction = StandardActions.NEW_WITH_MASTER_ACTION.mkAction(AssetType.class);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(AssetType.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_EMBEDDED_CENTRE_ACTION.mkAction(AssetType.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(AssetType.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityCentreConfig<AssetType> ecc = EntityCentreBuilder.centreFor(root)
                .runAutomatically()
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("crit1").asMulti()/*.autocompleter(Crit1.class)*/.text().also()
                .addCrit("crit2").asMulti()/*.autocompleter(Crit2.class)*/.text().also()
                .addCrit("crit3").asRange().integer()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardEmbeddedScrollingConfig(0))
                .addProp("prop1").order(1).asc().minWidth(80)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", AssetType.ENTITY_TITLE)).also()
                .addProp("prop2").minWidth(80).also()
                .addProp("prop3").minWidth(80)
                .addPrimaryAction(standardEditAction)
                .setQueryEnhancer(AssetClassMaster_AssetTypeCentre_QueryEnhancer.class, context().withMasterEntity().build())
                .build();

        return new EntityCentre<>(MiAssetClassMaster_AssetType.class, MiAssetClassMaster_AssetType.class.getSimpleName(), ecc, injector, null);
    }

    private static class AssetClassMaster_AssetTypeCentre_QueryEnhancer implements IQueryEnhancer<AssetType> {
        @Override
        public ICompleted<AssetType> enhanceQuery(final IWhere0<AssetType> where, final Optional<CentreContext<AssetType, ?>> context) {
            return enhanceEmbededCentreQuery(where, createConditionProperty("assetClass"), context.get().getMasterEntity().getKey());
        }
    }

}