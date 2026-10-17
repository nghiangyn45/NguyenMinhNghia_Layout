package com.example.nguyenminhnghia_2415053122129_quicktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nguyenminhnghia_2415053122129_quicktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Khai báo ViewBinding khớp với tên file layout activity_main.xml
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bắt sự kiện Click nút Tính toán
        binding.btnCalculate.setOnClickListener {
            calculateBMI()
        }

        // Lắng nghe trạng thái Switch (đã định nghĩa tường minh tham số)
        binding.switchSave.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Đã bật lưu lịch sử" else "Đã tắt lưu lịch sử"
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateBMI() {
        val weightStr = binding.edtWeight.text.toString().trim()
        val heightStr = binding.edtHeight.text.toString().trim()

        // Validate dữ liệu trống
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

        // Công thức tính chỉ số BMI
        val bmi = weight / (height * height)

        // Phân loại kết quả BMI
        val classification = when {
            bmi < 18.5 -> "Gầy"
            bmi < 25.0 -> "Bình thường"
            bmi < 30.0 -> "Thừa cân"
            else -> "Béo phì"
        }

        // Lấy giới tính từ RadioGroup
        val genderId = binding.rgGender.checkedRadioButtonId
        val gender = if (genderId == R.id.rbMale) "Nam" else "Nữ"

        // Hiển thị kết quả lên CardView (TextView)
        val resultText = String.format("BMI: %.2f (%s - %s)", bmi, classification, gender)
        binding.tvResult.text = resultText
    }
}