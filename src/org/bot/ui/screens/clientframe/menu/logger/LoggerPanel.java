package org.bot.ui.screens.clientframe.menu.logger;

import org.bot.Engine;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bautistabaiocchi-lora on 7/16/17.
 */

public class LoggerPanel extends JPanel {
	private final JScrollPane scrollPane;

	public LoggerPanel(final Logger logger) {
		scrollPane = new JScrollPane(logger, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setPreferredSize(new Dimension(Engine.getGameComponent().getWidth(), 150));
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}


}