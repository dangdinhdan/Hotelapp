package com.example.hotelloginapp.controller;

import com.example.hotelloginapp.dao.*;
import com.example.hotelloginapp.models.DichVu;
import com.example.hotelloginapp.models.KhachHang;
import com.example.hotelloginapp.models.SuDungDV;
import com.example.hotelloginapp.models.Phong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.example.hotelloginapp.controller.HotelController.currentUser;
import static com.example.hotelloginapp.dao.PhongDao.capNhatTrangThaiPhong;
import static com.example.hotelloginapp.utils.util.showDialog;

public class DatPhongController implements Initializable {

    @FXML private Label maPhieuField;
    @FXML private TextField ngayThueField;
    @FXML private TextField maKHField;
    @FXML private TextField tenKHField;
    @FXML private TextField sdtKHField;
    @FXML private TextField cccdKHField;
    @FXML private ChoiceBox<String> dichVuChoiceBox;
    @FXML private Button themDichVuButton;
    @FXML private Button datPhongButton;
    @FXML private Button huyButton;
    @FXML private Label maPhongLabel;
    @FXML private Label loaiPhongLabel;
    @FXML private TextField maNVField;
    @FXML private TableView<SuDungDV> tableDichVu;
    @FXML private TableColumn<SuDungDV, String> colMaDV;
    @FXML private TableColumn<SuDungDV, String> colTenDV;
    @FXML private TableColumn<SuDungDV, BigDecimal> colGiaDV;
    @FXML private TableColumn<SuDungDV, Integer> colSoluong;
    @FXML private Label tongTienLabel;
    @FXML private Button xoaDichVuButton;

    private ObservableList<SuDungDV> danhSachDichVuDaChon = FXCollections.observableArrayList();
    private Map<String, DichVu> danhSachDichVu = new HashMap<>();

    private Phong phong;
    private ThemPhongController.PhongChangeListener listener;

    public void setPhongChangeListener(ThemPhongController.PhongChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String maDPDisplay = IdGeneratorDao.generateFormattedId("DP", "DatPhong", 3);
        maPhieuField.setText(maDPDisplay);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ngayThueField.setText(now.format(formatter));
        ngayThueField.setEditable(false);
        loadDichVu();
        initTableDichVu();

        themDichVuButton.setOnAction(e -> themDichVuVaoTable());
        xoaDichVuButton.setOnAction(e -> xoaDichVuDaChon());
        datPhongButton.setOnAction(e -> datPhong());
        huyButton.setOnAction(this::handleHuy);
        maNVField.setText(String.valueOf(currentUser.getMaNV()));
        maNVField.setEditable(false);
        capNhatTongTien();
    }


    private void loadDichVu() {
        dichVuChoiceBox.setItems(DichVuDao.getAllTenDichVu());

        danhSachDichVu.clear();
        for (DichVu dv : DichVuDao.getAllDichVu()) {
            danhSachDichVu.put(dv.getTenDV(), dv);
        }
    }

    private void initTableDichVu() {
        colMaDV.setCellValueFactory(new PropertyValueFactory<>("maDV"));
        colTenDV.setCellValueFactory(new PropertyValueFactory<>("tenDV"));
        colGiaDV.setCellValueFactory(new PropertyValueFactory<>("giaDV"));
        colSoluong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

        tableDichVu.setItems(danhSachDichVuDaChon);
    }

    @FXML
    private void themDichVuVaoTable() {
        String tenDVChon = dichVuChoiceBox.getValue();
        if (tenDVChon == null || tenDVChon.isEmpty()) {
            showDialog("Vui lòng chọn dịch vụ trước khi thêm!");
            return;
        }

        DichVu dv = danhSachDichVu.get(tenDVChon);
        if (dv == null) {
            showDialog("Dịch vụ không hợp lệ!");
            return;
        }

        SuDungDV sddvFound = null;
        for (SuDungDV sddv : danhSachDichVuDaChon) {
            if (sddv.getMaDV().equals(dv.getMaDV())) {
                sddvFound = sddv;
                break;
            }
        }

        if (sddvFound != null) {
            sddvFound.setSoLuong(sddvFound.getSoLuong() + 1);
            sddvFound.tinhTongTien();  // Cập nhật tổng tiền
            tableDichVu.refresh();
        } else {
            SuDungDV sddvMoi = new SuDungDV();
            sddvMoi.setMaSDDV(0);
            sddvMoi.setNgayThue(LocalDateTime.now());
            sddvMoi.setMaDV(dv.getMaDV());
            sddvMoi.setTenDV(dv.getTenDV());
            sddvMoi.setGiaDV(dv.getGiaDV());
            sddvMoi.setSoLuong(1);
            sddvMoi.tinhTongTien();
            danhSachDichVuDaChon.add(sddvMoi);
        }

        capNhatTongTien();
    }
    @FXML
    private void xoaDichVuDaChon() {
        SuDungDV selected = tableDichVu.getSelectionModel().getSelectedItem();
        if (selected != null) {
            danhSachDichVuDaChon.remove(selected);
            capNhatTongTien();
        } else {
            showDialog("Vui lòng chọn dịch vụ cần xóa trong bảng!");
        }
    }


    private void capNhatTongTien() {
        BigDecimal tongTien = BigDecimal.ZERO;
        for (SuDungDV sddv : danhSachDichVuDaChon) {
            if (sddv.getTongTien() != null) {
                tongTien = tongTien.add(sddv.getTongTien());
            }
        }
        tongTienLabel.setText(String.format("%,.0f VND", tongTien));
    }
    public void setPhong(Phong phong) {
        this.phong = phong;
        if (phong != null) {
            maPhongLabel.setText(phong.getMaPhong());
            loaiPhongLabel.setText(phong.getKieuPhong());
        }
    }

    private void datPhong() {
        try {
            String ngayThueStr = ngayThueField.getText();
            String maKH = maKHField.getText().trim();
            String tenKH = tenKHField.getText().trim();
            String sdtKH = sdtKHField.getText().trim();
            String cccdKH = cccdKHField.getText().trim();
            String maPhong = maPhongLabel.getText();
            String maNVStr = maNVField.getText().trim();

            if (ngayThueStr.isEmpty() || maKH.isEmpty() || maNVStr.isEmpty()) {
                showDialog("Vui lòng điền đầy đủ thông tin (Mã KH, Mã NV, ngày thuê)!");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime ngayThue = LocalDateTime.parse(ngayThueStr, formatter);
            int maNV = Integer.parseInt(maNVStr);

            KhachHang kh = new KhachHang(maKH, tenKH, sdtKH, cccdKH);

            if (!QLKhachHangDao.isKhachHangExists(maKH)) {
                if (!QLKhachHangDao.insertKhachHang(kh)) {
                    showDialog("Không thể thêm khách hàng!");
                    return;
                }
            }

            if (!DatPhongDao.insertDatPhong(maKH, maPhong, maNV, ngayThue)) {
                showDialog("Không thể đặt phòng!");
                return;
            }

            capNhatTrangThaiPhong(maPhong, "Dang su dung");

            int maDPMoi = DatPhongDao.getLastInsertedMaDP();
            if (maDPMoi > 0) {
                for (SuDungDV sddv : danhSachDichVuDaChon) {
                    SuDungDV sddvToInsert = new SuDungDV(
                            0,
                            LocalDateTime.now(),
                            sddv.getSoLuong(),
                            sddv.getMaDV(),
                            sddv.getTenDV(),
                            sddv.getGiaDV(),
                            maDPMoi,
                            sddv.getTongTien()
                    );
                    if (!SuDungDVDao.insertSuDungDV(sddvToInsert)) {
                        showDialog("Đặt dịch vụ " + sddv.getTenDV() + " không thành công!");
                    }
                }
            }

            if (listener != null) {
                listener.onPhongAdded();
            }

            showDialog("Đặt phòng thành công!");
            closeWindow();

        } catch (Exception e) {
            showDialog("Lỗi khi đặt phòng: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private void closeWindow() {
        Stage stage = (Stage) datPhongButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleHuy(ActionEvent event) {
        Stage stage = (Stage) huyButton.getScene().getWindow();
        stage.close();
    }
}
