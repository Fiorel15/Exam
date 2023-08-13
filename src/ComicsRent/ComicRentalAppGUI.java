/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComicsRent;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.EmptyBorder;

public class ComicRentalAppGUI extends JFrame {
    
    // Inisiasi variable UI Component
    private Map<String, Comic> comics;
    private JComboBox<String> comicComboBox;
    private JTextField quantityField;
    private JLabel totalLabel, penulisLabel;
    private JButton calculateButton, saveButton, cancelButton, refreshButton, exitButton;

    private Connection connection;
    
    private Comic getComicById(int selectedComicId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM comic WHERE id_comic = ?");
            preparedStatement.setInt(1, selectedComicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int comicId = resultSet.getInt("id_comic");
                String judul = resultSet.getString("judul");
                String penulis = resultSet.getString("penulis");
                int jml_stok = resultSet.getInt("jml_stok");

                return new Comic(comicId, judul, penulis, jml_stok);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
}

    public ComicRentalAppGUI() {
        initializeDatabase();
        
        // Initialize GUI components
        comics = new HashMap<>();
        comicComboBox = new JComboBox<>();
        retrieveComicsFromDatabase();
        
        comicComboBox = new JComboBox<>(comics.keySet().toArray(new String[0]));
        quantityField = new JTextField(10);
        calculateButton = new JButton("Total");
        saveButton = new JButton("Save Transaction");
        cancelButton = new JButton("Cancel Transaction");
        refreshButton = new JButton("Refresh Field");
        totalLabel = new JLabel();
        penulisLabel = new JLabel();
        exitButton = new JButton("Exit");

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedComicTitle = (String) comicComboBox.getSelectedItem();
                Comic selectedComic = getComicByTitle(selectedComicTitle);
                
                if (selectedComic != null) {
                    int quantity = Integer.parseInt(quantityField.getText());

                    if (selectedComic.getStock() >= quantity) {
                        double totalCost = quantity * 10000;
                        totalLabel.setText("Total dari " + selectedComicTitle + ": Rp " + totalCost);
                         penulisLabel.setText("Penulis: " + selectedComic.getAuthor());
                    } else {
                        totalLabel.setText("Error: Stok tidak mencukupi.");
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedComicTitle = (String) comicComboBox.getSelectedItem();
                Comic selectedComic = comics.get(selectedComicTitle);
                int quantity = Integer.parseInt(quantityField.getText());

                if (selectedComic.getStock() >= quantity) {
                    selectedComic.reduceStock(quantity);
                    updateComicStockInDatabase(selectedComicTitle, selectedComic.getStock());
                    totalLabel.setText("Transaction saved successfully!");
                    penulisLabel.setText("");
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(ComicRentalAppGUI.this, "Input melebihi stok yang tersedia.", "Error", JOptionPane.ERROR_MESSAGE);
                    totalLabel.setText("");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        refreshButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        
        exitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Create panel for input components
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.setBorder(new EmptyBorder(20, 30, 35, 30));

        // Add components to the input panel
        inputPanel.add(new JLabel("Select Comic:"));
        inputPanel.add(comicComboBox);

        inputPanel.add(new JLabel("Penulis:"));
        inputPanel.add(penulisLabel); 

        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(calculateButton);
        inputPanel.add(totalLabel);
        
        // Create panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(0, 10, 10, 30)); 
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(exitButton);
        
        // Create main panel to hold input and button panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        setTitle("Comic Rental App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/comic_rental";
            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Koneksi ke database berhasil.");
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal.");
            e.printStackTrace();
        }
    }
    
    private Comic getComicByTitle(String judul) {
        return comics.get(judul);
    }

    private void retrieveComicsFromDatabase() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM comic");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int comicId = resultSet.getInt("id_comic");
                String judul = resultSet.getString("judul");
                String penulis = resultSet.getString("penulis");
                int jml_stok = resultSet.getInt("jml_stok");

               comics.put(judul, new Comic(comicId, judul, penulis, jml_stok));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateComicStockInDatabase(String comicTitle, int newStock) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE comic SET jml_stok = ? WHERE judul = ?");
            preparedStatement.setInt(1, newStock);
            preparedStatement.setString(2, comicTitle);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private String retrieveAuthorName(String selectedComicTitle) {
        String authorName = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT penulis FROM comic WHERE judul = ?");
            preparedStatement.setString(1, selectedComicTitle);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                authorName = resultSet.getString("penulis");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorName;
    }

    private void clearFields() {
        quantityField.setText("");
        totalLabel.setText("");
        penulisLabel.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComicRentalAppGUI());
    }

    private class Comic {
        private int id_comic;
        private String judul;
        private String penulis;
        private int jml_stok;

        public Comic(int id_comic, String judul, String penulis, int jml_stok) {
            this.id_comic = id_comic;
            this.judul = judul;
            this.penulis = penulis;
            this.jml_stok = jml_stok;
        }
        
         public int getId(){
            return id_comic;
        }

        public String getTitle() {
            return judul;
        }

        public String getAuthor() {
            return penulis;
        }
        
        public String getTitleWithAuthor() {
            return judul + " - " + penulis;
        }

        public int getStock() {
            return jml_stok;
        }

        public void reduceStock(int quantity) {
            jml_stok -= quantity;
        }

        @Override
        public String toString() {
            return judul;
        }
    }
}