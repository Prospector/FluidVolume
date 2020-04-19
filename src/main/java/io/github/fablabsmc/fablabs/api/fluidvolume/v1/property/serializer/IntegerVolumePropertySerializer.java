package io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.serializer;

import com.google.gson.JsonObject;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.VolumeProperty;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.VolumePropertyMergeHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;

public class IntegerVolumePropertySerializer extends VolumePropertySerializer<Integer> {

    protected final VolumePropertyMergeHandler mergeHandler;

    public IntegerVolumePropertySerializer(VolumePropertyMergeHandler mergeHandler) {
        this.mergeHandler = mergeHandler;
    }

    @Override
    public VolumeProperty<Integer, VolumePropertySerializer<Integer>> create(Integer value) {
        return new VolumeProperty<>(this, value, mergeHandler);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag, Integer value) {
        tag.putInt(VALUE_KEY, value);
        return tag;
    }

    @Override
    public Integer fromTag(CompoundTag tag) {
        return tag.getInt(VALUE_KEY);
    }

    @Override
    public JsonObject toJson(JsonObject json, Integer value) {
        json.addProperty(VALUE_KEY, value);
        return json;
    }

    @Override
    public Integer fromJson(JsonObject json) {
        return json.get(VALUE_KEY).getAsInt();
    }

    @Override
    public void toPacket(PacketByteBuf buf, Integer value) {
        buf.writeInt(value);
    }

    @Override
    public Integer fromPacket(PacketByteBuf buf) {
        return buf.readInt();
    }
}
