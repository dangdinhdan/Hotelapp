package com.example.hotelloginapp.controller;

import com.example.hotelloginapp.dao.QLNhanVienDao;
import com.example.hotelloginapp.models.NhanVien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.hotelloginapp.utils.util.showDialog;

public class QLNhanVienController implements Initializable {
    @FXML private TableView<NhanVien> tableNhanVien;
    @FXML private TableColumn<NhanVien, Integer> colMaNV;
    @FXML private TableColumn<NhanVien, String> colTenNV, colCccd, colSdt, colTaiKhoan, colMatKhau, colPhanCap, colGioiTinh;

    @FXML private TextField addManv, addTenNV, addCccd, addSdt, addTaikhoan;
    @FXML PasswordField addMatkhau;
    @FXML private RadioButton rbQuanLy;
    @FXML private RadioButton rbNhanVien;
    @FXML private RadioButton rbNam;
    @FXML private RadioButton rbNu;
    @FXML private Label lbMaNV;
    @FXML private Label lbTenNV;
    @FXML private Label lbSdt;
    @FXML private Label lbCccd;
    @FXML private Label lbTaiKhoan;
    @FXML private Label lbMatKhau;
    @FXML private Label lbPhanCap;
    @FXML private Label lbGioiTinh;
    private ObservableList<NhanVien> nhanvienList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        colTenNV.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
        colCccd.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        colSdt.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        colTaiKhoan.setCellValueFactory(new PropertyValueFactory<>("taiKhoan"));
        colMatKhau.setCellValueFactory(new PropertyValueFactory<>("matKhau"));
        colPhanCap.setCellValueFactory(new PropertyValueFactory<>("phanCap"));
        colGioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));

        ToggleGroup phanCapGroup = new ToggleGroup();
        rbNhanVien.setToggleGroup(phanCapGroup);
        rbQuanLy.setToggleGroup(phanCapGroup);
        ToggleGroup gioiTinhGroup = new ToggleGroup();
        rbNam.setToggleGroup(gioiTinhGroup);
        rbNu.setToggleGroup(gioiTinhGroup);

        loadTable();


        tableNhanVien.setRowFactory(tv -> {
            TableRow<NhanVien> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    NhanVien selected = row.getItem();
                    populateForm(selected);
                }
            });
            return row;
        });

       addListener();
    }
    public void loadTable() {
        nhanvienList = FXCollections.observableArrayList(QLNhanVienDao.getAllNhanVien());
        tableNhanVien.setItems(nhanvienList);
    }

    public void addListener(){
        tableNhanVien.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateLabels(newSelection);
            }
        });
    }
    
    private void updateLabels(NhanVien nv) {
        lbMaNV.setText(String.valueOf(nv.getMaNV()));
        lbTenNV.setText(nv.getTenNV());
        lbCccd.setText(nv.getCccd());
        lbSdt.setText(nv.getSdt());
        lbTaiKhoan.setText(nv.getTaiKhoan());
        lbMatKhau.setText(nv.getMatKhau());
        lbPhanCap.setText(nv.getPhanCap());
        lbGioiTinh.setText(nv.getGioiTinh());
    }

    private void populateForm(NhanVien nv) {
        addManv.setText(String.valueOf(nv.getMaNV()));
        addTenNV.setText(nv.getTenNV());
        addCccd.setText(nv.getCccd());
        addSdt.setText(nv.getSdt());
        addTaikhoan.setText(nv.getTaiKhoan());
        addMatkhau.setText(nv.getMatKhau());

        if ("manager".equals(nv.getPhanCap())) {
            rbQuanLy.setSelected(true);
        } else if ("employee".equals(nv.getPhanCap())) {
            rbNhanVien.setSelected(true);
        }

        if ("nam".equals(nv.getGioiTinh())) {
            rbNam.setSelected(true);
        } else if ("nu".equals(nv.getGioiTinh())) {
            rbNu.setSelected(true);
        }
    }


    public void add(ActionEvent e) {
        if (addManv.getText().isEmpty() || addTenNV.getText().isEmpty() ||
                addCccd.getText().isEmpty() || addSdt.getText().isEmpty() ||
                addTaikhoan.getText().isEmpty() || addMatkhau.getText().isEmpty()) {
            showDialog("Thiếu thông tin, Vui lòng điền đầy đủ tất cả các trường!");
            return;
        }
        if (!rbQuanLy.isSelected() && !rbNhanVien.isSelected()) {
            showDialog("❗ Vui lòng chọn Phân cấp (Quản lý hoặc Nhân viên)!");
            return;
        }

        if (!rbNam.isSelected() && !rbNu.isSelected()) {
            showDialog("❗ Vui lòng chọn Giới tính!");
            return;
        }

        try {
            int maNV = Integer.parseInt(addManv.getText());
            String tenNV = addTenNV.getText();
            String cccd = addCccd.getText();
            String sdt = addSdt.getText();
            String taiKhoan = addTaikhoan.getText();
            String matKhau = addMatkhau.getText();

            String phanCap = rbQuanLy.isSelected() ? "manager" : "employee";
            String gioiTinh = rbNam.isSelected() ? "nam" : "nu";

            NhanVien nv = new NhanVien(maNV, tenNV, cccd, sdt, taiKhoan, matKhau, phanCap, gioiTinh);

            if (QLNhanVienDao.insertNhanVien(nv)) {
                nhanvienList.add(nv);
                showDialog("✅ Thêm nhân viên thành công!");
            } else {
                showDialog("❌ Lỗi khi thêm nhân viên!");
            }

        } catch (NumberFormatException ex) {
            showDialog("❌ Mã NV phải là số!");
        }
    }


    public void deleteNhanVien(ActionEvent e) {
        NhanVien selected = tableNhanVien.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showDialog("❗ Vui lòng chọn một nhân viên để xoá!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xoá");
        confirm.setContentText("Bạn có chắc muốn xoá nhân viên: " + selected.getTenNV() + "?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            if (QLNhanVienDao.deleteNhanVien(selected.getMaNV())) {
                nhanvienList.remove(selected);
                showDialog("✅ Xoá nhân viên thành công!");
            } else {
                showDialog("❌ Lỗi khi xoá nhân viên!");
            }
        }
    }

    public void updateNhanVien(ActionEvent e) {
        NhanVien selected = tableNhanVien.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showDialog("❗ Vui lòng chọn một nhân viên để sửa!");
            return;
        }

        try {
            int maNV = Integer.parseInt(addManv.getText());
            String tenNV = addTenNV.getText();
            String cccd = addCccd.getText();
            String sdt = addSdt.getText();
            String taiKhoan = addTaikhoan.getText();
            String matKhau = addMatkhau.getText();
            String phanCap = rbQuanLy.isSelected() ? "manager" : "employee";
            String gioiTinh = rbNam.isSelected() ? "nam" : "nu";

            selected.setMaNV(maNV);
            selected.setTenNV(tenNV);
            selected.setCccd(cccd);
            selected.setSdt(sdt);
            selected.setTaiKhoan(taiKhoan);
            selected.setMatKhau(matKhau);
            selected.setPhanCap(phanCap);
            selected.setGioiTinh(gioiTinh);

            if (QLNhanVienDao.updateNhanVien(selected)) {
                tableNhanVien.refresh();
                showDialog("✅ Cập nhật thành công!");
            } else {
                showDialog("❌ Cập nhật thất bại!");
            }

        } catch (NumberFormatException ex) {
            showDialog("❌ Mã NV phải là số!");
        }
    }


}
