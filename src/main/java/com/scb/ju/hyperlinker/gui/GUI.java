package com.scb.ju.hyperlinker.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = -6926505039390838934L;

  private JLabel labelCheckBox = new JLabel("auto complete url schema :");
  private JCheckBox checkBoxAutoCompleteSchema = new JCheckBox();
  private JTextField textFieldUrl = new JTextField();
  private JTextField textFieldEndingCode = new JTextField();
  private JButton buttonOpenLink = new JButton("Open Link");

  private JPanel mainPanel = new JPanel();

  public GUI(String TITLE) {
    super(TITLE);

    initGUI();
  }

  /**
   * Initializes the UI components
   */
  private void initGUI() {
    textFieldUrl.setUI(new HintTextFieldUI("Enter base url"));
    textFieldUrl.setPreferredSize(new Dimension(300, 25));
    textFieldUrl.setMinimumSize(new Dimension(300, 25));
    textFieldEndingCode.setUI(new HintTextFieldUI("Enter ending code"));
    textFieldEndingCode.setPreferredSize(new Dimension(150, 25));
    textFieldEndingCode.setMinimumSize(new Dimension(150, 25));

    mainPanel.setLayout(new FlowLayout());
    addActionListeners();

    mainPanel.add(textFieldUrl);
    mainPanel.add(textFieldEndingCode);
    mainPanel.add(buttonOpenLink);
    mainPanel.add(labelCheckBox);
    mainPanel.add(checkBoxAutoCompleteSchema);

    this.add(mainPanel);

    this.pack();
    this.setResizable(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  /**
   * Adds action listeners to the UI components
   */
  private void addActionListeners() {
    buttonOpenLink.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        openLink();
      }
    });
  }

  /**
   * Opens the browser with a link based of this formula: Url + "/" (if not present) + Ending code
   */
  private void openLink() {
    String parsedURL = getParsedURL();
    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    try {
      if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        URI uri = new URI(parsedURL);
        desktop.browse(uri);
      }
    }
    catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Error: Unable to open the written link");
    }
  }

  /**
   * Parses the string in the textfields, returning a String representation of an URI Schema
   * 
   * @return
   */
  private String getParsedURL() {
    String url = textFieldUrl.getText();
    String code = textFieldEndingCode.getText();

    StringBuffer parsedString = new StringBuffer();
    if (!(url.startsWith("http://") || url.startsWith("ftp://") || url.startsWith("https://"))
        && checkBoxAutoCompleteSchema.isSelected()) {
      parsedString.append("http://");
    }
    parsedString.append(url);
    if (!url.endsWith("/")) {
      parsedString.append("/");
    }
    parsedString.append(code);

    return parsedString.toString();
  }
}
