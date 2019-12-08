package berlin.tablecodes.projects;

import java.util.Date;

import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DateOnly;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
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
@KeyType(String.class)
@KeyTitle("Key")
@CompanionObject(IProject.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
@DescRequired
public class Project extends AbstractPersistentEntity<String> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Project.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Title(value = "Project", desc = "Project for capex")
    @CompositeKeyMember(1)
    private String name;

    @Observable
    public Project setName(final String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    @Observable
    public Project setDesc(final String desc) {
        super.setDesc(desc);
        return this;
    }

    @IsProperty
    @DateOnly
    @Required
    @MapTo
    @Title(value = "Start date", desc = "Start date of this project")
    private Date startDate;

    @Observable
    public Project setStartDate(final Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    @IsProperty
    @DateOnly
    @MapTo
    @Title(value = "Finish date", desc = "Finish date of this project")
    private Date finishDate;

    @Observable
    public Project setFinishDate(final Date finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}
