package view;

import models.Users;
import repository.UserImplements;

import javax.swing.*;
import java.awt.*;

public class UserDialog extends JDialog {
    private JTextField txtName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;

    private UserImplements userImplements;
    private UserForm userForm;
    private Users editUser;

    public UserDialog(UserForm userForm, Users user){
        super(userForm, "Edit user", true);
        this.userImplements = new UserImplements();
        this.userForm = userForm;
        this.editUser = user;

        setSize(400,300);
        setLocationRelativeTo(userForm);
        setLayout(new BorderLayout());

        initializeComponents();
        loadData();
    }

    public UserDialog(UserForm userForm) {
        super(userForm, "New User", true);
        this.userForm = userForm;
        userImplements = new UserImplements();

        setSize(400,300);
        setLocationRelativeTo(userForm);
        setLayout(new BorderLayout());

        initializeComponents();
    }

    private void initializeComponents() {

        JPanel panel = new JPanel(new GridLayout(4,2,10,10));

        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton save = new JButton("Save");
        save.addActionListener(e -> saveUser());

        JPanel buttons = new JPanel();
        buttons.add(save);

        add(panel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void saveUser() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Complete required fields.");
            return;
        }

        if(editUser == null){
            //Insert
            Users user = new Users(name, email, password);

            boolean insert = userImplements.insert(user);
            if(insert){
                JOptionPane.showMessageDialog(this, "User saved successfully.");
                userForm.reloadTable();
                dispose();
        }
    }else{
            //Update
            editUser.setName(name);
            editUser.setEmail(email);
            editUser.setPassword(password);

            boolean updated =  userImplements.update(editUser);
            if(updated){
                JOptionPane.showMessageDialog(this, "Successfully update user.");
                userForm.reloadTable();
                dispose();
            }
        }
        }

    private void loadData() {
        txtName.setText(editUser.getName());
        txtEmail.setText(editUser.getEmail());
        txtPassword.setText(editUser.getPassword());
    }
}
