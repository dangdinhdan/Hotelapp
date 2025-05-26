package com.example.hotelloginapp.controller;


import com.example.hotelloginapp.models.Phong;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;



public class PhongController {
    @FXML private AnchorPane card;
    @FXML private Label lbmaphong, lbkieuphong, lbtrangthai;
    @FXML private Button actionButton;
    private Phong phong;
    private ThemPhongController.PhongChangeListener listener;


    public void setData(Phong phong) {
        this.phong = phong;
        lbmaphong.setText(phong.getMaPhong());
        lbkieuphong.setText(phong.getKieuPhong());
        lbtrangthai.setText(phong.getTrangThai());
        String status = phong.getTrangThai().toLowerCase();
        if (status.equals("trống") || status.equals("trong")) {
            card.setStyle("-fx-background-color: #d4fcdc; -fx-border-color: green; -fx-border-width: 2; -fx-background-radius: 10;");
            actionButton.setText("Đặt phòng");
        } else if (status.equals("đang sử dụng") || status.equals("dang su dung")) {
            card.setStyle("-fx-background-color: #ffd6d6; -fx-border-color: red; -fx-border-width: 2; -fx-background-radius: 10;");
            actionButton.setText("Thanh toán");
        } else {
            card.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: gray; -fx-border-width: 1;");
            actionButton.setText("Xử lý");
        }

        // Gắn sự kiện cho nút
        actionButton.setOnAction(e -> handleAction());
    }

    @FXML
    private void handleAction() {
        String action = actionButton.getText();
        try {
            FXMLLoader loader;
            Parent root;
            Stage stage = new Stage();

            if (action.equals("Đặt phòng")) {
                loader = new FXMLLoader(getClass().getResource("/com/example/hotelloginapp/view/datphong-view.fxml"));
                root = loader.load();
                DatPhongController controller = loader.getController();
                controller.setPhong(phong);
                controller.setPhongChangeListener(() -> {
                    if (listener != null) listener.onPhongAdded();
                    stage.close();
                });
                stage.setTitle("Đặt phòng");

            } else if (action.equals("Thanh toán")) {
                loader = new FXMLLoader(getClass().getResource("/com/example/hotelloginapp/view/thanhtoan-view.fxml"));
                root = loader.load();
                ThanhToanController controller = loader.getController();
                controller.setPhong(phong);
                controller.setPhongChangeListener(() -> {
                    if (listener != null) listener.onPhongAdded();
                    stage.close();
                });
                stage.setTitle("Thanh toán");
            } else {
                return;
            }

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPhongChangeListener(ThemPhongController.PhongChangeListener listener) {
        this.listener = listener;
    }
}

