package net.kings_of_devs.cozy_camping.block;

import net.kings_of_devs.cozy_camping.block.state.CozyCampProperties;
import net.kings_of_devs.cozy_camping.block.state.TentPiece;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class TentBlock extends Block {
    public static final VoxelShape SHAPE;
    public static final DirectionProperty FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF;
    public static final EnumProperty<TentPiece> PIECE;

    public TentBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER).with(PIECE, TentPiece.CENTER));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        var upperHalf = state.with(HALF, DoubleBlockHalf.UPPER);
        //Upper
        world.setBlockState(pos.up(), upperHalf, Block.NOTIFY_ALL);
        world.setBlockState(pos.up().north(), upperHalf.with(PIECE, TentPiece.NORTH), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().south(), upperHalf.with(PIECE, TentPiece.SOUTH), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().east(), upperHalf.with(PIECE, TentPiece.EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().west(), upperHalf.with(PIECE, TentPiece.WEST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().north().east(), upperHalf.with(PIECE, TentPiece.NORTH_EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().north().west(), upperHalf.with(PIECE, TentPiece.NORTH_WEST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().south().east(), upperHalf.with(PIECE, TentPiece.SOUTH_EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.up().south().west(), upperHalf.with(PIECE, TentPiece.SOUTH_WEST), Block.NOTIFY_ALL);
        //Lower
        world.setBlockState(pos.north(), state.with(PIECE, TentPiece.NORTH), Block.NOTIFY_ALL);
        world.setBlockState(pos.south(), state.with(PIECE, TentPiece.SOUTH), Block.NOTIFY_ALL);
        world.setBlockState(pos.east(), state.with(PIECE, TentPiece.EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.west(), state.with(PIECE, TentPiece.WEST), Block.NOTIFY_ALL);
        world.setBlockState(pos.north().east(), state.with(PIECE, TentPiece.NORTH_EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.north().west(), state.with(PIECE, TentPiece.NORTH_WEST), Block.NOTIFY_ALL);
        world.setBlockState(pos.south().east(), state.with(PIECE, TentPiece.SOUTH_EAST), Block.NOTIFY_ALL);
        world.setBlockState(pos.south().west(), state.with(PIECE, TentPiece.SOUTH_WEST), Block.NOTIFY_ALL);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        var isUpEmpty = world.getBlockState(pos.up()).isAir();
        var isNorthEmpty = world.getBlockState(pos.up().north()).isAir() && world.getBlockState(pos.north()).isAir();
        var isSouthEmpty = world.getBlockState(pos.up().south()).isAir() && world.getBlockState(pos.south()).isAir();
        var isEastEmpty = world.getBlockState(pos.up().east()).isAir() && world.getBlockState(pos.east()).isAir();
        var isWestEmpty = world.getBlockState(pos.up().west()).isAir() && world.getBlockState(pos.west()).isAir();
        var isNorthEastEmpty = world.getBlockState(pos.up().north().east()).isAir() && world.getBlockState(pos.north().east()).isAir();
        var isNorthWestEmpty = world.getBlockState(pos.up().north().west()).isAir() && world.getBlockState(pos.north().west()).isAir();
        var isSouthEastEmpty = world.getBlockState(pos.up().south().east()).isAir() && world.getBlockState(pos.south().east()).isAir();
        var isSouthWestEmpty = world.getBlockState(pos.up().south().west()).isAir() && world.getBlockState(pos.south().west()).isAir();
        //Ground
        var isGroundNorthValid = world.getBlockState(pos.down().north()).isSideSolidFullSquare(world, pos.down().north(), Direction.UP);
        var isGroundSouthValid = world.getBlockState(pos.down().south()).isSideSolidFullSquare(world, pos.down().south(), Direction.UP);
        var isGroundEastValid = world.getBlockState(pos.down().east()).isSideSolidFullSquare(world, pos.down().east(), Direction.UP);
        var isGroundWestValid = world.getBlockState(pos.down().west()).isSideSolidFullSquare(world, pos.down().west(), Direction.UP);
        var isGroundNorthEastValid = world.getBlockState(pos.down().north().east()).isSideSolidFullSquare(world, pos.down().north().east(), Direction.UP);
        var isGroundNorthWestValid = world.getBlockState(pos.down().north().west()).isSideSolidFullSquare(world, pos.down().north().west(), Direction.UP);
        var isGroundSouthEastValid = world.getBlockState(pos.down().south().east()).isSideSolidFullSquare(world, pos.down().south().east(), Direction.UP);
        var isGroundSouthWestValid = world.getBlockState(pos.down().south().west()).isSideSolidFullSquare(world, pos.down().south().west(), Direction.UP);

        var isUpperEmpty = isUpEmpty && isNorthEmpty && isSouthEmpty && isEastEmpty && isWestEmpty && isNorthEastEmpty && isNorthWestEmpty && isSouthEastEmpty && isSouthWestEmpty;
        var isGroundValid = isGroundNorthValid && isGroundSouthValid && isGroundEastValid && isGroundWestValid && isGroundNorthEastValid && isGroundNorthWestValid && isGroundSouthEastValid && isGroundSouthWestValid;
        return isUpperEmpty && isGroundValid;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        var half = state.get(HALF) == DoubleBlockHalf.UPPER ? Direction.DOWN : Direction.UP;

        switch (state.get(PIECE)) {
            case CENTER -> this.breakFromMiddle(half, world, pos, player);
            case NORTH -> this.breakFromSide(Direction.SOUTH, Direction.EAST, half, world, pos, player);
            case SOUTH -> this.breakFromSide(Direction.NORTH, Direction.EAST, half, world, pos, player);
            case EAST -> this.breakFromSide(Direction.WEST, Direction.NORTH, half, world, pos, player);
            case WEST -> this.breakFromSide(Direction.EAST, Direction.NORTH, half, world, pos, player);
            case NORTH_EAST -> this.breakFromCorner(Direction.SOUTH, Direction.WEST, half, world, pos, player);
            case NORTH_WEST -> this.breakFromCorner(Direction.SOUTH, Direction.EAST, half, world, pos, player);
            case SOUTH_EAST -> this.breakFromCorner(Direction.NORTH, Direction.WEST, half, world, pos, player);
            case SOUTH_WEST -> this.breakFromCorner(Direction.NORTH, Direction.EAST, half, world, pos, player);
        }

        super.onBreak(world, pos, state, player);
    }

    //this is a mess, please don't stare at it for too long
    private void breakFromCorner(Direction dir1, Direction dir2, Direction half, World world, BlockPos pos, PlayerEntity player) {
        var canLoot = !player.isCreative();
        world.breakBlock(pos.offset(dir1), canLoot, player);
        world.breakBlock(pos.offset(dir1, 1), canLoot, player);
        world.breakBlock(pos.offset(dir1, 2), canLoot, player);
        world.breakBlock(pos.offset(dir1).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(dir1, 1).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(dir1, 2).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(dir1).offset(dir2, 1), canLoot, player);
        world.breakBlock(pos.offset(dir1, 1).offset(dir2, 1), canLoot, player);
        world.breakBlock(pos.offset(dir1, 2).offset(dir2, 1), canLoot, player);
        world.breakBlock(pos.offset(dir1).offset(dir2, 2), canLoot, player);
        world.breakBlock(pos.offset(dir1, 1).offset(dir2, 2), canLoot, player);
        world.breakBlock(pos.offset(dir1, 2).offset(dir2, 2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 1), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 1).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 2).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1).offset(dir2, 1), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 1).offset(dir2, 1), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 2).offset(dir2, 1), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1).offset(dir2, 2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 1).offset(dir2, 2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 2).offset(dir2, 2), canLoot, player);
    }

    //same comment as for the method above here
    private void breakFromSide(Direction dir1, Direction dir2, Direction half, World world, BlockPos pos, PlayerEntity player) {
        var canLoot = !player.isCreative();
        world.breakBlock(pos.offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(dir2.getOpposite()), canLoot, player);
        world.breakBlock(pos.offset(dir1), canLoot, player);
        world.breakBlock(pos.offset(half), canLoot, player);
        world.breakBlock(pos.offset(dir1).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(dir1).offset(dir2.getOpposite()), canLoot, player);
        world.breakBlock(pos.offset(dir1, 1), canLoot, player);
        world.breakBlock(pos.offset(dir1, 1).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(dir1, 1).offset(dir2.getOpposite()), canLoot, player);
        world.breakBlock(pos.offset(dir1, 2), canLoot, player);
        world.breakBlock(pos.offset(dir1, 2).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(dir1, 2).offset(dir2.getOpposite()), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir2.getOpposite()), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1).offset(dir2.getOpposite()), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 1), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 1).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 1).offset(dir2.getOpposite()), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 2).offset(dir2), canLoot, player);
        world.breakBlock(pos.offset(half).offset(dir1, 2).offset(dir2.getOpposite()), canLoot, player);
    }

    private void breakFromMiddle(Direction half, World world, BlockPos pos, PlayerEntity player) {
        var canLoot = !player.isCreative();
        world.breakBlock(pos.north(), canLoot, player);
        world.breakBlock(pos.south(), canLoot, player);
        world.breakBlock(pos.east(), canLoot, player);
        world.breakBlock(pos.west(), canLoot, player);
        world.breakBlock(pos.north().east(), canLoot, player);
        world.breakBlock(pos.north().west(), canLoot, player);
        world.breakBlock(pos.south().east(), canLoot, player);
        world.breakBlock(pos.south().west(), canLoot, player);
        world.breakBlock(pos.offset(half), canLoot, player);
        world.breakBlock(pos.offset(half).north(), canLoot, player);
        world.breakBlock(pos.offset(half).south(), canLoot, player);
        world.breakBlock(pos.offset(half).east(), canLoot, player);
        world.breakBlock(pos.offset(half).west(), canLoot, player);
        world.breakBlock(pos.offset(half).north().east(), canLoot, player);
        world.breakBlock(pos.offset(half).north().west(), canLoot, player);
        world.breakBlock(pos.offset(half).south().east(), canLoot, player);
        world.breakBlock(pos.offset(half).south().west(), canLoot, player);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, PIECE);
    }

    static {
        SHAPE = VoxelShapes.cuboid(0D, 0D, 0D, 1D, 1D, 1D);
        HALF = Properties.DOUBLE_BLOCK_HALF;
        FACING = Properties.HORIZONTAL_FACING;
        PIECE = CozyCampProperties.TENT_PIECE;
    }
}
