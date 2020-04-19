package io.github.fablabsmc.fablabs.api.fluidvolume.v1;

import com.mojang.datafixers.Dynamic;
import net.minecraft.datafixer.NbtOps;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class FluidVolume {
    public static final FluidVolume EMPTY = new FluidVolume(Fluids.EMPTY);

    private Fluid fluid;
    private Fraction amount;

    private boolean empty;

    public FluidVolume(Fluid fluid) {
        this(fluid, Fraction.ONE);
    }

    public FluidVolume(Fluid fluid, Fraction amount) {
        this.fluid = fluid;
        this.amount = amount;
        this.updateEmptyState();
    }

    private FluidVolume(CompoundTag tag) {
        fluid = Registry.FLUID.get(new Identifier(tag.getString("Id")));
        amount = Fraction.deserialize(new Dynamic<>(NbtOps.INSTANCE, tag.getCompound("Amount")));

        this.updateEmptyState();
    }

    public Fluid getFluid() {
        return empty ? Fluids.EMPTY : fluid;
    }

    public Fraction getAmount() {
        return empty ? Fraction.ZERO : amount;
    }

    public boolean isEmpty() {
        if (this == EMPTY) {
            return true;
        } else if (this.getFluid() != null && this.getFluid() != Fluids.EMPTY) {
            return !this.amount.isPositive();
        } else {
            return true;
        }
    }

    public void setAmount(Fraction amount) {
        this.amount = amount;
    }

    public void increment(Fraction incrementBy) {
        amount = amount.add(incrementBy);
    }

    public void decrement(Fraction decrementBy) {
        amount = amount.subtract(decrementBy);
        if (amount.isNegative()) amount = Fraction.ZERO;
    }

    public FluidVolume split(Fraction amount) {
        Fraction min = Fraction.min(amount, this.amount);
        FluidVolume volume = this.copy();
        volume.setAmount(min);
        this.decrement(min);
        return volume;
    }

    //TODO: better equality/stackability methods
    public static boolean areFluidsEqual(FluidVolume left, FluidVolume right) {
        return left.getFluid() == right.getFluid();
    }

    private void updateEmptyState() {
        empty = isEmpty();
    }

    public FluidVolume copy() {
        if (this.isEmpty()) return FluidVolume.EMPTY;
        FluidVolume stack = new FluidVolume(this.fluid, this.amount);
        return stack;
    }

    public static FluidVolume fromTag(CompoundTag tag) {
        return new FluidVolume(tag);
    }

    public CompoundTag toTag(CompoundTag tag) {
        tag.putString("Id", Registry.FLUID.getId(getFluid()).toString());

        tag.put("Amount", amount.serialize(NbtOps.INSTANCE));

        return tag;
    }
}
