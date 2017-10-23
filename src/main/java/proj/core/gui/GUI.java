package proj.core.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import proj.core.gui.abstracts.StatusLabel;
import proj.core.gui.abstracts.TextSettable;
import proj.core.gui.abstracts.TextSubscriber;
import proj.core.gui.abstracts.URITextField;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField transactionsTextField;
	private JTextField identifiersTextField;
	private JTextField accountTextField;
	private JLabel statusLabel;

	public GUI() {
		initPrimaryContentPanel();
		addAccountNameLine();
		addTransactionsSourceLine();
		addIdentifiersSourceLine();
		addStatusLine();
		addParseActionLine();
		setDefaultSettings();
	}

	private void initPrimaryContentPanel() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
	}
	
	private void addAccountNameLine() {
		JLabel accountLabel = new JLabel("Account:");
		contentPane.add(accountLabel, "2, 2, right, default");
		
		accountTextField = new JTextField();
		contentPane.add(accountTextField, "4, 2, fill, default");
		accountTextField.setColumns(10);	
	}

	private void addTransactionsSourceLine() {
		JLabel TransactionsLabel = new JLabel("Transactions:");
		contentPane.add(TransactionsLabel, "2, 4, right, default");
		
		transactionsTextField = new URITextField();
		transactionsTextField.setText("\\");
		contentPane.add(transactionsTextField, "4, 4, 5, 1, fill, default");
		transactionsTextField.setColumns(10);
		
		JButton btnBrowseTr = new JButton("Browse");
        btnBrowseTr.addMouseListener(new FilePathSelector(GUI.this)
        		.setFilters("CSV", "csv")
        		.setSubscriber(new TextSubscriber((TextSettable) transactionsTextField)));
        contentPane.add(btnBrowseTr, "10, 4");
	}
	
	private void addIdentifiersSourceLine() {
		JLabel IdentifiersLabel = new JLabel("Identifiers:");
		contentPane.add(IdentifiersLabel, "2, 6, right, default");
		
		identifiersTextField = new URITextField();
		identifiersTextField.setText("\\");
		contentPane.add(identifiersTextField, "4, 6, 5, 1, fill, default");
		identifiersTextField.setColumns(10);
		
		JButton btnBrowseId = new JButton("Browse");
        btnBrowseId.addMouseListener( new FilePathSelector(GUI.this)
        		.setFilters("CSV", "csv")
        		.setSubscriber(new TextSubscriber((TextSettable) identifiersTextField)));
        contentPane.add(btnBrowseId, "10, 6");
	}

	private void addStatusLine() {
		statusLabel = new StatusLabel("");
		contentPane.add(statusLabel, "2, 10, 9, 1");	
	}
	
	private void addParseActionLine() {
		JProgressBar progressBar = new JProgressBar();
		contentPane.add(progressBar, "2, 8, 7, 1");
		
		JButton btnParse = new JButton("Parse");
		btnParse.addMouseListener(new ParseRequestProcessor(accountTextField, transactionsTextField, identifiersTextField)
				.setSubscriber(new TextSubscriber((TextSettable) statusLabel)));
		contentPane.add(btnParse, "10, 8");	
	}

	private void setDefaultSettings() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 200));
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public void start() {
		setVisible(true);
		pack();	
	}

}
