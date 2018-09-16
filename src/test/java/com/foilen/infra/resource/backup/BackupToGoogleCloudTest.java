/*
    Foilen Infra Resource Backup
    https://github.com/foilen/foilen-infra-resource-backup
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.backup;

import org.junit.Test;

import com.foilen.infra.plugin.core.system.fake.junits.AbstractIPPluginTest;
import com.foilen.infra.plugin.core.system.junits.JunitsHelper;
import com.foilen.infra.plugin.v1.core.context.ChangesContext;
import com.foilen.infra.resource.backup.resources.BackupToGoogleCloudConfig;
import com.foilen.infra.resource.machine.Machine;

public class BackupToGoogleCloudTest extends AbstractIPPluginTest {

    @Test
    public void test_basic() {

        // Add basic resources
        BackupToGoogleCloudConfig backupToGoogleCloudConfig = new BackupToGoogleCloudConfig();
        backupToGoogleCloudConfig.setUid("abc");
        backupToGoogleCloudConfig.setProjectId("my_project");
        backupToGoogleCloudConfig.setBucketName("my_bucket");
        backupToGoogleCloudConfig.setClientEmail("backup@myuser.iam.gserviceaccount.com");
        backupToGoogleCloudConfig.setClientId("1111111111111");
        backupToGoogleCloudConfig.setPrivateKeyId("111aaa1111aaa111aaa");
        backupToGoogleCloudConfig.setPrivateKey("-----BEGIN PRIVATE KEY-----\\nOZolTL9Evfdaqwasfbn/vhol\\n-----END PRIVATE KEY-----\\n");

        Machine f1 = new Machine("f1.example.com", "127.0.0.1");
        Machine f2 = new Machine("f2.example.com", "127.0.0.2");

        ChangesContext changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceAdd(backupToGoogleCloudConfig);
        changes.resourceAdd(f1);
        changes.resourceAdd(f2);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        JunitsHelper.assertState(getCommonServicesContext(), getInternalServicesContext(), "BackupToGoogleCloudTest-state-test_basic-1.json", getClass(), true);

        // Remove f2
        changes.resourceDelete(f2);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);
        JunitsHelper.assertState(getCommonServicesContext(), getInternalServicesContext(), "BackupToGoogleCloudTest-state-test_basic-2.json", getClass(), true);

        // Remove f1
        changes.resourceDelete(f1);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);
        JunitsHelper.assertState(getCommonServicesContext(), getInternalServicesContext(), "BackupToGoogleCloudTest-state-test_basic-3.json", getClass(), true);

    }

    @Test
    public void test_create_later() {

        // Add basic resources
        BackupToGoogleCloudConfig backupToGoogleCloudConfig = new BackupToGoogleCloudConfig();
        backupToGoogleCloudConfig.setUid("abc");
        backupToGoogleCloudConfig.setProjectId("my_project");
        backupToGoogleCloudConfig.setBucketName("my_bucket");
        backupToGoogleCloudConfig.setClientEmail("backup@myuser.iam.gserviceaccount.com");
        backupToGoogleCloudConfig.setClientId("1111111111111");
        backupToGoogleCloudConfig.setPrivateKeyId("111aaa1111aaa111aaa");
        backupToGoogleCloudConfig.setPrivateKey("-----BEGIN PRIVATE KEY-----\\nOZolTL9Evfdaqwasfbn/vhol\\n-----END PRIVATE KEY-----\\n");

        Machine f1 = new Machine("f1.example.com", "127.0.0.1");
        Machine f2 = new Machine("f2.example.com", "127.0.0.2");

        ChangesContext changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceAdd(f1);
        changes.resourceAdd(f2);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);
        changes.resourceAdd(backupToGoogleCloudConfig);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        JunitsHelper.assertState(getCommonServicesContext(), getInternalServicesContext(), "BackupToGoogleCloudTest-state-test_create_later-1.json", getClass(), true);

        // Remove config
        changes.resourceDelete(backupToGoogleCloudConfig);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);
        JunitsHelper.assertState(getCommonServicesContext(), getInternalServicesContext(), "BackupToGoogleCloudTest-state-test_create_later-2.json", getClass(), true);

    }

}
