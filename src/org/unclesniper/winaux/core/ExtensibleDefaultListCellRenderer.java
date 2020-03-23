package org.unclesniper.winaux.core;

import javax.swing.JList;
import java.awt.Component;
import javax.swing.ListCellRenderer;
import javax.swing.DefaultListCellRenderer;

public abstract class ExtensibleDefaultListCellRenderer<ItemT> implements ListCellRenderer<ItemT> {

	protected final DefaultListCellRenderer renderer = new DefaultListCellRenderer();

	public ExtensibleDefaultListCellRenderer() {}

	protected abstract void renderListCell(JList<? extends ItemT> list, ItemT value, int index,
			boolean isSelected, boolean cellHasFocus);

	@Override
	public Component getListCellRendererComponent(JList<? extends ItemT> list, ItemT value, int index,
			boolean isSelected, boolean cellHasFocus) {
		renderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		renderListCell(list, value, index, isSelected, cellHasFocus);
		return renderer;
	}

}
