package xyz.tildejustin.settime.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.integrated.IntegratedServer;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.tildejustin.settime.SetTime;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    private @Nullable IntegratedServer server;

    @Inject(method = "startIntegratedServer(Ljava/lang/String;)V", at = @At(value = "HEAD"))
    public void setLevelExists(CallbackInfo ci) {
        SetTime.levelExists = true;
    }

    @Inject(method = "startIntegratedServer(Ljava/lang/String;Lnet/minecraft/util/registry/RegistryTracker$Modifiable;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/client/MinecraftClient$WorldLoadAction;)V", at = @At(value = "TAIL"))
    private void setTime(CallbackInfo ci) {
        if (!SetTime.levelExists) {
            assert server != null;
            server.getOverworld().setTimeOfDay(SetTime.nightTime);
            SetTime.log(Level.INFO, "setting time");
        }
        SetTime.levelExists = false;
    }
}
