package backend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class CheckBoxNodeTree{

	public JTree getCheckBoxNodeTree(Object[] continut, TreeMap<String, String> map) {
		Object[] epVazute = null;
		if( map!= null){
			Set<String> episoadeVazute = map.keySet();
			epVazute = episoadeVazute.toArray();
		}
		Object rootNodes[] = new Object[continut.length];
		rootNodes[0] = continut[0];
		for(int i=1; i<continut.length; i++)
		{
			Object[] a = (Object[]) continut[i];
			CheckBoxNode accessibilityOptions[] = new CheckBoxNode[a.length-1];
			for(int j=1; j<a.length; j++){
				boolean ok = false;
				if(epVazute != null)
					for(int k=0; k<epVazute.length; k++)
						if(epVazute[k].equals((String) a[j])){
							ok = true;
							k = epVazute.length;
						}
				accessibilityOptions[j-1] = new CheckBoxNode((String) a[j],ok);
				
			}
			Vector<Object> accessVector = new NamedVector((String) a[0],accessibilityOptions);
			rootNodes[i] = accessVector;
		}
		Vector<Object> rootVector = new NamedVector("Root", rootNodes);
		JTree tree = new JTree(rootVector);
		CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);
		tree.setCellEditor(new CheckBoxNodeEditor(tree));
		tree.setEditable(true);
		return tree;
	}
}
class CheckBoxNodeRenderer implements TreeCellRenderer {
	private JCheckBox leafRenderer = new JCheckBox();
	private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();
	Color selectionBorderColor, selectionForeground, selectionBackground,textForeground, textBackground;
	protected JCheckBox getLeafRenderer() {
		return leafRenderer;
	}
	public CheckBoxNodeRenderer() {
		Font fontValue;
		fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) {
			leafRenderer.setFont(fontValue);
		}
		Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
		leafRenderer.setFocusPainted((booleanValue != null) && (booleanValue.booleanValue()));
		selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value,	boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Component returnValue;
		if (leaf) {
			String stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, false);
			leafRenderer.setText(stringValue);
			leafRenderer.setSelected(false);
			leafRenderer.setEnabled(tree.isEnabled());
			if (selected) {
				leafRenderer.setForeground(selectionForeground);
				leafRenderer.setBackground(selectionBackground);
			} else {
				leafRenderer.setForeground(textForeground);
				leafRenderer.setBackground(textBackground);
			}
			if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
				Object userObject = ((DefaultMutableTreeNode) value)
						.getUserObject();
				if (userObject instanceof CheckBoxNode) {
					CheckBoxNode node = (CheckBoxNode) userObject;
					leafRenderer.setText(node.getText());
					leafRenderer.setSelected(node.isSelected());
				}
			}
			returnValue = leafRenderer;
		} else {
			returnValue = nonLeafRenderer.getTreeCellRendererComponent(tree,
					value, selected, expanded, leaf, row, hasFocus);
		}
		return returnValue;
	}
}
class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
	ChangeEvent changeEvent = null;
	JTree tree;
	public CheckBoxNodeEditor(JTree tree) {
		this.tree = tree;
	}
	public Object getCellEditorValue() {
		JCheckBox checkbox = renderer.getLeafRenderer();
		CheckBoxNode checkBoxNode = new CheckBoxNode(checkbox.getText(),
				checkbox.isSelected());
		return checkBoxNode;
	}
	public boolean isCellEditable(EventObject event) {
		boolean returnValue = false;
		if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;
			TreePath path = tree.getPathForLocation(mouseEvent.getX(),
					mouseEvent.getY());
			if (path != null) {
				Object node = path.getLastPathComponent();
				if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
					Object userObject = treeNode.getUserObject();
					returnValue = ((treeNode.isLeaf()) && (userObject instanceof CheckBoxNode));
				}
			}
		}
		return returnValue;
	}
	public Component getTreeCellEditorComponent(JTree tree, Object value,boolean selected, boolean expanded, boolean leaf, int row) {
		Component editor = renderer.getTreeCellRendererComponent(tree, value,true, expanded, leaf, row, true);
		// editor always selected / focused
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if (stopCellEditing()) {
					fireEditingStopped();
				}
			}
		};
		if (editor instanceof JCheckBox) {
			((JCheckBox) editor).addItemListener(itemListener);
		}
		return editor;
	}
}
class CheckBoxNode {
	String text;
	boolean selected;
	public CheckBoxNode() {
		text = null;
		selected = false;
	}
	public CheckBoxNode(String text, boolean selected) {
		this.text = text;
		this.selected = selected;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean newValue) {
		selected = newValue;
	}
	public String getText() {
		return text;
	}
	public void setText(String newValue) {
		text = newValue;
	}
	public String toString() {
		return getClass().getName() + "[" + text + "/" + selected + "]";
	}
}
class NamedVector extends Vector<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;

	public NamedVector(String name) {
		this.name = name;
	}
	public NamedVector(String name, Object elements[]) {
		this.name = name;
		for (int i = 0, n = elements.length; i < n; i++) {
			add(elements[i]);
		}
	}
	public String toString() {
		return "[" + name + "]";
	}
}
