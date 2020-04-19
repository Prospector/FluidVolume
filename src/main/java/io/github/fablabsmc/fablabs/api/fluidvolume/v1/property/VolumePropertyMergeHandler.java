package io.github.fablabsmc.fablabs.api.fluidvolume.v1.property;

@FunctionalInterface
public interface VolumePropertyMergeHandler {
    /**
     * @param existing The property of the existing volume
     * @param merging  The property of volume being merged into the existing volume
     * @param <T>      The property type
     * @param <P>      The property
     * @return The merged product of the two properties merging. A result of null means they are unmergeable.
     */
    <T, P extends VolumeProperty<T, ?>> P merge(P existing, P merging);
}
