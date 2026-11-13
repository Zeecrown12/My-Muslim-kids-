package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.AppFlowScreen
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun ParentAuthScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Register, 1: Login

  // Registration form state
  var parentName by remember { mutableStateOf("") }
  var regEmail by remember { mutableStateOf("") }
  var regPassword by remember { mutableStateOf("") }
  var regPasswordConfirm by remember { mutableStateOf("") }
  var acceptTerms by remember { mutableStateOf(false) }
  var regError by remember { mutableStateOf<String?>(null) }
  var showPassword by remember { mutableStateOf(false) }

  // Login form state
  var loginEmail by remember { mutableStateOf("parent@mymuslimkids.com") }
  var loginPassword by remember { mutableStateOf("Salam1234") }
  var loginError by remember { mutableStateOf<String?>(null) }
  var showForgotPasswordDialog by remember { mutableStateOf(false) }

  val scrollState = rememberScrollState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("parent_auth_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
      // Top Navigation & Return button
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = { viewModel.navigateToFlow(AppFlowScreen.WELCOME) },
          modifier = Modifier.testTag("back_to_welcome_button")
        ) {
          Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDark)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Parent & Guardian Gateway",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Trust Banner
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = MintContainer,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(Color.White),
            contentAlignment = Alignment.Center
          ) {
            Text("🛡️", fontSize = 22.sp)
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Parent-Controlled & Family Safe",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldDark
            )
            Text(
              text = "Manage child profiles, monitor screen time & subscription securely.",
              fontSize = 11.sp,
              color = OnMintContainer
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Tab switcher: Register vs Sign In
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        TabRow(
          selectedTabIndex = selectedTab,
          containerColor = Color.White,
          contentColor = EmeraldPrimary
        ) {
          Tab(
            selected = selectedTab == 0,
            onClick = {
              selectedTab = 0
              regError = null
            },
            text = { Text("Create Account", fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = selectedTab == 1,
            onClick = {
              selectedTab = 1
              loginError = null
            },
            text = { Text("Sign In", fontWeight = FontWeight.Bold) }
          )
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      if (selectedTab == 0) {
        // REGISTRATION FORM
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Parent / Guardian Registration",
              fontSize = 17.sp,
              fontWeight = FontWeight.ExtraBold,
              color = TextDark
            )
            Text(
              text = "Create your family account to set up child profiles.",
              fontSize = 12.sp,
              color = TextMuted
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Parent Name
            OutlinedTextField(
              value = parentName,
              onValueChange = { parentName = it },
              label = { Text("Parent / Guardian Full Name") },
              leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = EmeraldPrimary) },
              singleLine = true,
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Email Address
            OutlinedTextField(
              value = regEmail,
              onValueChange = { regEmail = it },
              label = { Text("Parent's Email Address") },
              leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = EmeraldPrimary) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
              singleLine = true,
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password
            OutlinedTextField(
              value = regPassword,
              onValueChange = { regPassword = it },
              label = { Text("Create Password") },
              leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = EmeraldPrimary) },
              trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                  Icon(
                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null
                  )
                }
              },
              visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
              singleLine = true,
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Confirm Password
            OutlinedTextField(
              value = regPasswordConfirm,
              onValueChange = { regPasswordConfirm = it },
              label = { Text("Confirm Password") },
              leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = EmeraldPrimary) },
              visualTransformation = PasswordVisualTransformation(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
              singleLine = true,
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Terms Checkbox
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .fillMaxWidth()
                .clickable { acceptTerms = !acceptTerms }
            ) {
              Checkbox(
                checked = acceptTerms,
                onCheckedChange = { acceptTerms = it },
                colors = CheckboxDefaults.colors(checkedColor = EmeraldPrimary)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "I accept the Terms of Service & Privacy Policy",
                fontSize = 12.sp,
                color = TextDark
              )
            }

            if (regError != null) {
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = regError!!,
                color = CoralFun,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
              onClick = {
                when {
                  parentName.isBlank() -> regError = "Please enter parent/guardian name."
                  regEmail.isBlank() || !regEmail.contains("@") -> regError = "Please enter a valid email."
                  regPassword.length < 6 -> regError = "Password must be at least 6 characters."
                  regPassword != regPasswordConfirm -> regError = "Passwords do not match."
                  !acceptTerms -> regError = "Please accept the Terms & Privacy Policy."
                  else -> {
                    regError = null
                    viewModel.onParentRegistered(parentName.trim(), regEmail.trim())
                  }
                }
              },
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("submit_parent_registration_button"),
              colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
              shape = RoundedCornerShape(16.dp)
            ) {
              Text(
                text = "Proceed to Create Child Profile →",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      } else {
        // LOGIN FORM
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Sign In to Parent Account",
              fontSize = 17.sp,
              fontWeight = FontWeight.ExtraBold,
              color = TextDark
            )
            Text(
              text = "Manage your children's profiles and subscriptions.",
              fontSize = 12.sp,
              color = TextMuted
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
              value = loginEmail,
              onValueChange = { loginEmail = it },
              label = { Text("Parent Email") },
              leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = EmeraldPrimary) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
              singleLine = true,
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
              value = loginPassword,
              onValueChange = { loginPassword = it },
              label = { Text("Password") },
              leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = EmeraldPrimary) },
              visualTransformation = PasswordVisualTransformation(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
              singleLine = true,
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp)
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.End
            ) {
              TextButton(onClick = { showForgotPasswordDialog = true }) {
                Text("Forgot Password?", fontSize = 12.sp, color = EmeraldPrimary, fontWeight = FontWeight.Bold)
              }
            }

            if (loginError != null) {
              Text(
                text = loginError!!,
                color = CoralFun,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
              Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
              onClick = {
                if (loginEmail.isBlank() || loginPassword.isBlank()) {
                  loginError = "Please enter both email and password."
                } else {
                  loginError = null
                  viewModel.onParentLoggedIn(loginEmail)
                }
              },
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("submit_parent_login_button"),
              colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
              shape = RoundedCornerShape(16.dp)
            ) {
              Text("Sign In to Family Account", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Third-party Google sign-in simulation
            OutlinedButton(
              onClick = {
                viewModel.onParentLoggedIn("parent.google@mymuslimkids.com")
              },
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
              shape = RoundedCornerShape(16.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
              Text("🌐", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Continue with Google", fontSize = 14.sp, color = TextDark, fontWeight = FontWeight.Medium)
            }
          }
        }
      }
    }

    // Forgot Password Dialog
    if (showForgotPasswordDialog) {
      var resetEmail by remember { mutableStateOf(loginEmail) }
      var isSent by remember { mutableStateOf(false) }

      Dialog(onDismissRequest = { showForgotPasswordDialog = false }) {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = Color.White,
          modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text("Reset Parent Password", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              "Enter your registered parent email address. We will send you secure recovery instructions.",
              fontSize = 12.sp,
              color = TextMuted
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
              value = resetEmail,
              onValueChange = { resetEmail = it },
              label = { Text("Parent Email") },
              singleLine = true,
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp)
            )

            if (isSent) {
              Spacer(modifier = Modifier.height(10.dp))
              Surface(color = MintContainer, shape = RoundedCornerShape(10.dp)) {
                Text(
                  text = "Password reset instructions sent to $resetEmail!",
                  fontSize = 12.sp,
                  color = OnMintContainer,
                  modifier = Modifier.padding(10.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.End
            ) {
              TextButton(onClick = { showForgotPasswordDialog = false }) {
                Text("Close", color = TextMuted)
              }
              Spacer(modifier = Modifier.width(8.dp))
              Button(
                onClick = { isSent = true },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
              ) {
                Text("Send Reset Link")
              }
            }
          }
        }
      }
    }
  }
}
