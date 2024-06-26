package net.wenscHuix.mitemod.mixin.world;

import net.minecraft.ChunkCache;
import net.minecraft.EnumSkyBlock;
import net.wenscHuix.mitemod.shader.client.dynamicLight.DynamicLights;
import net.wenscHuix.mitemod.shader.client.dynamicLight.config.ShaderConfig;
import net.wenscHuix.mitemod.shader.util.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ChunkCache.class})
public abstract class ChunkCacheMixin {
   @Shadow public abstract int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4);

   /**
    * @author
    * @reason
    */
   @Overwrite
   public final int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4) {
      int var5 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, par1, par2, par3);
      int var6 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Block, par1, par2, par3);
      if (var6 < par4) {
         var6 = par4;
      }

      int cout = var5 << 20 | var6 << 4;
      if (ShaderConfig.isDynamicLights()) {
         cout = DynamicLights.getCombinedLight(new BlockPos(par1, par2, par3), cout);
      }

      return cout;
   }
}
