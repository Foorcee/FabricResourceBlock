package de.foorcee.resourceblock.mixin.client;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.server.network.packet.ResourcePackStatusC2SPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConfirmScreen.class)
public class MixinConfirmScreen extends Screen{
    @Shadow
    private Text message;

    protected MixinConfirmScreen(Text text_1) {
        super(text_1);
    }


    @Inject(method = "init", at = @At("TAIL"), remap = false)
    private void onInit(CallbackInfo ci) {
        System.out.println(getClass().getName() + " " + message.asFormattedString());
        if(message.asString().equals(I18n.translate("multiplayer.texturePrompt.line2", new Object[0]))){
            this.addButton(new ButtonWidget(this.width / 2 - 155 + 80, this.height / 6 + 120, 150, 20, "Block", (buttonWidget_1) -> {
                ClientSidePacketRegistry.INSTANCE.sendToServer(new ResourcePackStatusC2SPacket(ResourcePackStatusC2SPacket.Status.ACCEPTED));
                ClientSidePacketRegistry.INSTANCE.sendToServer(new ResourcePackStatusC2SPacket(ResourcePackStatusC2SPacket.Status.SUCCESSFULLY_LOADED));
                MinecraftClient.getInstance().openScreen(null);
            }));
        }
    }
}
