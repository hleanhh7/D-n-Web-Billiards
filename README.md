# Trần Anh Billiards

Dự án học xây dựng website bán dụng cụ bida bằng Java 25, Spring Boot, PostgreSQL và HTML/JavaScript. Hiện có API danh sách `GET /api/products`, API chi tiết `GET /api/products/{id}`, quan hệ sản phẩm–danh mục và trang danh sách sản phẩm.

## Chạy nhanh hằng ngày

Sau khi đã tạo database và chạy ứng dụng thành công lần đầu:

1. Nhấp đúp **`run.cmd`** trong thư mục chứa README này. Hoặc mở terminal tại thư mục đó và gõ `.\run.cmd`.
2. Nhập mật khẩu PostgreSQL khi được hỏi rồi nhấn Enter. Mật khẩu được che khi nhập.
3. Đợi dòng `Started TrananhBilliardsApplication`, rồi mở [http://localhost:8081/](http://localhost:8081/).
4. Giữ cửa sổ chạy ứng dụng mở; nhấn **Ctrl+C** khi muốn dừng.

`run.cmd` gọi `run.ps1`. Tệp PowerShell chọn thư mục dự án, dùng JDK tại `C:\Program Files\Java\jdk-25` nếu chưa đặt `JAVA_HOME`, hỏi mật khẩu và chạy Maven Wrapper. Mật khẩu không được lưu vào tệp; nếu biến `DB_PASSWORD` đã có, script dùng lại biến đó. Các biến `DB_URL`, `DB_USERNAME`, `SERVER_PORT` đang có cũng được giữ nguyên, nên địa chỉ thực tế sẽ thay đổi nếu bạn đã đặt `SERVER_PORT`.

Sau khi sửa Java, dừng rồi chạy lại để biên dịch và nạp mã mới. PostgreSQL cần đang hoạt động; tệp chạy nhanh không tự tạo bảng hoặc bật dịch vụ PostgreSQL.

`-ExecutionPolicy Bypass` trong tệp CMD chỉ áp dụng cho tiến trình PowerShell được mở để chạy script này, không đổi chính sách PowerShell lâu dài trên máy.

## 1. Chuẩn bị

- Cài **JDK 25** và **PostgreSQL**; đảm bảo dịch vụ PostgreSQL đang chạy.
- Có thể dùng **pgAdmin** để tạo database và chạy SQL.
- Maven Wrapper đã có trong dự án, không cần cài Maven riêng. Lần chạy đầu cần Internet để tải Maven và thư viện.
- Các lệnh bên dưới dùng **PowerShell**, bắt đầu tại thư mục chứa README này.

```powershell
Set-Location .\trananh-billiards
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-25'
& "$env:JAVA_HOME\bin\java.exe" -version
```

Nếu JDK nằm ở nơi khác, sửa `JAVA_HOME` cho đúng. Kết quả cần hiển thị Java 25.

## 2. Tạo cơ sở dữ liệu

Trong pgAdmin, kết nối PostgreSQL bằng tài khoản của bạn. Dự án mặc định dùng máy `localhost`, cổng **2706**, tài khoản **postgres**. Nếu PostgreSQL của bạn dùng cổng khác (ví dụ `5432`), dùng cổng đó trong pgAdmin và `DB_URL` ở bước 4.

**Với máy mới:** bấm chuột phải `Databases` → `Create` → `Database`, đặt tên **trananh_billiards**, chọn chủ sở hữu là tài khoản sẽ chạy ứng dụng, rồi lưu.

**Nếu đã có database này:** dùng database hiện có, không xóa hoặc tạo lại.

Chọn database **trananh_billiards** → mở **Query Tool**, rồi mở và chạy lần lượt:

1. [database/01-schema.sql](trananh-billiards/database/01-schema.sql): tạo bảng `products` có `id`, `name`, `price`.
2. [database/02-demo-data.sql](trananh-billiards/database/02-demo-data.sql): tùy chọn, thêm ba sản phẩm mẫu để kiểm tra trang.
3. [database/03-categories.sql](trananh-billiards/database/03-categories.sql): tạo bảng `categories` và thêm cột `products.category_id` tham chiếu danh mục. Cần chạy bước này trước khi khởi động phiên bản Java có entity `Category`.

Chạy từng tệp riêng; chỉ chuyển sang tệp tiếp theo khi tệp trước thành công (tệp dữ liệu mẫu có thể bỏ qua). Giá mẫu là số nguyên theo đơn vị VNĐ, chỉ dùng để thử nghiệm.

Script danh mục giữ nguyên dữ liệu cũ; sản phẩm chưa được gán danh mục có `category_id = NULL`. Nếu bạn đã tự tạo bảng và cột theo bài thực hành, script bỏ qua các phần đã tồn tại. Nó không sửa kiểu cột hoặc bổ sung khóa ngoại cho một cột `category_id` đã tồn tại nhưng được tạo khác hướng dẫn.

Kiểm tra bằng câu SQL:

```sql
SELECT id, name, price FROM public.products ORDER BY id;
```

Script đầu không xóa bảng hoặc bản ghi hiện có. Nếu bảng `products` đã tồn tại, script bỏ qua tạo bảng, **không tự sửa cấu trúc bảng cũ**. Script dữ liệu mẫu không ghi đè giá và không thêm lại các tên mẫu đã có khi chạy lần nữa.

Ứng dụng giữ `ddl-auto=validate`: kiểm tra bảng có phù hợp với entity, không tự tạo hoặc sửa bảng. SQL chỉ chạy khi bạn chủ động thực hiện bước này.

## 3. Đổi mật khẩu cũ nếu đang sử dụng

Mật khẩu từng xuất hiện trong tài liệu đã được Git theo dõi. Nếu đó là mật khẩu thật, hãy đổi trước khi sử dụng tiếp:

1. Trong pgAdmin, mở `Login/Group Roles`, chọn tài khoản đang dùng (mặc định là `postgres`).
2. Mở `Properties` → `Definition`, nhập mật khẩu mới và lưu.
3. Cập nhật mật khẩu đã lưu của kết nối pgAdmin nếu có; dùng mật khẩu mới ở bước 4.

Việc đổi này ảnh hưởng các ứng dụng khác dùng cùng tài khoản PostgreSQL. Mã nguồn hiện tại đã bỏ mật khẩu, nhưng lịch sử Git vẫn giữ các phiên bản cũ. Không dán mật khẩu vào chat, tài liệu hoặc commit.

## 4. Thiết lập kết nối và chạy

Trong **cùng cửa sổ PowerShell** đang ở thư mục `trananh-billiards`, thiết lập:

```powershell
$env:DB_URL = 'jdbc:postgresql://localhost:2706/trananh_billiards'
$env:DB_USERNAME = 'postgres'
$dbCredential = Get-Credential -UserName $env:DB_USERNAME -Message 'Nhập tài khoản và mật khẩu PostgreSQL'
if ($null -eq $dbCredential) { throw 'Bạn chưa nhập thông tin PostgreSQL.' }
$env:DB_USERNAME = $dbCredential.UserName
$env:DB_PASSWORD = $dbCredential.GetNetworkCredential().Password

try {
    .\mvnw.cmd spring-boot:run
} finally {
    Remove-Item Env:DB_PASSWORD -ErrorAction SilentlyContinue
    Remove-Variable dbCredential -ErrorAction SilentlyContinue
}
```

Hộp nhập thông tin che mật khẩu; bạn không phải gõ mật khẩu trực tiếp thành một câu lệnh trong lịch sử PowerShell. Biến môi trường được truyền cho ứng dụng và xóa khỏi cửa sổ này sau khi lệnh chạy kết thúc. Không cần tạo tệp `.env`: Spring Boot trong dự án này không tự đọc tệp đó.

Các biến cấu hình:

| Biến | Mặc định | Ý nghĩa |
| --- | --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:2706/trananh_billiards` | Máy, cổng và tên database |
| `DB_USERNAME` | `postgres` | Tài khoản PostgreSQL; mặc định dành cho học trên máy cá nhân |
| `DB_PASSWORD` | Không có; phải cung cấp | Mật khẩu của tài khoản trên |

Khi có dòng `Started TrananhBilliardsApplication`, mở:

- Trang web: [http://localhost:8081/](http://localhost:8081/).
- API: [http://localhost:8081/api/products](http://localhost:8081/api/products).

Dự án mặc định chạy ở cổng **8081**, vì cổng 8080 trên máy hiện đang được Apache (`httpd`) sử dụng. Nếu trước đó đã đặt biến `SERVER_PORT`, biến này sẽ ghi đè cấu hình trong tệp; dùng `Remove-Item Env:SERVER_PORT -ErrorAction SilentlyContinue` để quay về cổng mặc định của dự án.

API cần trả về một mảng JSON; nếu đã nạp dữ liệu mẫu vào database mới thì có ba sản phẩm. Trang web cần hiển thị tên và giá tương ứng. Khi không có sản phẩm, API trả `[]`; giao diện hiện tại chưa có thông báo danh sách rỗng.

Nhấn **Ctrl+C** để dừng. Khi mở cửa sổ PowerShell mới, thiết lập lại `JAVA_HOME` và các biến kết nối rồi chạy lại bước 4.

## 5. Kiểm tra và xử lý lỗi

Kiểm tra biên dịch trong thư mục `trananh-billiards`:

```powershell
.\mvnw.cmd compile
```

| Hiện tượng | Cách xử lý |
| --- | --- |
| Java không đúng phiên bản / `release version 25 not supported` | Kiểm tra `JAVA_HOME` trỏ tới JDK 25 |
| Thiếu `DB_PASSWORD` hoặc xác thực thất bại | Nhập lại tài khoản, mật khẩu theo bước 4; dùng cùng cửa sổ PowerShell |
| `Connection refused` | Kiểm tra PostgreSQL đã chạy và cổng trong `DB_URL` đúng với pgAdmin |
| Database không tồn tại | Tạo `trananh_billiards` theo bước 2 |
| `Schema-validation` báo thiếu bảng hoặc sai kiểu cột | Chạy script schema trong đúng database; nếu bảng cũ khác cấu trúc, cần đối chiếu và sửa có chủ đích, không xóa dữ liệu để thử |
| Cổng 8081 đang được sử dụng | Dừng phiên ứng dụng cũ hoặc đặt `$env:SERVER_PORT = '8082'` trước khi chạy, rồi mở cổng 8082 |
| Maven thiếu thư viện hoặc plugin | Bật Internet, chạy lại không có `-o`; nếu cần cập nhật tải về, dùng `.\mvnw.cmd -U test` |

Hiện chưa có mã kiểm thử tự động trong `src/test`; lệnh `test` thành công cũng chưa chứng minh luồng đọc PostgreSQL hoạt động. Hãy kiểm tra SQL, API và trang web như trên.

## 6. Tài liệu dự án

- [Định hướng và nghiệp vụ](docs/01-dinh-huong-va-nghiep-vu.md).
- [Sổ tay ghi chép](docs/00-so-tay-ghi-chep.md).
