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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width

class LifeTracker : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        retrieving player names from game setup using getStringExtra
//        ensures that if text fields are left blank the app shows "Player N" instead of blank
        val player1Name = intent.getStringExtra("PLAYER_1")?.ifBlank { "Player 1" } ?: "Player 1"
        val player2Name = intent.getStringExtra("PLAYER_2")?.ifBlank { "Player 2" } ?: "Player 2"
        val player3Name = intent.getStringExtra("PLAYER_3")?.ifBlank { "Player 3" } ?: "Player 3"
        val player4Name = intent.getStringExtra("PLAYER_4")?.ifBlank { "Player 4" } ?: "Player 4"

        val firstPlayer = intent.getStringExtra("FIRST_PLAYER") ?: ""

        setContent {
            //vals for all values that will be changed during a game
            //using one different val for each player's life total to make it so a future feature can check life totals
            //and automatically declare a winner based on who has life above zero when the game ends
            val round = remember { mutableIntStateOf(0) }
            val player1Life = remember { mutableIntStateOf(40) }
            val player2Life = remember { mutableIntStateOf(40) }
            val player3Life = remember { mutableIntStateOf(40) }
            val player4Life = remember { mutableIntStateOf(40) }

//            no box on this activity because I needed a specific layout for the life tracker itself
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff424242))
            ) {
//                uses 3 rows for players 1 and 2, a narrow row in the middle for option buttons, then another row at the bottom for players 3 and 4
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    //create columns inside rows so they can rotate properly and every player can see their own life total without issues
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .rotate(90f)
                    ) {
//                        calling out the function PlayerCounter and assigning individual players' life totals to them
//                        assigns names for players based on names provided in game set up
                        PlayerCounter(
                            if (player1Name == firstPlayer) "$player1Name \uD83D\uDC51" else player1Name,
                            player1Life.intValue,
                            Modifier.weight(1f)
                        ) {
                            player1Life.intValue = it
                        }
                    }
//                    adding a box between columns to separate them visually from each other
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(4.dp)
                            .background(Color.Black)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .rotate(270f)
                    ) {
                        PlayerCounter(
                            if (player2Name == firstPlayer) "$player2Name \uD83D\uDC51" else player2Name,
                            player2Life.intValue,
                            Modifier.weight(1f)
                        ) {
                            player2Life.intValue = it
                        }
                    }
                }
                //small row in the middle for buttons to leave game and round count
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.15f)
                        .background(Color(0xff000000)),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { //ends current game and won't be added to statistics, inconclusive result
                            finish()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff607d8b),
                            contentColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Main Menu") //will add a "are you sure?" option later before ending the game
                    }

                    Button(
                        onClick = { //increases round count for both players and for statistics later
                            round.intValue++
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff607d8b),
                            contentColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("New Round")
                    }

                    Text(
                        text = round.intValue.toString(),
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff607d8b),
                            contentColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("End Game")
                    }
                }
                Row( //same logic and layout from the first row with the two other players
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
//                    again, individual columns so they can rotate independently
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .rotate(90f)
                    ) {
//                        same logic as players 1 and 2
                        PlayerCounter(
                            if (player3Name == firstPlayer) "$player3Name \uD83D\uDC51" else player3Name,
                            player3Life.intValue,
                            Modifier.weight(1f)
                        ) {
                            player3Life.intValue = it
                        }
                    }
//                    separating same as players 1 and 2
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(4.dp)
                            .background(Color.Black)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .rotate(270f)
                    ) {
                        PlayerCounter(
                            if (player4Name == firstPlayer) "$player4Name \uD83D\uDC51" else player4Name,
                            player4Life.intValue,
                            Modifier.weight(1f)
                        ) {
                            player4Life.intValue = it
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerCounter(
    playerName: String,
    lifeTotal: Int,
    modifier: Modifier = Modifier,
    onLifeChange: (Int) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

//        displaying player name on top in white
        Text(
            text = playerName,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        //layout done like this so the buttons for increasing and decreasing life totals are directly on either side of life total
        Row {
            Button(
                onClick = {
                    onLifeChange(lifeTotal - 1)
                },
                modifier = Modifier.size(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "-",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = lifeTotal.toString(),
                fontSize = 48.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.White
            )
            Button(
                onClick = {
                    onLifeChange(lifeTotal + 1)
                },
                modifier = Modifier.size(72.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = " + ",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp
                )
            }
        }
    }
}