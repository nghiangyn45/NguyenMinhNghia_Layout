package com.example.nguyenminhnghia_2415053122129_quicktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nguyenminhnghia_2415053122129_quicktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Khai báo ViewBinding tự động ánh xạ giao diện XML
    private lateinit var binding: ActivityMainBinding

    // Biến cờ (flag) phục vụ cho việc thay đổi trạng thái nội dung TextView khi bấm nút
    private var isAlternativeContent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Xử lý sự kiện bấm nút Tính toán / Thay đổi nội dung (Đáp ứng yêu cầu Submit lần 2)
        binding.btnCalculate.setOnClickListener {
            toggleAndCalculateBMI()
        }

        // 2. Xử lý sự kiện bật/tắt Switch
        binding.switchSave.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Đã bật lưu lịch sử đo" else "Đã tắt lưu lịch sử đo"
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()

            // Cập nhật thay đổi trực tiếp lên TextView phụ nếu muốn
            binding.tvTitle.text = if (isChecked) "BMI CALCULATOR (Đã bật lưu)" else "TÍNH CHỈ SỐ BMI"
        }
    }

    private fun toggleAndCalculateBMI() {
        // Đảo trạng thái cờ khi bấm nút
        isAlternativeContent = !isAlternativeContent

        if (isAlternativeContent) {
            // Trạng thái 1: Thay đổi nội dung trực tiếp trên TextView theo yêu cầu submit lần 2
            binding.tvResult.text = "Trạng thái: Đã xử lý thành công dữ liệu giao diện!"
            Toast.makeText(this, "Đã thay đổi nội dung TextView thành công!", Toast.LENGTH_SHORT).show()
        } else {
            // Trạng thái 2: Thực hiện tính toán BMI chuẩn từ dữ liệu nhập vào EditText
            calculateBMIProcess()
        }
    }

    private fun calculateBMIProcess() {
        val weightStr = binding.edtWeight.text.toString().trim()
        val heightStr = binding.edtHeight.text.toString().trim()

        // Validate dữ liệu trống phòng thủ lỗi
        if (weightStr.isEmpty()) {
            binding.edtWeight.error = "Vui lòng nhập cân nặng!"
            binding.edtWeight.requestFocus()
            return
        }

        if (heightStr.isEmpty()) {
            binding.edtHeight.error = "Vui lòng nhập chiều cao!"
            binding.edtHeight.requestFocus()
            return
        }

        val weight = weightStr.toDoubleOrNull()
        val height = heightStr.toDoubleOrNull()

        if (weight == null || height == null || height <= 0.0) {
            Toast.makeText(this, "Giá trị nhập vào không hợp lệ!", Toast.LENGTH_SHORT).show()
            return
        }

        // Công thức tính BMI
        val bmi = weight / (height * height)

        // Phân loại
        val classification = when {
            bmi < 18.5 -> "Gầy"
            bmi < 25.0 -> "Bình thường"
            bmi < 30.0 -> "Thừa cân"
            else -> "Béo phì"
        }

        // Lấy giới tính từ RadioGroup
        val genderId = binding.rgGender.checkedRadioButtonId
        val gender = if (genderId == R.id.rbMale) "Nam" else "Nữ"

        // Hiển thị kết quả chi tiết lên TextView trong CardView
        val resultText = String.format("BMI: %.2f (%s - %s)", bmi, classification, gender)
        binding.tvResult.text = resultText
    }
}