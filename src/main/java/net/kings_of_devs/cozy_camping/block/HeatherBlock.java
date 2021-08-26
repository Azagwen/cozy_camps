package net.kings_of_devs.cozy_camping.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HeatherBlock extends PlantBlock {
    public static final VoxelShape OUTLINE = VoxelShapes.cuboid(0f, 0f, 0f, 0.8f, 0.8f, 0.8f);

    protected HeatherBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return OUTLINE;
    }
}
