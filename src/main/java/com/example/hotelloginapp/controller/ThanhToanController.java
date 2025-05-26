package com.example.hotelloginapp.controller;

import com.example.hotelloginapp.dao.*;
import com.example.hotelloginapp.models.DatPhong;
import com.example.hotelloginapp.models.HoaDon;
import com.example.hotelloginapp.models.Phong;
import com.example.hotelloginapp.models.SuDungDV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.hotelloginapp.dao.PhongDao.capNhatTrangThaiPhong;
import static com.example.hotelloginapp.utils.util.showDialog;


public class ThanhToanController implements Initializable {
    @FXML private Label lbMaPhong;
    @FXML private Label lbMaDatPhong;
    @FXML private Label lbKieuPhong;
    @FXML private Label lbMaHoaDon;
    @FXML private Label lbNgayLapHD;
    @FXML private Label lbKhachHang;
    @FXML private Label lbNgayThue;
    @FXML private Label lbMaNV;
    @FXML private Label lbTongTIen;
    @FXML private TextField tfTiendua,ngayTraField;
    @FXML private Label lbTienDu;
    @FXML private Label lbTongTienDV;
    @FXML private Label lbSoGio;
    @FXML private Label lbTongTienGio;
    @FXML private ChoiceBox<String> choiceBoxPhuongThuc;
    @FXML private TableView<SuDungDV> tableSuDungDV;
    @FXML private TableColumn<SuDungDV, String> colTenDV;
    @FXML private TableColumn<SuDungDV, Integer> colSoLuong;
    @FXML private TableColumn<SuDungDV, BigDecimal> colTongTien;

    @FXML private Button btnThanhToan;

    private double tongTien;
    private Phong phong;
    private ThemPhongController.PhongChangeListener listener;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String maHDDisplay = IdGeneratorDao.generateFormattedId("HD", "HoaDon", 3);
        lbMaHoaDon.setText(maHDDisplay);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        lbNgayLapHD.setText(now.format(formatter));
        ngayTraField.setText(now.format(formatter));
        ngayTraField.setEditable(false);
        choiceBoxPhuongThuc.getItems().addAll("Tien mat", "Tai khoan");
        choiceBoxPhuongThuc.setValue("Tien mat"); // mặc định chọn

        tfTiendua.textProperty().addListener((obs, oldVal, newVal) -> {
            tinhTienDu();
        });

        btnThanhToan.setOnAction(e -> handleThanhToan());
    }


    public void setPhong(Phong phong) {
        this.phong = phong;
        loadThongTinThanhToan(); // tự động hiển thị khi truyền phòng vào
    }
    private void loadThongTinThanhToan() {
        lbMaPhong.setText(phong.getMaPhong());
        lbKieuPhong.setText(phong.getKieuPhong());

        DatPhong datPhong = DatPhongDao.getDatPhongByMaPhong(phong.getMaPhong());
        if (datPhong != null) {
            lbMaDatPhong.setText(String.format("DP%03d", datPhong.getMaDP()));
            lbNgayThue.setText(datPhong.getNgayThue().toString());
            lbMaNV.setText(String.valueOf(datPhong.getMaNV()));

            // Lấy khách hàng
            String tenKH = QLKhachHangDao.getTenKhachHangByMa(datPhong.getMaKH());
            lbKhachHang.setText(tenKH);

            // Gọi tính giờ
            LocalDateTime ngayTra = LocalDateTime.now();
            tinhSoGioLuuTru(datPhong.getNgayThue(), ngayTra);

            // (Tùy chọn) Hiển thị tổng tiền giờ nếu có giá phòng theo giờ
            BigDecimal giaTheoGio = phong.getGiaGio(); // Đảm bảo getter này tồn tại
            Duration duration = Duration.between(datPhong.getNgayThue(), ngayTra);
            long soGio = duration.toHours();
            BigDecimal tienPhong = giaTheoGio.multiply(BigDecimal.valueOf(soGio));
            lbTongTienGio.setText(tienPhong.toString());

            // Load dịch vụ
            loadSuDungDV(datPhong.getMaDP());
            tinhTongTien();

        } else {
            lbMaDatPhong.setText("Chưa đặt");
            lbNgayThue.setText("...");
            lbMaNV.setText("...");
            lbKhachHang.setText("...");
            lbSoGio.setText("...");
            lbTongTienGio.setText("0");
            lbTongTienDV.setText("0");
        }
    }

    private void loadSuDungDV(int maDP) {
        List<SuDungDV> list = SuDungDVDao.getSuDungDVWithTenDVByMaDP(maDP); // ✅ dùng hàm mới
        ObservableList<SuDungDV> data = FXCollections.observableArrayList(list);

        colTenDV.setCellValueFactory(new PropertyValueFactory<>("tenDV"));  // ✅ lấy tên
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));

        tableSuDungDV.setItems(data);

        BigDecimal tongTien = data.stream()
                .map(SuDungDV::getTongTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        lbTongTienDV.setText(tongTien.toString());
    }

    private void tinhSoGioLuuTru(LocalDateTime ngayThue, LocalDateTime ngayTra) {
        if (ngayThue != null && ngayTra != null) {
            Duration duration = Duration.between(ngayThue, ngayTra);
            long soGio = duration.toHours(); // Lấy tổng số giờ
            lbSoGio.setText(soGio + " giờ");
        } else {
            lbSoGio.setText("Không xác định");
        }
    }
    private void tinhTongTien() {
        BigDecimal tongGio = new BigDecimal(lbTongTienGio.getText());
        BigDecimal tongDV = new BigDecimal(lbTongTienDV.getText());

        BigDecimal tong = tongGio.add(tongDV);
        lbTongTIen.setText(tong.toString());
        tinhTienDu();
    }

    private void tinhTienDu() {
        try {
            BigDecimal tienDua = new BigDecimal(tfTiendua.getText());
            BigDecimal tongTien = new BigDecimal(lbTongTIen.getText());

            BigDecimal tienDu = tienDua.subtract(tongTien);
            lbTienDu.setText(tienDu.toString());
        } catch (NumberFormatException e) {
            lbTienDu.setText("...");
        }
    }

    public void setPhongChangeListener(ThemPhongController.PhongChangeListener listener) {
        this.listener = listener;
    }

    private void handleThanhToan() {
        try {
            BigDecimal tienDua = new BigDecimal(tfTiendua.getText());
            BigDecimal tongTien = new BigDecimal(lbTongTIen.getText());

            if (tienDua.compareTo(tongTien) < 0) {
                showDialog("Số tiền đưa chưa đủ để thanh toán.");
                return;
            }

            HoaDon hoaDon = new HoaDon();

            String maDatPhongStr = lbMaDatPhong.getText();
            if (!maDatPhongStr.startsWith("DP")) {
                showDialog( "Mã đặt phòng không hợp lệ.");
                return;
            }
            int maDP = Integer.parseInt(maDatPhongStr.substring(2));
            hoaDon.setMaDP(maDP);  // Sửa lại chỗ này
            DatPhongDao.updateNgayTra(maDP, LocalDateTime.now());

            LocalDateTime ngayLapHD = LocalDateTime.now();
            hoaDon.setNgayLapHD(ngayLapHD);

            hoaDon.setTongTien(tongTien);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime ngayTT = LocalDateTime.parse(ngayTraField.getText(), formatter);
            hoaDon.setNgayTT(ngayTT);

            hoaDon.setPhuongThucTT(choiceBoxPhuongThuc.getValue());

            boolean success = HoaDonDao.insertHoaDon(hoaDon);

            if (success) {
                showDialog("Thanh toán thành công, Hóa đơn đã được lưu.");

                // Cập nhật trạng thái phòng sang "Trống"
                PhongDao.capNhatTrangThaiPhong(phong.getMaPhong(), "Trống");


                // Gọi listener nếu có để cập nhật UI ngoài
                capNhatTrangThaiPhong(lbMaPhong.getText(), "Trong");

                if (listener != null) {
                    listener.onPhongAdded();
                }
                closeWindow();
            } else {
                showDialog("Thanh toán không thành công, vui lòng thử lại.");
            }
        } catch (NumberFormatException e) {
            showDialog("Số tiền đưa không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            showDialog("Có lỗi xảy ra trong quá trình thanh toán.");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnThanhToan.getScene().getWindow();
        stage.close();
    }

}
