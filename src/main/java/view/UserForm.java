package view;

import models.Users;
import repository.UserImplements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserForm extends JFrame {
    private JTable usersTable;
    private DefaultTableModel model;
    private UserImplements userImplements;
    private JTextField txtSearch;
    private JButton btnSearch;

    public UserForm(){
        userImplements = new UserImplements();

        setTitle("Sistema de Gestion de Usuarios");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        initComponents();
        loadUsers();
    }

    private void initComponents(){
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Password");

        usersTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(usersTable);

        JButton btnLoad = new JButton("Load");
        btnLoad.addActionListener(e -> loadUsers());

        JButton btnNew = new JButton("New User");
        btnNew.addActionListener(e -> {
            UserDialog dialog = new UserDialog(this);
            dialog.setVisible(true);
        });

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> {
            int row = usersTable.getSelectedRow();

            if(row == -1){
                JOptionPane.showMessageDialog(this, "Please choose a user.");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            Users user = userImplements.SerachByID(id);

            if(user != null){
                UserDialog dialog = new UserDialog(this, user);
                dialog.setVisible(true);
            }
        });

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> {
           int row = usersTable.getSelectedRow();

           if (row == -1){
               JOptionPane.showMessageDialog(this, "Please choose a user.");
               return;
           }

           int confirm = JOptionPane.showConfirmDialog(this, "Delete this user?", "Confirm", JOptionPane.YES_NO_OPTION);

           if(confirm == JOptionPane.YES_OPTION){
               int id = (int) model.getValueAt(row, 0);
               boolean deleted =  userImplements.delete(id);

               if(deleted){
                   JOptionPane.showMessageDialog(this, "Deleted user.");
                   reloadTable();
               } else{
                   JOptionPane.showMessageDialog(this, "Delete error.");
               }
           }
        });

        JPanel panelButton = new JPanel();
        panelButton.add(btnLoad);
        panelButton.add(btnNew);
        panelButton.add(btnEdit);
        panelButton.add(btnDelete);

        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        JPanel searchPanel = new JPanel();
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search");

        searchPanel.add(new JLabel("Search (By Name,ID or Email):"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        add(searchPanel, BorderLayout.NORTH);

        btnSearch.addActionListener(e -> searchUser());
    }

    public void searchUser(){
        String text = txtSearch.getText().trim();

        if(text.isEmpty()){
            loadUsers();// Vuelve a listar a todos
            return;
        }

        model.setRowCount(0); //Reutilizamos la tabla que ya habiamos definido

        try{
            //Buscar por Id
            int id = Integer.parseInt(text);
            Users user = userImplements.SerachByID(id);

            if(user != null){
                model.addRow(new Object[]{
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword()
                });
            }else{
                JOptionPane.showMessageDialog(this, "User not Found");
            }
        }catch(NumberFormatException e){
            Users user = userImplements.byEmail(text); //Si el texto no es numero, buscamos por Email

            if(user != null) {
                model.addRow(new Object[]{
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword()
                });
            }

//            }else{
//                JOptionPane.showMessageDialog(this, "Usuario no encontrado");
//            }

        List<Users> userList = userImplements.seacrhName(text);
        for(Users u : userList) {
            model.addRow(new Object[]{
                    u.getId(),
                    u.getName(),
                    u.getEmail(),
                    u.getPassword()
            });
        }
            if (userList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "User No Found");
            }
        }
    }

    public void loadUsers(){
        model.setRowCount(0);
        List<Users> list = userImplements.list();

        for(Users u : list){
            model.addRow(new Object[]{u.getId(),u.getName(),u.getEmail(),u.getPassword()});
        }
    }

    public void reloadTable(){
        loadUsers();
    }

    public static void main(String[] args) {
            new UserForm();
    }
}
