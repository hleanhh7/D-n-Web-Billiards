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

## password PostgreSQL: anh12345;  port: 2706