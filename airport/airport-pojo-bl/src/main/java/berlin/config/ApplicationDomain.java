package berlin.config;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import berlin.assets.Asset;
import berlin.assets.AssetFinDet;
import berlin.assets.AssetOverseeing;
import berlin.assets.AssetOwnership;
import berlin.personnel.Person;
import berlin.tablecodes.assets.AssetClass;
import berlin.tablecodes.assets.AssetType;
import berlin.tablecodes.assets.AssetTypeOverseeing;
import berlin.tablecodes.assets.AssetTypeOwnership;
import berlin.tablecodes.assets.master.menu.actions.AssetClassMaster_OpenAssetType_MenuItem;
import berlin.tablecodes.assets.master.menu.actions.AssetClassMaster_OpenMain_MenuItem;
import berlin.tablecodes.assets.ui_actions.OpenAssetClassMasterAction;
import berlin.tablecodes.conditions.ConditionRating;
import berlin.tablecodes.owners.BusinessUnit;
import berlin.tablecodes.owners.Organisation;
import berlin.tablecodes.owners.Role;
import berlin.tablecodes.projects.Project;
import berlin.tablecodes.services.ServiceStatus;
import ua.com.fielden.platform.basic.config.IApplicationDomainProvider;
import ua.com.fielden.platform.domain.PlatformDomainTypes;
import ua.com.fielden.platform.entity.AbstractEntity;

/**
 * A class to register domain entities.
 * 
 * @author TG Team
 * 
 */
public class ApplicationDomain implements IApplicationDomainProvider {
	private static final Set<Class<? extends AbstractEntity<?>>> entityTypes = new LinkedHashSet<>();
	private static final Set<Class<? extends AbstractEntity<?>>> domainTypes = new LinkedHashSet<>();

	private static void add(final Class<? extends AbstractEntity<?>> domainType) {
		entityTypes.add(domainType);
		domainTypes.add(domainType);
	}

	/**
													     * This is a static initialisation block where all entity types should be registered.
													     */
	static {
		entityTypes.addAll(PlatformDomainTypes.types);
		add(Person.class);
		add(AssetClass.class);
		add(AssetType.class);
		add(Asset.class);
		add(OpenAssetClassMasterAction.class);
		add(AssetClassMaster_OpenMain_MenuItem.class);
		add(AssetClassMaster_OpenAssetType_MenuItem.class);
		add(ServiceStatus.class);
		add(ConditionRating.class);
		add(AssetFinDet.class);
		add(Role.class);
		add(BusinessUnit.class);
		add(Organisation.class);
		add(Project.class);
		add(AssetTypeOwnership.class);
		add(AssetOwnership.class);
		add(AssetOverseeing.class);
		add(AssetTypeOverseeing.class);
	}

	@Override
	public List<Class<? extends AbstractEntity<?>>> entityTypes() {
		return Collections.unmodifiableList(entityTypes.stream().collect(Collectors.toList()));
	}

	public List<Class<? extends AbstractEntity<?>>> domainTypes() {
		return Collections.unmodifiableList(domainTypes.stream().collect(Collectors.toList()));
	}
}
