package com.pcrewards;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.api.events.MenuOptionClicked;

@Slf4j
@PluginDescriptor(
		name = "PC Rewards Customize",
		description = "Customize the PC Rewards screen to prevent unwanted spending"
)
public class PcRewardsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private PcRewardsConfig config;


	@Override
	protected void startUp() throws Exception {


	}

	@Override
	protected void shutDown() throws Exception {
		//log.info("PC Rewards Customize stopped!");
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged event) {

		hideOptions();
		//log.info("Started PC Rewards");

	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
        /*if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "PC Rewards Customize says " + config.greeting(), null);
			log.info("Started PC Rewards");
			Widget points_Screen = client.getWidget(15925251);
			Widget rewards[] = points_Screen.getDynamicChildren();
			log.info("Reward len: {}", rewards.length);
			log.info("Rewards: {}", rewards[2].toString());

			rewards[3].setHidden(true);
			rewards[2].setHidden(true);

			rewards[7].setHidden(true);


			Widget points = client.getWidget(15925256);
			String input = points.getText();
			String value_points = input.substring(input.indexOf(">") + 1, input.lastIndexOf("<"));
			log.info("Raw points are: {}", value_points);

		}*/
	}


	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked menuOptionClicked) {

		Widget pc_shop_interface = menuOptionClicked.getWidget();
		if (pc_shop_interface == null) {
			return;
		}
		if (pc_shop_interface.getId() == 15925251){
			//log.info("An item in the PC has been chekced");
			clientThread.invokeAtTickEnd(() ->
			{
				hideOptions();
				//log.info("Started PC Rewards");

			});
		}


	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded widgetLoaded) {
		//PC Rewards screen is 243
		//log.info("Widget loaded: {}", widgetLoaded.toString());
		//log.info("Widget ID: {}", widgetLoaded.getGroupId());
		if (widgetLoaded.getGroupId() == 243) {

			clientThread.invokeAtTickEnd(() ->
			{
				hideOptions();
				//log.info("Started PC Rewards");

			});


		}
	}

/*	@Subscribe
	public void onWidgetClosed(WidgetClosed event) {
		int groupId = event.getGroupId();
		if (groupId == 243) {
			log.info("Closed PC interface");
		}
	}*/

	private void hideOptions(){
		Widget points_Screen = client.getWidget(15925251);
		Widget rewards[] = points_Screen.getDynamicChildren();

		Widget points = client.getWidget(15925256);
		String input = points.getText();
		String value_points = input.substring(input.indexOf(">") + 1, input.lastIndexOf("<"));
		int total_points = Integer.parseInt(value_points);

        /*
		enum RewardOptions
	{
		ALL, None are hidden
		NONE, All are hidden
		ONLY_100, only 100
		GREATER_THAN_100 if points >= 100 do none, else all
	} */
		switch (config.getAtkOp()){
			case ALL:
				rewards[2].setHidden(false); // 1pt
				rewards[3].setHidden(false); // 10 pt
				rewards[4].setHidden(false); // 100 pt
				break;
			case NONE:
				rewards[2].setHidden(true); // 1pt
				rewards[3].setHidden(true); // 10 pt
				rewards[4].setHidden(true); // 100 pt
				//rewards[0].setHidden(true); // icon
				//rewards[1].setHidden(true); // text
				break;
			case ONLY_100:
				rewards[2].setHidden(true); // 1pt
				rewards[3].setHidden(true); // 10 pt
				rewards[4].setHidden(false); // 100 pt
				break;
			case GREATER_THAN_100:
				if (total_points < 100) {
					rewards[2].setHidden(false); // 1pt
					rewards[3].setHidden(false); // 10 pt
					rewards[4].setHidden(false); // 100 pt
				} else {
					rewards[2].setHidden(true); // 1pt
					rewards[3].setHidden(true); // 10 pt
					rewards[4].setHidden(false); // 100 pt
				}
				break;
		}

		switch (config.getStrOp()){
			case ALL:
				rewards[7].setHidden(false); // 1pt
				rewards[8].setHidden(false); // 10 pt
				rewards[9].setHidden(false); // 100 pt
				break;
			case NONE:
				rewards[7].setHidden(true); // 1pt
				rewards[8].setHidden(true); // 10 pt
				rewards[9].setHidden(true); // 100 pt
				break;
			case ONLY_100:
				rewards[7].setHidden(true); // 1pt
				rewards[8].setHidden(true); // 10 pt
				rewards[9].setHidden(false); // 100 pt
				break;
			case GREATER_THAN_100:
				if (total_points < 100) {
					rewards[7].setHidden(false); // 1pt
					rewards[8].setHidden(false); // 10 pt
					rewards[9].setHidden(false); // 100 pt
				} else {
					rewards[7].setHidden(true); // 1pt
					rewards[8].setHidden(true); // 10 pt
					rewards[9].setHidden(false); // 100 pt
				}
				break;
		}

		switch (config.getDefOp()){
			case ALL:
				rewards[12].setHidden(false); // 1pt
				rewards[13].setHidden(false); // 10 pt
				rewards[14].setHidden(false); // 100 pt
				break;
			case NONE:
				rewards[12].setHidden(true); // 1pt
				rewards[13].setHidden(true); // 10 pt
				rewards[14].setHidden(true); // 100 pt
				break;
			case ONLY_100:
				rewards[12].setHidden(true); // 1pt
				rewards[13].setHidden(true); // 10 pt
				rewards[14].setHidden(false); // 100 pt
				break;
			case GREATER_THAN_100:
				if (total_points < 100) {
					rewards[12].setHidden(false); // 1pt
					rewards[13].setHidden(false); // 10 pt
					rewards[14].setHidden(false); // 100 pt
				} else {
					rewards[12].setHidden(true); // 1pt
					rewards[13].setHidden(true); // 10 pt
					rewards[14].setHidden(false); // 100 pt
				}
				break;
		}

		switch (config.getRngOp()){
			case ALL:
				rewards[17].setHidden(false); // 1pt
				rewards[18].setHidden(false); // 10 pt
				rewards[19].setHidden(false); // 100 pt
				break;
			case NONE:
				rewards[17].setHidden(true); // 1pt
				rewards[18].setHidden(true); // 10 pt
				rewards[19].setHidden(true); // 100 pt
				break;
			case ONLY_100:
				rewards[17].setHidden(true); // 1pt
				rewards[18].setHidden(true); // 10 pt
				rewards[19].setHidden(false); // 100 pt
				break;
			case GREATER_THAN_100:
				if (total_points < 100) {
					rewards[17].setHidden(false); // 1pt
					rewards[18].setHidden(false); // 10 pt
					rewards[19].setHidden(false); // 100 pt
				} else {
					rewards[17].setHidden(true); // 1pt
					rewards[18].setHidden(true); // 10 pt
					rewards[19].setHidden(false); // 100 pt
				}
				break;
		}

		switch (config.getMagicOp()){
			case ALL:
				rewards[22].setHidden(false); // 1pt
				rewards[23].setHidden(false); // 10 pt
				rewards[24].setHidden(false); // 100 pt
				break;
			case NONE:
				rewards[22].setHidden(true); // 1pt
				rewards[23].setHidden(true); // 10 pt
				rewards[24].setHidden(true); // 100 pt
				break;
			case ONLY_100:
				rewards[22].setHidden(true); // 1pt
				rewards[23].setHidden(true); // 10 pt
				rewards[24].setHidden(false); // 100 pt
				break;
			case GREATER_THAN_100:
				if (total_points < 100) {
					rewards[22].setHidden(false); // 1pt
					rewards[23].setHidden(false); // 10 pt
					rewards[24].setHidden(false); // 100 pt
				} else {
					rewards[22].setHidden(true); // 1pt
					rewards[23].setHidden(true); // 10 pt
					rewards[24].setHidden(false); // 100 pt
				}
				break;
		}

		switch (config.getHpOp()){
			case ALL:
				rewards[27].setHidden(false); // 1pt
				rewards[28].setHidden(false); // 10 pt
				rewards[29].setHidden(false); // 100 pt
				break;
			case NONE:
				rewards[27].setHidden(true); // 1pt
				rewards[28].setHidden(true); // 10 pt
				rewards[29].setHidden(true); // 100 pt
				break;
			case ONLY_100:
				rewards[27].setHidden(true); // 1pt
				rewards[28].setHidden(true); // 10 pt
				rewards[29].setHidden(false); // 100 pt
				break;
			case GREATER_THAN_100:
				if (total_points < 100) {
					rewards[27].setHidden(false); // 1pt
					rewards[28].setHidden(false); // 10 pt
					rewards[29].setHidden(false); // 100 pt
				} else {
					rewards[27].setHidden(true); // 1pt
					rewards[28].setHidden(true); // 10 pt
					rewards[29].setHidden(false); // 100 pt
				}
				break;
		}

		switch (config.getPrayOp()){
			case ALL:
				rewards[32].setHidden(false); // 1pt
				rewards[33].setHidden(false); // 10 pt
				rewards[34].setHidden(false); // 100 pt
				break;
			case NONE:
				rewards[32].setHidden(true); // 1pt
				rewards[33].setHidden(true); // 10 pt
				rewards[34].setHidden(true); // 100 pt
				break;
			case ONLY_100:
				rewards[32].setHidden(true); // 1pt
				rewards[33].setHidden(true); // 10 pt
				rewards[34].setHidden(false); // 100 pt
				break;
			case GREATER_THAN_100:
				if (total_points < 100) {
					rewards[32].setHidden(false); // 1pt
					rewards[33].setHidden(false); // 10 pt
					rewards[34].setHidden(false); // 100 pt
				} else {
					rewards[32].setHidden(true); // 1pt
					rewards[33].setHidden(true); // 10 pt
					rewards[34].setHidden(false); // 100 pt
				}
				break;
		}

		// HERB OPTION
		rewards[40].setHidden(config.getHerb());

		// MINERAL OPTION
		rewards[44].setHidden(config.getMine());

		// SEED OPTION
		rewards[48].setHidden(config.getSeed());

		// MACE
		rewards[54].setHidden(config.getMace());

		// Robe Top
		rewards[58].setHidden(config.getRTop());

		// robe bottoms
		rewards[62].setHidden(config.getRBtm());

		// gloves
		rewards[66].setHidden(config.getGlove());

		// mage helm
		rewards[70].setHidden(config.getMageHelm());

		// range helm
		rewards[74].setHidden(config.getRhelm());

		// melee helm
		rewards[78].setHidden(config.getMelHelm());

		// seal
		rewards[82].setHidden(config.getSeal());


	}


	@Provides
	PcRewardsConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(PcRewardsConfig.class);
	}
}
