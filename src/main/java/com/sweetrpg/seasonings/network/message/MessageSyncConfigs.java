/*******************************************************************************
 * Copyright 2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/
package com.sweetrpg.seasonings.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import com.sweetrpg.seasonings.api.config.SyncedConfig;
import com.sweetrpg.seasonings.core.Seasonings;

public class MessageSyncConfigs implements IMessage, IMessageHandler<MessageSyncConfigs, IMessage>
{
    public NBTTagCompound nbtOptions;
    
    public MessageSyncConfigs() {}
    
    public MessageSyncConfigs(NBTTagCompound nbtOptions)
    {
        this.nbtOptions = nbtOptions;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) 
    {
        this.nbtOptions = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) 
    {
        ByteBufUtils.writeTag(buf, nbtOptions);
    }

    @Override
    public IMessage onMessage(MessageSyncConfigs message, MessageContext ctx)
    {
        if (ctx.side == Side.CLIENT)
        {
            for (String key : message.nbtOptions.getKeySet())
            {
                SyncedConfig.SyncedConfigEntry entry = SyncedConfig.optionsToSync.get(key);
                
                if (entry == null) Seasonings.logger.error("Option " + key + " does not exist locally!");
                
                entry.value = message.nbtOptions.getString(key);
                Seasonings.logger.info("SS configuration synchronized with the server");
            }
        }
        
        return null;
    }
}
