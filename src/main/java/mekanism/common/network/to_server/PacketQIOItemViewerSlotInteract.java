package mekanism.common.network.to_server;

import java.util.UUID;
import mekanism.common.Mekanism;
import mekanism.common.content.qio.QIOFrequency;
import mekanism.common.inventory.container.QIOItemViewerContainer;
import mekanism.common.lib.inventory.HashedItem;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.util.InventoryUtils;
import mekanism.common.util.StackUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketQIOItemViewerSlotInteract implements IMekanismPacket {

    private final Type type;
    private final UUID typeUUID;
    private final int count;

    private PacketQIOItemViewerSlotInteract(Type type, UUID typeUUID, int count) {
        this.type = type;
        this.typeUUID = typeUUID;
        this.count = count;
    }

    public static PacketQIOItemViewerSlotInteract take(UUID typeUUID, int count) {
        return new PacketQIOItemViewerSlotInteract(Type.TAKE, typeUUID, count);
    }

    public static PacketQIOItemViewerSlotInteract put(int count) {
        return new PacketQIOItemViewerSlotInteract(Type.PUT, null, count);
    }

    public static PacketQIOItemViewerSlotInteract shiftTake(UUID typeUUID) {
        return new PacketQIOItemViewerSlotInteract(Type.SHIFT_TAKE, typeUUID, 0);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        ServerPlayerEntity player = context.getSender();
        if (player != null && player.openContainer instanceof QIOItemViewerContainer) {
            QIOItemViewerContainer container = (QIOItemViewerContainer) player.openContainer;
            QIOFrequency freq = container.getFrequency();
            ItemStack curStack = player.inventory.getItemStack();
            if (freq != null) {
                if (type == Type.TAKE) {
                    ItemStack ret = freq.removeByType(freq.getTypeByUUID(typeUUID), count);
                    if (curStack.isEmpty()) {
                        player.inventory.setItemStack(ret);
                    } else if (InventoryUtils.areItemsStackable(ret, curStack)) {
                        curStack.grow(ret.getCount());
                    }
                    player.connection.sendPacket(new SSetSlotPacket(-1, -1, player.inventory.getItemStack()));
                } else if (type == Type.SHIFT_TAKE) {
                    HashedItem itemType = freq.getTypeByUUID(typeUUID);
                    if (itemType != null) {
                        ItemStack ret = freq.removeByType(itemType, itemType.getStack().getMaxStackSize());
                        ItemStack remainder = container.insertIntoPlayerInventory(ret);
                        if (!remainder.isEmpty()) {
                            remainder = freq.addItem(remainder);
                            if (!remainder.isEmpty()) {
                                Mekanism.logger.error("QIO shift-click transfer resulted in lost items ({}). This shouldn't happen!", remainder);
                            }
                        }
                    }
                } else if (type == Type.PUT) {
                    if (!curStack.isEmpty()) {
                        ItemStack rejects = freq.addItem(StackUtils.size(curStack, Math.min(count, curStack.getCount())));
                        ItemStack newStack = StackUtils.size(curStack, curStack.getCount() - (count - rejects.getCount()));
                        player.inventory.setItemStack(newStack);
                    }
                    player.connection.sendPacket(new SSetSlotPacket(-1, -1, player.inventory.getItemStack()));
                }
            }
        }
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeEnumValue(type);
        switch (type) {
            case TAKE:
                buffer.writeUniqueId(typeUUID);
                buffer.writeVarInt(count);
                break;
            case SHIFT_TAKE:
                buffer.writeUniqueId(typeUUID);
                break;
            case PUT:
                buffer.writeVarInt(count);
                break;
        }
    }

    public static PacketQIOItemViewerSlotInteract decode(PacketBuffer buffer) {
        Type type = buffer.readEnumValue(Type.class);
        UUID typeUUID = null;
        int count = 0;
        switch (type) {
            case TAKE:
                typeUUID = buffer.readUniqueId();
                count = buffer.readVarInt();
                break;
            case SHIFT_TAKE:
                typeUUID = buffer.readUniqueId();
                break;
            case PUT:
                count = buffer.readVarInt();
                break;
        }
        return new PacketQIOItemViewerSlotInteract(type, typeUUID, count);
    }

    public enum Type {
        TAKE,
        SHIFT_TAKE,
        PUT;
    }
}
