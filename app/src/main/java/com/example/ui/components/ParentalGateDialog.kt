package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun ParentalGateDialog(
  onDismiss: () -> Unit,
  onVerified: () -> Unit
) {
  // Generate random simple math challenge
  val num1 = remember { (6..9).random() }
  val num2 = remember { (6..8).random() }
  val correctAnswer = remember { num1 * num2 }

  var selectedOption by remember { mutableStateOf<Int?>(null) }
  var isError by remember { mutableStateOf(false) }

  // 4 answer choices including correct
  val options = remember {
    val set = mutableSetOf(correctAnswer)
    while (set.size < 4) {
      val delta = (-10..10).random()
      val candidate = correctAnswer + delta
      if (candidate > 0 && candidate != correctAnswer) {
        set.add(candidate)
      }
    }
    set.toList().shuffled()
  }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      tonalElevation = 6.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("parental_gate_dialog")
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MintContainer),
          contentAlignment = Alignment.Center
        ) {
          Text("🛡️", fontSize = 28.sp)
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Grown-ups Only",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "To access Parent Settings & Subscription controls, please solve this question:",
          fontSize = 13.sp,
          color = TextMuted,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = IvoryBackground,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "$num1 × $num2 = ?",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = EmeraldPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
          )
        }

        if (isError) {
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Incorrect, please try again or ask a parent.",
            color = CoralFun,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2x2 grid of options
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          for (row in options.chunked(2)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              row.forEach { choice ->
                Button(
                  onClick = {
                    selectedOption = choice
                    if (choice == correctAnswer) {
                      isError = false
                      onVerified()
                    } else {
                      isError = true
                    }
                  },
                  modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("gate_option_$choice"),
                  colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedOption == choice) EmeraldPrimary else SkyContainer,
                    contentColor = if (selectedOption == choice) Color.White else OnSkyContainer
                  ),
                  shape = RoundedCornerShape(14.dp)
                ) {
                  Text(text = "$choice", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("cancel_parent_gate_button")
        ) {
          Text("Return to Kids World", color = TextMuted, fontSize = 13.sp)
        }
      }
    }
  }
}
