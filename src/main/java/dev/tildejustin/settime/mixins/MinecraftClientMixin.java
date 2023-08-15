package dev.tildejustin.settime.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.integrated.IntegratedServer;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.tildejustin.settime.SetTime;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    private @Nullable IntegratedServer server;

    @Inject(method = "startIntegratedServer(Ljava/lang/String;)V", at = @At(value = "HEAD"))
    public void setLevelExists(CallbackInfo ci) {
        SetTime.levelExists = true;
    }

    @Inject(
            method = {
                    "Lnet/minecraft/class_310;method_29610(Ljava/lang/String;Lnet/minecraft/class_5318$class_5319;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/class_310$class_5366;)V",
                    "Lnet/minecraft/class_310;method_29610(Ljava/lang/String;Lnet/minecraft/class_5455$class_5457;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/class_310$class_5366;)V"
            }, at = @At(value = "TAIL"), remap = false, require = 1)
    private void setTime(CallbackInfo ci) {
        if (!SetTime.levelExists) {
            assert server != null;
            server.getOverworld().setTimeOfDay(SetTime.nightTime);
            SetTime.log(Level.INFO, "setting time");
        }
        SetTime.levelExists = false;
    }
}
