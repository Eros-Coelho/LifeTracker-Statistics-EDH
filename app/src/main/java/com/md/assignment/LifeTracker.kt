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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.rotate

class LifeTracker : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        retrieving player names from game setup using getStringExtra
//        ensures that if text fields are left blank the app shows "Player N" instead of blank
        val player1Name =
            intent.getStringExtra("PLAYER_1")?.ifBlank{"Player 1"}?: "Player 1"
        val player2Name =
            intent.getStringExtra("PLAYER_2")?.ifBlank{"Player 2"}?: "Player 2"
        val player3Name =
            intent.getStringExtra("PLAYER_3")?.ifBlank{"Player 3"}?: "Player 3"
        val player4Name =
            intent.getStringExtra("PLAYER_4")?.ifBlank{"Player 4"}?: "Player 4"

        setContent {
            //vals for all values that will be changed during a game
            //using one different val for each player's life total to make it so a future feature can check life totals
            //and automatically declare a winner based on who has life above zero when the game ends
            val round = remember{mutableIntStateOf(0)}
            val player1Life = remember {mutableIntStateOf(40)}
            val player2Life = remember {mutableIntStateOf(40)}
            val player3Life = remember {mutableIntStateOf(40)}
            val player4Life = remember {mutableIntStateOf(40)}

//            no box on this activity because I needed a specific layout for the life tracker itself
            Column (
                modifier = Modifier.fillMaxSize()
            ){
//                uses 3 rows for players 1 and 2, a narrow row in the middle for option buttons, then another row at the bottom for players 3 and 4
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    //create columns inside rows so they can rotate properly and every player can see their own life total without issues
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .rotate(90f)
                    ) {
//                        calling out the function PlayerCounter and assigning individual players' life totals to them
//                        assigns names for players based on names provided in game set up
                        PlayerCounter(player1Name ?: "Player 1", player1Life.intValue, Modifier.weight(1f)) {
                            player1Life.intValue = it //callback from PlayerCounter and updates player 1's state
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .rotate(270f)) {
                        PlayerCounter(player2Name ?: "Player 2", player2Life.intValue, Modifier.weight(1f)) {
                            player2Life.intValue = it
                        }
                    }
                }
                //small row in the middle for buttons to leave game and round count
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.15f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { //ends current game and won't be added to statistics, inconclusive result
                        finish()
                    }){
                        Text("Main Menu") //will add a "are you sure?" option later before ending the game
                    }
                    Button(onClick = { //increases round count for both players and for statistics later
                        round.intValue++
                    }){
                        Text("New Round")
                    }

                    Text(text = round.intValue.toString())

                    Button(onClick = {}){ //empty for now, will add functionality to it later
                        Text("End Game")
                    }
                }
                Row ( //same logic and layout from the first row with the two other players
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
//                    again, individual columns so they can rotate independently
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .rotate(90f)
                    ){
//                        same logic as players 1 and 2
                        PlayerCounter(player3Name ?: "Player 3", player3Life.intValue, Modifier.weight(1f)){
                            player3Life.intValue = it
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .rotate(270f)) {
                            PlayerCounter(player4Name ?: "Player 4", player4Life.intValue, Modifier.weight(1f)) {
                                player4Life.intValue = it
                            }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerCounter(playerName: String, lifeTotal: Int, modifier: Modifier = Modifier, onLifeChange: (Int) -> Unit){

    Column(modifier = modifier
        .fillMaxSize()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {

        Text(playerName) //displaying player name on top

        //layout done like this so the buttons for increasing and decreasing life totals are directly on either side of life total
        Row {
            Button(onClick = {
                onLifeChange(lifeTotal - 1)
            }) {
                Text("-")
            }
            Text(
                text = lifeTotal.toString(),
                fontSize = 48.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Button(onClick = {
                onLifeChange(lifeTotal + 1)
            }) {
                Text("+")
            }
        }
    }
}