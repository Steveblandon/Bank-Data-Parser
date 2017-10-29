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
	
	private File selectedFile;
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
		this.selectedFile = new File(filePath);
		return this;
	}
	
	@Override
	public FilePathSelector setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
		return this;
	}
	
	@Override
    public void mouseClicked(MouseEvent e) {
		JFileChooser fileChooser = createFileChooserWithFilters();
		setDirectoryToPreviousSelection(fileChooser);
		updateSelectedFileOnUserSelection(fileChooser);
		updateSubscriber();
    }

	private JFileChooser createFileChooserWithFilters() {
		JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(filterDescription, filters);
        fileChooser.setFileFilter(filter);
        return fileChooser;
	}

	private void setDirectoryToPreviousSelection(JFileChooser fileChooser) {
        fileChooser.setCurrentDirectory(selectedFile);
	}

	private void updateSelectedFileOnUserSelection(JFileChooser fileChooser) {
		if (fileChooser.showDialog(parent, "select") == JFileChooser.APPROVE_OPTION) {
        	selectedFile = fileChooser.getSelectedFile();
        }
	}

	private void updateSubscriber() {
		if (subscriber != null) {
    		subscriber.update(selectedFile.getPath());
    	}
	}
}