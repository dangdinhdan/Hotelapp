package com.example.hotelloginapp.controller;

import com.example.hotelloginapp.Enums.RoleEnum;
import com.example.hotelloginapp.models.NhanVien;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HotelController {

    @FXML
    public Pane paneScreen;
    public HBox boxQLKH;
    public HBox boxQLDV;
    public HBox boxQLNV;

    public void initialize() {

   }

    public void loadtrangchu() {

        loadScreen("/com/example/hotelloginapp/view/trangchu-view.fxml");
    }
    private void loadScreen(String fxmlFile) {
        try {
            // Load FXML mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newScreen = loader.load();

            // Thêm màn hình mới vào mainScreen
            paneScreen.getChildren().clear();
            paneScreen.getChildren().add(newScreen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadQlphong() {

        loadScreen("/com/example/hotelloginapp/view/QLphong-view.fxml");
    }

    public void loadQlnhanvien() {
        loadScreen("/com/example/hotelloginapp/view/QLnhanvien-view.fxml");
    }


    public void loadQlkhachhang() {
        loadScreen("/com/example/hotelloginapp/view/QLkhachhang-view.fxml");
    }


    public void loadhoadon() {
        loadScreen("/com/example/hotelloginapp/view/hoadon-view.fxml");
    }


    public void loaddichvu() {
        loadScreen("/com/example/hotelloginapp/view/dichvu-view.fxml");
    }

    @FXML
    private Label lblWelcome;

    public static NhanVien currentUser;

    // Hàm này sẽ được gọi từ LoginController sau khi load xong giao diện
    public void setUser(NhanVien user) {
        currentUser = user;
        lblWelcome.setText("Xin chào, " + currentUser.getTenNV() + " (" + currentUser.getPhanCap() + ")");
        if (currentUser.getPhanCap().equals(RoleEnum.NHAN_VIEN.getValue())) {
             boxQLNV.setVisible(false);
             boxQLDV.setVisible(false);
        }
        loadtrangchu();
    }
    @FXML
    private Button btdangxuat;
    @FXML
    private void logout(ActionEvent event) {
        try {
            // Load lại giao diện đăng nhập
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelloginapp/view/dangnhap-view.fxml"));
            Parent root = loader.load();

            // Tạo và hiển thị màn hình đăng nhập mới
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dang nhap");
            stage.show();

            // Đóng cửa sổ hiện tại
            Stage currentStage = (Stage) btdangxuat.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
