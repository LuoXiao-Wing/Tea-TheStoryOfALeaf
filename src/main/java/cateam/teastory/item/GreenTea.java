package cateam.teastory.item;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import cateam.teastory.achievement.AchievementLoader;
import cateam.teastory.block.BlockLoader;
import cateam.teastory.common.ConfigLoader;
import cateam.teastory.creativetab.CreativeTabsLoader;
import cateam.teastory.potion.PotionLoader;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class GreenTea extends ItemTeaDrink
{
    public GreenTea()
    {
        super("green_tea");
    }

    @Override
    protected void onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if(!world.isRemote)
        {
        	int tier = itemstack.getItemDamage();
        	addPotion(tier, world, entityplayer);
        }
    }
    
    public static void addPotion(int tier, World world, EntityPlayer entityplayer)
    {
    	ItemHandlerHelper.giveItemToPlayer(entityplayer, new ItemStack(ItemLoader.tea_residue, 1, 0));
    	switch(tier)
    	{
    		case 1:
    		{
    			entityplayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (int)Math.max(0, ConfigLoader.TeaDrink_Time * 1.25F), 0)); 
            	entityplayer.addPotionEffect(new PotionEffect(PotionLoader.PotionAgility, (int)Math.max(0, ConfigLoader.TeaDrink_Time * 3.75F), 0)); 
            	return;
    		}
    		case 2:
    		{
    			entityplayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (int)Math.max(0, ConfigLoader.TeaDrink_Time * 0.5F), 1)); 
            	entityplayer.addPotionEffect(new PotionEffect(PotionLoader.PotionAgility, (int)Math.max(0, ConfigLoader.TeaDrink_Time * 1.5F), 1)); 
            	return;
    		}
    		case 3:
    		{
    			entityplayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (int)Math.max(0, ConfigLoader.TeaDrink_Time * 0.75F), 1)); 
            	entityplayer.addPotionEffect(new PotionEffect(PotionLoader.PotionAgility, (int)Math.max(0, ConfigLoader.TeaDrink_Time * 2.25F), 1)); 
            	return;
    		}
    		default:
    		{
    			entityplayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, (int)Math.max(0, ConfigLoader.TeaDrink_Time), 0)); 
            	entityplayer.addPotionEffect(new PotionEffect(PotionLoader.PotionAgility, (int)Math.max(0, ConfigLoader.TeaDrink_Time * 3), 0)); 
            	return;
    		}
    	}
    }
    
    @Override
    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
    	((EntityPlayer) entityLiving).addStat(AchievementLoader.greenTea);
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
    
    public Block getBlock(int meta)
	{
		switch(meta)
		{
		    case 1:
		    	return BlockLoader.greentea_stone_cup;
		    case 2:
		    	return BlockLoader.greentea_glass_cup;
		    case 3:
		    	return BlockLoader.greentea_porcelain_cup;
		    default:
		    	return BlockLoader.greentea_wood_cup;
		}
	}
    
    @Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if (playerIn.isSneaking())
		{
		    Block drinkblock = getBlock(stack.getItemDamage());
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos))
            {
                pos = pos.offset(facing);
            }

            if (stack.stackSize != 0 && playerIn.canPlayerEdit(pos, facing, stack) && worldIn.canBlockBePlaced(drinkblock, pos, false, facing, (Entity)null, stack))
            {
                int i = this.getMetadata(stack.getMetadata());
                IBlockState iblockstate1 = drinkblock.getDefaultState();

                if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
                {
                    SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, playerIn);
                    worldIn.playSound(playerIn, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    --stack.stackSize;
                }

                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
		}
        else
        {
            return EnumActionResult.PASS;
        }
    }
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, newState, 3)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == getBlock(stack.getItemDamage()))
        {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            getBlock(stack.getItemDamage()).onBlockPlacedBy(world, pos, state, player, stack);
        }

        return true;
    }
}