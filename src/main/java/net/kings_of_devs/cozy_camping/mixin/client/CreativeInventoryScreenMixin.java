package net.kings_of_devs.cozy_camping.mixin.client;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.group.TabbedItemGroup;
import net.kings_of_devs.cozy_camping.group.ItemGroupTabWidget;
import net.kings_of_devs.cozy_camping.group.AbstractTabbedItemGroup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmChatLinkScreen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    private static final Identifier MEDIA_ICON_TEXTURE = CozyCampingMain.id("textures/gui/info_button.png");
    private final String curseforgeLink = "https://www.curseforge.com/minecraft/mc-mods/atbyw";
    private final String githubLink = "https://github.com/Azagwen/ATBYW";
    private final List<TexturedButtonWidget> mediaButtons = Lists.newArrayList();
    private final List<ItemGroupTabWidget> tabButtons = Lists.newArrayList();
    private final boolean enableMediaButtons = false;
    private ItemGroupTabWidget selectedSubtab;

    @Inject(at = @At("HEAD"), method = "setSelectedTab(Lnet/minecraft/item/ItemGroup;)V")
    private void setSelectedTab(ItemGroup group, CallbackInfo cbi) {
        for (var button : this.tabButtons) {
            this.remove(button);
        }
        for (var button : this.mediaButtons) {
            this.remove(button);
        }
        this.tabButtons.clear();
        this.mediaButtons.clear();

        if(group instanceof AbstractTabbedItemGroup tabbedGroup) {
            if(!tabbedGroup.hasInitialized()) {
                tabbedGroup.initialize();
            }

            int i = 0;
            for(var tab : tabbedGroup.getTabs()) {
                var selectTab = i;
                var flipTab = i > 3;
                var xOffset = flipTab ? (this.x + 191) : (this.x - 29);
                var yOffset = flipTab ? (this.y + 12) + ((i - 4) * 30) : (this.y + 12) + (i * 30);
                var tabWidget = new ItemGroupTabWidget(xOffset, yOffset, flipTab, tab, (button)-> {
                    tabbedGroup.setSelectedTab(selectTab);
                    MinecraftClient.getInstance().openScreen(this);
                    ((ItemGroupTabWidget) button).isSelected = true;
                    this.selectedSubtab = (ItemGroupTabWidget) button;
                });

                if(i == tabbedGroup.getSelectedTabIndex()) {
                    this.selectedSubtab = tabWidget;
                    tabWidget.isSelected = true;
                }

                this.tabButtons.add(tabWidget);
                this.addDrawableChild(tabWidget);
                i++;
            }
        }

        if(group instanceof TabbedItemGroup && this.enableMediaButtons) {
            var curseforgeButton = new TexturedButtonWidget(this.x + 175, this.y + 4, 12, 12, 24, 0, 12, MEDIA_ICON_TEXTURE, 64, 64, (button) -> {
                this.client.openScreen(new ConfirmChatLinkScreen((opened) -> {
                    if (opened) {
                        Util.getOperatingSystem().open(curseforgeLink);
                    }

                    this.client.openScreen(this);
                }, this.curseforgeLink, true));
            }, new TranslatableText("itemGroup." + CozyCampingMain.MOD_ID + ".curseforgeLink"));
            var githubButton = new TexturedButtonWidget(this.x + 161, this.y + 4, 12, 12, 12, 0, 12, MEDIA_ICON_TEXTURE, 64, 64, (button) -> {
                this.client.openScreen(new ConfirmChatLinkScreen((opened) -> {
                    if (opened) {
                        Util.getOperatingSystem().open(this.githubLink);
                    }

                    this.client.openScreen(this);
                }, this.githubLink, true));
            }, new TranslatableText("itemGroup." + CozyCampingMain.MOD_ID + ".githubLink"));
            this.mediaButtons.add(curseforgeButton);
            this.mediaButtons.add(githubButton);
            this.addDrawableChild(curseforgeButton);
            this.addDrawableChild(githubButton);
        }
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V")
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo cbi) {
        if (this.enableMediaButtons) {
            this.tabButtons.forEach(button -> {
                if (button.isHovered()) {
                    renderTooltip(matrixStack, button.getMessage(), mouseX, mouseY);
                }
            });
            this.mediaButtons.forEach(button -> {
                if (button.isHovered()) {
                    renderTooltip(matrixStack, button.getMessage(), mouseX, mouseY);
                }
            });
        }
    }

    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }
}