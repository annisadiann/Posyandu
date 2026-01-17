# 👶 Posyandu Digital App

> **Aplikasi Pemantauan Kesehatan Balita Berbasis Android**
> Dibuat untuk memudahkan Admin Posyandu dalam mendata pasien, memantau gizi, dan menjadwalkan kontrol rutin.

---

## 🚀 Fitur Utama

* **🔐 Autentikasi Admin**: Sistem Login dan Register yang aman untuk pengelola.
* **📋 Manajemen Pasien**: Pendataan Ibu dan Balita dalam satu relasi data yang terintegrasi.
* **📊 Analisis Gizi Otomatis**: Perhitungan status gizi (Normal, Kurang, Buruk) secara real-time berdasarkan input BB, TB, dan Umur.
* **📅 Agenda Kontrol**: Pengelolaan jadwal kunjungan ulang balita yang memerlukan perhatian khusus.
* **📈 Rekapitulasi Laporan**: Fitur generate laporan periodik berdasarkan data pemeriksaan yang masuk.

---

## 🛠️ Teknologi yang Digunakan

* **Bahasa**: [Kotlin](https://kotlinlang.org/)
* **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Modern Declarative UI)
* **Database**: [Room Persistence Library](https://developer.android.com/training/data-storage/room) (SQLite)
* **Architecture**: MVVM (Model-View-ViewModel)
* **State Management**: Kotlin Flows & StateFlow

---

## 📱 Tampilan Aplikasi

| Daftar Pasien | Input Pemeriksaan | Laporan Statistik |
| :---: | :---: | :---: |
| |

---

## 🏗️ Struktur Proyek

Aplikasi ini mengikuti pola desain **MVVM** untuk memastikan kode mudah dipelihara:

- **Data**: Entity, DAO, Database, dan Repository.
- **ViewModel**: Logika bisnis dan pengolahan data dari Repository ke UI.
- **UI**: Komponen Compose (Screen & Reusable Items).
- **Utils**: Helper untuk validasi, konverter tanggal, dan shared preferences.

<img width="235" height="478" alt="Screenshot 2026-01-17 091722" src="https://github.com/user-attachments/assets/d4fb4648-790a-4946-aebb-523fd5f75a64" />

<img width="233" height="473" alt="Screenshot 2026-01-17 091846" src="https://github.com/user-attachments/assets/361829b4-eac7-46fc-b3c4-d950b627122b" />

<img width="231" height="474" alt="Screenshot 2026-01-17 092058" src="https://github.com/user-attachments/assets/58c99305-b929-42cb-83fe-4c5669984887" />

<img width="240" height="476" alt="image" src="https://github.com/user-attachments/assets/f801d8ea-0bda-42de-8e3e-dd831686d789" />

<img width="233" height="473" alt="Screenshot 2026-01-17 092824" src="https://github.com/user-attachments/assets/51288980-7514-4356-bf1f-7fcd370f568c" />

<img width="230" height="475" alt="Screenshot 2026-01-17 092912" src="https://github.com/user-attachments/assets/706010fd-7220-492e-b89a-727612b4a25b" />

<img width="235" height="475" alt="Screenshot 2026-01-17 092946" src="https://github.com/user-attachments/assets/b043b77e-83dd-46b8-beab-4b45b6ca0277" />

<img width="227" height="470" alt="Screenshot 2026-01-17 093017" src="https://github.com/user-attachments/assets/261381cc-a186-46d9-aec8-da55fd9fd7c5" />

<img width="231" height="469" alt="Screenshot 2026-01-17 093043" src="https://github.com/user-attachments/assets/59232ab8-a4bd-49ad-a305-b878bbd191ad" />

<img width="231" height="474" alt="Screenshot 2026-01-17 094304" src="https://github.com/user-attachments/assets/ae46347c-e19a-45eb-ae2f-0ae9d39bcd46" />

<img width="228" height="470" alt="Screenshot 2026-01-17 093246" src="https://github.com/user-attachments/assets/5cf0f6f2-2a09-4aef-a013-921299d3b699" />

<img width="227" height="470" alt="Screenshot 2026-01-17 093310" src="https://github.com/user-attachments/assets/c8125752-509f-49fa-bf7c-1c676553892f" />

<img width="226" height="471" alt="Screenshot 2026-01-17 093337" src="https://github.com/user-attachments/assets/68ac3a7f-2519-41f4-965c-709e1e5c9d12" />

<img width="232" height="474" alt="Screenshot 2026-01-17 093410" src="https://github.com/user-attachments/assets/5528044c-f3f8-4e5d-9ef7-5a78afcb0aa1" />

<img width="230" height="474" alt="Screenshot 2026-01-17 093447" src="https://github.com/user-attachments/assets/c4316d2c-eafb-4f3a-8b10-8ab9dab8aa85" />

<img width="225" height="476" alt="Screenshot 2026-01-17 093512" src="https://github.com/user-attachments/assets/131878f7-5ee0-4185-89a5-f6d051223910" />

