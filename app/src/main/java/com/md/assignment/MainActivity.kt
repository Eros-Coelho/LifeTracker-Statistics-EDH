package com.md.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.md.assignment.ui.theme.Assignment_MDTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import android.view.Menu
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.offset
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            box to make it so the buttons are shown in the centre of the screen for now
//            will probably change the centering when I do these pages on future milestones
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF000021))
            ) {
                Column( //columns with buttons linking the different activities, but only relevant ones
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Main Menu",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = {
                            val intent = Intent(this@MainActivity, GameSetUp::class.java)
                            startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff607d8b),
                            contentColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("New Game")
                    }
                    Button(
                        onClick = {
                            val intent = Intent(this@MainActivity, Statistics::class.java)
                            startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff607d8b),
                            contentColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("View Statistics")
                    }
//                    some banter specific to players of the game
                    Text(
                        text = "Remember:",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "If your commander is in the Top 100 on EDHREC, you can't complain when people say you are the threat!",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
//                    button leading to the EDHREC website, a popular site to see best and most used cards in the game
                    Button(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://edhrec.com/commanders")
                            )
                            startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff607d8b),
                            contentColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Check Out the Most Popular Commanders!")
                    }
                }
            }
        }
    }
}