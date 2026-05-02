# 🔐 OtpKit – Android OTP Input Library (XML + Compose)

A lightweight and customizable OTP (One-Time Password) input library for Android.

Supports both **XML View** and **Jetpack Compose**, with multiple UI styles and full input handling (typing, delete, paste, autofill).

---

## ✨ Features

* ✅ XML & Compose support
* ✅ Box, Circle, and Line styles
* ✅ Paste full OTP support
* ✅ Correct backspace handling
* ✅ Error state support
* ✅ Fully customizable UI
* ✅ Lightweight and easy to integrate

---

## 📸 Screenshots

<img width="300" alt="OtpKit Screenshot" src="https://github.com/user-attachments/assets/33745f9c-59a1-4c15-aba1-82335dc686b4" />

---

# 📦 Installation

### 1️⃣ Add JitPack repository

**settings.gradle / settings.gradle.kts**

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

---

### 2️⃣ Add dependency

```gradle
dependencies {
    implementation("com.github.code-with-anil-mandyal:otpkit:v0.1.7")
}
```

---

# 🧩 XML Usage

```xml
<com.codewithanil.otpkit.view.OtpView
    android:id="@+id/otpView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"

    app:otpLength="6"
    app:cellType="line"

    app:borderColor="@android:color/darker_gray"
    app:focusedBorderColor="@android:color/holo_blue_dark"
    app:filledBorderColor="@android:color/black"
    app:errorBorderColor="@android:color/holo_red_dark"

    app:textSize="18sp"
    app:textColor="@android:color/black"

    app:cornerRadius="0dp"
    app:borderWidth="2dp"/>
```

---

## 📌 XML API

```kotlin
otpView.setOtp("123456")
otpView.getOtp()
otpView.clearOtp()
otpView.setError(true)

otpView.setOnOtpCompleteListener { otp ->
    // Called once when OTP is fully entered
}
```

---

# 🧩 Jetpack Compose Usage

```kotlin
var otp by remember { mutableStateOf("") }

OtpInput(
    value = otp,
    onValueChange = { otp = it },
    onComplete = { finalOtp ->
        Log.d("OTP", finalOtp)
    },
    length = 6,
    isError = false,
    style = OtpStyle(
        cellType = OtpCellType.BOX,
        emptyBorderColor = Color.Gray,
        focusedBorderColor = Color.Blue,
        filledBorderColor = Color.Black,
        errorBorderColor = Color.Red,
        textSize = 18.sp,
        textColor = Color.Black,
        borderWidth = 2.dp
    )
)
```

---

# 🎨 Customization

### Cell Types

* `BOX`
* `CIRCLE`
* `LINE`

---

### Style Options

| Property           | Description                                     |
| ------------------ | ----------------------------------------------- |
| cellSize           | Size of each OTP cell                           |
| spacing            | Space between cells                             |
| textSize           | OTP text size                                   |
| textColor          | Text color                                      |
| borderWidth        | Border thickness                                |
| emptyBorderColor   | Default border color                            |
| focusedBorderColor | Focused cell border                             |
| filledBorderColor  | Filled cell border                              |
| errorBorderColor   | Error state color                               |
| shape              | Custom shape (Compose only)                     |
| cornerRadius       | Corner radius (XML only via `app:cornerRadius`) |

---

# 🧠 Behavior

* ✔ Index-based input (no shifting issues)
* ✔ Paste full OTP automatically distributes values
* ✔ Backspace behaves correctly
* ✔ No duplicate callbacks on complete
* ✔ Works with autofill

---

# 🚀 Roadmap

* ⏳ Kotlin Multiplatform support (Android / iOS)
* ⏳ Animation support
* ⏳ Accessibility improvements

---

# 🤝 Contributing

Feel free to open issues or submit pull requests.

---

# 📄 License

MIT License

Copyright (c) 2026 Anil Kumar

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


---

# 👨‍💻 Author

**Anil Kumar**
GitHub: https://github.com/code-with-anil-mandyal
