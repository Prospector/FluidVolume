package io.github.fablabsmc.fablabs.api.fluidvolume.v1.property;

import com.google.gson.JsonObject;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.serializer.VolumePropertySerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;

public class VolumeProperty<T, S extends VolumePropertySerializer<T>> {
    private final S serializer;
    protected final VolumePropertyMergeHandler mergeHandler;
    protected T value;

    public VolumeProperty(S serializer, T value) {
        this(serializer, value, null);
    }

    public VolumeProperty(S serializer, T value, VolumePropertyMergeHandler mergeHandler) {
        this.serializer = serializer;
        this.value = value;
        this.mergeHandler = mergeHandler;
    }

    public T getValue() {
        return value;
    }

    /**
     * @param property The property of volume being merged into the existing volume
     * @return The merged product of this property and another. A result of null means they cannot be merged
     */
    public VolumeProperty<T, ?> mergeWith(VolumeProperty<T, ?> property) {
        return mergeHandler == null ? null : mergeHandler.merge(this, property);
    }

    public final CompoundTag toTag(CompoundTag tag) {
        return serializer.toTag(tag, value);
    }

    public final void fromTag(CompoundTag tag) {
        serializer.fromTag(tag);
    }

    public final JsonObject toJson(JsonObject json) {
        return serializer.toJson(json, value);
    }

    public final void fromJson(JsonObject json) {
        serializer.fromJson(json);
    }

    public final void toPacket(PacketByteBuf buf) {
        serializer.toPacket(buf, value);
    }

    public final void fromPacket(PacketByteBuf buf) {
        serializer.fromPacket(buf);
    }

}
