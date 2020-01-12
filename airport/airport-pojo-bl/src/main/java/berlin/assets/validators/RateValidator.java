package berlin.assets.validators;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Set;

import berlin.assets.AssetFinDet;
import berlin.tablecodes.projects.Project;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.entity.query.model.EntityResultQueryModel;
import ua.com.fielden.platform.error.Result;

import static ua.com.fielden.platform.error.Result.failure;
import static ua.com.fielden.platform.error.Result.successful;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;

public class rateValidator extends AbstractBeforeChangeEventHandler<String> {
	
	public static final String NOT_A_NUMBER = "Please enter a number";
    public static final String INCORRECT_RANGE = "Rate must be from 0% to 100%";

    @Override
    public Result handle(final MetaProperty<String> property, String value, Set<Annotation> mutatorAnnotations) {
    	if (!value.substring(value.length() - 1, value.length()).equals("%")) {
            value = value.concat("%");
        }
        try {
            Integer load = Integer.parseInt(value.substring(0, value.length() - 1));
            if (load < 0 || load > 100) {
                return failure(INCORRECT_RANGE);
            }
        } catch (NumberFormatException nfe)
        {
            return failure(NOT_A_NUMBER);
        }
        
        return successful();
    }

}
