# Todo List Backend API

Bu layihə **Java Spring Boot** istifadə edərək yaradılmış sadə bir To-Do List backend servisidir. JWT authentication və H2 in-memory verilənlər bazası ilə təmin edilmişdir.

## 🚀 Xüsusiyyətlər

- ✅ **CRUD əməliyyatları** - Tapşırıq yaradıb, oxuyub, yeniləyib və silə bilərsiniz
- 🔐 **JWT Authentication** - JSON Web Token ilə təhlükəsizlik
- 📊 **H2 Database** - In-memory verilənlər bazası
- 🔍 **Axtarış funksiyası** - Tapşırıqları başlıq üzrə axtara bilərsiniz
- 📋 **Status filtri** - Tamamlanmış və ya tamamlanmamış tapşırıqları filtə edə bilərsiniz
- 🛡️ **Spring Security** - Təhlükəsizlik konfiqurasiyası
- 📝 **Validation** - Məlumat doğruluğu yoxlanması

## 🛠️ Texnologiyalar

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database**
- **JWT (JSON Web Token)**
- **Maven**
- **Hibernate Validator**

## 📋 Tələblər

- Java 17 və ya daha yeni versiya
- Maven 3.6+ (və ya Maven Wrapper istifadə edin)

## 🚀 Qurulum və İşə Salma

### 1. Layihəni klonlayın və ya yükləyin

```bash
git clone <repository-url>
cd todo-list-backend
```

### 2. Layihəni build edin

```bash
./mvnw clean compile
```

### 3. Tətbiqi işə salın

```bash
./mvnw spring-boot:run
```

Tətbiq **http://localhost:8080** ünvanında işə düşəcək.

## 🔐 Authentication

### Login

**POST** `/api/auth/login`

```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Cavab:**
```json
{
  "username": "admin",
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "tokenType": "Bearer"
}
```

### Test istifadəçiləri

- **Admin:** `admin` / `admin123`
- **İstifadəçi:** `user` / `user123`

## 📚 API Endpoint-ləri

### Authentication

| Method | Endpoint | Təsvir |
|--------|----------|---------|
| POST | `/api/auth/login` | Daxil olma |
| GET | `/api/auth/test` | Authentication test |

### Tasks (Tapşırıqlar)

**Qeyd:** Bütün task endpoint-ləri JWT token tələb edir.

| Method | Endpoint | Təsvir |
|--------|----------|---------|
| GET | `/api/tasks` | Bütün tapşırıqları əldə et |
| GET | `/api/tasks/{id}` | ID ilə tapşırıq əldə et |
| POST | `/api/tasks` | Yeni tapşırıq yarat |
| PUT | `/api/tasks/{id}` | Tapşırığı yenilə |
| DELETE | `/api/tasks/{id}` | Tapşırığı sil |
| PATCH | `/api/tasks/{id}/complete` | Tapşırığı tamamlanmış olaraq işarələ |
| PATCH | `/api/tasks/{id}/incomplete` | Tapşırığı tamamlanmamış olaraq işarələ |
| GET | `/api/tasks/completed` | Tamamlanmış tapşırıqlar |
| GET | `/api/tasks/pending` | Tamamlanmamış tapşırıqlar |

### Query Parametrləri

- `completed` - Tapşırıqları status-a görə filtrləyir (true/false)
- `search` - Tapşırıqları başlıq üzrə axtarır

## 📝 API İstifadə Nümunələri

### 1. Login və Token Alma

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

### 2. Bütün Tapşırıqları Alma

```bash
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 3. Yeni Tapşırıq Yaratma

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Yeni tapşırıq",
    "description": "Bu, yeni bir tapşırıq təsviridir"
  }'
```

### 4. Tapşırığı Yeniləmə

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Yenilənmiş başlıq",
    "description": "Yenilənmiş təsvir",
    "completed": true
  }'
```

### 5. Tapşırığı Silmə

```bash
curl -X DELETE http://localhost:8080/api/tasks/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 6. Tapşırıqları Axtarma

```bash
curl -X GET "http://localhost:8080/api/tasks?search=java" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 7. Tamamlanmış Tapşırıqları Alma

```bash
curl -X GET "http://localhost:8080/api/tasks?completed=true" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🗂️ Məlumat Strukturu

### Task Entity

```json
{
  "id": 1,
  "title": "Tapşırıq başlığı",
  "description": "Tapşırıq təsviri",
  "completed": false,
  "createdAt": "2023-12-01T10:00:00",
  "updatedAt": "2023-12-01T10:00:00"
}
```

### Task Create Request

```json
{
  "title": "Tapşırıq başlığı (tələb olunur, max 100 simvol)",
  "description": "Tapşırıq təsviri (isteğe bağlı, max 500 simvol)"
}
```

### Task Update Request

```json
{
  "title": "Yeni başlıq (isteğe bağlı)",
  "description": "Yeni təsvir (isteğe bağlı)",
  "completed": true
}
```

## 🔍 H2 Database Console

H2 verilənlər bazası konsolu aktivdir və development zamanı istifadə edilə bilər:

- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:todolistdb`
- **İstifadəçi adı:** `sa`
- **Şifrə:** `password`

## ⚙️ Konfiqurasiya

### Application Properties

Əsas konfiqurasiya faylı `src/main/resources/application.properties` faylındadır:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:h2:mem:todolistdb
spring.datasource.username=sa
spring.datasource.password=password

# JWT Configuration
app.jwt.secret=your-secret-key
app.jwt.expiration=86400000
```

## 🧪 Test

Əsas API endpoint-lərini test etmək üçün:

1. Tətbiqi işə salın
2. Yuxarıdakı nümunələri istifadə edərək curl komandalarını icra edin
3. H2 console-da məlumatları yoxlayın

## 📊 Sample Data

Tətbiq işə düşdükdə avtomatik olaraq 5 nümunə tapşırıq yaradılır:

1. "Java layihəsini tamamla" - Tamamlanmamış
2. "API testlərini et" - Tamamlanmamış  
3. "README yazısını yaz" - Tamamlanmamış
4. "Kod review" - Tamamlanmış
5. "GitHub-a yüklə" - Tamamlanmamış

## 🛡️ Təhlükəsizlik

- Bütün `/api/tasks/*` endpoint-ləri JWT authentication tələb edir
- `/api/auth/*` endpoint-ləri ümumi girişli (public) dir
- H2 console development üçün aktivdir
- CORS bütün origin-lər üçün aktivdir

## 🚀 Production üçün Məsləhətlər

Production mühitində istifadə etmək üçün:

1. H2 əvəzinə PostgreSQL və ya MySQL istifadə edin
2. JWT secret-i environment variable ilə təyin edin
3. CORS konfiqurasiyasını məhdudlaşdırın
4. SSL/HTTPS aktivləşdirin
5. Logging səviyyəsini azaldın

## 📈 Genişləndirmə İmkanları

- User registration sistemi
- Task kateqoriyaları
- Task priority
- Due dates
- File attachments
- Email notifications
- Task assignments

## 🤝 Töhfə

Bu layihəyə töhfə vermək üçün:

1. Fork edin
2. Feature branch yaradın
3. Dəyişikliklərinizi commit edin
4. Pull request açın

## 📄 Lisenziya

Bu layihə MIT lisenziyası altındadır.

---

**Qeyd:** Bu sadə bir demo layihəsidir və real production istifadəsi üçün əlavə təhlükəsizlik və optimallaşdırma tədbirləri tələb oluna bilər.