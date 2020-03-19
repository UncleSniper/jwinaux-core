package org.unclesniper.winaux.core;

import java.util.Map;
import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JTabbedPane;
import java.util.IdentityHashMap;
import java.awt.GridBagConstraints;
import java.util.function.Predicate;
import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;

public class TabbedSwingWindow extends AbstractSwingWindow {

	private final JTabbedPane tabbed;

	private final Map<SwingTabBlueprint, Integer> blueprints = new IdentityHashMap<SwingTabBlueprint, Integer>();

	public TabbedSwingWindow(AuxEngine engine, String title, Predicate<KnownWindow> onCreate,
			int width, int height) {
		super(engine, title, onCreate);
		Container content = getContentPane();
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = c.weighty = 1.0;
		tabbed = new JTabbedPane(JTabbedPane.TOP);
		tabbed.setPreferredSize(new Dimension(width, height));
		content.add(tabbed, c);
	}

	public int addTab(String title, Component component, SwingTabBlueprint blueprint) {
		if(title == null)
			throw new IllegalArgumentException("Tab title cannot be null");
		if(component == null)
			throw new IllegalArgumentException("Tab component cannot be null");
		int index = tabbed.getTabCount();
		tabbed.addTab(title, component);
		if(blueprint != null)
			blueprints.put(blueprint, index);
		return index;
	}

	public void setActiveTab(int index) {
		tabbed.setSelectedIndex(index);
	}

	public void setActiveTab(SwingTabBlueprint tab) {
		if(tab == null)
			throw new IllegalArgumentException("Tab cannot be null");
		Integer index = blueprints.get(tab);
		if(index == null)
			throw new IllegalArgumentException("Tab does not belong to this window");
		tabbed.setSelectedIndex(index);
	}

}
