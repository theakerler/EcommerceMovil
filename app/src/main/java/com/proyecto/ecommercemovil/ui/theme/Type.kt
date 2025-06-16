package com.proyecto.ecommercemovil.ui.theme

    import androidx.compose.material3.Typography
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.Font
    import androidx.compose.ui.text.font.FontFamily
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.sp
    import com.proyecto.ecommercemovil.R

    val Poppins = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal),
    )

    val KiwiFruit = FontFamily(
        Font(R.font.kiwifruit, FontWeight.Normal)
    )

    val Typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge = TextStyle(
            fontFamily = KiwiFruit,
            fontWeight = FontWeight.Normal,
            fontSize = 40.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )