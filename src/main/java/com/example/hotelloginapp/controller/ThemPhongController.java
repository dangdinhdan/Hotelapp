package com.example.hotelloginapp.controller;

import com.example.hotelloginapp.dao.PhongDao;
import com.example.hotelloginapp.models.Phong;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;

import static com.example.hotelloginapp.utils.util.showDialog;

public class ThemPhongController {

    @FXML private TextField maPhongField;
    @FXML private TextField kieuPhongField;
    @FXML private TextField giaGioField;
    @FXML private ComboBox<String> trangThaiBox;
    public interface PhongChangeListener {
        void onPhongAdded();
    }

    private PhongChangeListener listener;

    public void setPhongChangeListener(PhongChangeListener listener) {
        this.listener = listener;
    }
    @FXML
    public void initialize() {
        trangThaiBox.getItems().addAll("Trong", "Dang su dung");
        trangThaiBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleAddPhong() {
        String maPhong = maPhongField.getText();
        String kieuPhong = kieuPhongField.getText();
        String giaGioStr = giaGioField.getText();
        String trangThai = trangThaiBox.getValue();

        if (maPhong.isEmpty() || kieuPhong.isEmpty() || giaGioStr.isEmpty() || trangThai == null) {
            showDialog("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        try {
            BigDecimal giaGio = new BigDecimal(giaGioStr);
            if (giaGio.compareTo(BigDecimal.ZERO) < 0) {
                showDialog("Giá giờ không được nhỏ hơn 0!");
                return;
            }

            Phong phong = new Phong(maPhong, kieuPhong, giaGio, trangThai);
            boolean success = PhongDao.insertPhong(phong);

            if (success) {
                showDialog("Thêm phòng thành công!");
                if (listener != null) listener.onPhongAdded();
                ((Stage) maPhongField.getScene().getWindow()).close();
            } else {
                showDialog("Phòng đã tồn tại hoặc lỗi hệ thống!");
            }

        } catch (NumberFormatException e) {
            showDialog("Giá phải là số!");
        }
    }

}
