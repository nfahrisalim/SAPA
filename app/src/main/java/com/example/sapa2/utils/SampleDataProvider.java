package com.example.sapa2.utils;

import com.example.sapa2.models.Report;

import java.util.ArrayList;
import java.util.List;

public class SampleDataProvider {

    public static List<Report> getSampleReports() {
        List<Report> reports = new ArrayList<>();

        reports.add(new Report(
                "1",
                "Jalan Berlubang",
                "Tingkat Bahaya: Tinggi",
                "Jl. Antang Raya No.102, Antang, Kec. Manggala, Kota Makassar, Sulawesi Selatan 90234",
                "Jalanan berlubang sangat parah terletak di jalan Antang Raya dekat dengan kediaman ini akses jalan tertutup dan membuat jalanan sekitar menjadi macet. Sering kali karena jalanan berlubang ini pada musim hujan air menggenang dan membuat para warga sulitnya nantinya untuk jalan dan ada deadlok.",
                "sample_image_1",
                "DILAPORKAN",
                "Cantarella Fisalia",
                "avatar_1"
        ));

        reports.add(new Report(
                "2",
                "Kebanjiran",
                "Tingkat Bahaya: Menengah",
                "Jl. Pettarani No.45, Tidung, Kec. Rappocini, Kota Makassar, Sulawesi Selatan 90222",
                "Banjir setinggi 50cm di jalan utama menyebabkan kemacetan parah dan mengganggu aktivitas warga. Dibutuhkan tindakan segera untuk membersihkan saluran pembuangan yang tersumbat.",
                "sample_image_2",
                "DIPROSES",
                "Ahmad Fauzi",
                "avatar_2"
        ));

        reports.add(new Report(
                "3",
                "Lampu Jalan Rusak",
                "Tingkat Bahaya: Rendah",
                "Jl. Perintis Kemerdekaan Km.10, Tamalanrea, Kota Makassar, Sulawesi Selatan 90245",
                "Sejumlah lampu jalan di sepanjang jalan utama tidak berfungsi selama seminggu terakhir. Kondisi ini membahayakan pengguna jalan pada malam hari.",
                "sample_image_3",
                "SELESAI",
                "Ratna Dewi",
                "avatar_3"
        ));

        reports.add(new Report(
                "4",
                "Pohon Tumbang",
                "Tingkat Bahaya: Tinggi",
                "Jl. Veteran Selatan No.17, Banta-Bantaeng, Kec. Rappocini, Kota Makassar, Sulawesi Selatan 90141",
                "Pohon besar tumbang di tengah jalan menyebabkan jalan terblokir dan merusak kabel listrik. Dibutuhkan tim penanganan segera untuk mengamankan area dan membersihkan jalan.",
                "sample_image_4",
                "DIPROSES",
                "Bayu Prakoso",
                "avatar_4"
        ));

        reports.add(new Report(
                "5",
                "Tiang Listrik Miring",
                "Tingkat Bahaya: Menengah",
                "Jl. Sultan Alauddin No.63, Mangasa, Kec. Tamalate, Kota Makassar, Sulawesi Selatan 90221",
                "Tiang listrik terlihat miring dan berbahaya setelah hujan deras kemarin. Kabel-kabel listrik terlihat kendur dan bisa membahayakan warga yang lewat di bawahnya.",
                "sample_image_5",
                "DILAPORKAN",
                "Dina Putri",
                "avatar_5"
        ));

        return reports;
    }
}