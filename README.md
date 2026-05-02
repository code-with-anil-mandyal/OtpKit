# 🔐 OtpKit

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Platform](https://img.shields.io/badge/platform-Android-green.svg)
![Compose](https://img.shields.io/badge/Jetpack-Compose-blue.svg)

---

# 🔐 OtpKit – Android OTP Input Library (XML + Compose)

A lightweight and customizable OTP (One-Time Password) input library for Android.

Supports both **XML View** and **Jetpack Compose**, with multiple UI styles and full input handling (typing, delete, paste, autofill).

---

## ❓ Why OtpKit?

* No shifting bugs while editing OTP
* Handles paste correctly (rare in libraries)
* Works with autofill
* Same API for XML and Compose

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
import com.codewithanil.otpkit.compose.OtpInput
import com.codewithanil.otpkit.compose.OtpStyle
import com.codewithanil.otpkit.compose.OtpCellType

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

MIT License © 2026 Anil Kumar
See the LICENSE file for full details.

---

# 👨‍💻 Author

**Anil Kumar**
GitHub: https://github.com/code-with-anil-mandyal
