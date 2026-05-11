package util;

public class UserQueries {

    public static final String insert = "INSERT INTO usuarios (nombre, email, password) VALUES (?,?,?)";

    public static final String select = "SELECT * FROM usuarios";

    public static final String selectId = "SELECT * FROM usuarios WHERE id = ?";

    public static final String selectByEmail = "SELECT * FROM usuarios WHERE email = ?";

    public static final String update = "UPDATE usuarios set nombre = ?, email = ?, password = ? WHERE id = ?";

    public static final String delete = "DELETE FROM usuarios WHERE id = ?";

}
