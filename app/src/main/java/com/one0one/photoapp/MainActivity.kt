package com.one0one.photoapp

import android.Manifest;
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.one0one.photoapp.model.Places
import com.one0one.photoapp.model.UserMap
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
const val EXTRA_MAP_TITLE = "EXTRA_MAP_TITLE"

private const val REQUEST_CODE = 1234

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val userMaps = generateSampleData()
        //Set layout manager on the recycle view
        rvMaps.layoutManager = LinearLayoutManager(this)
//        // Set adapter on recycle view

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
            } else {
                // we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }
        rvMaps.adapter = MapsAdapter(this, userMaps, object: MapsAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                Log.i(TAG, "OnClickListen $position")
                //When user taps on view in RV, navigate to the new activity
                val intent  = Intent(this@MainActivity, DisplayMapActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, userMaps[position])
                startActivity(intent)
            }

        })

        fabCreateJourney.setOnClickListener {
            Log.i(TAG, "Tap on FAB")
            val intent = Intent(this@MainActivity, CreateMapActivity::class.java)
            intent.putExtra(EXTRA_MAP_TITLE, "new map name")
            startActivityForResult(intent, REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //Get new map data from the data

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun generateSampleData(): List<UserMap> {
        return listOf(
            UserMap(
                "Memories from University",
                listOf(
                    Places("Branner Hall", "Best dorm at Stanford", 37.426, -122.163),
                    Places("Gates CS building", "Many long nights in this basement", 37.430, -122.173),
                    Places("Pinkberry", "First date with my wife", 37.444, -122.170)
                )
            ),
            UserMap("January vacation planning!",
                listOf(
                    Places("Tokyo", "Overnight layover", 35.67, 139.65),
                    Places("Ranchi", "Family visit + wedding!", 23.34, 85.31),
                    Places("Singapore", "Inspired by \"Crazy Rich Asians\"", 1.35, 103.82)
                )),
            UserMap("Singapore travel itinerary",
                listOf(
                    Places("Gardens by the Bay", "Amazing urban nature park", 1.282, 103.864),
                    Places("Jurong Bird Park", "Family-friendly park with many varieties of birds", 1.319, 103.706),
                    Places("Sentosa", "Island resort with panoramic views", 1.249, 103.830),
                    Places("Botanic Gardens", "One of the world's greatest tropical gardens", 1.3138, 103.8159)
                )
            ),
            UserMap("My favorite places in the Midwest",
                listOf(
                    Places("Chicago", "Urban center of the midwest, the \"Windy City\"", 41.878, -87.630),
                    Places("Rochester, Michigan", "The best of Detroit suburbia", 42.681, -83.134),
                    Places("Mackinaw City", "The entrance into the Upper Peninsula", 45.777, -84.727),
                    Places("Michigan State University", "Home to the Spartans", 42.701, -84.482),
                    Places("University of Michigan", "Home to the Wolverines", 42.278, -83.738)
                )
            ),
            UserMap("Restaurants to try",
                listOf(
                    Places("Champ's Diner", "Retro diner in Brooklyn", 40.709, -73.941),
                    Places("Althea", "Chicago upscale dining with an amazing view", 41.895, -87.625),
                    Places("Shizen", "Elegant sushi in San Francisco", 37.768, -122.422),
                    Places("Citizen Eatery", "Bright cafe in Austin with a pink rabbit", 30.322, -97.739),
                    Places("Kati Thai", "Authentic Portland Thai food, served with love", 45.505, -122.635)
                )
            )
        )
    }
}