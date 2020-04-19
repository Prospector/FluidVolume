package io.github.fablabsmc.fablabs.api.fluidvolume.v1;

import io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.serializer.VolumePropertySerializer;
import io.github.fablabsmc.fablabs.impl.fluidvolume.FluidVolumePropertyRegistryImpl;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

import java.util.Set;

public interface FluidVolumePropertyRegistry {
    FluidVolumePropertyRegistry INSTANCE = new FluidVolumePropertyRegistryImpl();

    void registerProperty(Identifier id, VolumePropertySerializer<?> property, Fluid... fluids);

    void addProperty(VolumePropertySerializer<?> property, Fluid... fluids);

    void registerUniversalProperty(Identifier id, VolumePropertySerializer<?> property);

    Set<VolumePropertySerializer<?>> getProperties(Fluid fluid);

    VolumePropertySerializer<?> get(Identifier id);

    Identifier getId(VolumePropertySerializer<?> property);
}
