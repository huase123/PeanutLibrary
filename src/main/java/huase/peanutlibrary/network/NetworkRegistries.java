/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package huase.peanutlibrary.network;

import huase.peanutlibrary.ModMain;
import huase.peanutlibrary.network.server.ReClientboundUpdateAttributesPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkRegistries {

  private static final String PTC_VERSION = "1";

  public static SimpleChannel INSTANCE;

  private static int id = 0;

  public static void register() {

    INSTANCE = NetworkRegistry.ChannelBuilder.named(ModMain.prefix("main"))
        .networkProtocolVersion(() -> PTC_VERSION).clientAcceptedVersions(PTC_VERSION::equals)
        .serverAcceptedVersions(PTC_VERSION::equals).simpleChannel();

    //Client Packets
//
//    register(CPacketOpenAttrGUI.class, CPacketOpenAttrGUI::encode, CPacketOpenAttrGUI::decode, CPacketOpenAttrGUI::handle);
//    register(CPacketBreakthrough.class, CPacketBreakthrough::encode, CPacketBreakthrough::decode, CPacketBreakthrough::handle);
//    register(CPacketDanfire.class, CPacketDanfire::encode, CPacketDanfire::decode, CPacketDanfire::handle);
//    register(CPacketYvjian.class, CPacketYvjian::encode, CPacketYvjian::decode, CPacketYvjian::handle);
//    register(CPacketYvjianControl.class, CPacketYvjianControl::encode, CPacketYvjianControl::decode, CPacketYvjianControl::handle);

    // Server Packets
    register(ReClientboundUpdateAttributesPacket.class, ReClientboundUpdateAttributesPacket::encode, ReClientboundUpdateAttributesPacket::decode, ReClientboundUpdateAttributesPacket::handle);

  }

  private static <M> void register(Class<M> messageType, BiConsumer<M, FriendlyByteBuf> encoder,
                                   Function<FriendlyByteBuf, M> decoder,
                                   BiConsumer<M, Supplier<NetworkEvent.Context>> messageConsumer) {
    INSTANCE.registerMessage(id++, messageType, encoder, decoder, messageConsumer);
  }
}
