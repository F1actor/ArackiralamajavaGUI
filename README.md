# ArackiralamajavaGUI
# 🚗 Araç Kiralama Sistemi

Java Swing tabanlı masaüstü araç kiralama yönetim sistemi. PostgreSQL veritabanı kullanarak araç ve kiralama işlemlerini yönetir.

## 📋 Özellikler

- ✅ **Araç Yönetimi**: Otomobil ve SUV araçlarını ekleme, listeleme ve görüntüleme
- ✅ **Kiralama İşlemleri**: Müşteri bilgileriyle araç kiralama
- ✅ **Fiyat Hesaplama**: Araç tipine göre otomatik fiyat hesaplama (SUV için %10 ek ücret)
- ✅ **Müsaitlik Kontrolü**: Araçların kiralama durumunu takip etme
- ✅ **Grafik Arayüz**: Java Swing ile kullanıcı dostu arayüz
- ✅ **Veritabanı Entegrasyonu**: PostgreSQL ile veri saklama

## 🛠️ Teknolojiler

- **Java 17**: Programlama dili
- **Java Swing**: Grafik kullanıcı arayüzü
- **PostgreSQL**: İlişkisel veritabanı
- **JDBC**: Veritabanı bağlantısı

## 📦 Gereksinimler

- Java JDK 17 veya üzeri
- PostgreSQL 12 veya üzeri
- PostgreSQL JDBC Driver (projede `lib/postgresql-42.7.8.jar` mevcut)

## 🚀 Kurulum

### 1. Projeyi İndirin

```bash
git clone <repository-url>
cd arackiralama
```

### 2. Veritabanını Hazırlayın

PostgreSQL'de yeni bir veritabanı oluşturun:

```sql
CREATE DATABASE idpproject;
```

### 3. Veritabanı Tablolarını Oluşturun

Veritabanınızda aşağıdaki tabloları oluşturun:

```sql
-- Araçlar tablosu
CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY,
    plate VARCHAR(20) UNIQUE NOT NULL,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    fuel_type VARCHAR(20) NOT NULL,
    daily_price DECIMAL(10,2) NOT NULL,
    km INTEGER NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    vehicle_type VARCHAR(10) NOT NULL CHECK (vehicle_type IN ('CAR', 'SUV'))
);

-- Müşteriler tablosu
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL
);

-- Kiralamalar tablosu
CREATE TABLE rentals (
    id SERIAL PRIMARY KEY,
    vehicle_id INTEGER REFERENCES vehicles(id),
    customer_id INTEGER REFERENCES customers(id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 4. Veritabanı Bağlantı Ayarlarını Yapın

`src/com/sinan/rentacar/core/DbConnection.java` dosyasında veritabanı bilgilerinizi güncelleyin:

```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/idpproject";
private static final String DB_USER = "postgres";
private static final String DB_PASSWORD = "sifreniz"; // Buraya şifrenizi yazın
```

### 5. Projeyi Derleyin

```bash
javac -encoding UTF-8 -d bin -cp "lib/postgresql-42.7.8.jar" -sourcepath src src/com/sinan/rentacar/*.java src/com/sinan/rentacar/**/*.java
```

**Windows PowerShell için:**
```powershell
javac -encoding UTF-8 -d bin -cp "lib/postgresql-42.7.8.jar" -sourcepath src src/com/sinan/rentacar/App.java src/com/sinan/rentacar/**/*.java
```

### 6. Uygulamayı Çalıştırın

```bash
java -cp "bin;lib/postgresql-42.7.8.jar" com.sinan.rentacar.App
```

**Linux/Mac için:**
```bash
java -cp "bin:lib/postgresql-42.7.8.jar" com.sinan.rentacar.App
```

## 📁 Proje Yapısı

```
arackiralama/
├── src/
│   └── com/
│       └── sinan/
│           └── rentacar/
│               ├── App.java                    # Ana uygulama giriş noktası
│               ├── core/
│               │   ├── DbConnection.java       # Veritabanı bağlantı yönetimi
│               │   └── IGenericRepository.java # Generic repository interface
│               ├── entity/
│               │   ├── Vehicle.java            # Abstract araç sınıfı
│               │   ├── Car.java                # Otomobil sınıfı
│               │   ├── SUV.java                # SUV sınıfı
│               │   ├── Customer.java           # Müşteri sınıfı
│               │   └── Rental.java             # Kiralama sınıfı
│               ├── repository/
│               │   ├── VehicleRepository.java  # Araç veri erişim katmanı
│               │   ├── CustomerRepository.java # Müşteri veri erişim katmanı
│               │   └── RentalRepository.java   # Kiralama veri erişim katmanı
│               ├── service/
│               │   ├── VehicleService.java     # Araç iş mantığı
│               │   └── RentalService.java      # Kiralama iş mantığı
│               └── view/
│                   ├── VehicleView.java        # Ana pencere (araç listesi)
│                   └── RentalDialog.java      # Kiralama dialog penceresi
├── lib/
│   └── postgresql-42.7.8.jar                  # PostgreSQL JDBC driver
└── README.md
```

## 🏗️ Mimari

Proje **3 katmanlı mimari** kullanır:

1. **Entity Layer**: Veri modelleri (Vehicle, Customer, Rental)
2. **Repository Layer**: Veritabanı işlemleri (CRUD operasyonları)
3. **Service Layer**: İş mantığı (business logic)
4. **View Layer**: Kullanıcı arayüzü (Swing GUI)

### Tasarım Desenleri

- **Singleton Pattern**: `DbConnection` sınıfında tek bağlantı örneği
- **Repository Pattern**: Veri erişim katmanını soyutlama
- **Polymorphism**: `Vehicle` abstract sınıfı ve `Car`, `SUV` alt sınıfları
- **Strategy Pattern**: Farklı araç tipleri için farklı fiyat hesaplama

## 💻 Kullanım

### Araç Ekleme

1. Ana pencerede form alanlarını doldurun:
   - **Tür**: Otomobil veya SUV seçin
   - **Plaka**: Araç plakası (benzersiz olmalı)
   - **Marka**: Araç markası
   - **Model**: Araç modeli
   - **Yakıt**: Yakıt tipi (Benzin, Dizel, Elektrik, vb.)
   - **Fiyat**: Günlük kiralama fiyatı
   - **KM**: Araç kilometresi

2. **"YENİ ARAÇ EKLE"** butonuna tıklayın

### Araç Kirala

1. Tablodan kiralamak istediğiniz aracı seçin
2. **"SEÇİLİ ARACI KİRALA"** butonuna tıklayın
3. Açılan dialog penceresinde müşteri bilgilerini girin:
   - Müşteri adı
   - E-posta
   - Telefon
   - Başlangıç tarihi
   - Bitiş tarihi
4. Sistem otomatik olarak toplam fiyatı hesaplayacaktır
5. **"KİRALA"** butonuna tıklayın

### Fiyat Hesaplama

- **Otomobil**: `Günlük Fiyat × Gün Sayısı`
- **SUV**: `(Günlük Fiyat × Gün Sayısı) × 1.10` (%10 ek ücret)

## 🔧 Sorun Giderme

### Veritabanı Bağlantı Hatası

- PostgreSQL servisinin çalıştığından emin olun
- `DbConnection.java` dosyasındaki bağlantı bilgilerini kontrol edin
- Veritabanı şifresinin doğru olduğundan emin olun

### Derleme Hatası

- Java JDK 17'nin yüklü olduğundan emin olun
- PostgreSQL JDBC driver'ın `lib` klasöründe olduğunu kontrol edin
- Classpath ayarlarını kontrol edin

### "DbConnection cannot be resolved" Hatası

- Projeyi yeniden derleyin
- Tüm Java dosyalarının `src` klasörü altında doğru paket yapısında olduğundan emin olun

## 📝 Notlar

- Plaka numaraları benzersiz olmalıdır
- Kiralık araçlar tekrar kiralanamaz
- SUV araçlar için %10 ek ücret uygulanır
- Tüm tarih işlemleri `java.sql.Date` kullanır

## 👤 Geliştirici

Sinan - Araç Kiralama Sistemi

## 📄 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

