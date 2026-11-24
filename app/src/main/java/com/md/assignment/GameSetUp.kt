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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextField
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlin.math.abs

class GameSetUp : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastTimeShook = 0L //keeps last time the device was shaken
    private val cooldown =
        2000L // 2 seconds cooldown to prevent shaking being detected over and over again
    private var shakeDetected by mutableStateOf(false) //keep track if a shake has been already detected or not
    private var firstPlayer by mutableStateOf("") //storing player to go first

    //    creating 4 variables with mutable state to store player names and later pass them on to the life tracker
    private var player1Name by mutableStateOf("")
    private var player2Name by mutableStateOf("")
    private var player3Name by mutableStateOf("")
    private var player4Name by mutableStateOf("")

    //    storing the accelerometer readings and controlling shake detection
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f

    //    controlling how and when it detects shaking movements
    private val threshold = 4f
    private var previousReading = false
    private var waitingShake = false

    override fun onSensorChanged(event: SensorEvent?) {

        if (event != null) { //triggers whenever an event happens
            val x = event.values[0] //getting x, y and z axis for sensor
            val y = event.values[1]
            val z = event.values[2]

//            storing first reading as a base instead of registering it as a shake
            if (!previousReading) {
                lastX = x
                lastY = y
                lastZ = z
                previousReading = true
                return
            }

//            getting the difference between previous position and current position to determine if a shake occurred
            val deltaX = abs(x - lastX)
            val deltaY = abs(y - lastY)
            val deltaZ = abs(z - lastZ)

//            calculating absolute value of acceleration change on each value
            val acceleration = deltaX + deltaY + deltaZ

//            updating previous readings
            lastX = x
            lastY = y
            lastZ = z

//            getting current time
            val currentTime = System.currentTimeMillis()

//            after detecting a shake, it will ignore other shake detections for 2 seconds
//            only detects a shake if all conditions allow it
            if (acceleration > threshold && currentTime - lastTimeShook > cooldown && !shakeDetected && waitingShake) {
                lastTimeShook =
                    currentTime //updates last time shook to current time whenever a shake is detected
                shakeDetected = true //changes to shake detected if a shake is detected
                waitingShake = false

                val players = listOf( //creating a list of input players
                    player1Name,
                    player2Name,
                    player3Name,
                    player4Name
                ).filter { it.isNotBlank() } //filtering it to avoid blank names

                if (players.isNotEmpty()) { //if none of the players are empty spaces, selects a random player to go first
                    firstPlayer = players.random()
                }
                println(firstPlayer)
            }
        }
    }

    //    no need for accuracy, so this will stay empty
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    //    registering the accelerometer listener whenever the activity becomes active
    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }

    //    unregistering sensor when activity is paused
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        initialising accelerometer and getting access to the sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        setContent {
            var showShakeDialog by remember { mutableStateOf(false) } //variable to show pop up notification when needed

//            box to keep everything in the centre
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF000021))
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                    text fields for users to enter players' names
                    Text(
                        text = "Game Set Up",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextField(
                        value = player1Name,
                        onValueChange = { player1Name = it },
                        label = { Text("Player 1") }
                    )
                    TextField(
                        value = player2Name,
                        onValueChange = { player2Name = it },
                        label = { Text("Player 2") }
                    )
                    TextField(
                        value = player3Name,
                        onValueChange = { player3Name = it },
                        label = { Text("Player 3") }
                    )
                    TextField(
                        value = player4Name,
                        onValueChange = { player4Name = it },
                        label = { Text("Player 4") }
                    )

                    if (shakeDetected) { //if shake is detected and there's already a firstPlayer, show the name on screen
                        if (firstPlayer.isNotBlank()) {
                            Text("$firstPlayer goes first!")
                        } else { //error message saying no player names were entered before shaking
                            Text("No player names entered!")
                        }
                    }

//                    start button that takes the user to a pop up screen and resets variables as needed
                    Button(
                        onClick = {
                            shakeDetected = false
                            showShakeDialog = true
                            firstPlayer = ""
                            waitingShake = true
                            previousReading = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff607d8b),
                            contentColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Start")
                    }

//                    button to take user back to main menu
                    Button(
                        onClick = {
                            val intent = Intent(this@GameSetUp, MainActivity::class.java)
                            startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff607d8b),
                            contentColor = Color(0xffffffff)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Back")
                    }
                }
            }
            if (showShakeDialog) { //showing pop up window
                AlertDialog(
                    onDismissRequest = { //dismissing request
                        showShakeDialog = false
                        shakeDetected = false
                        firstPlayer = ""
                        waitingShake = false
                    },
                    title = {
                        Text("See who goes first!")
                    },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (shakeDetected) { //if shaken and no player has been selected to go first, select a player to go first
                                if (firstPlayer.isNotBlank()) {
                                    Text("$firstPlayer goes first!")
                                } else {
                                    Text("No player names entered!") //otherwise tell the user no player names have been entered
                                }
                            } else { //shake not detected yet
                                Text("Shake your phone to choose who goes first!")
                            }

//                            button to simulate shaking on emulator
                            Button(
                                onClick = {
                                    val players = listOf(
                                        player1Name,
                                        player2Name,
                                        player3Name,
                                        player4Name
                                    ).filter { it.isNotBlank() }

                                    if (players.isNotEmpty()) { //selects a random player to start and artificially detects a shake movement
                                        firstPlayer = players.random()
                                        shakeDetected = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xff607d8b),
                                    contentColor = Color(0xffffffff)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Simulate Shaking")
                            }
                        }
                    },
//                    button to start the game after players names were entered and first player was chosen
                    confirmButton = {
                        Button(
                            onClick = {
                                val intent = Intent(this@GameSetUp, LifeTracker::class.java)

//                            passing on player names to life tracker activity
                                intent.putExtra("PLAYER_1", player1Name)
                                intent.putExtra("PLAYER_2", player2Name)
                                intent.putExtra("PLAYER_3", player3Name)
                                intent.putExtra("PLAYER_4", player4Name)

                                intent.putExtra(
                                    "FIRST_PLAYER",
                                    firstPlayer
                                ) //passing on first player to life tracker

                                startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff607d8b),
                                contentColor = Color(0xffffffff)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Start the Game")
                        }
                    },
                    dismissButton = { //dismiss button, resets the first player and goes back to game set up
                        Button(
                            onClick = {
                                showShakeDialog = false
                                shakeDetected = false
                                firstPlayer = ""
                                waitingShake = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff607d8b),
                                contentColor = Color(0xffffffff)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}