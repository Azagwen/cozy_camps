package net.kings_of_devs.cozy_camping.group;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ItemGroupTabWidget extends ButtonWidget {
    public static final Identifier TEXTURE = CozyCampingMain.id("textures/gui/side_tabs.png");
    public boolean isSelected = false;
    public final boolean flipped;
    private final ItemGroupTab tab;

    public ItemGroupTabWidget(int x, int y, boolean flipped, ItemGroupTab tab, PressAction onPress) {
        super(x, y, 33, 28, new TranslatableText(tab.getTranslationKey()), onPress);
        this.flipped = flipped;
        this.tab = tab;
    }

    protected int getXImage(boolean isHovered) {
        return isSelected ? 2 : (isHovered ? 1 : 0);
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        var client = MinecraftClient.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int xOffset = this.getXImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrixStack, this.x, this.y, xOffset * width + (flipped ? width * 3 : 0), 28, this.width, this.height);
        this.renderBackground(matrixStack, client, mouseX, mouseY);
        int xIconOffset = flipped ? (this.isHovered() || isSelected ? 10 : 7) : (this.isHovered() || isSelected ? 7 : 10);
        client.getItemRenderer().renderGuiItemIcon(tab.getIcon(), this.x + xIconOffset, this.y + 6);
    }
}