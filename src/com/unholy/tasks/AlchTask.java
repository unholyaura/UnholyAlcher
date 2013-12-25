package com.unholy.tasks;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Action;
import org.powerbot.script.wrappers.Item;

import com.sk.methods.SkKeyboard;
import com.unholy.util.*;

public class AlchTask extends Task {

	private final int NAT_ID = 561;
	private final int HIGH_ALCH_ID = 758;
	private final int[] fireIds = { 1387, 1388, 1402, 1401, 3054, 3056, 1393,
			1394, 3053, 3055, 11736, 11737, 11738, 11739, 23047, 26112, 19323,
			19324 };

	public SkKeyboard keyboard;

	public AlchTask(MethodContext cxt) {
		super(cxt);

		keyboard = new SkKeyboard(cxt);

	}

	@Override
	public boolean activate() {

		return (!ctx.backpack.select().id(NAT_ID).isEmpty())
				&& (!ctx.equipment.select().id(fireIds).isEmpty())
				&& (ctx.players.local().getAnimation() == -1)
				&& (ctx.backpack.select().count() > 1);
	}

	@Override
	public void execute() {

		String key = findKey(HIGH_ALCH_ID);

		for (Item i : ctx.backpack.select()) {

			if (i.getId() == NAT_ID) {
				continue;
			}
			while (ctx.backpack.contains(i)) {
				
				ctx.keyboard.send(key);
				sleep(100, 150);
				i.interact("Cast");
				sleep(200, 250);
				while (ctx.players.local().getAnimation() != -1) {
					sleep(225, 250);
				}
				
				ctx.backpack.select();
				sleep(200, 250);
			}
		}

	}

	public String findKey(int keyId) {
		String key = null;

		Action[] bar = ctx.combatBar.getActions();
		for (Action a : bar) {
			if (a.getId() == keyId) {
				key = a.getBind();
				break;
			}
		}
		return key;

	}


}
