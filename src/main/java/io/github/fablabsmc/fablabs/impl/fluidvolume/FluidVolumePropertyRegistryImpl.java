package io.github.fablabsmc.fablabs.impl.fluidvolume;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.FluidVolumePropertyRegistry;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.VolumeProperty;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.property.serializer.VolumePropertySerializer;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.util.DefaultedHashMap;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FluidVolumePropertyRegistryImpl implements FluidVolumePropertyRegistry {
    private final BiMap<Identifier, VolumePropertySerializer<?>> properties = HashBiMap.create();

    private final Map<Fluid, Set<VolumePropertySerializer<?>>> fluidProperties = new DefaultedHashMap<>(new HashSet<>());
    private final Set<VolumePropertySerializer<?>> universalProperties = new HashSet<>();

    @Override
    public void registerProperty(Identifier id, VolumePropertySerializer<?> property, Fluid... fluids) {
        Identifier alreadyRegisteredId = properties.inverse().get(property);
        if (alreadyRegisteredId != null) {
            throw new RuntimeException("The property with ID: " + id + " has already been registered with the ID: " + alreadyRegisteredId);
        } else if (properties.putIfAbsent(id, property) != null) {
            throw new RuntimeException("A property with ID: " + id + " has already been registered.");
        }
        addProperty(property, fluids);
    }

    @Override
    public void addProperty(VolumePropertySerializer<?> property, Fluid... fluids) {
        for (Fluid fluid : fluids) {
            fluidProperties.computeIfAbsent(fluid, key -> new HashSet<>()).add(property);
        }
    }

    @Override
    public void registerUniversalProperty(Identifier id, VolumePropertySerializer<?> property) {
        registerProperty(id, property);
        universalProperties.add(property);
    }

    @Override
    public Set<VolumePropertySerializer<?>> getProperties(Fluid fluid) {
        return Sets.union(fluidProperties.get(fluid), universalProperties);
    }

    @Override
    public VolumePropertySerializer<?> get(Identifier id) {
        return properties.get(id);
    }

    @Override
    public Identifier getId(VolumePropertySerializer<?> property) {
        return properties.inverse().get(property);
    }
}
