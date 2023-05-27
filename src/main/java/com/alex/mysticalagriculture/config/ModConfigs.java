package com.alex.mysticalagriculture.config;

import com.alex.mysticalagriculture.forge.common.ConfigSpec;

public class ModConfigs {
    public static final ConfigSpec CLIENT;
    public static final ConfigSpec COMMON;

    public static final ConfigSpec.BooleanValue ANIMATED_GROWTH_ACCELERATORS;

    // Client
    static {
        final var client = new ConfigSpec.Builder();

        client.comment("General configuration options.").push("General");
        ANIMATED_GROWTH_ACCELERATORS = client
                .comment("Should Growth Accelerators use animated textures?")
                .define("animatedGrowthAccelerators", true);
        client.pop();

        CLIENT = client.build();
    }

    public static final ConfigSpec.DoubleValue INFERIUM_DROP_CHANCE;
    public static final ConfigSpec.IntValue INFUSION_CRYSTAL_USES;
    public static final ConfigSpec.IntValue GROWTH_ACCELERATOR_COOLDOWN;
    public static final ConfigSpec.DoubleValue FERTILIZED_ESSENCE_DROP_CHANCE;
    public static final ConfigSpec.BooleanValue SECONDARY_SEED_DROPS;
    public static final ConfigSpec.BooleanValue WITHER_DROPS_ESSENCE;
    public static final ConfigSpec.BooleanValue WITHER_DROPS_COGNIZANT;
    public static final ConfigSpec.BooleanValue DRAGON_DROPS_ESSENCE;
    public static final ConfigSpec.BooleanValue DRAGON_DROPS_COGNIZANT;
    public static final ConfigSpec.BooleanValue ESSENCE_FARMLAND_CONVERSION;
    public static final ConfigSpec.BooleanValue SEED_CRAFTING_RECIPES;
    public static final ConfigSpec.BooleanValue ENCHANTABLE_SUPREMIUM_TOOLS;
    public static final ConfigSpec.BooleanValue FAKE_PLAYER_WATERING;
    public static final ConfigSpec.BooleanValue AWAKENED_SUPREMIUM_SET_BONUS;

    public static final ConfigSpec.BooleanValue GENERATE_PROSPERITY;
    public static final ConfigSpec.BooleanValue GENERATE_INFERIUM;
    public static final ConfigSpec.BooleanValue GENERATE_SOULSTONE;
    public static final ConfigSpec.DoubleValue SOULIUM_ORE_CHANCE;
    
    static {
        final var common = new ConfigSpec.Builder();

        common.comment("General configuration options.").push("General");
        INFERIUM_DROP_CHANCE = common
                .comment("The percentage chance that a passive or hostile mob will drop an Inferium Essence when killed.")
                .defineInRange("inferiumDropChance", 0.2, 0.0, 1.0);
        INFUSION_CRYSTAL_USES = common
                .comment("The amount of uses the basic Infusion Crystal should have.")
                .defineInRange("infusionCrystalUses", 1000, 10, Integer.MAX_VALUE);
        GROWTH_ACCELERATOR_COOLDOWN = common
                .comment("The amount of time in seconds between each Growth Accelerator growth tick.")
                .defineInRange("growthAcceleratorCooldown", 10, 1, Integer.MAX_VALUE);
        FERTILIZED_ESSENCE_DROP_CHANCE = common
                .comment("The percentage chance that harvesting a Resource Crop will drop a Fertilized Essence.")
                .defineInRange("fertilizedEssenceChance", 0.1, 0.0, 1.0);
        SECONDARY_SEED_DROPS = common
                .comment("Should crops have a chance of dropping a second seed when harvested?")
                .define("secondarySeedDrops", true);
        WITHER_DROPS_ESSENCE = common
                .comment("Should the Wither drop essence when killed with an essence weapon?")
                .define("witherDropsEssence", true);
        WITHER_DROPS_COGNIZANT = common
                .comment("Should the Wither drop Cognizant Dust when killed with a Mystical Enlightenment enchanted essence weapon?")
                .define("witherDropsCognizant", true);
        DRAGON_DROPS_ESSENCE = common
                .comment("Should the Ender Dragon drop essence when killed with an essence weapon?")
                .define("dragonDropsEssence", true);
        DRAGON_DROPS_COGNIZANT = common
                .comment("Should the Ender Dragon drop Cognizant Dust when killed with a Mystical Enlightenment enchanted essence weapon?")
                .define("dragonDropsCognizant", true);
        ESSENCE_FARMLAND_CONVERSION = common
                .comment("Should right clicking on Farmland with an Essence create Essence Farmland?")
                .define("essenceFarmlandConversion", true);
        SEED_CRAFTING_RECIPES = common
                .comment("Should vanilla crafting recipes for seeds be generated?")
                .define("seedCraftingRecipes", false);
        ENCHANTABLE_SUPREMIUM_TOOLS = common
                .comment("Should Supremium Tools be enchantable in an Enchanting Table?")
                .define("enchantableSupremiumTools", true);
        FAKE_PLAYER_WATERING = common
                .comment("Should fake players be able to use watering cans?")
                .define("fakePlayerWatering", true);
        AWAKENED_SUPREMIUM_SET_BONUS = common
                .comment("Should wearing a full set of Awakened Supremium armor grant the Plant Growth AOE set bonus?")
                .define("awakenedSupremiumSetBonus", true);
        common.pop();

        common.comment("World generation options.").push("World");
        GENERATE_PROSPERITY = common
                .comment("Should Prosperity Ore generate in the world?")
                .define("generateProsperityOre", true);
        GENERATE_INFERIUM = common
                .comment("Should Inferium Ore generate in the world?")
                .define("generateInferiumOre", true);
        GENERATE_SOULSTONE = common
                .comment("Should Soulstone generate in the world?")
                .define("generateSoulstone", true);
        SOULIUM_ORE_CHANCE = common
                .comment("The percentage chance a Soulium Ore spawns in a Soulstone cluster.")
                .defineInRange("souliumOreChance", 0.05, 0, 1);
        common.pop();

        COMMON = common.build();
    }
}