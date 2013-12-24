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
	private ArrayList<Integer> fireStaves = new ArrayList<Integer>();
	private final int NATID = 561;
	public Antipatterns a;

	public AlchTask(MethodContext cxt) {
		super(cxt);
		a = new Antipatterns(cxt);
		keyboard = new SkKeyboard(cxt);
		a.setEnabled(true);
		fireStaves.add(1387);
		fireStaves.add(1388);
		fireStaves.add(1402);
		fireStaves.add(1401);
		fireStaves.add(3054);
		fireStaves.add(3056);
		fireStaves.add(1393);
		fireStaves.add(1394);
		fireStaves.add(3053);
		fireStaves.add(3055);
		fireStaves.add(11736);
		fireStaves.add(11737);
		fireStaves.add(11738);
		fireStaves.add(11739);
		fireStaves.add(23047);
		fireStaves.add(26112);
		fireStaves.add(19323);
		fireStaves.add(19324);

	}

	@Override
	public boolean activate() {

		return (0 >= ctx.backpack.indexOf(NATID) || ctx.backpack.indexOf(NATID) <= 27)
				&& (fireStaves.contains(ctx.equipment.getItemAt(
						Equipment.Slot.MAIN_HAND).getId()))
				&& (ctx.players.local().getAnimation() == -1)
				&& (ctx.backpack.select().count() > 1);
	}

	@Override
	public void execute() {

		itemLoop: for (Item i : ctx.backpack.select()) {
			if (i.getId() == NATID) {
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
