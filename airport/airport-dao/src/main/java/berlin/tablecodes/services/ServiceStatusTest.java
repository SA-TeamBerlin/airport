package berlin.tablecodes.services;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import berlin.tablecodes.assets.AssetClass;
import berlin.tablecodes.assets.AssetType;
import berlin.assets.Asset;
import berlin.test_config.AbstractDaoTestCase;

public class ServiceStatusTest extends AbstractDaoTestCase {

    @Test
    public void service_status_is_provided() {
        final AssetType at1 = co(AssetType.class).findByKey("AT1");
        final ServiceStatus ss1 = co(ServiceStatus.class).findByKey("SS1");
        final Asset savedAsset = save(co(Asset.class).new_().setAssetType(at1).setActive(true).setDesc("First asset"));


        final ServiceStatus assetServiceStatus = co(ServiceStatus.class).new_()
                .setAsset(savedAsset)
                .setStartDate(date("2019-01-01 00:00:00"));

        assertFalse(assetServiceStatus.isValid().isSuccessful());
        assertNull(assetServiceStatus.getProperty("serviceStatus").getValue());

        assetServiceStatus.setServiceStatus(ss1);
        assertNotNull(assetServiceStatus.getProperty("serviceStatus").getValue());


    }

    @Test
    public void regulatory_is_provided() {
        final AssetType at1 = co(AssetType.class).findByKey("AT1");
        final ServiceStatus ss1 = co(ServiceStatus.class).findByKey("SS1");
        final Asset savedAsset = save(co(Asset.class).new_().setAssetType(at1).setActive(true).setDesc("First asset"));
        assertNotNull(savedAsset.getRegulatory());
    }


    @Test
    public void key_service_is_provided() {
        final AssetType at1 = co(AssetType.class).findByKey("AT1");
        final ServiceStatus ss1 = co(ServiceStatus.class).findByKey("SS1");
        final Asset savedAsset = save(co(Asset.class).new_().setAssetType(at1).setActive(true).setDesc("First asset"));
        assertNotNull(savedAsset.getKeyService());
    }



    @Test
    public void start_date_is_provided() {
        final AssetType at1 = co(AssetType.class).findByKey("AT1");
        final ServiceStatus ss1 = co(ServiceStatus.class).findByKey("SS1");
        final Asset savedAsset = save(co(Asset.class).new_().setAssetType(at1).setActive(true).setDesc("First asset"));


        final ServiceStatus assetServiceStatus = co(ServiceStatus.class).new_()
                .setAsset(savedAsset)
                .setServiceStatus(ss1);

        assertFalse(assetServiceStatus.isValid().isSuccessful());
        assertNull(assetServiceStatus.getProperty("startDate").getValue());

        assetServiceStatus.setStartDate(date("2019-01-01 00:00:00"));
        assertNotNull(assetServiceStatus.getProperty("startDate").getValue());

    }



}
