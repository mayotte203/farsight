package com.farsight.mixin;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.LongHashMap;
import java.util.HashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cpw.mods.fml.common.Mod;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import io.github.tox1cozz.mixinbooterlegacy.IEarlyMixinLoader;

@Mixin(ChunkProviderClient.class)
public abstract class ChunkProviderClientMixin
{
    private HashMap<Long, Chunk> loadedChunks;

    @Shadow
    public abstract Chunk provideChunk(int p_73154_1_, int p_73154_2_);

    @Shadow
    public abstract void unloadChunk(int p_73234_1_, int p_73234_2_);
    
    @Unique
    private final HashMap<Long,ChunkCoordIntPair> toUnload = new HashMap<Long,ChunkCoordIntPair>(200);

    @Inject(method = "unloadChunk", at = @At("HEAD"), cancellable = true)
    private void onUnload(final int p_73234_1_,final int p_73234_2_, final CallbackInfo ci)
    {
        ci.cancel();
        toUnload.put(ChunkCoordIntPair.chunkXZ2Int(p_73234_1_, p_73234_2_), new ChunkCoordIntPair(p_73234_1_, p_73234_2_)); 
    }

    @Inject(method = "unloadQueuedChunks", at = @At("RETURN"))
    private void onTick(final CallbackInfoReturnable<Boolean> cir)
    {
    	Iterator<Entry<Long,ChunkCoordIntPair> > objectiterator = this.toUnload.entrySet().iterator();
        while (objectiterator.hasNext())
        {
        	ChunkCoordIntPair pos = objectiterator.next().getValue();
        	if (getDistanceSq(pos,Minecraft.getMinecraft().thePlayer) > (64 * 16 * 64 * 16))
            {
            	this.unloadChunk(pos.chunkXPos,pos.chunkZPos);
            	objectiterator.remove();
            }
        }
    }
    
    private static double getDistanceSq(ChunkCoordIntPair pos,Entity entityIn)
    {
        double d0 = (double)(pos.chunkXPos * 16 + 8);
        double d1 = (double)(pos.chunkZPos * 16 + 8);
        double d2 = d0 - entityIn.posX;
        double d3 = d1 - entityIn.posZ;
        return d2 * d2 + d3 * d3;
    }
}
