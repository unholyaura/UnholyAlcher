package com.unholy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Skills;

import com.unholy.tasks.AlchTask;
import com.unholy.util.Task;

@Manifest(name = "UnholyAlcher", description = "First script, high alchs everything in inventory.")
public class UnholyAlcher extends PollingScript implements PaintListener,
		MessageListener {

	private List<Task> taskList;

	// **Paint Vars**//
	private int alchCount;
	private int expGained;
	private int initMagicExp;
	private int expPerHour;
	public int startTime;
	private int currTime;
	private int runningTime;
	private int hours;
	private int minutes;
	private int seconds;

	@Override
	public void messaged(MessageEvent e) {
		String msg = e.getMessage().toLowerCase();
		if (msg.contains("pouch")) {
			alchCount++;
			expGained = ctx.skills.getExperience(Skills.MAGIC) - initMagicExp;

		}
	}

	@Override
	public void repaint(Graphics g) {
		currTime = (int) (System.currentTimeMillis() / 1000);
		runningTime = currTime - startTime;
		hours = runningTime / 3600;
		minutes = runningTime / 60;
		seconds = runningTime % 60;

		if (runningTime > 1) {
			expPerHour = (expGained / runningTime) * 3600;
		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Tahoma", Font.BOLD, 13));
		g.drawString("Time: " + hours + ":" + minutes + ":" + seconds, 50, 35);
		g.drawString("Alchs: " + alchCount, 50, 50);
		g.drawString("Exp: " + expGained, 50, 70);
		g.drawString("Exp/Hour: " + expPerHour, 50, 85);

	}

	@Override
	public void start() {
		startTime = (int) (System.currentTimeMillis() / 1000);
		taskList = new ArrayList<Task>();
		alchCount = 0;
		expGained = 0;
		initMagicExp = ctx.skills.getExperience(Skills.MAGIC);
		expPerHour = 0;
		currTime = 0;
		runningTime = 0;
		hours = 0;
		minutes = 0;
		seconds = 0;

		taskList.add(new AlchTask(getContext()));
	}

	@Override
	public int poll() {
		for (Task t : taskList) {
			if (t.activate()) {
				t.execute();
			} else {
				// System.out.println("reached stop code, still didnt stop");
				return -1;
			}
		}
		return 1;
	}

	@Override
	public void stop() {

	}

}
