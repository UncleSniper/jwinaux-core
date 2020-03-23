package org.unclesniper.winaux.core;

import java.awt.Color;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import org.unclesniper.winaux.Tag;
import org.unclesniper.winwin.HWnd;
import java.awt.GridBagConstraints;
import javax.swing.DefaultListModel;
import org.unclesniper.winaux.AuxEngine;
import org.unclesniper.winaux.KnownWindow;
import org.unclesniper.winwin.WindowsException;
import org.unclesniper.winaux.core.util.ColorBlend;
import org.unclesniper.winaux.core.util.SwingUtils;

public class InspectTagPanel extends JPanel {

	private static final class TagRenderer extends ExtensibleDefaultListCellRenderer<Tag> {

		TagRenderer() {}

		@Override
		protected void renderListCell(JList<? extends Tag> list, Tag value, int index,
				boolean isSelected, boolean cellHasFocus) {
			renderer.setText(value.getNameAndID());
		}

	}

	private static final class WindowRenderer extends ExtensibleDefaultListCellRenderer<KnownWindow> {

		private static final Color BAD_COLOR = Color.RED;

		private Color plainColor;

		private Color selectedColor;

		private Color hiddenColor;

		private Color hiddenSelectedColor;

		WindowRenderer() {}

		@Override
		protected void renderListCell(JList<? extends KnownWindow> list, KnownWindow value, int index,
				boolean isSelected, boolean cellHasFocus) {
System.err.println("rendering window " + value.getHWnd());
			if(isSelected) {
				if(selectedColor == null) {
					selectedColor = renderer.getForeground();
					hiddenSelectedColor = ColorBlend.alphaBlend(renderer.getBackground(), selectedColor, 150);
				}
			}
			else {
				if(plainColor == null) {
					plainColor = renderer.getForeground();
					hiddenColor = ColorBlend.alphaBlend(renderer.getBackground(), plainColor, 150);
				}
			}
			HWnd hwnd = value.getHWnd();
			try {
				renderer.setText(hwnd.getWindowText());
			}
			catch(WindowsException we) {
				renderer.setText(hwnd.toString());
			}
			try {
				if(hwnd.isWindowVisible())
					renderer.setForeground(isSelected ? selectedColor : plainColor);
				else
					renderer.setForeground(isSelected ? hiddenSelectedColor : hiddenColor);
			}
			catch(WindowsException we) {
				renderer.setForeground(WindowRenderer.BAD_COLOR);
			}
		}

	}

	private final JList<Tag> tagList;

	private final DefaultListModel<Tag> tagModel;

	private final JList<KnownWindow> windowList;

	private final DefaultListModel<KnownWindow> windowModel;

	public InspectTagPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = c.weighty = 1.0;
		tagModel = new DefaultListModel<Tag>();
		tagList = new JList<Tag>(tagModel);
		tagList.setCellRenderer(new TagRenderer());
		JScrollPane tagScroll = new JScrollPane(tagList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(tagScroll, c);
		c.gridx = 1;
		windowModel = new DefaultListModel<KnownWindow>();
		windowList = new JList<KnownWindow>(windowModel);
		windowList.setCellRenderer(new WindowRenderer());
		JScrollPane windowScroll = new JScrollPane(windowList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(windowScroll, c);
		tagList.addListSelectionListener(evt -> {
			if(evt.getValueIsAdjusting())
				return;
			Tag tag = tagList.getSelectedValue();
			windowModel.clear();
			if(tag != null) {
				for(KnownWindow window : tag.getTaggedWindows())
					windowModel.addElement(window);
			}
		});
	}

	public void updateTags(AuxEngine engine) {
		SwingUtils.invokeLater(() -> {
			windowModel.clear();
			tagModel.clear();
			for(Tag tag : engine.getKnownTags())
				tagModel.addElement(tag);
		});
	}

}
