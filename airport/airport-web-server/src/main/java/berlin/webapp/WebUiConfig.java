package berlin.webapp;

import org.apache.commons.lang.StringUtils;

import berlin.assets.Asset;
import berlin.assets.AssetFinDet;
import berlin.assets.AssetMaintenance;
import berlin.assets.AssetOverseeing;
import berlin.assets.AssetOwnership;
import berlin.config.personnel.PersonWebUiConfig;
import berlin.tablecodes.assets.AssetClass;
import berlin.tablecodes.assets.AssetServiceStatus;
import berlin.tablecodes.assets.AssetType;
import berlin.tablecodes.assets.AssetTypeMaintenance;
import berlin.tablecodes.assets.AssetTypeOverseeing;
import berlin.tablecodes.assets.AssetTypeOwnership;
import berlin.tablecodes.conditions.ConditionRating;
import berlin.tablecodes.owners.BusinessUnit;
import berlin.tablecodes.owners.Organisation;
import berlin.tablecodes.owners.Role;
import berlin.tablecodes.projects.Project;
import berlin.tablecodes.services.ServiceStatus;
import berlin.webapp.config.assets.AssetFinDetWebUiConfig;
import berlin.webapp.config.assets.AssetMaintenanceWebUiConfig;
import berlin.webapp.config.assets.AssetOverseeingWebUiConfig;
import berlin.webapp.config.assets.AssetOwnershipWebUiConfig;
import berlin.webapp.config.assets.AssetWebUiConfig;
import berlin.webapp.config.tablecodes.assets.AssetClassWebUiConfig;
import berlin.webapp.config.tablecodes.assets.AssetServiceStatusWebUiConfig;
import berlin.webapp.config.tablecodes.assets.AssetTypeMaintenanceWebUiConfig;
import berlin.webapp.config.tablecodes.assets.AssetServiceStatusWebUiConfig;
import berlin.webapp.config.tablecodes.assets.AssetTypeOverseeingWebUiConfig;
import berlin.webapp.config.tablecodes.assets.AssetTypeOwnershipWebUiConfig;
import berlin.webapp.config.tablecodes.assets.AssetTypeWebUiConfig;
import berlin.webapp.config.tablecodes.conditions.ConditionRatingWebUiConfig;
import berlin.webapp.config.tablecodes.owners.BusinessUnitWebUiConfig;
import berlin.webapp.config.tablecodes.owners.OrganisationWebUiConfig;
import berlin.webapp.config.tablecodes.owners.RoleWebUiConfig;
import berlin.webapp.config.tablecodes.projects.ProjectWebUiConfig;
import berlin.webapp.config.tablecodes.services.ServiceStatusWebUiConfig;
import ua.com.fielden.platform.basic.config.Workflows;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.resources.webui.AbstractWebUiConfig;
import ua.com.fielden.platform.web.resources.webui.UserRoleWebUiConfig;
import ua.com.fielden.platform.web.resources.webui.UserWebUiConfig;

/**
 * App-specific {@link IWebApp} implementation.
 *
 * @author Generated
 *
 */
public class WebUiConfig extends AbstractWebUiConfig {

    private final String domainName;
    private final String path;
    private final int port;

    public WebUiConfig(final String domainName, final int port, final Workflows workflow, final String path) {
        super("Berlin Airport Asset Management (development)", workflow, new String[] { "berlin/" });
        if (StringUtils.isEmpty(domainName) || StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Both the domain name and application binding path should be specified.");
        }
        this.domainName = domainName;
        this.port = port;
        this.path = path;
    }


    @Override
    public String getDomainName() {
        return domainName;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public int getPort() {
        return port;
    }

    /**
     * Configures the {@link WebUiConfig} with custom centres and masters.
     */
    @Override
    public void initConfiguration() {
        super.initConfiguration();

        final IWebUiBuilder builder = configApp();
        builder.setDateFormat("DD/MM/YYYY").setTimeFormat("HH:mm").setTimeWithMillisFormat("HH:mm:ss")
        // Minimum tablet width defines the boundary below which mobile layout takes place.
        // For example for Xiaomi Redmi 4x has official screen size of 1280x640,
        // still its viewport sizes is twice lesser: 640 in landscape orientation and 360 in portrait orientation.
        // When calculating reasonable transition tablet->mobile we need to consider "real" viewport sizes instead of physical pixel sizes (http://viewportsizes.com).
        .setMinTabletWidth(600);

        // Personnel
        final PersonWebUiConfig personWebUiConfig = PersonWebUiConfig.register(injector(), builder);
        final UserWebUiConfig userWebUiConfig = new UserWebUiConfig(injector());
        final UserRoleWebUiConfig userRoleWebUiConfig = new UserRoleWebUiConfig(injector());
        
        // Asset table codes
        final AssetClassWebUiConfig assetClassWebUiConfig = AssetClassWebUiConfig.register(injector(), builder);
        final AssetTypeWebUiConfig assetTypeWebUiConfig = AssetTypeWebUiConfig.register(injector(), builder);
        final AssetTypeOwnershipWebUiConfig assetTypeOwnershipWebUiConfig = AssetTypeOwnershipWebUiConfig.register(injector(), builder);
        final AssetOwnershipWebUiConfig assetOwnershipWebUiConfig = AssetOwnershipWebUiConfig.register(injector(), builder);
        
        final AssetTypeOverseeingWebUiConfig assetTypeOverseeingWebUiConfig = AssetTypeOverseeingWebUiConfig.register(injector(), builder);
        final AssetOverseeingWebUiConfig assetOverseeingWebUiConfig = AssetOverseeingWebUiConfig.register(injector(), builder);
        
        final AssetTypeMaintenanceWebUiConfig assetTypeMaintenanceWebUiConfig = AssetTypeMaintenanceWebUiConfig.register(injector(), builder);
        final AssetMaintenanceWebUiConfig assetMaintenanceWebUiConfig = AssetMaintenanceWebUiConfig.register(injector(), builder);
        
        // Asset
        final AssetWebUiConfig assetWebUiConfig = AssetWebUiConfig.register(injector(), builder);
        // Service Status
        final ServiceStatusWebUiConfig serviceStatusWebUiConfig = ServiceStatusWebUiConfig.register(injector(), builder);
        // Condition rating
        final ConditionRatingWebUiConfig conditionRatingWebUiConfig = ConditionRatingWebUiConfig.register(injector(), builder);
        // Financial details
        final AssetFinDetWebUiConfig assetFinDetWebUiConfig = AssetFinDetWebUiConfig.register(injector(), builder);
        // Owners
        final RoleWebUiConfig roleWebUiConfig = RoleWebUiConfig.register(injector(), builder);
        final BusinessUnitWebUiConfig businessUnitWebUiConfig = BusinessUnitWebUiConfig.register(injector(), builder);
        final OrganisationWebUiConfig organisationWebUiConfig = OrganisationWebUiConfig.register(injector(), builder);
        // Project related UI
        final ProjectWebUiConfig projectWebUiConfig = ProjectWebUiConfig.register(injector(), builder);
        // Asset Service Status
        final AssetServiceStatusWebUiConfig assetServiceStatusWebUiConfig = AssetServiceStatusWebUiConfig.register(injector(), builder);

        

        // Configure application web resources such as masters and centres
        configApp()
        .addMaster(userWebUiConfig.master)
        .addMaster(userWebUiConfig.rolesUpdater)
        .addMaster(userRoleWebUiConfig.master)
        .addMaster(userRoleWebUiConfig.tokensUpdater)
        // user/personnel module
        .addCentre(userWebUiConfig.centre)
        .addCentre(userRoleWebUiConfig.centre);

        // Configure application menu
        configDesktopMainMenu().
        addModule("Asset Acquisition").
            description("Asset acquisition module").
            icon("mainMenu:equipment").
            detailIcon("mainMenu:equipment").
            bgColor("#FFE680").
            captionBgColor("#FFD42A").menu()
                .addMenuItem(Asset.ENTITY_TITLE).description(String.format("%s Centre", Asset.ENTITY_TITLE))
                .centre(assetWebUiConfig.centre).done()
                .addMenuItem(AssetFinDet.ENTITY_TITLE).description(String.format("%s Centre", AssetFinDet.ENTITY_TITLE))
                .centre(assetFinDetWebUiConfig.centre).done()
                
                .addMenuItem(AssetServiceStatus.ENTITY_TITLE).description(String.format("%s Centre", AssetServiceStatus.ENTITY_TITLE))
                .centre(assetServiceStatusWebUiConfig.centre).done()
                
                .addMenuItem(Project.ENTITY_TITLE).description(String.format("%s Centre", Project.ENTITY_TITLE)).centre(projectWebUiConfig.centre).done()
                .done().done().
                   
            addModule("Users / Personnel").
                description("Provides functionality for managing application security and personnel data.").
                icon("mainMenu:help").
                detailIcon("anotherMainMenu:about").
                bgColor("#FFE680").
                captionBgColor("#FFD42A").menu()
                .addMenuItem("Personnel").description("Personnel related data")
                    .addMenuItem("Personnel").description("Personnel Centre").centre(personWebUiConfig.centre).done()
                .done()
                .addMenuItem("Users").description("Users related data")
                    .addMenuItem("Users").description("User centre").centre(userWebUiConfig.centre).done()
                    .addMenuItem("User Roles").description("User roles centre").centre(userRoleWebUiConfig.centre).done()
                .done().
            done().done().
            addModule("Table Codes").
                description("Table Codes Description").
                icon("mainMenu:tablecodes").
                detailIcon("mainMenu:tablecodes").
                bgColor("#FFE680").
                captionBgColor("#FFD42A").menu()
                .addMenuItem("Asset Table Codes").description("Various master data for assets.")
                    .addMenuItem(AssetClass.ENTITY_TITLE).description(String.format("%s Centre", AssetClass.ENTITY_TITLE))
                    .centre(assetClassWebUiConfig.centre).done()
                    .addMenuItem(AssetType.ENTITY_TITLE).description(String.format("%s Centre", AssetType.ENTITY_TITLE))
                    .centre(assetTypeWebUiConfig.centre).done()
                    .addMenuItem(AssetTypeOwnership.ENTITY_TITLE).description(String.format("%s Centre", AssetTypeOwnership.ENTITY_TITLE))
                    .centre(assetTypeOwnershipWebUiConfig.centre).done()
                    .addMenuItem(AssetOwnership.ENTITY_TITLE).description(String.format("%s Centre", AssetOwnership.ENTITY_TITLE))
                    .centre(assetOwnershipWebUiConfig.centre).done()
                    
                    .addMenuItem(AssetTypeOverseeing.ENTITY_TITLE).description(String.format("%s Centre", AssetTypeOverseeing.ENTITY_TITLE))
                    .centre(assetTypeOverseeingWebUiConfig.centre).done()
                    .addMenuItem(AssetOverseeing.ENTITY_TITLE).description(String.format("%s Centre", AssetOverseeing.ENTITY_TITLE))
                    .centre(assetOverseeingWebUiConfig.centre).done()
                    
                    .addMenuItem(AssetTypeMaintenance.ENTITY_TITLE).description(String.format("%s Centre", AssetTypeMaintenance.ENTITY_TITLE))
                    .centre(assetTypeMaintenanceWebUiConfig.centre).done()
                    .addMenuItem(AssetMaintenance.ENTITY_TITLE).description(String.format("%s Centre", AssetMaintenance.ENTITY_TITLE))
                    .centre(assetMaintenanceWebUiConfig.centre).done()
                    
                    .addMenuItem(ServiceStatus.ENTITY_TITLE).description(String.format("%s Centre", ServiceStatus.ENTITY_TITLE))
                    .centre(serviceStatusWebUiConfig.centre).done()
                    
                    .addMenuItem(AssetServiceStatus.ENTITY_TITLE).description(String.format("%s Centre", AssetServiceStatus.ENTITY_TITLE))
                    .centre(assetServiceStatusWebUiConfig.centre).done()
                    
                    .addMenuItem(ConditionRating.ENTITY_TITLE).description(String.format("%s Centre", ConditionRating.ENTITY_TITLE))
                    .centre(conditionRatingWebUiConfig.centre).done()
                    .addMenuItem(Role.ENTITY_TITLE).description(String.format("%s Centre", Role.ENTITY_TITLE))
                    .centre(roleWebUiConfig.centre).done()
                    .addMenuItem(BusinessUnit.ENTITY_TITLE).description(String.format("%s Centre", BusinessUnit.ENTITY_TITLE))
                    .centre(businessUnitWebUiConfig.centre).done()
                    .addMenuItem(Organisation.ENTITY_TITLE).description(String.format("%s Centre", Organisation.ENTITY_TITLE))
                    .centre(organisationWebUiConfig.centre).done()
                .done().
            done().done()
        .setLayoutFor(Device.DESKTOP, null, "[[[{\"rowspan\":2}], []], [[]]]")
        .setLayoutFor(Device.TABLET, null,  "[[[{\"rowspan\":2}], []], [[]]]")
        .setLayoutFor(Device.MOBILE, null, "[[[]],[[]], [[]]]")
        .minCellWidth(100).minCellHeight(148).done();
    }

}
