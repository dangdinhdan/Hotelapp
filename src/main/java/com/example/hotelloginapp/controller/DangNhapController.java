package com.example.hotelloginapp.controller;
import com.example.hotelloginapp.dao.DangNhapDao;
import com.example.hotelloginapp.models.NhanVien;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.io.IOException;
import static com.example.hotelloginapp.utils.util.showDialog;

public class DangNhapController {
    @FXML
    public PasswordField txtPassword;
    @FXML
    public TextField Username;

    public void Login(ActionEvent actionEvent) {
        String username = Username.getText();
        String password = txtPassword.getText();

        NhanVien nv = DangNhapDao.login(username, password);

        if (nv != null) {
            openMainForm(nv); // Truyền user vào
        } else {
            showDialog("Tài khoản hoặc mật khẩu không chính xác");
        }
    }

    private void openMainForm(NhanVien user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelloginapp/view/hotel-view.fxml"));
            Parent root = loader.load();

            // Lấy controller của màn hình chính
            HotelController controller = loader.getController();
            controller.setUser(user); // Truyền đối tượng user vào controller

            // Hiển thị giao diện chính
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hệ thống quản lý khách sạn");
            stage.show();

            // Đóng cửa sổ login
            Stage currentStage = (Stage) Username.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showDialog("Không thể mở giao diện chính.");
        }
    }
}