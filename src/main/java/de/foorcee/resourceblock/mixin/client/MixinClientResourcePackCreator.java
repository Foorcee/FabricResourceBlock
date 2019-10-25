package de.foorcee.resourceblock.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientResourcePackContainer;
import net.minecraft.client.resource.ClientResourcePackCreator;
import net.minecraft.client.resource.ResourceIndex;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Mixin(ClientResourcePackCreator.class)
public class MixinClientResourcePackCreator {

    @Shadow
    private ReentrantLock lock;
    @Shadow
    private ClientResourcePackContainer serverContainer;

    @Inject(method = "clear()V", at = @At("HEAD"))
    public void onClear(CallbackInfo ci){
        lock.lock();
        try{
            if(serverContainer != null){
                serverContainer.close();
                System.out.println("[FabricMC] Close ServerResourcePackContainer");
            }
        }finally {
            lock.unlock();
        }
    }

}
