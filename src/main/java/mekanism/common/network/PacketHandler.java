package mekanism.common.network;

import mekanism.common.Mekanism;
import mekanism.common.network.to_client.PacketClearRecipeCache;
import mekanism.common.network.to_client.PacketFlyingSync;
import mekanism.common.network.to_client.PacketFrequencyItemGuiUpdate;
import mekanism.common.network.to_client.PacketLaserHitBlock;
import mekanism.common.network.to_client.PacketLightningRender;
import mekanism.common.network.to_client.PacketPlayerData;
import mekanism.common.network.to_client.PacketPortableTeleporter;
import mekanism.common.network.to_client.PacketPortalFX;
import mekanism.common.network.to_client.PacketQIOItemViewerGuiSync;
import mekanism.common.network.to_client.PacketRadiationData;
import mekanism.common.network.to_client.PacketResetPlayerClient;
import mekanism.common.network.to_client.PacketSecurityUpdate;
import mekanism.common.network.to_client.PacketStepHeightSync;
import mekanism.common.network.to_client.PacketTransmitterUpdate;
import mekanism.common.network.to_client.PacketTransporterUpdate;
import mekanism.common.network.to_client.PacketUpdateTile;
import mekanism.common.network.to_client.container.PacketUpdateContainer;
import mekanism.common.network.to_client.container.PacketUpdateContainerBatch;
import mekanism.common.network.to_server.PacketAddTrusted;
import mekanism.common.network.to_server.PacketConfigurationUpdate;
import mekanism.common.network.to_server.PacketDropperUse;
import mekanism.common.network.to_server.PacketEditFilter;
import mekanism.common.network.to_server.PacketGearStateUpdate;
import mekanism.common.network.to_server.PacketGuiButtonPress;
import mekanism.common.network.to_server.PacketGuiInteract;
import mekanism.common.network.to_server.PacketGuiItemDataRequest;
import mekanism.common.network.to_server.PacketGuiSetEnergy;
import mekanism.common.network.to_server.PacketGuiSetFrequency;
import mekanism.common.network.to_server.PacketKey;
import mekanism.common.network.to_server.PacketModeChange;
import mekanism.common.network.to_server.PacketNewFilter;
import mekanism.common.network.to_server.PacketOpenGui;
import mekanism.common.network.to_server.PacketPortableTeleporterGui;
import mekanism.common.network.to_server.PacketQIOCraftingWindowSelect;
import mekanism.common.network.to_server.PacketQIOFillCraftingWindow;
import mekanism.common.network.to_server.PacketQIOItemViewerSlotInteract;
import mekanism.common.network.to_server.PacketQIOSetColor;
import mekanism.common.network.to_server.PacketRadialModeChange;
import mekanism.common.network.to_server.PacketRemoveModule;
import mekanism.common.network.to_server.PacketRobit;
import mekanism.common.network.to_server.PacketSecurityMode;
import mekanism.common.network.to_server.PacketTeleporterSetColor;
import mekanism.common.network.to_server.PacketUpdateInventorySlot;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler extends BasePacketHandler {

    private static final SimpleChannel netHandler = createChannel(Mekanism.rl(Mekanism.MODID));

    @Override
    protected SimpleChannel getChannel() {
        return netHandler;
    }

    @Override
    public void initialize() {
        //Client to server messages
        registerClientToServer(PacketAddTrusted.class, PacketAddTrusted::decode);
        registerClientToServer(PacketConfigurationUpdate.class, PacketConfigurationUpdate::decode);
        registerClientToServer(PacketDropperUse.class, PacketDropperUse::decode);
        registerClientToServer(PacketEditFilter.class, PacketEditFilter::decode);
        registerClientToServer(PacketGearStateUpdate.class, PacketGearStateUpdate::decode);
        registerClientToServer(PacketGuiButtonPress.class, PacketGuiButtonPress::decode);
        registerClientToServer(PacketGuiInteract.class, PacketGuiInteract::decode);
        registerClientToServer(PacketGuiItemDataRequest.class, PacketGuiItemDataRequest::decode);
        registerClientToServer(PacketGuiSetEnergy.class, PacketGuiSetEnergy::decode);
        registerClientToServer(PacketGuiSetFrequency.class, PacketGuiSetFrequency::decode);
        registerClientToServer(PacketKey.class, PacketKey::decode);
        registerClientToServer(PacketModeChange.class, PacketModeChange::decode);
        registerClientToServer(PacketNewFilter.class, PacketNewFilter::decode);
        registerClientToServer(PacketOpenGui.class, PacketOpenGui::decode);
        registerClientToServer(PacketPortableTeleporterGui.class, PacketPortableTeleporterGui::decode);
        registerClientToServer(PacketQIOCraftingWindowSelect.class, PacketQIOCraftingWindowSelect::decode);
        registerClientToServer(PacketQIOFillCraftingWindow.class, PacketQIOFillCraftingWindow::decode);
        registerClientToServer(PacketQIOItemViewerSlotInteract.class, PacketQIOItemViewerSlotInteract::decode);
        registerClientToServer(PacketQIOSetColor.class, PacketQIOSetColor::decode);
        registerClientToServer(PacketRadialModeChange.class, PacketRadialModeChange::decode);
        registerClientToServer(PacketRemoveModule.class, PacketRemoveModule::decode);
        registerClientToServer(PacketRobit.class, PacketRobit::decode);
        registerClientToServer(PacketSecurityMode.class, PacketSecurityMode::decode);
        registerClientToServer(PacketTeleporterSetColor.class, PacketTeleporterSetColor::decode);
        registerClientToServer(PacketUpdateInventorySlot.class, PacketUpdateInventorySlot::decode);

        //Server to client messages
        registerServerToClient(PacketClearRecipeCache.class, PacketClearRecipeCache::decode);
        registerServerToClient(PacketFlyingSync.class, PacketFlyingSync::decode);
        registerServerToClient(PacketFrequencyItemGuiUpdate.class, PacketFrequencyItemGuiUpdate::decode);
        registerServerToClient(PacketLaserHitBlock.class, PacketLaserHitBlock::decode);
        registerServerToClient(PacketLightningRender.class, PacketLightningRender::decode);
        registerServerToClient(PacketPlayerData.class, PacketPlayerData::decode);
        registerServerToClient(PacketPortableTeleporter.class, PacketPortableTeleporter::decode);
        registerServerToClient(PacketPortalFX.class, PacketPortalFX::decode);
        registerServerToClient(PacketQIOItemViewerGuiSync.class, PacketQIOItemViewerGuiSync::decode);
        registerServerToClient(PacketRadiationData.class, PacketRadiationData::decode);
        registerServerToClient(PacketResetPlayerClient.class, PacketResetPlayerClient::decode);
        registerServerToClient(PacketSecurityUpdate.class, PacketSecurityUpdate::decode);
        registerServerToClient(PacketStepHeightSync.class, PacketStepHeightSync::decode);
        registerServerToClient(PacketTransmitterUpdate.class, PacketTransmitterUpdate::decode);
        registerServerToClient(PacketTransporterUpdate.class, PacketTransporterUpdate::decode);
        registerServerToClient(PacketUpdateContainer.class, PacketUpdateContainer::decode);
        registerServerToClient(PacketUpdateContainerBatch.class, PacketUpdateContainerBatch::decode);
        registerServerToClient(PacketUpdateTile.class, PacketUpdateTile::decode);
    }
}