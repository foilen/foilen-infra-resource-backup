/*
    Foilen Infra Resource Backup
    https://github.com/foilen/foilen-infra-resource-backup
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.backup;

import java.util.Arrays;
import java.util.Collections;

import com.foilen.infra.plugin.v1.core.context.CommonServicesContext;
import com.foilen.infra.plugin.v1.core.plugin.IPPluginDefinitionProvider;
import com.foilen.infra.plugin.v1.core.plugin.IPPluginDefinitionV1;
import com.foilen.infra.resource.backup.editors.BackupToGoogleCloudConfigEditor;
import com.foilen.infra.resource.backup.handlers.BackupToGoogleCloudEventHandler;
import com.foilen.infra.resource.backup.handlers.MachineBackupToGoogleCloudEventHandler;
import com.foilen.infra.resource.backup.resources.BackupToGoogleCloudConfig;

public class FoilenBackupPluginDefinitionProvider implements IPPluginDefinitionProvider {

    @Override
    public IPPluginDefinitionV1 getIPPluginDefinition() {
        IPPluginDefinitionV1 pluginDefinition = new IPPluginDefinitionV1("Foilen", "Backup", "To manage backups", "1.0.0");

        pluginDefinition.addCustomResource(BackupToGoogleCloudConfig.class, BackupToGoogleCloudConfig.RESOURCE_TYPE, //
                Arrays.asList(), //
                Collections.emptyList());

        // Resource editors
        pluginDefinition.addTranslations("/com/foilen/infra/resource/backup/messages");
        pluginDefinition.addResourceEditor(new BackupToGoogleCloudConfigEditor(), BackupToGoogleCloudConfigEditor.EDITOR_NAME);

        // Updater Handler
        pluginDefinition.addUpdateHandler(new BackupToGoogleCloudEventHandler());
        pluginDefinition.addUpdateHandler(new MachineBackupToGoogleCloudEventHandler());

        return pluginDefinition;
    }

    @Override
    public void initialize(CommonServicesContext commonServicesContext) {
    }

}
