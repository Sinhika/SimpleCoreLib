package mod.alexndr.simplecorelib.api.datagen;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import mod.alexndr.simplecorelib.api.helpers.TagUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryObject;

public class MiningItemTags extends ItemTagsProvider 
{

	public MiningItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, 
			CompletableFuture<TagLookup<Block>> blockTagProvider, String modId,
			ExistingFileHelper existingFileHelper) 
	{
		super(output, lookupProvider, blockTagProvider, modId, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) 
	{
		registerOreTags();
	}

	/**
	 * Override this, obviously.
	 */
	protected void registerOreTags() 
    {}
    
	/**
	 * Automatically create minecraft:armor tags.
	 * 
	 * @param item_defregistry DeferredRegistry in which armor items exist.
	 */
	protected void registerArmorTags(DeferredRegister<Item> item_defregistry)
	{
		item_defregistry.getEntries().stream().map(DeferredHolder::get)
			.filter(entry -> (entry instanceof ArmorItem))
			.forEach(armor -> {
				if (((ArmorItem) armor).getType() == ArmorItem.Type.BOOTS) {
					this.tag(TagUtils.modTag("minecraft", "foot_armor")).add((ArmorItem) armor);
				}
				else if (((ArmorItem) armor).getType() == ArmorItem.Type.LEGGINGS) {
					this.tag(TagUtils.modTag("minecraft", "leg_armor")).add((ArmorItem) armor);
				}
				else if (((ArmorItem) armor).getType() == ArmorItem.Type.CHESTPLATE) {
					this.tag(TagUtils.modTag("minecraft", "chest_armor")).add((ArmorItem) armor);
				}
				else if (((ArmorItem) armor).getType() == ArmorItem.Type.LEGGINGS) {
					this.tag(TagUtils.modTag("minecraft", "head_armor")).add((ArmorItem) armor);
				}
			});
	} // end registerArmorTags()
	
	/**
	 * Automatically create tool tags.
	 * 
	 * @param item_defregistry DeferredRegistry in which tool items exist.
	 */
	protected void registerToolTags(DeferredRegister<Item> item_defregistry)
	{
		// first, tiered items as a group.
		item_defregistry.getEntries().stream().map(DeferredHolder::get)
			.filter(entry -> (entry instanceof TieredItem))
			.forEach(item -> {
				if (item instanceof SwordItem) {
					this.tag(TagUtils.modTag("minecraft", "swords")).add(item);
				}
				else if (item instanceof AxeItem) {
					this.tag(TagUtils.modTag("minecraft", "axes")).add(item);
				}
				else if (item instanceof HoeItem) {
					this.tag(TagUtils.modTag("minecraft", "hoes")).add(item);
				}
				else if (item instanceof PickaxeItem) {
					this.tag(TagUtils.modTag("minecraft", "pickaxes")).add(item);
				}
				else if (item instanceof ShovelItem) {
					this.tag(TagUtils.modTag("minecraft", "shovels")).add(item);
				}
			});

		// second, projectile weapons
		this.tag(TagUtils.forgeTag("tools"))
			.addTag(TagUtils.forgeTag("tools/bows"))
			.addTag(TagUtils.forgeTag("tools/crossbows"));
				
		item_defregistry.getEntries().stream().map(DeferredHolder::get)
			.filter(entry -> (entry instanceof ProjectileWeaponItem))
			.forEach(item -> {
				if (item instanceof BowItem) {
					this.tag(TagUtils.forgeTag("tools/bows")).add(item);
				}
				else if (item instanceof CrossbowItem) {
					this.tag(TagUtils.forgeTag("tools/crossbows")).add(item);
				}
			});

		// third, shears
		item_defregistry.getEntries().stream().map(DeferredHolder::get)
			.filter(entry -> (entry instanceof ShearsItem))
			.forEach(item -> {
				this.tag(Tags.Items.TOOLS_SHEARS).add(item);
			});
		
	} // end registerToolTags()
	
	/** 
	 * Creates ores_in_ground forge tags for item-blocks.
	 * 
	 * @param ore_blocks
	 * @param deepslate_ore_blocks
	 * @param netherrack_ore_blocks
	 */
	protected void registerOresInGroundTags(Collection<DropExperienceBlock> ore_blocks, 
										  Collection<DropExperienceBlock> deepslate_ore_blocks,
										  Collection<DropExperienceBlock> netherrack_ore_blocks )
	{
		if (ore_blocks != null && !ore_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> stone_item = this.tag(Tags.Items.ORES_IN_GROUND_STONE);
			ore_blocks.stream().forEach(b -> stone_item.add(b.asItem()));
		}
		if (deepslate_ore_blocks != null && !deepslate_ore_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> deepslate = this.tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE);
			deepslate_ore_blocks.stream().forEach(b -> deepslate.add(b.asItem()));
		}
		if (netherrack_ore_blocks != null && !netherrack_ore_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> netherrack = this.tag(Tags.Items.ORES_IN_GROUND_NETHERRACK);
			netherrack_ore_blocks.stream().forEach(b -> netherrack.add(b.asItem()));
		}
	} // end registerOresInGroundTags

	/**
	 * Creates forge:ore_rates tags for blocks.
	 * 
	 * @param sparse_ores ore blocks that drop less than a standard unit. e.g., nether_gold_ore drops nuggets.
	 * @param singular_ores blocks that drop a single unit (the usual case).
	 * @param dense_ores blocks that drop multiple units, e.g. redstone_ore or copper_ore.
	 */
	protected void registerOreRateTags(Collection<DropExperienceBlock> sparse_ores, Collection<DropExperienceBlock> singular_ores, 
									   Collection<DropExperienceBlock> dense_ores)
	{
		if (sparse_ores != null && !sparse_ores.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> sparse = this.tag(Tags.Items.ORE_RATES_SPARSE);
			sparse_ores.stream().forEach(b -> sparse.add(b.asItem()));
		}
		if (singular_ores != null && !singular_ores.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> singular = this.tag(Tags.Items.ORE_RATES_SINGULAR);
			singular_ores.stream().forEach(b -> singular.add(b.asItem()));
		}
		if (dense_ores != null && !dense_ores.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> dense = this.tag(Tags.Items.ORE_RATES_DENSE);
			dense_ores.stream().forEach(b -> dense.add(b.asItem()));
		}
	} // end registerOreRateTags

} // end class
