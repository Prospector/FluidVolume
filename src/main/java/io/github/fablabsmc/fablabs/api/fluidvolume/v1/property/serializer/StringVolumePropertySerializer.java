package io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.serializer;

import com.google.gson.JsonObject;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.VolumeProperty;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;

public class StringVolumePropertySerializer extends VolumePropertySerializer<String> {

    @Override
    public VolumeProperty<String, VolumePropertySerializer<String>> create(String value) {
        return new VolumeProperty<>(this, value);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag, String value) {
        tag.putString(VALUE_KEY, value);
        return tag;
    }

    @Override
    public String fromTag(CompoundTag tag) {
        return tag.getString(VALUE_KEY);
    }

    @Override
    public JsonObject toJson(JsonObject json, String value) {
        json.addProperty(VALUE_KEY, value);
        return json;
    }

    @Override
    public String fromJson(JsonObject json) {
        return json.get(VALUE_KEY).getAsString();
    }

    @Override
    public void toPacket(PacketByteBuf buf, String value) {
        buf.writeString(value);
    }

    @Override
    public String fromPacket(PacketByteBuf buf) {
        return buf.readString();
    }
}
