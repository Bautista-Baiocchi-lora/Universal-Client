package org.bot.component.inputs;

import org.bot.Engine;

import java.awt.*;

public class Mouse {
	public static void move(int x, int y) {
		Engine.getMouse().move(x, y);
	}

	public static void move(Point p) {
		if (p == null)
			return;
		move(p.x, p.y);
	}

	public static int getPressX() {
		return Engine.getMouse().getPressX();
	}

	public static int getPressY() {
		return Engine.getMouse().getPressY();
	}

	public static long getPressTime() {
		return Engine.getMouse().getPressTime();
	}

	public static boolean isPressed() {
		return Engine.getMouse().isPressed();
	}

	public static Point getLocation() {
		return Engine.getMouse().getLocation();
	}

	public static void click(boolean right) {
		Engine.getMouse().click(right);
	}

	public static void click(Point p, boolean right) {
		move(p);
		click(right);
	}

	public static void press(int x, int y, int button) {
		Engine.getMouse().press(x, y, button);
	}

	public static void release(int x, int y, int button) {
		Engine.getMouse().release(x, y, button);
	}

	public static boolean dragMouse(Point p1, Point p2) {
		return dragMouse(p1.getLocation().x, p1.getLocation().y, p2.getLocation().x, p2.getLocation().y);
	}

	public static boolean dragMouse(int x, int y, int x1, int y1) {
		return Engine.getMouse().drag(x, y, x1, y1);
	}
}