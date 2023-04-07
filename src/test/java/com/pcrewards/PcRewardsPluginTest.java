package com.pcrewards;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PcRewardsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PcRewardsPlugin.class);
		RuneLite.main(args);
	}
}