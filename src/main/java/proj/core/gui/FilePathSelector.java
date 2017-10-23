package proj.core.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import proj.core.gui.abstracts.Publisher;
import proj.core.gui.abstracts.Subscriber;

public class FilePathSelector extends MouseAdapter implements Publisher {
	
	private String filePath;
	private String filterDescription;
	private String[] filters;
	private Component parent;
	private Subscriber subscriber;
	
	
	public FilePathSelector(Component parent) {
		this.parent = parent;
		filterDescription = "";
		filters = new String[0];
	}
	
	public FilePathSelector setFilters(String filterDescription, String... fileExtFilters) {
		this.filterDescription = filterDescription;
		this.filters = fileExtFilters;
		return this;
	}
	
	public FilePathSelector setDefaultFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	@Override
	public FilePathSelector setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
		return this;
	}
	
	@Override
    public void mouseClicked(MouseEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(filterDescription, filters);
        fileChooser.setFileFilter(filter);
        File file = new File(filePath != null? filePath : "");
        fileChooser.setCurrentDirectory(file);
        if (fileChooser.showDialog(parent, "select") == JFileChooser.APPROVE_OPTION) {
        	filePath = fileChooser.getSelectedFile().getPath();
        	if (subscriber != null) {
        		subscriber.update(filePath);
        	}
        }
    }
}