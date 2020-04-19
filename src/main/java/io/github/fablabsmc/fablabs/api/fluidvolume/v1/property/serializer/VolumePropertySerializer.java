package io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.serializer;

import com.google.gson.JsonObject;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.VolumeProperty;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;

public abstract class VolumePropertySerializer<T> {
    protected static final String VALUE_KEY = "value";

    public abstract VolumeProperty<T, VolumePropertySerializer<T>> create(T value);

    public abstract CompoundTag toTag(CompoundTag tag, T value);

    public abstract T fromTag(CompoundTag tag);

    public abstract JsonObject toJson(JsonObject json, T value);

    public abstract T fromJson(JsonObject json);

    public abstract void toPacket(PacketByteBuf buf, T value);

    public abstract T fromPacket(PacketByteBuf buf);
}
