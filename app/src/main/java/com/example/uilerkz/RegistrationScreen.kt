package com.example.uilerkz

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
fun RegistrationScreen() {
    var mail by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Uiler.kz",
                style = TextStyle(
                    fontSize = 30.sp,
                    lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "easy to live",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
        }
        Spacer(Modifier.height(70.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(362.dp)
                .background(color = Color(0xFFE7E7E5), shape = RoundedCornerShape(size = 52.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(modifier = Modifier.padding(start = 50.dp, end = 50.dp , top = 20.dp)) {
                Text(
                    text = "Email",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ),

                    )
                Spacer(Modifier.height(10.dp))
                BasicTextField(
                    value = mail,
                    onValueChange = {
                        mail = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().height(23.dp)
                )
                Box(Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .height(1.5.dp)
                    .background(color = Color(0xFF000000)))
            }
            Spacer(Modifier.height(30.dp))

            Column(modifier =  Modifier.padding(start = 50.dp, end = 50.dp)) {

                Text(
                    text = "Phone number",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(Modifier.height(10.dp))
                BasicTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().height(23.dp)
                )
                Box(Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .height(1.5.dp)
                    .background(color = Color(0xFF000000)))
            }
            Spacer(Modifier.height(30.dp))

            Column(modifier =  Modifier.padding(start = 50.dp, end = 50.dp)) {
                Text(
                    text = "Password",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
//                    fontFamily = FontFamily(Font(R.font.space_grotesk)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ),

                    )
                Spacer(Modifier.height(10.dp))
                BasicTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().height(23.dp)
                )
                Box(Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .height(1.5.dp)
                    .background(color = Color(0xFF000000)))
            }
            Spacer(Modifier.height(50.dp))
            Button(
                onClick = {
//                    signUp(auth, email = mail, phone = phone, password = password)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF888888),
                    contentColor = Color(0xFF000000),
                    disabledContainerColor = Color(0xFFE7E7E5),
                    disabledContentColor = Color(0xFFE7E7E5)
                ),
                modifier = Modifier.width(148.dp).height(33.dp)
            ){
                Text(
                    text = "Sign up",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}
//private fun signUp(auth: FirebaseAuth, email: String, phone: String, password:String){
//    auth.createUserWithEmailAndPassword(email,password)
//        .addOnCompleteListener{task ->
//            if (task.isSuccessful) {
//                Log.d("sign up","signed up")
//            } else {
//                Log.d("sign up","cannot sign up")
//
//            }
//
//        }
//}