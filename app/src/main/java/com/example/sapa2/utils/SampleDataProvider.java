package com.example.sapa2.utils;

import com.example.sapa2.model.Report;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SampleDataProvider {

    public static List<Report> getSampleReports() {
        List<Report> reports = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        // Data sampel sekarang menggunakan constructor yang benar
        reports.add(new Report(
                "sample-1",
                "user-123", // Contoh userId
                "Jalan Berlubang Parah di Antang Raya",
                "Jalanan berlubang sangat parah terletak di jalan Antang Raya dekat dengan kediaman ini akses jalan tertutup dan membuat jalanan sekitar menjadi macet.",
                "Jl. Antang Raya No.102, Makassar",
                "Tinggi",
                "https://images.unsplash.com/photo-1593698054438-654b0553a84e?q=80&w=2070&auto=format&fit=crop",
                "DILAPORKAN",
                currentTime - TimeUnit.HOURS.toMillis(2) // 2 jam yang lalu
        ));

        reports.add(new Report(
                "sample-2",
                "user-456", // Contoh userId
                "Banjir di Jalan Pettarani",
                "Banjir setinggi 50cm di jalan utama menyebabkan kemacetan parah dan mengganggu aktivitas warga. Dibutuhkan tindakan segera untuk membersihkan saluran pembuangan.",
                "Jl. Pettarani No.45, Makassar",
                "Sedang",
                "https://images.unsplash.com/photo-1628260274193-41e7caf80016?q=80&w=1974&auto=format&fit=crop",
                "DIPROSES",
                currentTime - TimeUnit.DAYS.toMillis(1) // 1 hari yang lalu
        ));

        reports.add(new Report(
                "sample-3",
                "user-789", // Contoh userId
                "Lampu Jalan Rusak di Perintis Kemerdekaan",
                "Sejumlah lampu jalan di sepanjang jalan utama tidak berfungsi selama seminggu terakhir. Kondisi ini membahayakan pengguna jalan pada malam hari.",
                "Jl. Perintis Kemerdekaan Km.10, Makassar",
                "Rendah",
                "https://images.unsplash.com/photo-1617094541573-2179a4190c6a?q=80&w=1964&auto=format&fit=crop",
                "SELESAI",
                currentTime - TimeUnit.DAYS.toMillis(3) // 3 hari yang lalu
        ));

        reports.add(new Report(
                "sample-4",
                "user-101", // Contoh userId
                "Pohon Tumbang Blokir Jalan Veteran",
                "Pohon besar tumbang di tengah jalan menyebabkan jalan terblokir total dan merusak kabel listrik. Dibutuhkan tim penanganan segera.",
                "Jl. Veteran Selatan No.17, Makassar",
                "Tinggi",
                "https://images.unsplash.com/photo-1603899122634-0096d05908f3?q=80&w=1974&auto=format&fit=crop",
                "DIPROSES",
                currentTime - TimeUnit.DAYS.toMillis(5) // 5 hari yang lalu
        ));

        reports.add(new Report(
                "sample-5",
                "user-112", // Contoh userId
                "Tiang Listrik Miring di Sultan Alauddin",
                "Tiang listrik terlihat miring dan berbahaya setelah hujan deras kemarin. Kabel-kabel listrik terlihat kendur dan bisa membahayakan warga.",
                "Jl. Sultan Alauddin No.63, Makassar",
                "Sedang",
                "https://images.unsplash.com/photo-1611425888204-5178ab0a4542?q=80&w=1974&auto=format&fit=crop",
                "DILAPORKAN",
                currentTime - TimeUnit.DAYS.toMillis(7) // 7 hari yang lalu
        ));

        return reports;
    }
}
