package you.jass.betterhitreg.mixin;

//version 1.21.2+
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

//version 1.21.9+
import net.minecraft.client.render.command.OrderedRenderCommandQueue;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import you.jass.betterhitreg.settings.Toggle;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorMixin {
    //version 1.21.1-
//    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
//            at = @At("HEAD"), cancellable = true)
//    private void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
//        if (Toggle.HIDE_ARMOR.toggled()) ci.cancel();
//    }

    //version 1.21.2 - 1.21.8
//    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",
//            at = @At("HEAD"), cancellable = true)
//    private void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BipedEntityRenderState bipedEntityRenderState, float f, float g, CallbackInfo ci) {
//        if (Toggle.HIDE_ARMOR.toggled()) ci.cancel();
//    }

    //version 1.21.9+
    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int i, BipedEntityRenderState bipedEntityRenderState, float f, float g, CallbackInfo ci) {
        if (Toggle.HIDE_ARMOR.toggled()) ci.cancel();
    }
}