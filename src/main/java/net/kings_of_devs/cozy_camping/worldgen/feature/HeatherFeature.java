package net.kings_of_devs.cozy_camping.worldgen.feature;

import com.mojang.serialization.Codec;
import net.kings_of_devs.cozy_camping.block.BlockRegistry;
import net.kings_of_devs.cozy_camping.util.FastNoiseLite;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.lwjgl.system.CallbackI;

import java.util.Random;

public class HeatherFeature extends Feature<DefaultFeatureConfig> {
    public HeatherFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();

        FastNoiseLite flowerAmountNoise = new FastNoiseLite((int) context.getWorld().getSeed());
        flowerAmountNoise.SetFrequency(0.1F);
        float flowerValue = flowerAmountNoise.GetNoise(blockPos.getX(), 0, blockPos.getZ()) * 0.5F + 0.5F;

        int i = 0;
        if(blockPos.getY() < 128 && blockPos.getY() > 80 || (structureWorldAccess.getBiome(blockPos).getCategory() == Biome.Category.ICY  || structureWorldAccess.getBiome(blockPos).getCategory() == Biome.Category.EXTREME_HILLS)){
            BlockState flowerState = BlockRegistry.HEATHER.getDefaultState();
            if (structureWorldAccess.isAir(blockPos) && flowerState.canPlaceAt(structureWorldAccess, blockPos)) {
                if(context.getRandom().nextFloat() < flowerValue){
                    structureWorldAccess.setBlockState(blockPos, flowerState, Block.NOTIFY_LISTENERS);
                    ++i;
                }
            }
        }
        return i > 0;
    }
}
