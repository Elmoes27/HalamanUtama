package com.example.halamanutama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(amount: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
    return formatter.format(amount)
}

// Warna yang digunakan
val NavyBlue = Color(0xFF0B1A4A)
val RedColor = Color(0xFFE53935)
val SoftBg = Color(0xFFF5F5F7)

// Shape
class WaveBottomShape(private val waveHeight: Dp = 28.dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val waveHeightPx = with(density) { waveHeight.toPx() }
        val path = Path().apply {
            lineTo(0f, size.height - waveHeightPx)
            quadraticBezierTo(
                size.width / 2, size.height + waveHeightPx,
                size.width, size.height - waveHeightPx
            )
            lineTo(size.width, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}

// Tanggal pada date selector
data class DateOption(val day: Int, val label: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTicketBooking()
        }
    }
}

@Composable
fun MovieTicketBooking() {
    var quantity by remember { mutableStateOf(1) }
    val pricePerTicket = 45000
    val total = quantity * pricePerTicket
    val JudulFilm = "ODESSY"

    val dateOptions = listOf(
        DateOption(18, "SEN"),
        DateOption(19, "SEL"),
        DateOption(20, "RAB"),
        DateOption(21, "KAM"),
        DateOption(22, "JUM")
    )
    var selectedDate by remember { mutableStateOf(20) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBg)
    ) {
        // Header atas
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(WaveBottomShape(waveHeight = 28.dp))
                .background(NavyBlue)
                .padding(vertical = 32.dp, horizontal = 24.dp)
                .padding(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Pesan Tiket Nonton",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        // Isian konten
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Pemilihan tanggal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                dateOptions.forEach { option ->
                    val isSelected = option.day == selectedDate
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) NavyBlue else Color.White)
                            .clickable { selectedDate = option.day }
                            .padding(vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${option.day}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else NavyBlue
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = option.label,
                            fontSize = 12.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color.Gray
                        )
                    }
                }
            }

            // Kotak harga + judul film
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = JudulFilm,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyBlue
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Harga Tiket", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Rp${formatRupiah(pricePerTicket)}",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(text = "per tiket", fontSize = 13.sp)
                }
            }

            // Kotak Jumlah Tiket
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Jumlah Tiket", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(NavyBlue)
                                .clickable { if (quantity > 1) quantity-- },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "–",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Text(
                            text = "$quantity",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(NavyBlue)
                                .clickable { if (quantity < 10) quantity++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Kotak Total
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Total", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Rp${formatRupiah(total)}",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1B7A43)
                    )
                }
            }
        }

        // Tombol reset
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { quantity = 1 },
                colors = ButtonDefaults.buttonColors(containerColor = RedColor),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "RESET",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}