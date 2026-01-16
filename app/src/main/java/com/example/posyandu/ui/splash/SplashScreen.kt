package com.example.posyandu.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posyandu.R
import com.example.posyandu.viewmodel.AdminViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: AdminViewModel,
    onAnimationFinished: (Boolean) -> Unit
) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        // 1. Jalankan animasi
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )

        // 2. Beri jeda sedikit agar user bisa melihat logo (Total 2 detik)
        delay(1000)

        // 3. Panggil pengecekan sesi dari database (Logika Flowchart: Cek Sesi)
        viewModel.checkSession { isLoggedIn ->
            onAnimationFinished(isLoggedIn)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFD1DC), // Soft Pink
                        Color(0xFFFF69B4)  // Hot Pink
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha.value)
        ) {
            // Lingkaran Putih di belakang Logo
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo Posyandu",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "APLIKASI POSYANDU",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Text(
                text = "Monitoring Kesehatan Ibu & Balita",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }

        Text(
            text = "v1.0.0",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
    }
}