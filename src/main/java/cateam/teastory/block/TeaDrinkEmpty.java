package cateam.teastory.block;

import java.util.ArrayList;
import java.util.List;

import cateam.teastory.item.ItemLoader;
import cateam.teastory.item.ItemTeaDrink;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TeaDrinkEmpty extends TeaDrink
{
	public TeaDrinkEmpty(float hardness, String name, Material materialIn, SoundType soundType, int level)
	{
		super(hardness, name, materialIn, soundType, level);
	}
	
	@Override
	public ArrayList getDrops(IBlockAccess world, BlockPos pos, IBlockState blockstate, int fortune)
	{
	    ArrayList drops = new ArrayList();
	    drops.add(new ItemStack(ItemLoader.cup, 1, meta));
	    return drops;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
	    list.add(new ItemStack(ItemLoader.cup, 1, meta));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return ItemLoader.cup;
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		if (worldIn.isRemote)
        {
			return true;
        }
		if (playerIn.isSneaking())
		{
			if (!playerIn.inventory.addItemStackToInventory(new ItemStack(ItemLoader.cup, 1, meta)))
            {
                playerIn.getEntityWorld().spawnEntityInWorld(new EntityItem(playerIn.getEntityWorld(), playerIn.posX + 0.5D, playerIn.posY + 1.5D, playerIn.posZ + 0.5D, 
                		new ItemStack(ItemLoader.cup, 1, meta)));
            }
        	else if (playerIn instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
            }
    		worldIn.setBlockState(pos, Blocks.air.getDefaultState());
    		return true;
		}
		return false;
    }
}