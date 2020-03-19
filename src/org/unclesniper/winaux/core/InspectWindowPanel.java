package org.unclesniper.winaux.core;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import org.unclesniper.winwin.HWnd;
import org.unclesniper.winaux.KnownWindow;
import org.unclesniper.winwin.WindowsException;
import org.unclesniper.winaux.core.util.SwingUtils;

public class InspectWindowPanel extends JPanel {

	private static final Color BAD_COLOR = new Color(200, 0, 0);

	private final JTextField classField = new JTextField();

	private final JTextField titleField = new JTextField();

	private final Color originalColor;

	public InspectWindowPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 0, 10);
		add(new JLabel("Class:"), c);
		c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 10);
		add(new JLabel("Title:"), c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 0, 0, 5);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1.0;
		classField.setEditable(false);
		add(classField, c);
		c.gridy = 1;
		c.insets = new Insets(5, 0, 5, 5);
		titleField.setEditable(false);
		add(titleField, c);
		originalColor = classField.getForeground();
	}

	public void setInspectedWindow(KnownWindow window) {
		if(window == null) {
			SwingUtils.invokeLater(() -> {
				classField.setText("");
				titleField.setText("");
			});
			return;
		}
		HWnd hwnd = window.getHWnd();
		String className, windowName;
		Color color;
		try {
			className = hwnd.getClassName();
			windowName = hwnd.getWindowText();
			color = originalColor;
		}
		catch(WindowsException we) {
			className = windowName = we.toString();
			color = InspectWindowPanel.BAD_COLOR;
		}
		final String cn = className, wn = windowName;
		final Color c = color;
		SwingUtils.invokeLater(() -> {
			classField.setText(cn);
			classField.setForeground(c);
			titleField.setText(wn);
			titleField.setForeground(c);
		});
	}

}
