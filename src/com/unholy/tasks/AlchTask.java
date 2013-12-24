package com.unholy.tasks;

import java.util.ArrayList;

import org.powerbot.script.methods.Antipatterns;
import org.powerbot.script.methods.Equipment;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Item;

import com.sk.methods.SkKeyboard;

import com.unholy.util.*;

public class AlchTask extends Task {

	public SkKeyboard keyboard;
	private final int NAT_ID = 561;
	public Antipatterns a;
	private final int[] fireIds = {1387, 1388, 1402, 1401, 3054, 3056, 1393, 1394,
			3053, 3055, 11736, 11737, 11738, 11739, 23047, 26112, 19323, 19324};

	public AlchTask(MethodContext cxt) {
		super(cxt);
		a = new Antipatterns(cxt);
		keyboard = new SkKeyboard(cxt);
		a.setEnabled(true);

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

		itemLoop: for (Item i : ctx.backpack.select()) {
			if (i.getId() == NAT_ID) {
				continue itemLoop;
			}
			i.hover();
			while (ctx.backpack.contains(i)) {
				keyboard.press("c");
				sleep(100, 150);
				keyboard.release("c");
				sleep(100, 150);
				i.click();
				while (ctx.players.local().getAnimation() != -1) {
				}
				ctx.backpack.select();
			}
		}

	}

}
