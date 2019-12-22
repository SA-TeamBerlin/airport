package berlin.tablecodes.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class which provide record according to the service status
 *
 * @author Yaroslav Boiko
 */

import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.validation.annotation.Final;

@KeyTitle("Service Status Record")
@MapEntityTo
@DescTitle("The Class which make a record about service status")
@DisplayDescription
@DescRequired
public class ServiceStatusRecord {

	private static final Date datetimeOfRecord = new Date();
	private static ArrayList<Date> ArrayWithChanges = new ArrayList<>(100);

	@IsProperty
	@MapTo
	@Title(value = "ServiceStatus", desc = "The status(object) which is needed to record")
	@Final
	private ServiceStatus serviceStatus;

	@IsProperty
	@MapTo
	@Title(value = "Status", desc = "The name of service status")
	@Final
	public String status = serviceStatus.getName();


	public static String getDatetimeofrecord() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(datetimeOfRecord);
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	@Observable
	public void setServiceStatus(ServiceStatus serviceStatus) {
		Date newRecord = new Date();
		ArrayWithChanges.add(newRecord);
		this.serviceStatus = serviceStatus;
	}
}
