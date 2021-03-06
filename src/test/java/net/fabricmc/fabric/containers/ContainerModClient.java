/*
 * Copyright (c) 2016, 2017, 2018 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.containers;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry;
import net.minecraft.client.gui.ContainerGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;

public class ContainerModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		//Registers a gui factory that opens our example gui, this reads the block pos from the buffer
		GuiProviderRegistry.INSTANCE.registerFactory(ContainerMod.EXAMPLE_CONTAINER, (syncId, identifier, player, buf) -> {
			BlockPos pos = buf.readBlockPos();
			return new ExampleContainerGui(syncId, pos, player);
		});

		//Registers a gui factory that opens our example gui, this uses the container created by ContainerProviderRegistry
		GuiProviderRegistry.INSTANCE.registerFactory(ContainerMod.EXAMPLE_CONTAINER_2, ExampleContainerGui2::new);
	}

	//A container gui that shows the block pos that was sent
	public static class ExampleContainerGui extends ContainerGui<ContainerMod.ExampleContainer> {

		BlockPos pos;

		public ExampleContainerGui(int syncId, BlockPos pos, PlayerEntity playerEntity) {
			super(new ContainerMod.ExampleContainer(syncId, pos, playerEntity), playerEntity.inventory, new StringTextComponent("Example GUI"));
			this.pos = pos;
		}

		@Override
		protected void drawBackground(float v, int i, int i1) {
			fontRenderer.draw(pos.toString(), width / 2, height / 2, 0);
		}
	}


	//A container gui that shows how you can take in a container provided by a GuiFactory
	public static class ExampleContainerGui2 extends ContainerGui<ContainerMod.ExampleContainer> {

		BlockPos pos;

		public ExampleContainerGui2(ContainerMod.ExampleContainer container) {
			super(container, container.playerInventory, new StringTextComponent("Example GUI 2"));
			this.pos = container.pos;
		}

		@Override
		protected void drawBackground(float v, int i, int i1) {
			fontRenderer.draw(pos.toString(), width / 2, height / 2, 0);
		}
	}

}
