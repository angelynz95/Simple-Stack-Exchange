# Tugas 3 IF3110 Pengembangan Aplikasi Berbasis Web

Melanjutkan tugas 2 dengan tambahan web security dan frontend framework

### Tujuan Pembuatan Tugas

Diharapkan dengan tugas ini anda dapat mengerti:
* AngularJS dan kegunaannya.
* Web security terkait access token dan HTTP Headers.

### Petunjuk Pengerjaan

1. Gunakan organisasi yang anda buat pada tugas 2.
2. Tambahkan anggota tim pada organisasi anda.
3. Fork pada repository ini dengan organisasi yang telah dibuat pada tugas 2.
4. Silakan commit pada repository anda (hasil fork). Lakukan berberapa commit dengan pesan yang bermakna, contoh: `fix css`, `create post done`, jangan seperti `final`, `benerin dikit`. Disarankan untuk tidak melakukan commit dengan perubahan yang besar karena akan mempengaruhi penilaian (contoh: hanya melakukan satu commit kemudian dikumpulkan). **Commit dari setiap anggota tim akan mempengaruhi penilaian.** Jadi, setiap anggota tim harus melakukan commit yang berpengaruh terhadap proses pembuatan aplikasi.
5. Ganti bagian **link laporan** dengan link laporan anda (misal dropbox) dalam format PDF yang terdiri dari:
  - Komponen apa saja yang diimplementasi dengan AngularJS
  - Mekanisme pembuatan access token dan pengecekan access token terkait IP address dan user agent pengguna.
6. Pull request dari repository anda ke repository ini dengan format **Nama kelompok** - **NIM terkecil** - **Nama Lengkap dengan NIM terkecil** sebelum **Minggu, 6 Desember 2015 23.59**.

### Link Laporan

https://www.dropbox.com/s/ljzb57fjmrwv98o/IF3110-Tugas3-13513002.pdf?dl=0

### Arsitektur Umum Server

```
*---------------------------------------------------------------------------------------*
|Database                                                                               |
|(MYSQL, PGSQL, dll)                                                                    |
*---------------------------------------------------------------------------------------*
        |                               |                                   |
     Adapter                          Adapter                             Adapter
        |                               |                                   |
*-------------------------*       *----------------*           *------------------------*                   
| Comment & Vote Service  |consume|Identity Service|   consume |Stackexchange Webservice|                   
| Any Programming Language|------>|Java Servlet    |<----------|JAX-WS (atau Java SOAP WebService lain)     
|REST API                 |       |REST API        |           |SOAP                    |                   
*-------------------------*       *----------------*           *------------------------*                   
           ^                            ^                           ^          
           | consume                    | consume          consume  | 
           |                            |                           |
           |  *----------------------------------*                  |
           |  |    Frontend WebApp               |                  |
           |  |       JSP                        |                  |
           |  | *-----------------------*        |                  |
           |  | |Comment and Vote App   |        |                  |
           *----|    AngularJS          |        |------------------*
              | *-----------------------*        |
              *----------------------------------*
                     |
                     |
                   connect
                     |
              *----------------------* 
              |Web Browser           |
              |(chrome, firefox, dll)|
              *----------------------*
```

Tugas 3 ini terdiri dari komponen tugas 2 dan tambahan yang harus dibuat:
* Comment dan Vote REST Service: digunakan untuk menerima HTTP request dari client terkait fungsionalitas comment dan vote. Bebas menggunakan bahasa apapun.
* Komponen front end comment dan vote: digunakan untuk melakukan AJAX untuk fungsionalitas komentar dan vote, serta menampilkan hasil komen dan vote tersebut.

### Deskripsi Tugas

Anda diminta untuk menambahkan fitur ke stackexchange yang dibuat pada tugas 2. Kebutuhan fungsional tambahan yang harus dibuat adalah sebagai berikut.

1. Menambahkan komentar pada pertanyaan dari pengguna. Harus menggunakan AngularJS. Tampilan komentar dapat dilihat pada http://stackoverflow.com/ sebagai referensi.
2. Fungsionalitas vote pada JSP dipindah ke AngularJS. Satu pengguna hanya dapat melakukan satu vote untuk setiap pertanyaan
3. Pengguna yang ingin menambahkan komentar atau melakukan vote harus login terlebih dahulu. Silakan cari cara untuk melakukan *sharing session* antara JSP dengan AngularJS. Sebagai contoh, anda dapat menggunakan *cookies* yang dibaca oleh AngularJS atau JSP.
4. Apabila penambahan komentar tidak berhasil, akan dimunculkan pesan error.
5. List komentar tidak perlu diupdate secara real time (maksudnya ketika ada pengguna yang melakukan komentar, pengguna lain tidak perlu langsung mendapatkan komentar baru tersebut).
6. Pengguna dengan IP Address yang berbeda tidak dapat menggunakan access token yang sama.
7. Pengguna dengan User-agent (web browser) yang berbeda tidak dapat menggunakan access token yang sama. Untuk tugas ini, diasumsikan user-agent adalah web browser.
8. Membuat REST Service yang dapat menerima HTTP Request dari AngularJS untuk comment dan vote. Bahasa yang digunakan bebas.
9. Komponen yang harus digunakan pada AngularJS adalah:
  - Data binding (ng-model directives)
  - Controllers (ng-controllers)
  - ng-repeat, untuk menampilkan list
  - $http untuk AJAX request
  - $scope untuk komunikasi data antara controller dengan view.

### Skenario 

Skenario tugas 2 berlaku juga untuk tugas 3. Skenario ini adalah kebutuhan non fungsional yang harus dipenuhi.

##### Comment

1. Pengguna harus login terlebih dahulu dan mendapatkan access token.
2. Pengguna melihat pertanyaan, dan mengisi komentar pada pertanyaan tersebut.
3. AngularJS akan melakukan HTTP Request ke Comment dan Vote REST Service.
4. Comment dan Vote REST Service akan melakukan pengecekan ke Identity Service.
5. Identity Service harus bisa melakukan pengecekan terkait:
  - Apakah access token ini sudah kadaluarsa?
  - Apakah access token ini digunakan pada browser yang berbeda?
  - Apakah access token ini digunakan dengan koneksi internet yang berbeda?
  
  Jika jawaban salah satu pertanyaan tersebut adalah "ya", maka identity service akan memberikan respon error dan detail errornya.
6. Jika comment dan vote service
  - tidak mendapatkan error dari identity service, maka service tersebut akan menyimpan data ke dalam basis data.
  - mendapatkan error dari identity service, maka service tersebut akan menyampaikan pesan error ke pengguna.


##### Login

1. Pada tugas 2, data login adalah username dan password, lalu identity service akan menggenerate token.
2. Pada tugas 3, hasil token anda harus ditambahkan ip address dan user agent.
3. Silakan baca "mekanisme pembuatan token" untuk lebih jelasnya.


##### Mekanisme pembuatan token

1. Token anda harus mempunyai informasi terkait browser (user agent) dan IP address dari pengguna.
2. Identity service harus dapat mengekstrak informasi tersebut.

Sebagai contoh, anda dapat melakukan (tidak harus) konstruksi token dengan format:
`some_random_string#user_agent#ip_address`.
Jika pada tugas 2 token anda adalah
`abcdefgh`
Maka pada tugas 3 token anda adalah
`abcdefgh#Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36#167.205.22.104`

Token tidak perlu dienkripsi-dekripsi (for simplicity)


#### Referensi

[Angular HTTP dan controller](http://www.w3schools.com/angular/angular_http.asp)

[Chrome developer console](https://developer.chrome.com/devtools/docs/console)

untuk developer console Firefox
[Firebug](http://getfirebug.com/)

Silakan googling "user agent parser", "how to get my IP from HTTPServletRequest", dan "HTTP Headers field" untuk penjelasan lebih lanjut.


#### Prosedur Demo
 
Sebelum demo, asisten akan melakukan checkout ke hash commit terakhir yang dilakukan sebelum deadline. Hal ini digunakan untuk memastikan kode yang akan didemokan adalah kode yang terakhir disubmit sebelum deadline.

