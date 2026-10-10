## 1. Cấu trúc cơ bản của Spring Boot
src/
└── main/
    └── java/
        └── com/vidu/ungdung/
            ├── UngdungApplication.java        <-- File chạy ứng dụng gốc
            │
            ├── controller/
            │   └── ProductController.java     <-- File chứa API (Bước 5)
            │
            ├── dto/
            │   ├── request/
            │   │   └── ProductRequest.java    <-- (Bước 3.1)
            │   └── response/
            │       └── ProductResponse.java   <-- (Bước 3.2)
            │
            ├── entity/
            │   └── Product.java               <-- Bảng trong DB (Bước 1)
            │
            ├── repository/
            │   └── ProductRepository.java     <-- Giao tiếp DB (Bước 2)
            │
            └── service/
                ├── ProductService.java        <-- Giao diện hàm (Bước 4.1)
                └── impl/
                    └── ProductServiceImpl.java <-- Code xử lý thật (Bước 4.2)

**Tóm tắt Luồng Dữ Liệu (Data Flow) khi chạy thực tế**
Khi Client gửi một yêu cầu tạo sản phẩm:

1. Client gửi cục JSON có tên và giá (vào Controller).

2. Controller nhận JSON, tự động ép kiểu thành ProductRequest, kiểm tra hợp lệ, rồi ném ProductRequest cho Service.

3. Service nhận ProductRequest, tạo ra một Product (Entity) và nhét dữ liệu vào đó, rồi gọi Repository.

4. Repository nhận Product (Entity) và ra lệnh lưu vào Database.

5. Service lấy kết quả vừa lưu, chuyển nó thành ProductResponse và trả lại cho Controller.

6. Controller biến ProductResponse thành JSON và gửi về cho Client.

## 2. Kết nối PostgreSQL khi chạy trên máy cá nhân

- Cổng mặc định của dự án: `2706`.
- Tên cơ sở dữ liệu: `trananh_billiards`.
- Mật khẩu được truyền bằng biến môi trường `DB_PASSWORD`; không ghi mật khẩu vào mã nguồn hoặc tài liệu.
- Hướng dẫn tạo bảng, dữ liệu mẫu và chạy ứng dụng: [README](../README.md).

Mật khẩu từng được ghi trong lịch sử Git. Nếu đó là mật khẩu đang sử dụng, hãy đổi trong PostgreSQL rồi dùng mật khẩu mới khi chạy ứng dụng. Xóa dòng ghi chép không xóa mật khẩu khỏi lịch sử commit.
